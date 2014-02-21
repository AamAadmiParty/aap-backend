package com.next.aap.core.persistance;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="financial_planning")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="Account", include="all")
public class FinancialPlanning {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Version
	@Column(name="ver")
	private int ver;
	
	@Column(name="date_created")
	private Date dateCreated;
	@Column(name="date_modified")
	private Date dateModified;
	@Column(name="creator_id")
	private Long creatorId;
	@Column(name="modifier_id")
	private Long modifierId;

	
	@Column(name = "title", nullable = false, length=256)
	private String title;

	@Column(name = "description",  columnDefinition="LONGTEXT")
	private String description;

	@Column(name="end_date")
	private Date endDate;
	
	@Column(name = "amount_required")
	private double amountRequired;

	@Column(name = "provider", length=512)
	private String provider;
	
	@Column(name = "provider_contact_number", length=16)
	private String providerContactNumber;

	@Column(name = "fb_event_id", length=32)
	private String fbEventId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="location_campaign_id")
    private LocationCampaign locationCampaign;
	@Column(name="location_campaign_id", insertable=false,updatable=false)
	private Long locationCampaignId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVer() {
		return ver;
	}
	public void setVer(int ver) {
		this.ver = ver;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Long getModifierId() {
		return modifierId;
	}
	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
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
	public LocationCampaign getLocationCampaign() {
		return locationCampaign;
	}
	public void setLocationCampaign(LocationCampaign locationCampaign) {
		this.locationCampaign = locationCampaign;
	}
	public Long getLocationCampaignId() {
		return locationCampaignId;
	}
	public void setLocationCampaignId(Long locationCampaignId) {
		this.locationCampaignId = locationCampaignId;
	}

}
