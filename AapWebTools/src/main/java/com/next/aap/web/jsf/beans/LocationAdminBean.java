package com.next.aap.web.jsf.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "locationAdminBean", beanName="locationAdminBean", pattern = "/locationadmin", viewId = "/WEB-INF/jsf/location_admin.xhtml")
@URLBeanName("locationAdminBean")
public class LocationAdminBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	MenuBean menuBean;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		System.out.println("MenuBean = "+ menuBean);
		UserDto loggedInUser = getLoggedInUser(true,buildLoginUrl("/locationadmin"));
		if(loggedInUser == null){
			return;
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
		}
	}
	public void goToVoiceOfAapAdminPageTwitter(){
		if(isVoiceOfAapFbAllowed()){
			buildAndRedirect("/admin/voiceofaaptwitter");
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
	


}
