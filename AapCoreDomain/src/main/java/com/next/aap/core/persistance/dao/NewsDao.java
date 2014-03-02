package com.next.aap.core.persistance.dao;

import java.util.List;
import java.util.Map;

import com.next.aap.core.persistance.News;

public interface NewsDao {

	public abstract News saveNews(News news);
	
	public abstract void deleteNews(News news);
	
	public abstract News getNewsById(Long id);
	
	public abstract List<News> getAllNewss(int totalItems, int pageNumber);
	
	public abstract List<News> getAllNewss();
	
	public abstract List<News> getAllPublishedNewss();
	
	public abstract News getNewsByWebUrl(String webUrl);
	
	public abstract News getNewsByOriginalUrl(String originalUrl);
	
	public abstract long getLastNewsId();
	
	public abstract List<News> getNewsItemsAfterId(long newsId);
	
	public abstract List<Long> getAllNewsByLocation(long acId, long districtId, long stateId);
	
	public abstract List<Long> getNewsByLocation(long pcId, long stateId);
	
	
	public abstract List<News> getGlobalNews();
	
	public abstract List<News> getStateNews(Long stateId);
	
	public abstract List<News> getDistrictNews(Long districtId);
	
	public abstract List<News> getAcNews(Long acId);
	
	public abstract List<News> getPcNews(Long pcId);
	
	public abstract List<News> getCountryNews(Long countryId);
	
	public abstract List<News> getCountryRegionNews(Long countryRegionId);
	
	public abstract List<News> getCountryRegionAreaNews(Long countryRegionAreaId);
	
	public abstract List<Long> getAllNewsByAc(long acId);
	
	public abstract List<Long> getAllNewsByPc(long pcId);
	
	public abstract List<Long> getAllNewsByDistrict(long districtId);
	
	public abstract List<Long> getAllNewsByState(long stateId);
	
	public abstract List<Long> getAllNewsByCountry(long countryId);
	
	public abstract List<Long> getAllNewsByCountryRegion(long countryRegionId);
	
	
	public abstract Map<Long, List<Long>> getNewsItemsOfAllAc();
	
	public abstract Map<Long, List<Long>> getNewsItemsOfAllPc();
	
	public abstract Map<Long, List<Long>> getNewsItemsOfAllDistrict();
	
	public abstract Map<Long, List<Long>> getNewsItemsOfAllState();
	
	public abstract Map<Long, List<Long>> getNewsItemsOfAllCountry();
	
	public abstract Map<Long, List<Long>> getNewsItemsOfAllCountryRegion();

	
}
