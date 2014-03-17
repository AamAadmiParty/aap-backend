package com.next.aap.web.controller.login;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.next.aap.web.dto.UserDto;

@Controller
public class SpringVoiceOfAapFacebookLoginController extends BaseSocialLoginController<Facebook> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String appPermissions = "email,user_birthday,offline_access";


	@Value("${voa_facebook_app_id}")
	private String voiceOfAapAppId;
	@Value("${voa_facebook_app_secret}")
	private String voiceOfAapAppSecret;

	@Value("${server_domain_and_context}/login/voa/facebooksuccess")
	private String facebookRedirectUrl;

	
	public void setVoiceOfAapAppId(String voiceOfAapAppId) {
		this.voiceOfAapAppId = voiceOfAapAppId;
	}

	public void setVoiceOfAapAppSecret(String voiceOfAapAppSecret) {
		this.voiceOfAapAppSecret = voiceOfAapAppSecret;
	}

	@PostConstruct
	public void init(){
	}
	
	@RequestMapping(value = "/voa/facebook", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,HttpServletRequest httpServletRequest, @RequestParam("group") String permissionGroup) {
		if(permissionGroup.equalsIgnoreCase("timeline")){
			appPermissions = "publish_actions,publish_stream";
		}
		if(permissionGroup.equalsIgnoreCase("groups")){
			appPermissions = "publish_actions,publish_stream,user_groups";
		}
		if(permissionGroup.equalsIgnoreCase("pages")){
			appPermissions = "publish_actions,publish_stream,manage_pages,read_insight";
		}
		
		FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(voiceOfAapAppId, voiceOfAapAppSecret);

		OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(facebookRedirectUrl);
		params.setScope(appPermissions);
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
		
		setRedirectUrlInSessiom(httpServletRequest, getRedirectUrlForRedirectionAfterLogin(httpServletRequest));

		RedirectView rv = new RedirectView(authorizeUrl);
		logger.info("url= {}", authorizeUrl);
		mv.setView(rv);
		return mv;
	}
	@RequestMapping(value = "/voa/facebookfail", method = RequestMethod.GET)
	@ResponseBody
	public String loginFailed(HttpServletRequest httpServletRequest, ModelAndView mv) {
		return "Please login to facebook and give permission";
	}
	@RequestMapping(value = "/voa/facebooksuccess", method = RequestMethod.GET)
	public ModelAndView loginSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,  ModelAndView mv) {
		try {
			FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(voiceOfAapAppId, voiceOfAapAppSecret);
			OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();
			String authorizationCode = httpServletRequest.getParameter("code");
			AccessGrant accessGrant = oauthOperations.exchangeForAccess(authorizationCode, facebookRedirectUrl, null);
			Connection<Facebook> facebookConnection = facebookConnectionFactory.createConnection(accessGrant);
			
			afterSuccesfullLogin(httpServletRequest, httpServletResponse, facebookConnection);
			
			String redirectUrl = getAndRemoveRedirectUrlFromSession(httpServletRequest);
			logger.info("url= {}", redirectUrl);
			//if(redirectUrl == null)
			{
				redirectUrl = httpServletRequest.getContextPath()+"/voa.html";
			}
			RedirectView rv = new RedirectView(redirectUrl);
			logger.info("url= {}", redirectUrl);
			mv.setView(rv);

		} catch (Exception ex) {
			logger.error("unable to complete Voice of AAP facebook login", ex);
			String redirectUrl = httpServletRequest.getContextPath()+"/voa/facebookfail";
			RedirectView rv = new RedirectView(redirectUrl);
			logger.info("url= {}", redirectUrl);
			mv.setView(rv);

		}
		return mv;
	}

	@Override
	protected UserDto saveSocialUser(Connection<Facebook> socialConnection,UserDto loggedInUser) throws Exception{
		UserDto user;
		if(loggedInUser == null){
			user = aapService.saveFacebookUser(null, socialConnection, voiceOfAapAppId);	
		}else{
			user = aapService.saveFacebookUser(loggedInUser.getId(), socialConnection, voiceOfAapAppId);
		}
		return user;
	}


}
