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
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.ContentStatus;

@Component
public class BlogItemCacheImpl extends BasItemCacheImpl<BlogDto>  {

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
		List<BlogDto> allBlogDtos = aapService.getAllPublishedBlogs();
		logger.info("Total Blog : " + allBlogDtos.size());
		for(BlogDto oneBlogDto:allBlogDtos){
			addCacheItem(DEFAULT_LANGUAGE, oneBlogDto, oneBlogDto.getId(), oneBlogDto.isGlobal());
		}
		Map<Long, List<Long>> blogItemMap = aapService.getBlogItemsOfAllAc();
		for(Entry<Long, List<Long>> oneEntry:blogItemMap.entrySet()){
			logger.info("Adding AC Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addAcCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}
		
		blogItemMap = aapService.getBlogItemsOfAllPc();
		for(Entry<Long, List<Long>> oneEntry:blogItemMap.entrySet()){
			logger.info("Adding PC Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addPcCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		blogItemMap = aapService.getBlogItemsOfAllDistrict();
		for(Entry<Long, List<Long>> oneEntry:blogItemMap.entrySet()){
			logger.info("Adding District Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addDistrictCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}
		
		blogItemMap = aapService.getBlogItemsOfAllState();
		for(Entry<Long, List<Long>> oneEntry:blogItemMap.entrySet()){
			logger.info("Adding State Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addStateCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		blogItemMap = aapService.getBlogItemsOfAllCountry();
		for(Entry<Long, List<Long>> oneEntry:blogItemMap.entrySet()){
			logger.info("Adding Country Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addCountryCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

		blogItemMap = aapService.getBlogItemsOfAllCountryRegion();
		for(Entry<Long, List<Long>> oneEntry:blogItemMap.entrySet()){
			logger.info("Adding Country Region Item: {} , {} " , oneEntry.getKey(), oneEntry.getValue());
			addCountryRegionCacheItems(oneEntry.getKey(), oneEntry.getValue());
		}

	}


	@Override
	public void refreshCacheItemsForAc(long acId) {
		List<Long> blogItemIds = aapService.getBlogItemsOfAc(acId);
		addAcCacheItems(acId, blogItemIds);
	}

	@Override
	public void refreshCacheItemsForPc(long pcId) {
		List<Long> blogItemIds = aapService.getBlogItemsOfPc(pcId);
		addPcCacheItems(pcId, blogItemIds);
	}

	@Override
	public void refreshCacheItemsForDistrict(long districtId) {
		List<Long> blogItemIds = aapService.getBlogItemsOfDistrict(districtId);
		addDistrictCacheItems(districtId, blogItemIds);
	}

	@Override
	public void refreshCacheItemsForState(long stateId) {
		List<Long> blogItemIds = aapService.getBlogItemsOfState(stateId);
		addStateCacheItems(stateId, blogItemIds);
	}

	@Override
	public void refreshCacheItemsForGlobal() {
	}

	@Override
	public void refreshCacheItemsForCountry(long countryId) {
		List<Long> blogItemIds = aapService.getBlogItemsOfCountry(countryId);
		addStateCacheItems(countryId, blogItemIds);
	}

	@Override
	public void refreshCacheItemsForCountryRegion(long countryRegionId) {
		List<Long> blogItemIds = aapService.getBlogItemsOfCountryRegion(countryRegionId);
		addStateCacheItems(countryRegionId, blogItemIds);
	}

	@Override
	public void refreshCacheItemsForCountryRegionArea(long countryRegionAreaId) {
		/*
		List<Long> blogItemIds = aapService.getBlogItemsOfCountryRegionArea(countryRegionAreaId);
		addStateCacheItems(countryRegionAreaId, blogItemIds);
		*/
	}

	@Override
	public List<BlogDto> sortCacheItems(List<BlogDto> cacheItemListForLocation) {
		Collections.sort(cacheItemListForLocation, new Comparator<BlogDto>() {

			@Override
			public int compare(BlogDto o2, BlogDto o1) {
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
	public BlogDto getCacheItemFromDbById(Long id) {
		BlogDto blogDto = aapService.getBlogById(id);
		if(blogDto.getContentStatus().equals(ContentStatus.Published)){
			return blogDto;	
		}
		return null;
	}

	

}
