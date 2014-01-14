package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.DonationCampaign;
import com.next.aap.web.dto.DonationCampaignDto.CampaignType;

public interface DonationCampaignDao {

	public abstract DonationCampaign saveDonationCampaign(DonationCampaign donationCampaign);

	public abstract DonationCampaign getDonationCampaignById(Long id);

	public abstract DonationCampaign getDonationCampaignByDonationCampaign(String donationCampaign);
	
	public abstract List<DonationCampaign> getDonationCampaignsByUserId(Long userId);
	
	public abstract DonationCampaign getDonationCampaignByTypeAndUserId(CampaignType campaigntype, Long userId);
	
	public abstract Object[] getOldDonationCampaignInfo(String facebookUserId);

}