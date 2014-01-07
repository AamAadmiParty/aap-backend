package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.AccountTransaction;
import com.next.aap.core.persistance.dao.AccountTransactionDao;

@Component
public class AccountTransactionDaoHibernateSpringImpl extends BaseDaoHibernateSpring<AccountTransaction> implements AccountTransactionDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.AccountTransactionDao#saveAccountTransaction(com.next.aap.server.persistance.AccountTransaction)
	 */
	@Override
	public AccountTransaction saveAccountTransaction(AccountTransaction accountTransaction){
		checkIfObjectMissing("account", accountTransaction.getAccount());
		checkIfObjectMissing("Account Transaction Mode", accountTransaction.getAccountTransactionMode());
		checkIfObjectMissing("Account Transaction Type", accountTransaction.getAccountTransactionType());
		checkIfObjectMissing("Amount", accountTransaction.getAmount());
		checkIfObjectMissing("Balance", accountTransaction.getBalance());
		checkIfObjectMissing("Transaction Date", accountTransaction.getTransactionDate());
		accountTransaction = super.saveObject(accountTransaction);
		return accountTransaction;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.AccountTransactionDao#getAccountTransactionById(java.lang.Long)
	 */
	@Override
	public AccountTransaction getAccountTransactionById(Long id) {
		return super.getObjectById(AccountTransaction.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.AccountTransactionDao#getAccountTransactionsAfterId(java.lang.Long, int)
	 */
	@Override
	public List<AccountTransaction> getAccountTransactionsAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		params.put("pageSize", pageSize);
		List<AccountTransaction> list = executeQueryGetList("from AccountTransaction where id > :lastId order by id asc limit :pageSize");
		return list;
	}

	@Override
	public List<AccountTransaction> getAccountTransactionsByAccountId(Long accountId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("accountId", accountId);
		return executeQueryGetList("from AccountTransaction where accountId = :accountId order by dateCreated desc, id desc", params);
	}
}
