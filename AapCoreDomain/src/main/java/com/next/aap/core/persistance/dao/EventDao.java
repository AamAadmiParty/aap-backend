package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Event;

public interface EventDao {

	public abstract Event saveEvent(Event event);

	public abstract Event getEventById(Long id);

	public abstract List<Event> getAllFutureEvents();
	
	public abstract List<Event> getAllNationalEvents();
	
	public abstract List<Event> getStateEvents(Long stateId);
	
	public abstract List<Event> getDistrictEvents(Long district);
	
	public abstract List<Event> getAcEvents(Long acId);
	
	public abstract List<Event> getPcEvents(Long pcId);
	
	public abstract List<Event> getCountryEvents(Long pcId);
	
	public abstract List<Event> getCountryRegionEvents(Long pcId);
	
	public abstract List<Event> getCountryRegionAreaEvents(Long pcId);
}