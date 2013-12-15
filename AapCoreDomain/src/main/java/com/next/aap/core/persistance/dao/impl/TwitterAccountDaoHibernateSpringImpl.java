package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.TwitterAccount;
import com.next.aap.core.persistance.dao.TwitterAccountDao;


@Component
public class TwitterAccountDaoHibernateSpringImpl extends BaseDaoHibernateSpring<TwitterAccount> implements TwitterAccountDao{

	/* (non-Javadoc)
	 * @see com.next.vote.server.persistance.dao.impl.TwitterAccountDao#saveTwitterAccount(com.next.vote.server.persistance.TwitterAccount)
	 */
	@Override
	public TwitterAccount saveTwitterAccount(TwitterAccount twitterAccount) {
		//checkIfStringMissing("ScreenName", twitterAccount.getScreenName());
		twitterAccount.setScreenNameCap(twitterAccount.getScreenName().toUpperCase());
		twitterAccount = super.saveObject(twitterAccount);
		return twitterAccount;
	}

	/* (non-Javadoc)
	 * @see com.next.vote.server.persistance.dao.impl.TwitterAccountDao#getTwitterAccountById(java.lang.Long)
	 */
	@Override
	public TwitterAccount getTwitterAccountById(Long id) {
		return super.getObjectById(TwitterAccount.class, id);
	}
	
	/* (non-Javadoc)
	 * @see com.next.vote.server.persistance.dao.impl.TwitterAccountDao#getTwitterAccountByTwitterId(java.lang.Long)
	 */
	@Override
	public TwitterAccount getTwitterAccountByTwitterUserId(String twitterId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("twitterId", twitterId);
		TwitterAccount twitterAccount = executeQueryGetObject("from TwitterAccount where twitterId = :twitterId",params);
		return twitterAccount;
	}
	/*
	public TwitterAccount getTwitterAccountByUserId(Long userId) {
		HibernateQueryParamPageInfo pageInfo = new HibernateQueryParamPageInfo();
		QueryParam queryParam = new QueryParam();
		queryParam.setField("userId");
		queryParam.setFieldType(QueryParam.FIELD_TYPE_NUMBER);
		queryParam.setOperator(QueryParam.OPERATOR_EQUAL);
		queryParam.setValue(userId);
		pageInfo.addCriteria(queryParam);

		PageResult<TwitterAccount> pr = super.findObject(TwitterAccount.class, pageInfo);
		if(pr.getResultList() == null || pr.getResultList().size() <= 0){
			return null;
		}
		return pr.getResultList().get(0);
	}
	public TwitterAccount getTwitterAccountByTwitterScreenName(String twitterScreenName) {
		HibernateQueryParamPageInfo pageInfo = new HibernateQueryParamPageInfo();
		QueryParam queryParam = new QueryParam();
		queryParam.setField("screenNameCap");
		queryParam.setFieldType(QueryParam.FIELD_TYPE_STRING);
		queryParam.setOperator(QueryParam.OPERATOR_EQUAL);
		queryParam.setValue(twitterScreenName.toUpperCase());
		pageInfo.addCriteria(queryParam);

		PageResult<TwitterAccount> pr = super.findObject(TwitterAccount.class, pageInfo);
		if(pr.getResultList() == null || pr.getResultList().size() <= 0){
			return null;
		}
		return pr.getResultList().get(0);
	}
	
	public List<TwitterAccount> getTwitterAccountsAfterId(Long lastId,int pageSize) {
		HibernateQueryParamPageInfo pageInfo = new HibernateQueryParamPageInfo();
		QueryParam queryParam = new QueryParam();
		queryParam.setField("id");
		queryParam.setFieldType(QueryParam.FIELD_TYPE_NUMBER);
		queryParam.setOperator(QueryParam.OPERATOR_MORE_THEN);
		queryParam.setValue(lastId);
		pageInfo.addCriteria(queryParam);
		
		pageInfo.addOrderBy("id", ORDER.ASC);
		pageInfo.setPageSize(pageSize);
		
		PageResult<TwitterAccount> pr = super.findObject(TwitterAccount.class, pageInfo);
		return pr.getResultList();
	}
	*/

	@Override
	public TwitterAccount getTwitterAccountByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TwitterAccount getTwitterAccountByHandle(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TwitterAccount> getTwitterAccountsAfterId(Long lastId,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
}
