package com.next.aap.core.persistance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="facebook_group")
public class FacebookGroup {

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

	
	@Column(name = "facebook_group_ext_id", nullable = false)
	private String facebookGroupExternalId;

	@Column(name = "group_name", nullable = false)
	private String groupName;
	
	@Column(name = "post_allowed", nullable = false)
	private boolean postAllowed;

	@Column(name = "total_members" , columnDefinition="INT default 0")
	private int totalMembers;

	@Column(name = "members_with_us", columnDefinition="INT default 0")
	private int totalMembersEithUs;

	@Column(name = "member_with_permission_to_post", columnDefinition="INT default 0")
	private int totalMembersWithPermissionToPost;
	
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

	public String getFacebookGroupExternalId() {
		return facebookGroupExternalId;
	}

	public void setFacebookGroupExternalId(String facebookGroupExternalId) {
		this.facebookGroupExternalId = facebookGroupExternalId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public boolean isPostAllowed() {
		return postAllowed;
	}

	public void setPostAllowed(boolean postAllowed) {
		this.postAllowed = postAllowed;
	}

	public int getTotalMembers() {
		return totalMembers;
	}

	public void setTotalMembers(int totalMembers) {
		this.totalMembers = totalMembers;
	}

	public int getTotalMembersEithUs() {
		return totalMembersEithUs;
	}

	public void setTotalMembersEithUs(int totalMembersEithUs) {
		this.totalMembersEithUs = totalMembersEithUs;
	}

	public int getTotalMembersWithPermissionToPost() {
		return totalMembersWithPermissionToPost;
	}

	public void setTotalMembersWithPermissionToPost(
			int totalMembersWithPermissionToPost) {
		this.totalMembersWithPermissionToPost = totalMembersWithPermissionToPost;
	}
	
}
