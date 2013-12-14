package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.GoogleAccount;
import com.next.aap.core.persistance.dao.GoogleAccountDao;

@Component
public class GoogleAccountDaoHibernateSpringImpl extends BaseDaoHibernateSpring<GoogleAccount> implements GoogleAccountDao{


	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.GoogleAccountDao#saveGoogleAccount(com.next.aap.server.persistance.GoogleAccount)
	 */
	@Override
	public GoogleAccount saveGoogleAccount(GoogleAccount googleAccount){
		googleAccount = super.saveObject(googleAccount);
		return googleAccount;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.GoogleAccountDao#getGoogleAccountById(java.lang.Long)
	 */
	@Override
	public GoogleAccount getGoogleAccountById(Long id) {
		return super.getObjectById(GoogleAccount.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.GoogleAccountDao#getGoogleAccountByUserId(java.lang.Long)
	 */
	@Override
	public GoogleAccount getGoogleAccountByUserId(Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		GoogleAccount googleAccount = executeQueryGetObject("from GoogleAccount where userId = :userId", params);
		return googleAccount;
	}
	
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.GoogleAccountDao#getGoogleAccountsAfterId(java.lang.Long, int)
	 */
	@Override
	public List<GoogleAccount> getGoogleAccountsAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		params.put("pageSize", pageSize);
		List<GoogleAccount> list = executeQueryGetList("from GoogleAccount where id > :lastId order by id asc limit :pageSize");
		return list;
	}

	@Override
	public GoogleAccount getGoogleAccountByGoogleId(String googleUserId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("googleUserId", googleUserId);
		GoogleAccount googleAccount = executeQueryGetObject("from GoogleAccount where googleUserId = :googleUserId", params);
		return googleAccount;
	}
}
