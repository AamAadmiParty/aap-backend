package com.next.aap.core.persistance.dao;

import com.next.aap.core.persistance.FacebookPost;

public interface FacebookPostDao {

	public abstract FacebookPost saveFacebookPost(FacebookPost facebookPost);

	public abstract FacebookPost getFacebookPostById(Long id);

	public abstract FacebookPost getFacebookPostByPlannedPostIdAndFacebookAccountId(Long plannedFacebookPostId, Long facebookAccountId);

}