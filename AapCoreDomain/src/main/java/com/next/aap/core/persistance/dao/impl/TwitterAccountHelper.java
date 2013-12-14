package com.next.aap.core.persistance.dao.impl;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.TwitterAccount;

@Component
public class TwitterAccountHelper extends BaseDaoHibernateSpring<TwitterAccount>{

	public TwitterAccount saveTwitterAccount(TwitterAccount twitterAccount) {
		//checkIfStringMissing("ScreenName", twitterAccount.getScreenName());
		twitterAccount.setScreenNameCap(twitterAccount.getScreenName().toUpperCase());
		twitterAccount = super.saveObject(twitterAccount);
		return twitterAccount;
	}

	public TwitterAccount getTwitterAccountById(Long id) {
		return super.getObjectById(TwitterAccount.class, id);
	}
	/*
	public TwitterAccount getTwitterAccountByTwitterId(Long twitterId) {
		HibernateQueryParamPageInfo pageInfo = new HibernateQueryParamPageInfo();
		QueryParam queryParam = new QueryParam();
		queryParam.setField("twitterId");
		queryParam.setFieldType(QueryParam.FIELD_TYPE_NUMBER);
		queryParam.setOperator(QueryParam.OPERATOR_EQUAL);
		queryParam.setValue(twitterId);
		pageInfo.addCriteria(queryParam);

		PageResult<TwitterAccount> pr = super.findObject(TwitterAccount.class, pageInfo);
		if(pr.getResultList() == null || pr.getResultList().size() <= 0){
			return null;
		}
		return pr.getResultList().get(0);
	}
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
}
