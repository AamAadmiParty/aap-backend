package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.Phone;
import com.next.aap.core.persistance.dao.PhoneDao;

@Component
public class PhoneDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Phone> implements PhoneDao{


	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.PhoneDao#savePhone(com.next.aap.server.persistance.Phone)
	 */
	@Override
	public Phone savePhone(Phone phone){
		checkIfStringMissing("phone", phone.getPhoneNumber());
		checkIfStringMissing("countryCode", phone.getCountryCode());
		phone = super.saveObject(phone);
		return phone;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.PhoneDao#getPhoneById(java.lang.Long)
	 */
	@Override
	public Phone getPhoneById(Long id) {
		return super.getObjectById(Phone.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.PhoneDao#getPhonesAfterId(java.lang.Long, int)
	 */
	@Override
	public List<Phone> getPhonesAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		params.put("pageSize", pageSize);
		List<Phone> list = executeQueryGetList("from Phone where id > :lastId order by id asc limit :pageSize");
		return list;
	}

	@Override
	public Phone getPhoneByPhone(String phoneId, String countryCode) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("phone", phoneId);
		params.put("countryCode", countryCode);
		Phone phone = executeQueryGetObject("from Phone where phoneNumber = :phone and countryCode = :countryCode", params);
		return phone;
	}

	@Override
	public List<Phone> getPhonesOfUser(Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId",userId);
		return executeQueryGetList("from Phone where userId = :userId", params);
	}
}
