package com.next.aap.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.AapService;
import com.next.aap.web.controller.BaseController;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.util.CookieUtil;

@Controller
public class LoginController extends BaseController {

	@Autowired
	protected AapService aapService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		//httpServletRequest.getSession().invalidate();
		String redirectUrlAfterLogin = getRedirectUrlForRedirectionAfterLogin(httpServletRequest);
		RedirectView rv = null;
		if(CookieUtil.isLastLoggedInViaFacebook(httpServletRequest)){
			rv = new RedirectView(createLoginUrl(httpServletRequest, "facebook", redirectUrlAfterLogin));
			System.out.println("will redirect to facebook");
		}
		if(CookieUtil.isLastLoggedInViaTwitter(httpServletRequest)){
			rv = new RedirectView(createLoginUrl(httpServletRequest, "twitter", redirectUrlAfterLogin));
			System.out.println("will redirect to twitter");
		}
		if(rv == null){
			rv = new RedirectView(httpServletRequest.getContextPath()+"/socialaccounts");
			System.out.println("will redirect to default social login");
		}
		mv.setView(rv);
		return mv;
	}
	
	private String createLoginUrl(HttpServletRequest httpServletRequest,String accountType, String redirectUrlAfterLogin){
		return httpServletRequest.getContextPath()+"/login/"+accountType+"?"+REDIRECT_URL_PARAM_ID+"="+redirectUrlAfterLogin;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		httpServletRequest.getSession().invalidate();
		String redirectUrl = httpServletRequest.getContextPath()+"/socialaccounts";
		RedirectView rv = new RedirectView(redirectUrl);
		rv.setExposeModelAttributes(false);
		mv.setView(rv);
		return mv;
	}
	
	@RequestMapping(value = "/ur", method = RequestMethod.GET)
	@ResponseBody
	public String updateROlesAndPermissions(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		aapService.updateAllPermissionsAndRole();
		return "Job Finished";
	}
}
