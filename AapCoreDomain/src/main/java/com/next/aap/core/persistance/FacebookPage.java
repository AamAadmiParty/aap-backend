package com.next.aap.core.persistance;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="facebook_page")
public class FacebookPage{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@GeneratedValue(strategy=GenerationType.AUTO,  generator="FACEBOOK_GROUP_SEQ_GEN")
	/*
	@SequenceGenerator(
    name="FACEBOOK_GROUP_SEQ_GEN",
    sequenceName="FACEBOOK_GROUP_SEQ",
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

	
	@Column(name = "facebook_page_ext_id", nullable = false)
	private String facebookPageExternalId;

	@Column(name = "page_name", nullable = false)
	private String pageName;
	
	@Column(name = "post_allowed", nullable = false)
	private boolean postAllowed;
	
	@Column(name = "post_allowed_to_child", nullable = false)
	private boolean postAllowedToChild;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "facebook_group_subscription",
	joinColumns = {
	@JoinColumn(name="facebook_page_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="facebook_group_subscriber_id")
	})
	private Set<FacebookGroup> facebookGroupSubscribers;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "facebook_page_subscription",
	joinColumns = {
	@JoinColumn(name="facebook_page_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="facebook_page_subscriber_id")
	})
	private Set<FacebookPage> facebookPageSubscribers;

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

	public String getFacebookPageExternalId() {
		return facebookPageExternalId;
	}

	public void setFacebookPageExternalId(String facebookPageExternalId) {
		this.facebookPageExternalId = facebookPageExternalId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public boolean isPostAllowed() {
		return postAllowed;
	}

	public void setPostAllowed(boolean postAllowed) {
		this.postAllowed = postAllowed;
	}

	public Set<FacebookGroup> getFacebookGroupSubscribers() {
		return facebookGroupSubscribers;
	}

	public void setFacebookGroupSubscribers(
			Set<FacebookGroup> facebookGroupSubscribers) {
		this.facebookGroupSubscribers = facebookGroupSubscribers;
	}

	public Set<FacebookPage> getFacebookPageSubscribers() {
		return facebookPageSubscribers;
	}

	public void setFacebookPageSubscribers(
			Set<FacebookPage> facebookPageSubscribers) {
		this.facebookPageSubscribers = facebookPageSubscribers;
	}

	public boolean isPostAllowedToChild() {
		return postAllowedToChild;
	}

	public void setPostAllowedToChild(boolean postAllowedToChild) {
		this.postAllowedToChild = postAllowedToChild;
	}

	
}
