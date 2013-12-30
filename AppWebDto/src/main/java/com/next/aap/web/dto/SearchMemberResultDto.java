package com.next.aap.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchMemberResultDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private boolean userAlreadyExists;
	private String userAlreadyExistsMessage;
	private List<UserDto> users;
	
	public SearchMemberResultDto(){
		users = new ArrayList<UserDto>();
	}
	
	public boolean isUserAlreadyExists() {
		return userAlreadyExists;
	}
	public void setUserAlreadyExists(boolean userAlreadyExists) {
		this.userAlreadyExists = userAlreadyExists;
	}
	public String getUserAlreadyExistsMessage() {
		return userAlreadyExistsMessage;
	}
	public void setUserAlreadyExistsMessage(String userAlreadyExistsMessage) {
		this.userAlreadyExistsMessage = userAlreadyExistsMessage;
	}
	public List<UserDto> getUsers() {
		return users;
	}
	public void setUsers(List<UserDto> users) {
		this.users = users;
	}
}
