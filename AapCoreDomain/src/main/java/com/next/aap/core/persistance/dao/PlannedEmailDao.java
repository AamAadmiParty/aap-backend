package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.PlannedEmail;
import com.next.aap.web.dto.PostLocationType;


public interface PlannedEmailDao {

	public abstract PlannedEmail savePlannedEmail(PlannedEmail plannedEmail);

	public abstract PlannedEmail getPlannedEmailById(Long id);

	public abstract PlannedEmail getPlannedEmailByAppId(String appId);
	
	public abstract List<PlannedEmail> getPlannedEmailByLocationTypeAndLocationId(PostLocationType postLocationType, Long locationId);
	
	public abstract PlannedEmail getNextPlannedEmailToPublish();

}