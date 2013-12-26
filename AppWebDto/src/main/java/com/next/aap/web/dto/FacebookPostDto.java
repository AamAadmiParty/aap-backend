package com.next.aap.web.dto;


public class FacebookPostDto {

	private Long id;
	private String facebookPostExternalId;
	private Long facebookPageId;
	private Long facebookGroupId;
	private Long facebookAccountId;
	private Long plannedFacebookPostId;
	private String facebookShareLink;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFacebookPostExternalId() {
		return facebookPostExternalId;
	}
	public void setFacebookPostExternalId(String facebookPostExternalId) {
		this.facebookPostExternalId = facebookPostExternalId;
		if(facebookPostExternalId != null && facebookPostExternalId.indexOf("_") >= 0){
			String[] postIds = facebookPostExternalId.split("_");
			facebookShareLink = "http://www.facebook.com/permalink.php?story_fbid="+postIds[1]+"&id="+postIds[0];
			
		}
	}
	public Long getFacebookPageId() {
		return facebookPageId;
	}
	public void setFacebookPageId(Long facebookPageId) {
		this.facebookPageId = facebookPageId;
	}
	public Long getFacebookGroupId() {
		return facebookGroupId;
	}
	public void setFacebookGroupId(Long facebookGroupId) {
		this.facebookGroupId = facebookGroupId;
	}
	public Long getFacebookAccountId() {
		return facebookAccountId;
	}
	public void setFacebookAccountId(Long facebookAccountId) {
		this.facebookAccountId = facebookAccountId;
	}
	public Long getPlannedFacebookPostId() {
		return plannedFacebookPostId;
	}
	public void setPlannedFacebookPostId(Long plannedFacebookPostId) {
		this.plannedFacebookPostId = plannedFacebookPostId;
	}
	public String getFacebookShareLink() {
		return facebookShareLink;
	}
	public void setFacebookShareLink(String facebookShareLink) {
		this.facebookShareLink = facebookShareLink;
	}
	
	
	
	
}
