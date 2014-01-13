package com.next.aap.core.persistance.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.persistance.User;
import com.next.aap.core.persistance.dao.UserDao;
import com.next.aap.web.dto.AccountType;

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

	@Override
	public List<Long> getAllAdminUserForGlobalTreasur() {
		String sqlQuery = "select distinct uar.user_id as user_id from accounts a, user_all_roles uar where a.account_owner_id = uar.user_id and a.account_type = :accountType " +
				" union " +
				"select distinct uar.user_id as user_id from accounts a, user_state_roles uar , state_role sr where a.account_owner_id = uar.user_id and uar.state_role_id = sr.id and a.account_type = :accountType" +
				" union " +
				"select distinct uar.user_id from accounts a, user_district_roles uar , district_role dr where a.account_owner_id = uar.user_id and uar.district_role_id = dr.id and a.account_type = :accountType" +
				" union " +
				"select distinct uar.user_id as user_id from accounts a, user_ac_roles uar , ac_role ar where a.account_owner_id = uar.user_id and uar.ac_role_id = ar.id  and a.account_type = :accountType " +
				" union " +
				"select distinct uar.user_id from accounts a, user_pc_roles uar , pc_role pr where a.account_owner_id = uar.user_id and uar.pc_role_id = pr.id and a.account_type = :accountType";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("accountType", AccountType.Admin.toString());
		List<Long> userIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(userIds == null){
			userIds =  new ArrayList<>();
		}
		return userIds;

	}

	@Override
	public List<Long> getAllAdminUserForStateTreasure(long stateId) {
		String sqlQuery = "select distinct uar.user_id as user_id from accounts a, user_state_roles uar , state_role sr where a.account_owner_id = uar.user_id and uar.state_role_id = sr.id and sr.state_id = :stateId and a.account_type = :accountType" +
				" union " +
				"select distinct uar.user_id from accounts a, user_district_roles uar , district_role dr where a.account_owner_id = uar.user_id and uar.district_role_id = dr.id and dr.district_id in (select id from districts where state_id = :stateId) and a.account_type = :accountType" +
				" union " +
				"select distinct uar.user_id as user_id from accounts a, user_ac_roles uar , ac_role ar where a.account_owner_id = uar.user_id and uar.ac_role_id = ar.id and ar.ac_id in (select id from assembly_constituency where district_id in (select id from districts where state_id = :stateId))  and a.account_type = :accountType " +
				" union " +
				"select distinct uar.user_id from accounts a, user_pc_roles uar , pc_role pr where a.account_owner_id = uar.user_id and uar.pc_role_id = pr.id and pr.pc_id in (select id from parliament_constituency where state_id = :stateId) and a.account_type = :accountType";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("stateId", stateId);
		sqlQueryParams.put("accountType", AccountType.Admin.toString());
		List<Long> userIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(userIds == null){
			userIds =  new ArrayList<>();
		}
		return userIds;
	}

	@Override
	public List<Long> getAllAdminUserForDistrictTreasure(long districtId) {
		String sqlQuery = "select distinct uar.user_id from accounts a, user_district_roles uar , district_role dr where a.account_owner_id = uar.user_id and uar.district_role_id = dr.id and dr.district_id = :districtId and a.account_type = :accountType" +
				" union " +
				"select distinct uar.user_id as user_id from accounts a, user_ac_roles uar , ac_role ar where a.account_owner_id = uar.user_id and uar.ac_role_id = ar.id and ar.ac_id in (select id from assembly_constituency where district_id = :districtId)  and a.account_type = :accountType ";

		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("districtId", districtId);
		sqlQueryParams.put("accountType", AccountType.Admin.toString());
		List<Long> userIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(userIds == null){
			userIds =  new ArrayList<>();
		}
		return userIds;
	}

	@Override
	public List<Long> getAllAdminUserForAcTreasure(long acId) {
		String sqlQuery = "select distinct uar.user_id from accounts a, user_ac_roles uar , ac_role sr " +
				"where a.account_owner_id = uar.user_id and uar.ac_role_id = sr.id and sr.ac_id = :acId " +
				" and a.account_type = :accountType";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("acId", acId);
		sqlQueryParams.put("accountType", AccountType.Admin.toString());
		List<Long> userIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(userIds == null){
			userIds =  new ArrayList<>();
		}
		return userIds;
	}

	@Override
	public List<Long> getAllAdminUserForPcTreasure(long pcId) {
		String sqlQuery = "select distinct uar.user_id from accounts a, user_pc_roles uar , pc_role sr " +
				"where a.account_owner_id = uar.user_id and uar.pc_role_id = sr.id and sr.pc_id = :pcId " +
				" and a.account_type = :accountType";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("pcId", pcId);
		sqlQueryParams.put("accountType", AccountType.Admin.toString());
		List<Long> userIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(userIds == null){
			userIds =  new ArrayList<>();
		}
		return userIds;
	}
		
	
	@Override
	public List<Long> getAdminUserForGlobalTreasur() {
		String sqlQuery = "select distinct uar.user_id from accounts a, user_all_roles uar where a.account_owner_id = uar.user_id and a.account_type = :accountType";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("accountType", AccountType.Admin.toString());
		List<Long> userIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(userIds == null){
			userIds =  new ArrayList<>();
		}
		return userIds;

	}

	@Override
	public List<Long> getAdminUserForStateTreasure(long stateId) {
		String sqlQuery = "select distinct uar.user_id from accounts a, user_state_roles uar , state_role sr " +
				"where a.account_owner_id = uar.user_id and uar.state_role_id = sr.id and sr.state_id = :stateId " +
				"  and a.account_type = :accountType";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("stateId", stateId);
		sqlQueryParams.put("accountType", AccountType.Admin.toString());
		List<Long> userIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(userIds == null){
			userIds =  new ArrayList<>();
		}
		return userIds;
	}

	@Override
	public List<Long> getAdminUserForDistrictTreasure(long districtId) {
		String sqlQuery = "select distinct uar.user_id from accounts a, user_district_roles uar , district_role sr " +
				"where a.account_owner_id = uar.user_id and uar.district_role_id = sr.id and sr.district_id = :districtId " +
				" and a.account_type = :accountType";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("districtId", districtId);
		sqlQueryParams.put("accountType", AccountType.Admin.toString());
		List<Long> userIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(userIds == null){
			userIds =  new ArrayList<>();
		}
		return userIds;
	}

	@Override
	public List<Long> getAdminUserForAcTreasure(long acId) {
		String sqlQuery = "select distinct uar.user_id from accounts a, user_ac_roles uar , ac_role sr " +
				"where a.account_owner_id = uar.user_id and uar.ac_role_id = sr.id and sr.ac_id = :acId " +
				" and a.account_type = :accountType";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("acId", acId);
		sqlQueryParams.put("accountType", AccountType.Admin.toString());
		List<Long> userIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(userIds == null){
			userIds =  new ArrayList<>();
		}
		return userIds;
	}

	@Override
	public List<Long> getAdminUserForPcTreasure(long pcId) {
		String sqlQuery = "select distinct uar.user_id from accounts a, user_pc_roles uar , pc_role sr " +
				"where a.account_owner_id = uar.user_id and uar.pc_role_id = sr.id and sr.pc_id = :pcId " +
				" and a.account_type = :accountType";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("pcId", pcId);
		sqlQueryParams.put("accountType", AccountType.Admin.toString());
		List<Long> userIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(userIds == null){
			userIds =  new ArrayList<>();
		}
		return userIds;
	}
}
