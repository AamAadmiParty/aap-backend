package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.LocationCampaign;

public interface LocationCampaignDao {

	public abstract LocationCampaign saveLocationCampaign(LocationCampaign locationCampaign);

	public abstract LocationCampaign getLocationCampaignById(Long id);

	public abstract LocationCampaign getLocationCampaignByLocationCampaign(String locationCampaign);
	
	public abstract LocationCampaign getDefaultLocationCampaignByStateId(Long stateId);
	
	public abstract LocationCampaign getDefaultLocationCampaignByDistrictId(Long districtId);
	
	public abstract LocationCampaign getDefaultLocationCampaignByAcId(Long acId);
	
	public abstract LocationCampaign getDefaultLocationCampaignByPcId(Long pcId);
	
	public abstract List<LocationCampaign> getAllLocationCampaigns();
	
}