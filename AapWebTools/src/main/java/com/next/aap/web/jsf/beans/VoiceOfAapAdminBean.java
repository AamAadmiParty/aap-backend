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
	
	private int pageNumber = 1;
	private int pageSize = 20;
	private boolean showList = true;
	
	private List<PlannedFacebookPostDto> plannedFacebookPosts;
	public VoiceOfAapAdminBean(){
		super(AppPermission.ADMIN_VOICE_OF_AAP_FB, "/admin/voiceofaapfb");
	}
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		if(!checkUserAccess()){
			return;
		}
		refreshPosts();
		showList = true;
		
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
		showList = false;
		this.selectedFacebookPost = selectedFacebookPost;
		handlePostTypeChange();
	}
	
	public void showPreview(){
		
	}
	public void savePost(){
		try{
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
					if(today.getTime().after(selectedFacebookPost.getPostingTime())){
						sendErrorMessageToJsfScreen("Please enter a time in future(Now + 1 hour)");
					}
				}
			}
			if(isValidInput()){
				aapService.savePlannedFacebookPost(selectedFacebookPost);
				sendInfoMessageToJsfScreen("Post saved succesfully");
				refreshPosts();
				showList = true;
			}
				
		}catch(Exception ex){
			sendErrorMessageToJsfScreen("Unable to save Post",ex);
		}
		
	}
	private void refreshPosts(){
		plannedFacebookPosts = aapService.getPlannedFacebookPostsForLocation(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId(), pageNumber, pageSize);
	}
	public void newPost(){
		selectedFacebookPost = new PlannedFacebookPostDto();
		selectedFacebookPost.setPicture("https://lh6.googleusercontent.com/-G8Ikvc4xlmE/ToqQJiMd_LI/AAAAAAAAGtE/icBNFtCSwI4/s640/1226230024559.jpg");
		showList = false;
	}
	public void clear(){
		newPost();
	}
	public void cancel(){
		newPost();
		showList = true;
	}
	
	public void handlePostTypeChange(){
		if(selectedFacebookPost.getPostType().equals(PlannedFacebookPostDto.PHOTO_TYPE)){
			photoTypeFbPost = true;
			textTypeFbPost = false;
			linkTypeFbPost = false;
		}
		if(selectedFacebookPost.getPostType().equals(PlannedFacebookPostDto.LINK_TYPE)){
			photoTypeFbPost = false;
			textTypeFbPost = false;
			linkTypeFbPost = true;
		}
		if(selectedFacebookPost.getPostType().equals(PlannedFacebookPostDto.TEXT_TYPE)){
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
	public List<PlannedFacebookPostDto> getPlannedFacebookPosts() {
		return plannedFacebookPosts;
	}
	public void setPlannedFacebookPosts(List<PlannedFacebookPostDto> plannedFacebookPosts) {
		this.plannedFacebookPosts = plannedFacebookPosts;
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


}
