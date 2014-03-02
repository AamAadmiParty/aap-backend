package com.next.aap.core.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.core.service.HttpUtil;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.PostLocationType;

@Component
public class AapBlogDownloader {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String urlShortnerUrl = "http://myaap.in/yourls-api.php?format=json&username=arvind&password=4delhi&action=shorturl&url=";
	
	@Autowired
	private AapService aapService;
	
	public void downloadAapBlogs() throws Exception {
		logger.info("Downloading AAP Blogs");
		WebDriver webDriver = new HtmlUnitDriver(true);
		List<WebElement> readMoreLinks;
		List<String> allLinks = new ArrayList<String>();
		String url;
		WebElement pageDiv;
		BlogDto oneBlogItem;
		String title;
		String content;
		HttpUtil httpUtil = new HttpUtil();

		WebDriver webDriverForPage = new HtmlUnitDriver(true);
		BlogDto existingItem;
		String listPageUrl;
		for (int i = 2; i >= 0; i--) {
			listPageUrl = "http://www.aamaadmiparty.org/blog?page=" + i;
			logger.info("Hitting Url : "+listPageUrl);
			webDriver.get(listPageUrl);
			readMoreLinks = webDriver.findElements(By.className("read-more"));
			for (int j = readMoreLinks.size() - 1; j >= 0; j--) {
				WebElement oneWebElement = readMoreLinks.get(j);
				url = oneWebElement.findElement(By.tagName("a")).getAttribute(
						"href");
				allLinks.add(url);
				existingItem = aapService.getBlogByOriginalUrl(url);
				if(existingItem != null){
					continue;
				}
				logger.info("Hitting News Url : "+url);
				webDriverForPage.get(url);
				Thread.sleep(1000);
				pageDiv = webDriverForPage.findElement(By
						.className("aap-page-content-class"));

				title = pageDiv.findElement(By.className("pane-title"))
						.getText();
				WebElement nodeContentElement = pageDiv.findElement(By.className("node__content"));
				List<WebElement> fieldItems = nodeContentElement.findElements(By.className("field__item"));
				String imgSrc = null;
				try{
					WebElement imgElement = fieldItems.get(0).findElement(By.tagName("img"));
					if(imgElement != null){
						imgSrc = imgElement.getAttribute("src");
					}
				}catch(Exception ex){
					
				}
				WebElement contentElement = pageDiv.findElement(By.className("field--type-text-with-summary"));
				
				
				content = (String)((JavascriptExecutor)webDriverForPage).executeScript("return arguments[0].innerHTML;", contentElement); 
				
				//content = contentElement.getText();
				

				oneBlogItem = new BlogDto();
				oneBlogItem.setSource("www.aamaadmiparty.org");
				oneBlogItem.setTitle(title);
				oneBlogItem.setImageUrl(imgSrc);
				oneBlogItem.setOriginalUrl(url);
				oneBlogItem.setGlobal(true);
				oneBlogItem.setWebUrl(getShortUrl(httpUtil, url));
				oneBlogItem.setContent(content);
				// oneBlogItem.setId(id++);
				oneBlogItem.setAuthor("");
				aapService.saveBlog(oneBlogItem, null, PostLocationType.Global, 0L);

			}
		}
		System.out.println("Total Urls = " + allLinks.size());
		webDriverForPage.close();
		webDriver.close();
	}

	private static String getShortUrl(HttpUtil httpUtil, String longUrl) throws Exception{
		HttpClient httpClient = new DefaultHttpClient();
		System.out.println("Long url = " + longUrl);
		String encodedUrl = URLEncoder.encode(longUrl, "UTF-8");
		System.out.println("encodedUrl url = " + encodedUrl);
		System.out.println("final url = " + urlShortnerUrl + encodedUrl);
		String dayDonationString = httpUtil.getResponse(httpClient,
				urlShortnerUrl + encodedUrl);
		System.out.println("dayDonationString = " + dayDonationString);
		JSONObject jsonObject = new JSONObject(dayDonationString);
		String status = jsonObject.getString("status");
		if ("fail".equals(status)) {
			String errorCode = jsonObject.getString("code");
			if ("error:keyword".equals(errorCode)) {
				throw new RuntimeException(jsonObject.getString("message"));
			} else {
				throw new RuntimeException(jsonObject.getString("message"));
			}
		} else {
			String shortUrl = jsonObject.getString("shorturl");
			return shortUrl;
		}

	}

}
