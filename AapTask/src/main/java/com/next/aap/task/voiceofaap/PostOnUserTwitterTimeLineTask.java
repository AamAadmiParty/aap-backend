package com.next.aap.task.voiceofaap;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.PlannedTweetDto;
import com.next.aap.web.dto.TweetDto;
import com.next.aap.web.dto.TwitterAccountDto;

public class PostOnUserTwitterTimeLineTask implements Callable<Boolean> {

	private AapService aapService;
	private TwitterAccountDto twitterAccountDto;
	private PlannedTweetDto plannedTweetDto;
	private CountDownLatch countDownLatch;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String twitterConsumerKey;
	private String twitterConsumerSecret;
	
	public PostOnUserTwitterTimeLineTask(AapService aapService, TwitterAccountDto twitterAccountDto, PlannedTweetDto plannedTweetDto,
			CountDownLatch countDownLatch, String twitterConsumerKey, String twitterConsumerSecret) {
		this.aapService = aapService;
		this.twitterAccountDto = twitterAccountDto;
		this.plannedTweetDto = plannedTweetDto;
		this.countDownLatch = countDownLatch;
		this.twitterConsumerKey = twitterConsumerKey;
		this.twitterConsumerSecret = twitterConsumerSecret;
	}

	@Override
	public Boolean call() throws Exception {
		try {
			// First check if we have already posted planned FacebookPost from
			// this facebook account
			TweetDto tweetDtoDto = aapService.getTweetByPlannedTweetIdAndTwitterAccountId(plannedTweetDto.getId(), twitterAccountDto.getId());
			if (tweetDtoDto != null) {
				logger.warn("PlannedTweet [" + plannedTweetDto.getId() + "] already posted by twitter account [" + twitterAccountDto.getId() + "]");
				return false;
			}
			// Then post on facebook
			Twitter twitter = new TwitterTemplate(twitterConsumerKey, twitterConsumerSecret, twitterAccountDto.getToken(), twitterAccountDto.getTokenSecret());
			Long tweetExternalId = null;
			if (plannedTweetDto.getTweetType().equalsIgnoreCase(PlannedTweetDto.RETWEET_TYPE)) {
				logger.info("Retweeting from account "+ twitterAccountDto.getScreenName());
				twitter.timelineOperations().retweet(plannedTweetDto.getTweetId());
				tweetExternalId = plannedTweetDto.getTweetId();
			}
			org.springframework.social.twitter.api.Tweet tweet = null;;
			if (plannedTweetDto.getTweetType().equalsIgnoreCase(PlannedTweetDto.TWEET_TYPE)) {
				if(StringUtil.isEmpty(plannedTweetDto.getPicture())){
					logger.info("Tweeting from account "+ twitterAccountDto.getScreenName());
					tweet = twitter.timelineOperations().updateStatus(plannedTweetDto.getMessage());
				}else{
					logger.info("Tweeting from account "+ twitterAccountDto.getScreenName()+" with picture");
					Resource imageRespource = new UrlResource(plannedTweetDto.getPicture());
					tweet = twitter.timelineOperations().updateStatus(plannedTweetDto.getMessage(), imageRespource);
				}
				tweetExternalId = tweet.getId();
			}
			// Now update data base that post has been posted
			TweetDto tweetDto = new TweetDto();
			tweetDto.setPlannedTweetId(plannedTweetDto.getId());
			tweetDto.setImageUrl(plannedTweetDto.getPicture());
			tweetDto.setTweetContent(plannedTweetDto.getMessage());
			tweetDto.setTwitterAccountId(twitterAccountDto.getId());
			tweetDto.setTweetExternalId(tweetExternalId);
			tweetDto = aapService.saveTweetPost(tweetDto);
			return true;

		} catch (Exception ex) {
			logger.error("Unable to twwet from account "+twitterAccountDto.getScreenName(), ex);
		} finally {
			countDownLatch.countDown();
		}
		return false;
	}

}
