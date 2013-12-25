package com.next.aap.web.jsf.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "adminPanelBean", beanName="adminPanelBean", pattern = "/adminpanel", viewId = "/WEB-INF/jsf/admin_panel.xhtml")
@URLBeanName("adminPanelBean")
public class AdminPanelBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true,buildLoginUrl("/adminpanel"));
		if(loggedInUser == null){
			return;
		}
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}


}
