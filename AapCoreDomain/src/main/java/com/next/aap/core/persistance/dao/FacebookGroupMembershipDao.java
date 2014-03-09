package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.FacebookGroupMembership;

public interface FacebookGroupMembershipDao {

	public abstract FacebookGroupMembership saveFacebookGroupMembership(FacebookGroupMembership facebookGroupMembership);

	public abstract FacebookGroupMembership getFacebookGroupMembershipById(Long id);

	public abstract List<FacebookGroupMembership> getFacebookGroupMembershipByFacebookAccountId(Long facebookAccountId);

	public abstract FacebookGroupMembership getFacebookGroupMembershipByFacebookGroupIdForPosting(Long facebookGroupId);

	public abstract FacebookGroupMembership getFacebookGroupMembershipByFacebookGroupIdForReading(Long facebookGroupId);
	
	public abstract FacebookGroupMembership getFacebookGroupMembershipByFacebookUserIdAndGroupId(Long facebookAccountId, Long facebookGroupId);

	public abstract List<FacebookGroupMembership> getFacebookGroupMembershipsAfterId(Long lastId, int pageSize);

	public abstract List<FacebookGroupMembership> getFacebookGroupMembershipsAfterIdForFacebookGroup(Long facebookGroupId, Long lastId, int pageSize);

	public List<FacebookGroupMembership> getAllFacebookGroupMembershipByFacebookGroupIdForPosting(Long facebookGroupId);
	

}