package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.FacebookAccount;
import com.next.aap.core.persistance.dao.FacebookAccountDao;

@Component
public class FacebookAccountDaoHibernateSpringImpl extends BaseDaoHibernateSpring<FacebookAccount> implements FacebookAccountDao{


	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FacebookAccountDao#saveFacebookAccount(com.next.aap.server.persistance.FacebookAccount)
	 */
	@Override
	public FacebookAccount saveFacebookAccount(FacebookAccount facebookAccount){
		facebookAccount = super.saveObject(facebookAccount);
		return facebookAccount;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FacebookAccountDao#getFacebookAccountById(java.lang.Long)
	 */
	@Override
	public FacebookAccount getFacebookAccountById(Long id) {
		return super.getObjectById(FacebookAccount.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FacebookAccountDao#getFacebookAccountByUserId(java.lang.Long)
	 */
	@Override
	public FacebookAccount getFacebookAccountByUserId(Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		FacebookAccount facebookAccount = executeQueryGetObject("from FacebookAccount where userId = :userId", params);
		return facebookAccount;
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FacebookAccountDao#getFacebookAccountByUserName(java.lang.String)
	 */
	@Override
	public FacebookAccount getFacebookAccountByUserName(String userName){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userName", userName);
		FacebookAccount facebookAccount = executeQueryGetObject("from FacebookAccount where userName = :userName", params);
		return facebookAccount;
	}
	
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FacebookAccountDao#getFacebookAccountsAfterId(java.lang.Long, int)
	 */
	@Override
	public List<FacebookAccount> getFacebookAccountsAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		params.put("pageSize", pageSize);
		List<FacebookAccount> list = executeQueryGetList("from FacebookAccount where id > :lastId order by id asc limit :pageSize");
		return list;
	}

	@Override
	public FacebookAccount getFacebookAccountByFacebookUserId(String facebookUserId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookUserId", facebookUserId);
		FacebookAccount facebookAccount = executeQueryGetObject("from FacebookAccount where facebookUserId = :facebookUserId", params);
		return facebookAccount;
	}
}
