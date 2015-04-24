package com.next.aap.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.cache.CacheKeyService;
import com.next.aap.cache.beans.DonationCampaignInfo;
import com.next.aap.web.controller.bean.DonationBean;

@Controller
public class DonationController extends AppBaseController {
	
	@InitBinder("donation")
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
		//binder.setValidator(userProfileValidator);
	}

	@RequestMapping(value = "/donation.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		System.out.println("CID : "+httpServletRequest.getParameter("cid"));
		mv = new ModelAndView(design + "/donation", "donation", new DonationBean());
		addGenericValuesInModel(httpServletRequest, mv);
		addIndianStatesIntoModel(mv);
        mv.getModel().put("staticDirectory", staticDirectory);
		return mv;
	}
	
	@RequestMapping(value = "/donation.html", method = RequestMethod.POST)
	public ModelAndView postDonation(@ModelAttribute("donation") DonationBean donation, ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		System.out.println("Donate to : "+donation);
		mv = new ModelAndView(design + "/donation", "donation", new DonationBean());
		addGenericValuesInModel(httpServletRequest, mv);
		addIndianStatesIntoModel(mv);
        mv.getModel().put("staticDirectory", staticDirectory);
		return mv;
	}
	
	@RequestMapping(value = "/donationsforlocation.html", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getDonationsForLocation( ModelAndView mv,HttpServletRequest httpServletRequest) {
		String param = httpServletRequest.getParameter("lcid");
		System.out.println("param CID = "+param);
		try{
			if(!StringUtil.isEmpty(param)){
				String key = CacheKeyService.createLocationCampaignKey(param);
				System.out.println("Key = "+ key);
				DonationCampaignInfo donationCampaignInfo = cacheService.getData(key, DonationCampaignInfo.class);
				mv.getModel().put("donationCampaignInfo", donationCampaignInfo);
			}
		}catch(Exception ex){
			
		}
		
		mv.setViewName(design + "/locationdonationtable");
		return mv;
	}
	
}
