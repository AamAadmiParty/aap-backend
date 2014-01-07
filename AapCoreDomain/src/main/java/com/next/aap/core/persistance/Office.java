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
@Table(name="offices")
public class Office {
	
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

	@Column(name = "lattitude")
	private double lattitude;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "map_default_depth")
	private double depth;

	@Column(name = "address", length=512)
	private String address;

	@Column(name = "landline_number1", length=16)
	private String landlineNumber1;

	@Column(name = "landline_number2", length=16)
	private String landlineNumber2;

	@Column(name = "mobile_number1", length=16)
	private String mobileNumber1;

	@Column(name = "mobile_number2", length=16)
	private String mobileNumber2;

	@Column(name = "fb_page_id")
	private String fbPageId;

	@Column(name = "fb_group_id")
	private String fbGroupId;

	@Column(name = "twitter_handle")
	private String twitterHandle;

	@Column(name = "national")
	private boolean national;

	@Column(name = "other_info",  columnDefinition="LONGTEXT")
	private String otherInformation;//content of news which can be html or plain text

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="state_id")
    private State state;
	@Column(name="state_id", insertable=false,updatable=false)
	private Long stateId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="district_id")
    private District district;
	@Column(name="district_id", insertable=false,updatable=false)
	private Long districtId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="ac_id")
    private AssemblyConstituency assemblyConstituency;
	@Column(name="ac_id", insertable=false,updatable=false)
	private Long assemblyConstituencyId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="pc_id")
    private ParliamentConstituency parliamentConstituency;
	@Column(name="pc_id", insertable=false,updatable=false)
	private Long parliamentConstituencyId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="country_id")
    private Country country;
	@Column(name="country_id", insertable=false,updatable=false)
	private Long countryId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="country_region_id")
    private CountryRegion countryRegion;
	@Column(name="country_region_id", insertable=false,updatable=false)
	private Long countryRegionId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="country_region_area_id")
    private CountryRegionArea countryRegionArea;
	@Column(name="country_region_area_id", insertable=false,updatable=false)
	private Long countryRegionAreaId;
	
	
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
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public AssemblyConstituency getAssemblyConstituency() {
		return assemblyConstituency;
	}
	public void setAssemblyConstituency(AssemblyConstituency assemblyConstituency) {
		this.assemblyConstituency = assemblyConstituency;
	}
	public Long getAssemblyConstituencyId() {
		return assemblyConstituencyId;
	}
	public void setAssemblyConstituencyId(Long assemblyConstituencyId) {
		this.assemblyConstituencyId = assemblyConstituencyId;
	}
	public ParliamentConstituency getParliamentConstituency() {
		return parliamentConstituency;
	}
	public void setParliamentConstituency(ParliamentConstituency parliamentConstituency) {
		this.parliamentConstituency = parliamentConstituency;
	}
	public Long getParliamentConstituencyId() {
		return parliamentConstituencyId;
	}
	public void setParliamentConstituencyId(Long parliamentConstituencyId) {
		this.parliamentConstituencyId = parliamentConstituencyId;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public CountryRegion getCountryRegion() {
		return countryRegion;
	}
	public void setCountryRegion(CountryRegion countryRegion) {
		this.countryRegion = countryRegion;
	}
	public Long getCountryRegionId() {
		return countryRegionId;
	}
	public void setCountryRegionId(Long countryRegionId) {
		this.countryRegionId = countryRegionId;
	}
	public CountryRegionArea getCountryRegionArea() {
		return countryRegionArea;
	}
	public void setCountryRegionArea(CountryRegionArea countryRegionArea) {
		this.countryRegionArea = countryRegionArea;
	}
	public Long getCountryRegionAreaId() {
		return countryRegionAreaId;
	}
	public void setCountryRegionAreaId(Long countryRegionAreaId) {
		this.countryRegionAreaId = countryRegionAreaId;
	}
	
	


}
