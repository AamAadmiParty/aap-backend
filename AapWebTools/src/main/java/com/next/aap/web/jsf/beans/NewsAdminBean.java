package com.next.aap.web.jsf.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.UserDto;
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
	
	private List<NewsDto> newsList;
	public NewsAdminBean(){
		super("/admin/news", AppPermission.CREATE_NEWS,AppPermission.UPDATE_NEWS, AppPermission.DELETE_NEWS, AppPermission.APPROVE_NEWS);
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
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
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
	}
	public void showPreview(){
		
	}
	public void savePost(){
		try{
			System.out.println("MenuBean = "+ menuBean);
			
			if(StringUtil.isEmpty(selectedNews.getContent())){
				sendErrorMessageToJsfScreen("Please enter News Content");
			}
			if(StringUtil.isEmpty(selectedNews.getTitle())){
				sendErrorMessageToJsfScreen("Please enter News Title");
			}

			if(isValidInput()){
				aapService.saveNews(selectedNews, menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
				sendInfoMessageToJsfScreen("News saved succesfully");
				refreshNewsList();
				showList = true;
			}
				
		}catch(Exception ex){
			sendErrorMessageToJsfScreen("Unable to save Post",ex);
		}
		
	}
	public void newPost(){
		selectedNews = new NewsDto();
		showList = false;
	}
	public void clear(){
		newPost();
	}
	public void cancel(){
		newPost();
		showList = true;
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


}
