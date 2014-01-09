package com.next.aap.core.persistance.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.Interest;
import com.next.aap.core.persistance.dao.InterestDao;

@Component
public class InterestDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Interest> implements InterestDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.InterestDao#saveInterest(com.next.aap.server.persistance.Interest)
	 */
	@Override
	public Interest saveInterest(Interest interest){
		checkIfObjectMissing("Interest Group", interest.getInterestGroup());
		interest = super.saveObject(interest);
		return interest;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.InterestDao#getInterestById(java.lang.Long)
	 */
	@Override
	public Interest getInterestById(Long id) {
		return super.getObjectById(Interest.class, id);
	}

	@Override
	public List<Interest> getAllInterests() {
		return executeQueryGetList("from Interest");
	}


}
