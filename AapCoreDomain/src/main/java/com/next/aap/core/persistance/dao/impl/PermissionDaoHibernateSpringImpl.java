package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.Permission;
import com.next.aap.core.persistance.dao.PermissionDao;
import com.next.aap.web.dto.AppPermission;

@Repository
public class PermissionDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Permission> implements PermissionDao{


	private static final long serialVersionUID = 1L;

	@Override
	public Permission savePermission(Permission permission) {
		saveObject(permission);
		return permission;
	}

	@Override
	public void deletePermission(Permission permission) {
		deleteObject(permission);
	}

	@Override
	public Permission getPermissionById(Long id) {
		return (Permission)getObjectById(Permission.class, id);
	}

	@Override
	public List<Permission> getAllPermissions(int totalItems, int pageNumber) {
		String query = "from Permission order by dateCreated desc";
		List<Permission> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public List<Permission> getAllPermissions() {
		String query = "from Permission order by dateCreated desc";
		List<Permission> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public List<Permission> getPermissionItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
		String query = "from Permission where id > :lastId order by id asc";
		List<Permission> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public Permission getPermissionByName(AppPermission name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("permission", name);
		String query = "from Permission where permission = :permission";
		return  executeQueryGetObject(query, params);
	}

}
