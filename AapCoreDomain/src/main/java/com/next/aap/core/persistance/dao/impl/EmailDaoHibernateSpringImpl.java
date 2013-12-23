package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.Email;
import com.next.aap.core.persistance.dao.EmailDao;

@Component
public class EmailDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Email> implements EmailDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.EmailDao#saveEmail(com.next.aap.server.persistance.Email)
	 */
	@Override
	public Email saveEmail(Email email){
		checkIfStringMissing("email", email.getEmail());
		email.setEmailUp(email.getEmail().toUpperCase());
		email = super.saveObject(email);
		return email;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.EmailDao#getEmailById(java.lang.Long)
	 */
	@Override
	public Email getEmailById(Long id) {
		return super.getObjectById(Email.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.EmailDao#getEmailsAfterId(java.lang.Long, int)
	 */
	@Override
	public List<Email> getEmailsAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		params.put("pageSize", pageSize);
		List<Email> list = executeQueryGetList("from Email where id > :lastId order by id asc limit :pageSize");
		return list;
	}

	@Override
	public Email getEmailByEmail(String emailId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("email", emailId.toUpperCase());
		Email email = executeQueryGetObject("from Email where emailUp = :email", params);
		return email;
	}
}
