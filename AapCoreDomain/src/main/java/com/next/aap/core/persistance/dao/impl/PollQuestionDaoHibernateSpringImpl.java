package com.next.aap.core.persistance.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.News;
import com.next.aap.core.persistance.PollQuestion;
import com.next.aap.core.persistance.dao.PollQuestionDao;
import com.next.aap.web.dto.ContentStatus;

@Repository
public class PollQuestionDaoHibernateSpringImpl extends BaseDaoHibernateSpring<PollQuestion> implements PollQuestionDao{

	private static final long serialVersionUID = 1L;

	@Override
	public PollQuestion savePollQuestion(PollQuestion pollQuestion) {
		pollQuestion = saveObject(pollQuestion);
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
		
		if(acId == 587L){
			System.out.println("acId="+acId);
			System.out.println("districtId="+districtId);
			System.out.println("stateId="+stateId);
		}

		String query = "select pollQuestionlist.pollQuestionId from ((select poll_question_id as pollQuestionId from poll_question_ac where ac_id = :acId) " +
				"union (select poll_question_id as pollQuestionId from poll_question_district where district_id= :districtId) " +
				"union (select poll_question_id as pollQuestionId from poll_question_state where state_id= :stateId) " +
				"union (select id as pollQuestionId from poll_questions where global_allowed= true)) pollQuestionlist order by pollQuestionlist.pollQuestionId desc";
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

	@Override
	public List<PollQuestion> getCountryPollQuestion(Long countryId) {
		String sqlQuery = "select poll_question_id from poll_question_country where country_id = :countryId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryId", countryId);
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
	public List<PollQuestion> getCountryRegionPollQuestion(Long countryRegionId) {
		String sqlQuery = "select poll_question_id from poll_question_country_region where country_region_id = :countryRegionId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryRegionId", countryRegionId);
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
	public List<PollQuestion> getCountryRegionAreaPollQuestion(Long countryRegionAreaId) {
		String sqlQuery = "select poll_question_id from poll_question_country_region_area where country_region_area_id = :countryRegionAreaId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryRegionAreaId", countryRegionAreaId);
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
	public List<PollQuestion> getAllPollPublishedQuestions() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contentStatus", ContentStatus.Published);
		
		String query = "from PollQuestion where contentStatus = :contentStatus order by publishDate desc";
		List<PollQuestion> list = executeQueryGetList(query, params);
		return list;
	}
	
	@Override
	public List<Long> getAllPollQuestionByAc(long acId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acId", acId);
		String query = "select poll_question_id from poll_question_ac where ac_id = :acId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllPollQuestionByPc(long pcId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pcId", pcId);
		String query = "select poll_question_id from poll_question_pc where pc_id = :pcId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllPollQuestionByDistrict(long districtId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("districtId", districtId);
		String query = "select poll_question_id from poll_question_district where district_id = :districtId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllPollQuestionByState(long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stateId", stateId);
		String query = "select poll_question_id from poll_question_state where state_id = :stateId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllPollQuestionByCountry(long countryId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countryId", countryId);
		String query = "select poll_question_id from poll_question_country where country_id = :countryId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllPollQuestionByCountryRegion(long countryRegionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countryRegionId", countryRegionId);
		String query = "select poll_question_id from poll_question_country_region where country_region_id = :countryRegionId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	private Map<Long, List<Long>> getPollQuestionByLocationMapFromQuery(String query){
		List results = executeSqlQueryGetResultList(query);
		Map<Long, List<Long>> returnMap = new HashMap<>();
		Long acId;
		Long videoId;
		List<Long> videoIdList;
        for(ListIterator iter = results.listIterator(); iter.hasNext(); ) {
        	Object[] row = (Object[])iter.next();
        	if(row[0] instanceof BigInteger){
        		acId = ((BigInteger)row[0]).longValue();
            	videoId = ((BigInteger)row[1]).longValue();
        	}else{
        		acId = (Long)row[0];
            	videoId = (Long)row[1];
        	}
        	

        	videoIdList = returnMap.get(acId);
        	if(videoIdList == null){
        		videoIdList = new ArrayList<>();
        		returnMap.put(acId, videoIdList);
        	}
        	videoIdList.add(videoId);
        }
        return returnMap;
	}
	@Override
	public Map<Long, List<Long>> getPollQuestionItemsOfAllAc() {
		String query = "select ac_id, poll_question_id from poll_question_ac";
		return getPollQuestionByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getPollQuestionItemsOfAllPc() {
		String query = "select pc_id, poll_question_id from poll_question_pc";
		return getPollQuestionByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getPollQuestionItemsOfAllDistrict() {
		String query = "select district_id, poll_question_id from poll_question_district";
		return getPollQuestionByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getPollQuestionItemsOfAllState() {
		String query = "select state_id, poll_question_id from poll_question_state";
		return getPollQuestionByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getPollQuestionItemsOfAllCountry() {
		String query = "select country_id, poll_question_id from poll_question_country";
		return getPollQuestionByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getPollQuestionItemsOfAllCountryRegion() {
		String query = "select country_region_id, poll_question_id from poll_question_country_region";
		return getPollQuestionByLocationMapFromQuery(query);	
	}

}
