package com.next.aap.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.DonationCampaignDto;
import com.next.aap.web.dto.DonationDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.util.ContentDonwloadUtil;

@Controller
public class MyRippleController extends AppBaseController {
	
	@Autowired
	private ContentDonwloadUtil contentDonwloadUtil;
	
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
		List<DonationDto> successDonations = new ArrayList<>();
		List<DonationDto> failedDonations = new ArrayList<>();
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		DonationCampaignDto rippleCampaign = aapService.getRippleDonationCamapign(loggedInUser.getId());
		double total = 0.0;
		boolean rippleCampaignExists;
		if(rippleCampaign == null){
			rippleCampaignExists = false;
			rippleCampaign = new DonationCampaignDto();
		}else{
			rippleCampaignExists = true;
			successDonations = new ArrayList<>();
			failedDonations = new ArrayList<>();
			List<DonationDto> donations = aapService.getUserRippleDonations(loggedInUser.getId());
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
				addErrorInModel(mv, "Please enter a campaign Identifier");
			}else{
				if(!myPattern.matcher(campaignId).find()){
					addErrorInModel(mv, "campaign Identifier must contain only number and alphabets");
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
