package com.next.aap.web.dto;

import java.io.Serializable;


public class VolunteerDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String education;
	private String professionalBackground;
	private String domainExpertise;
	private String offences;
	private String emergencyContactName;
	private String emergencyContactRelation;
	private String emergencyContactNo;
	private String infoRecordedBy;
	private String infoRecordedAt;
	
	private Long userId;
    private boolean pastVolunteer;
    private String pastOrganisation;
    private boolean knowExistingMember;
    private String existingMember;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getProfessionalBackground() {
		return professionalBackground;
	}

	public void setProfessionalBackground(String professionalBackground) {
		this.professionalBackground = professionalBackground;
	}

	public String getDomainExpertise() {
		return domainExpertise;
	}

	public void setDomainExpertise(String domainExpertise) {
		this.domainExpertise = domainExpertise;
	}

	public String getOffences() {
		return offences;
	}

	public void setOffences(String offences) {
		this.offences = offences;
	}

	public String getEmergencyContactName() {
		return emergencyContactName;
	}

	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}

	public String getEmergencyContactRelation() {
		return emergencyContactRelation;
	}

	public void setEmergencyContactRelation(String emergencyContactRelation) {
		this.emergencyContactRelation = emergencyContactRelation;
	}

	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}

	public void setEmergencyContactNo(String emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}

	public String getInfoRecordedBy() {
		return infoRecordedBy;
	}

	public void setInfoRecordedBy(String infoRecordedBy) {
		this.infoRecordedBy = infoRecordedBy;
	}

	public String getInfoRecordedAt() {
		return infoRecordedAt;
	}

	public void setInfoRecordedAt(String infoRecordedAt) {
		this.infoRecordedAt = infoRecordedAt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    public boolean isPastVolunteer() {
        return pastVolunteer;
    }

    public void setPastVolunteer(boolean pastVolunteer) {
        this.pastVolunteer = pastVolunteer;
    }

    public String getPastOrganisation() {
        return pastOrganisation;
    }

    public void setPastOrganisation(String pastOrganisation) {
        this.pastOrganisation = pastOrganisation;
    }

    public boolean isKnowExistingMember() {
        return knowExistingMember;
    }

    public void setKnowExistingMember(boolean knowExistingMember) {
        this.knowExistingMember = knowExistingMember;
    }

    public String getExistingMember() {
        return existingMember;
    }

    public void setExistingMember(String existingMember) {
        this.existingMember = existingMember;
    }

    @Override
    public String toString() {
        return "VolunteerDto [id=" + id + ", education=" + education + ", professionalBackground=" + professionalBackground + ", domainExpertise=" + domainExpertise + ", offences=" + offences
                + ", emergencyContactName=" + emergencyContactName + ", emergencyContactRelation=" + emergencyContactRelation + ", emergencyContactNo=" + emergencyContactNo + ", infoRecordedBy="
                + infoRecordedBy + ", infoRecordedAt=" + infoRecordedAt + ", userId=" + userId + ", pastVolunteer=" + pastVolunteer + ", pastOrganisation=" + pastOrganisation + "]";
    }
	

	
}
