package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Email;

public interface EmailDao {

	public abstract Email saveEmail(Email email);

	public abstract Email getEmailById(Long id);


	public abstract Email getEmailByEmail(String email);
	
	public abstract List<Email> getEmailsByUserId(Long userId);

	public abstract List<Email> getEmailsAfterId(Long lastId, int pageSize);

}