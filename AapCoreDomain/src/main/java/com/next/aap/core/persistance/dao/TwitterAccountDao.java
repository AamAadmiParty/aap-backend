package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.TwitterAccount;

public interface TwitterAccountDao {

	public abstract TwitterAccount saveTwitterAccount(
			TwitterAccount twitterAccount);

	public abstract TwitterAccount getTwitterAccountById(Long id);

	public abstract TwitterAccount getTwitterAccountByUserId(Long userId);

	public abstract TwitterAccount getTwitterAccountByHandle(String userName);
	
	public abstract TwitterAccount getTwitterAccountByTwitterUserId(String twitterUserId);

	public abstract List<TwitterAccount> getTwitterAccountsAfterId(
			Long lastId, int pageSize);

}