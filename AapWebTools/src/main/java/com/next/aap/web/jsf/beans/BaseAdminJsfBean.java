package com.next.aap.web.jsf.beans;

import javax.faces.bean.ManagedProperty;

import org.springframework.beans.factory.annotation.Autowired;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;

public class BaseAdminJsfBean extends BaseJsfBean{

	@ManagedProperty("#{aapService}")
	protected AapService aapService;
	
	@ManagedProperty("#{menuBean}")
	protected MenuBean menuBean;

	AppPermission appPermission;
	String url;
	public BaseAdminJsfBean(AppPermission appPermission, String url){
		this.appPermission = appPermission;
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
		if(!ClientPermissionUtil.isAllowed(appPermission, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType())){
			buildAndRedirect("/admin/notallowed");
			return false;
		}
		return true;
	}
	public MenuBean getMenuBean() {
		return menuBean;
	}
	public void setMenuBean(MenuBean menuBean) {
		this.menuBean = menuBean;
	}
	public AapService getAapService() {
		return aapService;
	}
	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}
}
