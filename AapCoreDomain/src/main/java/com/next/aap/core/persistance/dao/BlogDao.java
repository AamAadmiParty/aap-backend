package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Blog;

public interface BlogDao {

	public abstract Blog saveBlog(Blog blog);
	
	public abstract void deleteBlog(Blog blog);
	
	public abstract Blog getBlogById(Long id);
	
	public abstract List<Blog> getAllBlogs(int totalItems, int pageNumber);
	
	public abstract List<Blog> getAllBlogs();
	
	public abstract Blog getBlogByWebUrl(String webUrl);
	
	public abstract Blog getBlogByOriginalUrl(String originalUrl);
	
	public abstract long getLastBlogId();
	
	public abstract List<Blog> getBlogItemsAfterId(long blogId);

	public abstract List<Long> getBlogByLocation(long acId, long districtId, long stateId);
	
	public abstract List<Long> getBlogByLocation(long pcId, long stateId);


}
