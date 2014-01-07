package com.next.aap.core.persistance.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.InterestGroup;
import com.next.aap.core.persistance.dao.InterestGroupDao;

@Component
public class InterestGroupDaoHibernateSpringImpl extends BaseDaoHibernateSpring<InterestGroup> implements InterestGroupDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.InterestGroupDao#saveInterestGroup(com.next.aap.server.persistance.InterestGroup)
	 */
	@Override
	public InterestGroup saveInterestGroup(InterestGroup interestGroup){
		checkIfStringMissing("interestGroup", interestGroup.getDescription());
		interestGroup = super.saveObject(interestGroup);
		return interestGroup;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.InterestGroupDao#getInterestGroupById(java.lang.Long)
	 */
	@Override
	public InterestGroup getInterestGroupById(Long id) {
		return super.getObjectById(InterestGroup.class, id);
	}

	@Override
	public List<InterestGroup> getAllInterestGroups() {
		return executeQueryGetList("from InterestGroup order by description");
	}
}
