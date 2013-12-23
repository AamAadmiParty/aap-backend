package com.next.aap.web.dto;

import java.util.Date;

public class FacebookAppPermissionDto{

	private Long id;
	private String token;
	private Date expireTime;
	private Long facebookAccountId;
	private Long facebookAppId;
	
	
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
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Long getFacebookAccountId() {
		return facebookAccountId;
	}
	public void setFacebookAccountId(Long facebookAccountId) {
		this.facebookAccountId = facebookAccountId;
	}
	public Long getFacebookAppId() {
		return facebookAppId;
	}
	public void setFacebookAppId(Long facebookAppId) {
		this.facebookAppId = facebookAppId;
	}
	
	
	
	
	
}
