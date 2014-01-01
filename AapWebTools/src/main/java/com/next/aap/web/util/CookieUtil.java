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
		if(allCookies == null || allCookies.length == 0){
			return false;
		}
		for(Cookie oneCookie:allCookies){
			if(oneCookie.getName().equals(LAST_ACCOUNT)){
				return oneCookie.getValue().equals(account);
			}
		}
		return false;
	}

	public static void setLastLoggedInAccountAsFacebookCookie(HttpServletResponse httpServletResponse){
		setLastLoggedInAccountCookie(httpServletResponse, FACEBOOK);
	}
	public static void setLastLoggedInAccountAsTwitterCookie(HttpServletResponse httpServletResponse){
		setLastLoggedInAccountCookie(httpServletResponse, TWITTER);
	}
	private static void setLastLoggedInAccountCookie(HttpServletResponse httpServletResponse, String account){
		System.out.println("Creating Cookie "+ LAST_ACCOUNT+" = "+account);
		Cookie lastAccountCookie = new Cookie(LAST_ACCOUNT, account);
		lastAccountCookie.setPath("/");
		lastAccountCookie.setMaxAge(3 * 30 * 24 * 60 * 60);
		httpServletResponse.addCookie(lastAccountCookie);
		
	}
}
