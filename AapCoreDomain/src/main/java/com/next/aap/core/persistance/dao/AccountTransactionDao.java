package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.AccountTransaction;

public interface AccountTransactionDao {

	public abstract AccountTransaction saveAccountTransaction(AccountTransaction accountTransaction);

	public abstract AccountTransaction getAccountTransactionById(Long id);

	public abstract List<AccountTransaction> getAccountTransactionsByAccountId(Long accountId);

	public abstract List<AccountTransaction> getAccountTransactionsAfterId(Long lastId, int pageSize);

}