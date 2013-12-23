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
@Table(name="facebook_group_membership")
public class FacebookGroupMembership{

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO,  generator="FACEBOOK_GROUP_MEMBERSHIP_SEQ_GEN")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	/*
	@SequenceGenerator(
    name="FACEBOOK_GROUP_MEMBERSHIP_SEQ_GEN",
    sequenceName="FACEBOOK_GROUP_MEMBERSHIP_SEQ",
    allocationSize=20
	)*/
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

	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="facebook_account_id")
    private FacebookAccount facebookAccount;
	@Column(name="facebook_account_id", insertable=false,updatable=false)
	private Long facebookAccountId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="facebook_group_id")
    private FacebookGroup facebookGroup;
	@Column(name="facebook_group_id", insertable=false,updatable=false)
	private Long facebookGroupId;
	
	@Column(name="last_post_date")
	private Date lastPostDate;
	
	@Column(name="last_read_date")
	private Date lastReadDate;
	
	@Column(name="allow_ddu_post")
	private boolean allowDduPost;
	
	@Column(name="allow_voice_of_aap_post")
	private boolean allowVoiceOfAapPost;
	
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
	public Date getLastPostDate() {
		return lastPostDate;
	}
	public void setLastPostDate(Date lastPostDate) {
		this.lastPostDate = lastPostDate;
	}
	public boolean isAllowDduPost() {
		return allowDduPost;
	}
	public void setAllowDduPost(boolean allowDduPost) {
		this.allowDduPost = allowDduPost;
	}
	public Date getLastReadDate() {
		return lastReadDate;
	}
	public void setLastReadDate(Date lastReadDate) {
		this.lastReadDate = lastReadDate;
	}
	public boolean isAllowVoiceOfAapPost() {
		return allowVoiceOfAapPost;
	}
	public void setAllowVoiceOfAapPost(boolean allowVoiceOfAapPost) {
		this.allowVoiceOfAapPost = allowVoiceOfAapPost;
	}

		
}
