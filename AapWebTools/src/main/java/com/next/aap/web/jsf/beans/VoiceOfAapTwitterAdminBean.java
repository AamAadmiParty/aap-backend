package com.next.aap.web.jsf.beans;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.PlannedFacebookPostDto;
import com.next.aap.web.dto.PlannedTweetDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "voiceOfAapTwitterAdminBean", beanName = "voiceOfAapTwitterAdminBean", pattern = "/admin/voiceofaaptwitter", viewId = "/WEB-INF/jsf/admin_voiceofaaptwitter.xhtml")
@URLBeanName("voiceOfAapTwitterAdminBean")
public class VoiceOfAapTwitterAdminBean extends BaseAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private PlannedTweetDto selectedPlannedTweet;

	@Autowired
	private MenuBean menuBean;

	private int pageNumber = 1;
	private int pageSize = 20;
	private boolean showList = true;

	private List<PlannedTweetDto> plannedTweets;
	private String tweetPreview;

	public VoiceOfAapTwitterAdminBean() {
		super(AppPermission.ADMIN_VOICE_OF_AAP_FB, "/admin/voiceofaaptwitter");
	}

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {
		if (!checkUserAccess()) {
			return;
		}
		refreshTweetList();
	}
	private void refreshTweetList(){
		plannedTweets = aapService.getPlannedTweetsForLocation(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId(), pageNumber, pageSize);
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}

	public void setSelectedFacebookPost(PlannedFacebookPostDto selectedFacebookPost) {
	}

	public void showPreview() {
		if (selectedPlannedTweet.getTweetType().equals(PlannedTweetDto.RETWEET_TYPE)) {
 
		}

	}

	public void savePost() {
		try {
			selectedPlannedTweet.setLocationType(menuBean.getLocationType());
			selectedPlannedTweet.setLocationId(menuBean.getAdminSelectedLocationId());

			if (selectedPlannedTweet.getTweetType().equals(PlannedTweetDto.TWEET_TYPE)) {
				if (StringUtil.isEmpty(selectedPlannedTweet.getMessage())) {
					sendErrorMessageToJsfScreen("Please enter a Image URL");
				} else {
					if (selectedPlannedTweet.getMessage().length() > 140) {
						sendErrorMessageToJsfScreen("Please enter a message equal or less then 140");
					}
				}
			}
			
			if (selectedPlannedTweet.getTweetType().equals(PlannedTweetDto.RETWEET_TYPE)) {
				if (selectedPlannedTweet.getTweetId() ==  null || selectedPlannedTweet.getTweetId() <=0) {
					sendErrorMessageToJsfScreen("Please enter tweet Id to retweet");
				} 
			}

			if (selectedPlannedTweet.getPostingTime() == null) {
				sendErrorMessageToJsfScreen("Please enter when you want to tweet it by choosing correct time in future");
			} else {
				Calendar today = Calendar.getInstance();
				System.out.println("today = " + today.getTime());
				if (today.getTime().after(selectedPlannedTweet.getPostingTime())) {
					sendErrorMessageToJsfScreen("Please enter posting time in future");
				}
			}
			if (isValidInput()) {
				selectedPlannedTweet = aapService.savePlannedTweet(selectedPlannedTweet);
				sendInfoMessageToJsfScreen("Tweet saved succesfully");
				refreshTweetList();
				showList = true;
			}

		} catch (Exception ex) {
			sendErrorMessageToJsfScreen("Unable to save Post", ex);
		}

	}

	public void newPost() {
		selectedPlannedTweet = new PlannedTweetDto();
		showList = false;
	}

	public void clear() {
		newPost();
	}

	public void cancel() {
		newPost();
		showList = true;
	}

	public void handlePostTypeChange() {
	}
	
	public boolean isNormalTweetType(){
		if(PlannedTweetDto.TWEET_TYPE.equals(selectedPlannedTweet.getTweetType())){
			return true;
		}
		return false;
	}
	public boolean isRetweetType(){
		if(PlannedTweetDto.RETWEET_TYPE.equals(selectedPlannedTweet.getTweetType())){
			return true;
		}
		return false;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isShowList() {
		return showList;
	}

	public void setShowList(boolean showList) {
		this.showList = showList;
	}

	public PlannedTweetDto getSelectedPlannedTweet() {
		return selectedPlannedTweet;
	}

	public void setSelectedPlannedTweet(PlannedTweetDto selectedPlannedTweet) {
		this.selectedPlannedTweet = selectedPlannedTweet;
		showList = false;
		handlePostTypeChange();
	}

	public List<PlannedTweetDto> getPlannedTweets() {
		return plannedTweets;
	}

	public void setPlannedTweets(List<PlannedTweetDto> plannedTweets) {
		this.plannedTweets = plannedTweets;
	}

	public String getTweetPreview() {
		return tweetPreview;
	}

	public void setTweetPreview(String tweetPreview) {
		this.tweetPreview = tweetPreview;
	}

}
