package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.CountryRegionArea;
import com.next.aap.core.persistance.dao.CountryRegionAreaDao;

@Component
public class CountryRegionAreaDaoHibernateSpringImpl extends BaseDaoHibernateSpring<CountryRegionArea> implements CountryRegionAreaDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.CountryRegionAreaDao#saveCountryRegionArea(com.next.aap.server.persistance.CountryRegionArea)
	 */
	@Override
	public CountryRegionArea saveCountryRegionArea(CountryRegionArea countryRegionArea){
		checkIfStringMissing("name", countryRegionArea.getName());
		countryRegionArea.setNameUp(countryRegionArea.getName().toUpperCase());
		countryRegionArea = super.saveObject(countryRegionArea);
		return countryRegionArea;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.CountryRegionAreaDao#getCountryRegionAreaById(java.lang.Long)
	 */
	@Override
	public CountryRegionArea getCountryRegionAreaById(Long id) {
		return super.getObjectById(CountryRegionArea.class, id);
	}

	@Override
	public CountryRegionArea getCountryRegionAreaByNameAndCountryRegionId(Long countryRegionId,String countryRegionAreaName) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("nameUp", countryRegionAreaName.toUpperCase());
		params.put("countryRegionId", countryRegionId);
		CountryRegionArea countryRegionArea = executeQueryGetObject("from CountryRegionArea where nameUp = :nameUp and countryRegionId = :countryRegionId", params);
		return countryRegionArea;
	}

	@Override
	public List<CountryRegionArea> getCountryRegionAreasByCountryRegionId(Long countryRegionId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("countryRegionId", countryRegionId);
		List<CountryRegionArea> countryRegionArea = executeQueryGetList("from CountryRegionArea where countryRegionId = :countryRegionId", params);
		return countryRegionArea;
	}

	@Override
	public List<CountryRegionArea> getAllCountryRegionAreas() {
		List<CountryRegionArea> countryRegionArea = executeQueryGetList("from CountryRegionArea");
		return countryRegionArea;
	}

}
