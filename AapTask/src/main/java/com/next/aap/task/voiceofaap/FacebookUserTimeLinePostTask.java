package com.next.aap.task.voiceofaap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.PlannedFacebookPostDto;
import com.next.aap.web.dto.PlannedPostStatus;

@Component
public class FacebookUserTimeLinePostTask extends BaseSocialTask{

	@Autowired
	private AapService aapService;
	
	@Autowired
	@Qualifier(value="facebookTimeLinePostThreadPool")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	//@Scheduled(cron="0 45 00 * * *")
	@Scheduled(fixedDelay=300000)
	public void runTask(){
		PlannedFacebookPostDto plannedFacebookPostDto = aapService.getNextPlannedFacebookPostToPublish();
		if(plannedFacebookPostDto == null){
			//No post to publish
			logger.info("No Plnaned post found");
			return;
		}
		logger.info("plannedFacebookPostDto="+plannedFacebookPostDto);
		List<FacebookAccountDto> allFacebookAccounts = aapService.getAllFacebookAccountsForVoiceOfAap(plannedFacebookPostDto.getLocationType(), plannedFacebookPostDto.getLocationId());
		logger.info("allFacebookAccounts="+allFacebookAccounts);
		Map<Future<Boolean>, FacebookAccountDto> facebookAccountsFutureMap = new HashMap<Future<Boolean>, FacebookAccountDto>();
		int totalSuccessTimeLines = 0;
		int totalSuccessTimeLineFriends = 0;
		int totalFailedTimeLines = 0;
		int totalFailedTimeLineFriends = 0;
		try{
			if(allFacebookAccounts != null && !allFacebookAccounts.isEmpty()){
				CountDownLatch countDownLatch = new CountDownLatch(allFacebookAccounts.size());
				for(FacebookAccountDto oneFacebookAccount:allFacebookAccounts){
					PostOnUserFacebookTimeLineTask postOnUserTimeLineTask = new PostOnUserFacebookTimeLineTask(aapService, oneFacebookAccount, plannedFacebookPostDto, countDownLatch);
					Future<Boolean> futureResult = threadPoolTaskExecutor.submit(postOnUserTimeLineTask);
					facebookAccountsFutureMap.put(futureResult, oneFacebookAccount);
					sleep();
				}
				//wait for all task to finish before proceeding
				countDownLatch.await();
				for(Entry<Future<Boolean>, FacebookAccountDto> oneEntry:facebookAccountsFutureMap.entrySet()){
					if(oneEntry.getKey().get()){
						totalSuccessTimeLines++;
						totalSuccessTimeLineFriends = totalSuccessTimeLineFriends + oneEntry.getValue().getTotalFriends();
					}else{
						totalFailedTimeLines++;
						totalFailedTimeLineFriends = totalFailedTimeLineFriends + oneEntry.getValue().getTotalFriends();
					}
				}
				
			}
			logger.info("Total Success TimeLine" + totalSuccessTimeLines+", Total Friends "+ totalSuccessTimeLineFriends);
			logger.info("Total Failed TimeLine" + totalFailedTimeLines+", Total Friends "+ totalFailedTimeLineFriends);
			aapService.updatePlannedFacebookPostStatus(plannedFacebookPostDto.getId(), PlannedPostStatus.DONE, null, totalSuccessTimeLines,
					totalSuccessTimeLineFriends, totalFailedTimeLines, totalFailedTimeLineFriends);
		}catch(Exception ex){
			aapService.updatePlannedFacebookPostStatus(plannedFacebookPostDto.getId(), PlannedPostStatus.DONE_WITH_ERROR, null, totalSuccessTimeLines,
					totalSuccessTimeLineFriends, totalFailedTimeLines, totalFailedTimeLineFriends);
		}
		
		
		
	}
}
