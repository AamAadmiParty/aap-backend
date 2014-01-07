package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.CountryRole;
import com.next.aap.core.persistance.dao.CountryRoleDao;

@Repository
public class CountryRoleDaoHibernateSpringImpl extends BaseDaoHibernateSpring<CountryRole> implements CountryRoleDao{


	private static final long serialVersionUID = 1L;

	@Override
	public CountryRole saveCountryRole(CountryRole countryRole) {
		saveObject(countryRole);
		return countryRole;
	}

	@Override
	public void deleteCountryRole(CountryRole countryRole) {
		deleteObject(countryRole);
	}

	@Override
	public CountryRole getCountryRoleById(Long id) {
		return (CountryRole)getObjectById(CountryRole.class, id);
	}

	@Override
	public List<CountryRole> getAllCountryRoles(int totalItems, int pageNumber) {
		String query = "from CountryRole order by dateCreated desc";
		List<CountryRole> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public List<CountryRole> getAllCountryRoles() {
		String query = "from CountryRole order by dateCreated desc";
		List<CountryRole> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public List<CountryRole> getCountryRoleItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
		String query = "from CountryRole where id > :lastId order by id asc";
		List<CountryRole> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public CountryRole getCountryRoleByCountryIdAndRoleId(Long countryId, Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countryId", countryId);
		params.put("roleId", roleId);
		String query = "from CountryRole where countryId = :countryId and roleId = :roleId";
		return  executeQueryGetObject(query, params);
	}

}
