package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.District;
import com.next.aap.core.persistance.dao.DistrictDao;

@Component
public class DistrictDaoHibernateSpringImpl extends BaseDaoHibernateSpring<District> implements DistrictDao {

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DistrictDao#saveDistrict(com.next.aap.server.persistance.District)
	 */
	@Override
	public District saveDistrict(District district)
	{
		checkIfStringMissing("Name", district.getName());
		checkIfObjectMissing("State", district.getState());
		checkIfDistrictExistsWithSameName(district);
		district.setNameUp(district.getName().toUpperCase());
		district = super.saveObject(district);
		return district;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DistrictDao#deleteDistrict(com.next.aap.server.persistance.District)
	 */
	@Override
	public void deleteDistrict(District district)
	{
		super.deleteObject(district);
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DistrictDao#getDistrictById(java.lang.Long)
	 */
	@Override
	public District getDistrictById(Long id)
	{
		District district = super.getObjectById(District.class, id);
		return district;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DistrictDao#getAllDistricts()
	 */
	@Override
	public List<District> getAllDistricts()
	{
		List<District> list = executeQueryGetList("from District order by name asc");
		return list;
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DistrictDao#getDistrictOfState(long)
	 */
	@Override
	public List<District> getDistrictOfState(long stateId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("stateId", stateId);
		List<District> list = executeQueryGetList("from District where stateId=:stateId order by name asc", params);
		return list;
	}

	private void checkIfDistrictExistsWithSameName(District district){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("stateId", district.getState().getId());
		params.put("nameUp", district.getName().toUpperCase());
		District existingDistrict = null;
		if(district.getId() != null && district.getId() > 0){
			params.put("id", district.getId());
			existingDistrict = executeQueryGetObject("from District where stateId=:stateId and nameUp= :nameUp and id != :id", params);
		}else{
			existingDistrict = executeQueryGetObject("from District where stateId=:stateId and nameUp= :nameUp", params);
		}
		if(existingDistrict != null){
			throw new RuntimeException("District already exists with name = "+district.getName()+" in state "+district.getState().getName());
		}
	}
	
	@Override
	public District getDistrictByNameAndStateId(Long stateId, String name) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("stateId", stateId);
		params.put("nameUp", name.toUpperCase());
		District existingDistrict = executeQueryGetObject("from District where stateId = :stateId and nameUp = :nameUp", params);
		return existingDistrict;
	}

}