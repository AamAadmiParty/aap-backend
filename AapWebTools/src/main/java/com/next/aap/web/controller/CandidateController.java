package com.next.aap.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.cache.beans.DonationCampaignInfo;
import com.next.aap.web.dto.CandidateDto;
import com.next.aap.web.dto.CandidateWithDonation;

@Controller
public class CandidateController extends AppBaseController {
	
	
	@RequestMapping(value = "/candidate/{urlPart1}/{urlPart2}.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,HttpServletRequest httpServletRequest,
			@PathVariable String urlPart1, @PathVariable String urlPart2) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		CandidateDto candidateDto = candidateCacheImpl.getCandidate(urlPart1, urlPart2);
		mv.getModel().put("candidate", candidateDto);
		addCandidateDonationInfo(candidateDto, mv);
		mv.getModel().put("PageTitle", candidateDto.getName() +" : Lokasabha Candiate from " + candidateDto.getStateName() +" - "+candidateDto.getPcName() +" of Aam Aadmi Party for Election 2014");
		mv.setViewName(design+"/candidate");
		return mv;
	}
	@RequestMapping(value = "/candidate/{stateName}.html", method = RequestMethod.GET)
	public ModelAndView showStateCandidates(ModelAndView mv,HttpServletRequest httpServletRequest,
			@PathVariable String stateName) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		Set<CandidateDto> allCandidates = candidateCacheImpl.getCandidatesOfState(stateName);
		List<CandidateWithDonation> candidates = addCandidateWithDonationInfoInModel(allCandidates);
		mv.getModel().put("candidates", candidates);
		String view = httpServletRequest.getParameter("type");
		if(view != null && view.equalsIgnoreCase("map")){
			mv.setViewName(design+"/candidatemap");	
		}else{
			mv.setViewName(design+"/candidatelist");
		}
		mv.getModel().put("PageTitle", "Lokasabha Candiate of Aam Aadmi Party for Election 2014 from " +stateName);

		return mv;
	}
	protected List<CandidateWithDonation> addCandidateWithDonationInfoInModel(Collection<CandidateDto> candidates){
		List<CandidateWithDonation> returnDonations = new ArrayList<>(candidates.size());
		for(CandidateDto oneCandidate:candidates){
			CandidateWithDonation candidateWithDonation = new CandidateWithDonation();
			BeanUtils.copyProperties(oneCandidate, candidateWithDonation);
			DonationCampaignInfo donationCampaignInfo = getCandidateDonationInfo(oneCandidate);
			if(donationCampaignInfo == null){
				candidateWithDonation.setTotalTransactions(0);
				candidateWithDonation.setTotalAmount(0.0);
			}else{
				candidateWithDonation.setTotalTransactions(donationCampaignInfo.getTtxn());
				candidateWithDonation.setTotalAmount(donationCampaignInfo.getTamt());
			}
			returnDonations.add(candidateWithDonation);
		}
		return returnDonations;
	}
	@RequestMapping(value = "/candidates.html", method = RequestMethod.GET)
	public ModelAndView showCandidateMap(ModelAndView mv,HttpServletRequest httpServletRequest) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		List<CandidateDto> allCandidates = candidateCacheImpl.getAllCandidates();
		List<CandidateWithDonation> candidates = addCandidateWithDonationInfoInModel(allCandidates);
		mv.getModel().put("candidates", candidates);
		String view = httpServletRequest.getParameter("type");
		if(view != null && view.equalsIgnoreCase("map")){
			mv.setViewName(design+"/candidatemap");	
		}else{
			mv.setViewName(design+"/candidatelist");
		}
		mv.getModel().put("PageTitle", "Lokasabha Candidates of Aam Aadmi Party for Election 2014");
		
		return mv;
	}
	
	@RequestMapping(value = "/aapcandidates.html", method = RequestMethod.GET)
	public ModelAndView showRandonAapCandidate(ModelAndView mv,HttpServletRequest httpServletRequest) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		addUserPcCandidateInModel(httpServletRequest, mv);
		String param = httpServletRequest.getParameter("wide");
		if(param != null && param.equals("1")){
			mv.setViewName(design+"/candidatewidgetwide");	
		}else{
			mv.setViewName(design+"/candidatewidget");
		}
		
		return mv;
	}
	
}
