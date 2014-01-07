package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.Country;
import com.next.aap.core.persistance.dao.CountryDao;

@Repository
public class CountryDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Country> implements CountryDao  {

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.CountryDao#saveCountry(com.next.aap.server.persistance.Country)
	 */
	@Override
	public Country saveCountry(Country country) 
	{
		checkIfStringMissing("Name", country.getName());
		checkIfCountryExistsWithSameName(country);
		country.setNameUp(country.getName().toUpperCase());
		country = super.saveObject(country);
		return country;
	}
	

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.CountryDao#deleteCountry(com.next.aap.server.persistance.Country)
	 */
	@Override
	public void deleteCountry(Country country)  {
		super.deleteObject(country);
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.CountryDao#getCountryById(java.lang.Long)
	 */
	@Override
	public Country getCountryById(Long id) 
	{
		Country country = super.getObjectById(Country.class, id);
		return country;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.CountryDao#getAllCountrys()
	 */
	@Override
	public List<Country> getAllCountries() 
	{
		List<Country> list = executeQueryGetList("from Country order by name asc");
		return list;
	}
	private void checkIfCountryExistsWithSameName(Country country) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("nameUp", country.getName());
		Country existingCountry;
		if(country.getId() != null && country.getId() > 0){
			params.put("id", country.getId());
			existingCountry = executeQueryGetObject("from Country where nameUp=:nameUp and id != :id", params);
		}else{
			existingCountry = executeQueryGetObject("from Country where nameUp=:nameUp ", params);
		}
		
		if(existingCountry != null){
			throw new RuntimeException("Country already exists with name = "+country.getName());
		}
	}
	
	@Override
	public Country getCountryByName(String name) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("nameUp", name.toUpperCase());
		Country existingCountry = executeQueryGetObject("from Country where nameUp = :nameUp ", params);
		return existingCountry;
	}

}