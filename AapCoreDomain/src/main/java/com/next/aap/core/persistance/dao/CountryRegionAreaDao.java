package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.CountryRegionArea;

public interface CountryRegionAreaDao {

	public abstract CountryRegionArea saveCountryRegionArea(CountryRegionArea countryRegionArea);

	public abstract CountryRegionArea getCountryRegionAreaById(Long id);

	public abstract CountryRegionArea getCountryRegionAreaByNameAndCountryRegionId(Long countryRegionId,String countryRegionArea);
	
	public abstract List<CountryRegionArea> getCountryRegionAreasByCountryRegionId(Long countryRegionId);
	
}