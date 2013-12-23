package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.StateRole;

public interface StateRoleDao {

	public abstract StateRole saveStateRole(StateRole stateRole);
	
	public abstract void deleteStateRole(StateRole stateRole);
	
	public abstract StateRole getStateRoleById(Long id);
	
	public abstract StateRole getStateRoleByStateIdAndRoleId(Long stateId,Long roleId);
	
	public abstract List<StateRole> getAllStateRoles(int totalItems, int pageNumber);
	
	public abstract List<StateRole> getAllStateRoles();
	
	public abstract List<StateRole> getStateRoleItemsAfterId(long stateRoleId);

}
