package com.next.aap.web.jsf.beans;

import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.util.CookieUtil;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean
//@Scope("session")
@ViewScoped
//@URLMapping(id = "socialAccountBean", beanName="socialAccountBean", pattern = "/socialaccounts", viewId = "/WEB-INF/jsf/socialaccounts.xhtml")
@URLMappings(mappings={
		@URLMapping(id = "socialAccountBean1", beanName="socialAccountBean", pattern = "/orig/socialaccounts", viewId = "/WEB-INF/jsf/socialaccounts.xhtml"),
		@URLMapping(id = "socialAccountBean2", beanName="socialAccountbean", pattern = "/aapstyle/socialaccounts", viewId = "/WEB-INF/jsf/aapstyle/socialaccounts.xhtml"),
		@URLMapping(id = "socialAccountBean3", beanName="socialAccountbean", pattern = "/socialaccounts", viewId = "/WEB-INF/jsf/aapnewstyle/socialaccounts.xhtml")
		})
@URLBeanName("socialAccountBean")
public class SocialAccountBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private boolean renderFacebookConnectionButton = true;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		HttpServletRequest httpServletRequest =  getHttpServletRequest();
		/*
		String passedRedirectUrl = getRedirectUrl(httpServletRequest);
		
		if(passedRedirectUrl != null){
			if(CookieUtil.isLastLoggedInViaFacebook(httpServletRequest)){
				redirectUrlAfterLogin = passedRedirectUrl;
				buildAndRedirect("/login/facebook");
				return;
			}else if(CookieUtil.isLastLoggedInViaTwitter(httpServletRequest)){
				redirectUrlAfterLogin = passedRedirectUrl;
				buildAndRedirect("/login/twitter");
				return;
			}
		}
		*/
		String redirectUrlAfterLogin = httpServletRequest.getContextPath()+"/socialaccounts";
		setRedirectUrlInSessiom(httpServletRequest, redirectUrlAfterLogin);
		
		LoginAccountDto loginAccounts = getLoggedInAccountsFromSesion();
		if(loginAccounts == null || loginAccounts.getFacebookAccounts() == null || loginAccounts.getFacebookAccounts().isEmpty()){
			renderFacebookConnectionButton = true;
		}else{
			renderFacebookConnectionButton = false;
		}
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public boolean isRenderFacebookConnectionButton() {
		return renderFacebookConnectionButton;
	}

	public void setRenderFacebookConnectionButton(boolean renderFacebookConnectionButton) {
		this.renderFacebookConnectionButton = renderFacebookConnectionButton;
	}

	

}
