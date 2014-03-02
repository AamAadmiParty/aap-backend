package com.next.aap.core.persistance;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="events")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="Account", include="all")
public class Event {

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

	@Column(name="start_date")
	private Date startDate;

	@Column(name="end_date")
	private Date endDate;
	
	@Column(name = "lattitude")
	private double lattitude;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "map_default_depth")
	private int depth;

	@Column(name = "address", length=512)
	private String address;
	
	@Column(name = "contact_number1", length=16)
	private String contactNumber1;

	@Column(name = "contact_number2", length=16)
	private String contactNumber2;

	@Column(name = "contact_number3", length=16)
	private String contactNumber3;

	@Column(name = "contact_number4", length=16)
	private String contactNumber4;

	@Column(name = "fb_event_id", length=32)
	private String fbEventId;

	@Column(name = "national")
	private boolean national;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "event_ac",
	joinColumns = {
	@JoinColumn(name="event_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="ac_id")
	})
	private List<AssemblyConstituency> assemblyConstituencies;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "event_pc",
	joinColumns = {
	@JoinColumn(name="event_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="pc_id")
	})
	private List<ParliamentConstituency> parliamentConstituencies;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "event_district",
	joinColumns = {
	@JoinColumn(name="event_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="district_id")
	})
	private List<District> districts;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "event_state",
	joinColumns = {
	@JoinColumn(name="event_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="state_id")
	})
	private List<State> states;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "event_country",
	joinColumns = {
	@JoinColumn(name="event_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="country_id")
	})
	private List<Country> countries;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "event_country_region",
	joinColumns = {
	@JoinColumn(name="event_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="country_region_id")
	})
	private List<CountryRegion> countryRegions;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "event_country_region_area",
	joinColumns = {
	@JoinColumn(name="event_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="country_region_area_id")
	})
	private List<CountryRegionArea> countryRegionsAreas;

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

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber1() {
		return contactNumber1;
	}

	public void setContactNumber1(String contactNumber1) {
		this.contactNumber1 = contactNumber1;
	}

	public String getContactNumber2() {
		return contactNumber2;
	}

	public void setContactNumber2(String contactNumber2) {
		this.contactNumber2 = contactNumber2;
	}

	public String getContactNumber3() {
		return contactNumber3;
	}

	public void setContactNumber3(String contactNumber3) {
		this.contactNumber3 = contactNumber3;
	}

	public String getContactNumber4() {
		return contactNumber4;
	}

	public void setContactNumber4(String contactNumber4) {
		this.contactNumber4 = contactNumber4;
	}

	public String getFbEventId() {
		return fbEventId;
	}

	public void setFbEventId(String fbEventId) {
		this.fbEventId = fbEventId;
	}

	public boolean isNational() {
		return national;
	}

	public void setNational(boolean national) {
		this.national = national;
	}

}
