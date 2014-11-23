package com.next.aap.task.voiceofaap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PlannedTweetDto;
import com.next.aap.web.dto.TwitterAccountDto;

public class TwitterUserTimeLinePostTaskAsync extends BaseSocialTask implements Callable<Boolean> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AapService aapService;

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private String twitterConsumerKey;

    private String twitterConsumerSecret;

    List<TwitterAccountDto> allTwitterAccounts;

    private PlannedTweetDto plannedTweetDto;

    public TwitterUserTimeLinePostTaskAsync(AapService aapService, ThreadPoolTaskExecutor threadPoolTaskExecutor, String twitterConsumerKey, String twitterConsumerSecret,
            List<TwitterAccountDto> allTwitterAccounts, PlannedTweetDto plannedTweetDto) {
        super();
        this.aapService = aapService;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.twitterConsumerKey = twitterConsumerKey;
        this.twitterConsumerSecret = twitterConsumerSecret;
        this.allTwitterAccounts = allTwitterAccounts;
        this.plannedTweetDto = plannedTweetDto;
    }

    @Override
    public Boolean call() {
        logger.info("Total Acounts to retweet : " + allTwitterAccounts.size() + ", tweetId = " + plannedTweetDto.getTweetId());
        Map<Future<Boolean>, TwitterAccountDto> twitterAccountsFutureMap = new HashMap<Future<Boolean>, TwitterAccountDto>();
        int totalSuccessTweet = 0;
        int totalFailedTweet = 0;
        Future<Boolean> futureResult;

        try {
            if (allTwitterAccounts != null && !allTwitterAccounts.isEmpty()) {
                int totalTweetsRequired = allTwitterAccounts.size();
                if (plannedTweetDto.getTotalRequired() != null && plannedTweetDto.getTotalRequired() > 0) {
                    totalTweetsRequired = plannedTweetDto.getTotalRequired();
                }
                if (totalTweetsRequired > allTwitterAccounts.size()) {
                    totalTweetsRequired = allTwitterAccounts.size();
                }
                CountDownLatch countDownLatch = new CountDownLatch(totalTweetsRequired);
                int count = 0;
                int totalAccounts = allTwitterAccounts.size();
                for (TwitterAccountDto oneTwitterAccount : allTwitterAccounts) {
                    PostOnUserTwitterTimeLineTask postOnUserTimeLineTask = new PostOnUserTwitterTimeLineTask(aapService, oneTwitterAccount, plannedTweetDto, countDownLatch, twitterConsumerKey,
                            twitterConsumerSecret);
                    futureResult = threadPoolTaskExecutor.submit(postOnUserTimeLineTask);
                    twitterAccountsFutureMap.put(futureResult, oneTwitterAccount);
                    count++;
                    logger.info(count + "of " + totalAccounts + " done");
                    if (futureResult.get()) {
                        sleep(5);
                    }
                    if (count > totalTweetsRequired) {
                        break;
                    }
                }
                // wait for all task to finish before proceeding
                logger.info("waiting for countdown latch to go to Zero");
                countDownLatch.await();
                logger.info("waiting for countdown latch finished");
                for (Entry<Future<Boolean>, TwitterAccountDto> oneEntry : twitterAccountsFutureMap.entrySet()) {
                    if (oneEntry.getKey().get()) {
                        totalSuccessTweet++;
                    } else {
                        totalFailedTweet++;
                    }
                }
                logger.info("Total Success Tweets" + totalSuccessTweet);
                logger.info("Total Failed Tweets " + totalFailedTweet);
                aapService.updatePlannedTweetStatus(plannedTweetDto.getId(), PlannedPostStatus.DONE, null, totalSuccessTweet, totalFailedTweet);
            } else {
                logger.info("No twitter accounts found for LocationType=" + plannedTweetDto.getLocationType() + " and location Id=" + plannedTweetDto.getLocationId());
                aapService.updatePlannedTweetStatus(plannedTweetDto.getId(), PlannedPostStatus.DONE_WITH_ERROR, "No twitter accounts found for LocationType=" + plannedTweetDto.getLocationType()
                        + " and location Id=" + plannedTweetDto.getLocationId(), totalSuccessTweet, totalFailedTweet);
            }
            return true;
        } catch (Exception ex) {
            aapService.updatePlannedTweetStatus(plannedTweetDto.getId(), PlannedPostStatus.DONE_WITH_ERROR, ex.getMessage(), totalSuccessTweet, totalFailedTweet);
            return false;
        }
    }
}
