package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.District;

public interface DistrictDao {

	/**
	 * Creates/updates a district in Database
	 * 
	 * @param district
	 * @return saved district
	 * @throws AppException
	 */
	public abstract District saveDistrict(District district);

	/**
	 * deletes a district in Database
	 * 
	 * @param district
	 * @return updated district
	 * @throws AppException
	 */
	public abstract void deleteDistrict(District district);

	/**
	 * return a District with given primary key/id
	 * 
	 * @param id
	 * @return District with PK as id(parameter)
	 * @throws AppException
	 */
	public abstract District getDistrictById(Long id);

	public abstract List<District> getAllDistricts();

	public abstract List<District> getDistrictOfState(long stateId);

	public abstract District getDistrictByNameAndStateId(Long stateId, String name);

}