package com.next.aap.web.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.PollQuestionDto;
import com.next.aap.web.dto.VideoDto;

public class AapDataHolder {

	Map<String,Map<Long, NewsDto>> newsCacheByLanguage = new HashMap<>();
	List<Long> allGlobalNewsIds = new ArrayList<>();
	Map<String,Map<Long, VideoDto>> videoCacheByLanguage = new HashMap<>();
	List<Long> allGlobalVideoIds = new ArrayList<>();
	Map<String,Map<Long, BlogDto>> blogCacheByLanguage = new HashMap<>();
	List<Long> allGlobalBlogIds = new ArrayList<>();
	Map<String,Map<Long, PollQuestionDto>> pollCacheByLanguage = new HashMap<>();
	List<Long> allGlobalPollIds = new ArrayList<>();
	
	Map<Long, List<Long>> acNewsDtos = new HashMap<>();
	Map<Long, List<Long>> acBlogDtos = new HashMap<>();
	Map<Long, List<Long>> acVideoDtos = new HashMap<>();
	Map<Long, List<Long>> acPollDtos = new HashMap<>();
	
	Map<Long, List<Long>> pcNewsDtos = new HashMap<>();
	Map<Long, List<Long>> pcBlogDtos = new HashMap<>();
	Map<Long, List<Long>> pcVideoDtos = new HashMap<>();
	Map<Long, List<Long>> pcPollDtos = new HashMap<>();
	
	Map<Long, Long> longCache = new HashMap<>();

	public void addNews(String language,NewsDto newsItem){
		Map<Long, NewsDto> languageNews = newsCacheByLanguage.get(language);
		if(languageNews == null){
			languageNews = new HashMap<>();
			newsCacheByLanguage.put(language, languageNews);
		}
		languageNews.put(newsItem.getId(), newsItem);
		if(newsItem.isGlobal()){
			allGlobalNewsIds.add(newsItem.getId());	
		}
		
	}
	
	public void addVideo(String language,VideoDto videoItem){
		Map<Long, VideoDto> languageVideos = videoCacheByLanguage.get(language);
		if(languageVideos == null){
			languageVideos = new HashMap<>();
			videoCacheByLanguage.put(language, languageVideos);
		}
		languageVideos.put(videoItem.getId(), videoItem);
		if(videoItem.isGlobal()){
			allGlobalVideoIds.add(videoItem.getId());	
		}
		
		
	}
	
	
	public void addBlog(String language,BlogDto blogItem){
		Map<Long, BlogDto> languageBlogs = blogCacheByLanguage.get(language);
		if(languageBlogs == null){
			languageBlogs = new HashMap<>();
			blogCacheByLanguage.put(language, languageBlogs);
		}
		languageBlogs.put(blogItem.getId(), blogItem);
		if(blogItem.isGlobal()){
			allGlobalBlogIds.add(blogItem.getId());	
		}
	}
	public void addPollQuestion(String language,PollQuestionDto pollQuestionDto){
		Map<Long, PollQuestionDto> languagePolls = pollCacheByLanguage.get(language);
		if(languagePolls == null){
			languagePolls = new HashMap<>();
			pollCacheByLanguage.put(language, languagePolls);
		}
		languagePolls.put(pollQuestionDto.getId(), pollQuestionDto);
		if(pollQuestionDto.isGlobal()){
			allGlobalPollIds.add(pollQuestionDto.getId());	
		}
		
		
	}
	
	private List<Long> convertToCache(List<Long> ids){
		List<Long> returnList = new ArrayList<>(ids.size());
		Long cachedId;
		for(Long oneId:ids){
			cachedId = getCachedLong(oneId);
			returnList.add(cachedId);
		}
		return returnList;
	}
	private Long getCachedLong(Long oneId){
		Long cachedId = longCache.get(oneId);
		if(cachedId == null){
			cachedId = oneId;
			longCache.put(oneId, cachedId);
		}
		return cachedId;

	}
	public void addAcNews(Long acId,List<Long> newsIds){
		acNewsDtos.put(acId, convertToCache(newsIds));
	}
	
	public void addAcBlogs(Long acId,List<Long> blogIds){
		acBlogDtos.put(acId, convertToCache(blogIds));
	}
	public void addAcVideos(Long acId,List<Long> videoIds){
		acVideoDtos.put(acId, convertToCache(videoIds));
	}
	public void addAcPolls(Long acId,List<Long> pollIds){
		acPollDtos.put(acId, convertToCache(pollIds));
	}
	
	public void addPcNews(Long pcId,List<Long> newsIds){
		pcNewsDtos.put(pcId, convertToCache(newsIds));
	}
	
	public void addPcBlogs(Long pcId,List<Long> blogIds){
		pcBlogDtos.put(pcId, convertToCache(blogIds));
	}
	public void addPcVideos(Long pcId,List<Long> videoIds){
		pcVideoDtos.put(pcId, convertToCache(videoIds));
	}
	public void addPcPolls(Long pcId,List<Long> pollIds){
		pcPollDtos.put(pcId, convertToCache(pollIds));
	}

	
	public List<NewsDto> getNewsDtos(String language, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber, int pageSize){
		int startItemCount = (pageNumber - 1) * pageSize;
		int endItemCount = startItemCount + pageSize;
		List<NewsDto> newsItems = new ArrayList<>(pageSize);
		
		List<Long> locationNewsIds =  getItemsForAllLocations(language, livingAcId, votingAcId, livingPcId, votingPcId, acNewsDtos, pcNewsDtos, allGlobalNewsIds);
		Map<Long, NewsDto> newsItemsByLang = newsCacheByLanguage.get(language);
		
		
		for(int i=startItemCount;i<endItemCount;i++){
			if(locationNewsIds.size() <= i){
				break;
			}
			newsItems.add(newsItemsByLang.get(locationNewsIds.get(i)));
		}
		return newsItems;
	}
	private List<Long> getNewsDtosForAllLocations(String language, long livingAcId, long votingAcId, long livingPcId, long votingPcId){
		Set<Long> locationNewsIds = new TreeSet<>();
		addNewsDtoOfOneLocation(locationNewsIds, acNewsDtos.get(livingAcId));
		if(livingAcId != votingAcId){
			addNewsDtoOfOneLocation(locationNewsIds, acNewsDtos.get(votingAcId));
		}
		addNewsDtoOfOneLocation(locationNewsIds, pcNewsDtos.get(livingPcId));
		if(livingPcId != votingPcId){
			addNewsDtoOfOneLocation(locationNewsIds, pcNewsDtos.get(votingPcId));
		}
		
		List<Long> returnList = new ArrayList<>();
		if(locationNewsIds.isEmpty()){
			returnList.addAll(allGlobalNewsIds);
		}
		else{
			returnList.addAll(locationNewsIds);
			Collections.reverse(returnList);
		}
		
		return returnList;
	}
	private void addNewsDtoOfOneLocation(Set<Long> locationNewsIds, List<Long> newsIdsToAdd){
		if(newsIdsToAdd != null){
			locationNewsIds.addAll(newsIdsToAdd);
		}
		
	}
	
	public long getTotalNewsDtoPages(String language, long livingAcId, long votingAcId, long livingPcId, long votingPcId,int pageSize){
		List<Long> locationNewsIds = getNewsDtosForAllLocations(language, livingAcId, votingAcId, livingPcId, votingPcId);
		return locationNewsIds.size() / pageSize;
	}
	
	public long getTotalVideoPages(String language, long livingAcId, long votingAcId, long livingPcId, long votingPcId,int pageSize){
		List<Long> locationVideoIds =  getItemsForAllLocations(language, livingAcId, votingAcId, livingPcId, votingPcId, acVideoDtos, pcVideoDtos, allGlobalVideoIds);
		return locationVideoIds.size() / pageSize;
	}

	public long getTotalBlogPages(String language, long livingAcId, long votingAcId, long livingPcId, long votingPcId,int pageSize){
		List<Long> locationBlogIds =  getItemsForAllLocations(language, livingAcId, votingAcId, livingPcId, votingPcId, acBlogDtos, pcBlogDtos, allGlobalBlogIds);
		return locationBlogIds.size() / pageSize;
	}

	public List<VideoDto> getVideoDtos(String language, long livingAcId, long votingAcId, long livingPcId, long votingPcId,int pageNumber, int pageSize){
		int startItemCount = (pageNumber - 1) * pageSize;
		int endItemCount = startItemCount + pageSize;
		List<VideoDto> videoItems = new ArrayList<>(pageSize);
		List<Long> acVideoIds = getItemsForAllLocations(language, livingAcId, votingAcId, livingPcId, votingPcId, acVideoDtos, pcVideoDtos, allGlobalVideoIds);
		if(acVideoIds == null){
			acVideoIds = allGlobalVideoIds;
		}
		Map<Long, VideoDto> videoItemsByLang = videoCacheByLanguage.get(language);
		
		for(int i=startItemCount;i<endItemCount;i++){
			if(acVideoIds.size() <= i){
				break;
			}
			videoItems.add(videoItemsByLang.get(acVideoIds.get(i)));
		}
		return videoItems;
	}
	
	
	private List<Long> getItemsForAllLocations(String language, long livingAcId, long votingAcId, long livingPcId, long votingPcId
			, Map<Long, List<Long>> acItemMap, Map<Long, List<Long>> pcItemMap, List<Long> defaultItemList){
		Set<Long> locationNewsIds = new TreeSet<>();
		addNewsDtoOfOneLocation(locationNewsIds, acItemMap.get(livingAcId));
		if(livingAcId != votingAcId){
			addNewsDtoOfOneLocation(locationNewsIds, acItemMap.get(votingAcId));
		}
		addNewsDtoOfOneLocation(locationNewsIds, pcItemMap.get(livingPcId));
		if(livingPcId != votingPcId){
			addNewsDtoOfOneLocation(locationNewsIds, pcItemMap.get(votingPcId));
		}
		
		List<Long> returnList = new ArrayList<>();
		if(locationNewsIds.isEmpty()){
			returnList.addAll(defaultItemList);
		}
		else{
			returnList.addAll(locationNewsIds);
		}
		Collections.reverse(returnList);
		return returnList;
	}
	
	
	public List<BlogDto> getBlogDtos(String language, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber, int pageSize){
		int startItemCount = (pageNumber - 1) * pageSize;
		int endItemCount = startItemCount + pageSize;
		List<BlogDto> blogItems = new ArrayList<>(pageSize);
		List<Long> acBlogIds = getItemsForAllLocations(language, livingAcId, votingAcId, livingPcId, votingPcId, acBlogDtos, pcBlogDtos, allGlobalBlogIds);
		if(acBlogIds == null){
			acBlogIds = allGlobalBlogIds;
		}
		Map<Long, BlogDto> blogItemsByLang = blogCacheByLanguage.get(language);
		
		for(int i=startItemCount;i<endItemCount;i++){
			if(acBlogIds.size() <= i){
				break;
			}
			blogItems.add(blogItemsByLang.get(acBlogIds.get(i)));
		}
		return blogItems;
	}
	
	public List<PollQuestionDto> getPollQuestionDtos(String language, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber, int pageSize){
		int startItemCount = (pageNumber - 1) * pageSize;
		int endItemCount = startItemCount + pageSize;
		List<PollQuestionDto> pollQuestionItems = new ArrayList<>(pageSize);
		List<Long> acPollQuestionIds = getItemsForAllLocations(language, livingAcId, votingAcId, livingPcId, votingPcId, acPollDtos, pcPollDtos, allGlobalPollIds);
		if(acPollQuestionIds == null){
			acPollQuestionIds = allGlobalPollIds;
		}
		Map<Long, PollQuestionDto> pollQuestionItemsByLang = pollCacheByLanguage.get(language);
		for(int i=startItemCount;i<endItemCount;i++){
			if(acPollQuestionIds.size() <= i){
				break;
			}
			pollQuestionItems.add(pollQuestionItemsByLang.get(acPollQuestionIds.get(i)));
		}
		return pollQuestionItems;
	}
	
	public long getTotalPollQuestionPages(String language, long livingAcId, long votingAcId, long livingPcId, long votingPcId,int pageSize){
		List<Long> locationPollIds =  getItemsForAllLocations(language, livingAcId, votingAcId, livingPcId, votingPcId, acPollDtos, pcPollDtos, allGlobalPollIds);
		return locationPollIds.size() / pageSize;
	}

	public void finishLoading(){
		allGlobalNewsIds = convertToCache(allGlobalNewsIds);
		allGlobalVideoIds = convertToCache(allGlobalVideoIds);
		allGlobalBlogIds = convertToCache(allGlobalBlogIds);
		allGlobalPollIds = convertToCache(allGlobalPollIds);
	}

}
