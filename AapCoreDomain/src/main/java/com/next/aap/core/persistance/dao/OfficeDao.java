package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Office;

public interface OfficeDao {

	public abstract Office saveOffice(Office office);

	public abstract Office getOfficeById(Long id);

	public abstract List<Office> getNationalOffices();
	
	public abstract List<Office> getStateOffices(Long stateId);
	
	public abstract List<Office> getDistrictOffices(Long districtId);
	
	public abstract List<Office> getAcOffices(Long acId);
	
	public abstract List<Office> getPcOffices(Long pcId);


}