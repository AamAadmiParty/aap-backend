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
@Table(name="facebook_post")
public class FacebookPost {

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

	
	@Column(name = "facebook_post_ext_id", nullable = false)
	private String facebookPostExternalId;

	@Column(name = "post_status")
	private String postStatus;

	@Column(name = "error_message")
	private String errorMessage;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="facebook_page_id")
    private FacebookPage facebookPage;
	@Column(name="facebook_page_id", insertable=false,updatable=false)
	private Long facebookPageId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="facebook_group_id")
    private FacebookGroup facebookGroup;
	@Column(name="facebook_group_id", insertable=false,updatable=false)
	private Long facebookGroupId;
	
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="facebook_account_id")
    private FacebookAccount facebookAccount;
	@Column(name="facebook_account_id", insertable=false,updatable=false)
	private Long facebookAccountId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="planned_facebook_post_id")
    private PlannedFacebookPost plannedFacebookPost;
	@Column(name="planned_facebook_post_id", insertable=false,updatable=false)
	private Long plannedFacebookPostId;
	
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
	public String getFacebookPostExternalId() {
		return facebookPostExternalId;
	}
	public void setFacebookPostExternalId(String facebookPostExternalId) {
		this.facebookPostExternalId = facebookPostExternalId;
	}
	public FacebookPage getFacebookPage() {
		return facebookPage;
	}
	public void setFacebookPage(FacebookPage facebookPage) {
		this.facebookPage = facebookPage;
	}
	public Long getFacebookPageId() {
		return facebookPageId;
	}
	public void setFacebookPageId(Long facebookPageId) {
		this.facebookPageId = facebookPageId;
	}
	public FacebookGroup getFacebookGroup() {
		return facebookGroup;
	}
	public void setFacebookGroup(FacebookGroup facebookGroup) {
		this.facebookGroup = facebookGroup;
	}
	public Long getFacebookGroupId() {
		return facebookGroupId;
	}
	public void setFacebookGroupId(Long facebookGroupId) {
		this.facebookGroupId = facebookGroupId;
	}
	public FacebookAccount getFacebookAccount() {
		return facebookAccount;
	}
	public void setFacebookAccount(FacebookAccount facebookAccount) {
		this.facebookAccount = facebookAccount;
	}
	public Long getFacebookAccountId() {
		return facebookAccountId;
	}
	public void setFacebookAccountId(Long facebookAccountId) {
		this.facebookAccountId = facebookAccountId;
	}
	public PlannedFacebookPost getPlannedFacebookPost() {
		return plannedFacebookPost;
	}
	public void setPlannedFacebookPost(PlannedFacebookPost plannedFacebookPost) {
		this.plannedFacebookPost = plannedFacebookPost;
	}
	public Long getPlannedFacebookPostId() {
		return plannedFacebookPostId;
	}
	public void setPlannedFacebookPostId(Long plannedFacebookPostId) {
		this.plannedFacebookPostId = plannedFacebookPostId;
	}
	public String getPostStatus() {
		return postStatus;
	}
	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
