package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;

public class BaseController {

    public static final String REDIRECT_URL_PARAM_ID = "sa_redirect_url";
	
	public static final String FINAL_REDIRECT_URL_PARAM_ID = "fru";
	
	public static final String defaultVersion = "v2";
	
	public static final String SESSION_USER_PARAM = "SESSION_USER_PARAM";
	public static final String SESSION_LOGIN_ACCOUNT_PARAM = "SESSION_LOGIN_ACCOUNT_PARAM";
	public static final String SESSION_USER_PERMISSIONS_PARAM = "SESSION_USER_PERMISSIONS_PARAM";
	
	public static final String PARAM_PAGE_NUMBER = "page";

    @Value("${s3_base_http_path_dc}/${s3_bucket_name_for_static_content}/${s3_base_dir_for_static_content}")
    protected String staticDirectory;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected int getIntPramater(HttpServletRequest httpServletRequest,String paramName, int defaultValue){
		try{
			return Integer.parseInt(httpServletRequest.getParameter(paramName));
		}catch(Exception ex){
			return defaultValue;
		}
	}
	protected long getLongPramater(HttpServletRequest httpServletRequest,String paramName, int defaultValue){
		try{
			return Long.parseLong(httpServletRequest.getParameter(paramName));
		}catch(Exception ex){
			return defaultValue;
		}
	}
	protected void passRedirectUrl(HttpServletRequest httpServletRequest, ModelAndView mv){
		String redirectUrl = httpServletRequest.getParameter(REDIRECT_URL_PARAM_ID);
		mv.getModel().put("redirectUrl", redirectUrl);
	}
	protected String getRedirectUrl(HttpServletRequest httpServletRequest){
		return httpServletRequest.getParameter(REDIRECT_URL_PARAM_ID);
	}
	protected void redirectToViewAfterLogin(HttpServletRequest httpServletRequest, ModelAndView mv){
		String redirectUrl = getRedirectUrl(httpServletRequest);
		redirectToViewAfterLogin(redirectUrl, mv);
	}
	protected void redirectToViewAfterLogin(String redirectUrl, ModelAndView mv){
        RedirectView rv = new RedirectView(redirectUrl);
        rv.setExposeModelAttributes(false);
        mv.setView(rv);
	}
	protected String getRedirectUrlForRedirectionAfterLogin(HttpServletRequest httpServletRequest){
		String redirectUrlAfterLogin = getRedirectUrl(httpServletRequest);
		logger.info("redirectUrlAfterLogin from param = "+redirectUrlAfterLogin);
		if(StringUtil.isEmpty(redirectUrlAfterLogin)){
			redirectUrlAfterLogin = httpServletRequest.getContextPath()+"/signin";
			logger.info("redirectUrlAfterLogin default = "+redirectUrlAfterLogin);
		}
		return redirectUrlAfterLogin;
	}

	
	
	protected void setRedirectUrlInSessiom(HttpServletRequest httpServletRequest, String redirectUrl){
		httpServletRequest.getSession(true).setAttribute(REDIRECT_URL_PARAM_ID, redirectUrl);
	}
	protected String getRedirectUrlFromSession(HttpServletRequest httpServletRequest){
		return (String)httpServletRequest.getSession().getAttribute(REDIRECT_URL_PARAM_ID);
	}
	protected String getAndRemoveRedirectUrlFromSession(HttpServletRequest httpServletRequest){
		String redirectUrl = (String)httpServletRequest.getSession().getAttribute(REDIRECT_URL_PARAM_ID);
		httpServletRequest.getSession().removeAttribute(REDIRECT_URL_PARAM_ID);
		return redirectUrl; 
	}
	
	protected void setFinalRedirectUrlInSesion(HttpServletRequest httpServletRequest,String url){
		httpServletRequest.getSession().setAttribute(FINAL_REDIRECT_URL_PARAM_ID, url);
	}
	public static String getFinalRedirectUrlFromSesion(HttpServletRequest httpServletRequest){
		return (String)httpServletRequest.getSession().getAttribute(FINAL_REDIRECT_URL_PARAM_ID);
	}
	public static void clearFinalRedirectUrlInSesion(HttpServletRequest httpServletRequest){
		httpServletRequest.getSession().removeAttribute(FINAL_REDIRECT_URL_PARAM_ID);
	}
	protected String getAndRemoveFinalRedirectUrlFromSession(HttpServletRequest httpServletRequest){
		String redirectUrl = getFinalRedirectUrlFromSesion(httpServletRequest);
		clearFinalRedirectUrlInSesion(httpServletRequest);
		return redirectUrl; 
	}
	/*
	protected UserDto getLoggedInUser(HttpServletRequest httpServletRequest){
		return VotingSessionUtil.getUserFromSession(httpServletRequest);
	}
	*/
	protected String getCurrentUrl(HttpServletRequest httpServletRequest){
		return httpServletRequest.getRequestURL().toString();
	}

	protected void setLoggedInUserInSesion(HttpServletRequest httpServletRequest,UserDto user){
		httpServletRequest.getSession(true).setAttribute(SESSION_USER_PARAM, user);
	}
	public static UserDto getLoggedInUserFromSesion(HttpServletRequest httpServletRequest){
		return (UserDto)httpServletRequest.getSession(true).getAttribute(SESSION_USER_PARAM);
	}

	protected void setLoggedInAccountsInSesion(HttpServletRequest httpServletRequest,LoginAccountDto loginAccountDto){
		httpServletRequest.getSession(true).setAttribute(SESSION_LOGIN_ACCOUNT_PARAM, loginAccountDto);
	}
	public LoginAccountDto getLoggedInAccountsFromSesion(HttpServletRequest httpServletRequest){
		return (LoginAccountDto)httpServletRequest.getSession(true).getAttribute(SESSION_LOGIN_ACCOUNT_PARAM);
	}

	
	protected void setUserRolePermissionInSesion(HttpServletRequest httpServletRequest,UserRolePermissionDto userRolePermissionDto){
		httpServletRequest.getSession(true).setAttribute(SESSION_USER_PERMISSIONS_PARAM, userRolePermissionDto);
	}
	public UserRolePermissionDto getUserRolePermissionInSesion(HttpServletRequest httpServletRequest){
		return (UserRolePermissionDto)httpServletRequest.getSession(true).getAttribute(SESSION_USER_PERMISSIONS_PARAM);
	}

}
