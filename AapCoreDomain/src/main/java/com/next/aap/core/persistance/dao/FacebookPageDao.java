package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.FacebookPage;

public interface FacebookPageDao {

	public abstract FacebookPage saveFacebookPage(FacebookPage facebookPage);

	public abstract FacebookPage getFacebookPageById(Long id);

	public abstract FacebookPage getFacebookPageByFacebookPageExternalId(String facebookPageExternalId);

	public abstract List<FacebookPage> getFacebookPagesForPostingAfterId(Long lastId, int pageSize);

	public abstract List<FacebookPage> getFacebookPagesForRePostingAfterId(Long lastId, int pageSize);

}