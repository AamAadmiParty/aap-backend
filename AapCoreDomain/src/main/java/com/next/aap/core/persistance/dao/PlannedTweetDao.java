package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.PlannedTweet;
import com.next.aap.web.dto.PostLocationType;


public interface PlannedTweetDao {

	public abstract PlannedTweet savePlannedTweet(PlannedTweet plannedTweet);

	public abstract PlannedTweet getPlannedTweetById(Long id);

	public abstract PlannedTweet getPlannedTweetByAppId(String appId);
	
	public abstract List<PlannedTweet> getPlannedTweetByLocationTypeAndLocationId(PostLocationType postLocationType, Long locationId);
	
	public abstract PlannedTweet getNextPlannedTweetToPublish();

}