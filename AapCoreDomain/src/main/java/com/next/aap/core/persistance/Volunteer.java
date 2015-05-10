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
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="volunteer")
public class Volunteer {

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
	
	@Column(name = "education")
	private String education;
	@Column(name = "professional_background", length=512)
	private String professionalBackground;
	@Column(name = "domain_expertise")
	private String domainExpertise;
	@Column(name = "offences", length=512)
	private String offences;
	@Column(name = "emergency_contact_name")
	private String emergencyContactName;
	@Column(name = "emergency_contact_relation")
	private String emergencyContactRelation;
	@Column(name = "emergency_contact_no")
	private String emergencyContactNo;
	@Column(name = "info_recorded_by")
	private String infoRecordedBy;
	@Column(name = "info_recorded_at")
	private String infoRecordedAt;
    @Column(name = "past_volunteer")
    private boolean pastVolunteer;
    @Column(name = "past_organisation", length = 128)
    private String pastOrganisation;
    @Column(name = "know_existing_member")
    private boolean knowExistingMember;
    @Column(name = "existingMember", length = 256)
    private String existingMember;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="user_id")
    private User user;
	@Column(name="user_id", insertable=false,updatable=false)
	private Long userId;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "volunteer_interests",
	joinColumns = {
	@JoinColumn(name="volunteer_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="interest_id")
	})
	Set<Interest> interests;
	
	
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Set<Interest> getInterests() {
		return interests;
	}
	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
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
        return "Volunteer [id=" + id + ", ver=" + ver + ", dateCreated=" + dateCreated + ", dateModified=" + dateModified + ", creatorId=" + creatorId + ", modifierId=" + modifierId + ", education="
                + education + ", professionalBackground=" + professionalBackground + ", domainExpertise=" + domainExpertise + ", offences=" + offences + ", emergencyContactName="
                + emergencyContactName + ", emergencyContactRelation=" + emergencyContactRelation + ", emergencyContactNo=" + emergencyContactNo + ", infoRecordedBy=" + infoRecordedBy
                + ", infoRecordedAt=" + infoRecordedAt + ", user=" + user + ", userId=" + userId + ", interests=" + interests + "]";
    }

	
}
