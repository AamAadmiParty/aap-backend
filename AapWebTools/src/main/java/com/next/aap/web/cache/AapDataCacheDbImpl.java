package com.next.aap.web.cache;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.next.aap.core.service.AapService;
import com.next.aap.web.ItemList;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PollQuestionDto;
import com.next.aap.web.dto.VideoDto;


@Service
public class AapDataCacheDbImpl{
	
	@Autowired 
	private LocationCacheDbImpl locationCacheDb;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private AapDataHolder aapDataHolder;
	
	private boolean loadCacheOnStartup = true;
	
	private boolean cacheLoaded = false;;
	
	/*
	@Value("${load.cache.on.startup}")
	public void setDatabaseName(boolean loadCacheOnStartup) {
		this.loadCacheOnStartup = loadCacheOnStartup;
	}
	*/
	
	
	private static final int MAX_NEWS_ITEM = 10;
	private static final int MAX_VIDEO_ITEM = 10;
	private static final int MAX_BLOG_ITEM = 10;
	private static final int MAX_EVENT_ITEM = 10;
	private static final String DEFAULT_LANGUAGE = "en";
	
	private Set<String> allValidLanguages;
	
	@Autowired 
	private AapService aapService;

	@PostConstruct
	public void init(){
		System.out.println("loadCacheOnStartup="+loadCacheOnStartup);
		if(loadCacheOnStartup){
			refreshFullCache();
		}
	}

	public void refreshFullCache(){
		Long startTime = System.currentTimeMillis();
		System.out.println("Start Loading Data Cache");
		
		AapDataHolder localAapDataHolder = new AapDataHolder();

		List<AssemblyConstituencyDto> allAssemblyConstituencyDtos = aapService.getAllAssemblyConstituencies();
		List<ParliamentConstituencyDto> allParliamentConstituencyDtos = aapService.getAllParliamentConstituencies();
		
		loadAllNews(localAapDataHolder, allAssemblyConstituencyDtos, allParliamentConstituencyDtos);
		loadAllBlogs(localAapDataHolder, allAssemblyConstituencyDtos, allParliamentConstituencyDtos);
		loadAllVideos(localAapDataHolder, allAssemblyConstituencyDtos, allParliamentConstituencyDtos);
		loadAllPolls(localAapDataHolder, allAssemblyConstituencyDtos, allParliamentConstituencyDtos);
		
		this.aapDataHolder = localAapDataHolder;
		cacheLoaded = true;
		Long endTime = System.currentTimeMillis();
		System.out.println("Cache Loading finished, total time taken is "+(endTime - startTime) +" ms");
	}
	
	private void loadAllNews(AapDataHolder localAapDataHolder,List<AssemblyConstituencyDto> allAssemblyConstituencyDtos, List<ParliamentConstituencyDto> allParliamentConstituencyDtos){
		List<NewsDto> allNewsDtos = aapService.getAllPublishedNews();
		for(NewsDto oneNewsDto:allNewsDtos){
			localAapDataHolder.addNews(DEFAULT_LANGUAGE, oneNewsDto);
		}
		
		List<Long> newsIds;
		for(AssemblyConstituencyDto oneAc:allAssemblyConstituencyDtos){
			newsIds = aapService.getNewsItemsOfAc(oneAc.getId());
			localAapDataHolder.addAcNews(oneAc.getId(), newsIds);
		}
		
		for(ParliamentConstituencyDto onePc:allParliamentConstituencyDtos){
			newsIds = aapService.getNewsItemsOfParliamentConstituency(onePc.getId());
			localAapDataHolder.addPcNews(onePc.getId(), newsIds);
		}
	}
	
	private void loadAllBlogs(AapDataHolder localAapDataHolder,List<AssemblyConstituencyDto> allAssemblyConstituencyDtos, List<ParliamentConstituencyDto> allParliamentConstituencyDtos){
		List<BlogDto> allBlogDtos = aapService.getAllPublishedBlogs();
		for(BlogDto oneBlogDto:allBlogDtos){
			localAapDataHolder.addBlog(DEFAULT_LANGUAGE, oneBlogDto);
		}
		
		List<Long> blogIds;
		for(AssemblyConstituencyDto oneAc:allAssemblyConstituencyDtos){
			blogIds = aapService.getBlogItemsOfAc(oneAc.getId());
			localAapDataHolder.addAcBlogs(oneAc.getId(), blogIds);
		}
		
		
		for(ParliamentConstituencyDto onePc:allParliamentConstituencyDtos){
			blogIds = aapService.getBlogItemsOfParliamentConstituency(onePc.getId());
			localAapDataHolder.addPcBlogs(onePc.getId(), blogIds);
		}
	}

	private void loadAllVideos(AapDataHolder localAapDataHolder,List<AssemblyConstituencyDto> allAssemblyConstituencyDtos, List<ParliamentConstituencyDto> allParliamentConstituencyDtos){
		List<VideoDto> allVideoDtos = aapService.getAllPublishedVideos();
		for(VideoDto oneVideoDto:allVideoDtos){
			localAapDataHolder.addVideo(DEFAULT_LANGUAGE, oneVideoDto);
		}
		
		List<Long> blogIds;
		for(AssemblyConstituencyDto oneAc:allAssemblyConstituencyDtos){
			blogIds = aapService.getVideoItemsOfAc(oneAc.getId());
			localAapDataHolder.addAcVideos(oneAc.getId(), blogIds);
		}
		
		
		for(ParliamentConstituencyDto onePc:allParliamentConstituencyDtos){
			blogIds = aapService.getVideoItemsOfParliamentConstituency(onePc.getId());
			localAapDataHolder.addPcVideos(onePc.getId(), blogIds);
		}
	}
	
	private void loadAllPolls(AapDataHolder localAapDataHolder,List<AssemblyConstituencyDto> allAssemblyConstituencyDtos, List<ParliamentConstituencyDto> allParliamentConstituencyDtos){
		List<PollQuestionDto> allPollQuestions = aapService.getAllPublishedPolls();
		for(PollQuestionDto onePollDto:allPollQuestions){
			localAapDataHolder.addPollQuestion(DEFAULT_LANGUAGE, onePollDto);
		}
		List<Long> blogIds;
		for(AssemblyConstituencyDto oneAc:allAssemblyConstituencyDtos){
			blogIds = aapService.getPollItemsOfAc(oneAc.getId());
			localAapDataHolder.addAcVideos(oneAc.getId(), blogIds);
		}
		
		
		for(ParliamentConstituencyDto onePc:allParliamentConstituencyDtos){
			blogIds = aapService.getVideoItemsOfParliamentConstituency(onePc.getId());
			localAapDataHolder.addPcVideos(onePc.getId(), blogIds);
		}
	}
	
	

	public ItemList<NewsDto> getNewsDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId) {
		return getNewsDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, 1);
	}
	public ItemList<NewsDto> getNewsDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber) {
		return getNewsDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber, MAX_NEWS_ITEM);
	}
	
	private ItemList<NewsDto> getNewsDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber, int pageSize) {
		List<NewsDto> newsItems = aapDataHolder.getNewsDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber, pageSize);
		ItemList<NewsDto> newsItemList = new ItemList<>(newsItems);
		newsItemList.setPageNumber(pageNumber);
		newsItemList.setPageSize(pageSize);
		newsItemList.setTotalPages(aapDataHolder.getTotalNewsDtoPages(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageSize));
		return newsItemList;
	}

	public ItemList<VideoDto> getVideoDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId) {
		return getVideoDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, 1);
	}
	public ItemList<VideoDto> getVideoDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber) {
		return getVideoDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber, MAX_VIDEO_ITEM);
	}

	private ItemList<VideoDto> getVideoDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber, int pageSize) {
		List<VideoDto> videoItems = aapDataHolder.getVideoDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber, pageSize);
		ItemList<VideoDto> newsItemList = new ItemList<>(videoItems);
		newsItemList.setPageNumber(pageNumber);
		newsItemList.setPageSize(pageSize);
		newsItemList.setTotalPages(aapDataHolder.getTotalVideoPages(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageSize));
		return newsItemList;
	}

	public ItemList<BlogDto> getBlogDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId) {
		return getBlogDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, 1);
	}
	public ItemList<BlogDto> getBlogDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber) {
		return getBlogDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber, MAX_BLOG_ITEM);
	}


	private ItemList<BlogDto> getBlogDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber, int pageSize) {
		List<BlogDto> blogItems = aapDataHolder.getBlogDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber, pageSize);
		ItemList<BlogDto> blogItemList = new ItemList<>(blogItems);
		blogItemList.setPageNumber(pageNumber);
		blogItemList.setPageSize(pageSize);
		blogItemList.setTotalPages(aapDataHolder.getTotalBlogPages(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageSize));
		return blogItemList;
	}

}
