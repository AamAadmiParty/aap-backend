package com.next.aap.web.dto;

import java.util.Date;

public class PlannedEmailDto {

	private Long id;
	private String message;
	private String subject;
	private Date postingTime;
	private PlannedPostStatus status;
	private PostLocationType locationType;
	private Long locationId;
	private String errorMessage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Date getPostingTime() {
		return postingTime;
	}
	public void setPostingTime(Date postingTime) {
		this.postingTime = postingTime;
	}
	public PlannedPostStatus getStatus() {
		return status;
	}
	public void setStatus(PlannedPostStatus status) {
		this.status = status;
	}
	public PostLocationType getLocationType() {
		return locationType;
	}
	public void setLocationType(PostLocationType locationType) {
		this.locationType = locationType;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	
	
}
