package com.next.aap.web.dto;

import java.io.Serializable;


public class DonationCampaignDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String campaignId;
	private String longUrl;
	private String myAapShortUrl;
	private String description;
	private double totalDonation;
	private Long userId;
	private CampaignType campaignType;

	
	public enum CampaignType{
		RIPPLE,
		MY_DONATION,
		MY_FACEBOOK_FRIEND
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCampaignId() {
		return campaignId;
	}


	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}


	public String getLongUrl() {
		return longUrl;
	}


	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}


	public String getMyAapShortUrl() {
		return myAapShortUrl;
	}


	public void setMyAapShortUrl(String myAapShortUrl) {
		this.myAapShortUrl = myAapShortUrl;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getTotalDonation() {
		return totalDonation;
	}


	public void setTotalDonation(double totalDonation) {
		this.totalDonation = totalDonation;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public CampaignType getCampaignType() {
		return campaignType;
	}


	public void setCampaignType(CampaignType campaignType) {
		this.campaignType = campaignType;
	}


	@Override
	public String toString() {
		return "DonationCampaignDto [id=" + id + ", campaignId=" + campaignId + ", longUrl=" + longUrl + ", myAapShortUrl=" + myAapShortUrl + ", description="
				+ description + ", totalDonation=" + totalDonation + ", userId=" + userId + ", campaignType=" + campaignType + "]";
	}


	


}
