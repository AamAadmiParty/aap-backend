package com.next.aap.web.jsf.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.AccountTransactionDto;
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
	
	private AdminAccountDto selectedAccount;
	
	private List<AdminAccountDto> accountList;
	
	private List<AccountTransactionDto> accountTransactions;
	
	private double amountToReceive;

	public TreasuryIncomingPanelBean(){
		super("/admin/treasury" , AppPermission.TREASURY);
	}
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		if(!checkUserAccess()){
			return;
		}
		refreshAccountList();
		
	}
	private void refreshAccountList(){
		accountList = aapService.getAdminAccountDetails(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
	}
	
	public void receiveMoney(){
		if(amountToReceive > selectedAccount.getBalance()){
			sendErrorMessageToJsfScreen("You can not accept more amount then "+selectedAccount.getBalance());
		}
		if(isValidInput()){
			try{
				UserDto loggedInTreasuryUser = getLoggedInUser();
				aapService.receiveMoneyIntoTreasuryAccount(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId(), loggedInTreasuryUser.getId(), amountToReceive, selectedAccount.getAccountOwnerDto().getId());
				refreshAccountList();
			}catch(Exception ex){
				sendErrorMessageToJsfScreen(ex.getMessage());
			}
		}
		
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
	public AdminAccountDto getSelectedAccount() {
		System.out.println("getSelectedAccount = "+selectedAccount);
		return selectedAccount;
	}
	public void setSelectedAccount(AdminAccountDto selectedAccount) {
		System.out.println("selected Account = "+selectedAccount);
		this.selectedAccount = selectedAccount;
		amountToReceive = selectedAccount.getBalance();
	}
	public void setSelectedAccountForDetail(AdminAccountDto selectedAccount) {
		System.out.println("selected Account = "+selectedAccount);
		this.selectedAccount = selectedAccount;
		accountTransactions = aapService.getAccountTransactions(selectedAccount.getId());
	}
	public double getAmountToReceive() {
		return amountToReceive;
	}
	public void setAmountToReceive(double amountToReceive) {
		this.amountToReceive = amountToReceive;
	}
	public List<AccountTransactionDto> getAccountTransactions() {
		return accountTransactions;
	}
	public void setAccountTransactions(List<AccountTransactionDto> accountTransactions) {
		this.accountTransactions = accountTransactions;
	}
	


}
