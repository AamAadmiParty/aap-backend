package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.ParliamentConstituency;

public interface ParliamentConstituencyDao {

	/**
	 * Creates/updates a parliamentConstituency in Database
	 * 
	 * @param parliamentConstituency
	 * @return saved parliamentConstituency
	 * @throws AppException
	 */
	public abstract ParliamentConstituency saveParliamentConstituency(
			ParliamentConstituency parliamentConstituency);

	/**
	 * deletes a parliamentConstituency in Database
	 * 
	 * @param parliamentConstituency
	 * @return updated parliamentConstituency
	 * @throws AppException
	 */
	public abstract void deleteParliamentConstituency(
			ParliamentConstituency parliamentConstituency);

	/**
	 * return a ParliamentConstituency with given primary key/id
	 * 
	 * @param id
	 * @return ParliamentConstituency with PK as id(parameter)
	 * @throws AppException
	 */
	public abstract ParliamentConstituency getParliamentConstituencyById(Long id);

	public abstract List<ParliamentConstituency> getAllParliamentConstituencys();

	public abstract List<ParliamentConstituency> getParliamentConstituencyOfState(long stateId);
	
	public abstract ParliamentConstituency getParliamentConstituencyNameAndStateId(long districtId, String urlName);

}