package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.FacebookGroup;
import com.next.aap.core.persistance.dao.FacebookGroupDao;

@Component
public class FacebookGroupDaoHibernateSpringImpl extends BaseDaoHibernateSpring<FacebookGroup> implements FacebookGroupDao{

	private static final long serialVersionUID = 1L;

	public FacebookGroupDaoHibernateSpringImpl() {
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupDao#saveFacebookGroup(com.next.imager.persistance.FacebookGroup)
	 */
	@Override
	public FacebookGroup saveFacebookGroup(FacebookGroup facebookGroup){
		checkIfStringMissing("Facebook Group ExternalId", facebookGroup.getFacebookGroupExternalId());
		checkIfStringMissing("Facebook Group Name", facebookGroup.getGroupName());
		facebookGroup = super.saveObject(facebookGroup);
		return facebookGroup;
	}

	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupDao#getFacebookGroupById(java.lang.Long)
	 */
	@Override
	public FacebookGroup getFacebookGroupById(Long id){
		return super.getObjectById(FacebookGroup.class, id);
	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupDao#getFacebookGroupByFacebookGroupExternalId(java.lang.String)
	 */
	@Override
	public FacebookGroup getFacebookGroupByFacebookGroupExternalId(String facebookGroupExternalId){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookGroupExternalId", facebookGroupExternalId);
		FacebookGroup facebookGroup = executeQueryGetObject("from FacebookGroup where facebookGroupExternalId = :facebookGroupExternalId", params);
		return facebookGroup;

	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupDao#getFacebookGroupsForPostingAfterId(java.lang.Long, int)
	 */
	@Override
	public List<FacebookGroup> getFacebookGroupsForPostingAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
        List<FacebookGroup> list = executeQueryGetList("from FacebookGroup where id > :lastId and postAllowed = true and totalMembers > 1000 and totalMembersWithPermissionToPost > 1 order by id asc",
                params, pageSize);
		//List<FacebookGroup> list = executeQueryGetList("from FacebookGroup where id > :lastId and postAllowed = true order by id asc", params, pageSize);
		return list;

	}
	
	/* (non-Javadoc)
	 * @see com.next.imager.persistance.helper.FacebookGroupDao#getFacebookGroups(java.lang.Long, int)
	 */
	@Override
	public List<FacebookGroup> getFacebookGroups(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		List<FacebookGroup> list = executeQueryGetList("from FacebookGroup where id > :lastId order by id asc", params, pageSize);
		return list;

	}
}
