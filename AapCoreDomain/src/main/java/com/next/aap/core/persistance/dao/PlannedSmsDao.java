package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.PlannedSms;
import com.next.aap.web.dto.PostLocationType;


public interface PlannedSmsDao {

	public abstract PlannedSms savePlannedSms(PlannedSms plannedSms);

	public abstract PlannedSms getPlannedSmsById(Long id);

	public abstract PlannedSms getPlannedSmsByAppId(String appId);
	
	public abstract List<PlannedSms> getPlannedSmsByLocationTypeAndLocationId(PostLocationType postLocationType, Long locationId);
	
	public abstract PlannedSms getNextPlannedSmsToPublish();

}