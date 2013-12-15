package com.next.aap.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.controller.BaseController;

@Controller
public class LoginController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		mv.setViewName("login");
		return mv;
	}
}
