package com.next.aap.web.jsf.beans;

import javax.faces.bean.ManagedProperty;

import com.next.aap.core.service.AapService;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;

public class BaseMultiPermissionAdminJsfBean extends BaseJsfBean{

	@ManagedProperty("#{aapService}")
	protected AapService aapService;
	
	@ManagedProperty("#{aapDataCacheDbImpl}")
	protected AapDataCache aapDataCache;

	@ManagedProperty("#{menuBean}")
	protected MenuBean menuBean;

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
		System.out.println("menuBean="+menuBean);
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
	public AapService getAapService() {
		return aapService;
	}
	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}
	public MenuBean getMenuBean() {
		return menuBean;
	}
	public void setMenuBean(MenuBean menuBean) {
		this.menuBean = menuBean;
	}
	public AapDataCache getAapDataCache() {
		return aapDataCache;
	}
	public void setAapDataCache(AapDataCache aapDataCache) {
		this.aapDataCache = aapDataCache;
	}
}
