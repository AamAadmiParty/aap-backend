package com.next.aap.task.voiceofaap;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PlannedTweetDto;
import com.next.aap.web.dto.PostLocationType;

@Component
public class TweetListenerTask extends BaseSocialTask{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapService aapService;
	

    String consumerKey = "qXC4eGQBckhlcNIUBF6AsXitQ";
    String consumerSecret = "YepMYNDEOiDwzDYJpO222lC35VIvfolAGCNM7fDu3brQkhoIa4";
    String accessToken = "287659262-tsNgC0DnwyB85hrjPXcwcFT89B4xWX5T0D7iMnK7";
    String accessTokenSecret = "z6QKDOwaoKpFA7IoQ32mqvWrprFHSBBl41GhUgr2UVvLA";

    public static void main(String[] args) {

    }

    // @Scheduled(cron="0 45 00 * * *")
    @Scheduled(fixedDelay = 60000)
    public void runTask() {
        Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        List<Tweet> tweets = twitter.timelineOperations().getUserTimeline(1415538206, 5);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -5);
        Calendar postingTime = Calendar.getInstance();
        postingTime.add(Calendar.MINUTE, 5);
        for (Tweet oneTweet : tweets) {
            logger.info("Tweet : " + oneTweet.getCreatedAt() + " : " + oneTweet.getText());
            if (oneTweet.getCreatedAt().after(now.getTime())) {
                PlannedTweetDto plannedTweetDto;
                try {
                    plannedTweetDto = aapService.getPlannedTweetByTweetId(oneTweet.getId());
                    logger.info("plannedTweetDto : " + plannedTweetDto);
                    if (plannedTweetDto != null) {
                        logger.info("Ignoring as already have planned Tweet");
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Create New Planned tweet only if it happens in last 5 minutes

                logger.info("Creating a planned Tweet");
                plannedTweetDto = new PlannedTweetDto();
                plannedTweetDto.setLocationType(PostLocationType.Global);
                plannedTweetDto.setStatus(PlannedPostStatus.PENDING);
                plannedTweetDto.setTweetType("Retweet");
                plannedTweetDto.setTotalRequired(50);
                plannedTweetDto.setTweetId(oneTweet.getId());
                plannedTweetDto.setPostingTime(postingTime.getTime());
                plannedTweetDto = aapService.savePlannedTweet(plannedTweetDto);
                logger.info("Creating a planned Tweet Success");
            } else {
                logger.info("Ignoring as not in last 5 minutes");
            }

        }
	}

}
