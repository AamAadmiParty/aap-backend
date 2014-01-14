package com.next.aap.core.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpUtil {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public  String getResponse(HttpClient httpClient, String url) throws ClientProtocolException, IOException{
		logger.info("Hitting Url = {}", url);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		//System.out.println("Got Response= "+ httpResponse);
		HttpEntity httpEntity = httpResponse.getEntity();
		//System.out.println("Converting to String= "+ httpEntity);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		//System.out.println("IOUtils.copy(httpEntity.getContent(), byteArrayOutputStream);");
		IOUtils.copy(httpEntity.getContent(), byteArrayOutputStream);
		String dayDonationString = byteArrayOutputStream.toString();
		return dayDonationString;
	}
	
	public  void saveHttpImage(HttpClient httpClient, String url,String filePath) throws ClientProtocolException, IOException{
		logger.info("Hitting Url = {}", url);
		logger.info("ans saving to  = {}", filePath);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		//System.out.println("Got Response= "+ httpResponse);
		HttpEntity httpEntity = httpResponse.getEntity();
		//System.out.println("Converting to String= "+ httpEntity);
		FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
		//System.out.println("IOUtils.copy(httpEntity.getContent(), byteArrayOutputStream);");
		IOUtils.copy(httpEntity.getContent(), fileOutputStream);
		fileOutputStream.close();
	}
}
