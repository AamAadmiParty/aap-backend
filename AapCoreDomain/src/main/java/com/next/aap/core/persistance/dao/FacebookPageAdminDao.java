package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.FacebookPageAdmins;

public interface FacebookPageAdminDao {

	public abstract FacebookPageAdmins saveFacebookPageAdmins(FacebookPageAdmins facebookPageAdmins) ;

	public abstract FacebookPageAdmins getFacebookPageAdminsById(Long id) ;

	public abstract List<FacebookPageAdmins> getFacebookPageAdminsByFacebookAccountId(Long facebookAccountId) ;

	public abstract FacebookPageAdmins getFacebookPageAdminsByFacebookPageIdForPosting(Long facebookGroupId) ;

	public abstract FacebookPageAdmins getFacebookPageAdminsByFacebookGroupIdForReading(Long facebookGroupId) ;

	public abstract FacebookPageAdmins getFacebookPageAdminsByFacebookUserIdAndPageId(Long facebookAccountId, Long facebookPageId) ;

	public abstract List<FacebookPageAdmins> getFacebookPageAdminssAfterId(Long lastId, int pageSize) ;

}