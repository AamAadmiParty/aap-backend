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
public class AapDataCacheDbImpl implements AapDataCache{
	
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
	private static final int MAX_POLL_ITEM = 4;
	private static final int MAX_EVENT_ITEM = 10;
	public static final String DEFAULT_LANGUAGE = "en";
	
	private Set<String> allValidLanguages;
	
	@Autowired 
	private AapService aapService;

	@PostConstruct
	public void init(){
		logger.info("loadCacheOnStartup="+loadCacheOnStartup);
		if(loadCacheOnStartup){
			//refreshFullCache();
		}
	}

	/* (non-Javadoc)
	 * @see com.next.aap.web.cache.AapDataCache#refreshFullCache()
	 */
	@Override
	public void refreshFullCache(){
		Long startTime = System.currentTimeMillis();
		logger.info("Start Loading Data Cache");
		
		AapDataHolder localAapDataHolder = new AapDataHolder();

		List<AssemblyConstituencyDto> allAssemblyConstituencyDtos = aapService.getAllAssemblyConstituencies();
		List<ParliamentConstituencyDto> allParliamentConstituencyDtos = aapService.getAllParliamentConstituencies();
		logger.info("allAssemblyConstituencyDtos.size="+allAssemblyConstituencyDtos.size());	
		logger.info("allParliamentConstituencyDtos.size="+allParliamentConstituencyDtos.size());	
		//loadAllNews(localAapDataHolder, allAssemblyConstituencyDtos, allParliamentConstituencyDtos);
		//loadAllBlogs(localAapDataHolder, allAssemblyConstituencyDtos, allParliamentConstituencyDtos);
		//loadAllVideos(localAapDataHolder, allAssemblyConstituencyDtos, allParliamentConstituencyDtos);
		loadAllPolls(localAapDataHolder, allAssemblyConstituencyDtos, allParliamentConstituencyDtos);
		
		this.aapDataHolder = localAapDataHolder;
		cacheLoaded = true;
		Long endTime = System.currentTimeMillis();
		logger.info("Cache Loading finished, total time taken is "+(endTime - startTime) +" ms");
	}
	
	private void loadAllNews(AapDataHolder localAapDataHolder,List<AssemblyConstituencyDto> allAssemblyConstituencyDtos, List<ParliamentConstituencyDto> allParliamentConstituencyDtos){
		List<NewsDto> allNewsDtos = aapService.getAllPublishedNews();
		logger.info("allNewsDtos.size : "+allNewsDtos.size());
		for(NewsDto oneNewsDto:allNewsDtos){
			localAapDataHolder.addNews(DEFAULT_LANGUAGE, oneNewsDto);
		}
		
		
		for(AssemblyConstituencyDto oneAc:allAssemblyConstituencyDtos){
			loadAcNews(oneAc.getId(), localAapDataHolder);
		}
		
		for(ParliamentConstituencyDto onePc:allParliamentConstituencyDtos){
			loadPcNews(onePc.getId(), localAapDataHolder);
		}
	}
	@Override
	public void loadAcNews(long acId){
		loadAcNews(acId, aapDataHolder);
	}
	private void loadAcNews(long acId, AapDataHolder localAapDataHolder){
		List<Long> newsIds = aapService.getAllNewsItemsOfAc(acId);
		localAapDataHolder.addAcNews(acId, newsIds);
	}
	@Override
	public void loadPcNews(long pcId){
		loadPcNews(pcId, aapDataHolder);
	}
	private void loadPcNews(long pcId, AapDataHolder localAapDataHolder){
		List<Long> newsIds = aapService.getAllNewsItemsOfParliamentConstituency(pcId);
		localAapDataHolder.addPcNews(pcId, newsIds);
	}
	@Override
	public void loadStateNews(long stateId){
		List<AssemblyConstituencyDto> allAssemblyConstituencyDtos = aapService.getAllAssemblyConstituenciesOfState(stateId);
		for(AssemblyConstituencyDto oneAssemblyConstituency:allAssemblyConstituencyDtos){
			loadAcNews(oneAssemblyConstituency.getId());
		}
		
		List<ParliamentConstituencyDto> allPcs = aapService.getAllParliamentConstituenciesOfState(stateId);
		for(ParliamentConstituencyDto onePc:allPcs){
			loadPcNews(onePc.getId());
		}
	}
	@Override
	public void loadDistrictNews(long districtId){
		List<AssemblyConstituencyDto> allAssemblyConstituencyDtos = aapService.getAllAssemblyConstituenciesOfDistrict(districtId);
		for(AssemblyConstituencyDto oneAssemblyConstituency:allAssemblyConstituencyDtos){
			loadAcNews(oneAssemblyConstituency.getId());
		}
	}
	
	@Override
	public void loadAcBlogs(long acId){
		loadAcBlogs(acId, aapDataHolder);
	}
	private void loadAcBlogs(long acId, AapDataHolder localAapDataHolder){
		List<Long> newsIds = aapService.getAllBlogItemsOfAc(acId);
		localAapDataHolder.addAcBlogs(acId, newsIds);
	}
	@Override
	public void loadPcBlogs(long pcId){
		loadPcBlogs(pcId, aapDataHolder);
	}
	private void loadPcBlogs(long pcId, AapDataHolder localAapDataHolder){
		List<Long> newsIds = aapService.getBlogItemsOfParliamentConstituency(pcId);
		localAapDataHolder.addPcBlogs(pcId, newsIds);
	}
	@Override
	public void loadStateBlogs(long stateId){
		List<AssemblyConstituencyDto> allAssemblyConstituencyDtos = aapService.getAllAssemblyConstituenciesOfState(stateId);
		for(AssemblyConstituencyDto oneAssemblyConstituency:allAssemblyConstituencyDtos){
			loadAcBlogs(oneAssemblyConstituency.getId());
		}
		
		List<ParliamentConstituencyDto> allPcs = aapService.getAllParliamentConstituenciesOfState(stateId);
		for(ParliamentConstituencyDto onePc:allPcs){
			loadPcBlogs(onePc.getId());
		}
	}
	@Override
	public void loadDistrictBlogs(long districtId){
		List<AssemblyConstituencyDto> allAssemblyConstituencyDtos = aapService.getAllAssemblyConstituenciesOfDistrict(districtId);
		for(AssemblyConstituencyDto oneAssemblyConstituency:allAssemblyConstituencyDtos){
			loadAcBlogs(oneAssemblyConstituency.getId());
		}
	}
	
	
	@Override
	public void loadAcVideos(long acId){
		loadAcVideos(acId, aapDataHolder);
	}
	private void loadAcVideos(long acId, AapDataHolder localAapDataHolder){
		List<Long> newsIds = aapService.getAllVideoItemsOfAc(acId);
		localAapDataHolder.addAcVideos(acId, newsIds);
	}
	@Override
	public void loadPcVideos(long pcId){
		loadPcVideos(pcId, aapDataHolder);
	}
	private void loadPcVideos(long pcId, AapDataHolder localAapDataHolder){
		List<Long> newsIds = aapService.getVideoItemsOfParliamentConstituency(pcId);
		localAapDataHolder.addPcVideos(pcId, newsIds);
	}
	@Override
	public void loadStateVideos(long stateId){
		List<AssemblyConstituencyDto> allAssemblyConstituencyDtos = aapService.getAllAssemblyConstituenciesOfState(stateId);
		for(AssemblyConstituencyDto oneAssemblyConstituency:allAssemblyConstituencyDtos){
			loadAcVideos(oneAssemblyConstituency.getId());
		}
		
		List<ParliamentConstituencyDto> allPcs = aapService.getAllParliamentConstituenciesOfState(stateId);
		for(ParliamentConstituencyDto onePc:allPcs){
			loadPcVideos(onePc.getId());
		}
	}
	@Override
	public void loadDistrictVideos(long districtId){
		List<AssemblyConstituencyDto> allAssemblyConstituencyDtos = aapService.getAllAssemblyConstituenciesOfDistrict(districtId);
		for(AssemblyConstituencyDto oneAssemblyConstituency:allAssemblyConstituencyDtos){
			loadAcVideos(oneAssemblyConstituency.getId());
		}
	}
	
	private void loadAllBlogs(AapDataHolder localAapDataHolder,List<AssemblyConstituencyDto> allAssemblyConstituencyDtos, List<ParliamentConstituencyDto> allParliamentConstituencyDtos){
		List<BlogDto> allBlogDtos = aapService.getAllPublishedBlogs();
		for(BlogDto oneBlogDto:allBlogDtos){
			localAapDataHolder.addBlog(DEFAULT_LANGUAGE, oneBlogDto);
		}
		
		List<Long> blogIds;
		for(AssemblyConstituencyDto oneAc:allAssemblyConstituencyDtos){
			blogIds = aapService.getAllBlogItemsOfAc(oneAc.getId());
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
			blogIds = aapService.getAllVideoItemsOfAc(oneAc.getId());
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
		List<Long> questionIds;
		for(AssemblyConstituencyDto oneAc:allAssemblyConstituencyDtos){
			questionIds = aapService.getAllPollItemsOfAc(oneAc.getId());
			localAapDataHolder.addAcPolls(oneAc.getId(), questionIds);
		}
		
		
		for(ParliamentConstituencyDto onePc:allParliamentConstituencyDtos){
			questionIds = aapService.getPollItemsOfParliamentConstituency(onePc.getId());
			localAapDataHolder.addPcPolls(onePc.getId(), questionIds);
		}
	}
	
	

	/* (non-Javadoc)
	 * @see com.next.aap.web.cache.AapDataCache#getNewsDtos(java.lang.String, long, long, long, long)
	 */
	@Override
	public ItemList<NewsDto> getNewsDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId) {
		return getNewsDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, 1);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.web.cache.AapDataCache#getNewsDtos(java.lang.String, long, long, long, long, int)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.next.aap.web.cache.AapDataCache#getVideoDtos(java.lang.String, long, long, long, long)
	 */
	@Override
	public ItemList<VideoDto> getVideoDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId) {
		return getVideoDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, 1);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.web.cache.AapDataCache#getVideoDtos(java.lang.String, long, long, long, long, int)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.next.aap.web.cache.AapDataCache#getBlogDtos(java.lang.String, long, long, long, long)
	 */
	@Override
	public ItemList<BlogDto> getBlogDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId) {
		return getBlogDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, 1);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.web.cache.AapDataCache#getBlogDtos(java.lang.String, long, long, long, long, int)
	 */
	@Override
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

	
	/* (non-Javadoc)
	 * @see com.next.aap.web.cache.AapDataCache#getPollQuestionDtos(java.lang.String, long, long, long, long)
	 */
	@Override
	public ItemList<PollQuestionDto> getPollQuestionDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId) {
		return getPollQuestionDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, 1);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.web.cache.AapDataCache#getPollQuestionDtos(java.lang.String, long, long, long, long, int)
	 */
	@Override
	public ItemList<PollQuestionDto> getPollQuestionDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber) {
		return getPollQuestionDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber, MAX_POLL_ITEM);
	}


	private ItemList<PollQuestionDto> getPollQuestionDtos(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber, int pageSize) {
		List<PollQuestionDto> pollQuestionItems = aapDataHolder.getPollQuestionDtos(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber, pageSize);
		ItemList<PollQuestionDto> pollQuestionItemList = new ItemList<>(pollQuestionItems);
		pollQuestionItemList.setPageNumber(pageNumber);
		pollQuestionItemList.setPageSize(pageSize);
		pollQuestionItemList.setTotalPages(aapDataHolder.getTotalPollQuestionPages(lang, livingAcId, votingAcId, livingPcId, votingPcId, pageSize));
		return pollQuestionItemList;
	}

	@Override
	public NewsDto getNewsById(String lang, long newsId) {
		return aapDataHolder.getNewsById(lang, newsId);
	}

	@Override
	public NewsDto getNewsById(long newsId) {
		return getNewsById(DEFAULT_LANGUAGE, newsId);
	}

	@Override
	public VideoDto getVideoById(String lang, long videoId) {
		return aapDataHolder.getVideoById(lang, videoId);
	}

	@Override
	public VideoDto getVideoById(long videoId) {
		return getVideoById(DEFAULT_LANGUAGE, videoId);
	}

	@Override
	public BlogDto getBlogById(String lang, long blogId) {
		return aapDataHolder.getBlogById(lang, blogId);
	}

	@Override
	public BlogDto getBlogById(long blogId) {
		return aapDataHolder.getBlogById(DEFAULT_LANGUAGE, blogId);
	}

	@Override
	public PollQuestionDto getPollQuestionById(String lang, long pollQuestionId) {
		return aapDataHolder.getPollQuestionById(lang, pollQuestionId);
	}

	@Override
	public PollQuestionDto getPollQuestionDtoById(long pollQuestionId) {
		return aapDataHolder.getPollQuestionById(DEFAULT_LANGUAGE, pollQuestionId);
	}

}
