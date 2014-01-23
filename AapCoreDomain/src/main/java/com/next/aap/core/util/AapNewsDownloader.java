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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.core.service.HttpUtil;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.PostLocationType;

@Component
public class AapNewsDownloader {

	private static final String urlShortnerUrl = "http://myaap.in/yourls-api.php?format=json&username=arvind&password=4delhi&action=shorturl&url=";
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AapService aapService;
	
	public static void main(String[] args) throws Exception{
		System.out.println("Opening URL");
		AapNewsDownloader aapNewsDownloader = new AapNewsDownloader();
		aapNewsDownloader.downloadData();
		
	}

	public void downloadData() throws Exception {
		logger.info("Downloading AAP News");
		WebDriver webDriver = new FirefoxDriver();
		List<WebElement> readMoreLinks;
		List<String> allLinks = new ArrayList<String>();
		String url;
		WebElement pageDiv;
		NewsDto oneNewsItem;
		String title;
		String content;
		HttpUtil httpUtil = new HttpUtil();

		WebDriver webDriverForPage = new FirefoxDriver();// new HtmlUnitDriver(true);
		WebDriverWait _wait = new WebDriverWait(webDriver, 10);
		WebDriverWait _wait2 = new WebDriverWait(webDriverForPage, 10);
		NewsDto existingItem = null;
		String listPageUrl;
		for (int i = 57; i >= 0; i--) {
			listPageUrl = "http://www.aamaadmiparty.org/news?page=" + i;
			logger.info("Hitting Url : "+listPageUrl);
			webDriver.get(listPageUrl);	
			
			
			readMoreLinks = webDriver.findElements(By.className("read-more"));
			for (int j = readMoreLinks.size() - 1; j >= 0; j--) {
				WebElement oneWebElement = readMoreLinks.get(j);
				url = oneWebElement.findElement(By.tagName("a")).getAttribute(
						"href");
				allLinks.add(url);
				existingItem = aapService.getNewsByOriginalUrl(url);
				if(existingItem != null){
					logger.info("Already exists "+ url);
					continue;
				}
				logger.info("Hitting News Url : "+ url);
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
				

				oneNewsItem = new NewsDto();
				oneNewsItem.setSource("www.aamaadmiparty.org");
				oneNewsItem.setTitle(title);
				oneNewsItem.setImageUrl(imgSrc);
				oneNewsItem.setOriginalUrl(url);
				oneNewsItem.setWebUrl(getShortUrl(httpUtil, url));
				oneNewsItem.setGlobal(true);
				oneNewsItem.setContent(content);
				// oneNewsItem.setId(id++);
				oneNewsItem.setAuthor("");
				System.out.println("Content = "+content);
				oneNewsItem = aapService.saveNews(oneNewsItem, null, PostLocationType.Global, 0L);
				//News coming from AAP website, just approve it
				aapService.publishNews(oneNewsItem.getId());
				

			}
		}
		logger.info("Total Urls = " + allLinks.size());
		webDriverForPage.close();
		webDriver.close();
	}

	private static String getShortUrl(HttpUtil httpUtil, String longUrl) throws Exception{
		HttpClient httpClient = new DefaultHttpClient();
		String encodedUrl = URLEncoder.encode(longUrl, "UTF-8");
		String dayDonationString = httpUtil.getResponse(httpClient,urlShortnerUrl + encodedUrl);
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
