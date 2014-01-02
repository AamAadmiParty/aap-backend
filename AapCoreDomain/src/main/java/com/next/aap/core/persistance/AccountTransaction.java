package com.next.aap.core.persistance;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.next.aap.web.dto.AccountTransactionMode;
import com.next.aap.web.dto.AccountTransactionType;
import com.next.aap.web.dto.AccountType;

@Entity
@Table(name="accounts")
public class AccountTransaction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Version
	@Column(name="ver")
	private int ver;
	
	@Column(name="date_created")
	private Date dateCreated;
	@Column(name="date_modified")
	private Date dateModified;
	@Column(name="creator_id")
	private Long creatorId;
	@Column(name="modifier_id")
	private Long modifierId;

	
	@Column(name = "transaction_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountTransactionType accountTransactionType;

	@Column(name = "transaction_mode", nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountTransactionMode accountTransactionMode;

	
	@Column(name="description")
	private String description;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="balance")
	private Double balance;

	@Column(name="transaction_date")
	private Date transactionDate;
	
	@Column(name="account_id", insertable=false,updatable=false)
	private Long accountId;
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="account_id")
    private Account account;
	
	@Column(name="transaction_identifier")
	private String transactionIdentifier;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}

	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}

}
