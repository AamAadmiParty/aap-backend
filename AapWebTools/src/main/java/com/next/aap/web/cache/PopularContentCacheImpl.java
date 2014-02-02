package com.next.aap.web.cache;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.VideoDto;

@Component
public class PopularContentCacheImpl implements PopularContentCache {

	@Autowired
	private AapService aapService;
	
	@PostConstruct
	public void init(){
		refreshPopularContent();
	}
	//Connect to Google Analytics and get analytics every hour
	@Scheduled(cron = "01 01 * * * *")
	public void refreshPopularContent() {
		
	}

	@Override
	public List<BlogDto> getPopularBlogs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NewsDto> getPopularNews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VideoDto> getPopularVideos() {
		// TODO Auto-generated method stub
		return null;
	}

}
