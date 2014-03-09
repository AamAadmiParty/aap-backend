package com.next.aap.web.dto;


public class FacebookGroupDto {

	private Long id;
	private String facebookGroupExternalId;
	private String groupName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
}
