package com.next.aap.web.jsf.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookPostDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "voiceOfAapActivityBean", beanName="voiceOfAapActivityBean", pattern = "/voafactivity", viewId = "/WEB-INF/jsf/voafactivity.xhtml")
@URLBeanName("voiceOfAapActivityBean")
public class VoiceOfAapActivityBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private UserDto loggedInUser;
	
	
	@Autowired
	private AapService aapService;
	
	private boolean beVoiceOfAap;
	private boolean postOnGroup;
	private boolean postOnTimeLine;
	private boolean tweetFromMyAccount;
	
	FacebookAccountDto oneFacebookAccountDto;
	List<FacebookPostDto> userPosts;
	
	//@URLActions(actions = { @URLAction(mappingId = "voiceOfAapActivityBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		loggedInUser = getLoggedInUser(true,buildLoginUrl("/voafactivity"));
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

			userPosts = aapService.getUserFacebookPosts(oneFacebookAccountDto.getId());
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

	public List<FacebookPostDto> getUserPosts() {
		return userPosts;
	}

	public void setUserPosts(List<FacebookPostDto> userPosts) {
		this.userPosts = userPosts;
	}

	
}
