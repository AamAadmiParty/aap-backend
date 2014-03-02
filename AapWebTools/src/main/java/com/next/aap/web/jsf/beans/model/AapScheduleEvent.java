package com.next.aap.web.jsf.beans.model;

import java.util.Date;

import org.primefaces.model.DefaultScheduleEvent;

import com.next.aap.web.dto.EventDto;

public class AapScheduleEvent extends DefaultScheduleEvent {

	private static final long serialVersionUID = 1L;
	private String description;
	private double lattitude;
	private double longitude;
	private int depth;
	private String address;
	private String contactNumber1;
	private String contactNumber2;
	private String contactNumber3;
	private String contactNumber4;
	private String fbEventId;
	private boolean national;
	private Long dbId;
	
	
	public AapScheduleEvent() {
		super();
	}
	public AapScheduleEvent(EventDto eventDto) {
		setTitle(eventDto.getTitle());
		setStartDate(eventDto.getStartDate());
		setEndDate(eventDto.getEndDate());
		description = eventDto.getDescription();
		lattitude = eventDto.getLattitude();
		longitude = eventDto.getLongitude();
		depth = eventDto.getDepth();
		address = eventDto.getAddress();
		contactNumber1 = eventDto.getContactNumber1();
		contactNumber2 = eventDto.getContactNumber2();
		contactNumber3 = eventDto.getContactNumber3();
		contactNumber4 = eventDto.getContactNumber4();
		fbEventId = eventDto.getFbEventId();
		national = eventDto.isNational();
		dbId = eventDto.getId();
	}
	public AapScheduleEvent(String title, Date start, Date end, boolean allDay) {
		super(title, start, end, allDay);
	}
	public AapScheduleEvent(String title, Date start, Date end, Object data) {
		super(title, start, end, data);
	}
	public AapScheduleEvent(String title, Date start, Date end, String styleClass) {
		super(title, start, end, styleClass);
	}
	public AapScheduleEvent(String title, Date start, Date end) {
		super(title, start, end);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
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
	public Long getDbId() {
		return dbId;
	}
	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}
	

}
