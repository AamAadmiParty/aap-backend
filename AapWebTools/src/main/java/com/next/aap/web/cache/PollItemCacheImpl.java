package com.next.aap.web.cache;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.PollQuestionDto;
import com.next.aap.web.dto.ContentStatus;

@Component
public class PollItemCacheImpl extends BasItemCacheImpl<PollQuestionDto>  {

	@Autowired
	private LocationCacheDbImpl locationCacheDbImpl;
	
	@Autowired
	private AapService aapService;
	
	@PostConstruct
	public void init(){
		refreshFullCache();
	}
	
	@Override
	public void refreshFullCache() {
		List<PollQuestionDto> allPollQuestionDtos = aapService.getAllPublishedPolls();
		logger.info("Total PollQuestion : " + allPollQuestionDtos.size());
		for(PollQuestionDto onePollQuestionDto:allPollQuestionDtos){
			addCacheItem(DEFAULT_LANGUAGE, onePollQuestionDto, onePollQuestionDto.getId(), onePollQuestionDto.isGlobal());
		}
		Map<Long, List<Long>> pollQuestionItemMap = aapService.getPollQuestionItemsOfAllAc();
		for(Entry<Long, List<Long>> oneEntry:pollQuestionItemMap.entrySet()){
			logger.info("Adding AC Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addAcCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}
		
		pollQuestionItemMap = aapService.getPollQuestionItemsOfAllPc();
		for(Entry<Long, List<Long>> oneEntry:pollQuestionItemMap.entrySet()){
			logger.info("Adding PC Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addPcCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		pollQuestionItemMap = aapService.getPollQuestionItemsOfAllDistrict();
		for(Entry<Long, List<Long>> oneEntry:pollQuestionItemMap.entrySet()){
			logger.info("Adding District Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addDistrictCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}
		
		pollQuestionItemMap = aapService.getPollQuestionItemsOfAllState();
		for(Entry<Long, List<Long>> oneEntry:pollQuestionItemMap.entrySet()){
			logger.info("Adding State Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addStateCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		pollQuestionItemMap = aapService.getPollQuestionItemsOfAllCountry();
		for(Entry<Long, List<Long>> oneEntry:pollQuestionItemMap.entrySet()){
			logger.info("Adding Country Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addCountryCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		pollQuestionItemMap = aapService.getPollQuestionItemsOfAllCountryRegion();
		for(Entry<Long, List<Long>> oneEntry:pollQuestionItemMap.entrySet()){
			logger.info("Adding Country Region Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addCountryRegionCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

	}


	@Override
	public void refreshCacheItemsForAc(long acId) {
		List<Long> pollQuestionItemIds = aapService.getPollQuestionItemsOfAc(acId);
		addAcCacheItems(acId, pollQuestionItemIds);
	}

	@Override
	public void refreshCacheItemsForPc(long pcId) {
		List<Long> pollQuestionItemIds = aapService.getPollQuestionItemsOfPc(pcId);
		addPcCacheItems(pcId, pollQuestionItemIds);
	}

	@Override
	public void refreshCacheItemsForDistrict(long districtId) {
		List<Long> pollQuestionItemIds = aapService.getPollQuestionItemsOfDistrict(districtId);
		addDistrictCacheItems(districtId, pollQuestionItemIds);
	}

	@Override
	public void refreshCacheItemsForState(long stateId) {
		List<Long> pollQuestionItemIds = aapService.getPollQuestionItemsOfState(stateId);
		addStateCacheItems(stateId, pollQuestionItemIds);
	}

	@Override
	public void refreshCacheItemsForGlobal() {
	}

	@Override
	public void refreshCacheItemsForCountry(long countryId) {
		List<Long> pollQuestionItemIds = aapService.getPollQuestionItemsOfCountry(countryId);
		addStateCacheItems(countryId, pollQuestionItemIds);
	}

	@Override
	public void refreshCacheItemsForCountryRegion(long countryRegionId) {
		List<Long> pollQuestionItemIds = aapService.getPollQuestionItemsOfCountryRegion(countryRegionId);
		addStateCacheItems(countryRegionId, pollQuestionItemIds);
	}

	@Override
	public void refreshCacheItemsForCountryRegionArea(long countryRegionAreaId) {
		/*
		List<Long> pollQuestionItemIds = aapService.getPollQuestionItemsOfCountryRegionArea(countryRegionAreaId);
		addStateCacheItems(countryRegionAreaId, pollQuestionItemIds);
		*/
	}

	@Override
	public List<PollQuestionDto> sortCacheItems(List<PollQuestionDto> cacheItemListForLocation) {
		Collections.sort(cacheItemListForLocation, new Comparator<PollQuestionDto>() {

			@Override
			public int compare(PollQuestionDto o2, PollQuestionDto o1) {
				if(o1.getPublishDate() != null && o2.getPublishDate() != null){
					return o1.getPublishDate().compareTo(o2.getPublishDate());
				}
				if(o1.getDateCreated() != null && o2.getDateCreated() != null){
					return o1.getDateCreated().compareTo(o2.getDateCreated());
				}
				return 0;
			}
		});
		return cacheItemListForLocation;
	}


	@Override
	public PollQuestionDto getCacheItemFromDbById(Long id) {
		PollQuestionDto pollQuestionDto = aapService.getPollQuestionById(id);
		if(pollQuestionDto.getContentStatus().equals(ContentStatus.Published)){
			return pollQuestionDto;	
		}
		return null;
	}

	

}
