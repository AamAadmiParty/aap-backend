package com.next.aap.cache.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DonationCampaignInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer ttxn;//total transaction till date
	private Double tamt;//total amount till date
	private List<DonationBean> dns = new ArrayList<>();//recent N donations
	public Integer getTtxn() {
		return ttxn;
	}
	public void setTtxn(Integer ttxn) {
		this.ttxn = ttxn;
	}
	public Double getTamt() {
		return tamt;
	}
	public void setTamt(Double tamt) {
		this.tamt = tamt;
	}
	public List<DonationBean> getDns() {
		return dns;
	}
	public void setDns(List<DonationBean> dns) {
		this.dns = dns;
	}
	@Override
	public String toString() {
		return "DonationCampaignInfo [ttxn=" + ttxn + ", tamt=" + tamt + ", dns=" + dns + "]";
	}
	
	
}
