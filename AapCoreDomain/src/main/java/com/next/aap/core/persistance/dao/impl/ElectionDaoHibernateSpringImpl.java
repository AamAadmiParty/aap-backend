package com.next.aap.core.persistance.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.Election;
import com.next.aap.core.persistance.dao.ElectionDao;

@Component
public class ElectionDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Election> implements ElectionDao {


	private static final long serialVersionUID = 1;

	    /*
     * (non-Javadoc)
     * 
     * @see
     * com.next.aap.server.persistance.dao.impl.ElectionDao#saveElection(com
     * .next.aap.server.persistance.Election)
     */
	@Override
    public Election saveElection(Election Election) {
        checkIfObjectMissing("Election Title", Election.getTitle());
        Election = super.saveObject(Election);
        return Election;
	}

	    /*
     * (non-Javadoc)
     * 
     * @see
     * com.next.aap.server.persistance.dao.impl.ElectionDao#getElectionById(
     * java.lang.Long)
     */
	@Override
    public Election getElectionById(Long id) {
        return super.getObjectById(Election.class, id);
	}

    @Override
    public List<Election> getAllElections() {
        return getAll(Election.class);
    }

}
