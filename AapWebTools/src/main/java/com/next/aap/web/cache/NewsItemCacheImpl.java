package com.next.aap.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.ItemList;
import com.next.aap.web.dto.NewsDto;

@Component
public class NewsItemCacheImpl implements DataItemCache<NewsDto> {

	@Autowired
	private LocationCacheDbImpl locationCacheDbImpl;
	
	@Autowired
	private AapService aapService;
	
	@Override
	public void refreshFullCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemList<NewsDto> getItemsFromCache(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, long livingCountryId,
			long livingCountryRegionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemList<NewsDto> getItemsFromCache(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, long livingCountryId,
			long livingCountryRegionId, int pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshCacheItemsForAc(long acId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshCacheItemsForPc(long pcId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshCacheItemsForDistrict(long districtId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshCacheItemsForState(long stateId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshCacheItemsForGlobal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshCacheItemsForCountry(long countryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshCacheItemsForCountryRegion(long countryRegionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshCacheItemsForCountryRegionArea(long countryRegionAreaId) {
		// TODO Auto-generated method stub
		
	}

	

}
