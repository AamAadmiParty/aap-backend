package com.next.aap.web.dto;

import java.util.Date;

public class AccountTransactionDto {

	private Long id;
	private AccountTransactionType accountTransactionType;
	private AccountTransactionMode accountTransactionMode;
	private String description;
	private Double amount;
	private Double balance;
	private Date transactionDate;
	private Long accountId;
	private String transactionIdentifier;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public AccountTransactionType getAccountTransactionType() {
		return accountTransactionType;
	}
	public void setAccountTransactionType(AccountTransactionType accountTransactionType) {
		this.accountTransactionType = accountTransactionType;
	}
	public AccountTransactionMode getAccountTransactionMode() {
		return accountTransactionMode;
	}
	public void setAccountTransactionMode(AccountTransactionMode accountTransactionMode) {
		this.accountTransactionMode = accountTransactionMode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}
	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}

	
}
