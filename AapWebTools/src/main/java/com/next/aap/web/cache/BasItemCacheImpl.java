package com.next.aap.web.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.next.aap.web.ItemList;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.DistrictDto;

public abstract class BasItemCacheImpl<ItemType> implements DataItemCache<ItemType>{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LocationCacheDbImpl locationCacheDbImpl;
	
	public BasItemCacheImpl(int pageSize){
		this.pageSize = pageSize;
	}
	public BasItemCacheImpl(){
		this(10);
	}
	private int pageSize;
	public static final String DEFAULT_LANGUAGE = "en";

	Map<String,Map<Long, ItemType>> cacheByLanguage = new HashMap<>();
	List<Long> allGlobalItemIds = new ArrayList<>();
	Map<Long, List<Long>> acItemDtos = new HashMap<>();
	Map<Long, List<Long>> districtItemDtos = new HashMap<>();
	Map<Long, List<Long>> pcItemDtos = new HashMap<>();
	Map<Long, List<Long>> stateItemDtos = new HashMap<>();
	Map<Long, List<Long>> countryItemDtos = new HashMap<>();
	Map<Long, List<Long>> countryRegionItemDtos = new HashMap<>();
	static Map<Long, Long> longCache = new HashMap<>();
	
    protected void clearAllCache() {
        cacheByLanguage = new HashMap<>();
        allGlobalItemIds = new ArrayList<>();
        acItemDtos = new HashMap<>();
        districtItemDtos = new HashMap<>();
        pcItemDtos = new HashMap<>();

        stateItemDtos = new HashMap<>();
        countryItemDtos = new HashMap<>();
        countryRegionItemDtos = new HashMap<>();
    }
	public void addCacheItem(String language,ItemType newsItem, Long key, boolean global){
		Map<Long, ItemType> languageCacheItems = cacheByLanguage.get(language);
		if(languageCacheItems == null){
			languageCacheItems = new HashMap<>();
			cacheByLanguage.put(language, languageCacheItems);
		}
		languageCacheItems.put(key, newsItem);
		if(global){
			allGlobalItemIds.add(key);	
		}
	}
	public void addAcCacheItems(Long acId,List<Long> itemIds){
		acItemDtos.put(acId, convertToCache(itemIds));
	}
	public void addDistrictCacheItems(Long districtId,List<Long> itemIds){
		districtItemDtos.put(districtId, convertToCache(itemIds));
	}
	public void addPcCacheItems(Long pcId,List<Long> itemIds){
		pcItemDtos.put(pcId, convertToCache(itemIds));
	}
	public void addStateCacheItems(Long stateId,List<Long> itemIds){
		stateItemDtos.put(stateId, convertToCache(itemIds));
	}
	public void addCountryCacheItems(Long countryId,List<Long> itemIds){
		countryItemDtos.put(countryId, convertToCache(itemIds));
	}
	public void addCountryRegionCacheItems(Long countryRegionId,List<Long> itemIds){
		countryRegionItemDtos.put(countryRegionId, convertToCache(itemIds));
	}
	
	@Override
    public ItemList<ItemType> getItemsFromCache(String lang, Long domainStateId, long livingAcId, long votingAcId, long livingPcId, long votingPcId, long livingCountryId,
			long livingCountryRegionId) {
        return getItemsFromCache(lang, domainStateId, livingAcId, votingAcId, livingPcId, votingPcId, livingCountryId, livingCountryRegionId, 1);
	}

	@Override
    public ItemList<ItemType> getItemsFromCache(String lang, Long domainStateId, long livingAcId, long votingAcId, long livingPcId, long votingPcId, long livingCountryId,
			long livingCountryRegionId, int pageNumber) {
        logger.debug("Getting Items for livingAcId=" + livingAcId + ",votingAcId=" + votingAcId + ",livingPcId=" + livingPcId + ",votingPcId" + votingPcId +
				",livingCountryId="+livingCountryId +",livingCountryRegionId="+livingCountryRegionId+",pageNumber="+pageNumber);
        return getItemsFromCache(lang, domainStateId, livingAcId, votingAcId, livingPcId, votingPcId, livingCountryId, livingCountryRegionId, pageNumber, pageSize);
	}

    public ItemList<ItemType> getItemsFromCache(String language, Long domainStateId, Long livingAcId, Long votingAcId, Long livingPcId, Long votingPcId,
			Long livingCountryId,Long livingCountryRegionId, int pageNumber, int pageSize){
		//Make sure the first page number is always 1, i.e. 1,2,3,4 etc
		if(pageNumber <= 0){
			pageNumber = 1;
		}
		int startItemCount = (pageNumber - 1) * pageSize;
		int endItemCount = startItemCount + pageSize;
        // logger.info("startItemCount = {}, endItemCount={}", startItemCount, endItemCount);
		//get all Cache item Ids for user's location
        Set<Long> locationCacheItemIds = getAllCacheItemIdsforLocation(language, domainStateId, livingAcId, votingAcId, livingPcId, votingPcId, livingCountryId, livingCountryRegionId);
		
		//Now create actual cache item List 
		List<ItemType> cacheItemListForLocation = new ArrayList<>(locationCacheItemIds.size());
		int totalPages = locationCacheItemIds.size() / pageSize + 1;
		Iterator<Long> cacheIterator = locationCacheItemIds.iterator();
		Long itemId;
		ItemType item;
		while(cacheIterator.hasNext()){
			itemId = cacheIterator.next();
			item = getCacheItemById(language, itemId);
			if(item != null){
				cacheItemListForLocation.add(item);	
			}
		}
		//Sort Items
		cacheItemListForLocation = sortCacheItems(cacheItemListForLocation);
        // logger.info("cacheItemListForLocation = {}", cacheItemListForLocation);
		List<ItemType> returnList = new ArrayList<>(pageSize);
		
		if(endItemCount >= cacheItemListForLocation.size()){
            endItemCount = cacheItemListForLocation.size();
		}

		for(int i=startItemCount;i<endItemCount;i++){
			returnList.add(cacheItemListForLocation.get(i));
		}
		ItemList<ItemType> returnItemList = new ItemList<ItemType>(returnList);
		returnItemList.setPageNumber(pageNumber);
		returnItemList.setPageSize(pageSize);
		returnItemList.setTotalPages(totalPages);
        // logger.info("returnItemList = {}", returnItemList);
		return returnItemList;
	}
	
	public abstract List<ItemType> sortCacheItems(List<ItemType> cacheItemListForLocation);
	
	@Override
	public ItemType getCacheItemById(Long itemId){
		return getCacheItemById(DEFAULT_LANGUAGE, itemId);
	}

	@Override
	public ItemType getCacheItemById(String language, Long itemId){
		Map<Long, ItemType> cacheItemsByLang = cacheByLanguage.get(language);
		ItemType item = cacheItemsByLang.get(itemId);
		if(item == null){
			if(DEFAULT_LANGUAGE.equals(language)){
				return null;
			}
			item = getCacheItemById(itemId);
			if(item != null){
				cacheItemsByLang.put(itemId, item);
			}
		}
		return item;
	}
	public abstract ItemType getCacheItemFromDbById(Long id);
	
    private Set<Long> getAllCacheItemIdsforLocation(String language, Long domainStateId, Long livingAcId, Long votingAcId, Long livingPcId, Long votingPcId,
			Long livingCountryId,Long livingCountryRegionId){
		Set<Long> returnList = new HashSet<>(allGlobalItemIds.size());
        if (domainStateId == null || domainStateId == 0) {
            returnList.addAll(allGlobalItemIds);
        }

        logger.info("Getting Cache for domainStateId={},votingAcId={},livingAcId={},votingPcId={},livingPcId={},livingCountryId={},livingCountryRegionId={}", domainStateId, votingAcId, livingAcId,
                votingPcId, livingPcId, livingCountryId, livingCountryRegionId);
		addItems(returnList, votingAcId, acItemDtos);
		addItems(returnList, livingAcId, acItemDtos);
		addItems(returnList, votingPcId, pcItemDtos);
		addItems(returnList, livingPcId, pcItemDtos);
		addItems(returnList, livingCountryId, countryItemDtos);
		addItems(returnList, livingCountryRegionId, countryRegionItemDtos);
        addItems(returnList, domainStateId, stateItemDtos);
		addDistrictItems(returnList, votingAcId);
		addDistrictItems(returnList, livingAcId);
        // logger.info("return List : {}", returnList);
		return returnList;
	}
	private void addDistrictItems(Set<Long> returnList,Long acId){
		if(acId == null || acId <= 0 ){
			return;
		}
		AssemblyConstituencyDto ac = locationCacheDbImpl.getAssemblyConstituencyById(acId);
		addItems(returnList, ac.getDistrictId(), districtItemDtos);
		addStateItemsByDistrictIds(returnList, ac.getDistrictId());
	}
	private void addStateItemsByDistrictIds(Set<Long> returnList,Long districtId){
		if(districtId == null || districtId <= 0 ){
			return;
		}
		DistrictDto district = locationCacheDbImpl.getDistrictById(districtId);
		if(district == null){
			logger.warn("District with Id "+districtId+" not found");
			return;
		}
		addItems(returnList, district.getStateId(), stateItemDtos);
	}
	private void addItems(Set<Long> returnList, Long locationId, Map<Long, List<Long>> cacheItemsOfLocation){
        if (locationId != null && locationId > 0) {
			List<Long> ids = cacheItemsOfLocation.get(locationId);
			if(ids != null){
				returnList.addAll(ids);	
			}
		}
		
	}
	private List<Long> convertToCache(List<Long> ids){
		List<Long> returnList = new ArrayList<>(ids.size());
		Long cachedId;
		for(Long oneId:ids){
			cachedId = getCachedLong(oneId);
			returnList.add(cachedId);
		}
		return returnList;
	}
	private Long getCachedLong(Long oneId){
		Long cachedId = longCache.get(oneId);
		if(cachedId == null){
			cachedId = oneId;
			longCache.put(oneId, cachedId);
		}
		return cachedId;

	}
	
}
