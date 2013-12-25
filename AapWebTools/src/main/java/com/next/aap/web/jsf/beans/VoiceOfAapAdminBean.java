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
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "voiceOfAapAdminBean", beanName="voiceOfAapAdminBean", pattern = "/admin/voiceofaapfb", viewId = "/WEB-INF/jsf/admin_voiceofaap.xhtml")
@URLBeanName("voiceOfAapAdminBean")
public class VoiceOfAapAdminBean extends BaseAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private boolean photoTypeFbPost;
	private boolean textTypeFbPost;
	private boolean linkTypeFbPost;
	private PlannedFacebookPostDto selectedFacebookPost;
	
	@Autowired 
	private MenuBean menuBean;
	
	private List<PlannedFacebookPostDto> plannedFacebookPosts;
	public VoiceOfAapAdminBean(){
		super(AppPermission.ADMIN_VOICE_OF_AAP_FB, "/admin/voiceofaapfb");
		selectedFacebookPost = new PlannedFacebookPostDto();
		selectedFacebookPost.setPicture("https://lh6.googleusercontent.com/-G8Ikvc4xlmE/ToqQJiMd_LI/AAAAAAAAGtE/icBNFtCSwI4/s640/1226230024559.jpg");
	}
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		if(!checkUserAccess()){
			return;
		}
		
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}
	public PlannedFacebookPostDto getSelectedFacebookPost() {
		return selectedFacebookPost;
	}
	public void setSelectedFacebookPost(PlannedFacebookPostDto selectedFacebookPost) {
		this.selectedFacebookPost = selectedFacebookPost;
	}
	
	public void showPreview(){
		
	}
	public void savePost(){
		try{
			System.out.println("MenuBean = "+ menuBean);
			selectedFacebookPost.setLocationType(menuBean.getLocationType());
			selectedFacebookPost.setLocationId(menuBean.getAdminSelectedLocationId());
			
			if(photoTypeFbPost){
				if(StringUtil.isEmpty(selectedFacebookPost.getPicture())){
					sendErrorMessageToJsfScreen("Please enter a Image URL");
				}
			}
			if(textTypeFbPost){
				if(StringUtil.isEmpty(selectedFacebookPost.getMessage())){
					sendErrorMessageToJsfScreen("Please enter Message");
				}
			}
			if(linkTypeFbPost){
				if(StringUtil.isEmpty(selectedFacebookPost.getCaption())){
					sendErrorMessageToJsfScreen("Please enter Caption");
				}
				if(StringUtil.isEmpty(selectedFacebookPost.getDescription())){
					sendErrorMessageToJsfScreen("Please enter Description");
				}
				if(StringUtil.isEmpty(selectedFacebookPost.getLink())){
					sendErrorMessageToJsfScreen("Please enter Link");
				}
				if(StringUtil.isEmpty(selectedFacebookPost.getName())){
					sendErrorMessageToJsfScreen("Please enter Name");
				}
				if(selectedFacebookPost.getPostingTime() == null){
					sendErrorMessageToJsfScreen("Please enter when you want to post it by choosing correct time in future");
				}else{
					Calendar today = Calendar.getInstance();
					System.out.println("today = "+today.getTime());
					if(today.getTime().after(selectedFacebookPost.getPostingTime())){
						sendErrorMessageToJsfScreen("Please enter a time in future(Now + 1 hour)");
					}
				}
			}
			System.out.println("selectedFacebookPost=" + selectedFacebookPost);
			if(isValidInput()){
				aapService.savePlannedFacebookPost(selectedFacebookPost);	
			}
				
		}catch(Exception ex){
			sendErrorMessageToJsfScreen("Unable to save Post",ex);
		}
		
	}
	public void clear(){
		selectedFacebookPost = new PlannedFacebookPostDto();
	}
	
	public void handlePostTypeChange(){
		if(selectedFacebookPost.getPostType().equals("Photo")){
			photoTypeFbPost = true;
			textTypeFbPost = false;
			linkTypeFbPost = false;
		}
		if(selectedFacebookPost.getPostType().equals("Link")){
			photoTypeFbPost = false;
			textTypeFbPost = false;
			linkTypeFbPost = true;
		}
		if(selectedFacebookPost.getPostType().equals("TextOnly")){
			photoTypeFbPost = false;
			textTypeFbPost = true;
			linkTypeFbPost = false;
		}

	}
	public boolean isPhotoTypeFbPost() {
		return photoTypeFbPost;
	}
	public void setPhotoTypeFbPost(boolean photoTypeFbPost) {
		this.photoTypeFbPost = photoTypeFbPost;
	}
	public boolean isTextTypeFbPost() {
		return textTypeFbPost;
	}
	public void setTextTypeFbPost(boolean textTypeFbPost) {
		this.textTypeFbPost = textTypeFbPost;
	}
	public boolean isLinkTypeFbPost() {
		return linkTypeFbPost;
	}
	public void setLinkTypeFbPost(boolean linkTypeFbPost) {
		this.linkTypeFbPost = linkTypeFbPost;
	}


}
