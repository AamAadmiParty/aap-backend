package com.next.aap.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.controller.BaseController;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;

@Controller
public class LoginController extends BaseController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		//httpServletRequest.getSession().invalidate();
		String redirectUrlAfterLogin = httpServletRequest.getRequestURL().toString();
		setRedirectUrlInSessiom(httpServletRequest, redirectUrlAfterLogin);
		UserDto loggedInUser = getLoggedInUserInSesion(httpServletRequest);
		LoginAccountDto loginAccountDto = getLoggedInAccountsInSesion(httpServletRequest);
		mv.getModel().put("loggedInUser", loggedInUser);
		mv.getModel().put("loginAccounts", loginAccountDto);
		
		mv.setViewName("login");
		return mv;
	}
}
