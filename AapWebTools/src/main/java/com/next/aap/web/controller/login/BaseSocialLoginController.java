package com.next.aap.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

import com.next.aap.web.controller.BaseController;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;

public abstract class BaseSocialLoginController<T> extends BaseController {

	@Autowired 
	protected ConnectionFactoryLocator connectionFactoryLocator;
	
	@Autowired
	protected UsersConnectionRepository usersConnectionRepository ;
	
	
	protected abstract UserDto saveSocialUser(Connection<T> socialConnection, UserDto loggedInUser);

	protected void afterSuccesfullLogin(HttpServletRequest httpServletRequest, Connection<T> socialConnection){
		UserDto loggedInUser = getLoggedInUserInSesion(httpServletRequest);
		UserDto user = saveSocialUser(socialConnection, loggedInUser);
		setLoggedInUserInSesion(httpServletRequest, user);
		
		LoginAccountDto userLoginAccounts = aapService.getUserLoginAccounts(user.getId());
		setLoggedInAccountsInSesion(httpServletRequest, userLoginAccounts);
	}
}
