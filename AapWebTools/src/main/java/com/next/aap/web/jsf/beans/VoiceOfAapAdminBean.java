package com.next.aap.web.jsf.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
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

@ManagedBean
//@Scope("session")
@ViewScoped
@URLMapping(id = "voiceOfAapAdminBean", beanName="voiceOfAapAdminBean", pattern = "/admin/voiceofaapfb", viewId = "/WEB-INF/jsf/admin_voiceofaap.xhtml")
@URLBeanName("voiceOfAapAdminBean")
public class VoiceOfAapAdminBean extends BaseAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private boolean photoTypeFbPost;
	private boolean textTypeFbPost;
	private boolean linkTypeFbPost;
	private PlannedFacebookPostDto selectedFacebookPost;
	
	private int pageNumber = 1;
	private int pageSize = 20;
	private boolean showList = true;
	private int maxChartY;
	private CartesianChartModel totalReachModel;
	private int maxChartYForTimeLine;
	private CartesianChartModel totalTimeLineModel; 
	
	private List<PlannedFacebookPostDto> plannedFacebookPosts;
	private List<PlannedFacebookPostDto> executedFacebookPosts;
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
		executedFacebookPosts = aapService.getExecutedFacebookPostsForLocation(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId(), pageNumber, pageSize);
		maxChartY = 200;
		maxChartYForTimeLine = 200;
		totalReachModel = new CartesianChartModel();
		totalTimeLineModel = new CartesianChartModel();
		
		ChartSeries totalSuccessTimelineSeries = new ChartSeries();  
		totalSuccessTimelineSeries.setLabel("Success");  
		ChartSeries totalFailedTimelineSeries = new ChartSeries();  
		totalFailedTimelineSeries.setLabel("Failed");  
  
		ChartSeries totalSuccessTimelineReachSeries = new ChartSeries();  
		totalSuccessTimelineReachSeries.setLabel("Success");  
		ChartSeries totalFailedTimelineReachSeries = new ChartSeries();  
		totalFailedTimelineReachSeries.setLabel("Failed");  
  
		for(PlannedFacebookPostDto onePlannedFacebookPostDto:executedFacebookPosts){
			
			totalSuccessTimelineSeries.set(onePlannedFacebookPostDto.getId() +"", onePlannedFacebookPostDto.getTotalSuccessTimeLines());
			totalFailedTimelineSeries.set(onePlannedFacebookPostDto.getId() +"", onePlannedFacebookPostDto.getTotalFailedTimeLines());
			totalSuccessTimelineReachSeries.set(onePlannedFacebookPostDto.getId() +"", onePlannedFacebookPostDto.getTotalSuccessTimeLineFriends());
			totalFailedTimelineReachSeries.set(onePlannedFacebookPostDto.getId() +"", onePlannedFacebookPostDto.getTotalFailedTimeLineFriends());
			
			if(onePlannedFacebookPostDto.getTotalSuccessTimeLineFriends() > maxChartY){
				maxChartY = onePlannedFacebookPostDto.getTotalSuccessTimeLineFriends() + onePlannedFacebookPostDto.getTotalSuccessTimeLineFriends() / 10;
			}
			if(onePlannedFacebookPostDto.getTotalFailedTimeLineFriends() > maxChartY){
				maxChartY = onePlannedFacebookPostDto.getTotalFailedTimeLineFriends() + onePlannedFacebookPostDto.getTotalFailedTimeLineFriends() / 10;
			}
			if(onePlannedFacebookPostDto.getTotalSuccessTimeLines() > maxChartYForTimeLine){
				maxChartYForTimeLine = onePlannedFacebookPostDto.getTotalSuccessTimeLines() + onePlannedFacebookPostDto.getTotalSuccessTimeLines() / 10;
			}
			if(onePlannedFacebookPostDto.getTotalFailedTimeLines() > maxChartYForTimeLine){
				maxChartYForTimeLine = onePlannedFacebookPostDto.getTotalFailedTimeLines() + onePlannedFacebookPostDto.getTotalFailedTimeLines() / 10;
			}
		}
		totalTimeLineModel.addSeries(totalSuccessTimelineSeries);
		totalTimeLineModel.addSeries(totalFailedTimelineSeries);
		
		totalReachModel.addSeries(totalSuccessTimelineReachSeries);
		totalReachModel.addSeries(totalFailedTimelineReachSeries);
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
	public List<PlannedFacebookPostDto> getExecutedFacebookPosts() {
		return executedFacebookPosts;
	}
	public void setExecutedFacebookPosts(List<PlannedFacebookPostDto> executedFacebookPosts) {
		this.executedFacebookPosts = executedFacebookPosts;
	}
	public CartesianChartModel getTotalReachModel() {
		return totalReachModel;
	}
	public void setTotalReachModel(CartesianChartModel totalReachModel) {
		this.totalReachModel = totalReachModel;
	}
	public int getMaxChartY() {
		return maxChartY;
	}
	public void setMaxChartY(int maxChartY) {
		this.maxChartY = maxChartY;
	}
	public CartesianChartModel getTotalTimeLineModel() {
		return totalTimeLineModel;
	}
	public void setTotalTimeLineModel(CartesianChartModel totalTimeLineModel) {
		this.totalTimeLineModel = totalTimeLineModel;
	}
	public int getMaxChartYForTimeLine() {
		return maxChartYForTimeLine;
	}
	public void setMaxChartYForTimeLine(int maxChartYForTimeLine) {
		this.maxChartYForTimeLine = maxChartYForTimeLine;
	}


}
