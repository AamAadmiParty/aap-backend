package com.next.aap.core.persistance.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.Role;
import com.next.aap.core.persistance.dao.RoleDao;

@Repository
public class RoleDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Role> implements RoleDao{


	private static final long serialVersionUID = 1L;

	@Override
	public Role saveRole(Role role) {
		saveObject(role);
		return role;
	}

	@Override
	public void deleteRole(Role role) {
		deleteObject(role);
	}

	@Override
	public Role getRoleById(Long id) {
		return (Role)getObjectById(Role.class, id);
	}

	@Override
	public List<Role> getAllRoles(int totalItems, int pageNumber) {
		String query = "from Role order by dateCreated desc";
		List<Role> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public List<Role> getAllRoles() {
		String query = "from Role order by dateCreated desc";
		List<Role> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public List<Role> getRoleItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
		String query = "from Role where id > :lastId order by id asc";
		List<Role> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public Role getRoleByName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		String query = "from Role where name = :name";
		return  executeQueryGetObject(query, params);
	}

	@Override
	public List<Role> getUserGlobalRoles(long userId) {
		String sqlQuery = "select role_id from user_all_roles where user_id = :userId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("userId", userId);
		List<Long> roleIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(roleIds == null || roleIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Role where id in (:ids)";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", roleIds);
		List<Role> list = executeQueryGetList(query, queryParams);
		return list;
		
	}

	@Override
	public List<Role> getUserStateRoles(long userId, long stateId) {
		String sqlQuery = "select role_id from user_state_roles usr, state_role sr where usr.user_id = :userId and sr.state_id=:stateId and usr.state_role_id=sr.id";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("userId", userId);
		sqlQueryParams.put("stateId", stateId);
		List<Long> roleIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);

		if(roleIds == null || roleIds.isEmpty()){
			return new ArrayList<>();
		}
		String query = "from Role where id in (:ids)";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", roleIds);
		List<Role> list = executeQueryGetList(query, queryParams);
		return list;

	}

	@Override
	public List<Role> getUserDistrictRoles(long userId, long districtId) {
		String sqlQuery = "select role_id from user_district_roles udr, district_role dr where udr.user_id = :userId and dr.district_id=:districtId and udr.district_role_id=dr.id";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("userId", userId);
		sqlQueryParams.put("districtId", districtId);
		List<Long> roleIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);

		if(roleIds == null || roleIds.isEmpty()){
			return new ArrayList<>();
		}
		String query = "from Role where id in (:ids)";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", roleIds);
		List<Role> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Role> getUserAcRoles(long userId, long acId) {
		String sqlQuery = "select role_id from user_ac_roles uar, ac_role ar where uar.user_id = :userId and ar.ac_id=:acId and uar.ac_role_id=ar.id";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("userId", userId);
		sqlQueryParams.put("acId", acId);
		List<Long> roleIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);

		if(roleIds == null || roleIds.isEmpty()){
			return new ArrayList<>();
		}
		String query = "from Role where id in (:ids)";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", roleIds);
		List<Role> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Role> getUserPcRoles(long userId, long pcId) {
		String sqlQuery = "select role_id from user_pc_roles upr, pc_role pr where upr.user_id = :userId and pr.pc_id=:pcId and upr.pc_role_id=pr.id";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("userId", userId);
		sqlQueryParams.put("pcId", pcId);
		List<Long> roleIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);

		if(roleIds == null || roleIds.isEmpty()){
			return new ArrayList<>();
		}
		String query = "from Role where id in (:ids)";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", roleIds);
		List<Role> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Role> getGlobalRoles() {
		return getAllRoles();
	}

	@Override
	public List<Role> getStateRoles(long stateId) {
		String sqlQuery = "select sr.role_id from state_role sr where sr.state_id=:stateId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("stateId", stateId);
		List<Long> roleIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);

		if(roleIds == null || roleIds.isEmpty()){
			return new ArrayList<>();
		}
		String query = "from Role where id in (:ids)";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", roleIds);
		List<Role> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Role> getDistrictRoles(long districtId) {
		String sqlQuery = "select dr.role_id from district_role dr where dr.district_id=:districtId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("districtId", districtId);
		List<Long> roleIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);

		if(roleIds == null || roleIds.isEmpty()){
			return new ArrayList<>();
		}
		String query = "from Role where id in (:ids)";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", roleIds);
		List<Role> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Role> getAcRoles(long acId) {
		String sqlQuery = "select ar.role_id from ac_role ar where ar.ac_id=:acId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("acId", acId);
		List<Long> roleIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);

		if(roleIds == null || roleIds.isEmpty()){
			return new ArrayList<>();
		}
		String query = "from Role where id in (:ids)";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", roleIds);
		List<Role> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Role> getPcRoles(long pcId) {
		String sqlQuery = "select pr.role_id from pc_role pr where pr.pc_id=:pcId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(2);
		sqlQueryParams.put("pcId", pcId);
		List<Long> roleIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);

		if(roleIds == null || roleIds.isEmpty()){
			return new ArrayList<>();
		}
		String query = "from Role where id in (:ids)";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", roleIds);
		List<Role> list = executeQueryGetList(query, queryParams);
		return list;
	}

}
