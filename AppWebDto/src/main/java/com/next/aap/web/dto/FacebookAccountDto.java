package com.next.aap.web.dto;

import java.io.Serializable;

public class FacebookAccountDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String userName;
	private String facebookUserId;
	private String imageUrl;
	private Long userId;
	private Integer totalFriends;
	private Integer totalFriendsWithUs;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getTotalFriends() {
		return totalFriends;
	}
	public void setTotalFriends(Integer totalFriends) {
		this.totalFriends = totalFriends;
	}
	public Integer getTotalFriendsWithUs() {
		return totalFriendsWithUs;
	}
	public void setTotalFriendsWithUs(Integer totalFriendsWithUs) {
		this.totalFriendsWithUs = totalFriendsWithUs;
	}
	
	

}
