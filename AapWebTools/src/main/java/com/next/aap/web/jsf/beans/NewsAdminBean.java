package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.ContentTweetDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "newsAdminBean", beanName="newsAdminBean", pattern = "/admin/news", viewId = "/WEB-INF/jsf/admin_news.xhtml")
@URLBeanName("newsAdminBean")
public class NewsAdminBean extends BaseMultiPermissionAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private NewsDto selectedNews;;
	
	@Autowired 
	private MenuBean menuBean;
	
	private boolean showList = true;
	private List<ContentTweetDto> tweetList;
	private ContentTweetDto selectedTweet;
	private boolean newTweet = false;
	private boolean showTweetList = true;
	
	private List<NewsDto> newsList;
	public NewsAdminBean(){
		super("/admin/news", AppPermission.CREATE_NEWS,AppPermission.UPDATE_NEWS, AppPermission.DELETE_NEWS, AppPermission.APPROVE_NEWS);
		selectedTweet = new ContentTweetDto();
	}
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		if(!checkUserAccess()){
			return;
		}
		refreshNewsList();
	}
	private void refreshNewsList(){
		newsList = aapService.getNews(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
		tweetList = new ArrayList<>();
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}
	public void cancelTweet(){
		showTweetList = true;
	}
	public void createNewsTweet(){
		selectedTweet = new ContentTweetDto();
		newTweet = true;
		showTweetList = false;
	}
	public void addNewsTweet(){
		if(newTweet){
			tweetList.add(selectedTweet);
		}
		showTweetList = true;
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}
	public NewsDto getSelectedNews() {
		return selectedNews;
	}
	public void setSelectedNews(NewsDto selectedNews) {
		this.selectedNews = selectedNews;
		showList = false;
		tweetList = aapService.getNewsContentTweets(selectedNews.getId());
	}
	public boolean isSaveDraft(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isAllowed(AppPermission.CREATE_NEWS, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()) ||
				ClientPermissionUtil.isAllowed(AppPermission.UPDATE_NEWS, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isSaveAndPublish(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return (ClientPermissionUtil.isAllowed(AppPermission.CREATE_NEWS, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()) ||
				ClientPermissionUtil.isAllowed(AppPermission.UPDATE_NEWS, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType())) &&
				ClientPermissionUtil.isAllowed(AppPermission.APPROVE_NEWS, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isPublish(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isAllowed(AppPermission.APPROVE_NEWS, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isEditAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return (ClientPermissionUtil.isAllowed(AppPermission.CREATE_NEWS, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()) ||
				ClientPermissionUtil.isAllowed(AppPermission.UPDATE_NEWS, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()));
	}
	public void saveAndPublishPost() {
		savePost();
		publishPost();
	}
	public void publishPost(){
		try{
			if(selectedNews == null){
				sendErrorMessageToJsfScreen("No news selected to publish");
			}
			if(selectedNews.getId() == null || selectedNews.getId() <= 0){
				sendErrorMessageToJsfScreen("Please save the News first");
			}
			if(!isPublish()){
				sendErrorMessageToJsfScreen("You do not have permission to publish a news");
			}
			if(isValidInput()){
				selectedNews = aapService.publishNews(selectedNews.getId());	
			}
			
		}catch(Exception ex){
			sendErrorMessageToJsfScreen("Unable to save Post",ex);
		}
	}
	public void savePost(){
		try{
			if(StringUtil.isEmpty(selectedNews.getContent())){
				sendErrorMessageToJsfScreen("Please enter News Content");
			}
			if(StringUtil.isEmpty(selectedNews.getTitle())){
				sendErrorMessageToJsfScreen("Please enter News Title");
			}

			if(isValidInput()){
				
				selectedNews = aapService.saveNews(selectedNews, tweetList, menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
				sendInfoMessageToJsfScreen("News saved succesfully");
				refreshNewsList();
				showList = true;
			}
				
		}catch(Exception ex){
			sendErrorMessageToJsfScreen("Unable to save Post",ex);
		}
		
	}
	public void createNews(){
		selectedNews = new NewsDto();
		showList = false;
		tweetList = new ArrayList<>();
	}
	public void cancel(){
		createNews();
		showList = true;
	}
	public void deleteTweet(){
		tweetList.remove(selectedTweet);
	}
	
	public boolean isShowList() {
		return showList;
	}
	public void setShowList(boolean showList) {
		this.showList = showList;
	}
	public List<NewsDto> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<NewsDto> newsList) {
		this.newsList = newsList;
	}
	public List<ContentTweetDto> getTweetList() {
		return tweetList;
	}
	public void setTweetList(List<ContentTweetDto> tweetList) {
		this.tweetList = tweetList;
	}
	public ContentTweetDto getSelectedTweet() {
		return selectedTweet;
	}
	public void setSelectedTweet(ContentTweetDto selectedTweet) {
		this.selectedTweet = selectedTweet;
		newTweet = false;
		showTweetList = false;
	}
	public boolean isNewTweet() {
		return newTweet;
	}
	public void setNewTweet(boolean newTweet) {
		this.newTweet = newTweet;
	}
	public boolean isShowTweetList() {
		return showTweetList;
	}
	public void setShowTweetList(boolean showTweetList) {
		this.showTweetList = showTweetList;
	}


}
