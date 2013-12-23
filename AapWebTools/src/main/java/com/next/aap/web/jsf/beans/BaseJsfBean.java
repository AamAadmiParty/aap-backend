package com.next.aap.web.jsf.beans;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.AccessDeniedException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.controller.BaseController;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;

public class BaseJsfBean extends BaseController implements Serializable {

	public static HttpServletRequest getHttpServletRequest() {
		HttpServletRequest h;
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public void redirect(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void buildAndRedirect(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(buildUrl(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void setFinalRedirectUrlInSesion(String url){
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		setFinalRedirectUrlInSesion(httpServletRequest, url);
	}

	protected void ssaveLoggedInUserInSession(UserDto user) {
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		super.setLoggedInUserInSesion(httpServletRequest, user);
	}

	public LoginAccountDto getLoggedInAccountsFromSesion(){
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		return getLoggedInAccountsFromSesion(httpServletRequest);
	}
	protected UserDto getLoggedInUser() {
		return getLoggedInUser(false, "");
	}

	protected UserDto getLoggedInUser(boolean redirect, String url) {
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		UserDto user = getLoggedInUserFromSesion(httpServletRequest);
		if (user == null) {
			if (redirect) {
				redirect(url);
			}
		}
		return user;
	}

	public static String buildLoginUrl(String redirectUrl) {
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		return buildLoginUrl(httpServletRequest, redirectUrl);
	}

	public static String buildLoginUrl(HttpServletRequest httpServletRequest, String redirectUrl) {
		return httpServletRequest.getContextPath() + "/socialaccounts?" + REDIRECT_URL_PARAM_ID + "=" + httpServletRequest.getContextPath() + redirectUrl;
	}

	public static String buildUrl(String url) {
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		return httpServletRequest.getContextPath() + url;
	}

	protected void sendErrorMessageToJsfScreen(String componentId, Exception e) {
		sendErrorMessageToJsfScreen(componentId, null, e);
	}

	protected void sendErrorMessageToJsfScreen(String componentId, String message) {
		sendErrorMessageToJsfScreen(componentId, message, null);
	}

	protected void sendErrorMessageToJsfScreen(Exception e) {
		sendErrorMessageToJsfScreen(null, null, e);
	}

	protected void sendErrorMessageToJsfScreen(String message) {
		sendErrorMessageToJsfScreen(null, message, null);
	}

	protected void sendErrorMessageToJsfScreen(String componentId, String message, Exception e) {
		if (e != null) {
			e.printStackTrace();
		}
		if (StringUtil.isEmptyOrWhitespace(message)) {
			if (e instanceof AccessDeniedException) {
				FacesContext.getCurrentInstance().addMessage(componentId,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are not allowed for this operation", e.getMessage()));
			} else {
				FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
			}
		} else {
			if (e == null) {
				FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));

			} else {
				FacesContext.getCurrentInstance().addMessage(componentId,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, message + " : " + e.getMessage(), message + " : " + e.getMessage()));

			}

		}
	}

	protected void sendInfoMessageToJsfScreen(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));

	}

	protected boolean isValidInput() {
		if (FacesContext.getCurrentInstance().getMessageList().size() > 0) {
			return false;
		}
		return true;

	}

}