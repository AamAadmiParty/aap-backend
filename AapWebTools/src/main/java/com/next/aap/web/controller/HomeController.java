package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {
	
	@Value("${design}")
	private String design;
	
	@RequestMapping(value = "/aaps/index.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		//httpServletRequest.getSession().invalidate();
		mv.getModel().put("design", design);
		mv.setViewName(design+"/index");
		return mv;
	}
}
