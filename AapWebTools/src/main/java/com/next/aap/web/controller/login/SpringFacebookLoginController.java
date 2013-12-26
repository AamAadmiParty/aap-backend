package com.next.aap.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
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
public class SpringFacebookLoginController extends BaseSocialLoginController<Facebook> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String localRedirectUrl = "http://localhost:8081/aap/login/facebooksuccess";
	private static final String productionRedirectUrl = "http://www.vote4delhi.com/vote/login/facebooksuccess";
	//private static final String appPermissions = "email,user_birthday,user_hometown,user_location,user_photos,offline_access";
	private static final String appPermissions = "email,user_birthday,offline_access";

	@Value("${aap_facebook_app_secret}")
	private String appFacebokAppId;

	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		
		FacebookConnectionFactory facebookConnectionFactory = (FacebookConnectionFactory)connectionFactoryLocator.getConnectionFactory(Facebook.class);

		OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();
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
	@RequestMapping(value = "/facebookfail", method = RequestMethod.GET)
	@ResponseBody
	public String loginFailed(HttpServletRequest httpServletRequest, ModelAndView mv) {
		return "Please login to facebook and give permission";
	}
	@RequestMapping(value = "/facebooksuccess", method = RequestMethod.GET)
	public ModelAndView loginSuccess(HttpServletRequest httpServletRequest, ModelAndView mv) {
		try {
			FacebookConnectionFactory facebookConnectionFactory = (FacebookConnectionFactory)connectionFactoryLocator.getConnectionFactory(Facebook.class);
			OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();
			String authorizationCode = httpServletRequest.getParameter("code");
			System.out.println("authorizationCode="+authorizationCode);
			AccessGrant accessGrant = oauthOperations.exchangeForAccess(authorizationCode, getFacebookRedirectUrl(httpServletRequest), null);
			Connection<Facebook> facebookConnection = facebookConnectionFactory.createConnection(accessGrant);
			
			afterSuccesfullLogin(httpServletRequest, facebookConnection);
			
			/*
			ConnectionRepository facebookConnectionRepository = usersConnectionRepository.createConnectionRepository(user.getExternalId());
			facebookConnectionRepository.updateConnection(connection);
			*/
			String redirectUrl = getAndRemoveRedirectUrlFromSession(httpServletRequest);
			RedirectView rv = new RedirectView(redirectUrl);
			logger.info("url= {}", redirectUrl);
			mv.setView(rv);

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
	protected UserDto saveSocialUser(Connection<Facebook> socialConnection,UserDto loggedInUser) {
		System.out.println("loggedInUser"+loggedInUser);
		UserDto user;
		if(loggedInUser == null){
			user = aapService.saveFacebookUser(null, socialConnection, appFacebokAppId);	
		}else{
			user = aapService.saveFacebookUser(loggedInUser.getId(), socialConnection, appFacebokAppId);
		}
		System.out.println("user"+user);
		return user;
	}

	

}
