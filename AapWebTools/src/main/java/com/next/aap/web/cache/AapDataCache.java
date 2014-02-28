package com.next.aap.web.cache;

import java.util.List;

import com.next.aap.web.ItemList;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.PollQuestionDto;
import com.next.aap.web.dto.VideoDto;

public interface AapDataCache {

	public abstract void refreshFullCache();

	public abstract ItemList<NewsDto> getNewsDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId);
	
	public abstract ItemList<NewsDto> getNewsDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber);

	public abstract NewsDto getNewsById(String lang, long newsId);
	
	public abstract NewsDto getNewsById(long newsId);

	public abstract ItemList<VideoDto> getVideoDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId);

	public abstract ItemList<VideoDto> getVideoDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber);

	public abstract VideoDto getVideoById(String lang, long videoId);
	
	public abstract VideoDto getVideoById(long videoId);

	public abstract ItemList<BlogDto> getBlogDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId);

	public abstract ItemList<BlogDto> getBlogDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber);

	public abstract BlogDto getBlogById(String lang, long blogId);
	
	public abstract BlogDto getBlogById(long blogId);

	public abstract ItemList<PollQuestionDto> getPollQuestionDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId);

	public abstract ItemList<PollQuestionDto> getPollQuestionDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId,
			int pageNumber);

	public abstract PollQuestionDto getPollQuestionById(String lang, long pollQuestionId);
	
	public abstract PollQuestionDto getPollQuestionDtoById(long pollQuestionId);
	
	public void loadAcNews(long acId);
	public void loadPcNews(long pcId);
	public void loadStateNews(long stateId);
	public void loadDistrictNews(long districtId);

	public void loadAcBlogs(long acId);
	public void loadPcBlogs(long pcId);
	public void loadStateBlogs(long stateId);
	public void loadDistrictBlogs(long districtId);
	
	public void loadAcVideos(long acId);
	public void loadPcVideos(long pcId);
	public void loadStateVideos(long stateId);
	public void loadDistrictVideos(long districtId);
	
}