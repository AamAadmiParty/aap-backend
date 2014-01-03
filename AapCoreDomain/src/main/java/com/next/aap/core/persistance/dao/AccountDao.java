package com.next.aap.core.persistance.dao;

import com.next.aap.core.persistance.Account;

public interface AccountDao {

	public abstract Account saveAccount(Account account);

	public abstract Account getAccountById(Long id);

	public abstract Account getGlobalTreasuryAccount();
	
	public abstract Account getStateTreasuryAccount(long stateId);
	
	public abstract Account getDistrictTreasuryAccount(long districtId);
	
	public abstract Account getAcTreasuryAccount(long acId);
	
	public abstract Account getPcTreasuryAccount(long acId);
	
	public abstract Account getAdminAccountByUserId(Long userId);

}