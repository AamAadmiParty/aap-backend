package com.next.aap.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.core.service.AapService;
import com.next.aap.web.ItemList;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.cache.AapDataCacheDbImpl;
import com.next.aap.web.cache.LocationCacheDbImpl;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
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

	protected void addNewsInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
		long livingAcId = 0;
		long votingAcId = 0;
		long livingPcId = 0;
		long votingPcId = 0;
		
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
		}else{
			//get it from Cookies
			livingAcId = CookieUtil.getUserLivingAcIdCookie(httpServletRequest);
			livingPcId = CookieUtil.getUserLivingPcIdCookie(httpServletRequest);
			votingAcId = CookieUtil.getUserVotingAcIdCookie(httpServletRequest);
			votingPcId = CookieUtil.getUserVotingPcIdCookie(httpServletRequest);
		}
		int pageNumber = getIntPramater(httpServletRequest, PARAM_PAGE_NUMBER, 1);
		ItemList<NewsDto> newsItems = aapDataCacheDbImpl.getNewsDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber);
		mv.getModel().put("newsItems", newsItems);
		mv.getModel().put("pageNumber", pageNumber);
	}
	
	protected void addSingleNewsInModel(HttpServletRequest httpServletRequest, ModelAndView mv, Long newsId){
		NewsDto news = aapDataCacheDbImpl.getNewsById(newsId);
		mv.getModel().put("news", news);
	}
	protected void addSingleBlogInModel(HttpServletRequest httpServletRequest, ModelAndView mv, Long blogId){
		BlogDto blog = aapDataCacheDbImpl.getBlogById(blogId);
		mv.getModel().put("blog", blog);
	}
	protected void addSingleVideoInModel(HttpServletRequest httpServletRequest, ModelAndView mv, Long videoId){
		VideoDto video = aapDataCacheDbImpl.getVideoById(videoId);
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
		}else{
			//get it from Cookies
			livingAcId = CookieUtil.getUserLivingAcIdCookie(httpServletRequest);
			livingPcId = CookieUtil.getUserLivingPcIdCookie(httpServletRequest);
			votingAcId = CookieUtil.getUserVotingAcIdCookie(httpServletRequest);
			votingPcId = CookieUtil.getUserVotingPcIdCookie(httpServletRequest);
		}
		int pageNumber = getIntPramater(httpServletRequest, PARAM_PAGE_NUMBER, 1);
		ItemList<VideoDto> videoItems = aapDataCacheDbImpl.getVideoDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber);
		mv.getModel().put("videoItems", videoItems);
		mv.getModel().put("pageNumber", pageNumber);
		
	}
	
	protected void addBlogsInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
		long livingAcId = 0;
		long votingAcId = 0;
		long livingPcId = 0;
		long votingPcId = 0;
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
		}else{
			//get it from Cookies
			livingAcId = CookieUtil.getUserLivingAcIdCookie(httpServletRequest);
			livingPcId = CookieUtil.getUserLivingPcIdCookie(httpServletRequest);
			votingAcId = CookieUtil.getUserVotingAcIdCookie(httpServletRequest);
			votingPcId = CookieUtil.getUserVotingPcIdCookie(httpServletRequest);
		}
		int pageNumber = getIntPramater(httpServletRequest, PARAM_PAGE_NUMBER, 1);
		ItemList<BlogDto> blogItems = aapDataCacheDbImpl.getBlogDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId, pageNumber);
		mv.getModel().put("blogItems", blogItems);
		mv.getModel().put("pageNumber", pageNumber);
	}
	
	protected void addGenericValuesInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
		mv.getModel().put("design", design);
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		mv.getModel().put("loggedInUser", loggedInUser);
		mv.getModel().put("staticDirectory", "https://s3-us-west-2.amazonaws.com/my.aamaadmiparty.org/01prandesign");
		mv.getModel().put("contextPath", httpServletRequest.getContextPath());
		LoginAccountDto loginAccountDto = getLoggedInAccountsFromSesion(httpServletRequest);
		mv.getModel().put("loginAccounts", loginAccountDto);
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion(httpServletRequest);
		if(userRolePermissionDto != null){
			mv.getModel().put("admin", userRolePermissionDto.isAdmin());	
		}else{
			mv.getModel().put("admin", false);
		}
		
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
	
	
}
