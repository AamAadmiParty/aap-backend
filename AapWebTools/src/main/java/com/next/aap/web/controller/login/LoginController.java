package com.next.aap.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.next.aap.core.service.AapService;
import com.next.aap.web.controller.BaseController;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.CookieUtil;

@Controller
public class LoginController extends BaseController {

	@Autowired
	protected AapService aapService;
	@Value("${design:stylenew}")
	protected String design;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		String redirectUrlAfterLogin = getRedirectUrlForRedirectionAfterLogin(httpServletRequest);
		RedirectView rv = null;
		if(CookieUtil.isLastLoggedInViaFacebook(httpServletRequest)){
			rv = new RedirectView(createLoginUrl(httpServletRequest, "facebook", redirectUrlAfterLogin));
			logger.info("will redirect to facebook and then "+redirectUrlAfterLogin);
		}
		if(CookieUtil.isLastLoggedInViaTwitter(httpServletRequest)){
			rv = new RedirectView(createLoginUrl(httpServletRequest, "twitter", redirectUrlAfterLogin));
			logger.info("will redirect to twitter and then "+redirectUrlAfterLogin);
		}
		if(rv == null){
            rv = new RedirectView(httpServletRequest.getContextPath() + "/signin?" + REDIRECT_URL_PARAM_ID + "=" + redirectUrlAfterLogin);
			logger.info("will redirect to default signin");
		}
		mv.setView(rv);
		return mv;
	}
	protected void addGenericValuesInModel(HttpServletRequest httpServletRequest, ModelAndView mv){
		mv.getModel().put("design", design);
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		mv.getModel().put("loggedInUser", loggedInUser);
		//mv.getModel().put("staticDirectory", "https://s3-us-west-2.amazonaws.com/my.aamaadmiparty.org/01prandesign");
		mv.getModel().put("staticDirectory", "https://s3.amazonaws.com/myaap");
		mv.getModel().put("contextPath", httpServletRequest.getContextPath());
		LoginAccountDto loginAccountDto = getLoggedInAccountsFromSesion(httpServletRequest);
		mv.getModel().put("loginAccounts", loginAccountDto);
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion(httpServletRequest);
		if(userRolePermissionDto != null){
			mv.getModel().put("admin", userRolePermissionDto.isAdmin());	
		}
		
}

	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView signin(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		addGenericValuesInModel(httpServletRequest, mv);
		LoginAccountDto loginAccountDto = getLoggedInAccountsFromSesion(httpServletRequest);
        String redirectUrlAfterLogin = getRedirectUrlForRedirectionAfterLogin(httpServletRequest);
        String loginParams = REDIRECT_URL_PARAM_ID + "=" + redirectUrlAfterLogin;
		mv.getModel().put("loginAccounts", loginAccountDto);
        mv.getModel().put("loginParams", loginParams);
		mv.setViewName(design+"/login");
		return mv;
	}
	
	private String createLoginUrl(HttpServletRequest httpServletRequest,String accountType, String redirectUrlAfterLogin){
		return httpServletRequest.getContextPath()+"/login/"+accountType+"?"+REDIRECT_URL_PARAM_ID+"="+redirectUrlAfterLogin;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		httpServletRequest.getSession().invalidate();
		String redirectUrl = httpServletRequest.getContextPath()+"/signin";
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
