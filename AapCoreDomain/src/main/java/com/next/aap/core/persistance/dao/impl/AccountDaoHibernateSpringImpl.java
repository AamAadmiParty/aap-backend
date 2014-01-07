package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.Account;
import com.next.aap.core.persistance.dao.AccountDao;
import com.next.aap.web.dto.AccountType;

@Component
public class AccountDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Account> implements AccountDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.AccountDao#saveAccount(com.next.aap.server.persistance.Account)
	 */
	@Override
	public Account saveAccount(Account account){
		checkIfObjectMissing("Account Type", account.getAccountType());
		if(account.getAccountType() == AccountType.TreasuryCash || account.getAccountType() == AccountType.TreasuryBank){
			if(account.getAccountOwner() != null){
				throw new RuntimeException("Treasury account can not have owner");
			}
			if(account.getState() == null && account.getDistrict() == null && account.getAssemblyConstituency() == null && account.getParliamentConstituency() == null && !account.isGlobal()){
				throw new RuntimeException("Treasury account must be associated with a location");
			}
		}
		if(account.getAccountType() == AccountType.Admin){
			checkIfObjectMissing("Account Owner", account.getAccountOwner());
			if(!(account.getState() == null && account.getDistrict() == null && account.getAssemblyConstituency() == null && account.getParliamentConstituency() == null && !account.isGlobal())){
				throw new RuntimeException("Admin account can not be associated with a location");
			}
		}
		account = super.saveObject(account);
		return account;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.AccountDao#getAccountById(java.lang.Long)
	 */
	@Override
	public Account getAccountById(Long id) {
		return super.getObjectById(Account.class, id);
	}

	@Override
	public Account getGlobalTreasuryAccount(AccountType accountType) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("global", true);
		params.put("accountType", accountType);
		Account account = executeQueryGetObject("from Account where global = :global and accountType = :accountType", params);
		return account;
	}

	@Override
	public Account getStateTreasuryAccount(long stateId, AccountType accountType) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("stateId", stateId);
		params.put("accountType", accountType);
		Account account = executeQueryGetObject("from Account where stateId = :stateId and accountType = :accountType", params);
		return account;
	}

	@Override
	public Account getDistrictTreasuryAccount(long districtId, AccountType accountType) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("districtId", districtId);
		params.put("accountType", accountType);
		Account account = executeQueryGetObject("from Account where districtId = :districtId and accountType = :accountType", params);
		return account;
	}

	@Override
	public Account getAcTreasuryAccount(long assemblyConstituencyId, AccountType accountType) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("assemblyConstituencyId", assemblyConstituencyId);
		params.put("accountType", accountType);
		Account account = executeQueryGetObject("from Account where assemblyConstituencyId = :assemblyConstituencyId and accountType = :accountType", params);
		return account;
	}

	@Override
	public Account getPcTreasuryAccount(long parliamentConstituencyId, AccountType accountType) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("parliamentConstituencyId", parliamentConstituencyId);
		params.put("accountType", accountType);
		Account account = executeQueryGetObject("from Account where parliamentConstituencyId = :parliamentConstituencyId and accountType = :accountType", params);
		return account;
	}

	@Override
	public Account getAdminAccountByUserId(Long accountOwnerId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("accountOwnerId", accountOwnerId);
		params.put("accountType", AccountType.Admin);
		Account account = executeQueryGetObject("from Account where accountOwnerId = :accountOwnerId and accountType = :accountType", params);
		return account;
	}

	@Override
	public List<Account> getAccountsByUserId(List<Long> userids) {
		if(userids == null || userids.isEmpty()){
			return null;
		}
		String query = "from Account where accountOwnerId in (:ids) order by balance desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", userids);
		List<Account> list = executeQueryGetList(query, queryParams);
		return list;
	}
}
