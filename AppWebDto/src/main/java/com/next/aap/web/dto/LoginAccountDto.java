package com.next.aap.web.dto;

import java.io.Serializable;
import java.util.List;

public class LoginAccountDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	List<FacebookAccountDto> facebookAccounts;
	List<TwitterAccountDto> twitterAccounts;
	AapAccountDto aapAccountDto;
	
	
	public List<FacebookAccountDto> getFacebookAccounts() {
		return facebookAccounts;
	}
	public void setFacebookAccounts(List<FacebookAccountDto> facebookAccounts) {
		this.facebookAccounts = facebookAccounts;
	}
	public List<TwitterAccountDto> getTwitterAccounts() {
		return twitterAccounts;
	}
	public void setTwitterAccounts(List<TwitterAccountDto> twitterAccounts) {
		this.twitterAccounts = twitterAccounts;
	}
	public AapAccountDto getAapAccountDto() {
		return aapAccountDto;
	}
	public void setAapAccountDto(AapAccountDto aapAccountDto) {
		this.aapAccountDto = aapAccountDto;
	}

}
