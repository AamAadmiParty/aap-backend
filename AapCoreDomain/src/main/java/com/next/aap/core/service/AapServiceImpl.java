package com.next.aap.core.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.persistance.Email;
import com.next.aap.core.persistance.Email.ConfirmationType;
import com.next.aap.core.persistance.FacebookAccount;
import com.next.aap.core.persistance.TwitterAccount;
import com.next.aap.core.persistance.User;
import com.next.aap.core.persistance.dao.EmailDao;
import com.next.aap.core.persistance.dao.FacebookAccountDao;
import com.next.aap.core.persistance.dao.TwitterAccountDao;
import com.next.aap.core.persistance.dao.UserDao;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.TwitterAccountDto;
import com.next.aap.web.dto.UserDto;


@Service("aapService")
public class AapServiceImpl implements AapService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private FacebookAccountDao facebookAccountDao;
	@Autowired
	private TwitterAccountDao twitterAccountDao;
	@Autowired
	private EmailDao emailDao;
	
	@Override
	@Transactional
	public UserDto saveFacebookUser(Long existingUserId,Connection<Facebook> connection) {
		User user = null;
		//See if user exists
		if(existingUserId != null && existingUserId > 0){
			System.out.println("existingUserId="+existingUserId);
			user = userDao.getUserById(existingUserId);
		}
		
		FacebookAccount dbFacebookAccount = null;
		ConnectionData fbConnectionData = connection.createData();
		dbFacebookAccount = facebookAccountDao.getFacebookAccountByFacebookUserId(fbConnectionData.getProviderUserId());
		System.out.println("dbFacebookAccount="+dbFacebookAccount);
		String facebookAccountEmail = connection.getApi().userOperations().getUserProfile().getEmail();

		if(dbFacebookAccount == null){
			System.out.println("creating new dbFacebookAccount="+dbFacebookAccount);
			//Account do not exists so create one now
			dbFacebookAccount = new FacebookAccount();
			dbFacebookAccount.setDateCreated(new Date());
			dbFacebookAccount.setFacebookUserId(fbConnectionData.getProviderUserId());
			if(fbConnectionData.getDisplayName() == null){
				dbFacebookAccount.setUserName(facebookAccountEmail);
			}else{
				dbFacebookAccount.setUserName(connection.getDisplayName());
			}
			
		}else{
			user = dbFacebookAccount.getUser();
		}
		System.out.println("user="+user);
		//First create/update user
		if(user == null){
			System.out.println("creating new user="+user);
			user = new User();
			if(fbConnectionData.getDisplayName() == null){
				user.setName(facebookAccountEmail);
			}else{
				user.setName(fbConnectionData.getDisplayName());
			}
			
			user.setDateCreated(new Date());
			user.setExternalId(UUID.randomUUID().toString());
		}
		System.out.println("user="+user);
		user = userDao.saveUser(user);
		
		//check if this email already exists in our system
		if(!StringUtil.isEmpty(facebookAccountEmail)){
			Email email = emailDao.getEmailByEmail(facebookAccountEmail);
			if(email == null){
				//create a new Email in the system
				email = new Email();
				email.setEmail(facebookAccountEmail);
				email.setConfirmationType(ConfirmationType.CONFIRMED_FACEBOOK_ACCOUNT);
				email.setConfirmed(true);
				email.setUser(user);
				email.setDateCreated(new Date());
			}else{
				email.setDateModified(new Date());
				if(email.isConfirmed()){
					//if existing email was confirmed
					//check if User are same
					if(email.getUser().equals(user)){
						//if same no need to merge ot detach user
					}else{
						//merge two users data
						mergeUser(user, email.getUser());
						email.setUser(user);
					}
				}else{
					if(email.getUser() != null){
						//detach this email from this user.. Need to write more code here to 
						//make sure we delete the detached user if that user do not have anything like email or mobile number etc
						email.setUser(user);
					}
				}
				
			}
			email = emailDao.saveEmail(email);
		}
		
		
		
		dbFacebookAccount.setDateModified(new Date());
		dbFacebookAccount.setToken(fbConnectionData.getAccessToken());
		dbFacebookAccount.setExpireTime(new Date(fbConnectionData.getExpireTime()));
		dbFacebookAccount.setImageUrl(fbConnectionData.getImageUrl());
		dbFacebookAccount.setUser(user);
		dbFacebookAccount = facebookAccountDao.saveFacebookAccount(dbFacebookAccount);
		
		UserDto returnUser = new UserDto();
		BeanUtils.copyProperties(user, returnUser);
		returnUser.setExternalId(user.getExternalId());
		return returnUser;
	}
	
	private void mergeUser(User targetUser, User sourceUser){
		
	}

	@Override
	@Transactional
	public UserDto saveTwitterUser(Long existingUserId,
			Connection<Twitter> connection) {
		User user = null;
		//See if user exists
		if(existingUserId != null && existingUserId > 0){
			System.out.println("existingUserId="+existingUserId);
			user = userDao.getUserById(existingUserId);
		}
		
		TwitterAccount dbTwitterAccount = null;
		ConnectionData twitterConnectionData = connection.createData();
		dbTwitterAccount = twitterAccountDao.getTwitterAccountByTwitterUserId(twitterConnectionData.getProviderUserId());
		System.out.println("dbTwitterAccount="+dbTwitterAccount);

		if(dbTwitterAccount == null){
			System.out.println("creating new dbTwitterAccount="+dbTwitterAccount);
			//Account do not exists so create one now
			dbTwitterAccount = new TwitterAccount();
			dbTwitterAccount.setDateCreated(new Date());
			dbTwitterAccount.setTwitterId(twitterConnectionData.getProviderUserId());
			dbTwitterAccount.setScreenName(twitterConnectionData.getDisplayName());
			
		}else{
			user = dbTwitterAccount.getUser();
		}
		System.out.println("user="+user);
		//First create/update user
		if(user == null){
			System.out.println("creating new user="+user);
			user = new User();
			user.setName(twitterConnectionData.getDisplayName());
			user.setDateCreated(new Date());
			user.setExternalId(UUID.randomUUID().toString());
		}
		user = userDao.saveUser(user);
		
		
		dbTwitterAccount.setDateModified(new Date());
		dbTwitterAccount.setToken(twitterConnectionData.getAccessToken());
		dbTwitterAccount.setTokenSecret(twitterConnectionData.getSecret());
		dbTwitterAccount.setImageUrl(twitterConnectionData.getImageUrl());
		dbTwitterAccount.setUser(user);
		dbTwitterAccount = twitterAccountDao.saveTwitterAccount(dbTwitterAccount);
		
		UserDto returnUser = new UserDto();
		BeanUtils.copyProperties(user, returnUser);
		System.out.println("user.getId()="+user.getId());
		return returnUser;
	}

	@Override
	public UserDto saveGoogleUser(Long existingUserid,
			Connection<Google> connection) {
		return null;
	}

	@Override
	@Transactional
	public LoginAccountDto getUserLoginAccounts(Long userId) {
		User user = userDao.getUserById(userId);
		LoginAccountDto loginAccountDto = new LoginAccountDto();
		loginAccountDto.setTwitterAccounts(convertTwitterAccounts(user.getTwitterAccounts()));
		loginAccountDto.setFacebookAccounts(convertFacebookAccounts(user.getFacebookAccounts()));
		return loginAccountDto;
	}
	private List<TwitterAccountDto> convertTwitterAccounts(Collection<TwitterAccount> twitterAccounts){
		List<TwitterAccountDto> returnTwitterAccounts = new ArrayList<>();
		if(twitterAccounts != null){
			for(TwitterAccount oneTwitterAccount:twitterAccounts){
				returnTwitterAccounts.add(convertTwitterAccount(oneTwitterAccount));
			}
		}
		return returnTwitterAccounts;
	}
	private TwitterAccountDto convertTwitterAccount(TwitterAccount twitterAccount){
		if(twitterAccount == null){
			return null;
		}
		TwitterAccountDto returnTwitterAccountDto = new TwitterAccountDto();
		BeanUtils.copyProperties(twitterAccount, returnTwitterAccountDto);
		return returnTwitterAccountDto;
	}
	
	private List<FacebookAccountDto> convertFacebookAccounts(Collection<FacebookAccount> facebookAccounts){
		List<FacebookAccountDto> returnTwitterAccounts = new ArrayList<>();
		if(facebookAccounts != null){
			for(FacebookAccount oneFacebookAccount:facebookAccounts){
				returnTwitterAccounts.add(convertFacebookAccount(oneFacebookAccount));
			}
		}
		return returnTwitterAccounts;
	}
	private FacebookAccountDto convertFacebookAccount(FacebookAccount facebookAccount){
		if(facebookAccount == null){
			return null;
		}
		FacebookAccountDto returnFacebookAccountDto = new FacebookAccountDto();
		BeanUtils.copyProperties(facebookAccount, returnFacebookAccountDto);
		return returnFacebookAccountDto;
	}

}
