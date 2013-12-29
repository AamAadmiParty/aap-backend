package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.News;
import com.next.aap.core.persistance.dao.NewsDao;

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
	public List<Long> getNewsByLocation(long acId, long districtId, long stateId) {
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
		
		
		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<News> getDistrictNews(Long districtId) {
		String sqlQuery = "select news_id from news_district where district_id = :districtId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("districtId", districtId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		
		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;

	}

	@Override
	public List<News> getAcNews(Long acId) {
		String sqlQuery = "select news_id from news_ac where ac_id = :acId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("acId", acId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		
		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<News> getPcNews(Long pcId) {
		String sqlQuery = "select news_id from news_pc where pc_id = :pcId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("pcId", pcId);
		List<Long> newsIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		
		String query = "from News where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("ids", newsIds);
		List<News> list = executeQueryGetList(query, queryParams);
		return list;
	}

}
