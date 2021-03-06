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
@Table(name = "candidates")
public class Candidate {

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

	
	@Column(name = "name")
	private String name;

	@Column(name = "content",  columnDefinition="LONGTEXT")
	private String content;//content of news which can be html or plain text

	@Column(name = "state_id_ext")
	private String stateIdExt;

	@Column(name = "pc_id_ext")
	private String pcIdExt;
	
    @Column(name = "ac_id_ext")
    private String acIdExt;

	@Column(name = "candidate_fb_page_id")
	private String candidateFbPageId;
	
	@Column(name = "twitter_id")
	private String twitterId;
	
	@Column(name = "landing_page_url_id")
	private String landingPageUrlId;

	@Column(name = "landing_page_small_url")
	private String landingPageSmallUrl;

	@Column(name = "landing_page_full_url", length=256)
	private String landingPageFullUrl;
	
	@Column(name = "donation_page_full_url")
	private String donationPageFullUrl;

	@Column(name = "donate_page_url_id")
	private String donatePageUrlId;

	@Column(name = "location_campaign_id")
	private String locationCampaignId;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "image_url_64")
	private String imageUrl64;

	@Column(name = "image_url_32")
	private String imageUrl32;

	@Column(name = "lattitude")
	private double lattitude;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "map_default_depth")
	private int depth;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="state_id")
    private State state;
	@Column(name="state_id", insertable=false,updatable=false)
	private Long stateId;
	@Column(name = "state_name")
	private String stateName;

	@Column(name = "url_text_part1")
	private String urlTextPart1;
	@Column(name = "url_text_part2")
	private String urlTextPart2;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="pc_id")
    private ParliamentConstituency parliamentConstituency;
	@Column(name="pc_id", insertable=false,updatable=false)
	private Long parliamentConstituencyId;
	@Column(name = "pc_name")
	private String pcName;
	
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ac_id")
    private AssemblyConstituency assemblyConstituency;
    @Column(name = "ac_id", insertable = false, updatable = false)
    private Long assemblyConstituencyId;
    @Column(name = "ac_name")
    private String acName;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "election_id")
    private Election election;
    @Column(name = "election_id", insertable = false, updatable = false)
    private Long electionId;

    @Column(name = "candidate_type", nullable = false)
    private String candidateType;

    @Column(name = "vote_url")
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	public String getTwitterId() {
		return twitterId;
	}
	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}
	public String getCandidateFbPageId() {
		return candidateFbPageId;
	}
	public void setCandidateFbPageId(String candidateFbPageId) {
		this.candidateFbPageId = candidateFbPageId;
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

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }
    public Election getElection() {
        return election;
    }
    public void setElection(Election election) {
        this.election = election;
    }
    public Long getElectionId() {
        return electionId;
    }
    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }

    public String getCandidateType() {
        return candidateType;
    }

    public void setCandidateType(String candidateType) {
        this.candidateType = candidateType;
    }

    public String getVoteUrl() {
        return voteUrl;
    }

    public void setVoteUrl(String voteUrl) {
        this.voteUrl = voteUrl;
    }
	
	
}
