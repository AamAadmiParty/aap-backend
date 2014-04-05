package com.next.aap.task.donation;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.next.aap.cache.CacheKeyService;
import com.next.aap.cache.CacheService;
import com.next.aap.core.service.AapService;
import com.next.aap.task.util.AwsQueueListener;
import com.next.aap.web.dto.GlobalCampaignDto;
import com.next.aap.web.dto.LocationCampaignDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.LocationCampaignDto.LocationCampaignType;

@Component
public class DonationMemcacheRefreshListener extends AwsQueueListener {

	@Autowired
	public DonationMemcacheRefreshListener(
			@Value("${refresh_memcache_donation_info_queue}") String queueName,
			@Value("${aws_access_key}") String accessKey, @Value("${aws_access_secret}") String accessSecret) {
		super(queueName, accessKey, accessSecret);
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapService aapService;
	@Autowired
	private CacheService cacheService;

	@Override
	public void onQueueMessage(String jsonMessage) {
		try {
			logger.info("onQueueMessage " + jsonMessage);
			JSONObject jsonObject = new JSONObject(jsonMessage);
			String data = jsonObject.getString("Subject");
			if (data.equalsIgnoreCase("Global")) {
				List<GlobalCampaignDto> allGlobalCampaigns = aapService.getGlobalCampaigns();
				for (GlobalCampaignDto oneGlobalCampaign : allGlobalCampaigns) {
					String key = CacheKeyService.createGlobalCampaignKey(oneGlobalCampaign.getCampaignId());
					// cacheService.saveData(key, value);
				}

			}
			if (data.equalsIgnoreCase("AllLocations")) {
				logger.info(new Date().toString());
				logger.info("Starting DonationMemcacheUpdaterTask task");
				try {
					aapService.updateAllLocationCampaignInCache();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(data.equalsIgnoreCase("StateLcid")){
				List<ParliamentConstituencyDto> allPcs = aapService.getAllParliamentConstituencies();
				LocationCampaignDto locationCampaign;
				for(ParliamentConstituencyDto onePc : allPcs){
					locationCampaign = aapService.getDefaultPcLocationCampaign(onePc.getId());
					if(locationCampaign == null){
						locationCampaign = createLocationCampaign("pc", onePc.getName(), onePc.getId());
						aapService.savePcLocationCampaign(locationCampaign, onePc.getId());
					}
				}
			}

		} catch (Exception ex) {
			logger.error("Unable to import Donation" + jsonMessage, ex);
		}
	}
	private LocationCampaignDto createLocationCampaign(String locationType, String location, Long locationId){
		logger.info("Creating State Campaign for  "+locationType+" "+location+", "+locationId);
		LocationCampaignDto locationCampaign = new LocationCampaignDto();
		locationCampaign.setCampaignId("l"+locationType+""+locationId);
		locationCampaign.setDescription("Default campaign for "+locationType+" "+location +", "+ locationId);
		locationCampaign.setTitle("Default campaign for "+locationType+" "+location);
		locationCampaign.setTotalDonation(0.0);
		locationCampaign.setTotalNumberOfDonations(0);
		locationCampaign.setCampaignType(LocationCampaignType.DEFAULT);
		return locationCampaign;
		
	}
}
