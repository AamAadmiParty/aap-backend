package com.next.aap.core.persistance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.next.aap.web.dto.LocationCampaignDto.LocationCampaignType;

@Entity
@Table(name="location_campaign")
public class LocationCampaign {

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
	
	@Column(name = "campaign_id")
	private String campaignId;

	@Column(name = "campaign_id_up")
	private String campaignIdUp;
	
	@Column(name = "long_url")
	private String longUrl;

	@Column(name = "myaap_short_url")
	private String myAapShortUrl;

	@Column(name = "title")
	private String title;

	@Column(name = "description", columnDefinition="LONGTEXT")
	private String description;

	@Column(name = "total_donation")
	private double totalDonation;

	@Column(name = "target_donation")
	private double targetDonation;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "total_no_of_donations")
	private int totalNumberOfDonations;

	@Column(name = "campaign_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private LocationCampaignType campaignType;

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

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignIdUp() {
		return campaignIdUp;
	}

	public void setCampaignIdUp(String campaignIdUp) {
		this.campaignIdUp = campaignIdUp;
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

	public LocationCampaignType getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(LocationCampaignType campaignType) {
		this.campaignType = campaignType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationCampaign other = (LocationCampaign) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}
