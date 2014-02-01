package com.next.aap.core.persistance.dao;

import com.next.aap.core.persistance.LocationCampaign;

public interface LocationCampaignDao {

	public abstract LocationCampaign saveLocationCampaign(LocationCampaign locationCampaign);

	public abstract LocationCampaign getLocationCampaignById(Long id);

	public abstract LocationCampaign getLocationCampaignByLocationCampaign(String locationCampaign);
	
}