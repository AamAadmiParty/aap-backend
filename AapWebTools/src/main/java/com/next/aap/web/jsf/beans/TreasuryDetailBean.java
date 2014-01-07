package com.next.aap.web.jsf.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.AccountTransactionDto;
import com.next.aap.web.dto.AppPermission;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "treasuryDetailBean", beanName="treasuryDetailBean", pattern = "/admin/treasurydetail", viewId = "/WEB-INF/jsf/treasury_detail.xhtml")
@URLBeanName("treasuryDetailBean")
public class TreasuryDetailBean extends BaseMultiPermissionAdminJsfBean {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MenuBean menuBean;
	
	private List<AccountTransactionDto> bankAccountTransactions;
	
	private List<AccountTransactionDto> cashAccountTransactions;
	
	public TreasuryDetailBean(){
		super("/admin/treasurydetail" , AppPermission.TREASURY);
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
		bankAccountTransactions = aapService.getTreasuryBankAccountTransactions(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
		cashAccountTransactions = aapService.getTreasuryCashAccountTransactions(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
	}
	public double getCashBalance(){
		if(cashAccountTransactions != null && !cashAccountTransactions.isEmpty()){
			return cashAccountTransactions.get(0).getBalance();
		}
		return 0.0;
	}
	public double getBankBalance(){
		if(bankAccountTransactions != null && !bankAccountTransactions.isEmpty()){
			return bankAccountTransactions.get(0).getBalance();
		}
		return 0.0;
	}
	public List<AccountTransactionDto> getBankAccountTransactions() {
		return bankAccountTransactions;
	}
	public void setBankAccountTransactions(List<AccountTransactionDto> bankAccountTransactions) {
		this.bankAccountTransactions = bankAccountTransactions;
	}
	public List<AccountTransactionDto> getCashAccountTransactions() {
		return cashAccountTransactions;
	}
	public void setCashAccountTransactions(List<AccountTransactionDto> cashAccountTransactions) {
		this.cashAccountTransactions = cashAccountTransactions;
	}
	

}
