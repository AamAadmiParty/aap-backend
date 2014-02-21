package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.ItemList;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.cache.AapDataCacheDbImpl;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.VideoDto;
import com.next.aap.web.util.ContentDonwloadUtil;

public class AppBaseController extends BaseController{

	@Autowired
	protected AapDataCache aapDataCacheDbImpl;
	@Value("${design:stylenew}")
	protected String design;
	@Autowired
	protected ContentDonwloadUtil contentDonwloadUtil;

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
		}
		ItemList<NewsDto> newsItems = aapDataCacheDbImpl.getNewsDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId);
		mv.getModel().put("newsItems", newsItems);
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
		}
		ItemList<VideoDto> videoItems = aapDataCacheDbImpl.getVideoDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId);
		mv.getModel().put("videoItems", videoItems);
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
		}
		ItemList<BlogDto> blogItems = aapDataCacheDbImpl.getBlogDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId);
		mv.getModel().put("blogItems", blogItems);
	}
	
	protected void addGenericValuesInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
		mv.getModel().put("design", design);
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		mv.getModel().put("loggedInUser", loggedInUser);
		mv.getModel().put("staticDirectory", "https://s3-us-west-2.amazonaws.com/my.aamaadmiparty.org/01prandesign");
		
	}
	
}
