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
		return getObjectById(Candidate.class, id);
	}

	@Override
	public List<Candidate> getAllCandidates(int totalItems, int pageNumber) {
		String query = "from Candidate order by stateName, pcName";
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
    public Candidate getCandidateByPcIdAndElectionId(Long pcId, Long electionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parliamentConstituencyId", pcId);
        params.put("electionId", electionId);
        String query = "from Candidate where parliamentConstituencyId = :parliamentConstituencyId and electionId = :electionId";
		return  executeQueryGetObject(query, params);
	}

    @Override
    public Candidate getCandidateByAcIdAndElectionId(Long acId, Long electionId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("assemblyConstituencyId", acId);
        params.put("electionId", electionId);
        String query = "from Candidate where assemblyConstituencyId = :assemblyConstituencyId and electionId = :electionId";
        return executeQueryGetObject(query, params);
    }

	@Override
	public Candidate getCandidateByExtPcId(String pcIdExt) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pcIdExt", pcIdExt);
		String query = "from Candidate where pcIdExt = :pcIdExt";
		return  executeQueryGetObject(query, params);
	}

    @Override
    public List<Candidate> getAllCandidatesByElectionId(Long electionId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("electionId", electionId);
        String query = "from Candidate where electionId = :electionId";
        return executeQueryGetList(query, params);
    }

}
