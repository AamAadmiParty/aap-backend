package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RefreshController extends AppBaseController {
	
	@RequestMapping(value = "/aaps/refresh", method = RequestMethod.GET)
	@ResponseBody
	public String refreshCache(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		aapDataCacheDbImpl.refreshFullCache();
		return "cache Refreshed";
	}
	
	@RequestMapping(value = "/aaps/video", method = RequestMethod.GET)
	@ResponseBody
	public String downloadVideos(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		contentDonwloadUtil.refreshVideoList();
		return "Video downloaded";
	}
}
