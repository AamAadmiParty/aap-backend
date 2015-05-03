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
import com.next.aap.web.dto.ContentStatus;
import com.next.aap.web.dto.NewsDto;

@Component
public class NewsItemCacheImpl extends BasItemCacheImpl<NewsDto>  {

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
        clearAllCache();
		List<NewsDto> allNewsDtos = aapService.getAllPublishedNews();
        logger.info("Total News : " + allNewsDtos.size());
		for(NewsDto oneNewsDto:allNewsDtos){
			addCacheItem(DEFAULT_LANGUAGE, oneNewsDto, oneNewsDto.getId(), oneNewsDto.isGlobal());
		}
		Map<Long, List<Long>> newsItemMap = aapService.getNewsItemsOfAllAc();
		for(Entry<Long, List<Long>> oneEntry:newsItemMap.entrySet()){
			logger.info("Adding AC Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addAcCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}
		
		newsItemMap = aapService.getNewsItemsOfAllPc();
		for(Entry<Long, List<Long>> oneEntry:newsItemMap.entrySet()){
			logger.info("Adding PC Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addPcCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		newsItemMap = aapService.getNewsItemsOfAllDistrict();
		for(Entry<Long, List<Long>> oneEntry:newsItemMap.entrySet()){
			logger.info("Adding District Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addDistrictCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}
		
		newsItemMap = aapService.getNewsItemsOfAllState();
		for(Entry<Long, List<Long>> oneEntry:newsItemMap.entrySet()){
			logger.info("Adding State Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addStateCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		newsItemMap = aapService.getNewsItemsOfAllCountry();
		for(Entry<Long, List<Long>> oneEntry:newsItemMap.entrySet()){
			logger.info("Adding Country Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addCountryCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		newsItemMap = aapService.getNewsItemsOfAllCountryRegion();
		for(Entry<Long, List<Long>> oneEntry:newsItemMap.entrySet()){
			logger.info("Adding Country Region Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addCountryRegionCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

	}


	@Override
	public void refreshCacheItemsForAc(long acId) {
		List<Long> newsItemIds = aapService.getNewsItemsOfAc(acId);
		addAcCacheItems(acId, newsItemIds);
	}

	@Override
	public void refreshCacheItemsForPc(long pcId) {
		List<Long> newsItemIds = aapService.getNewsItemsOfPc(pcId);
		addPcCacheItems(pcId, newsItemIds);
	}

	@Override
	public void refreshCacheItemsForDistrict(long districtId) {
		List<Long> newsItemIds = aapService.getNewsItemsOfDistrict(districtId);
		addDistrictCacheItems(districtId, newsItemIds);
	}

	@Override
	public void refreshCacheItemsForState(long stateId) {
		List<Long> newsItemIds = aapService.getNewsItemsOfState(stateId);
		addStateCacheItems(stateId, newsItemIds);
	}

	@Override
	public void refreshCacheItemsForGlobal() {
	}

	@Override
	public void refreshCacheItemsForCountry(long countryId) {
		List<Long> newsItemIds = aapService.getNewsItemsOfCountry(countryId);
		addStateCacheItems(countryId, newsItemIds);
	}

	@Override
	public void refreshCacheItemsForCountryRegion(long countryRegionId) {
		List<Long> newsItemIds = aapService.getNewsItemsOfCountryRegion(countryRegionId);
		addStateCacheItems(countryRegionId, newsItemIds);
	}

	@Override
	public void refreshCacheItemsForCountryRegionArea(long countryRegionAreaId) {
		/*
		List<Long> newsItemIds = aapService.getNewsItemsOfCountryRegionArea(countryRegionAreaId);
		addStateCacheItems(countryRegionAreaId, newsItemIds);
		*/
	}

	@Override
	public List<NewsDto> sortCacheItems(List<NewsDto> cacheItemListForLocation) {
		Collections.sort(cacheItemListForLocation, new Comparator<NewsDto>() {

			@Override
			public int compare(NewsDto o2, NewsDto o1) {
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
	public NewsDto getCacheItemFromDbById(Long id) {
		NewsDto newsDto = aapService.getNewsById(id);
		if(newsDto.getContentStatus().equals(ContentStatus.Published)){
			return newsDto;	
		}
		return null;
	}

	

}
