package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.PlannedFacebookPost;
import com.next.aap.core.persistance.dao.PlannedFacebookPostDao;
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
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("postLocationType", postLocationType);
		params.put("locationId", locationId);
		return executeQueryGetList("from PlannedFacebookPost where locationType = :postLocationType and locationId = :locationId", params);
	}
	
}
