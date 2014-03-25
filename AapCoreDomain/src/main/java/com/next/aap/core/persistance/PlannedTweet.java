package com.next.aap.core.persistance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PostLocationType;

@Entity
@Table(name = "planned_tweets")
public class PlannedTweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Version
	@Column(name = "ver")
	private int ver;

	@Column(name = "date_created")
	private Date dateCreated;
	@Column(name = "date_modified")
	private Date dateModified;
	@Column(name = "creator_id")
	private Long creatorId;
	@Column(name = "modifier_id")
	private Long modifierId;

	@Column(name = "tweet_type")
	private String tweetType;
	
	@Column(name = "tweet_id")
	private Long tweetId;

	@Column(name = "picture")
	private String picture;

	@Column(name = "message", length = 140)
	private String message;

	@Column(name = "posting_time")
	private Date postingTime;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private PlannedPostStatus status;

	@Column(name = "location_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private PostLocationType locationType;

	@Column(name = "location_id")
	private Long locationId;

	@Column(name = "error_message")
	private String errorMessage;

	@Column(name = "total_success_tweets")
	private int totalSuccessTweets;
	@Column(name = "total_failed_tweets")
	private int totalFailedTweets;

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

	public String getTweetType() {
		return tweetType;
	}

	public void setTweetType(String tweetType) {
		this.tweetType = tweetType;
	}

	public Long getTweetId() {
		return tweetId;
	}

	public void setTweetId(Long tweetId) {
		this.tweetId = tweetId;
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

	public int getTotalSuccessTweets() {
		return totalSuccessTweets;
	}

	public void setTotalSuccessTweets(int totalSuccessTweets) {
		this.totalSuccessTweets = totalSuccessTweets;
	}

	public int getTotalFailedTweets() {
		return totalFailedTweets;
	}

	public void setTotalFailedTweets(int totalFailedTweets) {
		this.totalFailedTweets = totalFailedTweets;
	}

}
