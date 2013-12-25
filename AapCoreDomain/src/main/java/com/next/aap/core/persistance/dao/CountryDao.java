package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Country;

public interface CountryDao {

	/**
	 * Creates/updates a country in Database
	 * 
	 * @param country
	 * @return saved country
	 * @
	 */
	public abstract Country saveCountry(Country country);

	/**
	 * deletes a country in Database
	 * 
	 * @param country
	 * @return updated country
	 * @
	 */
	public abstract void deleteCountry(Country country);

	/**
	 * return a Country with given primary key/id
	 * 
	 * @param id
	 * @return Country with PK as id(parameter)
	 * @
	 */
	public abstract Country getCountryById(Long id);
	
	public abstract Country getCountryByName(String name);

	public abstract List<Country> getAllCountries();

}