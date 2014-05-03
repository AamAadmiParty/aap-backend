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
	public static String createUserPollVoteKey(Long userId,Long pollQuestionId){
		return "UPQ_"+userId+"."+pollQuestionId;
	}
	public static String createPollVoteKey(Long pollQuestionId){
		return "PVK_"+pollQuestionId;
	}
}
