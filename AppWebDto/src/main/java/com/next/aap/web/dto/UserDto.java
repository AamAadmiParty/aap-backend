package com.next.aap.web.dto;

import java.io.Serializable;
import java.util.Date;

public class UserDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private int ver;
	
	private Date dateCreated;
	private Date dateModified;
	private Long creatorId;
	private Long modifierId;

	
	private String externalId;

	private String name;
	
	private String gender;
	
	private Date dateOfBirth;

	private Long stateLivingId;

	private Long districtLivingId;

	private Long assemblyConstituencyLivingId;
	
	private Long parliamentConstituencyLivingId;

	private Long stateVotingId;

	private Long districtVotingId;

	private Long assemblyConstituencyVotingId;

	private Long parliamentConstituencyVotingId;
	
	


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

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Long getStateLivingId() {
		return stateLivingId;
	}

	public void setStateLivingId(Long stateLivingId) {
		this.stateLivingId = stateLivingId;
	}

	public Long getDistrictLivingId() {
		return districtLivingId;
	}

	public void setDistrictLivingId(Long districtLivingId) {
		this.districtLivingId = districtLivingId;
	}

	public Long getAssemblyConstituencyLivingId() {
		return assemblyConstituencyLivingId;
	}

	public void setAssemblyConstituencyLivingId(Long assemblyConstituencyLivingId) {
		this.assemblyConstituencyLivingId = assemblyConstituencyLivingId;
	}

	public Long getParliamentConstituencyLivingId() {
		return parliamentConstituencyLivingId;
	}

	public void setParliamentConstituencyLivingId(
			Long parliamentConstituencyLivingId) {
		this.parliamentConstituencyLivingId = parliamentConstituencyLivingId;
	}

	public Long getStateVotingId() {
		return stateVotingId;
	}

	public void setStateVotingId(Long stateVotingId) {
		this.stateVotingId = stateVotingId;
	}

	public Long getDistrictVotingId() {
		return districtVotingId;
	}

	public void setDistrictVotingId(Long districtVotingId) {
		this.districtVotingId = districtVotingId;
	}

	public Long getAssemblyConstituencyVotingId() {
		return assemblyConstituencyVotingId;
	}

	public void setAssemblyConstituencyVotingId(Long assemblyConstituencyVotingId) {
		this.assemblyConstituencyVotingId = assemblyConstituencyVotingId;
	}

	public Long getParliamentConstituencyVotingId() {
		return parliamentConstituencyVotingId;
	}

	public void setParliamentConstituencyVotingId(
			Long parliamentConstituencyVotingId) {
		this.parliamentConstituencyVotingId = parliamentConstituencyVotingId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
		UserDto other = (UserDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
