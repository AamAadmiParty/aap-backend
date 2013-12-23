package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.PcRole;
import com.next.aap.core.persistance.dao.PcRoleDao;

@Repository
public class PcRoleDaoHibernateSpringImpl extends BaseDaoHibernateSpring<PcRole> implements PcRoleDao{


	private static final long serialVersionUID = 1L;

	@Override
	public PcRole savePcRole(PcRole pcRole) {
		saveObject(pcRole);
		return pcRole;
	}

	@Override
	public void deletePcRole(PcRole pcRole) {
		deleteObject(pcRole);
	}

	@Override
	public PcRole getPcRoleById(Long id) {
		return (PcRole)getObjectById(PcRole.class, id);
	}

	@Override
	public List<PcRole> getAllPcRoles(int totalItems, int pageNumber) {
		String query = "from PcRole order by dateCreated desc";
		List<PcRole> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public List<PcRole> getAllPcRoles() {
		String query = "from PcRole order by dateCreated desc";
		List<PcRole> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public List<PcRole> getPcRoleItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
		String query = "from PcRole where id > :lastId order by id asc";
		List<PcRole> list = executeQueryGetList(query, params);
		return list;
	}


	@Override
	public PcRole getPcRoleByPcIdAndRoleId(Long pcId, Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parliamentConstituencyId", pcId);
		params.put("roleId", roleId);
		String query = "from PcRole where parliamentConstituencyId = :parliamentConstituencyId and roleId = :roleId";
		return  executeQueryGetObject(query, params);
	}

}
