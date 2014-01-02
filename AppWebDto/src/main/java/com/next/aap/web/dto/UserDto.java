package com.next.aap.web.dto;

import java.io.Serializable;
import java.util.Date;

public class UserDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String externalId;
	private String membershipNumber;
	private String name;
	private String fatherName;
	private String motherName;
	private String address;
	private String gender;
	private Date dateOfBirth;
	private boolean nri;
	private Long nriCountryId;
	private Long stateLivingId;
	private Long districtLivingId;
	private Long assemblyConstituencyLivingId;
	private Long parliamentConstituencyLivingId;
	private Long stateVotingId;
	private Long districtVotingId;
	private Long assemblyConstituencyVotingId;
	private Long parliamentConstituencyVotingId;
	private boolean allowTweets;
	private String profilePic;
	private boolean superAdmin;
	private boolean member;
	private boolean volunteer;
	private String memberPic;
	private String countryCode;
	private String mobileNumber;
	private String membershipStatus;
	private String passportNumber;
	private String voterId;
	private String email;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isNri() {
		return nri;
	}

	public void setNri(boolean nri) {
		this.nri = nri;
	}

	public Long getNriCountryId() {
		return nriCountryId;
	}

	public void setNriCountryId(Long nriCountryId) {
		this.nriCountryId = nriCountryId;
	}

	public String getMembershipNumber() {
		return membershipNumber;
	}

	public void setMembershipNumber(String membershipNumber) {
		this.membershipNumber = membershipNumber;
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

	public boolean isAllowTweets() {
		return allowTweets;
	}

	public void setAllowTweets(boolean allowTweets) {
		this.allowTweets = allowTweets;
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

	public String getMemberPic() {
		return memberPic;
	}

	public void setMemberPic(String memberPic) {
		this.memberPic = memberPic;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isVolunteer() {
		return volunteer;
	}

	public void setVolunteer(boolean volunteer) {
		this.volunteer = volunteer;
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

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", externalId=" + externalId + ", membershipNumber=" + membershipNumber + ", name=" + name + ", fatherName=" + fatherName
				+ ", motherName=" + motherName + ", address=" + address + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", nri=" + nri
				+ ", nriCountryId=" + nriCountryId + ", stateLivingId=" + stateLivingId + ", districtLivingId=" + districtLivingId
				+ ", assemblyConstituencyLivingId=" + assemblyConstituencyLivingId + ", parliamentConstituencyLivingId=" + parliamentConstituencyLivingId
				+ ", stateVotingId=" + stateVotingId + ", districtVotingId=" + districtVotingId + ", assemblyConstituencyVotingId="
				+ assemblyConstituencyVotingId + ", parliamentConstituencyVotingId=" + parliamentConstituencyVotingId + ", allowTweets=" + allowTweets
				+ ", profilePic=" + profilePic + ", superAdmin=" + superAdmin + ", member=" + member + ", memberPic=" + memberPic + ", countryCode="
				+ countryCode + ", mobileNumber=" + mobileNumber + "]";
	}

}
