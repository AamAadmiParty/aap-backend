package com.next.aap.core.persistance.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.Blog;
import com.next.aap.core.persistance.dao.BlogDao;
import com.next.aap.web.dto.ContentStatus;

@Repository
public class BlogDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Blog> implements BlogDao{


	private static final long serialVersionUID = 1L;

	@Override
	public Blog saveBlog(Blog blog) {
		saveObject(blog);
		return blog;
	}

	@Override
	public void deleteBlog(Blog blog) {
		deleteObject(blog);
	}

	@Override
	public Blog getBlogById(Long id) {
		return (Blog)getObjectById(Blog.class, id);
	}

	@Override
	public List<Blog> getAllBlogs(int totalItems, int pageNumber) {
		String query = "from Blog order by dateCreated desc";
		List<Blog> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public Blog getBlogByWebUrl(String webUrl) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("webUrl", webUrl);
		String query = "from Blog where webUrl=:webUrl";
		Blog dbBlog = executeQueryGetObject(query, params);
		return dbBlog;
	}

	@Override
	public Blog getBlogByOriginalUrl(String originalUrl) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("originalUrl", originalUrl);
		String query = "from Blog where originalUrl=:originalUrl";
		Blog dbBlog = executeQueryGetObject(query, params);
		return dbBlog;
	}

	@Override
	public List<Blog> getAllBlogs() {
		String query = "from Blog order by dateCreated desc";
		List<Blog> list = executeQueryGetList(query);
		return list;
	}
	
	@Override
	public long getLastBlogId() {
		String query = "select max(id) from Blog";
		Long maxId = executeQueryGetLong(query);
		if(maxId == null){
			return 0;
		}
		return maxId;
	}

	@Override
	public List<Blog> getBlogItemsAfterId(long blogId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", blogId);
		String query = "from Blog where id > :lastId order by id asc";
		List<Blog> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public List<Long> getBlogByLocation(long acId, long districtId, long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acId", acId);
		params.put("districtId", districtId);
		params.put("stateId", stateId);
		
		String query = "select bloglist.blogId from ((select blog_id as blogId from blog_ac where ac_id = :acId) " +
				"union (select blog_id as blogId from blog_district where district_id= :districtId) " +
				"union (select blog_id as blogId from blog_state where state_id= :stateId) " +
				"union (select id as blogId from blogs where global_allowed= true)) bloglist order by bloglist.blogId desc";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}

	@Override
	public List<Long> getBlogByLocation(long pcId, long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pcId", pcId);
		params.put("stateId", stateId);
		
		String query = "select bloglist.blogId from ((select blog_id as blogId from blog_pc where pc_id = :pcId) " +
				"union (select blog_id as blogId from blog_state where state_id= :stateId) " +
				"union (select id as blogId from blogs where global_allowed= true)) bloglist order by bloglist.blogId desc";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}
	
	@Override
	public List<Blog> getGlobalBlog() {
		Map<String, Object> params = new HashMap<>(1);
		params.put("global", true);
		String query = "from Blog where global = :global order by dateCreated desc";
		List<Blog> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public List<Blog> getStateBlog(Long stateId) {
		String sqlQuery = "select blog_id from blog_state where state_id = :stateId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("stateId", stateId);
		List<Long> blogIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(blogIds == null || blogIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Blog where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", blogIds);
		List<Blog> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Blog> getDistrictBlog(Long districtId) {
		String sqlQuery = "select blog_id from blog_district where district_id = :districtId";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("districtId", districtId);
		List<Long> blogIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(blogIds == null || blogIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Blog where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", blogIds);
		List<Blog> list = executeQueryGetList(query, queryParams);
		return list;

	}

	@Override
	public List<Blog> getAcBlog(Long acId) {
		String sqlQuery = "select blog_id from blog_ac where ac_id = :acId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("acId", acId);
		List<Long> blogIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(blogIds == null || blogIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Blog where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", blogIds);
		List<Blog> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Blog> getPcBlog(Long pcId) {
		String sqlQuery = "select blog_id from blog_pc where pc_id = :pcId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("pcId", pcId);
		List<Long> blogIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(blogIds == null || blogIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Blog where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", blogIds);
		List<Blog> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Blog> getCountryBlog(Long countryId) {
		String sqlQuery = "select blog_id from blog_country where country_id = :countryId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryId", countryId);
		List<Long> blogIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(blogIds == null || blogIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Blog where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", blogIds);
		List<Blog> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Blog> getCountryRegionBlog(Long countryRegionId) {
		String sqlQuery = "select blog_id from blog_country_region where country_region_id = :countryRegionId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryRegionId", countryRegionId);
		List<Long> blogIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(blogIds == null || blogIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Blog where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", blogIds);
		List<Blog> list = executeQueryGetList(query, queryParams);
		return list;
	}

	@Override
	public List<Blog> getCountryRegionAreaBlog(Long countryRegionAreaId) {
		String sqlQuery = "select blog_id from blog_country_region_area where country_region_area_id = :countryRegionAreaId ";
		Map<String, Object> sqlQueryParams = new HashMap<String, Object>(1);
		sqlQueryParams.put("countryRegionAreaId", countryRegionAreaId);
		List<Long> blogIds = executeSqlQueryGetListOfLong(sqlQuery, sqlQueryParams);
		
		if(blogIds == null || blogIds.isEmpty()){
			return new ArrayList<>();
		}

		String query = "from Blog where id in (:ids) order by dateCreated desc";
		Map<String, Object> queryParams = new HashMap<String, Object>(1);
		queryParams.put("ids", blogIds);
		List<Blog> list = executeQueryGetList(query, queryParams);
		return list;
	}
	
	@Override
	public List<Blog> getAllPublishedBlogs() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contentStatus", ContentStatus.Published);
		
		String query = "from Blog where contentStatus = :contentStatus order by publishDate desc";		
		List<Blog> list = executeQueryGetList(query, params);
		return list;
	}

}
