package com.next.aap.task.donation;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.next.aap.cache.CacheKeyService;
import com.next.aap.cache.CacheService;
import com.next.aap.core.service.AapService;
import com.next.aap.task.util.AwsQueueListener;
import com.next.aap.web.dto.GlobalCampaignDto;

public class DonationMemcacheRefreshListener extends AwsQueueListener{

	@Autowired
	public DonationMemcacheRefreshListener(@Value("${refresh_memcache_donation_info_queue:https://sqs.us-east-1.amazonaws.com/365163509554/RefreshMemcacheQueue}") String queueName,@Value("${aws_access_key}") String accessKey
			, @Value("${aws_access_secret}") String accessSecret) {
		super(queueName, accessKey, accessSecret);
	}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapService aapService;
	@Autowired
	private CacheService cacheService;
	
	@Override
	public void onQueueMessage(String jsonMessage) {
		try{
			logger.info("onQueueMessage "+ jsonMessage);
			JSONObject jsonObject = new JSONObject(jsonMessage);
			String data = jsonObject.getString("Subject");
			if(data.equalsIgnoreCase("Global")){
				List<GlobalCampaignDto> allGlobalCampaigns = aapService.getGlobalCampaigns();
				for(GlobalCampaignDto oneGlobalCampaign:allGlobalCampaigns){
					String key = CacheKeyService.createGlobalCampaignKey(oneGlobalCampaign.getCampaignId());
					//cacheService.saveData(key, value);
				}
				
			}
		}catch(Exception ex){
			logger.error("Unable to import Donation"+ jsonMessage,ex);
		}
		
		
		
	}
	

}
