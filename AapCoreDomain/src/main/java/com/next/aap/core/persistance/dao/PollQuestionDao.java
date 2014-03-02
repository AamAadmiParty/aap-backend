package com.next.aap.core.persistance.dao;

import java.util.List;
import java.util.Map;

import com.next.aap.core.persistance.PollQuestion;

public interface PollQuestionDao {

	public abstract PollQuestion savePollQuestion(PollQuestion pollQuestion);
	
	public abstract void deletePollQuestion(PollQuestion pollQuestion);
	
	public abstract PollQuestion getPollQuestionById(Long id);
	
	public abstract List<PollQuestion> getAllPollQuestions(int totalItems, int pageNumber);
	
	public abstract List<PollQuestion> getAllPollQuestions();
	
	public abstract List<PollQuestion> getAllPollPublishedQuestions();
	
	public abstract PollQuestion getPollQuestionByWebUrl(String webUrl);
	
	public abstract PollQuestion getPollQuestionByOriginalUrl(String originalUrl);
	
	public abstract long getLastPollQuestionId();
	
	public abstract List<PollQuestion> getPollQuestionItemsAfterId(long pollQuestionId);

	public abstract List<Long> getPollQuestionByLocation(long acId, long districtId, long stateId);
	
	public abstract List<Long> getPollQuestionByLocation(long pcId, long stateId);

	
	public abstract List<PollQuestion> getGlobalPollQuestion();
	
	public abstract List<PollQuestion> getStatePollQuestion(Long stateId);
	
	public abstract List<PollQuestion> getDistrictPollQuestion(Long districtId);
	
	public abstract List<PollQuestion> getAcPollQuestion(Long acId);
	
	public abstract List<PollQuestion> getPcPollQuestion(Long pcId);
	
	public abstract List<PollQuestion> getCountryPollQuestion(Long pcId);
	
	public abstract List<PollQuestion> getCountryRegionPollQuestion(Long pcId);
	
	public abstract List<PollQuestion> getCountryRegionAreaPollQuestion(Long pcId);
	
	
	public abstract List<Long> getAllPollQuestionByAc(long acId);
	
	public abstract List<Long> getAllPollQuestionByPc(long pcId);
	
	public abstract List<Long> getAllPollQuestionByDistrict(long districtId);
	
	public abstract List<Long> getAllPollQuestionByState(long stateId);
	
	public abstract List<Long> getAllPollQuestionByCountry(long countryId);
	
	public abstract List<Long> getAllPollQuestionByCountryRegion(long countryRegionId);
	
	
	public abstract Map<Long, List<Long>> getPollQuestionItemsOfAllAc();
	
	public abstract Map<Long, List<Long>> getPollQuestionItemsOfAllPc();
	
	public abstract Map<Long, List<Long>> getPollQuestionItemsOfAllDistrict();
	
	public abstract Map<Long, List<Long>> getPollQuestionItemsOfAllState();
	
	public abstract Map<Long, List<Long>> getPollQuestionItemsOfAllCountry();
	
	public abstract Map<Long, List<Long>> getPollQuestionItemsOfAllCountryRegion();

}
