package com.next.aap.core.persistance.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.News;
import com.next.aap.core.persistance.dao.NewsDao;
import com.next.aap.web.dto.ContentStatus;

@Repository
public class NewsDaoHibernateSpringImpl extends BaseDaoHibernateSpring<News> implements NewsDao{


	private static final long serialVersionUID = 1L;

	@Override
	public News saveNews(News news) {
		saveObject(news);
		return news;
	}

	@Override
	public void deleteNews(News news) {
		deleteObject(news);
	}

	@Override
	public News getNewsById(Long id) {
		return (News)getObjectById(News.class, id);
	}

	@Override
	public List<News> getAllNewss(int totalItems, int pageNumber) {
		String query = "from News order by dateCreated desc";
		List<News> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public News getNewsByWebUrl(String webUrl) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("webUrl", webUrl);
		String query = "from News where webUrl=:webUrl";
		News dbNews = executeQueryGetObject(query, params);
		return dbNews;
	}

	@Override
	public News getNewsByOriginalUrl(String originalUrl) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("originalUrl", originalUrl);
		String query = "from News where originalUrl=:originalUrl";
		News dbNews = executeQueryGetObject(query, params);
		return dbNews;
	}

	@Override
	public List<News> getAllNewss() {
		String query = "from News order by dateCreated desc";
		List<News> list = executeQueryGetList(query);
		return list;
	}

	@Override
	public long getLastNewsId() {
		String query = "select max(id) from News";
		Long maxId = executeQueryGetLong(query);
		if(maxId == null){
			return 0;
		}
		return maxId;
	}

	@Override
	public List<News> getNewsItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
		String query = "from News where id > :lastId order by id asc";
		List<News> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public List<Long> getAllNewsByLocation(long acId, long districtId, long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acId", acId);
		params.put("districtId", districtId);
		params.put("stateId", stateId);
		
		String query = "select newslist.newsId from ((select news_id as newsId from news_ac where ac_id = :acId) " +
				"union (select news_id as newsId from news_district where district_id= :districtId) " +
				"union (select news_id as newsId from news_state where state_id= :stateId) " +
				"union (select id as newsId from news where global_allowed= true)) newslist order by newslist.newsId desc";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}

	@Override
	public List<Long> getNewsByLocation(long pcId, long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pcId", pcId);
		params.put("stateId", stateId);
		
		String query = "select newslist.newsId from ((select news_id as newsId from news_pc where pc_id = :pcId) " +
				"union (select news_id as newsId from news_state where state_id= :stateId) " +
				"union (select id as newsId from news where global_allowed= true)) newslist order by newslist.newsId desc";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}

	@Override
	public List<News> getGlobalNews() {
		Map<String, Object> params = new HashMap<>(1);
		params.put("global", true);
		String query = "from News where global = :global order by dateCreated desc";
		List<News> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public List<News> getStateNews(Long stateId) {
		String sqlQuery = "select news_id from news_state where state_id = :stateId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("stateId", stateId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<News> getDistrictNews(Long districtId) {
		String sqlQuery = "select news_id from news_district where district_id = :districtId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("districtId", districtId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;

	}

	@Override
	public List<News> getAcNews(Long acId) {
		String sqlQuery = "select news_id from news_ac where ac_id = :acId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("acId", acId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<News> getPcNews(Long pcId) {
		String sqlQuery = "select news_id from news_pc where pc_id = :pcId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("pcId", pcId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<News> getCountryNews(Long countryId) {
		String sqlQuery = "select news_id from news_country where country_id = :countryId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryId", countryId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<News> getCountryRegionNews(Long countryRegionId) {
		String sqlQuery = "select news_id from news_country_region where country_region_id = :countryRegionId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryRegionId", countryRegionId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<News> getCountryRegionAreaNews(Long countryRegionAreaId) {
		String sqlQuery = "select news_id from news_country_region_area where country_region_area_id = :countryRegionAreaId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryRegionAreaId", countryRegionAreaId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(newsIds == null || newsIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<News> getAllPublishedNewss() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contentStatus", ContentStatus.Published);
		
		String query = "from News where contentStatus = :contentStatus order by publishDate desc";
		List<News> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public List<Long> getAllNewsByAc(long acId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acId", acId);
		
		String query = "select news_id as newsId from news_ac where ac_id = :acId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllNewsByPc(long pcId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pcId", pcId);
		
		String query = "select news_id as newsId from news_pc where pc_id = :pcId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}

	@Override
	public List<Long> getAllNewsByDistrict(long districtId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("districtId", districtId);
		
		String query = "select news_id as newsId from news_district where district_id = :districtId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}

	@Override
	public List<Long> getAllNewsByState(long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stateId", stateId);
		
		String query = "select news_id as newsId from news_state where state_id = :stateId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}

	@Override
	public List<Long> getAllNewsByCountry(long countryId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countryId", countryId);
		
		String query = "select news_id as newsId from news_country where country_id = :countryId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}

	@Override
	public List<Long> getAllNewsByCountryRegion(long countryRegionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countryRegionId", countryRegionId);
		
		String query = "select news_id as newsId from news_country_region where country_region_id = :countryRegionId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}
	
	private Map<Long, List<Long>> getNewsByLocationMapFromQuery(String query){
		List results = executeSqlQueryGetResultList(query);
		Map<Long, List<Long>> returnMap = new HashMap<>();
		Long acId;
		Long newsId;
		List<Long> newsIdList;
        for(ListIterator iter = results.listIterator(); iter.hasNext(); ) {
        	Object[] row = (Object[])iter.next();
        	if(row[0] instanceof BigInteger){
        		acId = ((BigInteger)row[0]).longValue();
            	newsId = ((BigInteger)row[1]).longValue();
        	}else{
        		acId = (Long)row[0];
            	newsId = (Long)row[1];
        	}
        	

        	newsIdList = returnMap.get(acId);
        	if(newsIdList == null){
        		newsIdList = new ArrayList<>();
        		returnMap.put(acId, newsIdList);
        	}
        	newsIdList.add(newsId);
        }
        return returnMap;
	}

	@Override
	public Map<Long, List<Long>> getNewsItemsOfAllAc() {
		String query = "select ac_id, news_id as newsId from news_ac";
		return getNewsByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getNewsItemsOfAllPc() {
		String query = "select pc_id, news_id as newsId from news_pc";
		return getNewsByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getNewsItemsOfAllDistrict() {
		String query = "select district_id, news_id as newsId from news_district";
		return getNewsByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getNewsItemsOfAllState() {
		String query = "select state_id, news_id as newsId from news_state";
		return getNewsByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getNewsItemsOfAllCountry() {
		String query = "select country_id, news_id as newsId from news_country";
		return getNewsByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getNewsItemsOfAllCountryRegion() {
		String query = "select country_region_id, news_id as newsId from news_country_region";
		return getNewsByLocationMapFromQuery(query);	
	}

}
