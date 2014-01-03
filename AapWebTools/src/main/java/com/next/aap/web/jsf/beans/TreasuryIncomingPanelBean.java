package com.next.aap.web.jsf.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.AdminAccountDto;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "treasuryIncomingPanelBean", beanName="treasuryIncomingPanelBean", pattern = "/admin/treasury", viewId = "/WEB-INF/jsf/treasury_incoming.xhtml")
@URLBeanName("treasuryIncomingPanelBean")
public class TreasuryIncomingPanelBean extends BaseMultiPermissionAdminJsfBean {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MenuBean menuBean;
	
	private List<AdminAccountDto> accountList;

	public TreasuryIncomingPanelBean(){
		super("/admin/treasury" , AppPermission.TREASURY);
	}
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		if(!checkUserAccess()){
			return;
		}
		accountList = aapService.getAdminAccountDetails(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
		
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}

	public List<AdminAccountDto> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AdminAccountDto> accountList) {
		this.accountList = accountList;
	}


}
