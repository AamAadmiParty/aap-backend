package com.next.aap.web.dto;

import java.io.Serializable;


public class TwitterAccountDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String imageUrl;
	private String token;
	private String tokenSecret;
	private Long userId;
	private String twitterId;
	private String screenName;
	private String screenNameCap;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTokenSecret() {
		return tokenSecret;
	}
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTwitterId() {
		return twitterId;
	}
	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getScreenNameCap() {
		return screenNameCap;
	}
	public void setScreenNameCap(String screenNameCap) {
		this.screenNameCap = screenNameCap;
	}

	

}
