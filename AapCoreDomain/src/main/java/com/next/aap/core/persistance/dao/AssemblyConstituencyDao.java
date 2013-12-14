package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.AssemblyConstituency;

public interface AssemblyConstituencyDao {

	/**
	 * Creates/updates a assemblyConstituency in Database
	 * 
	 * @param assemblyConstituency
	 * @return saved assemblyConstituency
	 * @throws AppException
	 */
	public abstract AssemblyConstituency saveAssemblyConstituency(
			AssemblyConstituency assemblyConstituency);

	/**
	 * deletes a assemblyConstituency in Database
	 * 
	 * @param assemblyConstituency
	 * @return updated assemblyConstituency
	 * @throws AppException
	 */
	public abstract void deleteAssemblyConstituency(
			AssemblyConstituency assemblyConstituency);

	/**
	 * return a AssemblyConstituency with given primary key/id
	 * 
	 * @param id
	 * @return AssemblyConstituency with PK as id(parameter)
	 * @throws AppException
	 */
	public abstract AssemblyConstituency getAssemblyConstituencyById(Long id);

	public abstract List<AssemblyConstituency> getAllAssemblyConstituencys();

	public abstract List<AssemblyConstituency> getAssemblyConstituencyOfDistrict(long districtId);
	
	public abstract List<AssemblyConstituency> getAssemblyConstituencyOfState(long stateId);
	
	public abstract AssemblyConstituency getAssemblyConstituencyNameAndDistrictId(long districtId, String urlName);

}