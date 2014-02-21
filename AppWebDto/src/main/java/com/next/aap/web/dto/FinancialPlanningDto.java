package com.next.aap.web.dto;

import java.util.Date;

public class FinancialPlanningDto {

	private Long id;
	private String title;
	private String description;
	private Date endDate;
	private double amountRequired;
	private String provider;
	private String providerContactNumber;
	private String fbEventId;
	private Long locationCampaignId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public double getAmountRequired() {
		return amountRequired;
	}
	public void setAmountRequired(double amountRequired) {
		this.amountRequired = amountRequired;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getProviderContactNumber() {
		return providerContactNumber;
	}
	public void setProviderContactNumber(String providerContactNumber) {
		this.providerContactNumber = providerContactNumber;
	}
	public String getFbEventId() {
		return fbEventId;
	}
	public void setFbEventId(String fbEventId) {
		this.fbEventId = fbEventId;
	}
	public Long getLocationCampaignId() {
		return locationCampaignId;
	}
	public void setLocationCampaignId(Long locationCampaignId) {
		this.locationCampaignId = locationCampaignId;
	}
	
	
}
