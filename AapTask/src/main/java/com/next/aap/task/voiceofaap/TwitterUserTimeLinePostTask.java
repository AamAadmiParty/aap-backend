package com.next.aap.task.voiceofaap;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.PlannedFacebookPostDto;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PlannedTweetDto;
import com.next.aap.web.dto.TwitterAccountDto;

@Component
public class TwitterUserTimeLinePostTask {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapService aapService;
	
	@Autowired
	@Qualifier(value="twitterTimeLinePostThreadPool")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Value("${twitter_consumer_key}")
	private String twitterConsumerKey;
	
	@Value("${twitter_consumer_secret}")
	private String twitterConsumerSecret;


	//@Scheduled(cron="0 45 00 * * *")
	@Scheduled(fixedDelay=60000)
	public void runTask(){
		while(true){
			PlannedTweetDto plannedTweetDto = aapService.getNextPlannedTweetToPublish();
			if(plannedTweetDto == null){
				//No post to publish
				logger.info("No Planned Tweet found");
				return;
			}
			List<TwitterAccountDto> allTwitterAccounts = aapService.getAllTwitterAccountsForVoiceOfAap(plannedTweetDto.getLocationType(), plannedTweetDto.getLocationId());
			try{
				if(allTwitterAccounts != null && !allTwitterAccounts.isEmpty()){
					CountDownLatch countDownLatch = new CountDownLatch(allTwitterAccounts.size());
					for(TwitterAccountDto oneTwitterAccount:allTwitterAccounts){
						PostOnUserTwitterTimeLineTask postOnUserTimeLineTask = new PostOnUserTwitterTimeLineTask(aapService, oneTwitterAccount, plannedTweetDto, countDownLatch, twitterConsumerKey, twitterConsumerSecret);
						threadPoolTaskExecutor.submit(postOnUserTimeLineTask);
					}
					//wait for all task to finish before proceeding
					countDownLatch.await();
					aapService.updatePlannedTweetStatus(plannedTweetDto.getId(), PlannedPostStatus.DONE, null);	
				}else{
					logger.info("No twitter accounts found for LocationType="+plannedTweetDto.getLocationType() +" and location Id="+plannedTweetDto.getLocationId());
					aapService.updatePlannedTweetStatus(plannedTweetDto.getId(), PlannedPostStatus.DONE_WITH_ERROR, "No twitter accounts found for LocationType="+plannedTweetDto.getLocationType() +" and location Id="+plannedTweetDto.getLocationId());
				}
				
			}catch(Exception ex){
				aapService.updatePlannedTweetStatus(plannedTweetDto.getId(), PlannedPostStatus.DONE_WITH_ERROR, ex.getMessage());
			}
		}
	}
}
