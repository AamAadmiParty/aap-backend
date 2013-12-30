package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.persistance.User;
import com.next.aap.core.persistance.dao.UserDao;

@Repository
public class UserDaoHibernateSpringImpl extends BaseDaoHibernateSpring<User> implements UserDao {

	private static final long serialVersionUID = 1L;

	@Override
	public User saveUser(User user) {
		if(user.getPassportNumber() != null){
			user.setPassportNumber(user.getPassportNumber().toUpperCase());
		}
		if(StringUtil.isEmpty(user.getExternalId())){
			user.setExternalId(UUID.randomUUID().toString());
		}
		user = saveObject(user);
		assignMembershipNumber(user);
		return user;
	}

	private void assignMembershipNumber(User user) {
		System.out.println("user.isMember()=" + user.isMember());
		System.out.println("StringUtil.isEmpty(user.getMembershipNumber())=" + StringUtil.isEmpty(user.getMembershipNumber()));
		if (user.isMember()) {
			if (StringUtil.isEmpty(user.getMembershipNumber())) {
				String membershipNumber = "AAP" + ensureDigits(user.getId(), 6);
				if (user.getName().indexOf(" ") >= 0) {
					String names[] = user.getName().split(" ");
					membershipNumber = membershipNumber + names[0].substring(0, 1) + names[names.length - 1].substring(0, 1);
				} else {
					if (user.getName().length() > 1) {
						membershipNumber = membershipNumber + user.getName().substring(0, 2);
					} else {
						membershipNumber = membershipNumber + user.getName();
					}
				}
				membershipNumber = membershipNumber.toUpperCase();
				user.setMembershipNumber(membershipNumber);
			}
			if (StringUtil.isEmpty(user.getMembershipStatus())) {
				user.setMembershipStatus("Payment Await");
			}
		}
	}

	private String ensureDigits(Long id, int digits) {
		String idString = id.toString();
		while (idString.length() < digits) {
			idString = "0" + idString;
		}
		return idString;
	}

	@Override
	public void deleteUser(User user) {
		getCurrentSession().delete(user);
	}

	@Override
	public User getUserById(Long id) {
		return (User) getObjectById(User.class, id);
	}

	@Override
	public List<User> searchUsers() {
		return getAll(User.class);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(String userEmail) {
		if (userEmail == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("emailUp", userEmail.toUpperCase());
		String query = "from User where emailUp=:emailUp";
		return executeQueryGetObject(query, params);
	}

	@Override
	public User getUserByMembershipNumber(String membershipNumber) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("membershipNumber", membershipNumber.toUpperCase());
		String query = "from User where membershipNumber=:membershipNumber";
		return executeQueryGetObject(query, params);
	}

	@Override
	public User getUserByPassportNumber(String passportNumber) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("passportNumber", passportNumber.toUpperCase());
		String query = "from User where passportNumber=:passportNumber";
		return executeQueryGetObject(query, params);
	}

	@Override
	public List<User> searchUserOfAssemblyConstituency(String name, Long livingAcId, Long votingAcId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%" +name.toUpperCase()+"%");
		params.put("livingAcId", livingAcId);
		params.put("votingAcId", votingAcId);
		String query = "from User where (assemblyConstituencyLivingId = :livingAcId or assemblyConstituencyVotingId = :votingAcId) and UPPER(name) like :name";
		return executeQueryGetList(query, params);
	}
		
}
