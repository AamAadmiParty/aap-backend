package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.Blog;
import com.next.aap.core.persistance.dao.BlogDao;

@Repository
public class BlogDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Blog> implements BlogDao{


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
	public List<Blog> getBlogItemsAfterId(long newsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", newsId);
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

}
