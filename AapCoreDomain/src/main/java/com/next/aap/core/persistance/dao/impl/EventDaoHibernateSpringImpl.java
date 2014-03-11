package com.next.aap.core.persistance.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.Event;
import com.next.aap.core.persistance.dao.EventDao;

@Component
public class EventDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Event> implements EventDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.EventDao#saveEvent(com.next.aap.server.persistance.Event)
	 */
	@Override
	public Event saveEvent(Event event){
		checkIfObjectMissing("End Date", event.getEndDate());
		checkIfObjectMissing("Start Date", event.getStartDate());
		checkIfObjectMissing("Title", event.getTitle());
		event = super.saveObject(event);
		return event;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.EventDao#getEventById(java.lang.Long)
	 */
	@Override
	public Event getEventById(Long id) {
		return super.getObjectById(Event.class, id);
	}

	@Override
	public List<Event> getAllNationalEvents() {
		Map<String, Object> params = new HashMap<>();
		params.put("national", true);
		List<Event> list = executeQueryGetList("from Event where national = :national", params);
		return list;
	}

	@Override
	public List<Event> getStateEvents(Long stateId) {
		String sqlQuery = "select event_id from event_state where state_id = :stateId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("stateId", stateId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Event where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<Event> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Event> getDistrictEvents(Long districtId) {
		String sqlQuery = "select event_id from event_district where district_id = :districtId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("districtId", districtId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Event where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<Event> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Event> getAcEvents(Long acId) {
		String sqlQuery = "select event_id from event_ac where ac_id = :acId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("acId", acId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Event where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<Event> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Event> getPcEvents(Long pcId) {
		String sqlQuery = "select event_id from event_pc where pc_id = :pcId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("pcId", pcId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Event where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<Event> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Event> getCountryEvents(Long countryId) {
		String sqlQuery = "select event_id from event_country where country_id = :countryId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryId", countryId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Event where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<Event> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Event> getCountryRegionEvents(Long countryRegionId) {
		String sqlQuery = "select event_id from event_country_region where country_region_id = :countryRegionId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryRegionId", countryRegionId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Event where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<Event> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Event> getCountryRegionAreaEvents(Long countryRegionAreaId) {
		String sqlQuery = "select event_id from event_country_region_area where country_region_area_id = :countryRegionAreaId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryRegionAreaId", countryRegionAreaId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Event where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<Event> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Event> getAllFutureEvents() {
		String query = "from Event where startDate > :startDate order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -2);
		queryParams.put("startDate", calendar.getTime());
		List<Event> list = executeQueryGetList(query, queryParams);
		return list;
	}

}
