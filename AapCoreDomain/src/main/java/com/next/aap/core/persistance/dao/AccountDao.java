package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Account;
import com.next.aap.web.dto.AccountType;

public interface AccountDao {

	public abstract Account saveAccount(Account account);

	public abstract Account getAccountById(Long id);

	public abstract Account getGlobalTreasuryAccount(AccountType accountType);
	
	public abstract Account getStateTreasuryAccount(long stateId, AccountType accountType);
	
	public abstract Account getDistrictTreasuryAccount(long districtId, AccountType accountType);
	
	public abstract Account getAcTreasuryAccount(long acId, AccountType accountType);
	
	public abstract Account getPcTreasuryAccount(long acId, AccountType accountType);
	
	public abstract Account getAdminAccountByUserId(Long userId);
	
	public abstract List<Account> getAccountsByUserId(List<Long> userids);

}