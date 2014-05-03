package com.next.aap.task.voiceofaap;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.FacebookPostDto;
import com.next.aap.web.dto.PlannedFacebookPostDto;

public class PostOnUserFacebookTimeLineTask implements Callable<Boolean> {

	private AapService aapService;
	private FacebookAccountDto facebookAccountDto;
	private PlannedFacebookPostDto plannedFacebookPostDto;
	private CountDownLatch countDownLatch;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public PostOnUserFacebookTimeLineTask(AapService aapService, FacebookAccountDto facebookAccountDto, PlannedFacebookPostDto plannedFacebookPostDto, CountDownLatch countDownLatch){
		this.aapService = aapService;
		this.facebookAccountDto = facebookAccountDto;
		this.plannedFacebookPostDto = plannedFacebookPostDto;
		this.countDownLatch = countDownLatch;
	}
	public static void main(String args[]) throws MalformedURLException{
		Facebook facebook = new FacebookTemplate("CAAHXYzhpziIBAOkP5FTERnOQRXGV3GmGmzVxSZAfgkBj7NGuP6G0pEZCLug5ZBQLyUiRP7Lyuz3YPpdcyCFDljdv8G3DQHUIx2V4U2eOoJkdw3BrFdSXly8aZAqMChwP6T13okTAkbQEx9W2OZCxScSZBbTLEPtMdjGmkGPF95WQijjpscII5w");
		PlannedFacebookPostDto plannedFacebookPostDto = new PlannedFacebookPostDto();
		plannedFacebookPostDto.setPostType(PlannedFacebookPostDto.LINK_TYPE);
		plannedFacebookPostDto.setMessage("This is test message to test Voice of AAP");
		plannedFacebookPostDto.setLink("Http://my.aamaadmiparty.org");
		plannedFacebookPostDto.setName("Ravi Sharma");
		plannedFacebookPostDto.setCaption("My Love");
		plannedFacebookPostDto.setDescription("I am testing Something");
		plannedFacebookPostDto.setPicture("https://lh4.googleusercontent.com/-A_b0YHzjJiw/U13jIQ_ta9I/AAAAAAAAQcA/Lx92KoXtscU/s912/27-Apr-2014.png");
		String facebookPostExternalId = null;
		if(plannedFacebookPostDto.getPostType().equalsIgnoreCase(PlannedFacebookPostDto.LINK_TYPE)){
			if(StringUtil.isEmpty(plannedFacebookPostDto.getPicture())){
				FacebookLink facebookLink = new FacebookLink(plannedFacebookPostDto.getLink(), plannedFacebookPostDto.getName(), plannedFacebookPostDto.getCaption(), plannedFacebookPostDto.getDescription());
				facebookPostExternalId = facebook.feedOperations().postLink(plannedFacebookPostDto.getMessage(), facebookLink);
			}else{
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.set("link", plannedFacebookPostDto.getLink());
				map.set("name", plannedFacebookPostDto.getName());
				map.set("caption", plannedFacebookPostDto.getCaption());
				map.set("description", plannedFacebookPostDto.getDescription());
				map.set("message", plannedFacebookPostDto.getMessage());
				map.set("access_token", "CAAHXYzhpziIBAOkP5FTERnOQRXGV3GmGmzVxSZAfgkBj7NGuP6G0pEZCLug5ZBQLyUiRP7Lyuz3YPpdcyCFDljdv8G3DQHUIx2V4U2eOoJkdw3BrFdSXly8aZAqMChwP6T13okTAkbQEx9W2OZCxScSZBbTLEPtMdjGmkGPF95WQijjpscII5w");
				map.add("picture", plannedFacebookPostDto.getPicture());
				facebookPostExternalId = facebook.publish("me", "feed", map);
			}
		}
		if(plannedFacebookPostDto.getPostType().equalsIgnoreCase(PlannedFacebookPostDto.PHOTO_TYPE)){
			Resource photo = new FileSystemResource(plannedFacebookPostDto.getPicture());
			facebookPostExternalId = facebook.mediaOperations().postPhoto(photo, plannedFacebookPostDto.getMessage());
		}
		if(plannedFacebookPostDto.getPostType().equalsIgnoreCase(PlannedFacebookPostDto.TEXT_TYPE)){
			facebookPostExternalId = facebook.feedOperations().updateStatus(plannedFacebookPostDto.getMessage());
		}
		System.out.println("Facebook Post created " + facebookPostExternalId);
	}
	@Override
	public Boolean call() throws Exception {
		try{
			//First check if we have already posted planned FacebookPost from this facebook account
			FacebookPostDto facebookPostDto = aapService.getFacebookPostByPlannedPostIdAndFacebookAccountId(plannedFacebookPostDto.getId(), facebookAccountDto.getId());
			if(facebookPostDto != null){
				logger.warn("PlannedFacebookPost ["+plannedFacebookPostDto.getId()+"] already posted by facebook account ["+facebookAccountDto.getId()+"]");
				return false;
			}
			//Get access token from Data base for Voice of aap app
			 FacebookAppPermissionDto facebookAppPermissionDto = aapService.getVoiceOfAapFacebookPermission(facebookAccountDto.getId());
			 if(facebookAppPermissionDto == null){
				 logger.warn("Facebook Account[" +facebookAccountDto.getId() + "] have not given pemrission to voice of aap");
				 return false;
			 }
			 
			 if(facebookAppPermissionDto.getExpireTime().before(new Date())){
				 logger.warn("Facebook Account[" +facebookAccountDto.getId() + "] token has expired on "+ facebookAppPermissionDto.getExpireTime());
				 return false;
			 }
			//Then post on facebook
			Facebook facebook = new FacebookTemplate(facebookAppPermissionDto.getToken());
			String facebookPostExternalId = null;
			if(plannedFacebookPostDto.getPostType().equalsIgnoreCase(PlannedFacebookPostDto.LINK_TYPE)){
				if(StringUtil.isEmpty(plannedFacebookPostDto.getPicture())){
					FacebookLink facebookLink = new FacebookLink(plannedFacebookPostDto.getLink(), plannedFacebookPostDto.getName(), plannedFacebookPostDto.getCaption(), plannedFacebookPostDto.getDescription());
					facebookPostExternalId = facebook.feedOperations().postLink(plannedFacebookPostDto.getMessage(), facebookLink);
				}else{
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
					map.set("link", plannedFacebookPostDto.getLink());
					map.set("name", plannedFacebookPostDto.getName());
					map.set("caption", plannedFacebookPostDto.getCaption());
					map.set("description", plannedFacebookPostDto.getDescription());
					map.set("message", plannedFacebookPostDto.getMessage());
					map.set("access_token", facebookAppPermissionDto.getToken());
					map.add("picture", plannedFacebookPostDto.getPicture());
					facebookPostExternalId = facebook.publish("me", "feed", map);
				}
			}
			if(plannedFacebookPostDto.getPostType().equalsIgnoreCase(PlannedFacebookPostDto.PHOTO_TYPE)){
				Resource photo = new UrlResource(plannedFacebookPostDto.getPicture());
				facebookPostExternalId = facebook.mediaOperations().postPhoto(photo, plannedFacebookPostDto.getMessage());
			}
			if(plannedFacebookPostDto.getPostType().equalsIgnoreCase(PlannedFacebookPostDto.TEXT_TYPE)){
				facebookPostExternalId = facebook.feedOperations().updateStatus(plannedFacebookPostDto.getMessage());
			}
			//Now update data base that post has been posted
			facebookPostDto = new FacebookPostDto();
			facebookPostDto.setFacebookAccountId(facebookAccountDto.getId());
			facebookPostDto.setFacebookPostExternalId(facebookPostExternalId);
			facebookPostDto.setPlannedFacebookPostId(plannedFacebookPostDto.getId());
			facebookPostDto.setPostStatus("Success");
			facebookPostDto = aapService.saveFacebookPost(facebookPostDto);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			//Now update data base that post has been posted
			FacebookPostDto facebookPostDto = new FacebookPostDto();
			facebookPostDto.setFacebookAccountId(facebookAccountDto.getId());
			facebookPostDto.setFacebookPostExternalId("");
			facebookPostDto.setPlannedFacebookPostId(plannedFacebookPostDto.getId());
			facebookPostDto.setPostStatus("Failed");
			facebookPostDto.setErrorMessage(ex.getMessage());
			facebookPostDto = aapService.saveFacebookPost(facebookPostDto);
		}finally{
			countDownLatch.countDown();
		}
		return false;
	}

}
