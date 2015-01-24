package com.next.aap.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

import com.google.gdata.util.common.base.StringUtil;
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
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.dto.VideoDto;
import com.next.aap.web.util.ContentDonwloadUtil;
import com.next.aap.web.util.CookieUtil;

public class AppBaseController extends BaseController{

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

	protected void addUserPcCandidateInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
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
			mv.getModel().put("candidate", candidateDto);
			addCandidateDonationInfo(candidateDto, mv);
		}else{
			CandidateDto candidateDto = candidateCacheImpl.getCandidateByPcId(pcId);
			mv.getModel().put("candidate", candidateDto);
			addCandidateDonationInfo(candidateDto, mv);
		}
		
		
	}

    protected void addUserAcCandidateInModel(HttpServletRequest httpServletRequest, ModelAndView mv) {
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

    }
	protected void addCandidateDonationInfo(CandidateDto candidateDto, ModelAndView mv){
		if(candidateDto != null){
			if(!StringUtil.isEmpty(candidateDto.getLocationCampaignId())){
				String key = CacheKeyService.createLocationCampaignKey(candidateDto.getLocationCampaignId());
                // logger.info("Key = "+ key);
				DonationCampaignInfo donationCampaignInfo = cacheService.getData(key, DonationCampaignInfo.class);
                // logger.info("donationCampaignInfo = "+ donationCampaignInfo);
				mv.getModel().put("donationCampaignInfo", donationCampaignInfo);
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
	protected void addNewsInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
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
		mv.getModel().put("newsItems", newsItems);
		mv.getModel().put("pageNumber", pageNumber);
	}
	protected void addAllEventsInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
		List<EventDto> allEvents = eventCacheImpl.getEvents();
		mv.getModel().put("events", allEvents);
	}
	
	protected void addSingleNewsInModel(HttpServletRequest httpServletRequest, ModelAndView mv, Long newsId){
		NewsDto news = newsItemCacheImpl.getCacheItemById(newsId);
		mv.getModel().put("news", news);
	}
	protected void addSingleBlogInModel(HttpServletRequest httpServletRequest, ModelAndView mv, Long blogId){
		BlogDto blog = blogItemCacheImpl.getCacheItemById(blogId);
		mv.getModel().put("blog", blog);
	}
	protected void addSingleVideoInModel(HttpServletRequest httpServletRequest, ModelAndView mv, Long videoId){
		VideoDto video = videoItemCacheImpl.getCacheItemById(videoId);
		mv.getModel().put("video", video);
	}
	protected void addSinglePollQuestionInModel(HttpServletRequest httpServletRequest, ModelAndView mv, Long pollQuestionId){
		PollQuestionDto pollQuestion = aapDataCacheDbImpl.getPollQuestionDtoById(pollQuestionId);
		mv.getModel().put("pollQuestion", pollQuestion);
	}
	
	protected void addVideosInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
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
		mv.getModel().put("videoItems", videoItems);
		mv.getModel().put("pageNumber", pageNumber);
		
	}
	
	protected void addPollsInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
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
		mv.getModel().put("pollItems", pollItems);
		mv.getModel().put("pageNumber", pageNumber);
		
	}
	
	protected void addBlogsInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
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
		mv.getModel().put("blogItems", blogItems);
		mv.getModel().put("pageNumber", pageNumber);
	}
	
	protected void addGenericValuesInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
		mv.getModel().put("design", design);
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		mv.getModel().put("loggedInUser", loggedInUser);
		//mv.getModel().put("staticDirectory", "https://s3-us-west-2.amazonaws.com/my.aamaadmiparty.org/01prandesign");
		mv.getModel().put("staticDirectory", "https://s3.amazonaws.com/myaap");
		
		mv.getModel().put("contextPath", httpServletRequest.getContextPath());
		LoginAccountDto loginAccountDto = getLoggedInAccountsFromSesion(httpServletRequest);
		mv.getModel().put("loginAccounts", loginAccountDto);
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion(httpServletRequest);
		if(userRolePermissionDto != null){
			mv.getModel().put("admin", userRolePermissionDto.isAdmin());	
		}else{
			mv.getModel().put("admin", false);
		}
		mv.getModel().put("currentUrl", httpServletRequest.getRequestURI());
		
	}
	protected void addErrorInModel(ModelAndView mv, String errorMessage){
		List<String> errorMessages = (List<String>)mv.getModel().get("errorMessages");
		if(errorMessages == null){
			errorMessages = new ArrayList<>();
		}
		errorMessages.add(errorMessage);
		mv.getModel().put("errorMessages", errorMessages);
	}
	protected boolean isValidInput(ModelAndView mv){
		List<String> errorMessages = (List<String>)mv.getModel().get("errorMessages");
		System.out.println("errorMessages="+errorMessages);
		if(errorMessages == null || errorMessages.isEmpty()){
			System.out.println("true");
			return true;
		}
		System.out.println("false");
		return false;
	}
	
	protected void addNriCountriesIntoModel(ModelAndView mv){
		List<CountryDto> nriCountries = locationCacheDbImpl.getAllNriCountries();
		mv.getModel().put("nriCountries", nriCountries);
	}
	protected void addNriCountryRegionsIntoModel(ModelAndView mv, Long countryId){
		List<CountryRegionDto> nriCountryRegions = locationCacheDbImpl.getCountryRegionsOfCountry(countryId);
		mv.getModel().put("nriCountryRegions", nriCountryRegions);
	}
	protected void addNriCountryRegionAreasIntoModel(ModelAndView mv, Long countryRegionId){
		List<CountryRegionAreaDto> nriCountryRegionAreas = locationCacheDbImpl.getCountryRegionAreasOfCountryRegion(countryRegionId);
		mv.getModel().put("nriCountryRegionAreas", nriCountryRegionAreas);
	}
	protected void addIndianStatesIntoModel(ModelAndView mv){
		List<StateDto> states = locationCacheDbImpl.getAllStates();
		mv.getModel().put("states", states);
	}
	protected void addDistrictIntoModel(ModelAndView mv, Long stateId, String variableName){
		List<DistrictDto> districts = locationCacheDbImpl.getAllDistrictOfState(stateId);
		mv.getModel().put(variableName, districts);
	}
	protected void addAcIntoModel(ModelAndView mv, Long districtId, String variableName){
		List<AssemblyConstituencyDto> acs = locationCacheDbImpl.getAllAssemblyConstituenciesOfDistrict(districtId);
		mv.getModel().put(variableName, acs);
	}
	protected void addPcIntoModel(ModelAndView mv, Long stateId, String variableName){
		List<ParliamentConstituencyDto> pcs = locationCacheDbImpl.getAllParliamentConstituenciesOfState(stateId);
		mv.getModel().put(variableName, pcs);
	}
	
	protected PollQuestionDto addPollIntoModel(String pollId, ModelAndView mv){
		PollQuestionDto pollQuestion = null;
		if(NumberUtils.isNumber(pollId)){
			pollQuestion = pollItemCacheImpl.getCacheItemById(Long.parseLong(pollId));
		}else{
			pollQuestion = pollItemCacheImpl.getPollQuestionByUrlId(pollId);
		}
		
		mv.getModel().put("poll", pollQuestion);
		return pollQuestion;
	}
	
	protected PollStatsDto getPollStats(Long pollQuestionId){
		String pollKey = CacheKeyService.createPollVoteKey(pollQuestionId);
		return cacheService.getData(pollKey, PollStatsDto.class);
	}
	protected void addPollChartToModel(Long userId, Long pollQuestionId, ModelAndView mv){
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
		
		mv.getModel().put("chartData", voteMap.toString());
		
		String userAnswer = cacheService.getData(CacheKeyService.createUserPollVoteKey(userId, pollQuestionId), String.class);
		if(userAnswer != null){
			for(PollAnswerDto oneAnswer:pollQuestionDto.getAnswers()){
				if(userAnswer.equals(String.valueOf(oneAnswer.getId()))){
					mv.getModel().put("userAnswer", oneAnswer.getContentWithoutHtml());
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
