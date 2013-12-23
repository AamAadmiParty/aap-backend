package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.StateRole;
import com.next.aap.core.persistance.dao.StateRoleDao;

@Repository
public class StateRoleDaoHibernateSpringImpl extends BaseDaoHibernateSpring<StateRole> implements StateRoleDao{


	private static final long serialVersionUID = 1L;

	@Override
	public StateRole saveStateRole(StateRole stateRole) {
		saveObject(stateRole);
		return stateRole;
	}

	@Override
	public void deleteStateRole(StateRole stateRole) {
		deleteObject(stateRole);
	}

	@Override
	public StateRole getStateRoleById(Long id) {
		return (StateRole)getObjectById(StateRole.class, id);
	}

	@Override
	public List<StateRole> getAllStateRoles(int totalItems, int pageNumber) {
		String query = "from StateRole order by dateCreated desc";
		List<StateRole> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public List<StateRole> getAllStateRoles() {
		String query = "from StateRole order by dateCreated desc";
		List<StateRole> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public List<StateRole> getStateRoleItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
		String query = "from StateRole where id > :lastId order by id asc";
		List<StateRole> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public StateRole getStateRoleByStateIdAndRoleId(Long stateId, Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stateId", stateId);
		params.put("roleId", roleId);
		String query = "from StateRole where stateId = :stateId and roleId = :roleId";
		return  executeQueryGetObject(query, params);
	}

}
