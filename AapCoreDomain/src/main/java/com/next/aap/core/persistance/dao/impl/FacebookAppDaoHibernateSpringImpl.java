package com.next.aap.core.persistance.dao.impl;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.FacebookApp;
import com.next.aap.core.persistance.dao.FacebookAppDao;

@Component
public class FacebookAppDaoHibernateSpringImpl extends BaseDaoHibernateSpring<FacebookApp> implements FacebookAppDao{

	private static final long serialVersionUID = 1L;
	public FacebookAppDaoHibernateSpringImpl() {
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookAppDao#saveFacebookApp(com.next.imager.persistance.FacebookApp)
	 */
	@Override
	public FacebookApp saveFacebookApp(FacebookApp facebookApp){
		facebookApp = super.saveObject(facebookApp);
		return facebookApp;
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookAppDao#getFacebookAppById(java.lang.Long)
	 */
	@Override
	public FacebookApp getFacebookAppById(Long id){
		return super.getObjectById(FacebookApp.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookAppDao#getFacebookAppByAppId(java.lang.String)
	 */
	@Override
	public FacebookApp getFacebookAppByAppId(String appId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("appId", appId);
		FacebookApp facebookApp = executeQueryGetObject("from FacebookApp where appId = :appId", params);
		return facebookApp;

	}
	
}
