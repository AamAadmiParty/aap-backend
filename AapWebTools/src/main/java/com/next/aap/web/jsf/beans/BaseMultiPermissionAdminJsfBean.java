package com.next.aap.web.jsf.beans;

import org.springframework.beans.factory.annotation.Autowired;

import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;

public class BaseMultiPermissionAdminJsfBean extends BaseJsfBean{

	@Autowired
	private MenuBean menuBean;
	
	AppPermission[] appPermissions;
	String url;
	public BaseMultiPermissionAdminJsfBean(String url, AppPermission...appPermissions){
		this.appPermissions = appPermissions;
		this.url = url;
	}
	protected boolean checkUserAccess(){
		UserDto loggedInUser = getLoggedInUser(true,buildLoginUrl(url));
		if(loggedInUser == null){
			return false;
		}
		if(isLocationNotSelected(menuBean)){
			buildAndRedirect("/admin/home");
			return false;
		}
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if(appPermissions == null || appPermissions.length == 0){
			return true;
		}
		for(AppPermission oneAppPermission:appPermissions){
			//if atleast one permission is true, allow user to go to screen
			if(ClientPermissionUtil.isAllowed(oneAppPermission, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType())){
				return true;
			}
			
		}
		buildAndRedirect("/admin/notallowed");
		return false;
	}
}
