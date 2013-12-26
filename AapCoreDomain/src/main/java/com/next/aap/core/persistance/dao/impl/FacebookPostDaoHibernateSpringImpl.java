package com.next.aap.core.persistance.dao.impl;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.FacebookPost;
import com.next.aap.core.persistance.dao.FacebookPostDao;

@Component
public class FacebookPostDaoHibernateSpringImpl extends BaseDaoHibernateSpring<FacebookPost> implements FacebookPostDao {

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.next.imager.persistance.helper.FacebookPostDao#saveFacebookPost(com
	 * .next.imager.persistance.FacebookPost)
	 */
	@Override
	public FacebookPost saveFacebookPost(FacebookPost facebookPost) {
		facebookPost = super.saveObject(facebookPost);
		return facebookPost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.next.imager.persistance.helper.FacebookPostDao#getFacebookPostById
	 * (java.lang.Long)
	 */
	@Override
	public FacebookPost getFacebookPostById(Long id) {
		return super.getObjectById(FacebookPost.class, id);
	}

	@Override
	public FacebookPost getFacebookPostByPlannedPostIdAndFacebookAccountId(Long plannedFacebookPostId, Long facebookAccountId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("plannedFacebookPostId", plannedFacebookPostId);
		params.put("facebookAccountId", facebookAccountId);
		FacebookPost facebookPost = executeQueryGetObject("from FacebookPost where plannedFacebookPostId = :plannedFacebookPostId and facebookAccountId = :facebookAccountId", params);
		return facebookPost;
	}


}
