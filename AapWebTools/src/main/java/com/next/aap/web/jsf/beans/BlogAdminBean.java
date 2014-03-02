package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.ContentTweetDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

//@Scope("session")
@ViewScoped
@ManagedBean
@URLMapping(id = "blogAdminBean", beanName="blogAdminBean", pattern = "/admin/blog", viewId = "/WEB-INF/jsf/admin_blog.xhtml")
@URLBeanName("blogAdminBean")
public class BlogAdminBean extends BaseMultiPermissionAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private BlogDto selectedBlog;;
	
	private boolean showList = true;
	private List<ContentTweetDto> tweetList;
	private ContentTweetDto selectedTweet;
	private boolean newTweet = false;
	private boolean showTweetList = true;
	
	private List<BlogDto> blogList;
	public BlogAdminBean(){
		super("/admin/blog", AppPermission.CREATE_BLOG,AppPermission.UPDATE_BLOG, AppPermission.DELETE_BLOG, AppPermission.APPROVE_BLOG);
		selectedTweet = new ContentTweetDto();
	}
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		if(!checkUserAccess()){
			return;
		}
		refreshBlogList();
	}
	private void refreshBlogList(){
		blogList = aapService.getBlog(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
		tweetList = new ArrayList<>();
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}
	public void cancelTweet(){
		showTweetList = true;
	}
	public void createBlogTweet(){
		selectedTweet = new ContentTweetDto();
		newTweet = true;
		showTweetList = false;
	}
	public void addBlogTweet(){
		if(newTweet){
			tweetList.add(selectedTweet);
		}
		showTweetList = true;
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}
	public BlogDto getSelectedBlog() {
		return selectedBlog;
	}
	public void setSelectedBlog(BlogDto selectedBlog) {
		this.selectedBlog = selectedBlog;
		showList = false;
		tweetList = aapService.getBlogContentTweets(selectedBlog.getId());
	}
	public boolean isSaveDraft(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isAllowed(AppPermission.CREATE_BLOG, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()) ||
				ClientPermissionUtil.isAllowed(AppPermission.UPDATE_BLOG, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isSaveAndPublish(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return (ClientPermissionUtil.isAllowed(AppPermission.CREATE_BLOG, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()) ||
				ClientPermissionUtil.isAllowed(AppPermission.UPDATE_BLOG, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType())) &&
				ClientPermissionUtil.isAllowed(AppPermission.APPROVE_BLOG, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isPublish(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isAllowed(AppPermission.APPROVE_BLOG, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isEditAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return (ClientPermissionUtil.isAllowed(AppPermission.CREATE_BLOG, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()) ||
				ClientPermissionUtil.isAllowed(AppPermission.UPDATE_BLOG, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()));
	}
	public void saveAndPublishPost() {
		savePost();
		publishPost();
	}
	public void publishPost(){
		try{
			if(selectedBlog == null){
				sendErrorMessageToJsfScreen("No blog selected to publish");
			}
			if(selectedBlog.getId() == null || selectedBlog.getId() <= 0){
				sendErrorMessageToJsfScreen("Please save the Blog first");
			}
			if(!isPublish()){
				sendErrorMessageToJsfScreen("You do not have permission to publish a blog");
			}
			if(isValidInput()){
				selectedBlog = aapService.publishBlog(selectedBlog.getId());
				sendInfoMessageToJsfScreen("Blog Published Succesfully");
				refreshBlogList();
				refreshBlogsInCache();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			sendErrorMessageToJsfScreen("Unable to save Post",ex);
		}
	}
	private void refreshBlogsInCache(){
		System.out.println("Refreshing Cache for Location "+ menuBean.getLocationType());
		switch(menuBean.getLocationType()){
		case Global:
			blogItemCacheImpl.refreshFullCache();
			break;
		case STATE:
			blogItemCacheImpl.refreshCacheItemsForState(menuBean.getSelectedStateId());
			break;
		case DISTRICT:
			blogItemCacheImpl.refreshCacheItemsForDistrict(menuBean.getSelectedDistrictId());
			break;
		case AC:
			blogItemCacheImpl.refreshCacheItemsForAc(menuBean.getSelectedAcId());
			break;
		case PC:
			blogItemCacheImpl.refreshCacheItemsForPc(menuBean.getSelectedPcId());
			break;
		case COUNTRY:
			newsItemCacheImpl.refreshCacheItemsForCountry(menuBean.getSelectedCountryId());
			break;
		case REGION :
			blogItemCacheImpl.refreshCacheItemsForCountryRegion(menuBean.getSelectedCountryRegiontId());
			break;
			/*
		case AREA :
			newsItemCacheImpl.refreshCacheItemsForCountryRegionArea(menuBean.getSelectedCountryRegiontAreaId());
			break;
			*/
		}
	}
	public void savePost(){
		try{
			if(StringUtil.isEmpty(selectedBlog.getContent())){
				sendErrorMessageToJsfScreen("Please enter Blog Content");
			}
			if(StringUtil.isEmpty(selectedBlog.getTitle())){
				sendErrorMessageToJsfScreen("Please enter Blog Title");
			}

			if(isValidInput()){
				
				selectedBlog = aapService.saveBlog(selectedBlog, tweetList, menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
				sendInfoMessageToJsfScreen("Blog saved succesfully");
				refreshBlogList();
				showList = true;
			}
				
		}catch(Exception ex){
			sendErrorMessageToJsfScreen("Unable to save Post",ex);
		}
		
	}
	public void createBlog(){
		selectedBlog = new BlogDto();
		showList = false;
		tweetList = new ArrayList<>();
	}
	public void cancel(){
		createBlog();
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
	public List<BlogDto> getBlogList() {
		return blogList;
	}
	public void setBlogList(List<BlogDto> blogList) {
		this.blogList = blogList;
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
