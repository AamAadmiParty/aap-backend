package com.next.aap.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.next.aap.core.util.EnvironmentUtil;
import com.next.aap.web.dto.UserDto;

@Controller
public class SpringGoogleLoginController extends BaseSocialLoginController<Google> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String localRedirectUrl = "http://localhost:8081/aap/login/googlesuccess";
	private static final String productionRedirectUrl = "http://www.vote4delhi.com/vote/login/googlesuccess";
	//private static final String appPermissions = "email,user_birthday,user_hometown,user_location,user_photos,offline_access";
	private static final String appPermissions = "profile";

	@RequestMapping(value = "/google", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		
		GoogleConnectionFactory googleConnectionFactory = (GoogleConnectionFactory)connectionFactoryLocator.getConnectionFactory(Google.class);

		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(getFacebookRedirectUrl(httpServletRequest));
		params.setScope(appPermissions);
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
		
		//setRedirectUrlInSessiom(httpServletRequest, getRedirectUrl(httpServletRequest));

		RedirectView rv = new RedirectView(authorizeUrl);
		logger.info("url= {}", authorizeUrl);
		mv.setView(rv);
		return mv;
	}
	@RequestMapping(value = "/googlefail", method = RequestMethod.GET)
	@ResponseBody
	public String loginFailed(HttpServletRequest httpServletRequest, ModelAndView mv) {
		return "Please login to facebook and give permission";
	}
	@RequestMapping(value = "/googlesuccess", method = RequestMethod.GET)
	public ModelAndView loginSuccess(HttpServletRequest httpServletRequest, ModelAndView mv) {
		try {
			GoogleConnectionFactory googleConnectionFactory = (GoogleConnectionFactory)connectionFactoryLocator.getConnectionFactory(Google.class);
			OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
			String authorizationCode = httpServletRequest.getParameter("code");
			AccessGrant accessGrant = oauthOperations.exchangeForAccess(authorizationCode, getFacebookRedirectUrl(httpServletRequest), null);
			Connection<Google> googleConnection = googleConnectionFactory.createConnection(accessGrant);
			
			afterSuccesfullLogin(httpServletRequest, googleConnection);
			
			/*
			ConnectionRepository facebookConnectionRepository = usersConnectionRepository.createConnectionRepository("ravi");
			facebookConnectionRepository.addConnection(connection);
			*/

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mv;
	}

	protected String getFacebookRedirectUrl(HttpServletRequest httpServletRequest) {
		String url = localRedirectUrl; 
		if (EnvironmentUtil.isProductionEnv()) {
			url = productionRedirectUrl;
		}
		return url;
	}
	@Override
	protected UserDto saveSocialUser(Connection<Google> socialConnection,
			UserDto loggedInUser) {
		UserDto user;
		if(loggedInUser == null){
			user = aapService.saveGoogleUser(null, socialConnection);	
		}else{
			user = aapService.saveGoogleUser(loggedInUser.getId(), socialConnection);
		}
		return user;
	}

}
