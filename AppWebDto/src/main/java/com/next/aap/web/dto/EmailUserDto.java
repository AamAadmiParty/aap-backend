package com.next.aap.web.dto;

import java.io.Serializable;

public class EmailUserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String email;
	private String userName;
	private String userExternalid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserExternalid() {
		return userExternalid;
	}
	public void setUserExternalid(String userExternalid) {
		this.userExternalid = userExternalid;
	}

}
