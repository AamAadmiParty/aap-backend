package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.News;

public interface NewsDao {

	public abstract News saveNews(News news);
	
	public abstract void deleteNews(News news);
	
	public abstract News getNewsById(Long id);
	
	public abstract List<News> getAllNewss(int totalItems, int pageNumber);
	
	public abstract List<News> getAllNewss();
	
	public abstract News getNewsByWebUrl(String webUrl);
	
	public abstract News getNewsByOriginalUrl(String originalUrl);
	
	public abstract long getLastNewsId();
	
	public abstract List<News> getNewsItemsAfterId(long newsId);
	
	public abstract List<Long> getNewsByLocation(long acId, long districtId, long stateId);
	
	public abstract List<Long> getNewsByLocation(long pcId, long stateId);
	
	
	public abstract List<News> getGlobalNews();
	
	public abstract List<News> getStateNews(Long stateId);
	
	public abstract List<News> getDistrictNews(Long districtId);
	
	public abstract List<News> getAcNews(Long acId);
	
	public abstract List<News> getPcNews(Long pcId);
	
}
