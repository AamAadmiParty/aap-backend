package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.CountryRegionRole;
import com.next.aap.core.persistance.dao.CountryRegionRoleDao;

@Repository
public class CountryRegionRoleDaoHibernateSpringImpl extends BaseDaoHibernateSpring<CountryRegionRole> implements CountryRegionRoleDao{


	private static final long serialVersionUID = 1L;

	@Override
	public CountryRegionRole saveCountryRegionRole(CountryRegionRole countryRegionRole) {
		saveObject(countryRegionRole);
		return countryRegionRole;
	}

	@Override
	public void deleteCountryRegionRole(CountryRegionRole countryRegionRole) {
		deleteObject(countryRegionRole);
	}

	@Override
	public CountryRegionRole getCountryRegionRoleById(Long id) {
		return (CountryRegionRole)getObjectById(CountryRegionRole.class, id);
	}

	@Override
	public List<CountryRegionRole> getAllCountryRegionRoles(int totalItems, int pageNumber) {
		String query = "from CountryRegionRole order by dateCreated desc";
		List<CountryRegionRole> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public List<CountryRegionRole> getAllCountryRegionRoles() {
		String query = "from CountryRegionRole order by dateCreated desc";
		List<CountryRegionRole> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public List<CountryRegionRole> getCountryRegionRoleItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
		String query = "from CountryRegionRole where id > :lastId order by id asc";
		List<CountryRegionRole> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public CountryRegionRole getCountryRegionRoleByCountryRegionIdAndRoleId(Long countryRegionId, Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countryRegionId", countryRegionId);
		params.put("roleId", roleId);
		String query = "from CountryRegionRole where countryRegionId = :countryRegionId and roleId = :roleId";
		return  executeQueryGetObject(query, params);
	}

}
