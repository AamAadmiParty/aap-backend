package com.next.aap.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

import com.google.gdata.util.common.base.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.next.aap.cache.CacheKeyService;
import com.next.aap.cache.CacheService;
import com.next.aap.cache.beans.DonationCampaignInfo;
import com.next.aap.core.service.AapService;
import com.next.aap.web.ItemList;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.cache.AapDataCacheDbImpl;
import com.next.aap.web.cache.BlogItemCacheImpl;
import com.next.aap.web.cache.CandidateCacheImpl;
import com.next.aap.web.cache.EventCacheImpl;
import com.next.aap.web.cache.LocationCacheDbImpl;
import com.next.aap.web.cache.NewsItemCacheImpl;
import com.next.aap.web.cache.PollItemCacheImpl;
import com.next.aap.web.cache.TemplateCache;
import com.next.aap.web.cache.VideoItemCacheImpl;
import com.next.aap.web.cache.dto.PollStatsDto;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.CandidateDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.EventDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PollAnswerDto;
import com.next.aap.web.dto.PollQuestionDto;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.TemplateUrlDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.dto.VideoDto;
import com.next.aap.web.util.ContentDonwloadUtil;
import com.next.aap.web.util.CookieUtil;

public class AppBaseJsonController extends BaseController{

	@Autowired
	protected AapService aapService;
	@Autowired
	protected AapDataCache aapDataCacheDbImpl;
	@Value("${design:stylenew}")
	protected String design;
	@Autowired
	protected ContentDonwloadUtil contentDonwloadUtil;
	@Autowired
	protected LocationCacheDbImpl locationCacheDbImpl;
	@Autowired
	protected NewsItemCacheImpl newsItemCacheImpl;
	@Autowired
	protected BlogItemCacheImpl blogItemCacheImpl;
	@Autowired
	protected VideoItemCacheImpl videoItemCacheImpl;
	@Autowired
	protected PollItemCacheImpl pollItemCacheImpl;
	@Autowired
	protected EventCacheImpl eventCacheImpl;
	@Autowired
	protected CacheService cacheService;
	@Autowired
	protected CandidateCacheImpl candidateCacheImpl;

    @Autowired
    protected TemplateCache templateCache;

    private Gson gson = new Gson();

    protected void addUserPcCandidateInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject) {
		Long pcId = getLongPramater(httpServletRequest, "pcId", 0);
		if(pcId <= 0){
			UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
			long livingPcId = 0;
			long votingPcId = 0;
			if(loggedInUser != null){
				//get user's location candidate
				if(loggedInUser.getParliamentConstituencyLivingId() != null){
					livingPcId = loggedInUser.getParliamentConstituencyLivingId();
				}
				if(loggedInUser.getParliamentConstituencyVotingId() != null){
					votingPcId = loggedInUser.getParliamentConstituencyVotingId();	
				}
			}else{
				livingPcId = CookieUtil.getUserLivingPcIdCookie(httpServletRequest);
				votingPcId = CookieUtil.getUserVotingPcIdCookie(httpServletRequest);
			}
			CandidateDto candidateDto = candidateCacheImpl.getCandidateByPcId(livingPcId, votingPcId);
            contextJsonObject.add("candidate", gson.toJsonTree(candidateDto));
            addCandidateDonationInfo(candidateDto, contextJsonObject);
		}else{
			CandidateDto candidateDto = candidateCacheImpl.getCandidateByPcId(pcId);
            contextJsonObject.add("candidate", gson.toJsonTree(candidateDto));
            addCandidateDonationInfo(candidateDto, contextJsonObject);
		}
		
		
	}

    protected void addUserAcCandidateInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject) {
        /*
        Long acId = getLongPramater(httpServletRequest, "acId", 0);
        if (acId <= 0) {
            UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
            long livingAcId = 0;
            long votingAcId = 0;
            if (loggedInUser != null) {
                // get user's location candidate
                if (loggedInUser.getAssemblyConstituencyLivingId() != null) {
                    livingAcId = loggedInUser.getAssemblyConstituencyLivingId();
                }
                if (loggedInUser.getAssemblyConstituencyVotingId() != null) {
                    votingAcId = loggedInUser.getAssemblyConstituencyVotingId();
                }
            } else {
                livingAcId = CookieUtil.getUserLivingAcIdCookie(httpServletRequest);
                votingAcId = CookieUtil.getUserVotingAcIdCookie(httpServletRequest);
            }
            CandidateDto candidateDto = candidateCacheImpl.getCandidateByAcId(livingAcId, votingAcId);
            mv.getModel().put("candidate", candidateDto);
            addCandidateDonationInfo(candidateDto, mv);
        } else {
            CandidateDto candidateDto = candidateCacheImpl.getCandidateByAcId(acId);
            mv.getModel().put("candidate", candidateDto);
            addCandidateDonationInfo(candidateDto, mv);
        }
        */

    }

    protected void addCandidateDonationInfo(CandidateDto candidateDto, JsonObject contextJsonObject) {
		if(candidateDto != null){
			if(!StringUtil.isEmpty(candidateDto.getLocationCampaignId())){
				String key = CacheKeyService.createLocationCampaignKey(candidateDto.getLocationCampaignId());
                // logger.info("Key = "+ key);
				DonationCampaignInfo donationCampaignInfo = cacheService.getData(key, DonationCampaignInfo.class);
                // logger.info("donationCampaignInfo = "+ donationCampaignInfo);
                contextJsonObject.add("donationCampaignInfo", gson.toJsonTree(donationCampaignInfo));
			}
		}
	}
	protected String getUserPollQuestion(Long userId, Long pollQuestionId){
			String key = CacheKeyService.createUserPollVoteKey(userId, pollQuestionId);
			logger.info("Key = "+ key);
			String answerId = cacheService.getData(key, String.class);
			logger.info("answerId = "+ answerId);
			return answerId;
	}
	protected DonationCampaignInfo getCandidateDonationInfo(CandidateDto candidateDto){
		if(candidateDto != null){
			if(!StringUtil.isEmpty(candidateDto.getLocationCampaignId())){
				String key = CacheKeyService.createLocationCampaignKey(candidateDto.getLocationCampaignId());
				DonationCampaignInfo donationCampaignInfo = cacheService.getData(key, DonationCampaignInfo.class);
				return donationCampaignInfo;
			}
		}
		return null;
	}

    protected void addNewsInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject) {
		long livingAcId = 0;
		long votingAcId = 0;
		long livingPcId = 0;
		long votingPcId = 0;
		long nriCountryId = 0;
		long nriCountryRegionId = 0;
		long nriCountryRegionAreaId = 0;
		
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		if(loggedInUser != null){
			if(loggedInUser.getAssemblyConstituencyLivingId() != null){
				livingAcId = loggedInUser.getAssemblyConstituencyLivingId();
			}
			if(loggedInUser.getAssemblyConstituencyVotingId() != null){
				votingAcId = loggedInUser.getAssemblyConstituencyVotingId();
			}
			if(loggedInUser.getParliamentConstituencyLivingId() != null){
				livingPcId = loggedInUser.getParliamentConstituencyLivingId();
			}
			if(loggedInUser.getParliamentConstituencyVotingId() != null){
				votingPcId = loggedInUser.getParliamentConstituencyVotingId();
			}
			if(loggedInUser.getNriCountryId() != null){
				nriCountryId = loggedInUser.getNriCountryId();
			}
			if(loggedInUser.getNriCountryRegionId() != null){
				nriCountryRegionId = loggedInUser.getNriCountryRegionId();
			}
			if(loggedInUser.getNriCountryRegionAreaId() != null){
				nriCountryRegionAreaId = loggedInUser.getNriCountryRegionAreaId();
			}
		}else{
			//get it from Cookies
			livingAcId = CookieUtil.getUserLivingAcIdCookie(httpServletRequest);
			livingPcId = CookieUtil.getUserLivingPcIdCookie(httpServletRequest);
			votingAcId = CookieUtil.getUserVotingAcIdCookie(httpServletRequest);
			votingPcId = CookieUtil.getUserVotingPcIdCookie(httpServletRequest);
			nriCountryId = CookieUtil.getUserNriCountryIdCookie(httpServletRequest);
			nriCountryRegionId = CookieUtil.getUserNriCountryRegionIdCookie(httpServletRequest);
			nriCountryRegionAreaId = CookieUtil.getUserNriCountryRegionAreaIdCookie(httpServletRequest);
		}
		int pageNumber = getIntPramater(httpServletRequest, PARAM_PAGE_NUMBER, 1);
		ItemList<NewsDto> newsItems = newsItemCacheImpl.getItemsFromCache(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, 
				votingPcId, nriCountryId, nriCountryRegionId,  pageNumber);
		contextJsonObject.add("newsItems", gson.toJsonTree(newsItems));
        contextJsonObject.addProperty("pageNumber", pageNumber);
	}

    protected void addAllEventsInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject) {
		List<EventDto> allEvents = eventCacheImpl.getEvents();
        contextJsonObject.add("events", gson.toJsonTree(allEvents));
	}
	
    protected void addSingleNewsInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject, Long newsId){
		NewsDto news = newsItemCacheImpl.getCacheItemById(newsId);
        contextJsonObject.add("news", gson.toJsonTree(news));
	}

    protected void addSingleBlogInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject, Long blogId) {
		BlogDto blog = blogItemCacheImpl.getCacheItemById(blogId);
        contextJsonObject.add("blog", gson.toJsonTree(blog));
	}

    protected void addSingleVideoInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject, Long videoId) {
		VideoDto video = videoItemCacheImpl.getCacheItemById(videoId);
        contextJsonObject.add("video", gson.toJsonTree(video));
	}

    protected void addSinglePollQuestionInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject, Long pollQuestionId) {
		PollQuestionDto pollQuestion = aapDataCacheDbImpl.getPollQuestionDtoById(pollQuestionId);
        contextJsonObject.add("pollQuestion", gson.toJsonTree(pollQuestion));
	}
	
    protected void addVideosInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject) {
		long livingAcId = 0;
		long votingAcId = 0;
		long livingPcId = 0;
		long votingPcId = 0;
		long nriCountryId = 0;
		long nriCountryRegionId = 0;
		long nriCountryRegionAreaId = 0;
		
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		if(loggedInUser != null){
			if(loggedInUser.getAssemblyConstituencyLivingId() != null){
				livingAcId = loggedInUser.getAssemblyConstituencyLivingId();
			}
			if(loggedInUser.getAssemblyConstituencyVotingId() != null){
				votingAcId = loggedInUser.getAssemblyConstituencyVotingId();
			}
			if(loggedInUser.getParliamentConstituencyLivingId() != null){
				livingPcId = loggedInUser.getParliamentConstituencyLivingId();
			}
			if(loggedInUser.getParliamentConstituencyVotingId() != null){
				votingPcId = loggedInUser.getParliamentConstituencyVotingId();
			}
			if(loggedInUser.getNriCountryId() != null){
				nriCountryId = loggedInUser.getNriCountryId();
			}
			if(loggedInUser.getNriCountryRegionId() != null){
				nriCountryRegionId = loggedInUser.getNriCountryRegionId();
			}
			if(loggedInUser.getNriCountryRegionAreaId() != null){
				nriCountryRegionAreaId = loggedInUser.getNriCountryRegionAreaId();
			}
		}else{
			//get it from Cookies
			livingAcId = CookieUtil.getUserLivingAcIdCookie(httpServletRequest);
			livingPcId = CookieUtil.getUserLivingPcIdCookie(httpServletRequest);
			votingAcId = CookieUtil.getUserVotingAcIdCookie(httpServletRequest);
			votingPcId = CookieUtil.getUserVotingPcIdCookie(httpServletRequest);
			nriCountryId = CookieUtil.getUserNriCountryIdCookie(httpServletRequest);
			nriCountryRegionId = CookieUtil.getUserNriCountryRegionIdCookie(httpServletRequest);
			nriCountryRegionAreaId = CookieUtil.getUserNriCountryRegionAreaIdCookie(httpServletRequest);
		}
		int pageNumber = getIntPramater(httpServletRequest, PARAM_PAGE_NUMBER, 1);
		ItemList<VideoDto> videoItems = videoItemCacheImpl.getItemsFromCache(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId, 
				nriCountryId, nriCountryRegionId, pageNumber);
        contextJsonObject.add("videoItems", gson.toJsonTree(videoItems));
        contextJsonObject.addProperty("pageNumber", pageNumber);
		
	}
	
    protected void addPollsInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject) {
		long livingAcId = 0;
		long votingAcId = 0;
		long livingPcId = 0;
		long votingPcId = 0;
		long nriCountryId = 0;
		long nriCountryRegionId = 0;
		long nriCountryRegionAreaId = 0;
		
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		if(loggedInUser != null){
			if(loggedInUser.getAssemblyConstituencyLivingId() != null){
				livingAcId = loggedInUser.getAssemblyConstituencyLivingId();
			}
			if(loggedInUser.getAssemblyConstituencyVotingId() != null){
				votingAcId = loggedInUser.getAssemblyConstituencyVotingId();
			}
			if(loggedInUser.getParliamentConstituencyLivingId() != null){
				livingPcId = loggedInUser.getParliamentConstituencyLivingId();
			}
			if(loggedInUser.getParliamentConstituencyVotingId() != null){
				votingPcId = loggedInUser.getParliamentConstituencyVotingId();
			}
			if(loggedInUser.getNriCountryId() != null){
				nriCountryId = loggedInUser.getNriCountryId();
			}
			if(loggedInUser.getNriCountryRegionId() != null){
				nriCountryRegionId = loggedInUser.getNriCountryRegionId();
			}
			if(loggedInUser.getNriCountryRegionAreaId() != null){
				nriCountryRegionAreaId = loggedInUser.getNriCountryRegionAreaId();
			}
		}else{
			//get it from Cookies
			livingAcId = CookieUtil.getUserLivingAcIdCookie(httpServletRequest);
			livingPcId = CookieUtil.getUserLivingPcIdCookie(httpServletRequest);
			votingAcId = CookieUtil.getUserVotingAcIdCookie(httpServletRequest);
			votingPcId = CookieUtil.getUserVotingPcIdCookie(httpServletRequest);
			nriCountryId = CookieUtil.getUserNriCountryIdCookie(httpServletRequest);
			nriCountryRegionId = CookieUtil.getUserNriCountryRegionIdCookie(httpServletRequest);
			nriCountryRegionAreaId = CookieUtil.getUserNriCountryRegionAreaIdCookie(httpServletRequest);
		}
		int pageNumber = getIntPramater(httpServletRequest, PARAM_PAGE_NUMBER, 1);
		ItemList<PollQuestionDto> pollItems = pollItemCacheImpl.getItemsFromCache(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId, 
				nriCountryId, nriCountryRegionId, pageNumber);
		
        contextJsonObject.add("pollItems", gson.toJsonTree(pollItems));
        contextJsonObject.addProperty("pageNumber", pageNumber);

	}
	
    protected void addBlogsInModel(HttpServletRequest httpServletRequest, JsonObject contextJsonObject) {
		long livingAcId = 0;
		long votingAcId = 0;
		long livingPcId = 0;
		long votingPcId = 0;
		long nriCountryId = 0;
		long nriCountryRegionId = 0;
		long nriCountryRegionAreaId = 0;
		
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		if(loggedInUser != null){
			if(loggedInUser.getAssemblyConstituencyLivingId() != null){
				livingAcId = loggedInUser.getAssemblyConstituencyLivingId();
			}
			if(loggedInUser.getAssemblyConstituencyVotingId() != null){
				votingAcId = loggedInUser.getAssemblyConstituencyVotingId();
			}
			if(loggedInUser.getParliamentConstituencyLivingId() != null){
				livingPcId = loggedInUser.getParliamentConstituencyLivingId();
			}
			if(loggedInUser.getParliamentConstituencyVotingId() != null){
				votingPcId = loggedInUser.getParliamentConstituencyVotingId();
			}
			if(loggedInUser.getNriCountryId() != null){
				nriCountryId = loggedInUser.getNriCountryId();
			}
			if(loggedInUser.getNriCountryRegionId() != null){
				nriCountryRegionId = loggedInUser.getNriCountryRegionId();
			}
			if(loggedInUser.getNriCountryRegionAreaId() != null){
				nriCountryRegionAreaId = loggedInUser.getNriCountryRegionAreaId();
			}
		}else{
			//get it from Cookies
			livingAcId = CookieUtil.getUserLivingAcIdCookie(httpServletRequest);
			livingPcId = CookieUtil.getUserLivingPcIdCookie(httpServletRequest);
			votingAcId = CookieUtil.getUserVotingAcIdCookie(httpServletRequest);
			votingPcId = CookieUtil.getUserVotingPcIdCookie(httpServletRequest);
			nriCountryId = CookieUtil.getUserNriCountryIdCookie(httpServletRequest);
			nriCountryRegionId = CookieUtil.getUserNriCountryRegionIdCookie(httpServletRequest);
			nriCountryRegionAreaId = CookieUtil.getUserNriCountryRegionAreaIdCookie(httpServletRequest);
		}
		int pageNumber = getIntPramater(httpServletRequest, PARAM_PAGE_NUMBER, 1);
		ItemList<BlogDto> blogItems = blogItemCacheImpl.getItemsFromCache(BlogItemCacheImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId, 
				nriCountryId, nriCountryRegionId, pageNumber);
        contextJsonObject.add("blogItems", gson.toJsonTree(blogItems));
        contextJsonObject.addProperty("pageNumber", pageNumber);

	}

    protected void addTemplateInModel(HttpServletRequest httpServletRequest, ModelAndView mv, String url) {
        StateDto stateDto = locationCacheDbImpl.getStatesByDomain(httpServletRequest.getServerName());
        Long stateId = 0L;
        if (stateDto != null) {
            stateId = stateDto.getId();
        }
        TemplateUrlDto templateUrlDto = templateCache.getStateTemplate(stateId, url);
        System.out.println("templateUrlDto : " + templateUrlDto);
        if (templateUrlDto != null) {
            if ("1".equals(httpServletRequest.getParameter("draft"))) {
                mv.getModel().put("template", templateUrlDto.getDraftContent());
            } else {
                mv.getModel().put("template", templateUrlDto.getPublishedContent());
            }
        }

    }
    protected void addGenericValuesInModel(HttpServletRequest httpServletRequest, ModelAndView mv, JsonObject contextJsonObject) {
        contextJsonObject.addProperty("design", design);
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
        contextJsonObject.add("loggedInUser", gson.toJsonTree(loggedInUser));
		//mv.getModel().put("staticDirectory", "https://s3-us-west-2.amazonaws.com/my.aamaadmiparty.org/01prandesign");
        contextJsonObject.addProperty("staticDirectory", staticDirectory);
        mv.getModel().put("staticDirectory", staticDirectory);
        contextJsonObject.addProperty("contextPath", httpServletRequest.getContextPath());
        mv.getModel().put("contextPath", httpServletRequest.getContextPath());

		LoginAccountDto loginAccountDto = getLoggedInAccountsFromSesion(httpServletRequest);
        contextJsonObject.add("loginAccounts", gson.toJsonTree(loginAccountDto));
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion(httpServletRequest);
		if(userRolePermissionDto != null){
            contextJsonObject.addProperty("admin", userRolePermissionDto.isAdmin());
		}else{
            contextJsonObject.addProperty("admin", false);
		}
        contextJsonObject.addProperty("currentUrl", httpServletRequest.getRequestURL().toString());
        mv.getModel().put("currentUrl", httpServletRequest.getRequestURL().toString());
        contextJsonObject.add("states", gson.toJsonTree(locationCacheDbImpl.getAllStates()));
		
	}

    protected void addErrorInModel(JsonObject contextJsonObject, String errorMessage) {
        /*
        JsonArray errorMessageJsoanArray = null;
        if (contextJsonObject.get("errorMessages") != null) {
            errorMessageJsoanArray = contextJsonObject.get("errorMessages").getAsJsonArray();
        }
        if (errorMessageJsoanArray == null) {
            errorMessageJsoanArray = new JsonArray();
        }
        errorMessageJsoanArray.add(errorMessage);
		List<String> errorMessages = (List<String>)mv.getModel().get("errorMessages");
		if(errorMessages == null){
			errorMessages = new ArrayList<>();
		}
		errorMessages.add(errorMessage);
		mv.getModel().put("errorMessages", errorMessages);
		*/
        contextJsonObject.addProperty("errorMessage", errorMessage);
	}

    protected boolean isValidInput(JsonObject contextJsonObject) {
        return contextJsonObject.get("errorMessage") == null;
	}
	
    protected void addNriCountriesIntoModel(JsonObject contextJsonObject) {
		List<CountryDto> nriCountries = locationCacheDbImpl.getAllNriCountries();
        contextJsonObject.add("nriCountries", gson.toJsonTree(nriCountries));
	}

    protected void addNriCountryRegionsIntoModel(JsonObject contextJsonObject, Long countryId) {
		List<CountryRegionDto> nriCountryRegions = locationCacheDbImpl.getCountryRegionsOfCountry(countryId);
        contextJsonObject.add("nriCountryRegions", gson.toJsonTree(nriCountryRegions));
	}

    protected void addNriCountryRegionAreasIntoModel(JsonObject contextJsonObject, Long countryRegionId) {
		List<CountryRegionAreaDto> nriCountryRegionAreas = locationCacheDbImpl.getCountryRegionAreasOfCountryRegion(countryRegionId);
        contextJsonObject.add("nriCountryRegionAreas", gson.toJsonTree(nriCountryRegionAreas));
	}

    protected void addIndianStatesIntoModel(JsonObject contextJsonObject) {
		List<StateDto> states = locationCacheDbImpl.getAllStates();
        contextJsonObject.add("states", gson.toJsonTree(states));
	}

    protected void addDistrictIntoModel(JsonObject contextJsonObject, Long stateId, String variableName) {
		List<DistrictDto> districts = locationCacheDbImpl.getAllDistrictOfState(stateId);
        contextJsonObject.add(variableName, gson.toJsonTree(districts));
	}

    protected void addAcIntoModel(JsonObject contextJsonObject, Long districtId, String variableName) {
		List<AssemblyConstituencyDto> acs = locationCacheDbImpl.getAllAssemblyConstituenciesOfDistrict(districtId);
        contextJsonObject.add(variableName, gson.toJsonTree(acs));
	}

    protected void addPcIntoModel(JsonObject contextJsonObject, Long stateId, String variableName) {
		List<ParliamentConstituencyDto> pcs = locationCacheDbImpl.getAllParliamentConstituenciesOfState(stateId);
        contextJsonObject.add(variableName, gson.toJsonTree(pcs));
	}
	
    protected PollQuestionDto addPollIntoModel(String pollId, JsonObject contextJsonObject) {
		PollQuestionDto pollQuestion = null;
		if(NumberUtils.isNumber(pollId)){
			pollQuestion = pollItemCacheImpl.getCacheItemById(Long.parseLong(pollId));
		}else{
			pollQuestion = pollItemCacheImpl.getPollQuestionByUrlId(pollId);
		}
        contextJsonObject.add("poll", gson.toJsonTree(pollQuestion));
		return pollQuestion;
	}
	
	protected PollStatsDto getPollStats(Long pollQuestionId){
		String pollKey = CacheKeyService.createPollVoteKey(pollQuestionId);
		return cacheService.getData(pollKey, PollStatsDto.class);
	}

    protected void addPollChartToModel(Long userId, Long pollQuestionId, JsonObject contextJsonObject) {
		PollStatsDto pollStats = getPollStats(pollQuestionId);
		if(pollStats == null){
			pollStats = new PollStatsDto();
		}
		PollQuestionDto pollQuestionDto = pollItemCacheImpl.getCacheItemById(pollQuestionId);
		List<String> voteMap = new ArrayList<>();
		if(pollQuestionDto != null){
			
			voteMap.add(createEntry("Answer", "'Total Votes'"));
			for(PollAnswerDto oneAnswer:pollQuestionDto.getAnswers()){
				Long voteCount = pollStats.getAnswerCounts(oneAnswer.getId());
				if(voteCount == null){
					voteCount = 0L;
				}
				voteMap.add(createEntry(oneAnswer.getContent(), ""+voteCount));
			}
		}
        contextJsonObject.addProperty("chartData", voteMap.toString());
		
		String userAnswer = cacheService.getData(CacheKeyService.createUserPollVoteKey(userId, pollQuestionId), String.class);
		if(userAnswer != null){
			for(PollAnswerDto oneAnswer:pollQuestionDto.getAnswers()){
				if(userAnswer.equals(String.valueOf(oneAnswer.getId()))){
                    contextJsonObject.addProperty("userAnswer", oneAnswer.getContentWithoutHtml());
				}
			}
		}
	}
	private static String createEntry(String key, String value){
		return "['"+key+"', "+value+"]";
	}
	public static void main(String args[]){
		List<String> voteMap = new ArrayList<>();
		voteMap.add(createEntry("Answer", "'Total Votes'"));
		voteMap.add(createEntry("First Answer", ""+10));
		voteMap.add(createEntry("Second Answer", ""+20));
		voteMap.add(createEntry("Third Answer", ""+30));
		
		System.out.println(voteMap.toString());
	}
	
}
