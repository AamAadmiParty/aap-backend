package com.next.aap.web.dto;


public class OfficeDto {
	
	private Long id;
	private double lattitude;
	private double longitude;
	private double depth;
	private String address;
	private String landlineNumber1;
	private String landlineNumber2;
	private String mobileNumber1;
	private String mobileNumber2;
	private String fbPageId;
	private String fbGroupId;
	private String twitterHandle;
	private boolean national;
	private String otherInformation;//content of news which can be html or plain text
	private Long stateId;
	private Long districtId;
	private Long assemblyConstituencyId;
	private Long parliamentConstituencyId;
	private Long countryId;
	private Long countryRegionId;
	private Long countryRegionAreaId;
	private String stateName;
	private String districtName;
	private String assemblyConstituencyName;
	private String parliamentConstituencyName;
	private String countryName;
	private String email;
	private String countryRegionName;
	private String countryRegionAreaName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getDepth() {
		return depth;
	}
	public void setDepth(double depth) {
		this.depth = depth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLandlineNumber1() {
		return landlineNumber1;
	}
	public void setLandlineNumber1(String landlineNumber1) {
		this.landlineNumber1 = landlineNumber1;
	}
	public String getLandlineNumber2() {
		return landlineNumber2;
	}
	public void setLandlineNumber2(String landlineNumber2) {
		this.landlineNumber2 = landlineNumber2;
	}
	public String getMobileNumber1() {
		return mobileNumber1;
	}
	public void setMobileNumber1(String mobileNumber1) {
		this.mobileNumber1 = mobileNumber1;
	}
	public String getMobileNumber2() {
		return mobileNumber2;
	}
	public void setMobileNumber2(String mobileNumber2) {
		this.mobileNumber2 = mobileNumber2;
	}
	public String getFbPageId() {
		return fbPageId;
	}
	public void setFbPageId(String fbPageId) {
		this.fbPageId = fbPageId;
	}
	public String getFbGroupId() {
		return fbGroupId;
	}
	public void setFbGroupId(String fbGroupId) {
		this.fbGroupId = fbGroupId;
	}
	public String getTwitterHandle() {
		return twitterHandle;
	}
	public void setTwitterHandle(String twitterHandle) {
		this.twitterHandle = twitterHandle;
	}
	public boolean isNational() {
		return national;
	}
	public void setNational(boolean national) {
		this.national = national;
	}
	public String getOtherInformation() {
		return otherInformation;
	}
	public void setOtherInformation(String otherInformation) {
		this.otherInformation = otherInformation;
	}
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
	public Long getAssemblyConstituencyId() {
		return assemblyConstituencyId;
	}
	public void setAssemblyConstituencyId(Long assemblyConstituencyId) {
		this.assemblyConstituencyId = assemblyConstituencyId;
	}
	public Long getParliamentConstituencyId() {
		return parliamentConstituencyId;
	}
	public void setParliamentConstituencyId(Long parliamentConstituencyId) {
		this.parliamentConstituencyId = parliamentConstituencyId;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public Long getCountryRegionId() {
		return countryRegionId;
	}
	public void setCountryRegionId(Long countryRegionId) {
		this.countryRegionId = countryRegionId;
	}
	public Long getCountryRegionAreaId() {
		return countryRegionAreaId;
	}
	public void setCountryRegionAreaId(Long countryRegionAreaId) {
		this.countryRegionAreaId = countryRegionAreaId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getAssemblyConstituencyName() {
		return assemblyConstituencyName;
	}
	public void setAssemblyConstituencyName(String assemblyConstituencyName) {
		this.assemblyConstituencyName = assemblyConstituencyName;
	}
	public String getParliamentConstituencyName() {
		return parliamentConstituencyName;
	}
	public void setParliamentConstituencyName(String parliamentConstituencyName) {
		this.parliamentConstituencyName = parliamentConstituencyName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryRegionName() {
		return countryRegionName;
	}
	public void setCountryRegionName(String countryRegionName) {
		this.countryRegionName = countryRegionName;
	}
	public String getCountryRegionAreaName() {
		return countryRegionAreaName;
	}
	public void setCountryRegionAreaName(String countryRegionAreaName) {
		this.countryRegionAreaName = countryRegionAreaName;
	}
	
	public String getLocationName(){
		if(countryName != null){
			return countryName;
		}
		if(stateName != null){
			return stateName;
		}
		if(districtName != null){
			return districtName;
		}
		if(assemblyConstituencyName != null){
			return assemblyConstituencyName;
		}
		if(parliamentConstituencyName != null){
			return parliamentConstituencyName;
		}
		if(countryRegionName != null){
			return countryRegionName;
		}
		if(countryRegionAreaName != null){
			return countryRegionAreaName;
		}
		if(national){
			return "Head Office";
		}
		return "";
	}
	@Override
	public String toString() {
		return "OfficeDto [id=" + id + ", lattitude=" + lattitude + ", longitude=" + longitude + ", depth=" + depth + ", address=" + address
				+ ", landlineNumber1=" + landlineNumber1 + ", landlineNumber2=" + landlineNumber2 + ", mobileNumber1=" + mobileNumber1 + ", mobileNumber2="
				+ mobileNumber2 + ", fbPageId=" + fbPageId + ", fbGroupId=" + fbGroupId + ", twitterHandle=" + twitterHandle + ", national=" + national
				+ ", otherInformation=" + otherInformation + ", stateId=" + stateId + ", districtId=" + districtId + ", assemblyConstituencyId="
				+ assemblyConstituencyId + ", parliamentConstituencyId=" + parliamentConstituencyId + ", countryId=" + countryId + ", countryRegionId="
				+ countryRegionId + ", countryRegionAreaId=" + countryRegionAreaId + ", stateName=" + stateName + ", districtName=" + districtName
				+ ", assemblyConstituencyName=" + assemblyConstituencyName + ", parliamentConstituencyName=" + parliamentConstituencyName + ", countryName="
				+ countryName + ", email=" + email + ", countryRegionName=" + countryRegionName + ", countryRegionAreaName=" + countryRegionAreaName 
				+ ", locationName=" + getLocationName() +"]";
	}
	


}
