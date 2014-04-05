package com.next.aap.task.temp;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.LocationCampaignDto;
import com.next.aap.web.dto.LocationCampaignDto.LocationCampaignType;
import com.next.aap.web.dto.ParliamentConstituencyDto;

public class CreateLocationCampaignIds {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapService aapService;
	
	@PostConstruct
	public void runTask(){
		logger.info("Start Creating Location Campaign Ids");
		try{
			LocationCampaignDto locationCampaign;
			/*
			List<StateDto> allStates = aapService.getAllStates();
			logger.info("Creating State Campaign Ids");
			for(StateDto oneState:allStates){
				locationCampaign = aapService.getDefaultStateLocationCampaign(oneState.getId());
				if(locationCampaign == null){
					locationCampaign = createLocationCampaign("state", oneState.getName(), oneState.getId());
					aapService.saveStateLocationCampaign(locationCampaign, oneState.getId());
				}
			}
			//Create all District Campaigns
			List<DistrictDto> allDistricts = aapService.getAllDistricts();
			for(DistrictDto oneDistrict : allDistricts){
				locationCampaign = aapService.getDefaultDistrictLocationCampaign(oneDistrict.getId());
				if(locationCampaign == null){
					locationCampaign = createLocationCampaign("district", oneDistrict.getName(), oneDistrict.getId());
					aapService.saveDistrictLocationCampaign(locationCampaign, oneDistrict.getId());
				}
			}
			*/
			//Create all PC Campaigns
			List<ParliamentConstituencyDto> allPcs = aapService.getAllParliamentConstituencies();
			for(ParliamentConstituencyDto onePc : allPcs){
				locationCampaign = aapService.getDefaultPcLocationCampaign(onePc.getId());
				if(locationCampaign == null){
					locationCampaign = createLocationCampaign("pc", onePc.getName(), onePc.getId());
					aapService.savePcLocationCampaign(locationCampaign, onePc.getId());
				}
			}
			
			
			//Create all AC Campaigns
			/*
			List<AssemblyConstituencyDto> allAcs = aapService.getAllAssemblyConstituencies();
			for(AssemblyConstituencyDto oneAc : allAcs){
				locationCampaign = aapService.getDefaultAcLocationCampaign(oneAc.getId());
				if(locationCampaign == null){
					locationCampaign = createLocationCampaign("ac", oneAc.getName(), oneAc.getId());
					aapService.saveAcLocationCampaign(locationCampaign, oneAc.getId());
				}
			}
			*/
		}catch(Exception ex){
			logger.error("Unable to create Location campaign", ex);
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
