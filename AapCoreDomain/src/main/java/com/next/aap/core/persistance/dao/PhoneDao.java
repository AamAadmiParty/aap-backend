package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Phone;

public interface PhoneDao {

	public abstract Phone savePhone(Phone phone);

	public abstract Phone getPhoneById(Long id);

	public abstract Phone getPhoneByPhone(String phone, String countryCode);
	
	public abstract List<Phone> getPhonesAfterId(Long lastId, int pageSize);
	
	public abstract List<Phone> getPhonesOfUser(Long userId);

}