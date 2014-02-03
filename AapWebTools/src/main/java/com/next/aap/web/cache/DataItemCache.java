package com.next.aap.web.cache;

import com.next.aap.web.ItemList;

public interface DataItemCache<T> {

	public abstract ItemList<T> getItemsFromCache(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId);
	
	public abstract ItemList<T> getItemsFromCache(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber);

	public abstract ItemList<T> getNriItemsFromCache(String lang, long livingCountryId, long livingCountryRegionId, long votingAcId, long votingPcId);
	
	public abstract ItemList<T> getNriItemsFromCache(String lang, long livingCountryId, long livingCountryRegionId, long votingAcId, long votingPcId, int pageNumber);

	public abstract void refreshCacheItemsForAc(long acId);
	
	public abstract void refreshCacheItemsForPc(long pcId);
	
	public abstract void refreshCacheItemsForDistrict(long districtId);
	
	public abstract void refreshCacheItemsForState(long stateId);
	
	public abstract void refreshCacheItemsForAcNational();
	
	public abstract void refreshCacheItemsForCountry(long countryId);
	
	public abstract void refreshCacheItemsForCountryRegion(long countryRegionId);
	
	public abstract void refreshCacheItemsForCountryRegionArea(long countryRegionAreaId);

}
