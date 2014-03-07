package com.next.aap.cache;

public class CacheKeyService {

	public static String createLocationCampaignKey(String donationCampaignId){
		return "LOC_"+donationCampaignId.toUpperCase();
	}
	public static String createDonationCampaignKey(String donationCampaignId){
		return "DON_"+donationCampaignId.toUpperCase();
	}
	public static String createGlobalCampaignKey(String donationCampaignId){
		return "GLO_"+donationCampaignId.toUpperCase();
	}
}
