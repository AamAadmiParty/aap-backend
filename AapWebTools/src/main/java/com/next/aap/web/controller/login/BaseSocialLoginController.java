package com.next.aap.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.controller.BaseController;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;

public abstract class BaseSocialLoginController<T> extends BaseController {

	@Autowired 
	protected ConnectionFactoryLocator connectionFactoryLocator;
	
	protected abstract UserDto saveSocialUser(Connection<T> socialConnection, UserDto loggedInUser) throws Exception;

	protected void afterSuccesfullLogin(HttpServletRequest httpServletRequest, Connection<T> socialConnection) throws Exception{
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		UserDto user = saveSocialUser(socialConnection, loggedInUser);
		setLoggedInUserInSesion(httpServletRequest, user);
		
		LoginAccountDto userLoginAccounts = aapService.getUserLoginAccounts(user.getId());
		setLoggedInAccountsInSesion(httpServletRequest, userLoginAccounts);
		
		UserRolePermissionDto userRolePermissionDto = aapService.getUserRolePermissions(user.getId());
		setUserRolePermissionInSesion(httpServletRequest, userRolePermissionDto);
	}
}
