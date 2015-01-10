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
public class VideoController extends AppBaseController {
	
	@Autowired
	private ContentDonwloadUtil contentDonwloadUtil;
	
	@RequestMapping(value = "/video/{videoId}", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,HttpServletRequest httpServletRequest, @PathVariable Long videoId) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		addSingleVideoInModel(httpServletRequest, mv, videoId);
        addUserAcCandidateInModel(httpServletRequest, mv);
		mv.setViewName(design+"/video");
		return mv;
		
	}
	
}
