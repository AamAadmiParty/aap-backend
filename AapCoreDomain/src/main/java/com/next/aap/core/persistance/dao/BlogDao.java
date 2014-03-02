package com.next.aap.core.persistance.dao;

import java.util.List;
import java.util.Map;

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

	
	public abstract List<Long> getAllBlogByAc(long acId);
	
	public abstract List<Long> getAllBlogByPc(long pcId);
	
	public abstract List<Long> getAllBlogByDistrict(long districtId);
	
	public abstract List<Long> getAllBlogByState(long stateId);
	
	public abstract List<Long> getAllBlogByCountry(long countryId);
	
	public abstract List<Long> getAllBlogByCountryRegion(long countryRegionId);
	
	
	public abstract Map<Long, List<Long>> getBlogItemsOfAllAc();
	
	public abstract Map<Long, List<Long>> getBlogItemsOfAllPc();
	
	public abstract Map<Long, List<Long>> getBlogItemsOfAllDistrict();
	
	public abstract Map<Long, List<Long>> getBlogItemsOfAllState();
	
	public abstract Map<Long, List<Long>> getBlogItemsOfAllCountry();
	
	public abstract Map<Long, List<Long>> getBlogItemsOfAllCountryRegion();
}
