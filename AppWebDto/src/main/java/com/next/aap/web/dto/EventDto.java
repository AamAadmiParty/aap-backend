package com.next.aap.web.dto;

import java.util.Date;

public class EventDto {

	private Long id;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private double lattitude;
	private double longitude;
	private double depth;
	private String address;
	private String contactNumber1;
	private String contactNumber2;
	private String contactNumber3;
	private String contactNumber4;
	private String fbEventId;
	private boolean national;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getDepth() {
		return depth;
	}
	public void setDepth(double depth) {
		this.depth = depth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNumber1() {
		return contactNumber1;
	}
	public void setContactNumber1(String contactNumber1) {
		this.contactNumber1 = contactNumber1;
	}
	public String getContactNumber2() {
		return contactNumber2;
	}
	public void setContactNumber2(String contactNumber2) {
		this.contactNumber2 = contactNumber2;
	}
	public String getContactNumber3() {
		return contactNumber3;
	}
	public void setContactNumber3(String contactNumber3) {
		this.contactNumber3 = contactNumber3;
	}
	public String getContactNumber4() {
		return contactNumber4;
	}
	public void setContactNumber4(String contactNumber4) {
		this.contactNumber4 = contactNumber4;
	}
	public String getFbEventId() {
		return fbEventId;
	}
	public void setFbEventId(String fbEventId) {
		this.fbEventId = fbEventId;
	}
	public boolean isNational() {
		return national;
	}
	public void setNational(boolean national) {
		this.national = national;
	}

	
}
