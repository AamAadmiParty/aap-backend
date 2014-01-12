package com.next.aap.core.persistance;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.next.aap.web.dto.CreationType;

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

	@Column(name = "membership_no")
	private String membershipNumber;

	@Column(name = "name", nullable = false, length=256)
	private String name;
	
	@Column(name = "father_name")
	private String fatherName;

	@Column(name = "mother_name")
	private String motherName;

	@Column(name = "address", length=512)
	private String address;

	@Column(name = "gender")
	private String gender;

	@Column(name = "creation_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private CreationType creationType;

	
	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "nri")
	private boolean nri;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="nri_country_id")
    private Country nriCountry;
	@Column(name="nri_country_id", insertable=false,updatable=false)
	private Long nriCountryId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="nri_country_region_id")
    private CountryRegion nriCountryRegion;
	@Column(name="nri_country_region_id", insertable=false,updatable=false)
	private Long nriCountryRegionId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="nri_country_region_area_id")
    private CountryRegionArea nriCountryRegionArea;
	@Column(name="nri_country_region_area_id", insertable=false,updatable=false)
	private Long nriCountryRegionAreaId;

	
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
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<Donation> donations;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<Email> emails;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<Phone> phones;

	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_all_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="role_id")
	})
	Set<Role> allRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_state_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="state_role_id")
	})
	Set<StateRole> stateRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_district_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="district_role_id")
	})
	Set<DistrictRole> districtRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_ac_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="ac_role_id")
	})
	Set<AcRole> acRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_pc_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="pc_role_id")
	})
	Set<PcRole> pcRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_country_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="country_role_id")
	})
	Set<CountryRole> countryRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_country_region_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="country_region_role_id")
	})
	Set<CountryRegionRole> countryRegionRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_country_region_area_roles",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="country_region_area_role_id")
	})
	Set<CountryRegionAreaRole> countryRegionAreaRoles;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "user_interests",
	joinColumns = {
	@JoinColumn(name="user_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="interest_id")
	})
	Set<Interest> interests;

	@Column(name = "allow_tweets", nullable = false)
	private boolean allowTweets;

	@Column(name = "profile_pic")
	private String profilePic;

	@Column(name = "super_admin", nullable = false)
	private boolean superAdmin;

	@Column(name = "member", nullable = false)
	private boolean member;
	
	@Column(name = "donor", nullable = false)
	private boolean donor;

	@Column(name = "volunteer", nullable = false)
	private boolean volunteer;

	@Column(name = "membership_status")
	private String membershipStatus;
	
	@Column(name = "passport_number")
	private String passportNumber;

	@Column(name = "voter_id")
	private String voterId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="membership_confirmed_by")
    private User membershipConfirmedBy;
	@Column(name="membership_confirmed_by", insertable=false,updatable=false)
	private Long membershipConfirmedById;
	
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

	public String getMembershipNumber() {
		return membershipNumber;
	}

	public void setMembershipNumber(String membershipNumber) {
		this.membershipNumber = membershipNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isNri() {
		return nri;
	}

	public void setNri(boolean nri) {
		this.nri = nri;
	}

	public Country getNriCountry() {
		return nriCountry;
	}

	public void setNriCountry(Country nriCountry) {
		this.nriCountry = nriCountry;
	}

	public Long getNriCountryId() {
		return nriCountryId;
	}

	public void setNriCountryId(Long nriCountryId) {
		this.nriCountryId = nriCountryId;
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

	public void setAssemblyConstituencyLiving(AssemblyConstituency assemblyConstituencyLiving) {
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

	public void setParliamentConstituencyLiving(ParliamentConstituency parliamentConstituencyLiving) {
		this.parliamentConstituencyLiving = parliamentConstituencyLiving;
	}

	public Long getParliamentConstituencyLivingId() {
		return parliamentConstituencyLivingId;
	}

	public void setParliamentConstituencyLivingId(Long parliamentConstituencyLivingId) {
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

	public void setAssemblyConstituencyVoting(AssemblyConstituency assemblyConstituencyVoting) {
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

	public void setParliamentConstituencyVoting(ParliamentConstituency parliamentConstituencyVoting) {
		this.parliamentConstituencyVoting = parliamentConstituencyVoting;
	}

	public Long getParliamentConstituencyVotingId() {
		return parliamentConstituencyVotingId;
	}

	public void setParliamentConstituencyVotingId(Long parliamentConstituencyVotingId) {
		this.parliamentConstituencyVotingId = parliamentConstituencyVotingId;
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

	public Set<Role> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(Set<Role> allRoles) {
		this.allRoles = allRoles;
	}

	public Set<StateRole> getStateRoles() {
		return stateRoles;
	}

	public void setStateRoles(Set<StateRole> stateRoles) {
		this.stateRoles = stateRoles;
	}

	public Set<DistrictRole> getDistrictRoles() {
		return districtRoles;
	}

	public void setDistrictRoles(Set<DistrictRole> districtRoles) {
		this.districtRoles = districtRoles;
	}

	public Set<AcRole> getAcRoles() {
		return acRoles;
	}

	public void setAcRoles(Set<AcRole> acRoles) {
		this.acRoles = acRoles;
	}

	public Set<PcRole> getPcRoles() {
		return pcRoles;
	}

	public void setPcRoles(Set<PcRole> pcRoles) {
		this.pcRoles = pcRoles;
	}

	public boolean isAllowTweets() {
		return allowTweets;
	}

	public void setAllowTweets(boolean allowTweets) {
		this.allowTweets = allowTweets;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}

	public String getMembershipStatus() {
		return membershipStatus;
	}

	public void setMembershipStatus(String membershipStatus) {
		this.membershipStatus = membershipStatus;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	public User getMembershipConfirmedBy() {
		return membershipConfirmedBy;
	}

	public void setMembershipConfirmedBy(User membershipConfirmedBy) {
		this.membershipConfirmedBy = membershipConfirmedBy;
	}

	public Long getMembershipConfirmedById() {
		return membershipConfirmedById;
	}

	public void setMembershipConfirmedById(Long membershipConfirmedById) {
		this.membershipConfirmedById = membershipConfirmedById;
	}

	public Set<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

	public CountryRegion getNriCountryRegion() {
		return nriCountryRegion;
	}

	public void setNriCountryRegion(CountryRegion nriCountryRegion) {
		this.nriCountryRegion = nriCountryRegion;
	}

	public Long getNriCountryRegionId() {
		return nriCountryRegionId;
	}

	public void setNriCountryRegionId(Long nriCountryRegionId) {
		this.nriCountryRegionId = nriCountryRegionId;
	}

	public CountryRegionArea getNriCountryRegionArea() {
		return nriCountryRegionArea;
	}

	public void setNriCountryRegionArea(CountryRegionArea nriCountryRegionArea) {
		this.nriCountryRegionArea = nriCountryRegionArea;
	}

	public Long getNriCountryRegionAreaId() {
		return nriCountryRegionAreaId;
	}

	public void setNriCountryRegionAreaId(Long nriCountryRegionAreaId) {
		this.nriCountryRegionAreaId = nriCountryRegionAreaId;
	}

	public Set<CountryRole> getCountryRoles() {
		return countryRoles;
	}

	public void setCountryRoles(Set<CountryRole> countryRoles) {
		this.countryRoles = countryRoles;
	}

	public Set<CountryRegionRole> getCountryRegionRoles() {
		return countryRegionRoles;
	}

	public void setCountryRegionRoles(Set<CountryRegionRole> countryRegionRoles) {
		this.countryRegionRoles = countryRegionRoles;
	}

	public Set<CountryRegionAreaRole> getCountryRegionAreaRoles() {
		return countryRegionAreaRoles;
	}

	public void setCountryRegionAreaRoles(Set<CountryRegionAreaRole> countryRegionAreaRoles) {
		this.countryRegionAreaRoles = countryRegionAreaRoles;
	}

	public boolean isVolunteer() {
		return volunteer;
	}

	public void setVolunteer(boolean volunteer) {
		this.volunteer = volunteer;
	}

	public boolean isDonor() {
		return donor;
	}

	public void setDonor(boolean donor) {
		this.donor = donor;
	}

	public CreationType getCreationType() {
		return creationType;
	}

	public void setCreationType(CreationType creationType) {
		this.creationType = creationType;
	}

	public Set<Donation> getDonations() {
		return donations;
	}

	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
	}

	public Set<Email> getEmails() {
		return emails;
	}

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
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
