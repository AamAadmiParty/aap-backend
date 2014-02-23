package com.next.aap.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.dto.DonationDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.util.ContentDonwloadUtil;

@Controller
public class MyDonationController extends AppBaseController {
	
	@Autowired
	private ContentDonwloadUtil contentDonwloadUtil;
	
	@RequestMapping(value = "/mydonations.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		
		List<DonationDto> successDonations = new ArrayList<>();
		List<DonationDto> failedDonations = new ArrayList<>();
		
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);

		List<DonationDto> donations = aapService.getUserDonations(loggedInUser.getId());
		double total = 0.0;
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
		
		addGenericValuesInModel(httpServletRequest, mv);
		
		mv.getModel().put("successDonations", successDonations);
		mv.getModel().put("failedDonations", failedDonations);
		mv.getModel().put("total", total);
		mv.setViewName(design+"/mydonations");
		return mv;
	}
	
}
