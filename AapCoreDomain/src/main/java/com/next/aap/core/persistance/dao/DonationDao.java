package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Donation;
import com.next.aap.core.persistance.DonationDump;

public interface DonationDao {

	public abstract Donation saveDonation(Donation donation);

	public abstract Donation getDonationById(Long id);

	public abstract Donation getDonationByDonation(String donation);
	
	public abstract List<Donation> getDonationsByUserId(Long userId);
	
	public abstract Donation getDonationByDonorId(String donorId);

	public abstract List<Donation> getDonationsAfterId(Long lastId, int pageSize);
	
	public abstract List<Donation> getStateDonations(Long stateId);
	
	public abstract List<Donation> getDistrictDonations(Long districtId);
	
	public abstract List<Donation> getAcDonations(Long acId);
	
	public abstract List<Donation> getPcDonations(Long pcId);
	
	public abstract List<Donation> getCountryDonations(Long countryId);
	
	public abstract List<Donation> getCountryRegionDonations(Long countryRegionId);
	
	public abstract List<Donation> getCountryRegionAreaDonations(Long countryRegionAreaId);
	
	public abstract List<DonationDump> getDonationsToImport(int totalRecords);
	
	public abstract void updateDonationStatus(String donorId, String Status, String statusMessage);
	
	
	
	


}