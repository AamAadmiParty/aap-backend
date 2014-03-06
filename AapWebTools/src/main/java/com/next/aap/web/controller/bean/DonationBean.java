package com.next.aap.web.controller.bean;

public class DonationBean {

	private Long stateId;
	private Long districtId;
	private Long parliamentConstituencyId;
	private Long assemblyConstituencyId;
	private String donationLocation;
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public Long getParliamentConstituencyId() {
		return parliamentConstituencyId;
	}
	public void setParliamentConstituencyId(Long parliamentConstituencyId) {
		this.parliamentConstituencyId = parliamentConstituencyId;
	}
	public Long getAssemblyConstituencyId() {
		return assemblyConstituencyId;
	}
	public void setAssemblyConstituencyId(Long assemblyConstituencyId) {
		this.assemblyConstituencyId = assemblyConstituencyId;
	}
	public String getDonationLocation() {
		return donationLocation;
	}
	public void setDonationLocation(String donationLocation) {
		this.donationLocation = donationLocation;
	}
	@Override
	public String toString() {
		return "DonationBean [stateId=" + stateId + ", districtId=" + districtId + ", parliamentConstituencyId=" + parliamentConstituencyId
				+ ", assemblyConstituencyId=" + assemblyConstituencyId + ", donationLocation=" + donationLocation + "]";
	}
	
	
}
