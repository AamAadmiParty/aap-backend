package com.next.aap.core.util;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.exception.AppException;
import com.next.aap.core.service.HttpUtil;

@Component
public class MyaapInUtil {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String baseShortenUrl = "http://myaap.in/yourls-api.php?format=json&username=arvind&password=4delhi";
	private final String urlShortnerUrl= baseShortenUrl+"&action=shorturl&url=";
	private final String urlCheckerUrl = baseShortenUrl+"&action=expand&shorturl=";
	private final String urlStatsUrl = baseShortenUrl+"&action=url-stats&shorturl=";
	@Autowired
	private HttpUtil httpUtil;

	
	public boolean isUrlAlreadyExists(String shortUrl) throws AppException{
		HttpClient httpClient = new DefaultHttpClient();
		try{
			logger.info("Checking for url = "+shortUrl);
			String finalUrl = urlCheckerUrl + shortUrl;

			String dayDonationString = httpUtil.getResponse(httpClient, finalUrl);
			logger.info("dayDonationString = "+dayDonationString);
			JSONObject jsonObject = new JSONObject(dayDonationString);
			int status = getIntFromJson(jsonObject, "errorCode", 200);
			if(status == 200){
				return true;
			}else{
				return false;
			}
			
		}catch(Exception ex){
			logger.error("Unable to check Url + "+shortUrl, ex);
			throw new AppException(ex.getMessage());
		}
	}
	
	public int getUrlTotalClicks(String shortUrl) throws AppException{
		HttpClient httpClient = new DefaultHttpClient();
		try{
			logger.info("Checking Stats for url = "+shortUrl);
			String finalUrl = urlStatsUrl + shortUrl;

			String dayDonationString = httpUtil.getResponse(httpClient, finalUrl);
			logger.info("dayDonationString = "+dayDonationString);
			JSONObject jsonObject = new JSONObject(dayDonationString);
			int status = getIntFromJson(jsonObject, "errorCode", 200);
			if(status == 200){
				JSONObject linkJson = jsonObject.getJSONObject("link");
				return getIntFromJson(linkJson, "clicks", 0);
			}else{
				return 0;
			}
			
		}catch(Exception ex){
			logger.error("Unable to check Url + "+shortUrl, ex);
			throw new AppException(ex.getMessage());
		}
	}
	
	public String createShortUrl(String longUrl,String shortUrlNameWithoutDomain) throws AppException{
		HttpClient httpClient = new DefaultHttpClient();
		try{
			logger.info("Long url = "+longUrl);
			String encodedUrl = URLEncoder.encode(longUrl,"UTF-8");
			logger.info("encodedUrl url = "+encodedUrl);
			logger.info("final url = "+urlShortnerUrl+encodedUrl);
			String dayDonationString = httpUtil.getResponse(httpClient, urlShortnerUrl+encodedUrl+"&keyword="+shortUrlNameWithoutDomain);
			logger.info("dayDonationString = "+dayDonationString);
			JSONObject jsonObject = new JSONObject(dayDonationString);
			String status = jsonObject.getString("status");
			if("fail".equals(status)){
				String errorCode = jsonObject.getString("code");
				if("error:keyword".equals(errorCode)){
					throw new AppException("Idenitifier "+ shortUrlNameWithoutDomain +" already used, please try something else");	
				}else{
					throw new AppException(jsonObject.getString("message"));
				}
			}else{
				String shortUrl = jsonObject.getString("shorturl");
				return shortUrl;
			}
			
		}catch(AppException aex){
			logger.error("Unable to shorten Url + "+longUrl + " , cid="+shortUrlNameWithoutDomain, aex);
			throw aex;
		}catch(Exception ex){
			logger.error("Unable to shorten Url + "+longUrl + " , cid="+shortUrlNameWithoutDomain, ex);
			throw new AppException(ex.getMessage());
		}
	}
	
	private String getStringFromJson(JSONObject json,String key, String defaultValue){
		String value = defaultValue;
		try{
			value = json.getString(key);	
		}catch(Exception ex){
			
		}
		return value;
	}
	
	private int getIntFromJson(JSONObject json,String key, int defaultValue){
		int value = defaultValue;
		try{
			value = json.getInt(key);	
		}catch(Exception ex){
			
		}
		return value;
	}

}
