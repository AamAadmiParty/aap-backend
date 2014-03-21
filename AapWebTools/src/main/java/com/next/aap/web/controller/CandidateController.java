package com.next.aap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.cache.CacheKeyService;
import com.next.aap.cache.beans.DonationCampaignInfo;
import com.next.aap.web.cache.CandidateCacheImpl;
import com.next.aap.web.dto.CandidateDto;
import com.next.aap.web.dto.DonationCampaignDto;

@Controller
public class CandidateController extends AppBaseController {
	
	
	@RequestMapping(value = "/candidate/{urlPart1}/{urlPart2}.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,HttpServletRequest httpServletRequest,
			@PathVariable String urlPart1, @PathVariable String urlPart2) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		CandidateDto candidateDto = candidateCacheImpl.getCandidate(urlPart1, urlPart2);
		mv.getModel().put("candidate", candidateDto);
		addCandidateDonationInfo(candidateDto, mv);
		mv.setViewName(design+"/candidate");
		return mv;
	}
	@RequestMapping(value = "/candidates.html", method = RequestMethod.GET)
	public ModelAndView showCandidateMap(ModelAndView mv,HttpServletRequest httpServletRequest) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		List<CandidateDto> allCandidates = candidateCacheImpl.getAllCandidates();
		mv.getModel().put("candidates", allCandidates);
		mv.setViewName(design+"/candidatelist");
		return mv;
	}
	
	@RequestMapping(value = "/aapcandidates.html", method = RequestMethod.GET)
	public ModelAndView showRandonAapCandidate(ModelAndView mv,HttpServletRequest httpServletRequest) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		addUserPcCandidateInModel(httpServletRequest, mv);
		mv.setViewName(design+"/candidatewidget");
		return mv;
	}
	
}
