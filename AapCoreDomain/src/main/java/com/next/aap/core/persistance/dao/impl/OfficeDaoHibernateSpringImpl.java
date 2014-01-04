package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.Office;
import com.next.aap.core.persistance.dao.OfficeDao;

@Component
public class OfficeDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Office> implements OfficeDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.OfficeDao#saveOffice(com.next.aap.server.persistance.Office)
	 */
	@Override
	public Office saveOffice(Office office){
		office = super.saveObject(office);
		return office;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.OfficeDao#getOfficeById(java.lang.Long)
	 */
	@Override
	public Office getOfficeById(Long id) {
		return super.getObjectById(Office.class, id);
	}

	@Override
	public List<Office> getNationalOffices() {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("national", true);
		List<Office> offices = executeQueryGetList("from Office where national = :national", params);
		return offices;
	}

	@Override
	public List<Office> getStateOffices(Long stateId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("stateId", stateId);
		List<Office> offices = executeQueryGetList("from Office where stateId = :stateId", params);
		return offices;
	}

	@Override
	public List<Office> getDistrictOffices(Long districtId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("districtId", districtId);
		List<Office> offices = executeQueryGetList("from Office where districtId = :districtId", params);
		return offices;
	}

	@Override
	public List<Office> getAcOffices(Long acId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("assemblyConstituencyId", acId);
		List<Office> offices = executeQueryGetList("from Office where assemblyConstituencyId = :assemblyConstituencyId", params);
		return offices;
	}

	@Override
	public List<Office> getPcOffices(Long pcId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("parliamentConstituencyId", pcId);
		List<Office> offices = executeQueryGetList("from Office where parliamentConstituencyId = :parliamentConstituencyId", params);
		return offices;
	}
}
