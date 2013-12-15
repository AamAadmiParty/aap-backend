package com.next.aap.core.service;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;

public interface AapService {

	void saveFacebookUser(Long existingUserid, Connection<Facebook> connection);
	
	void saveTwitterUser(Long existingUserid, UserProfile facebookUserProfile);
}
