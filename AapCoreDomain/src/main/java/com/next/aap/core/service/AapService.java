package com.next.aap.core.service;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;

import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;

public interface AapService {

	UserDto saveFacebookUser(Long existingUserid, Connection<Facebook> connection);
	
	UserDto saveGoogleUser(Long existingUserid, Connection<Google> connection);
	
	UserDto saveTwitterUser(Long existingUserid, Connection<Twitter> connection);
	
	LoginAccountDto getUserLoginAccounts(Long userId);
}
