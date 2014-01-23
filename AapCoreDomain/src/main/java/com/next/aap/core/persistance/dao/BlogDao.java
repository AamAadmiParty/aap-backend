package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Blog;

public interface BlogDao {

	public abstract Blog saveBlog(Blog blog);
	
	public abstract void deleteBlog(Blog blog);
	
	public abstract Blog getBlogById(Long id);
	
	public abstract List<Blog> getAllBlogs(int totalItems, int pageNumber);
	
	public abstract List<Blog> getAllBlogs();
	
	public abstract List<Blog> getAllPublishedBlogs();
	
	public abstract Blog getBlogByWebUrl(String webUrl);
	
	public abstract Blog getBlogByOriginalUrl(String originalUrl);
	
	public abstract long getLastBlogId();
	
	public abstract List<Blog> getBlogItemsAfterId(long blogId);

	public abstract List<Long> getBlogByLocation(long acId, long districtId, long stateId);
	
	public abstract List<Long> getBlogByLocation(long pcId, long stateId);

	
	public abstract List<Blog> getGlobalBlog();
	
	public abstract List<Blog> getStateBlog(Long stateId);
	
	public abstract List<Blog> getDistrictBlog(Long districtId);
	
	public abstract List<Blog> getAcBlog(Long acId);
	
	public abstract List<Blog> getPcBlog(Long pcId);
	
	public abstract List<Blog> getCountryBlog(Long countryId);
	
	public abstract List<Blog> getCountryRegionBlog(Long countryRegionId);
	
	public abstract List<Blog> getCountryRegionAreaBlog(Long countryRegionAreaId);

}
