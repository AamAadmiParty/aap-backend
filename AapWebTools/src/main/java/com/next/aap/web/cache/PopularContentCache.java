package com.next.aap.web.cache;

import java.util.List;

import com.next.aap.core.persistance.dao.NewsDao;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.VideoDto;

public interface PopularContentCache {

	List<BlogDto> getPopularBlogs();
	
	List<NewsDto> getPopularNews();
	
	List<VideoDto> getPopularVideos();
	
}
