package com.next.aap.core.persistance.dao.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.PlannedTweet;
import com.next.aap.core.persistance.dao.PlannedTweetDao;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PostLocationType;

@Component
public class PlannedTweetDaoHibernateSpringImpl extends BaseDaoHibernateSpring<PlannedTweet> implements PlannedTweetDao{

	private static final long serialVersionUID = 1L;
	public PlannedTweetDaoHibernateSpringImpl() {
	}

	@Override
	public PlannedTweet savePlannedTweet(PlannedTweet plannedTweet){
		plannedTweet = super.saveObject(plannedTweet);
		return plannedTweet;
	}

	@Override
	public PlannedTweet getPlannedTweetById(Long id){
		return super.getObjectById(PlannedTweet.class, id);
	}
	@Override
	public PlannedTweet getPlannedTweetByAppId(String appId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("appId", appId);
		PlannedTweet plannedTweet = executeQueryGetObject("from PlannedTweet where appId = :appId", params);
		return plannedTweet;

	}

	@Override
	public List<PlannedTweet> getPlannedTweetByLocationTypeAndLocationId(PostLocationType postLocationType, Long locationId) {
		PlannedPostStatus pending = PlannedPostStatus.PENDING;
		String query = "from PlannedTweet where status = :status and  locationType = :postLocationType ";
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
	public PlannedTweet getNextPlannedTweetToPublish() {
		PlannedPostStatus pending = PlannedPostStatus.PENDING;
		Calendar now = Calendar.getInstance();
		String query = "from PlannedTweet where status = :status and postingTime <= :postingTime";
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("status", pending);
		params.put("postingTime", now.getTime());
		query = query + " order by postingTime asc";
		List<PlannedTweet> list = executeQueryGetList(query, params);
		if(list ==  null || list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
}
