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
import com.next.aap.web.dto.VideoDto;
import com.next.aap.web.dto.ContentStatus;

@Component
public class VideoItemCacheImpl extends BasItemCacheImpl<VideoDto>  {

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
		List<VideoDto> allVideoDtos = aapService.getAllPublishedVideos();
		logger.info("Total Video : " + allVideoDtos.size());
		for(VideoDto oneVideoDto:allVideoDtos){
			addCacheItem(DEFAULT_LANGUAGE, oneVideoDto, oneVideoDto.getId(), oneVideoDto.isGlobal());
		}
		Map<Long, List<Long>> videoItemMap = aapService.getVideoItemsOfAllAc();
		for(Entry<Long, List<Long>> oneEntry:videoItemMap.entrySet()){
			logger.info("Adding AC Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addAcCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}
		
		videoItemMap = aapService.getVideoItemsOfAllPc();
		for(Entry<Long, List<Long>> oneEntry:videoItemMap.entrySet()){
			logger.info("Adding PC Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addPcCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		videoItemMap = aapService.getVideoItemsOfAllDistrict();
		for(Entry<Long, List<Long>> oneEntry:videoItemMap.entrySet()){
			logger.info("Adding District Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addDistrictCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}
		
		videoItemMap = aapService.getVideoItemsOfAllState();
		for(Entry<Long, List<Long>> oneEntry:videoItemMap.entrySet()){
			logger.info("Adding State Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addStateCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		videoItemMap = aapService.getVideoItemsOfAllCountry();
		for(Entry<Long, List<Long>> oneEntry:videoItemMap.entrySet()){
			logger.info("Adding Country Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addCountryCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		videoItemMap = aapService.getVideoItemsOfAllCountryRegion();
		for(Entry<Long, List<Long>> oneEntry:videoItemMap.entrySet()){
			logger.info("Adding Country Region Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addCountryRegionCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

	}


	@Override
	public void refreshCacheItemsForAc(long acId) {
		List<Long> videoItemIds = aapService.getVideoItemsOfAc(acId);
		addAcCacheItems(acId, videoItemIds);
	}

	@Override
	public void refreshCacheItemsForPc(long pcId) {
		List<Long> videoItemIds = aapService.getVideoItemsOfPc(pcId);
		addPcCacheItems(pcId, videoItemIds);
	}

	@Override
	public void refreshCacheItemsForDistrict(long districtId) {
		List<Long> videoItemIds = aapService.getVideoItemsOfDistrict(districtId);
		addDistrictCacheItems(districtId, videoItemIds);
	}

	@Override
	public void refreshCacheItemsForState(long stateId) {
		List<Long> videoItemIds = aapService.getVideoItemsOfState(stateId);
		addStateCacheItems(stateId, videoItemIds);
	}

	@Override
	public void refreshCacheItemsForGlobal() {
	}

	@Override
	public void refreshCacheItemsForCountry(long countryId) {
		List<Long> videoItemIds = aapService.getVideoItemsOfCountry(countryId);
		addStateCacheItems(countryId, videoItemIds);
	}

	@Override
	public void refreshCacheItemsForCountryRegion(long countryRegionId) {
		List<Long> videoItemIds = aapService.getVideoItemsOfCountryRegion(countryRegionId);
		addStateCacheItems(countryRegionId, videoItemIds);
	}

	@Override
	public void refreshCacheItemsForCountryRegionArea(long countryRegionAreaId) {
		/*
		List<Long> videoItemIds = aapService.getVideoItemsOfCountryRegionArea(countryRegionAreaId);
		addStateCacheItems(countryRegionAreaId, videoItemIds);
		*/
	}

	@Override
	public List<VideoDto> sortCacheItems(List<VideoDto> cacheItemListForLocation) {
		Collections.sort(cacheItemListForLocation, new Comparator<VideoDto>() {

			@Override
			public int compare(VideoDto o2, VideoDto o1) {
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
	public VideoDto getCacheItemFromDbById(Long id) {
		VideoDto videoDto = aapService.getVideoById(id);
		if(videoDto.getContentStatus().equals(ContentStatus.Published)){
			return videoDto;	
		}
		return null;
	}

	

}
