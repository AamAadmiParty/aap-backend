package com.next.aap.web.jsf.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "locationAdminBean", beanName="locationAdminBean", pattern = "/admin/location", viewId = "/WEB-INF/jsf/location_admin.xhtml")
@URLBeanName("locationAdminBean")
public class LocationAdminBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	MenuBean menuBean;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true,buildLoginUrl("/admin/location"));
		if(loggedInUser == null){
			return;
		}
		if(isLocationNotSelected(menuBean)){
			buildAndRedirect("/admin/home");
		}
	}
	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}
	
	public void goToVoiceOfAapAdminPageFb(){
		if(isVoiceOfAapFbAllowed()){
			buildAndRedirect("/admin/voiceofaapfb");
		}else{
			buildAndRedirect("/admin/notallowed");
		}
	}
	public void goToVoiceOfAapAdminPageTwitter(){
		if(isVoiceOfAapTwitterAllowed()){
			buildAndRedirect("/admin/voiceofaaptwitter");
		}else{
			buildAndRedirect("/admin/notallowed");
		}
	}
	public void goToManageNewsPage(){
		if(isManageNewsAllowed()){
			buildAndRedirect("/admin/news");
		}else{
			buildAndRedirect("/admin/notallowed");
		}
	}
	public void goToManageBlogPage(){
		if(isManageBlogAllowed()){
			buildAndRedirect("/admin/blog");
		}else{
			buildAndRedirect("/admin/notallowed");
		}
	}
	public void goToManagePollPage(){
		if(isManagePollAllowed()){
			buildAndRedirect("/admin/poll");
		}else{
			buildAndRedirect("/admin/notallowed");
		}
	}
	public void goToManageMemberPage(){
		if(isManageMemberAllowed()){
			buildAndRedirect("/admin/register");
		}else{
			buildAndRedirect("/admin/notallowed");
		}
	}
	
	public void goToManageUserRolePage(){
		if(isManageUserRoleAllowed()){
			buildAndRedirect("/admin/roles");
		}else{
			buildAndRedirect("/admin/notallowed");
		}
	}
	
	
	
	
	public boolean isVoiceOfAapFbAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isVoiceOfAapFbAllowed(userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isVoiceOfAapTwitterAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isVoiceOfAapTwitterAllowed(userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isManageNewsAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManageNewsAllowed(userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isManageBlogAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManageBlogAllowed(userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isManagePollAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManagePollAllowed(userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isManageMemberAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManageMemberAllowed(userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isManageUserRoleAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManageUserRoleAllowed(userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
}
