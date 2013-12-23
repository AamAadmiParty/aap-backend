package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.FacebookPage;
import com.next.aap.core.persistance.dao.FacebookPageDao;

@Component
public class FacebookPageDaoHibernateSpringImpl extends BaseDaoHibernateSpring<FacebookPage> implements FacebookPageDao{

	public FacebookPageDaoHibernateSpringImpl() {
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageDao#saveFacebookPage(com.next.imager.persistance.FacebookPage)
	 */
	@Override
	public FacebookPage saveFacebookPage(FacebookPage facebookPage){
		checkIfStringMissing("Facebook Page ExternalId", facebookPage.getFacebookPageExternalId());
		checkIfStringMissing("Facebook Page Name", facebookPage.getPageName());
		facebookPage = super.saveObject(facebookPage);
		return facebookPage;
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageDao#getFacebookPageById(java.lang.Long)
	 */
	@Override
	public FacebookPage getFacebookPageById(Long id){
		return super.getObjectById(FacebookPage.class, id);
	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageDao#getFacebookPageByFacebookPageExternalId(java.lang.String)
	 */
	@Override
	public FacebookPage getFacebookPageByFacebookPageExternalId(String facebookPageExternalId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookPageExternalId", facebookPageExternalId);
		FacebookPage facebookPage = executeQueryGetObject("from FacebookPage where facebookPageExternalId = :facebookPageExternalId", params);
		return facebookPage;
	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageDao#getFacebookPagesForPostingAfterId(java.lang.Long, int)
	 */
	@Override
	public List<FacebookPage> getFacebookPagesForPostingAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		List<FacebookPage> list = executeQueryGetList("from FacebookPage where id > :lastId and postAllowed = true order by id asc", params, pageSize);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageDao#getFacebookPagesForRePostingAfterId(java.lang.Long, int)
	 */
	@Override
	public List<FacebookPage> getFacebookPagesForRePostingAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		List<FacebookPage> list = executeQueryGetList("from FacebookPage where id > :lastId and postAllowedToChild = true order by id asc", params, pageSize);
		return list;
	}
}
