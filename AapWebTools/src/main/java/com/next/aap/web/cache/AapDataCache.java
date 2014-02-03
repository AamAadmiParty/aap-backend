package com.next.aap.web.cache;

import com.next.aap.web.ItemList;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.PollQuestionDto;
import com.next.aap.web.dto.VideoDto;

public interface AapDataCache {

	public abstract void refreshFullCache();

	public abstract ItemList<NewsDto> getNewsDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId);
	
	public abstract ItemList<NewsDto> getNewsDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber);

	public abstract ItemList<VideoDto> getVideoDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId);

	public abstract ItemList<VideoDto> getVideoDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber);

	public abstract ItemList<BlogDto> getBlogDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId);

	public abstract ItemList<BlogDto> getBlogDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber);

	public abstract ItemList<PollQuestionDto> getPollQuestionDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId);

	public abstract ItemList<PollQuestionDto> getPollQuestionDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId,
			int pageNumber);

}