package com.next.aap.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gdata.util.common.base.StringUtil;
import com.mysql.jdbc.Statement;
import com.next.aap.core.exception.AppException;
import com.next.aap.core.util.MyaapInUtil;
import com.next.aap.web.dto.DonationCampaignDto;
import com.next.aap.web.dto.DonationDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.util.ContentDonwloadUtil;

@Controller
public class MyRippleController extends AppBaseController {
	
	@Autowired
	private ContentDonwloadUtil contentDonwloadUtil;
	@Autowired
	private MyaapInUtil myaapInUtil;
	
	@RequestMapping(value = "/ripple/{campaignId}.html", method = RequestMethod.GET)
	public ModelAndView showRipplePublicPage(ModelAndView mv,HttpServletRequest httpServletRequest, @PathVariable String campaignId) {
		
		addUserRippleDonations(mv, campaignId);
		addGenericValuesInModel(httpServletRequest, mv);
		mv.setViewName(design+"/ripple");
		return mv;
	}
	
	@RequestMapping(value = "/ripple.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		
		addUserRippleDonations(mv, httpServletRequest);

		
		addGenericValuesInModel(httpServletRequest, mv);
		
		mv.setViewName(design+"/myripple");
		return mv;
	}
	private void addUserRippleDonations(ModelAndView mv,
			HttpServletRequest httpServletRequest){
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		DonationCampaignDto rippleCampaign = aapService.getRippleDonationCamapign(loggedInUser.getId());
		addUserRippleDonations(mv, rippleCampaign);

	}
	private void addUserRippleDonations(ModelAndView mv,String rippleCampaignId){
		try{
			DonationCampaignDto rippleCampaign = aapService.getRippleDonationCamapignByCid(rippleCampaignId);
			if(rippleCampaign != null){
				UserDto rippleUser = aapService.getUserByid(rippleCampaign.getUserId());
				LoginAccountDto loginAccountDto = aapService.getUserLoginAccounts(rippleUser.getId());
				mv.getModel().put("rippleUser", rippleUser);
				if(loginAccountDto != null && loginAccountDto.getTwitterAccounts() != null && !loginAccountDto.getTwitterAccounts().isEmpty()){
					if(loginAccountDto.getTwitterAccounts().get(0).getScreenName().startsWith("@")){
						mv.getModel().put("rippleUserTwitterAccount", loginAccountDto.getTwitterAccounts().get(0).getScreenName().substring(1));	
					}else{
						mv.getModel().put("rippleUserTwitterAccount", loginAccountDto.getTwitterAccounts().get(0).getScreenName());
					}
					
				}
				if(loginAccountDto != null && loginAccountDto.getFacebookAccounts() != null && !loginAccountDto.getFacebookAccounts().isEmpty()){
					mv.getModel().put("rippleUserFacebookAccount", loginAccountDto.getFacebookAccounts().get(0).getFacebookUserId());	
					
				}
				
			}
			
			addUserRippleDonations(mv, rippleCampaign);
			
		}catch(Exception ex){
			logger.error("Unable to addUserRippleDonations", ex);
		}
	}
	private void addUserRippleDonations(ModelAndView mv,DonationCampaignDto rippleCampaign){
		List<DonationDto> successDonations = new ArrayList<>();
		List<DonationDto> failedDonations = new ArrayList<>();
		double total = 0.0;
		boolean rippleCampaignExists;
		if(rippleCampaign == null){
			rippleCampaignExists = false;
			rippleCampaign = new DonationCampaignDto();
		}else{
			rippleCampaignExists = true;
			successDonations = new ArrayList<>();
			failedDonations = new ArrayList<>();
			List<DonationDto> donations = aapService.getUserRippleDonations(rippleCampaign.getUserId());
			if(donations != null && donations.size() > 0){
				for(DonationDto oneDonationDto:donations){
					if("Success".equalsIgnoreCase(oneDonationDto.getPgErrorMessage())){
						total = total + oneDonationDto.getAmount();
						successDonations.add(oneDonationDto);
					}else{
						failedDonations.add(oneDonationDto);
					}
				}
			}
			try {
				int totalClicks = myaapInUtil.getUrlTotalClicks(rippleCampaign.getCampaignId());
				mv.getModel().put("totalClicks", totalClicks);
			} catch (AppException e) {
			}
		}
		
		mv.getModel().put("rippleCampaign", rippleCampaign);
		mv.getModel().put("rippleCampaignExists", rippleCampaignExists);
		mv.getModel().put("successDonations", successDonations);
		mv.getModel().put("failedDonations", failedDonations);
		mv.getModel().put("total", total);

	}
	static Pattern myPattern = Pattern.compile("^[a-zA-Z0-9]+$");
	
	@RequestMapping(value = "/ripple.html", method = RequestMethod.POST)
	public ModelAndView saveRippleOnPost(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		
		
		
		try{
			String campaignId = httpServletRequest.getParameter("campaignId");
			if(campaignId != null){
				campaignId = campaignId.trim();
			}
			String description = httpServletRequest.getParameter("description");
			System.out.println("Description : "+description);
			if(description != null){
				description = description.trim();
			}
			if(StringUtil.isEmpty(campaignId)){
				addErrorInModel(mv, "Please enter a campaign Identifier/Name");
			}else{
				if(!myPattern.matcher(campaignId).find()){
					addErrorInModel(mv, "campaign Identifier/Name must contain only number and alphabets");
				}
			}
			if(isValidInput(mv)){
				UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
				aapService.saveRippleDonationCamapign(campaignId.toLowerCase(), description, loggedInUser.getId());	
			}
		}catch(Exception ex){
			addErrorInModel(mv, ex.getMessage());
		}
		addUserRippleDonations(mv, httpServletRequest);
		
		addGenericValuesInModel(httpServletRequest, mv);

		mv.setViewName(design+"/myripple");
		return mv;
	}
	
}
