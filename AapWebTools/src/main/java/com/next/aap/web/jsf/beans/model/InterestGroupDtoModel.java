package com.next.aap.web.jsf.beans.model;

import com.next.aap.web.dto.InterestGroupDto;

public class InterestGroupDtoModel extends InterestGroupDto{

	private static final long serialVersionUID = 1L;
	private InterestDtoModel interestDtoModels;
	public InterestGroupDtoModel(InterestGroupDto interestGroupDto){
		this.setDescription(interestGroupDto.getDescription());
		this.setId(interestGroupDto.getId());
		interestDtoModels = new InterestDtoModel(interestGroupDto.getInterestDtos());
	}
	public InterestDtoModel getInterestDtoModels() {
		return interestDtoModels;
	}
	public void setInterestDtoModels(InterestDtoModel interestDtoModels) {
		this.interestDtoModels = interestDtoModels;
	}
    
}
