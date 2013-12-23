package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.FacebookGroup;

public interface FacebookGroupDao {

	public abstract FacebookGroup saveFacebookGroup(FacebookGroup facebookGroup);

	public abstract FacebookGroup getFacebookGroupById(Long id);

	public abstract FacebookGroup getFacebookGroupByFacebookGroupExternalId(String facebookGroupExternalId);

	public abstract List<FacebookGroup> getFacebookGroupsForPostingAfterId(Long lastId, int pageSize);

	public abstract List<FacebookGroup> getFacebookGroups(Long lastId, int pageSize);

}