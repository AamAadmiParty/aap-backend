package com.next.aap.core.persistance.dao;

import com.next.aap.core.persistance.FacebookApp;


public interface FacebookAppDao {

	public abstract FacebookApp saveFacebookApp(FacebookApp facebookApp);

	public abstract FacebookApp getFacebookAppById(Long id);

	public abstract FacebookApp getFacebookAppByAppId(String appId);

}