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
		newsItemCacheImpl.refreshFullCache();
		videoItemCacheImpl.refreshFullCache();
		blogItemCacheImpl.refreshFullCache();
		pollItemCacheImpl.refreshFullCache();
		return "cache Refreshed";
	}
	
	@RequestMapping(value = "/aaps/video", method = RequestMethod.GET)
	@ResponseBody
	public String downloadVideos(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		contentDonwloadUtil.refreshVideoList();
		videoItemCacheImpl.refreshFullCache();
		return "Video downloaded";
	}
	
	@RequestMapping(value = "/aaps/news", method = RequestMethod.GET)
	@ResponseBody
	public String downloadNews(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		contentDonwloadUtil.refreshNewsList();
		newsItemCacheImpl.refreshFullCache();
		return "news downloaded";
	}
	
	@RequestMapping(value = "/aaps/blog", method = RequestMethod.GET)
	@ResponseBody
	public String downloadBlogs(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		contentDonwloadUtil.refreshBlogList();
		blogItemCacheImpl.refreshFullCache();
		return "blog downloaded";
	}
	
	@RequestMapping(value = "/aaps/poll", method = RequestMethod.GET)
	@ResponseBody
	public String refreshPolls(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		blogItemCacheImpl.refreshFullCache();
		return "blog downloaded";
	}
	
	@RequestMapping(value = "/aaps/all", method = RequestMethod.GET)
	@ResponseBody
	public String downloadAll(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		contentDonwloadUtil.refreshAllCache();
		return "all downloaded";
	}
	
	
}
