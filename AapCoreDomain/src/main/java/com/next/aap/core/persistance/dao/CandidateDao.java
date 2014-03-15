package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Candidate;

public interface CandidateDao {

	public abstract Candidate saveCandidate(Candidate Candidate);
	
	public abstract void deleteCandidate(Candidate candidate);
	
	public abstract Candidate getCandidateById(Long id);
	
	public abstract Candidate getCandidateByPcId(Long pcId);
	
	public abstract List<Candidate> getAllCandidates(int totalItems, int pageNumber);
	
	public abstract List<Candidate> getAllCandidates();
	
	public abstract List<Candidate> getCandidateItemsAfterId(long candidateId);

}
