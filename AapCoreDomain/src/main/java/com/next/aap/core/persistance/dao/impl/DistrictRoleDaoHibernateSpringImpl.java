package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.DistrictRole;
import com.next.aap.core.persistance.dao.DistrictRoleDao;

@Repository
public class DistrictRoleDaoHibernateSpringImpl extends BaseDaoHibernateSpring<DistrictRole> implements DistrictRoleDao{


	private static final long serialVersionUID = 1L;

	@Override
	public DistrictRole saveDistrictRole(DistrictRole districtRole) {
		saveObject(districtRole);
		return districtRole;
	}

	@Override
	public void deleteDistrictRole(DistrictRole districtRole) {
		deleteObject(districtRole);
	}

	@Override
	public DistrictRole getDistrictRoleById(Long id) {
		return (DistrictRole)getObjectById(DistrictRole.class, id);
	}

	@Override
	public List<DistrictRole> getAllDistrictRoles(int totalItems, int pageNumber) {
		String query = "from DistrictRole order by dateCreated desc";
		List<DistrictRole> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public List<DistrictRole> getAllDistrictRoles() {
		String query = "from DistrictRole order by dateCreated desc";
		List<DistrictRole> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public List<DistrictRole> getDistrictRoleItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
		String query = "from DistrictRole where id > :lastId order by id asc";
		List<DistrictRole> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public DistrictRole getDistrictRoleByDistrictIdAndRoleId(Long districtId, Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("districtId", districtId);
		params.put("roleId", roleId);
		String query = "from DistrictRole where districtId = :districtId and roleId = :roleId";
		return  executeQueryGetObject(query, params);
	}

}
