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

}
