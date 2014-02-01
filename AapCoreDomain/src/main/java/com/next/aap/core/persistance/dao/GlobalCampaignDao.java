package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.GlobalCampaign;

public interface GlobalCampaignDao {

	public abstract GlobalCampaign saveGlobalCampaign(GlobalCampaign globalCampaign);

	public abstract GlobalCampaign getGlobalCampaignById(Long id);

	public abstract GlobalCampaign getGlobalCampaignByGlobalCampaign(String globalCampaign);
	
	public abstract List<GlobalCampaign> getGlobalCampaigns();
	
}