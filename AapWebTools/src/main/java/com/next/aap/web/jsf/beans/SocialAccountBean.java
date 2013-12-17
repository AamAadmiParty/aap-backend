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

	private LoginAccountDto loginAccounts;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction
	public void init() throws Exception {
		HttpServletRequest httpServletRequest =  getHttpServletRequest();
		String redirectUrlAfterLogin = httpServletRequest.getContextPath()+"/socialaccounts";
		setRedirectUrlInSessiom(httpServletRequest, redirectUrlAfterLogin);
	}

	public LoginAccountDto getLoginAccounts() {
		loginAccounts = getLoggedInAccountsFromSesion();
		return loginAccounts;
	}

	public void setLoginAccounts(LoginAccountDto loginAccounts) {
		this.loginAccounts = loginAccounts;
	}

	

}
