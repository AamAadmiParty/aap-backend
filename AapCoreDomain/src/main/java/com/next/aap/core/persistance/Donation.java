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
@Table(name="donations")
public class Donation {

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
	
	@Column(name = "donor_id")
	private String donorId;
	@Column(name = "merchant_reference_number")
	private String merchantReferenceNumber;
	@Column(name = "transaction_id")
	private String transactionId;
	@Column(name = "payment_gateway")
	private String paymentGateway;
	@Column(name = "payment_gateway_trn_type")
	private String transactionType;
	@Column(name = "donation_date")
	private Date donationDate;
	@Column(name = "donor_ip")
	private String donorIp;
	@Column(name = "amount")
	private Double amount;
	@Column(name = "utm_source")
	private String utmSource;
	@Column(name = "utm_medium")
	private String utmMedium;
	@Column(name = "utm_term")
	private String utmTerm;
	@Column(name = "utm_content")
	private String utmContent;
	@Column(name = "utm_campaign")
	private String utmCampaign;
	@Column(name = "pg_error_msg")
	private String pgErrorMessage;
	@Column(name = "cid")
	private String cid;
	@Column(name = "pg_error_detail")
	private String pgErrorDetail;
	@Column(name = "remark", columnDefinition="LONGTEXT")
	private String remark;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="user_id")
    private User user;
	@Column(name="user_id", insertable=false,updatable=false)
	private Long userId;

	//Following fields are for old records and will not be used anywahere, storing only for future auditin purpose
	@Column(name = "donor_name")
	private String donorName;
	@Column(name = "donor_gender")
	private String donorGender;
	@Column(name = "donor_age")
	private String donorAge;
	@Column(name = "donor_mobile")
	private String donorMobile;
	@Column(name = "donor_email")
	private String donorEmail;
	@Column(name = "donor_country_id")
	private String donorCountryId;
	@Column(name = "donor_state_id")
	private String donorStateId;
	@Column(name = "donor_district_id")
	private String donorDistrictId;
	@Column(name = "donor_address")
	private String donorAddress;

	
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
	public String getMerchantReferenceNumber() {
		return merchantReferenceNumber;
	}
	public void setMerchantReferenceNumber(String merchantReferenceNumber) {
		this.merchantReferenceNumber = merchantReferenceNumber;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Date getDonationDate() {
		return donationDate;
	}
	public void setDonationDate(Date donationDate) {
		this.donationDate = donationDate;
	}
	public String getDonorIp() {
		return donorIp;
	}
	public void setDonorIp(String donorIp) {
		this.donorIp = donorIp;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getUtmSource() {
		return utmSource;
	}
	public void setUtmSource(String utmSource) {
		this.utmSource = utmSource;
	}
	public String getUtmMedium() {
		return utmMedium;
	}
	public void setUtmMedium(String utmMedium) {
		this.utmMedium = utmMedium;
	}
	public String getUtmTerm() {
		return utmTerm;
	}
	public void setUtmTerm(String utmTerm) {
		this.utmTerm = utmTerm;
	}
	public String getUtmContent() {
		return utmContent;
	}
	public void setUtmContent(String utmContent) {
		this.utmContent = utmContent;
	}
	public String getUtmCampaign() {
		return utmCampaign;
	}
	public void setUtmCampaign(String utmCampaign) {
		this.utmCampaign = utmCampaign;
	}
	public String getPgErrorMessage() {
		return pgErrorMessage;
	}
	public void setPgErrorMessage(String pgErrorMessage) {
		this.pgErrorMessage = pgErrorMessage;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getPgErrorDetail() {
		return pgErrorDetail;
	}
	public void setPgErrorDetail(String pgErrorDetail) {
		this.pgErrorDetail = pgErrorDetail;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getDonorId() {
		return donorId;
	}
	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}
	public String getDonorName() {
		return donorName;
	}
	public void setDonorName(String donorName) {
		this.donorName = donorName;
	}
	public String getDonorGender() {
		return donorGender;
	}
	public void setDonorGender(String donorGender) {
		this.donorGender = donorGender;
	}
	public String getDonorAge() {
		return donorAge;
	}
	public void setDonorAge(String donorAge) {
		this.donorAge = donorAge;
	}
	public String getDonorMobile() {
		return donorMobile;
	}
	public void setDonorMobile(String donorMobile) {
		this.donorMobile = donorMobile;
	}
	public String getDonorEmail() {
		return donorEmail;
	}
	public void setDonorEmail(String donorEmail) {
		this.donorEmail = donorEmail;
	}
	public String getDonorCountryId() {
		return donorCountryId;
	}
	public void setDonorCountryId(String donorCountryId) {
		this.donorCountryId = donorCountryId;
	}
	public String getDonorStateId() {
		return donorStateId;
	}
	public void setDonorStateId(String donorStateId) {
		this.donorStateId = donorStateId;
	}
	public String getDonorDistrictId() {
		return donorDistrictId;
	}
	public void setDonorDistrictId(String donorDistrictId) {
		this.donorDistrictId = donorDistrictId;
	}
	public String getDonorAddress() {
		return donorAddress;
	}
	public void setDonorAddress(String donorAddress) {
		this.donorAddress = donorAddress;
	}

	
	
}
