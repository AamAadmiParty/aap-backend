package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.cache.CacheKeyService;
import com.next.aap.cache.beans.DonationCampaignInfo;

@Controller
public class CounterController extends AppBaseController {

	@RequestMapping(value = "/lcounter.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		String param = httpServletRequest.getParameter("lcid");
		if(!StringUtil.isEmpty(param)){
			String key = CacheKeyService.createLocationCampaignKey(param);
			DonationCampaignInfo donationCampaignInfo = cacheService.getData(key, DonationCampaignInfo.class);
			if(donationCampaignInfo == null){
				mv.getModel().put("totalDonation", 0);	
			}else{
				mv.getModel().put("totalDonation", donationCampaignInfo.getTamt());
			}
		}else{
			mv.getModel().put("totalDonation", 0);	
		}

		mv.setViewName(design+"/counter");
		return mv;
	}
	
	@RequestMapping(value = "/gcounter.html", method = RequestMethod.GET)
	public ModelAndView getGlobaclCampaignCounter(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		String param = httpServletRequest.getParameter("gcid");
		if(!StringUtil.isEmpty(param)){
			String key = CacheKeyService.createGlobalCampaignKey(param);
			DonationCampaignInfo donationCampaignInfo = cacheService.getData(key, DonationCampaignInfo.class);
			if(donationCampaignInfo == null){
				mv.getModel().put("totalDonation", 0);	
			}else{
				mv.getModel().put("totalDonation", donationCampaignInfo.getTamt());
			}
		}else{
			mv.getModel().put("totalDonation", 0);	
		}

		mv.setViewName(design+"/counter");
		return mv;
	}
	
	
	@RequestMapping(value = "/dcounter.html", method = RequestMethod.GET)
	public ModelAndView getDonationCampaignCounter(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		String param = httpServletRequest.getParameter("cid");
		if(!StringUtil.isEmpty(param)){
			String key = CacheKeyService.createDonationCampaignKey(param);
			DonationCampaignInfo donationCampaignInfo = cacheService.getData(key, DonationCampaignInfo.class);
			if(donationCampaignInfo == null){
				mv.getModel().put("totalDonation", 0);	
			}else{
				mv.getModel().put("totalDonation", donationCampaignInfo.getTamt());
			}
		}else{
			mv.getModel().put("totalDonation", 0);	
		}

		mv.setViewName(design+"/counter");
		return mv;
	}
	
}
