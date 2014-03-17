package com.next.aap.task.donation;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import com.next.aap.core.exception.AppException;
import com.next.aap.core.service.AapService;
import com.next.aap.core.service.HttpUtil;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;

@Component
public class FacebookDataSyncTask implements Runnable{

	private final String baseUrl = "http://remote.aamaadmiparty.org:8080/swaraj/rest/stats/";
	private final String newBaseUrl = "http://remote.aamaadmiparty.org:8080/swaraj/rest/stats/";
	
	private final String dayDonationUrl = baseUrl+"date";
	private final String monthDonationUrl = newBaseUrl+"month";
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private HttpUtil httpUtil;
	@Autowired
	private AapService aapService;

	
	public static void main(String[] args) throws Exception{
		FacebookDataSyncTask facebookDataSyncTask = new FacebookDataSyncTask();
		Facebook facebook = new FacebookTemplate("");
		facebookDataSyncTask.downloadFriendsAndSave(1l, facebook);
		facebookDataSyncTask.downloadGroupsAndSave(1l, facebook);
	}
	//http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger
	//http://freshgroundjava.blogspot.in/2012/07/spring-scheduled-tasks-cron-expression.html
	//ss mm hh dd
	//@Scheduled(cron="0 45 02 * * *")
	public void downlaodFacebookDataForUsers(){
		logger.info("Starting downlaodFacebookDataForUsers");
		long startId = 0l;
		int pageSize = 100;
		try {
			logger.info("Calling aapService.getFacebookAccounts "+startId +" , "+ pageSize);
			List<FacebookAccountDto> facebookAccounts = aapService.getFacebookAccounts(startId, pageSize);
			logger.info("Got "+facebookAccounts.size() +" accounts");
			while(facebookAccounts != null && !facebookAccounts.isEmpty()){
				for(FacebookAccountDto oneFacebookAccount : facebookAccounts){
					logger.info("Downloading Facebook data for User "+ oneFacebookAccount.getId() + ", "+oneFacebookAccount.getUserName());
					downloadFacebookUserData(oneFacebookAccount);
					startId = oneFacebookAccount.getId();
				}
				logger.info("Calling aapService.getFacebookAccounts "+startId +" , "+ pageSize);
				facebookAccounts = aapService.getFacebookAccounts(startId, pageSize);
			}
		} catch (Exception e) {
			logger.error("Unable to sync Data", e);
		}
		
	}
	private void downloadFacebookUserData(FacebookAccountDto facebookAccount){
		try{
			List<FacebookAppPermissionDto> facebookAppPermissions = aapService.getAllFacebookAppPermissions(facebookAccount.getId());
			if(facebookAppPermissions == null || facebookAppPermissions.isEmpty()){
				logger.info("No App Permission found for "+facebookAccount.getId() +", "+facebookAccount.getUserName());
				return;
			}
			for(FacebookAppPermissionDto oneFacebookAppPermission : facebookAppPermissions){
				Facebook facebook = new FacebookTemplate(oneFacebookAppPermission.getToken());
				downloadFriendsAndSave(facebookAccount.getId(), facebook);
				downloadGroupsAndSave(facebookAccount.getId(), facebook);
				break;
			}
			
			
		}catch(Exception ex){
			logger.info("Unable to download Facebook account data "+facebookAccount.getId() +", "+facebookAccount.getUserName());
		}
	}
	private void downloadFriendsAndSave(Long facebookAccountId, Facebook facebook) throws AppException{
		int offset = 0;
		int pageSize = 100;
		int totalFriends = 0;
		logger.info("calling Service again with offset " + offset);
		List<FacebookProfile> friendProfiles = facebook.friendOperations().getFriendProfiles(offset, pageSize);
		while(friendProfiles != null && !friendProfiles.isEmpty()){
			totalFriends = totalFriends + friendProfiles.size();
			aapService.saveFacebookUserFriends(facebookAccountId, friendProfiles, totalFriends);
			offset = offset + pageSize;
			logger.info("calling Service again with offset " + offset);
			friendProfiles = facebook.friendOperations().getFriendProfiles(offset, pageSize);
		}
	}
	
	private void downloadGroupsAndSave(Long facebookAccountId,Facebook facebook){
		logger.info("Downloading Groups");
		List<GroupMembership> userGroupMembership = facebook.groupOperations().getMemberships();
		aapService.saveFacebookAccountGroups(facebookAccountId, userGroupMembership);
		/*
		List<GroupMemberReference> groupMemberReferences;
		for(GroupMembership oneGroupMembership:userGroupMembership){
			System.out.println(oneGroupMembership.getId() + " , "+oneGroupMembership.getName() );
			groupMemberReferences = facebook.groupOperations().getMembers(oneGroupMembership.getId());
			System.out.println("    Total Members : "+ groupMemberReferences.size() );
			
		}
		*/
	}
	@Override
	public void run() {
		downlaodFacebookDataForUsers();
	}
	
}
