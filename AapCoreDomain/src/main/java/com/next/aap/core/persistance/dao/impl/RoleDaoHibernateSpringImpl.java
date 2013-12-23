package com.next.aap.core.persistance.dao.impl;

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

}
