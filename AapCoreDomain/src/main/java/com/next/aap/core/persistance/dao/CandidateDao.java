package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Candidate;

public interface CandidateDao {

	public abstract Candidate saveCandidate(Candidate Candidate);
	
	public abstract void deleteCandidate(Candidate candidate);
	
	public abstract Candidate getCandidateById(Long id);
	
    public abstract Candidate getCandidateByPcIdAndElectionId(Long pcId, Long electionId);
	
    public abstract Candidate getCandidateByAcIdAndElectionId(Long acId, Long electionId);

	public abstract Candidate getCandidateByExtPcId(String pcId);
	
    public abstract Candidate getCandidateByExtAcId(String acId);

	public abstract List<Candidate> getAllCandidates(int totalItems, int pageNumber);
	
	public abstract List<Candidate> getAllCandidates();
	
    public abstract List<Candidate> getAllCandidatesByElectionId(Long electionId);

	public abstract List<Candidate> getCandidateItemsAfterId(long candidateId);

}
