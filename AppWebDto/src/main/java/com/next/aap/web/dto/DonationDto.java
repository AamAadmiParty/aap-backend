package com.next.aap.web.dto;

import java.util.Date;

public class DonationDto {

	private Long id;
	private String merchantReferenceNumber;
	private String transactionId;
	private String paymentGateway;
	private String transactionType;
	private Date donationDate;
	private String donorIp;
	private Double amount;
	private Date date;
	private String utmSource;
	private String utmMedium;
	private String utmTerm;
	private String utmContent;
	private String utmCampaign;
	private String pgErrorMessage;
	private String cid;
	private String pgErrorDetail;
	private String remark;

	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
