package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.PlannedFacebookPost;
import com.next.aap.web.dto.PostLocationType;


public interface PlannedFacebookPostDao {

	public abstract PlannedFacebookPost savePlannedFacebookPost(PlannedFacebookPost plannedFacebookPost);

	public abstract PlannedFacebookPost getPlannedFacebookPostById(Long id);

	public abstract PlannedFacebookPost getPlannedFacebookPostByAppId(String appId);
	
	public abstract List<PlannedFacebookPost> getPlannedFacebookPostByLocationTypeAndLocationId(PostLocationType postLocationType, Long locationId);
	
	public abstract List<PlannedFacebookPost> getExecutedFacebookPostByLocationTypeAndLocationId(PostLocationType postLocationType, Long locationId);
	
	public abstract PlannedFacebookPost getNextPlannedFacebookPostToPublish();

}