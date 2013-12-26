package com.next.aap.core.persistance.dao.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.PlannedFacebookPost;
import com.next.aap.core.persistance.dao.PlannedFacebookPostDao;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PostLocationType;

@Component
public class PlannedFacebookPostDaoHibernateSpringImpl extends BaseDaoHibernateSpring<PlannedFacebookPost> implements PlannedFacebookPostDao{

	private static final long serialVersionUID = 1L;
	public PlannedFacebookPostDaoHibernateSpringImpl() {
	}

	@Override
	public PlannedFacebookPost savePlannedFacebookPost(PlannedFacebookPost plannedFacebookPost){
		plannedFacebookPost = super.saveObject(plannedFacebookPost);
		return plannedFacebookPost;
	}

	@Override
	public PlannedFacebookPost getPlannedFacebookPostById(Long id){
		return super.getObjectById(PlannedFacebookPost.class, id);
	}
	@Override
	public PlannedFacebookPost getPlannedFacebookPostByAppId(String appId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("appId", appId);
		PlannedFacebookPost plannedFacebookPost = executeQueryGetObject("from PlannedFacebookPost where appId = :appId", params);
		return plannedFacebookPost;

	}

	@Override
	public List<PlannedFacebookPost> getPlannedFacebookPostByLocationTypeAndLocationId(PostLocationType postLocationType, Long locationId) {
		PlannedPostStatus pending = PlannedPostStatus.PENDING;
		String query = "from PlannedFacebookPost where status = :status and  locationType = :postLocationType ";
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
	public PlannedFacebookPost getNextPlannedFacebookPostToPublish() {
		PlannedPostStatus pending = PlannedPostStatus.PENDING;
		Calendar now = Calendar.getInstance();
		String query = "from PlannedFacebookPost where status = :status and postingTime <= :postingTime";
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("status", pending);
		params.put("postingTime", now.getTime());
		query = query + " order by postingTime asc";
		List<PlannedFacebookPost> list = executeQueryGetList(query, params);
		if(list ==  null || list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
}
