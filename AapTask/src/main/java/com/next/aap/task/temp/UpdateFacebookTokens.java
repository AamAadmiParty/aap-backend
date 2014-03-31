package com.next.aap.task.temp;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.core.service.HttpUtil;
import com.next.aap.web.dto.FacebookAppPermissionDto;

@Component
public class UpdateFacebookTokens implements Callable<Boolean>{

	String url = "https://graph.facebook.com/oauth/access_token_info?client_id=518296124902946&access_token=CAAHXYzhpziIBAMa3C9nwwvCw93IRcZBM5gZA50E9MgNl1hQzGs35zwtwJVbx1gz0bSQf4lOv4UhjuQvJRwdmNKUW1YhxM0jtpqmYp15ND31VxieGMyy3BZBuBcn2misZB9x8XdtrYp8uXZAVNFZBgxZA8eA4whVcdSQFn5aYIoS1sX2dmEIHiZBInZBxfqtokq1sZD";
	String baseUrl = "https://graph.facebook.com/oauth/access_token_info?client_id=518296124902946&access_token=";
	
	@Autowired
	private AapService aapService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier(value="facebookTimeLinePostThreadPool")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	
	//@PostConstruct
	public void runTask(){
		//threadPoolTaskExecutor.submit(this);
	}
	
	@Override
	public Boolean call() throws Exception {
		logger.info("Start UpdateFacebookTokens Task");
		try{
			Long startId = 0L;
			int pageSize = 50;
			Date expiryTime;
			while(true){
				logger.info("Getting FacebookAppPermissionDto startId = "+startId+", pageSize = "+pageSize);
				List<FacebookAppPermissionDto> allFacebookAppPermissions = aapService.getAllFacebookAppPermissions(startId, pageSize);
				if(allFacebookAppPermissions == null || allFacebookAppPermissions.isEmpty()){
					break;
				}
				for(FacebookAppPermissionDto oneFacebookAppPermissionDto:allFacebookAppPermissions){
					expiryTime = getExpiryTime(oneFacebookAppPermissionDto);
					if(expiryTime != null){
						logger.info("Updating expiry time of appPermission"+oneFacebookAppPermissionDto.getId()+" to = "+expiryTime);
						aapService.updateFacebookAppPermissionExpiryTime(oneFacebookAppPermissionDto.getId(), expiryTime);
					}else{
						logger.info("Expiry time is null");
					}
					startId = oneFacebookAppPermissionDto.getId();
				}
				
				
			}
			
		}catch(Exception ex){
			logger.error("Unable to create Location campaign", ex);
		}
		return true;
	}
	
	private Date getExpiryTime(FacebookAppPermissionDto facebookAppPermissionDto){
		HttpUtil httpUtil = new HttpUtil();
		String finalUrl = baseUrl + facebookAppPermissionDto.getToken();
		try {
			logger.info("Hiting Url "+finalUrl);
			String data = httpUtil.getResponse(new DefaultHttpClient(), finalUrl);
			logger.info("Got Response "+data);
			JSONObject jsonObject = new JSONObject(data);
			int expiresIn = jsonObject.getInt("expires_in");
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND, expiresIn);
			return calendar.getTime();
			
		} catch (ClientProtocolException e) {
			logger.error("Unable to get Facebook token info", e);
		} catch (IOException e) {
			logger.error("Unable to get Facebook token info", e);
		} catch (Exception e) {
			logger.error("Unable to get Facebook token info", e);
		}
		return null;
	}
}
