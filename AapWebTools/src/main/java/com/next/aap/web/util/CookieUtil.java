package com.next.aap.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.next.aap.web.dto.UserDto;

public class CookieUtil {

	private static final String LAST_ACCOUNT = "LA";
	private static final String FACEBOOK = "FB";
	private static final String TWITTER = "TW";
	
	private static final String LIVING_AC_ID_COOKIE = "LAIC";
	private static final String LIVING_PC_ID_COOKIE = "LPIC";
	private static final String VOTING_AC_ID_COOKIE = "VAIC";
	private static final String VOTING_PC_ID_COOKIE = "VPIC";
	private static final String NRI_COUNTRY_ID_COOKIE = "NCIC";
	private static final String NRI_COUNTRY_REGION_ID_COOKIE = "NCRIC";
	private static final String NRI_COUNTRY_REGION_AREA_ID_COOKIE = "NCRAIC";
	
	private static final String VOICE_OF_PAGE_COOKIE = "VOAC";
	
	private static final String POLL_COOKIE = "POC";
	
	private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

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

	public static void setVoiceOfAapPageCookie(HttpServletResponse httpServletResponse){
		logger.info("Creating Cookie "+ VOICE_OF_PAGE_COOKIE);
		Cookie lastAccountCookie = new Cookie(VOICE_OF_PAGE_COOKIE, VOICE_OF_PAGE_COOKIE);
		lastAccountCookie.setPath("/");
		lastAccountCookie.setMaxAge(30 * 24 * 60 * 60);
		httpServletResponse.addCookie(lastAccountCookie);
	}
	public static void setUserPollCookie(HttpServletResponse httpServletResponse,Long userId , Long pollId, Long answerId){
		String cookieName = POLL_COOKIE +"_"+userId+"_"+pollId;
		logger.info("Creating Cookie "+ cookieName);
		Cookie lastAccountCookie = new Cookie(cookieName, ""+answerId);
		lastAccountCookie.setPath("/");
		lastAccountCookie.setMaxAge(365 * 24 * 60 * 60);
		httpServletResponse.addCookie(lastAccountCookie);
	}
	public static String getUserPollCookie(HttpServletRequest httpServletRequest,Long userId , Long pollId){
		String cookieName = POLL_COOKIE +"_"+userId+"_"+pollId;
		logger.info("Getting Cookie "+ cookieName);
		return getStringCookie(httpServletRequest, cookieName);
	}
	public static boolean isUserBeenToVoiceOfAAp(HttpServletRequest httpServletRequest){
		logger.info("Creating Cookie "+ VOICE_OF_PAGE_COOKIE);
		String cookie = getStringCookie(httpServletRequest, VOICE_OF_PAGE_COOKIE);
		if(cookie == null){
			return false;
		}
		return true;
		
	}

	public static void setLastLoggedInAccountAsFacebookCookie(HttpServletResponse httpServletResponse){
		setLastLoggedInAccountCookie(httpServletResponse, FACEBOOK);
	}
	public static void setLastLoggedInAccountAsTwitterCookie(HttpServletResponse httpServletResponse){
		setLastLoggedInAccountCookie(httpServletResponse, TWITTER);
	}
	private static void setLastLoggedInAccountCookie(HttpServletResponse httpServletResponse, String account){
		logger.info("Creating Cookie "+ LAST_ACCOUNT+" = "+account);
		Cookie lastAccountCookie = new Cookie(LAST_ACCOUNT, account);
		lastAccountCookie.setPath("/");
		lastAccountCookie.setMaxAge(3 * 30 * 24 * 60 * 60);
		httpServletResponse.addCookie(lastAccountCookie);
	}
	public static void setUserLocationCookie(HttpServletResponse httpServletResponse, UserDto user){
		setUserLocationCookie(httpServletResponse, LIVING_AC_ID_COOKIE, user.getAssemblyConstituencyLivingId());
		setUserLocationCookie(httpServletResponse, LIVING_PC_ID_COOKIE, user.getParliamentConstituencyLivingId());
		setUserLocationCookie(httpServletResponse, VOTING_AC_ID_COOKIE, user.getAssemblyConstituencyVotingId());
		setUserLocationCookie(httpServletResponse, VOTING_PC_ID_COOKIE, user.getParliamentConstituencyVotingId());
		setUserLocationCookie(httpServletResponse, NRI_COUNTRY_ID_COOKIE, user.getNriCountryId());
		setUserLocationCookie(httpServletResponse, NRI_COUNTRY_REGION_ID_COOKIE, user.getNriCountryRegionId());
		setUserLocationCookie(httpServletResponse, NRI_COUNTRY_REGION_AREA_ID_COOKIE, user.getNriCountryRegionAreaId());
	}
	private static void setUserLocationCookie(HttpServletResponse httpServletResponse, String cookieName, Long id){
		if(id != null && id > 0){
			logger.info("Creating Cookie "+ cookieName+" = "+id);
			Cookie lastAccountCookie = new Cookie(cookieName, id.toString());
			lastAccountCookie.setPath("/");
			lastAccountCookie.setMaxAge(3 * 30 * 24 * 60 * 60);
			httpServletResponse.addCookie(lastAccountCookie);
		}
	}
	public static Long getUserNriCountryIdCookie(HttpServletRequest httpServletRequest){
		return getUserLocationCookie(httpServletRequest, NRI_COUNTRY_ID_COOKIE);
	}
	public static Long getUserNriCountryRegionIdCookie(HttpServletRequest httpServletRequest){
		return getUserLocationCookie(httpServletRequest, NRI_COUNTRY_REGION_ID_COOKIE);
	}
	public static Long getUserNriCountryRegionAreaIdCookie(HttpServletRequest httpServletRequest){
		return getUserLocationCookie(httpServletRequest, NRI_COUNTRY_REGION_AREA_ID_COOKIE);
	}
	public static Long getUserLivingAcIdCookie(HttpServletRequest httpServletRequest){
		return getUserLocationCookie(httpServletRequest, LIVING_AC_ID_COOKIE);
	}
	public static Long getUserLivingPcIdCookie(HttpServletRequest httpServletRequest){
		return getUserLocationCookie(httpServletRequest, LIVING_PC_ID_COOKIE);
	}
	public static Long getUserVotingAcIdCookie(HttpServletRequest httpServletRequest){
		return getUserLocationCookie(httpServletRequest, VOTING_AC_ID_COOKIE);
	}
	public static Long getUserVotingPcIdCookie(HttpServletRequest httpServletRequest){
		return getUserLocationCookie(httpServletRequest, VOTING_PC_ID_COOKIE);
	}
	private static Long getUserLocationCookie(HttpServletRequest httpServletRequest, String cookieName){
		try{
			Cookie[] allCookies = httpServletRequest.getCookies();
			if(allCookies == null || allCookies.length == 0){
				return 0L;
			}
			for(Cookie oneCookie:allCookies){
				if(oneCookie.getName().equals(cookieName)){
					return Long.valueOf(oneCookie.getValue());
				}
			}
			
		}catch(Exception ex){
			
		}
		return 0l;
	}
	private static Long getLongCookie(HttpServletRequest httpServletRequest, String cookieName){
		try{
			Cookie[] allCookies = httpServletRequest.getCookies();
			if(allCookies == null || allCookies.length == 0){
				return 0L;
			}
			for(Cookie oneCookie:allCookies){
				if(oneCookie.getName().equals(cookieName)){
					return Long.valueOf(oneCookie.getValue());
				}
			}
			
		}catch(Exception ex){
			
		}
		return 0l;
	}
	private static String getStringCookie(HttpServletRequest httpServletRequest, String cookieName){
		try{
			Cookie[] allCookies = httpServletRequest.getCookies();
			if(allCookies == null || allCookies.length == 0){
				return null;
			}
			for(Cookie oneCookie:allCookies){
				if(oneCookie.getName().equals(cookieName)){
					return oneCookie.getValue();
				}
			}
			
		}catch(Exception ex){
			
		}
		return null;
	}

}
