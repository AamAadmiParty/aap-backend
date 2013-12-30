package com.next.aap.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	private static final String LAST_ACCOUNT = "LA";
	private static final String FACEBOOK = "FB";
	private static final String TWITTER = "TW";

	public static boolean isLastLoggedInViaFacebook(HttpServletRequest httpServletRequest){
		return isLastLoggedInViaAccount(httpServletRequest, FACEBOOK);
	}
	public static boolean isLastLoggedInViaTwitter(HttpServletRequest httpServletRequest){
		return isLastLoggedInViaAccount(httpServletRequest, TWITTER);
	}
	
	public static boolean isLastLoggedInViaAccount(HttpServletRequest httpServletRequest, String account){
		Cookie[] allCookies = httpServletRequest.getCookies();
		System.out.println("allCookies="+allCookies);
		if(allCookies == null || allCookies.length == 0){
			return false;
		}
		for(Cookie oneCookie:allCookies){
			System.out.println("oneCookie = "+oneCookie.getName() +" : "+oneCookie.getValue());
			if(oneCookie.getName().equals(LAST_ACCOUNT)){
				return oneCookie.getValue().equals(account);
			}
		}
		return false;
	}

	public static void setLastLoggedInAccountAsFacebookCookie(HttpServletResponse httpServletResponse){
		System.out.println("Setting Cookie "+ LAST_ACCOUNT+" : "+ FACEBOOK);
		Cookie lastAccountCookie = new Cookie(LAST_ACCOUNT, FACEBOOK);
		lastAccountCookie.setMaxAge(3 * 30 * 24 * 60 * 60);
		lastAccountCookie.setPath("/");
		System.out.println("Setting Cookie "+ lastAccountCookie.getPath());
		httpServletResponse.addCookie(lastAccountCookie);
	}
	public static void setLastLoggedInAccountAsTwitterCookie(HttpServletResponse httpServletResponse){
		System.out.println("Setting Cookie "+ LAST_ACCOUNT+" : "+ TWITTER);
		Cookie lastAccountCookie = new Cookie(LAST_ACCOUNT, TWITTER);
		lastAccountCookie.setMaxAge(3 * 30 * 24 * 60 * 60);
		httpServletResponse.addCookie(lastAccountCookie);
	}
}
