package com.next.aap.core.persistance.dao.impl;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.FacebookAppPermission;
import com.next.aap.core.persistance.dao.FacebookAppPermissionDao;

@Component
public class FacebookAppPermissionDaoHibernateSpringImpl extends BaseDaoHibernateSpring<FacebookAppPermission> implements FacebookAppPermissionDao{


	private static final long serialVersionUID = 1L;
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookAppPermissionDao#saveFacebookAppPermission(com.next.imager.persistance.FacebookAppPermission)
	 */
	@Override
	public FacebookAppPermission saveFacebookAppPermission(FacebookAppPermission facebookApp) {
		facebookApp = super.saveObject(facebookApp);
		return facebookApp;
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookAppPermissionDao#getFacebookAppPermissionById(java.lang.Long)
	 */
	@Override
	public FacebookAppPermission getFacebookAppPermissionById(Long id) {
		return super.getObjectById(FacebookAppPermission.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookAppPermissionDao#getFacebookAppPermissionByAppIdAndFacebookAccountId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public FacebookAppPermission getFacebookAppPermissionByAppIdAndFacebookAccountId(Long facebookAppId,Long facebookAccountId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookAppId", facebookAppId);
		params.put("facebookAccountId", facebookAccountId);
		FacebookAppPermission facebookAppPermission = executeQueryGetObject("from FacebookAppPermission where facebookAppId = :facebookAppId and facebookAccountId = :facebookAccountId", params);
		return facebookAppPermission;
	}
	
	@Override
	public FacebookAppPermission getFacebookAppPermissionByFacebookAppIdAndFacebookAccountId(String facebookAppId,Long facebookAccountId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookAppId", facebookAppId);
		params.put("facebookAccountId", facebookAccountId);
		FacebookAppPermission facebookAppPermission = executeQueryGetObject("from FacebookAppPermission where facebookApp.appId = :facebookAppId and facebookAccountId = :facebookAccountId", params);
		return facebookAppPermission;
	}
	
}
