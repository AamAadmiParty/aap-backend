package com.next.aap.web.dto;

import java.io.Serializable;
import java.util.Date;

public class PlannedTweetDto implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String TWEET_TYPE = "Tweet";
	public static final String RETWEET_TYPE = "Retweet";

	private Long id;
	private String tweetType;
	private String picture;
	private String message;
	private Date postingTime;
	private Long tweetId;
	private PlannedPostStatus status;
	private PostLocationType locationType;
	private Long locationId;
	private String errorMessage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTweetType() {
		return tweetType;
	}
	public void setTweetType(String tweetType) {
		this.tweetType = tweetType;
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
	public Date getPostingTime() {
		return postingTime;
	}
	public void setPostingTime(Date postingTime) {
		this.postingTime = postingTime;
	}
	public Long getTweetId() {
		return tweetId;
	}
	public void setTweetId(Long tweetId) {
		this.tweetId = tweetId;
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
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Override
	public String toString() {
		return "PlannedTweetDto [id=" + id + ", tweetType=" + tweetType + ", picture=" + picture + ", message=" + message + ", postingTime=" + postingTime
				+ ", tweetId=" + tweetId + ", status=" + status + ", locationType=" + locationType + ", locationId=" + locationId + ", errorMessage="
				+ errorMessage + "]";
	}

	

}
