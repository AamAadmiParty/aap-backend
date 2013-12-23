package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.AcRole;

public interface AcRoleDao {

	public abstract AcRole saveAcRole(AcRole acRole);
	
	public abstract void deleteAcRole(AcRole acRole);
	
	public abstract AcRole getAcRoleById(Long id);
	
	public abstract AcRole getAcRoleByAcIdAndRoleId(Long acId, Long roleId);
	
	public abstract List<AcRole> getAllAcRoles(int totalItems, int pageNumber);
	
	public abstract List<AcRole> getAllAcRoles();
	
	public abstract List<AcRole> getAcRoleItemsAfterId(long acRoleId);

}
