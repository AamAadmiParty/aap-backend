package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.FacebookAccount;

public interface FacebookAccountDao {

	public abstract FacebookAccount saveFacebookAccount(
			FacebookAccount facebookAccount);

	public abstract FacebookAccount getFacebookAccountById(Long id);

	public abstract FacebookAccount getFacebookAccountByUserId(Long userId);
	
	public abstract FacebookAccount getFacebookAccountByFacebookUserId(String userId);

	public abstract FacebookAccount getFacebookAccountByUserName(String userName);

	public abstract List<FacebookAccount> getFacebookAccountsAfterId(
			Long lastId, int pageSize);

}