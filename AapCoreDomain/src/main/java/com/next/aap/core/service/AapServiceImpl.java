package com.next.aap.core.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.persistance.Email;
import com.next.aap.core.persistance.Email.ConfirmationType;
import com.next.aap.core.persistance.FacebookAccount;
import com.next.aap.core.persistance.User;
import com.next.aap.core.persistance.dao.EmailDao;
import com.next.aap.core.persistance.dao.FacebookAccountDao;
import com.next.aap.core.persistance.dao.TwitterAccountDao;
import com.next.aap.core.persistance.dao.UserDao;


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
	public void saveFacebookUser(Long existingUserId,Connection<Facebook> connection) {
		User user = null;
		//See if user exists
		if(existingUserId != null && existingUserId > 0){
			user = userDao.getUserById(existingUserId);
		}
		
		FacebookAccount dbFacebookAccount = null;
		ConnectionData fbConnectionData = connection.createData();
		dbFacebookAccount = facebookAccountDao.getFacebookAccountByFacebookUserId(fbConnectionData.getProviderUserId());
		
		String facebookAccountEmail = connection.getApi().userOperations().getUserProfile().getEmail();

		if(dbFacebookAccount == null){
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
		
		//First create/update user
		if(user == null){
			user = new User();
			if(fbConnectionData.getDisplayName() == null){
				user.setName(facebookAccountEmail);
			}else{
				user.setName(fbConnectionData.getDisplayName());
			}
			
			user.setDateCreated(new Date());
			user.setExternalId(UUID.randomUUID().toString());
		}
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
		

	}
	
	private void mergeUser(User targetUser, User sourceUser){
		
	}

	@Override
	@Transactional
	public void saveTwitterUser(Long existingUserid,
			UserProfile facebookUserProfile) {
		// TODO Auto-generated method stub

	}

}
