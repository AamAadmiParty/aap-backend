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
@Table(name="facebook_app_permission")
public class FacebookAppPermission{

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO, generator="FACEBOOK_ACCOUNT_SEQ_GEN")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	/*
	@SequenceGenerator(
    name="FACEBOOK_ACCOUNT_SEQ_GEN",
    sequenceName="FACEBOOK_ACCOUNT_SEQ",
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

	
	@Column(name = "token", nullable = false, length=256)
	private String token;
	@Column(name = "expire_time", nullable = false)
	private Date expireTime;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="facebook_account_id")
    private FacebookAccount facebookAccount;
	@Column(name="facebook_account_id", insertable=false,updatable=false)
	private Long facebookAccountId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="facebook_app_id")
    private FacebookApp facebookApp;
	@Column(name="facebook_app_id", insertable=false,updatable=false)
	private Long facebookAppId;
	
	
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	public FacebookApp getFacebookApp() {
		return facebookApp;
	}
	public void setFacebookApp(FacebookApp facebookApp) {
		this.facebookApp = facebookApp;
	}
	public Long getFacebookAppId() {
		return facebookAppId;
	}
	public void setFacebookAppId(Long facebookAppId) {
		this.facebookAppId = facebookAppId;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	
	
}
