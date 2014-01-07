package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.CountryRegion;
import com.next.aap.core.persistance.dao.CountryRegionDao;

@Component
public class CountryRegionDaoHibernateSpringImpl extends BaseDaoHibernateSpring<CountryRegion> implements CountryRegionDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.CountryRegionDao#saveCountryRegion(com.next.aap.server.persistance.CountryRegion)
	 */
	@Override
	public CountryRegion saveCountryRegion(CountryRegion countryRegion){
		checkIfStringMissing("name", countryRegion.getName());
		countryRegion.setNameUp(countryRegion.getName().toUpperCase());
		countryRegion = super.saveObject(countryRegion);
		return countryRegion;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.CountryRegionDao#getCountryRegionById(java.lang.Long)
	 */
	@Override
	public CountryRegion getCountryRegionById(Long id) {
		return super.getObjectById(CountryRegion.class, id);
	}

	@Override
	public CountryRegion getCountryRegionByNameAndCountryId(Long countryId,String countryRegionName) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("nameUp", countryRegionName.toUpperCase());
		params.put("countryId", countryId);
		CountryRegion countryRegion = executeQueryGetObject("from CountryRegion where nameUp = :nameUp and countryId = :countryId", params);
		return countryRegion;
	}

	@Override
	public List<CountryRegion> getAllCountryRegions() {
		List<CountryRegion> list = executeQueryGetList("from CountryRegion order by name asc");
		return list;
	}

}
