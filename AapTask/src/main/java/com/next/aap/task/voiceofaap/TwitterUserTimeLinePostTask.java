package com.next.aap.task.voiceofaap;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PlannedTweetDto;
import com.next.aap.web.dto.TwitterAccountDto;

@Component
public class TwitterUserTimeLinePostTask extends BaseSocialTask{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapService aapService;
	
	@Autowired
	@Qualifier(value="twitterTimeLinePostThreadPool")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
    @Autowired
    @Qualifier(value = "parallelTweetThreadPool")
    private ThreadPoolTaskExecutor parallelTweetThreadPool;

	@Value("${twitter_consumer_key}")
	private String twitterConsumerKey;
	
	@Value("${twitter_consumer_secret}")
	private String twitterConsumerSecret;


    Long afterId = 0L;

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
            int totalTweetsRequired = 0;
            if (plannedTweetDto.getTotalRequired() != null && plannedTweetDto.getTotalRequired() > 0) {
                totalTweetsRequired = plannedTweetDto.getTotalRequired();
            }
            if(totalTweetsRequired == 0){
                totalTweetsRequired = 10000;
            }
            aapService.updatePlannedTweetStatus(plannedTweetDto.getId(), PlannedPostStatus.PROCESSING, null, 0, 0);
            List<TwitterAccountDto> allTwitterAccounts = aapService
                    .getAllTwitterAccountsForVoiceOfAap(plannedTweetDto.getLocationType(), plannedTweetDto.getLocationId(), afterId, totalTweetsRequired);
			logger.info("Total Acounts to retweet : "+ allTwitterAccounts.size());
            if (afterId > 0 && totalTweetsRequired < 10000 && allTwitterAccounts.size() < totalTweetsRequired) {
                afterId = 0L;
                int moreRequired = totalTweetsRequired - allTwitterAccounts.size();
                List<TwitterAccountDto> moreTwitterAccounts = aapService.getAllTwitterAccountsForVoiceOfAap(plannedTweetDto.getLocationType(), plannedTweetDto.getLocationId(), afterId, moreRequired);
                allTwitterAccounts.addAll(moreTwitterAccounts);
            }
            if (allTwitterAccounts.size() > 0) {
                afterId = allTwitterAccounts.get(allTwitterAccounts.size() - 1).getId();
            }

            TwitterUserTimeLinePostTaskAsync twitterUserTimeLinePostTaskAsync = new TwitterUserTimeLinePostTaskAsync(aapService, threadPoolTaskExecutor, twitterConsumerKey, twitterConsumerSecret,
                    allTwitterAccounts, plannedTweetDto);
            parallelTweetThreadPool.submit(twitterUserTimeLinePostTaskAsync);
            logger.info("Submitting parallel Retweet Task for tweet " + plannedTweetDto.getTweetId());

		}
	}
}
