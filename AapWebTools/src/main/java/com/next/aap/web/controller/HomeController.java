package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.util.ContentDonwloadUtil;

@Controller
public class HomeController extends AppBaseController {
	
	@Autowired
	private ContentDonwloadUtil contentDonwloadUtil;
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		addNewsInModel(httpServletRequest, mv);
		mv.setViewName(design+"/index");
		return mv;
	}
	
}
