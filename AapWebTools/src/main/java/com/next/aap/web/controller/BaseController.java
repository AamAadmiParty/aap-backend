package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;

public class BaseController {

	public static final String REDIRECT_URL_PARAM_ID = "v4d_redirect_url";
	
	public static final String FINAL_REDIRECT_URL_PARAM_ID = "fru";
	
	public static final String defaultVersion = "v2";
	
	public static final String SESSION_USER_PARAM = "SESSION_USER_PARAM";
	public static final String SESSION_LOGIN_ACCOUNT_PARAM = "SESSION_LOGIN_ACCOUNT_PARAM";

	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected AapService aapService;

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
		if(redirectUrl == null){
			redirectUrl = "http://localhost:8081/vote/ac/delhi/adarsh_nagar";
		}
        RedirectView rv = new RedirectView(redirectUrl);
        rv.setExposeModelAttributes(false);
        mv.setView(rv);
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
		httpServletRequest.getSession(true).setAttribute(FINAL_REDIRECT_URL_PARAM_ID, url);
	}
	public static String getFinalRedirectUrlFromSesion(HttpServletRequest httpServletRequest){
		return (String)httpServletRequest.getSession(true).getAttribute(FINAL_REDIRECT_URL_PARAM_ID);
	}
	public static void clearFinalRedirectUrlInSesion(HttpServletRequest httpServletRequest){
		httpServletRequest.getSession(true).removeAttribute(FINAL_REDIRECT_URL_PARAM_ID);
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
	public static UserDto getLoggedInUserInSesion(HttpServletRequest httpServletRequest){
		return (UserDto)httpServletRequest.getSession(true).getAttribute(SESSION_USER_PARAM);
	}

	protected void setLoggedInAccountsInSesion(HttpServletRequest httpServletRequest,LoginAccountDto loginAccountDto){
		httpServletRequest.getSession(true).setAttribute(SESSION_LOGIN_ACCOUNT_PARAM, loginAccountDto);
	}
	public static LoginAccountDto getLoggedInAccountsInSesion(HttpServletRequest httpServletRequest){
		return (LoginAccountDto)httpServletRequest.getSession(true).getAttribute(SESSION_LOGIN_ACCOUNT_PARAM);
	}

}
