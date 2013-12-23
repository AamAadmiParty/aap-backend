package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.AcRole;
import com.next.aap.core.persistance.dao.AcRoleDao;

@Repository
public class AcRoleDaoHibernateSpringImpl extends BaseDaoHibernateSpring<AcRole> implements AcRoleDao{


	private static final long serialVersionUID = 1L;

	@Override
	public AcRole saveAcRole(AcRole acRole) {
		saveObject(acRole);
		return acRole;
	}

	@Override
	public void deleteAcRole(AcRole acRole) {
		deleteObject(acRole);
	}

	@Override
	public AcRole getAcRoleById(Long id) {
		return (AcRole)getObjectById(AcRole.class, id);
	}

	@Override
	public List<AcRole> getAllAcRoles(int totalItems, int pageNumber) {
		String query = "from AcRole order by dateCreated desc";
		List<AcRole> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public List<AcRole> getAllAcRoles() {
		String query = "from AcRole order by dateCreated desc";
		List<AcRole> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public List<AcRole> getAcRoleItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
		String query = "from AcRole where id > :lastId order by id asc";
		List<AcRole> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public AcRole getAcRoleByAcIdAndRoleId(Long assemblyConstituencyId, Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assemblyConstituencyId", assemblyConstituencyId);
		params.put("roleId", roleId);
		String query = "from AcRole where assemblyConstituencyId = :assemblyConstituencyId and roleId = :roleId";
		return  executeQueryGetObject(query, params);
	}

}
