package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
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

	@Override
	public List<Email> getEmailsByUserId(Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		return executeQueryGetList("from Email where userId = :userId", params);
	}

	
	@Override
	public List<Email> getStateEmails(Long stateId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("stateId", stateId);
		return executeQueryGetList("from Email where user.stateVotingId = :stateId or user.stateLivingId = :stateId", params);
	}

	@Override
	public List<Email> getDistrictEmails(Long districtId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("districtId", districtId);
		return executeQueryGetList("from Email where user.districtVotingId = :districtId or user.districtLivingId = :districtId", params);
	}

	@Override
	public List<Email> getAcEmails(Long acId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("acId", acId);
		return executeQueryGetList("from Email where user.assemblyConstituencyVotingId = :acId or user.assemblyConstituencyLivingId = :acId", params);
	}

	@Override
	public List<Email> getPcEmails(Long pcId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("pcId", pcId);
		return executeQueryGetList("from Email where user.parliamentConstituencyVotingId = :pcId or user.parliamentConstituencyLivingId = :pcId", params);
	}

	@Override
	public List<Email> getCountryEmails(Long nriCountryId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("nriCountryId", nriCountryId);
		return executeQueryGetList("from Email where user.nriCountryId = :nriCountryId", params);
	}

	@Override
	public List<Email> getCountryRegionEmails(Long nriCountryRegionId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("nriCountryRegionId", nriCountryRegionId);
		return executeQueryGetList("from Email where user.nriCountryRegionId = :nriCountryRegionId", params);
	}

	@Override
	public List<Email> getCountryRegionAreaEmails(Long nriCountryRegionAreaId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("nriCountryRegionAreaId", nriCountryRegionAreaId);
		return executeQueryGetList("from Email where user.nriCountryRegionAreaId = :nriCountryRegionAreaId", params);
	}
}
