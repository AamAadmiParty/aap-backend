package com.next.aap.web.dto;

import java.io.Serializable;
import java.util.List;


public class InterestGroupDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String description;
	private List<InterestDto> interestDtos;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<InterestDto> getInterestDtos() {
		return interestDtos;
	}
	public void setInterestDtos(List<InterestDto> interestDtos) {
		this.interestDtos = interestDtos;
	}
	
	

	
}
