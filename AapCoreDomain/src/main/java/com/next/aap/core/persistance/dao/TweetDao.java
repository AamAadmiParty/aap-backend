package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Tweet;

public interface TweetDao {

	public abstract Tweet saveTweet(Tweet tweet);

	public abstract Tweet getTweetById(Long id);

	public abstract Tweet getTweetByPlannedPostIdAndTwitterAccountId(Long plannedTweetId, Long twitterAccountId);
	
	public abstract List<Tweet> getTweetByUserId(Long userId);

}