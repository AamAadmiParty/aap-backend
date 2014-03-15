package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.Candidate;
import com.next.aap.core.persistance.dao.CandidateDao;

@Repository
public class CandidateDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Candidate> implements CandidateDao{


	private static final long serialVersionUID = 1L;

	@Override
	public Candidate saveCandidate(Candidate candidate) {
		saveObject(candidate);
		return candidate;
	}

	@Override
	public void deleteCandidate(Candidate candidate) {
		deleteObject(candidate);
	}

	@Override
	public Candidate getCandidateById(Long id) {
		return (Candidate)getObjectById(Candidate.class, id);
	}

	@Override
	public List<Candidate> getAllCandidates(int totalItems, int pageNumber) {
		String query = "from Candidate order by name desc";
		List<Candidate> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public List<Candidate> getAllCandidates() {
		String query = "from Candidate order by name desc";
		List<Candidate> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public List<Candidate> getCandidateItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
		String query = "from Candidate where id > :lastId order by id asc";
		List<Candidate> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public Candidate getCandidateByPcId(Long pcId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parliamentConstituencyId", pcId);
		String query = "from Candidate where parliamentConstituencyId = :parliamentConstituencyId";
		return  executeQueryGetObject(query, params);
	}

}
