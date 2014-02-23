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
public class BlogController extends AppBaseController {
	
	@Autowired
	private ContentDonwloadUtil contentDonwloadUtil;
	
	@RequestMapping(value = "/blog/{blogId}", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,HttpServletRequest httpServletRequest, @PathVariable Long blogId) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		addSingleBlogInModel(httpServletRequest, mv, blogId);
		mv.setViewName(design+"/blog");
		return mv;
		
	}
	
}
