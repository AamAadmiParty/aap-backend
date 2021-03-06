package com.next.aap.web.dto;

import java.util.Date;

public class CandidateDto {

	private Long id;
	private int ver;
	private Date dateCreated;
	private Date dateModified;
	private Long creatorId;
	private Long modifierId;

	private String name;
	private String content;//content of news which can be html or plain text
    private String contentSummary;// content of news which can be html or plain text
	private String stateIdExt;
	private String pcIdExt;
    private String acIdExt;
	private String landingPageUrlId;
	private String donatePageUrlId;
	private double lattitude;
	private double longitude;
	private int depth;
	private Long stateId;
	private Long parliamentConstituencyId;
	private String imageUrl;
	private String stateName;
	private String pcName;
	private String candidateFbPageId;
	private String twitterId;
	private String urlTextPart1;
	private String urlTextPart2;
	private String landingPageSmallUrl;
	private String landingPageFullUrl;
	private String donationPageFullUrl;
	private String locationCampaignId;
	private String imageUrl64;
	private String imageUrl32;
    private String candidateType;
    private Long assemblyConstituencyId;
    private String acName;
    private Long electionId;
    private String voteUrl;

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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStateIdExt() {
		return stateIdExt;
	}
	public void setStateIdExt(String stateIdExt) {
		this.stateIdExt = stateIdExt;
	}
	public String getPcIdExt() {
		return pcIdExt;
	}
	public void setPcIdExt(String pcIdExt) {
		this.pcIdExt = pcIdExt;
	}
	public String getLandingPageUrlId() {
		return landingPageUrlId;
	}
	public void setLandingPageUrlId(String landingPageUrlId) {
		this.landingPageUrlId = landingPageUrlId;
	}
	public String getDonatePageUrlId() {
		return donatePageUrlId;
	}
	public void setDonatePageUrlId(String donatePageUrlId) {
		this.donatePageUrlId = donatePageUrlId;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public Long getParliamentConstituencyId() {
		return parliamentConstituencyId;
	}
	public void setParliamentConstituencyId(Long parliamentConstituencyId) {
		this.parliamentConstituencyId = parliamentConstituencyId;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public String getCandidateFbPageId() {
		return candidateFbPageId;
	}
	public void setCandidateFbPageId(String candidateFbPageId) {
		this.candidateFbPageId = candidateFbPageId;
	}
	public String getTwitterId() {
		return twitterId;
	}
	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}
	public String getUrlTextPart1() {
		return urlTextPart1;
	}
	public void setUrlTextPart1(String urlTextPart1) {
		this.urlTextPart1 = urlTextPart1;
	}
	public String getUrlTextPart2() {
		return urlTextPart2;
	}
	public void setUrlTextPart2(String urlTextPart2) {
		this.urlTextPart2 = urlTextPart2;
	}
	public String getLandingPageSmallUrl() {
		return landingPageSmallUrl;
	}
	public void setLandingPageSmallUrl(String landingPageSmallUrl) {
		this.landingPageSmallUrl = landingPageSmallUrl;
	}
	public String getLandingPageFullUrl() {
		return landingPageFullUrl;
	}
	public void setLandingPageFullUrl(String landingPageFullUrl) {
		this.landingPageFullUrl = landingPageFullUrl;
	}
	public String getDonationPageFullUrl() {
		return donationPageFullUrl;
	}
	public void setDonationPageFullUrl(String donationPageFullUrl) {
		this.donationPageFullUrl = donationPageFullUrl;
	}
	public String getLocationCampaignId() {
		return locationCampaignId;
	}
	public void setLocationCampaignId(String locationCampaignId) {
		this.locationCampaignId = locationCampaignId;
	}
	public String getImageUrl64() {
		return imageUrl64;
	}
	public void setImageUrl64(String imageUrl64) {
		this.imageUrl64 = imageUrl64;
	}
	public String getImageUrl32() {
		return imageUrl32;
	}
	public void setImageUrl32(String imageUrl32) {
		this.imageUrl32 = imageUrl32;
	}

    public String getAcIdExt() {
        return acIdExt;
    }

    public void setAcIdExt(String acIdExt) {
        this.acIdExt = acIdExt;
    }

    public String getCandidateType() {
        return candidateType;
    }

    public void setCandidateType(String candidateType) {
        this.candidateType = candidateType;
    }

    public Long getAssemblyConstituencyId() {
        return assemblyConstituencyId;
    }

    public void setAssemblyConstituencyId(Long assemblyConstituencyId) {
        this.assemblyConstituencyId = assemblyConstituencyId;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public Long getElectionId() {
        return electionId;
    }

    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }

    public String getVoteUrl() {
        return voteUrl;
    }

    public void setVoteUrl(String voteUrl) {
        this.voteUrl = voteUrl;
    }

    public String getContentSummary() {
        return contentSummary;
    }

    public void setContentSummary(String contentSummary) {
        this.contentSummary = contentSummary;
    }
    @Override
    public String toString() {
        return "CandidateDto [id=" + id + ", ver=" + ver + ", dateCreated=" + dateCreated + ", dateModified=" + dateModified + ", creatorId=" + creatorId + ", modifierId=" + modifierId + ", name="
                + name + ", content=" + content + ", stateIdExt=" + stateIdExt + ", pcIdExt=" + pcIdExt + ", acIdExt=" + acIdExt + ", landingPageUrlId=" + landingPageUrlId + ", donatePageUrlId="
                + donatePageUrlId + ", lattitude=" + lattitude + ", longitude=" + longitude + ", depth=" + depth + ", stateId=" + stateId + ", parliamentConstituencyId=" + parliamentConstituencyId
                + ", imageUrl=" + imageUrl + ", stateName=" + stateName + ", pcName=" + pcName + ", candidateFbPageId=" + candidateFbPageId + ", twitterId=" + twitterId + ", urlTextPart1="
                + urlTextPart1 + ", urlTextPart2=" + urlTextPart2 + ", landingPageSmallUrl=" + landingPageSmallUrl + ", landingPageFullUrl=" + landingPageFullUrl + ", donationPageFullUrl="
                + donationPageFullUrl + ", locationCampaignId=" + locationCampaignId + ", imageUrl64=" + imageUrl64 + ", imageUrl32=" + imageUrl32 + ", candidateType=" + candidateType
                + ", assemblyConstituencyId=" + assemblyConstituencyId + ", acName=" + acName + "]";
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
        CandidateDto other = (CandidateDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
	
	
	
}
