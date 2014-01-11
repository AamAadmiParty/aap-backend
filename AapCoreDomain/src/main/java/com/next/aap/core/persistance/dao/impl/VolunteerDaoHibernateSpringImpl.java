package com.next.aap.core.persistance.dao.impl;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.Volunteer;
import com.next.aap.core.persistance.dao.VolunteerDao;

@Component
public class VolunteerDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Volunteer> implements VolunteerDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.VolunteerDao#saveVolunteer(com.next.aap.server.persistance.Volunteer)
	 */
	@Override
	public Volunteer saveVolunteer(Volunteer volunteer){
		checkIfObjectMissing("User", volunteer.getUser());
		volunteer = super.saveObject(volunteer);
		return volunteer;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.VolunteerDao#getVolunteerById(java.lang.Long)
	 */
	@Override
	public Volunteer getVolunteerById(Long id) {
		return super.getObjectById(Volunteer.class, id);
	}

	@Override
	public Volunteer getVolunteersByUserId(Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		return executeQueryGetObject("from Volunteer where userId = :userId", params);
	}
}
