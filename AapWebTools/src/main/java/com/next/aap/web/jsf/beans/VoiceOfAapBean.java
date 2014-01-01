package com.next.aap.web.jsf.beans;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.social.RevokedAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.VoiceOfAapData;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

/*
@Component
//@Scope("session")
@SessionScoped
@URLMappings(mappings = { @URLMapping(id = "voiceOfAapBean", pattern = "/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml") })
@URLBeanName("voiceOfAapBean")
*/
@Component
@Scope("session")
@URLMapping(id = "voiceOfAapBean", beanName="voiceOfAapBean", pattern = "/voiceofaap", viewId = "/WEB-INF/jsf/voiceofaap.xhtml")
@URLBeanName("voiceOfAapBean")
public class VoiceOfAapBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private UserDto loggedInUser;
	
	
	@Autowired
	private AapService aapService;
	
	private boolean beVoiceOfAap;
	private boolean postOnGroup;
	private boolean postOnTimeLine;
	private boolean tweetFromMyAccount;
	
	FacebookAppPermissionDto facebookAppPermission;
	FacebookAccountDto oneFacebookAccountDto;
	
	Map<String, String> userGroups = new TreeMap<>();
    private List<String> selectedGroups;  
    
	//@URLActions(actions = { @URLAction(mappingId = "voiceOfAapBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		loggedInUser = getLoggedInUser(true,buildLoginUrl("/voiceofaap"));
		if(loggedInUser == null){
			return;
		}
		if(loggedInUser != null){
			LoginAccountDto loginAccounts = getLoggedInAccountsFromSesion();
			if(loginAccounts == null || loginAccounts.getFacebookAccounts() == null || loginAccounts.getFacebookAccounts().isEmpty()){
				//buildAndRedirect to facebook Login Account
				logger.info("User is not logegd In to facebook, So redirecting to facebook login");
				buildAndRedirect("/login/voa/facebook?group=timeline&"+REDIRECT_URL_PARAM_ID+"="+buildUrl("/voiceofaap"));
				return;
			}
			oneFacebookAccountDto = loginAccounts.getFacebookAccounts().get(0);

			facebookAppPermission = aapService.getVoiceOfAapFacebookPermission(oneFacebookAccountDto.getId());
			if(facebookAppPermission == null){
				//buildAndRedirect to facebook Login Account
				logger.info("User is logegd In to facebook, but have not given permission to voice of aap");
				buildAndRedirect("/login/voa/facebook?group=timeline&"+REDIRECT_URL_PARAM_ID+"="+buildUrl("/voiceofaap"));
				setFinalRedirectUrlInSesion(buildUrl("/voiceofaap"));
				return;
			}

			Calendar currectTime = Calendar.getInstance();
			currectTime.add(Calendar.DATE, 10);
			if(currectTime.after(facebookAppPermission.getExpireTime())){
				//buildAndRedirect to facebook Login Account
				logger.info("User is logegd In to facebook,and given permission to voice of aap but permission about to expire");
				buildAndRedirect("/login/voa/facebook?group=timeline&"+REDIRECT_URL_PARAM_ID+"="+buildUrl("/voiceofaap"));
				return;
			}
			
			Facebook facebook = new FacebookTemplate(facebookAppPermission.getToken());
			try{
				if(!facebook.userOperations().getUserPermissions().contains("publish_actions") || !facebook.userOperations().getUserPermissions().contains("publish_stream")){
					logger.info("User has not given publish permission");
					beVoiceOfAap = false;
				}else{
					beVoiceOfAap = true;
				}
				
				if(!facebook.userOperations().getUserPermissions().contains("publish_actions") 
						|| !facebook.userOperations().getUserPermissions().contains("publish_stream")
						|| !facebook.userOperations().getUserPermissions().contains("user_groups")){
					logger.info("User has not given publish permission");
					postOnGroup = false;
				}else{
					postOnGroup = true;
					//get all facebook groups of user
					loadUserGroups(facebook);
					
				}
			}catch(RevokedAuthorizationException ex){
				logger.info("User has revoked permission");
				beVoiceOfAap = false;
				return;
			}
		}
	}

	public void handlePostOnGroupCheckBoxChange(){
		if(postOnGroup){
			Facebook facebook = new FacebookTemplate(facebookAppPermission.getToken());
			try{
				if(!facebook.userOperations().getUserPermissions().contains("publish_actions") 
						|| !facebook.userOperations().getUserPermissions().contains("publish_stream")
						|| !facebook.userOperations().getUserPermissions().contains("user_groups")){
					logger.info("User has not given publish permission or group permission");
					//buildAndRedirect to facebook Login Account
					buildAndRedirect("/login/voa/facebook?group=groups&"+REDIRECT_URL_PARAM_ID+"="+buildUrl("/voiceofaap"));
					return;
				}
				//get all facebook groups of user
				loadUserGroups(facebook);
			}catch(RevokedAuthorizationException ex){
				logger.info("User has revoked permission");
				//buildAndRedirect to facebook Login Account
				buildAndRedirect("/login/voa/facebook?group=timeline&"+REDIRECT_URL_PARAM_ID+"="+buildUrl("/voiceofaap"));
				return;
			}
		}
	}
	private void loadUserGroups(Facebook facebook){
		if(userGroups.isEmpty()){
			List<GroupMembership> userGroupMembership = facebook.groupOperations().getMemberships();
			userGroups.clear();
			for(GroupMembership oneGroup:userGroupMembership){
				userGroups.put(oneGroup.getName(), oneGroup.getId());
			}
			aapService.saveFacebookAccountGroups(oneFacebookAccountDto.getId(), userGroupMembership);
			VoiceOfAapData voiceOfAapData = aapService.getVoiceOfAapSetting(oneFacebookAccountDto.getId());
			selectedGroups = voiceOfAapData.getSelectedGroups();
			beVoiceOfAap = voiceOfAapData.isBeVoiceOfAap();
			postOnTimeLine = voiceOfAapData.isPostOnTimeLine();
			tweetFromMyAccount = voiceOfAapData.isTweetFromMyAccount();
			postOnGroup = !selectedGroups.isEmpty();
		}
	}
	public void handleVoiceOfAapCheckBoxChange() {
		if(beVoiceOfAap){
			Facebook facebook = new FacebookTemplate(facebookAppPermission.getToken());
			try{
				if(!facebook.userOperations().getUserPermissions().contains("publish_actions") || !facebook.userOperations().getUserPermissions().contains("publish_stream")){
					logger.info("User has not given publish permission");
					//buildAndRedirect to facebook Login Account
					buildAndRedirect("/login/voa/facebook?group=timeline&"+REDIRECT_URL_PARAM_ID+"="+buildUrl("/voiceofaap"));
					return;
				}
			}catch(RevokedAuthorizationException ex){
				logger.info("User has revoked permission");
				//buildAndRedirect to facebook Login Account
				buildAndRedirect("/login/voa/facebook?group=timeline&"+REDIRECT_URL_PARAM_ID+"="+buildUrl("/voiceofaap"));
				return;
			}
		}
		
	}
	public void handlePostOnTimeLineCheckBoxChange(){
	}
	public void handleTweetFromMyAccountCheckBoxChange(){
		if(postOnGroup){
			LoginAccountDto loginAccounts = getLoggedInAccountsFromSesion();
			if(loginAccounts.getTwitterAccounts() == null || loginAccounts.getTwitterAccounts().isEmpty()){
				buildAndRedirect("/login/twitter?"+REDIRECT_URL_PARAM_ID+"="+buildUrl("/voiceofaap"));
				return;
			}
		}
	}
	public void saveVoiceOfAapSeeting(){
		logger.info("Saving Voice of Aap Settings 1 ");
		try{
			aapService.saveVoiceOfAapSettings(oneFacebookAccountDto.getId(), beVoiceOfAap, postOnTimeLine, selectedGroups, null, tweetFromMyAccount);
		}catch(Exception ex){
			sendErrorMessageToJsfScreen("unable to save setting");
		}
	}

	public UserDto getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(UserDto loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public boolean isBeVoiceOfAap() {
		return beVoiceOfAap;
	}

	public void setBeVoiceOfAap(boolean beVoiceOfAap) {
		this.beVoiceOfAap = beVoiceOfAap;
	}

	public boolean isPostOnGroup() {
		return postOnGroup;
	}

	public void setPostOnGroup(boolean postOnGroup) {
		this.postOnGroup = postOnGroup;
	}

	public Map<String, String> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Map<String, String> userGroups) {
		this.userGroups = userGroups;
	}

	public List<String> getSelectedGroups() {
		return selectedGroups;
	}

	public void setSelectedGroups(List<String> selectedGroups) {
		this.selectedGroups = selectedGroups;
	}

	public boolean isPostOnTimeLine() {
		return postOnTimeLine;
	}

	public void setPostOnTimeLine(boolean postOnTimeLine) {
		this.postOnTimeLine = postOnTimeLine;
	}

	public boolean isTweetFromMyAccount() {
		return tweetFromMyAccount;
	}

	public void setTweetFromMyAccount(boolean tweetFromMyAccount) {
		this.tweetFromMyAccount = tweetFromMyAccount;
	}

	
}
