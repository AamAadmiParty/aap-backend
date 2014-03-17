package com.next.aap.web.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.AapService;
import com.next.aap.web.controller.BaseController;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.CookieUtil;

public abstract class BaseSocialLoginController<T> extends BaseController {

	@Autowired 
	protected ConnectionFactoryLocator connectionFactoryLocator;
	
	@Autowired
	protected AapService aapService;
	
	protected abstract UserDto saveSocialUser(Connection<T> socialConnection, UserDto loggedInUser) throws Exception;

	protected void afterSuccesfullLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Connection<T> socialConnection) throws Exception{
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		UserDto user = saveSocialUser(socialConnection, loggedInUser);
		setLoggedInUserInSesion(httpServletRequest, user);
		
		LoginAccountDto userLoginAccounts = aapService.getUserLoginAccounts(user.getId());
		setLoggedInAccountsInSesion(httpServletRequest, userLoginAccounts);
		CookieUtil.setUserLocationCookie(httpServletResponse, user);
		
		UserRolePermissionDto userRolePermissionDto = aapService.getUserRolePermissions(user.getId());
		setUserRolePermissionInSesion(httpServletRequest, userRolePermissionDto);
	}
}
