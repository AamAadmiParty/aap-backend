package com.next.aap.task.voiceofaap;

import java.util.List;
import java.util.concurrent.CountDownLatch;

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
public class FacebookUserTimeLinePostTask {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
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
		try{
			if(allFacebookAccounts != null && !allFacebookAccounts.isEmpty()){
				CountDownLatch countDownLatch = new CountDownLatch(allFacebookAccounts.size());
				for(FacebookAccountDto oneFacebookAccount:allFacebookAccounts){
					PostOnUserTimeLineTask postOnUserTimeLineTask = new PostOnUserTimeLineTask(aapService, oneFacebookAccount, plannedFacebookPostDto, countDownLatch);
					threadPoolTaskExecutor.submit(postOnUserTimeLineTask);
				}
				//wait for all task to finish before proceeding
				countDownLatch.await();
				
			}
			aapService.updatePlannedFacebookPostStatus(plannedFacebookPostDto.getId(), PlannedPostStatus.DONE, null);
		}catch(Exception ex){
			aapService.updatePlannedFacebookPostStatus(plannedFacebookPostDto.getId(), PlannedPostStatus.DONE_WITH_ERROR, null);
		}
		
		
		
	}

}
