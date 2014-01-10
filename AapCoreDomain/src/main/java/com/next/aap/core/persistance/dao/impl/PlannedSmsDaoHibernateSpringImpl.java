package com.next.aap.core.persistance.dao.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.PlannedSms;
import com.next.aap.core.persistance.dao.PlannedSmsDao;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PostLocationType;

@Component
public class PlannedSmsDaoHibernateSpringImpl extends BaseDaoHibernateSpring<PlannedSms> implements PlannedSmsDao{

	private static final long serialVersionUID = 1L;
	public PlannedSmsDaoHibernateSpringImpl() {
	}

	@Override
	public PlannedSms savePlannedSms(PlannedSms plannedSms){
		plannedSms = super.saveObject(plannedSms);
		return plannedSms;
	}

	@Override
	public PlannedSms getPlannedSmsById(Long id){
		return super.getObjectById(PlannedSms.class, id);
	}
	@Override
	public PlannedSms getPlannedSmsByAppId(String appId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("appId", appId);
		PlannedSms plannedSms = executeQueryGetObject("from PlannedSms where appId = :appId", params);
		return plannedSms;

	}

	@Override
	public List<PlannedSms> getPlannedSmsByLocationTypeAndLocationId(PostLocationType postLocationType, Long locationId) {
		PlannedPostStatus pending = PlannedPostStatus.PENDING;
		String query = "from PlannedSms where status = :status and  locationType = :postLocationType ";
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("postLocationType", postLocationType);
		params.put("status", pending);
		if(!postLocationType.equals(PostLocationType.Global)){
			query = query + "and locationId = :locationId";
			params.put("locationId", locationId);	
		}
		query = query + " order by postingTime asc";
		return executeQueryGetList(query, params);
	}

	@Override
	public PlannedSms getNextPlannedSmsToPublish() {
		PlannedPostStatus pending = PlannedPostStatus.PENDING;
		Calendar now = Calendar.getInstance();
		String query = "from PlannedSms where status = :status and postingTime <= :postingTime";
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("status", pending);
		params.put("postingTime", now.getTime());
		query = query + " order by postingTime asc";
		List<PlannedSms> list = executeQueryGetList(query, params);
		if(list ==  null || list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
}
