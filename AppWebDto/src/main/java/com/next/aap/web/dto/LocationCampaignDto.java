package com.next.aap.web.dto;

import java.util.Date;

public class LocationCampaignDto {

	private Long id;
	private String campaignId;
	private String longUrl;
	private String myAapShortUrl;
	private String title;
	private String description;
	private double totalDonation;
	private double targetDonation;
	private Date startDate;
	private Date endDate;
	private int totalNumberOfDonations;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public double getTargetDonation() {
		return targetDonation;
	}

	public void setTargetDonation(double targetDonation) {
		this.targetDonation = targetDonation;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getTotalNumberOfDonations() {
		return totalNumberOfDonations;
	}

	public void setTotalNumberOfDonations(int totalNumberOfDonations) {
		this.totalNumberOfDonations = totalNumberOfDonations;
	}

	

}
