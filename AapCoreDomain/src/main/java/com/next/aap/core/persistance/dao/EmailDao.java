package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Email;

public interface EmailDao {

	public abstract Email saveEmail(Email email);

	public abstract Email getEmailById(Long id);

	public abstract Email getEmailByEmail(String email);
	
	public abstract List<Email> getEmailsByUserId(Long userId);

	public abstract List<Email> getEmailsAfterId(Long lastId, int pageSize);
	
	public abstract List<Email> getStateEmails(Long stateId);
	
	public abstract List<Email> getDistrictEmails(Long districtId);
	
	public abstract List<Email> getAcEmails(Long acId);
	
	public abstract List<Email> getPcEmails(Long pcId);
	
	public abstract List<Email> getCountryEmails(Long countryId);
	
	public abstract List<Email> getCountryRegionEmails(Long countryRegionId);
	
	public abstract List<Email> getCountryRegionAreaEmails(Long countryRegionAreaId);

}