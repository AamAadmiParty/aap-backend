package com.next.aap.web.controller.login;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.util.EnvironmentUtil;
import com.next.aap.web.controller.BaseController;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

@Controller
public class FacebookLoginController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String appId = "618283854899839";
	private static final String appSecret = "4a8d9a3005c298dc9176b0990a996ea6";
	private static final String localAppId = "372184272903954";
	private static final String localAppSecret = "bec883d1ffb415fff01248f8c46f78f9";
	
	private static final String localRedirectUrl = "http://localhost:8081/aap/login/facebooksuccess";
	private static final String productionRedirectUrl = "http://www.vote4delhi.com/vote/login/facebooksuccess";
	private static final String appPermissions = "email,user_birthday,user_hometown,user_location,user_photos,offline_access";

	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		StringBuilder urlBuilder = new StringBuilder(
				"https://graph.facebook.com/oauth/authorize?");
		addParam(urlBuilder, "client_id", getAppId());
		urlBuilder.append("&");
		addParam(urlBuilder, "redirect_uri", getFacebookRedirectUrl(httpServletRequest));
		urlBuilder.append("&");
		addParam(urlBuilder, "scope", appPermissions);
		
		setRedirectUrlInSessiom(httpServletRequest, getRedirectUrl(httpServletRequest));

		RedirectView rv = new RedirectView(urlBuilder.toString());
		logger.info("url= {}", urlBuilder);
		mv.setView(rv);
		return mv;
	}
	private String getAppId(){
		if(EnvironmentUtil.isProductionEnv()){
			return appId;
		}
		return localAppId;
	}
	private String getAppSecret(){
		if(EnvironmentUtil.isProductionEnv()){
			return appSecret;
		}
		return localAppSecret;
	}
	@RequestMapping(value = "/facebooksuccess", method = RequestMethod.GET)
	public ModelAndView loginSuccess(HttpServletRequest httpServletRequest, ModelAndView mv) {
		try {
			String errorReason = httpServletRequest
					.getParameter("error_reason");
			String error = httpServletRequest.getParameter("error");
			String errorDescription = httpServletRequest
					.getParameter("error_description");
			String code = httpServletRequest.getParameter("code");
			if (error == null) {
				// mean success
				StringBuilder urlBuilder = new StringBuilder(
						"https://graph.facebook.com/oauth/access_token?format=json&");
				addParam(urlBuilder, "client_id", getAppId());
				urlBuilder.append("&");
				addParam(urlBuilder, "redirect_uri", getFacebookRedirectUrl(httpServletRequest));
				urlBuilder.append("&");
				addParam(urlBuilder, "client_secret", getAppSecret());
				urlBuilder.append("&");
				addParam(urlBuilder, "code", code);

				HttpClient httpClient = new DefaultHttpClient();
				logger.debug("success url= {}", urlBuilder);
				String response = getResponse(httpClient, urlBuilder.toString());
				logger.debug("response={}", response);
				// response = response.replaceFirst("access_token=", "");
				String[] tokens = response.split("&");
				String accessToken = null;
				String expires = null;
				for (String onePair : tokens) {
					String[] pairTokens = onePair.split("=");
					System.out.println(pairTokens[0] + " = " + pairTokens[1]);
					if (pairTokens[0].equals("access_token")) {
						accessToken = pairTokens[1];
					}
					if (pairTokens[0].equals("expires")) {
						expires = pairTokens[1];
					}
				}
				logger.info("accessToken={}", accessToken);
				logger.info("expires={}", expires);

				FacebookClient facebookClient = new DefaultFacebookClient(
						accessToken);
				User facebookUser = facebookClient
						.fetchObject("me", User.class);
				
				Calendar calendar = Calendar.getInstance();
				if(!StringUtil.isEmpty(expires)){
					calendar.add(Calendar.SECOND, Integer.parseInt(expires));	
				}
				//UserDto user = votingService.saveFacebookAccount(facebookUser,accessToken,calendar.getTime());
				
				//VotingSessionUtil.saveUserInSession(httpServletRequest, user);
			
				String redirectUrl = getAndRemoveRedirectUrlFromSession(httpServletRequest);
				redirectToViewAfterLogin(redirectUrl, mv);

			} else {
				// Its error and show user error message
				RedirectView redirectView = new RedirectView("/Vote/home");
				redirectView.setExposeModelAttributes(false);
				mv.getModel().put("message", "You must accept facebook Login permission to vote");
				mv.setView(redirectView);
			}
			System.out.println("errorReason = " + errorReason);
			System.out.println("error = " + error);
			System.out.println("errorDescription = " + errorDescription);
			System.out.println("code = " + code);

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

	
	protected void addParam(StringBuilder urlBuilder, String key, String value) {
		urlBuilder.append(key);
		urlBuilder.append("=");
		urlBuilder.append(value);
	}

	public String getResponse(HttpClient httpClient, String url)
			throws ClientProtocolException, IOException {
		logger.info("Hitting Url = {}", url);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		// System.out.println("Got Response= "+ httpResponse);
		HttpEntity httpEntity = httpResponse.getEntity();
		// System.out.println("Converting to String= "+ httpEntity);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// System.out.println("IOUtils.copy(httpEntity.getContent(), byteArrayOutputStream);");
		IOUtils.copy(httpEntity.getContent(), byteArrayOutputStream);
		String dayDonationString = byteArrayOutputStream.toString();
		return dayDonationString;
	}

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 5183999);
		System.out.println("Expires on " + calendar.getTime());
	}

}
