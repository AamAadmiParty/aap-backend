package com.next.aap.core.persistance.dao.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.PlannedEmail;
import com.next.aap.core.persistance.dao.PlannedEmailDao;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PostLocationType;

@Component
public class PlannedEmailDaoHibernateSpringImpl extends BaseDaoHibernateSpring<PlannedEmail> implements PlannedEmailDao{

	private static final long serialVersionUID = 1L;
	public PlannedEmailDaoHibernateSpringImpl() {
	}

	@Override
	public PlannedEmail savePlannedEmail(PlannedEmail plannedEmail){
		plannedEmail = super.saveObject(plannedEmail);
		return plannedEmail;
	}

	@Override
	public PlannedEmail getPlannedEmailById(Long id){
		return super.getObjectById(PlannedEmail.class, id);
	}
	@Override
	public PlannedEmail getPlannedEmailByAppId(String appId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("appId", appId);
		PlannedEmail plannedEmail = executeQueryGetObject("from PlannedEmail where appId = :appId", params);
		return plannedEmail;

	}

	@Override
	public List<PlannedEmail> getPlannedEmailByLocationTypeAndLocationId(PostLocationType postLocationType, Long locationId) {
		PlannedPostStatus pending = PlannedPostStatus.PENDING;
		String query = "from PlannedEmail where status = :status and  locationType = :postLocationType ";
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
	public PlannedEmail getNextPlannedEmailToPublish() {
		PlannedPostStatus pending = PlannedPostStatus.PENDING;
		Calendar now = Calendar.getInstance();
		String query = "from PlannedEmail where status = :status and postingTime <= :postingTime";
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("status", pending);
		params.put("postingTime", now.getTime());
		query = query + " order by postingTime asc";
		List<PlannedEmail> list = executeQueryGetList(query, params);
		if(list ==  null || list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
}
