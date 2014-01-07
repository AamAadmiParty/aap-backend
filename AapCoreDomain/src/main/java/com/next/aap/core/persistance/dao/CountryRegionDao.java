package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.CountryRegion;

public interface CountryRegionDao {

	public abstract CountryRegion saveCountryRegion(CountryRegion countryRegion);

	public abstract CountryRegion getCountryRegionById(Long id);
	
	public abstract List<CountryRegion> getAllCountryRegions();

	public abstract CountryRegion getCountryRegionByNameAndCountryId(Long countryId,String countryRegion);
	
	public abstract List<CountryRegion> getCountryRegionsByCountryId(Long countryId);
	
}