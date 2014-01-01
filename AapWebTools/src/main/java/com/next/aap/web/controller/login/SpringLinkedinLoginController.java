package com.next.aap.web.controller.login;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
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
public class SpringLinkedinLoginController extends BaseSocialLoginController<LinkedIn> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String localRedirectUrl = "http://localhost:8081/aap/login/linkedinsuccess";
	private static final String productionRedirectUrl = "http://www.vote4delhi.com/vote/login/linkedinsuccess";
	//private static final String appPermissions = "email,user_birthday,user_hometown,user_location,user_photos,offline_access";
	private static final String appPermissions = "profile";

	@RequestMapping(value = "/linkedin", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		
		LinkedInConnectionFactory googleConnectionFactory = (LinkedInConnectionFactory)connectionFactoryLocator.getConnectionFactory(LinkedIn.class);

		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(getFacebookRedirectUrl(httpServletRequest));
		//params.setScope(appPermissions);
		params.setState(UUID.randomUUID().toString());
		
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
		
		//setRedirectUrlInSessiom(httpServletRequest, getRedirectUrl(httpServletRequest));

		RedirectView rv = new RedirectView(authorizeUrl);
		logger.info("url= {}", authorizeUrl);
		mv.setView(rv);
		return mv;
	}
	@RequestMapping(value = "/linkedinfail", method = RequestMethod.GET)
	@ResponseBody
	public String loginFailed(HttpServletRequest httpServletRequest, ModelAndView mv) {
		return "Please login to facebook and give permission";
	}
	@RequestMapping(value = "/linkedinsuccess", method = RequestMethod.GET)
	public ModelAndView loginSuccess(HttpServletRequest httpServletRequest, ModelAndView mv) {
		try {
			LinkedInConnectionFactory linkedinConnectionFactory = (LinkedInConnectionFactory)connectionFactoryLocator.getConnectionFactory(LinkedIn.class);
			OAuth2Operations oauthOperations = linkedinConnectionFactory.getOAuthOperations();
			String authorizationCode = httpServletRequest.getParameter("code");
			AccessGrant accessGrant = oauthOperations.exchangeForAccess(authorizationCode, getFacebookRedirectUrl(httpServletRequest), null);
			Connection<LinkedIn> connection = linkedinConnectionFactory.createConnection(accessGrant);
			
			//aapService.saveGoogleUser(null, connection);
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
	protected UserDto saveSocialUser(Connection<LinkedIn> socialConnection,
			UserDto loggedInUser) {
		UserDto user;
		if(loggedInUser == null){
			//user = aapService.saveLi(null, socialConnection);	
		}else{
			//user = aapService.saveGoogleUser(loggedInUser.getId(), socialConnection);
		}
		return null;
	}

	

}
