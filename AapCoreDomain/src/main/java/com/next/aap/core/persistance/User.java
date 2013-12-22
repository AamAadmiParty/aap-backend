package com.next.aap.core.persistance;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="users")
public class User {

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

	
	@Column(name = "external_id", nullable = false, length=256)
	private String externalId;

	@Column(name = "name", nullable = false, length=256)
	private String name;
	
	@Column(name = "gender")
	private String gender;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="living_state_id")
    private State stateLiving;
	@Column(name="living_state_id", insertable=false,updatable=false)
	private Long stateLivingId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="living_district_id")
    private District districtLiving;
	@Column(name="living_district_id", insertable=false,updatable=false)
	private Long districtLivingId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="living_ac_id")
    private AssemblyConstituency assemblyConstituencyLiving;
	@Column(name="living_ac_id", insertable=false,updatable=false)
	private Long assemblyConstituencyLivingId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="living_pc_id")
    private ParliamentConstituency parliamentConstituencyLiving;
	@Column(name="living_pc_id", insertable=false,updatable=false)
	private Long parliamentConstituencyLivingId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="voting_state_id")
    private State stateVoting;
	@Column(name="voting_state_id", insertable=false,updatable=false)
	private Long stateVotingId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="voting_district_id")
    private District districtVoting;
	@Column(name="voting_district_id", insertable=false,updatable=false)
	private Long districtVotingId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="voting_ac_id")
    private AssemblyConstituency assemblyConstituencyVoting;
	@Column(name="voting_ac_id", insertable=false,updatable=false)
	private Long assemblyConstituencyVotingId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="voting_pc_id")
    private ParliamentConstituency parliamentConstituencyVoting;
	@Column(name="voting_pc_id", insertable=false,updatable=false)
	private Long parliamentConstituencyVotingId;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<FacebookAccount> facebookAccounts;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<TwitterAccount> twitterAccounts; 
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_state_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="role_id")
	})
	Set<Role> stateRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_district_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="role_id")
	})
	Set<Role> districtRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_ac_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="role_id")
	})
	Set<Role> acRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_pc_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="role_id")
	})
	Set<Role> pcRoles;

	

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public State getStateLiving() {
		return stateLiving;
	}

	public void setStateLiving(State stateLiving) {
		this.stateLiving = stateLiving;
	}

	public Long getStateLivingId() {
		return stateLivingId;
	}

	public void setStateLivingId(Long stateLivingId) {
		this.stateLivingId = stateLivingId;
	}

	public District getDistrictLiving() {
		return districtLiving;
	}

	public void setDistrictLiving(District districtLiving) {
		this.districtLiving = districtLiving;
	}

	public Long getDistrictLivingId() {
		return districtLivingId;
	}

	public void setDistrictLivingId(Long districtLivingId) {
		this.districtLivingId = districtLivingId;
	}

	public AssemblyConstituency getAssemblyConstituencyLiving() {
		return assemblyConstituencyLiving;
	}

	public void setAssemblyConstituencyLiving(
			AssemblyConstituency assemblyConstituencyLiving) {
		this.assemblyConstituencyLiving = assemblyConstituencyLiving;
	}

	public Long getAssemblyConstituencyLivingId() {
		return assemblyConstituencyLivingId;
	}

	public void setAssemblyConstituencyLivingId(Long assemblyConstituencyLivingId) {
		this.assemblyConstituencyLivingId = assemblyConstituencyLivingId;
	}

	public ParliamentConstituency getParliamentConstituencyLiving() {
		return parliamentConstituencyLiving;
	}

	public void setParliamentConstituencyLiving(
			ParliamentConstituency parliamentConstituencyLiving) {
		this.parliamentConstituencyLiving = parliamentConstituencyLiving;
	}

	public Long getParliamentConstituencyLivingId() {
		return parliamentConstituencyLivingId;
	}

	public void setParliamentConstituencyLivingId(
			Long parliamentConstituencyLivingId) {
		this.parliamentConstituencyLivingId = parliamentConstituencyLivingId;
	}

	public State getStateVoting() {
		return stateVoting;
	}

	public void setStateVoting(State stateVoting) {
		this.stateVoting = stateVoting;
	}

	public Long getStateVotingId() {
		return stateVotingId;
	}

	public void setStateVotingId(Long stateVotingId) {
		this.stateVotingId = stateVotingId;
	}

	public District getDistrictVoting() {
		return districtVoting;
	}

	public void setDistrictVoting(District districtVoting) {
		this.districtVoting = districtVoting;
	}

	public Long getDistrictVotingId() {
		return districtVotingId;
	}

	public void setDistrictVotingId(Long districtVotingId) {
		this.districtVotingId = districtVotingId;
	}

	public AssemblyConstituency getAssemblyConstituencyVoting() {
		return assemblyConstituencyVoting;
	}

	public void setAssemblyConstituencyVoting(
			AssemblyConstituency assemblyConstituencyVoting) {
		this.assemblyConstituencyVoting = assemblyConstituencyVoting;
	}

	public Long getAssemblyConstituencyVotingId() {
		return assemblyConstituencyVotingId;
	}

	public void setAssemblyConstituencyVotingId(Long assemblyConstituencyVotingId) {
		this.assemblyConstituencyVotingId = assemblyConstituencyVotingId;
	}

	public ParliamentConstituency getParliamentConstituencyVoting() {
		return parliamentConstituencyVoting;
	}

	public void setParliamentConstituencyVoting(
			ParliamentConstituency parliamentConstituencyVoting) {
		this.parliamentConstituencyVoting = parliamentConstituencyVoting;
	}

	public Long getParliamentConstituencyVotingId() {
		return parliamentConstituencyVotingId;
	}

	public void setParliamentConstituencyVotingId(
			Long parliamentConstituencyVotingId) {
		this.parliamentConstituencyVotingId = parliamentConstituencyVotingId;
	}

	public Set<Role> getStateRoles() {
		return stateRoles;
	}

	public void setStateRoles(Set<Role> stateRoles) {
		this.stateRoles = stateRoles;
	}

	public Set<Role> getDistrictRoles() {
		return districtRoles;
	}

	public void setDistrictRoles(Set<Role> districtRoles) {
		this.districtRoles = districtRoles;
	}

	public Set<Role> getAcRoles() {
		return acRoles;
	}

	public void setAcRoles(Set<Role> acRoles) {
		this.acRoles = acRoles;
	}

	public Set<Role> getPcRoles() {
		return pcRoles;
	}

	public void setPcRoles(Set<Role> pcRoles) {
		this.pcRoles = pcRoles;
	}

	public Set<FacebookAccount> getFacebookAccounts() {
		return facebookAccounts;
	}

	public void setFacebookAccounts(Set<FacebookAccount> facebookAccounts) {
		this.facebookAccounts = facebookAccounts;
	}

	public Set<TwitterAccount> getTwitterAccounts() {
		return twitterAccounts;
	}

	public void setTwitterAccounts(Set<TwitterAccount> twitterAccounts) {
		this.twitterAccounts = twitterAccounts;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", ver=" + ver + ", externalId=" + externalId
				+ ", name=" + name + ", dateOfBith=" + dateOfBirth + "]";
	}

}
