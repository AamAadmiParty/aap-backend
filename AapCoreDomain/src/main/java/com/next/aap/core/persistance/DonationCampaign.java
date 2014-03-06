package com.next.aap.core.persistance;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.next.aap.web.dto.DonationCampaignDto.CampaignType;

@Entity
@Table(name="donation_campaign")
public class DonationCampaign {

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

	@Column(name = "description", columnDefinition="LONGTEXT")
	private String description;

	@Column(name = "total_donation")
	private double totalDonation;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="user_id")
    private User user;
	@Column(name="user_id", insertable=false,updatable=false)
	private Long userId;

	@Column(name = "campaign_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private CampaignType campaignType;

	@Column(name = "total_number_of_donation")
	private int totalNumberOfDonations;


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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
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


	public int getTotalNumberOfDonations() {
		return totalNumberOfDonations;
	}


	public void setTotalNumberOfDonations(int totalNumberOfDonations) {
		this.totalNumberOfDonations = totalNumberOfDonations;
	}



}
