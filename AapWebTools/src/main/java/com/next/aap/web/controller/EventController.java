package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.util.ContentDonwloadUtil;

@Controller
public class EventController extends AppBaseController {
	
	@Autowired
	private ContentDonwloadUtil contentDonwloadUtil;
	
	@RequestMapping(value = "/events.html", method = RequestMethod.GET)
	public ModelAndView showEventPage(ModelAndView mv,HttpServletRequest httpServletRequest) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		addAllEventsInModel(httpServletRequest, mv);
		mv.setViewName(design+"/events");
		return mv;
	}
	
}
