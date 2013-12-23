package com.next.aap.web.dto;

import java.io.Serializable;
import java.util.List;

public class VoiceOfAapData implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean beVoiceOfAap;
	private boolean postOnTimeLine;
	private boolean tweetFromMyAccount;
    private List<String> selectedGroups;
	public boolean isBeVoiceOfAap() {
		return beVoiceOfAap;
	}
	public void setBeVoiceOfAap(boolean beVoiceOfAap) {
		this.beVoiceOfAap = beVoiceOfAap;
	}
	public boolean isPostOnTimeLine() {
		return postOnTimeLine;
	}
	public void setPostOnTimeLine(boolean postOnTimeLine) {
		this.postOnTimeLine = postOnTimeLine;
	}
	public List<String> getSelectedGroups() {
		return selectedGroups;
	}
	public void setSelectedGroups(List<String> selectedGroups) {
		this.selectedGroups = selectedGroups;
	}
	public boolean isTweetFromMyAccount() {
		return tweetFromMyAccount;
	}
	public void setTweetFromMyAccount(boolean tweetFromMyAccount) {
		this.tweetFromMyAccount = tweetFromMyAccount;
	}  

}
