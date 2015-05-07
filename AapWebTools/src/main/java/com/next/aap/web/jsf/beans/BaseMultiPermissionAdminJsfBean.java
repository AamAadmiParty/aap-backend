package com.next.aap.web.jsf.beans;

import javax.faces.bean.ManagedProperty;

import com.next.aap.core.service.AapService;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.cache.BlogItemCacheImpl;
import com.next.aap.web.cache.NewsItemCacheImpl;
import com.next.aap.web.cache.TemplateCache;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;

public class BaseMultiPermissionAdminJsfBean extends BaseJsfBean{

	@ManagedProperty("#{aapService}")
	protected AapService aapService;
	
	@ManagedProperty("#{aapDataCacheDbImpl}")
	protected AapDataCache aapDataCache;

	@ManagedProperty("#{newsItemCacheImpl}")
	protected NewsItemCacheImpl newsItemCacheImpl;

	@ManagedProperty("#{blogItemCacheImpl}")
	protected BlogItemCacheImpl blogItemCacheImpl;

	@ManagedProperty("#{menuBean}")
	protected MenuBean menuBean;

    @ManagedProperty("#{templateCaheInMemoryImpl}")
    protected TemplateCache templateCache;

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
	public NewsItemCacheImpl getNewsItemCacheImpl() {
		return newsItemCacheImpl;
	}
	public void setNewsItemCacheImpl(NewsItemCacheImpl newsItemCacheImpl) {
		this.newsItemCacheImpl = newsItemCacheImpl;
	}
	public BlogItemCacheImpl getBlogItemCacheImpl() {
		return blogItemCacheImpl;
	}
	public void setBlogItemCacheImpl(BlogItemCacheImpl blogItemCacheImpl) {
		this.blogItemCacheImpl = blogItemCacheImpl;
	}

    public TemplateCache getTemplateCache() {
        return templateCache;
    }

    public void setTemplateCache(TemplateCache templateCache) {
        this.templateCache = templateCache;
    }
}
