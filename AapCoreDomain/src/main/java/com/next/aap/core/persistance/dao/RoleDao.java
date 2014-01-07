package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Role;

public interface RoleDao {

	public abstract Role saveRole(Role role);
	
	public abstract void deleteRole(Role role);
	
	public abstract Role getRoleById(Long id);
	
	public abstract Role getRoleByName(String name);
	
	public abstract List<Role> getAllRoles(int totalItems, int pageNumber);
	
	public abstract List<Role> getAllRoles();
	
	public abstract List<Role> getRoleItemsAfterId(long roleId);
	
	public abstract List<Role> getUserGlobalRoles(long userId);
	
	public abstract List<Role> getUserStateRoles(long userId, long stateId);
	
	public abstract List<Role> getUserDistrictRoles(long userId, long districtId);
	
	public abstract List<Role> getUserAcRoles(long userId, long acId);
	
	public abstract List<Role> getUserPcRoles(long userId, long pcId);
	
	public abstract List<Role> getUserCountryRoles(long userId, long countryId);
	
	public abstract List<Role> getUserCountryRegionRoles(long userId, long countryRegionId);
	
	public abstract List<Role> getUserCountryRegionAreaRoles(long userId, long countryRegionAreaId);
	
	public abstract List<Role> getGlobalRoles();
	
	public abstract List<Role> getStateRoles(long stateId);
	
	public abstract List<Role> getDistrictRoles(long districtId);
	
	public abstract List<Role> getAcRoles(long acId);
	
	public abstract List<Role> getPcRoles(long pcId);

	public abstract List<Role> getCountryRoles(long countryId);
	
	public abstract List<Role> getCountryRegionRoles(long countryRegionId);
	
	public abstract List<Role> getCountryRegionAreaRoles(long countryRegionAreaId);
}
