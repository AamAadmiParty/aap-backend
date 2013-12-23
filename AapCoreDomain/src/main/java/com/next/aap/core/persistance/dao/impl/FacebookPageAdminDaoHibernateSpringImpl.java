package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.FacebookPageAdmins;
import com.next.aap.core.persistance.dao.FacebookPageAdminDao;

@Component
public class FacebookPageAdminDaoHibernateSpringImpl extends BaseDaoHibernateSpring<FacebookPageAdmins> implements FacebookPageAdminDao{

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageAdminDao#saveFacebookPageAdmins(com.next.imager.persistance.FacebookPageAdmins)
	 */
	@Override
	public FacebookPageAdmins saveFacebookPageAdmins(FacebookPageAdmins facebookPageAdmins){
		checkIfObjectMissing("Facebook Account", facebookPageAdmins.getFacebookAccount());
		checkIfObjectMissing("Facebook Access Token", facebookPageAdmins.getAccessToken());
		facebookPageAdmins = super.saveObject(facebookPageAdmins);
		return facebookPageAdmins;
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageAdminDao#getFacebookPageAdminsById(java.lang.Long)
	 */
	@Override
	public FacebookPageAdmins getFacebookPageAdminsById(Long id){
		return super.getObjectById(FacebookPageAdmins.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageAdminDao#getFacebookPageAdminsByFacebookAccountId(java.lang.Long)
	 */
	@Override
	public List<FacebookPageAdmins> getFacebookPageAdminsByFacebookAccountId(Long facebookAccountId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookAccountId", facebookAccountId);
		List<FacebookPageAdmins> list = executeQueryGetList("from FacebookPageAdmins where facebookAccountId > :facebookAccountId", params);
		return list;

	}
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageAdminDao#getFacebookPageAdminsByFacebookPageIdForPosting(java.lang.Long)
	 */
	@Override
	public FacebookPageAdmins getFacebookPageAdminsByFacebookPageIdForPosting(Long facebookGroupId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookGroupId", facebookGroupId);
		List<FacebookPageAdmins> list = executeQueryGetList("from FacebookPageAdmins where facebookGroupId = :facebookGroupId order by lastPostDate asc", params, 1);
		if(list == null || list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageAdminDao#getFacebookPageAdminsByFacebookGroupIdForReading(java.lang.Long)
	 */
	@Override
	public FacebookPageAdmins getFacebookPageAdminsByFacebookGroupIdForReading(Long facebookPageId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookPageId", facebookPageId);
		List<FacebookPageAdmins> list = executeQueryGetList("from FacebookPageAdmins where facebookPageId = :facebookPageId order by lastReadDate asc", params, 1);
		if(list == null || list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageAdminDao#getFacebookPageAdminsByFacebookUserIdAndPageId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public FacebookPageAdmins getFacebookPageAdminsByFacebookUserIdAndPageId(Long facebookAccountId,Long facebookPageId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookAccountId", facebookAccountId);
		params.put("facebookPageId", facebookPageId);
		FacebookPageAdmins facebookPageAdmins = executeQueryGetObject("from FacebookPageAdmins where facebookPageId = :facebookPageId and facebookAccountId = :facebookAccountId", params);
		return facebookPageAdmins;
	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookPageAdminDao#getFacebookPageAdminssAfterId(java.lang.Long, int)
	 */
	@Override
	public List<FacebookPageAdmins> getFacebookPageAdminssAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		List<FacebookPageAdmins> list = executeQueryGetList("from FacebookPageAdmins where id > :lastId order by id asc ", params, pageSize);
		return list;
	}
}
