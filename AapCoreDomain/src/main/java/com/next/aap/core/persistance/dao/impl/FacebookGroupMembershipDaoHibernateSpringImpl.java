package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.FacebookGroupMembership;
import com.next.aap.core.persistance.dao.FacebookGroupMembershipDao;

@Component
public class FacebookGroupMembershipDaoHibernateSpringImpl extends BaseDaoHibernateSpring<FacebookGroupMembership> implements FacebookGroupMembershipDao{

	private static final long serialVersionUID = 1L;

	public FacebookGroupMembershipDaoHibernateSpringImpl() {
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupMembershipDao#saveFacebookGroupMembership(com.next.imager.persistance.FacebookGroupMembership)
	 */
	@Override
	public FacebookGroupMembership saveFacebookGroupMembership(FacebookGroupMembership facebookGroupMembership){
		checkIfObjectMissing("Facebook Account", facebookGroupMembership.getFacebookAccount());
		checkIfObjectMissing("Facebook Group", facebookGroupMembership.getFacebookGroup());
		facebookGroupMembership = super.saveObject(facebookGroupMembership);
		return facebookGroupMembership;
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupMembershipDao#getFacebookGroupMembershipById(java.lang.Long)
	 */
	@Override
	public FacebookGroupMembership getFacebookGroupMembershipById(Long id){
		return super.getObjectById(FacebookGroupMembership.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupMembershipDao#getFacebookGroupMembershipByFacebookAccountId(java.lang.Long)
	 */
	@Override
	public List<FacebookGroupMembership> getFacebookGroupMembershipByFacebookAccountId(Long facebookAccountId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookAccountId", facebookAccountId);
		List<FacebookGroupMembership> list = executeQueryGetList("from FacebookGroupMembership where facebookAccountId = :facebookAccountId", params);
		return list;
	}
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupMembershipDao#getFacebookGroupMembershipByFacebookGroupIdForPosting(java.lang.Long)
	 */
	@Override
	public FacebookGroupMembership getFacebookGroupMembershipByFacebookGroupIdForPosting(Long facebookGroupId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookGroupId", facebookGroupId);
		List<FacebookGroupMembership> facebookGroupMembership = executeQueryGetList("from FacebookGroupMembership where facebookGroupId = :facebookGroupId order by lastPostDate asc", params, 1);
		if(facebookGroupMembership == null || facebookGroupMembership.isEmpty()){
			return null;
		}
		return facebookGroupMembership.get(0);
	}
	
	@Override
	public List<FacebookGroupMembership> getAllFacebookGroupMembershipByFacebookGroupIdForPosting(Long facebookGroupId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookGroupId", facebookGroupId);
		List<FacebookGroupMembership> facebookGroupMembership = executeQueryGetList("from FacebookGroupMembership where facebookGroupId = :facebookGroupId order by lastPostDate asc", params);
		return facebookGroupMembership;
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupMembershipDao#getFacebookGroupMembershipByFacebookGroupIdForReading(java.lang.Long)
	 */
	@Override
	public FacebookGroupMembership getFacebookGroupMembershipByFacebookGroupIdForReading(Long facebookGroupId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookGroupId", facebookGroupId);
		List<FacebookGroupMembership> facebookGroupMembership = executeQueryGetList("from FacebookGroupMembership where facebookGroupId = :facebookGroupId order by lastReadDate asc", params, 1);
		if(facebookGroupMembership == null || facebookGroupMembership.isEmpty()){
			return null;
		}
		return facebookGroupMembership.get(0);
	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupMembershipDao#getFacebookGroupMembershipByFacebookUserIdAndGroupId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public FacebookGroupMembership getFacebookGroupMembershipByFacebookUserIdAndGroupId(Long facebookAccountId,Long facebookGroupId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookGroupId", facebookGroupId);
		params.put("facebookAccountId", facebookAccountId);
		List<FacebookGroupMembership> facebookGroupMembership = executeQueryGetList("from FacebookGroupMembership where facebookGroupId = :facebookGroupId and facebookAccountId = :facebookAccountId", params, 1);
		if(facebookGroupMembership == null || facebookGroupMembership.isEmpty()){
			return null;
		}
		return facebookGroupMembership.get(0);
	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupMembershipDao#getFacebookGroupMembershipsAfterId(java.lang.Long, int)
	 */
	@Override
	public List<FacebookGroupMembership> getFacebookGroupMembershipsAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		List<FacebookGroupMembership> list = executeQueryGetList("from FacebookGroupMembership where id > :lastId order by id asc", params, pageSize);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupMembershipDao#getFacebookGroupMembershipsAfterIdForFacebookGroup(java.lang.Long, java.lang.Long, int)
	 */
	@Override
	public List<FacebookGroupMembership> getFacebookGroupMembershipsAfterIdForFacebookGroup(Long facebookGroupId,Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		params.put("facebookGroupId", facebookGroupId);
		List<FacebookGroupMembership> list = executeQueryGetList("from FacebookGroupMembership where id > :lastId and facebookGroupId > :facebookGroupId order by id asc", params, pageSize);
		return list;

	}
}
