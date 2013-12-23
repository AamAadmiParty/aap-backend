package com.next.aap.core.persistance.dao;

import com.next.aap.core.persistance.FacebookAppPermission;

public interface FacebookAppPermissionDao {

	public abstract FacebookAppPermission saveFacebookAppPermission(FacebookAppPermission facebookApp);

	public abstract FacebookAppPermission getFacebookAppPermissionById(Long id);

	public abstract FacebookAppPermission getFacebookAppPermissionByAppIdAndFacebookAccountId(Long appId, Long facebookAccountId);
	
	public abstract FacebookAppPermission getFacebookAppPermissionByFacebookAppIdAndFacebookAccountId(String facebookAppId, Long facebookAccountId);

}