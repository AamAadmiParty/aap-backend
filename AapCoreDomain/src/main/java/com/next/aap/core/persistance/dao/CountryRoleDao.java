package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.CountryRole;

public interface CountryRoleDao {

	public abstract CountryRole saveCountryRole(CountryRole countryRole);
	
	public abstract void deleteCountryRole(CountryRole countryRole);
	
	public abstract CountryRole getCountryRoleById(Long id);
	
	public abstract CountryRole getCountryRoleByCountryIdAndRoleId(Long countryId, Long roleId);
	
	public abstract List<CountryRole> getAllCountryRoles(int totalItems, int pageNumber);
	
	public abstract List<CountryRole> getAllCountryRoles();
	
	public abstract List<CountryRole> getCountryRoleItemsAfterId(long countryRoleId);

}
