package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.jsf.beans.AppDataBean;
import com.next.aap.web.util.ContentDonwloadUtil;

@Controller
public class HomeController extends BaseController {
	
	@Value("${design}")
	private String design;
	
	@Autowired
	private ContentDonwloadUtil contentDonwloadUtil;
	
	@Autowired
	private AapDataCache aapDataCacheDbImpl;
	
	@RequestMapping(value = "/aaps/index.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		//httpServletRequest.getSession().invalidate();
		mv.getModel().put("design", design);
		mv.setViewName(design+"/index");
		return mv;
	}
	
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
