package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.News;
import com.next.aap.core.persistance.dao.NewsDao;

@Repository
public class NewsDaoHibernateSpringImpl extends BaseDaoHibernateSpring<News> implements NewsDao{


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

}
