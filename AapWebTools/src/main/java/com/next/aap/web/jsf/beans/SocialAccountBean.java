package com.next.aap.web.jsf.beans;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.LoginAccountDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "socialAccountBean", beanName="socialAccountBean", pattern = "/socialaccounts", viewId = "/WEB-INF/jsf/socialaccounts.xhtml")
@URLBeanName("socialAccountBean")
public class SocialAccountBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private boolean renderFacebookConnectionButton = true;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		HttpServletRequest httpServletRequest =  getHttpServletRequest();
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
