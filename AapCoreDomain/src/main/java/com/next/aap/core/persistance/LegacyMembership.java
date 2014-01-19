package com.next.aap.core.persistance;

import java.util.Date;


public class LegacyMembership {

	private Long id;
	private String email;
	private Long membershipNo;
	private String name;
	private String mobile;
	private String state;
	private String district;
	private String loksabha;
	private String vidhansabha;
	private Date dateCreated;
	private String payStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getMembershipNo() {
		return membershipNo;
	}
	public void setMembershipNo(Long membershipNo) {
		this.membershipNo = membershipNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getLoksabha() {
		return loksabha;
	}
	public void setLoksabha(String loksabha) {
		this.loksabha = loksabha;
	}
	public String getVidhansabha() {
		return vidhansabha;
	}
	public void setVidhansabha(String vidhansabha) {
		this.vidhansabha = vidhansabha;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	@Override
	public String toString() {
		return "LegacyMembership [id=" + id + ", email=" + email + ", membershipNo=" + membershipNo + ", name=" + name + ", mobile=" + mobile + ", state="
				+ state + ", district=" + district + ", loksabha=" + loksabha + ", vidhansabha=" + vidhansabha + ", dateCreated=" + dateCreated
				+ ", payStatus=" + payStatus + "]";
	}



}
