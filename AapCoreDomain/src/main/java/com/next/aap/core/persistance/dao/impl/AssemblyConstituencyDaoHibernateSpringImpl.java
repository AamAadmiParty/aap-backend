package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.AssemblyConstituency;
import com.next.aap.core.persistance.dao.AssemblyConstituencyDao;

@Repository
public class AssemblyConstituencyDaoHibernateSpringImpl extends BaseDaoHibernateSpring<AssemblyConstituency> implements AssemblyConstituencyDao  {

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.AssemblyConstituencyDao#saveAssemblyConstituency(com.next.aap.server.persistance.AssemblyConstituency)
	 */
	@Override
	public AssemblyConstituency saveAssemblyConstituency(AssemblyConstituency assemblyConstituency)
	{
		checkIfStringMissing("Name", assemblyConstituency.getName());
		checkIfObjectMissing("District", assemblyConstituency.getDistrict());
		assemblyConstituency.setNameUp(assemblyConstituency.getName().toUpperCase());
		checkIfAssemblyConstituencyExistsWithSameName(assemblyConstituency);
		assemblyConstituency = super.saveObject(assemblyConstituency);
		return assemblyConstituency;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.AssemblyConstituencyDao#deleteAssemblyConstituency(com.next.aap.server.persistance.AssemblyConstituency)
	 */
	@Override
	public void deleteAssemblyConstituency(AssemblyConstituency assemblyConstituency){
		super.deleteObject(assemblyConstituency);
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.AssemblyConstituencyDao#getAssemblyConstituencyById(java.lang.Long)
	 */
	@Override
	public AssemblyConstituency getAssemblyConstituencyById(Long id)
	{
		return getObjectById(AssemblyConstituency.class, id);
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.AssemblyConstituencyDao#getAllAssemblyConstituencys()
	 */
	@Override
	public List<AssemblyConstituency> getAllAssemblyConstituencys()
	{
		List<AssemblyConstituency> list = executeQueryGetList("from AssemblyConstituency order by name asc");
		return list;
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.AssemblyConstituencyDao#getAssemblyConstituencyOfDistrict(long)
	 */
	@Override
	public List<AssemblyConstituency> getAssemblyConstituencyOfDistrict(long districtId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("districtId", districtId);
		List<AssemblyConstituency> list = executeQueryGetList("from AssemblyConstituency where districtId = :districtId order by name asc",params);
		return list;
	}
	private void checkIfAssemblyConstituencyExistsWithSameName(AssemblyConstituency assemblyConstituency){
		AssemblyConstituency existingAssemblyConstituency = null;
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("districtId", assemblyConstituency.getDistrict().getId());
		params.put("nameUp", assemblyConstituency.getName().toUpperCase());
		if(assemblyConstituency.getId() != null && assemblyConstituency.getId() > 0){
			params.put("id", assemblyConstituency.getId());
			existingAssemblyConstituency = executeQueryGetObject("from AssemblyConstituency where districtId = :districtId and nameUp = :nameUp and id != :id", params);
		}else{
			existingAssemblyConstituency = executeQueryGetObject("from AssemblyConstituency where districtId = :districtId and nameUp = :nameUp", params);
		}

		if(existingAssemblyConstituency != null){
			throw new RuntimeException("AssemblyConstituency already exists with name = "+assemblyConstituency.getName()+" in district "+assemblyConstituency.getDistrict().getName());
		}
		
	}

	@Override
	public List<AssemblyConstituency> getAssemblyConstituencyOfState(
			long stateId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("stateId", stateId);
		List<AssemblyConstituency> list = executeQueryGetList("from AssemblyConstituency where district.stateId = :stateId order by name asc",params);
		return list;
	}
	
	@Override
	public AssemblyConstituency getAssemblyConstituencyNameAndDistrictId(
			long districtId, String urlName) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("name", urlName.toUpperCase());
		params.put("districtId", districtId);
		AssemblyConstituency assemblyConstituency = executeQueryGetObject("from AssemblyConstituency where name = :name and districtId=:districtId",params);
		return assemblyConstituency;
	}

}