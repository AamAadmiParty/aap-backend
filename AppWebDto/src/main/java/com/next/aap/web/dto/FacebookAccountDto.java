package com.next.aap.web.dto;

import java.io.Serializable;
import java.util.Date;

public class FacebookAccountDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String token;
	private String userName;
	private String facebookUserId;
	private String imageUrl;
	private Date expireTime;
	private Long userId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFacebookUserId() {
		return facebookUserId;
	}
	public void setFacebookUserId(String facebookUserId) {
		this.facebookUserId = facebookUserId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	

}
