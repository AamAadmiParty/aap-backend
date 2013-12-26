package com.next.aap.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.util.EnvironmentUtil;
import com.next.aap.web.dto.UserDto;

@Controller
public class SpringTwitterLoginController extends BaseSocialLoginController<Twitter> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String localRedirectUrl = "http://localhost:8081/aap/login/twittersuccess";
	private static final String productionRedirectUrl = "http://www.vote4delhi.com/vote/login/twittersuccess";
	//private static final String appPermissions = "email,user_birthday,user_hometown,user_location,user_photos,offline_access";

	@RequestMapping(value = "/twitter", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		
		TwitterConnectionFactory twitterConnectionFactory = (TwitterConnectionFactory)connectionFactoryLocator.getConnectionFactory(Twitter.class);

		OAuth1Operations oauthOperations = twitterConnectionFactory.getOAuthOperations();
		OAuthToken requestToken = oauthOperations.fetchRequestToken(getTwitterRedirectUrl(httpServletRequest), null);
		String authorizeUrl = oauthOperations.buildAuthenticateUrl(requestToken.getValue() , OAuth1Parameters.NONE);
		
		//setRedirectUrlInSessiom(httpServletRequest, getRedirectUrl(httpServletRequest));

		RedirectView rv = new RedirectView(authorizeUrl);
		logger.info("url= {}", authorizeUrl);
		mv.setView(rv);
		return mv;
	}
	@RequestMapping(value = "/twitterfail", method = RequestMethod.GET)
	@ResponseBody
	public String loginFailed(HttpServletRequest httpServletRequest, ModelAndView mv) {
		return "Please login to facebook and give permission";
	}
	@RequestMapping(value = "/twittersuccess", method = RequestMethod.GET)
	public ModelAndView loginSuccess(HttpServletRequest httpServletRequest, ModelAndView mv) {
		try {
			// upon receiving the callback from the provider:
			TwitterConnectionFactory twitterConnectionFactory = (TwitterConnectionFactory)connectionFactoryLocator.getConnectionFactory(Twitter.class);
			OAuth1Operations oauthOperations = twitterConnectionFactory.getOAuthOperations();
			String requestTokenValue = httpServletRequest.getParameter("oauth_token");
			String oauthVerifier = httpServletRequest.getParameter("oauth_verifier");
			OAuthToken requestToken =  new OAuthToken(requestTokenValue, "5fmM9fVoDTIgHqKb8OeZ9cZullLdbL0uSrcC3mrTyM");
			OAuthToken accessToken = oauthOperations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);
			Connection<Twitter> twitterConnection = twitterConnectionFactory.createConnection(accessToken);
										
			
			afterSuccesfullLogin(httpServletRequest, twitterConnection);
			/*
			ConnectionRepository twitterConnectionRepository = usersConnectionRepository.createConnectionRepository(user.getExternalId());
			twitterConnectionRepository.updateConnection(connection);
			System.out.println(connection.getImageUrl());
			*/
			
			String redirectUrl = getAndRemoveRedirectUrlFromSession(httpServletRequest);
			if(StringUtil.isEmpty(redirectUrl)){
				redirectUrl = httpServletRequest.getContextPath()+"/socialaccounts";
			}
			RedirectView rv = new RedirectView(redirectUrl);
			logger.info("url= {}", redirectUrl);
			mv.setView(rv);
			return mv;

			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mv;
	}

	protected String getTwitterRedirectUrl(HttpServletRequest httpServletRequest) {
		String url = localRedirectUrl; 
		if (EnvironmentUtil.isProductionEnv()) {
			url = productionRedirectUrl;
		}
		return url;
	}
	@Override
	protected UserDto saveSocialUser(Connection<Twitter> socialConnection, UserDto loggedInUser) {
		System.out.println("loggedInUser"+loggedInUser);
		UserDto user;
		if(loggedInUser == null){
			user = aapService.saveTwitterUser(null, socialConnection);	
		}else{
			user = aapService.saveTwitterUser(loggedInUser.getId(), socialConnection);
		}
		System.out.println("user"+user);
		return user;
	}

	

}
