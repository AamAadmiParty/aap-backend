package com.next.aap.cache.beans;

import java.io.Serializable;
import java.util.Date;

import com.next.aap.core.persistance.Donation;

public class DonationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String did;
	private Double amt;
	private Date dt;
	private String name;
	
	public DonationBean(){
		
	}
	public DonationBean(Donation donation){
		did = donation.getTransactionId();
		amt = donation.getAmount();
		dt = donation.getDonationDate();
		name = donation.getDonorName();
	}
	
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public Double getAmt() {
		return amt;
	}
	public void setAmt(Double amt) {
		this.amt = amt;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "DonationBean [did=" + did + ", amt=" + amt + ", dt=" + dt + ", name=" + name + "]";
	}
	
}
