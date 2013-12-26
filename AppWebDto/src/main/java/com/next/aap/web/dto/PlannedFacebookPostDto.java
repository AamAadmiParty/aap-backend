package com.next.aap.web.dto;

import java.util.Date;

public class PlannedFacebookPostDto {

	public static final String PHOTO_TYPE = "Photo";
	public static final String LINK_TYPE = "Link";
	public static final String TEXT_TYPE = "TextOnly";
	private Long id;
	private String postType;
	private String picture;
	private String message;
	private String source;
	private String link;
	private String caption;
	private String name;
	private String description;
	private Date postingTime;
	private PlannedPostStatus status;
	private PostLocationType locationType;
	private Long locationId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPostType() {
		return postType;
	}
	public void setPostType(String postType) {
		this.postType = postType;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getPostingTime() {
		return postingTime;
	}
	public void setPostingTime(Date postingTime) {
		this.postingTime = postingTime;
	}
	public PlannedPostStatus getStatus() {
		return status;
	}
	public void setStatus(PlannedPostStatus status) {
		this.status = status;
	}
	public PostLocationType getLocationType() {
		return locationType;
	}
	public void setLocationType(PostLocationType locationType) {
		this.locationType = locationType;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	@Override
	public String toString() {
		return "PlannedFacebookPostDto [id=" + id + ", postType=" + postType + ", picture=" + picture + ", message=" + message + ", source=" + source
				+ ", link=" + link + ", caption=" + caption + ", name=" + name + ", description=" + description + ", postingTime=" + postingTime + ", status="
				+ status + ", locationType=" + locationType + ", locationId=" + locationId + "]";
	}
	
}
