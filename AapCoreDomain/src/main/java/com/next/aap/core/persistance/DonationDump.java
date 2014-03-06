package com.next.aap.core.persistance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

//@Entity
//@Table(name="donation_dump")
public class DonationDump {

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
	
	@Column(name = "Donor_Id")
	private String donorId;
	@Column(name = "Merchant_Reference_No")
	private String merchantReferenceNumber;
	@Column(name = "Transaction_Id")
	private String transactionId;
	@Column(name = "Name")
	private String donorName;
	@Column(name = "Gender")
	private String donorGender;
	@Column(name = "Age")
	private Integer donorAge;
	@Column(name = "Mobile")
	private String donorMobile;
	@Column(name = "Email")
	private String donorEmail;
	@Column(name = "Country_Id")
	private String donorCountryId;
	@Column(name = "State_Id")
	private String donorStateId;
	@Column(name = "District_Id")
	private String donorDistrictId;
	@Column(name = "Address")
	private String donorAddress;
	
	@Column(name = "Payment_Gateway_Used")
	private String paymentGateway;
	@Column(name = "Payment_Gateway")
	private String transactionType;
	@Column(name = "Donation_Date")
	private String donationDate;
	@Column(name = "Donor_IP")
	private String donorIp;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "Utm_Source")
	private String utmSource;
	@Column(name = "Utm_Medium")
	private String utmMedium;
	@Column(name = "Utm_Term")
	private String utmTerm;
	@Column(name = "Utm_Content")
	private String utmContent;
	@Column(name = "Utm_Campaign")
	private String utmCampaign;
	@Column(name = "PGErrorMsg")
	private String pgErrorMessage;
	@Column(name = "cid")
	private String cid;
	@Column(name = "lid")
	private String lid;
	@Column(name = "PGErrorDetail")
	private String pgErrorDetail;
	//@Column(name = "Remarks", columnDefinition="LONGTEXT")
	private String remark;
	@Column(name = "status")
	private String status;
	@Column(name = "status_message")
	private String statusMessage;
	
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
	public String getDonorId() {
		return donorId;
	}
	public void setDonorId(String donorId) {
		this.donorId = donorId;
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
	public Integer getDonorAge() {
		return donorAge;
	}
	public void setDonorAge(Integer donorAge) {
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
	public String getDonationDate() {
		return donationDate;
	}
	public void setDonationDate(String donationDate) {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	@Override
	public String toString() {
		return "DonationDump [id=" + id + ", ver=" + ver + ", dateCreated=" + dateCreated + ", dateModified=" + dateModified + ", creatorId=" + creatorId
				+ ", modifierId=" + modifierId + ", donorId=" + donorId + ", merchantReferenceNumber=" + merchantReferenceNumber + ", transactionId="
				+ transactionId + ", donorName=" + donorName + ", donorGender=" + donorGender + ", donorAge=" + donorAge + ", donorMobile=" + donorMobile
				+ ", donorEmail=" + donorEmail + ", donorCountryId=" + donorCountryId + ", donorStateId=" + donorStateId + ", donorDistrictId="
				+ donorDistrictId + ", donorAddress=" + donorAddress + ", paymentGateway=" + paymentGateway + ", transactionType=" + transactionType
				+ ", donationDate=" + donationDate + ", donorIp=" + donorIp + ", amount=" + amount + ", utmSource=" + utmSource + ", utmMedium=" + utmMedium
				+ ", utmTerm=" + utmTerm + ", utmContent=" + utmContent + ", utmCampaign=" + utmCampaign + ", pgErrorMessage=" + pgErrorMessage + ", cid="
				+ cid + ", lid=" + lid + ", pgErrorDetail=" + pgErrorDetail + ", remark=" + remark + ", status=" + status + ", statusMessage=" + statusMessage
				+ "]";
	}
	

	
	
}
