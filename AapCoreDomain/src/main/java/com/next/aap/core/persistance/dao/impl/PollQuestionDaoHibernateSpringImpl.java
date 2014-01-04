package com.next.aap.core.persistance.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.PollQuestion;
import com.next.aap.core.persistance.dao.PollQuestionDao;

@Repository
public class PollQuestionDaoHibernateSpringImpl extends BaseDaoHibernateSpring<PollQuestion> implements PollQuestionDao{

	private static final long serialVersionUID = 1L;

	@Override
	public PollQuestion savePollQuestion(PollQuestion pollQuestion) {
		saveObject(pollQuestion);
		return pollQuestion;
	}

	@Override
	public void deletePollQuestion(PollQuestion pollQuestion) {
		deleteObject(pollQuestion);
	}

	@Override
	public PollQuestion getPollQuestionById(Long id) {
		return (PollQuestion)getObjectById(PollQuestion.class, id);
	}

	@Override
	public List<PollQuestion> getAllPollQuestions(int totalItems, int pageNumber) {
		String query = "from PollQuestion order by dateCreated desc";
		List<PollQuestion> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public PollQuestion getPollQuestionByWebUrl(String webUrl) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("webUrl", webUrl);
		String query = "from PollQuestion where webUrl=:webUrl";
		PollQuestion dbPollQuestion = executeQueryGetObject(query, params);
		return dbPollQuestion;
	}

	@Override
	public PollQuestion getPollQuestionByOriginalUrl(String originalUrl) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("originalUrl", originalUrl);
		String query = "from PollQuestion where originalUrl=:originalUrl";
		PollQuestion dbPollQuestion = executeQueryGetObject(query, params);
		return dbPollQuestion;
	}

	@Override
	public List<PollQuestion> getAllPollQuestions() {
		String query = "from PollQuestion order by dateCreated desc";
		List<PollQuestion> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public long getLastPollQuestionId() {
		String query = "select max(id) from PollQuestion";
		Long maxId = executeQueryGetLong(query);
		if(maxId == null){
			return 0;
		}
		return maxId;
	}

	@Override
	public List<PollQuestion> getPollQuestionItemsAfterId(long pollQuestionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", pollQuestionId);
		String query = "from PollQuestion where id > :lastId order by id asc";
		List<PollQuestion> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public List<Long> getPollQuestionByLocation(long acId, long districtId, long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acId", acId);
		params.put("districtId", districtId);
		params.put("stateId", stateId);
		
		String query = "select pollQuestionlist.pollQuestionId from ((select poll_question_id as pollQuestionId from poll_question_ac where ac_id = :acId) " +
				"union (select poll_question_id as pollQuestionId from poll_question_district where district_id= :districtId) " +
				"union (select poll_question_id as pollQuestionId from poll_question_state where state_id= :stateId) " +
				"union (select id as pollQuestionId from pollQuestions where global_allowed= true)) pollQuestionlist order by pollQuestionlist.pollQuestionId desc";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}

	@Override
	public List<Long> getPollQuestionByLocation(long pcId, long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pcId", pcId);
		params.put("stateId", stateId);
		
		String query = "select pollQuestionlist.pollQuestionId from ((select poll_question_id as pollQuestionId from poll_question_pc where pc_id = :pcId) " +
				"union (select poll_question_id as pollQuestionId from poll_question_state where state_id= :stateId) " +
				"union (select id as pollQuestionId from poll_questions where global_allowed= true)) pollQuestionlist order by pollQuestionlist.pollQuestionId desc";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}
	
	@Override
	public List<PollQuestion> getGlobalPollQuestion() {
		Map<String, Object> params = new HashMap<>(1);
		params.put("global", true);
		String query = "from PollQuestion where global = :global order by dateCreated desc";
		List<PollQuestion> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public List<PollQuestion> getStatePollQuestion(Long stateId) {
		String sqlQuery = "select poll_question_id from poll_question_state where state_id = :stateId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("stateId", stateId);
		List<Long> pollQuestionIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(pollQuestionIds == null || pollQuestionIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from PollQuestion where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", pollQuestionIds);
		List<PollQuestion> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<PollQuestion> getDistrictPollQuestion(Long districtId) {
		String sqlQuery = "select poll_question_id from poll_question_district where district_id = :districtId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("districtId", districtId);
		List<Long> pollQuestionIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(pollQuestionIds == null || pollQuestionIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from PollQuestion where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", pollQuestionIds);
		List<PollQuestion> list = executeQueryGetList(query, queryParams);
		return list;

	}

	@Override
	public List<PollQuestion> getAcPollQuestion(Long acId) {
		String sqlQuery = "select poll_question_id from poll_question_ac where ac_id = :acId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("acId", acId);
		List<Long> pollQuestionIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(pollQuestionIds == null || pollQuestionIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from PollQuestion where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", pollQuestionIds);
		List<PollQuestion> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<PollQuestion> getPcPollQuestion(Long pcId) {
		String sqlQuery = "select poll_question_id from poll_question_pc where pc_id = :pcId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("pcId", pcId);
		List<Long> pollQuestionIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(pollQuestionIds == null || pollQuestionIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from PollQuestion where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", pollQuestionIds);
		List<PollQuestion> list = executeQueryGetList(query, queryParams);
		return list;
	}

}
