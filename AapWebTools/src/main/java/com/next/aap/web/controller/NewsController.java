package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.util.ContentDonwloadUtil;

@Controller
public class NewsController extends AppBaseController {
	
	@RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,HttpServletRequest httpServletRequest, @PathVariable Long newsId) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		addSingleNewsInModel(httpServletRequest, mv, newsId);
		mv.setViewName(design+"/news");
		return mv;
		
	}
	
}
