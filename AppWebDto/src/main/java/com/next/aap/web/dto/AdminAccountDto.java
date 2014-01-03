package com.next.aap.web.dto;

import java.io.Serializable;


public class AdminAccountDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private AccountType accountType;
	private Double balance;
	private String description;
    private UserDto accountOwnerDto;
	private Long accountOwnerId;
	private boolean global;
	private Long stateId;
	private Long districtId;
	private Long assemblyConstituencyId;
	private Long parliamentConstituencyId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public UserDto getAccountOwnerDto() {
		return accountOwnerDto;
	}
	public void setAccountOwnerDto(UserDto accountOwnerDto) {
		this.accountOwnerDto = accountOwnerDto;
	}
	public Long getAccountOwnerId() {
		return accountOwnerId;
	}
	public void setAccountOwnerId(Long accountOwnerId) {
		this.accountOwnerId = accountOwnerId;
	}
	public boolean isGlobal() {
		return global;
	}
	public void setGlobal(boolean global) {
		this.global = global;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public Long getAssemblyConstituencyId() {
		return assemblyConstituencyId;
	}
	public void setAssemblyConstituencyId(Long assemblyConstituencyId) {
		this.assemblyConstituencyId = assemblyConstituencyId;
	}
	public Long getParliamentConstituencyId() {
		return parliamentConstituencyId;
	}
	public void setParliamentConstituencyId(Long parliamentConstituencyId) {
		this.parliamentConstituencyId = parliamentConstituencyId;
	}
}
