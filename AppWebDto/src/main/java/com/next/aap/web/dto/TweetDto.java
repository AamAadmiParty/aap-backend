package com.next.aap.web.dto;

import java.io.Serializable;


public class TweetDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String tweetContent;//content of tweet
	private String imageUrl;// image preview url for this tweet
	private Long twitterAccountId;
	private Long plannedTweetId;
	private Long tweetExternalId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTweetContent() {
		return tweetContent;
	}
	public void setTweetContent(String tweetContent) {
		this.tweetContent = tweetContent;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Long getTwitterAccountId() {
		return twitterAccountId;
	}
	public void setTwitterAccountId(Long twitterAccountId) {
		this.twitterAccountId = twitterAccountId;
	}
	public Long getPlannedTweetId() {
		return plannedTweetId;
	}
	public void setPlannedTweetId(Long plannedTweetId) {
		this.plannedTweetId = plannedTweetId;
	}
	public Long getTweetExternalId() {
		return tweetExternalId;
	}
	public void setTweetExternalId(Long tweetExternalId) {
		this.tweetExternalId = tweetExternalId;
	}

	
}
