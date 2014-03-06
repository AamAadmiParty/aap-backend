package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends AppBaseController {
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		addNewsInModel(httpServletRequest, mv);
		mv.setViewName(design+"/index");
		return mv;
	}
	
}
