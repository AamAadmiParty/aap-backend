package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.ParliamentConstituency;
import com.next.aap.core.persistance.dao.ParliamentConstituencyDao;

@Repository
public class ParliamentConstituencyDaoHibernateSpringImpl extends BaseDaoHibernateSpring<ParliamentConstituency> implements ParliamentConstituencyDao  {

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.ParliamentConstituencyDao#saveParliamentConstituency(com.next.aap.server.persistance.ParliamentConstituency)
	 */
	@Override
	public ParliamentConstituency saveParliamentConstituency(ParliamentConstituency parliamentConstituency)
	{
		checkIfStringMissing("Name", parliamentConstituency.getName());
		checkIfObjectMissing("State", parliamentConstituency.getState());
		parliamentConstituency.setNameUp(parliamentConstituency.getName().toUpperCase());
		checkIfParliamentConstituencyExistsWithSameName(parliamentConstituency);
		parliamentConstituency = super.saveObject(parliamentConstituency);
		return parliamentConstituency;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.ParliamentConstituencyDao#deleteParliamentConstituency(com.next.aap.server.persistance.ParliamentConstituency)
	 */
	@Override
	public void deleteParliamentConstituency(ParliamentConstituency parliamentConstituency){
		super.deleteObject(parliamentConstituency);
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.ParliamentConstituencyDao#getParliamentConstituencyById(java.lang.Long)
	 */
	@Override
	public ParliamentConstituency getParliamentConstituencyById(Long id)
	{
		return getObjectById(ParliamentConstituency.class, id);
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.ParliamentConstituencyDao#getAllParliamentConstituencys()
	 */
	@Override
	public List<ParliamentConstituency> getAllParliamentConstituencys()
	{
		List<ParliamentConstituency> list = executeQueryGetList("from ParliamentConstituency order by name asc");
		return list;
	}
	private void checkIfParliamentConstituencyExistsWithSameName(ParliamentConstituency parliamentConstituency){
		ParliamentConstituency existingParliamentConstituency = null;
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("stateId", parliamentConstituency.getState().getId());
		params.put("nameUp", parliamentConstituency.getName().toUpperCase());
		if(parliamentConstituency.getId() != null && parliamentConstituency.getId() > 0){
			params.put("id", parliamentConstituency.getId());
			existingParliamentConstituency = executeQueryGetObject("from ParliamentConstituency where stateId = :stateId and nameUp = :nameUp and id != :id", params);
		}else{
			existingParliamentConstituency = executeQueryGetObject("from ParliamentConstituency where stateId = :stateId and nameUp = :nameUp", params);
		}

		if(existingParliamentConstituency != null){
			throw new RuntimeException("ParliamentConstituency already exists with name = "+parliamentConstituency.getName()+" in State "+parliamentConstituency.getState().getName());
		}
		
	}

	@Override
	public List<ParliamentConstituency> getParliamentConstituencyOfState(
			long stateId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("stateId", stateId);
		List<ParliamentConstituency> list = executeQueryGetList("from ParliamentConstituency where stateId = :stateId order by name asc",params);
		return list;
	}
	
	@Override
	public ParliamentConstituency getParliamentConstituencyNameAndStateId(
			long stateId, String urlName) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("nameUp", urlName.toUpperCase());
		params.put("stateId", stateId);
		ParliamentConstituency parliamentConstituency = executeQueryGetObject("from ParliamentConstituency where nameUp = :nameUp and stateId=:stateId",params);
		return parliamentConstituency;
	}

}