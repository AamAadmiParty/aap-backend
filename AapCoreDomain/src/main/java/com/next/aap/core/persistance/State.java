package com.next.aap.core.persistance;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="states")
public class State {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,  generator="STATE_SEQ_GEN")
	@SequenceGenerator(
    name="STATE_SEQ_GEN",
    sequenceName="STATE_SEQ",
    initialValue=1000,
    allocationSize=10
	)
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

	
	@Column(name = "name", nullable = false, length=256)
	private String name;
	
	@Column(name = "name_up", nullable = false, length=256)
	private String nameUp;
	
	@Column(name = "district_data_available")
	private Boolean districtDataAvailable;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "state_campaigns",
	joinColumns = {
	@JoinColumn(name="state_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="location_campaign_id")
	})
	private Set<LocationCampaign> campaigns;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNameUp() {
		return nameUp;
	}

	public void setNameUp(String nameUp) {
		this.nameUp = nameUp;
	}

	public Boolean getDistrictDataAvailable() {
		return districtDataAvailable;
	}

	public void setDistrictDataAvailable(Boolean districtDataAvailable) {
		this.districtDataAvailable = districtDataAvailable;
	}

	public Set<LocationCampaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(Set<LocationCampaign> campaigns) {
		this.campaigns = campaigns;
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
		State other = (State) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
