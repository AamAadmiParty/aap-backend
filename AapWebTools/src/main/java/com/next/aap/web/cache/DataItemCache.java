package com.next.aap.web.cache;

import com.next.aap.web.ItemList;

/**
 * For implemntaion wise, 
 * 	it shud be made thread safe
 * 	all refresh function shud be async, that from any where we will call the method and it will return instantaneously
 *  with in implemneation we must make sure that only on call will run at a time and others will stay in queue.
 *  So basically lets create one ThreadPool with 1 thread only, each call will put one runnbae into this threadPool 
 *  and oce thread becomes available it will call it and refresh the cache.
 *  
 *   WE DO NOT want N numbers of thread refreshing the cache.
 * 
 * @author ravi
 *
 * @param <T>
 */
public interface DataItemCache<T> {

	/**
	 * This function will return a List of T type Items from Cache with page number and pageSize of a particular language
	 * @param lang
	 * @param livingAcId
	 * @param votingAcId
	 * @param livingPcId
	 * @param votingPcId
	 * @return
	 */
	public abstract ItemList<T> getItemsFromCache(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId);
	
	/**
	 * This function will return a List of T type Items from Cache with page number and pageSize of a particular language and pageNumber
	 * @param lang
	 * @param livingAcId
	 * @param votingAcId
	 * @param livingPcId
	 * @param votingPcId
	 * @param pageNumber
	 * @return
	 */
	public abstract ItemList<T> getItemsFromCache(String lang, long livingAcId, long votingAcId, long livingPcId, long votingPcId, int pageNumber);

	/**
	 * NRI Location structure is different then Indian structure, so creating special functions for NRI, its similar to getItemsFromCache
	 * but works for NRI
	 * @param lang
	 * @param livingCountryId
	 * @param livingCountryRegionId
	 * @param votingAcId
	 * @param votingPcId
	 * @return
	 */
	public abstract ItemList<T> getNriItemsFromCache(String lang, long livingCountryId, long livingCountryRegionId, long votingAcId, long votingPcId);
	
	/**
	 * NRI Location structure is different then Indian structure, so creating special functions for NRI, its similar to getItemsFromCache
	 * but works for NRI
	 * 
	 * @param lang
	 * @param livingCountryId
	 * @param livingCountryRegionId
	 * @param votingAcId
	 * @param votingPcId
	 * @param pageNumber
	 * @return
	 */
	public abstract ItemList<T> getNriItemsFromCache(String lang, long livingCountryId, long livingCountryRegionId, long votingAcId, long votingPcId, int pageNumber);

	/**
	 * This function will refresh cache for a given assembly Constituency on Demand 
	 * @param acId
	 */
	public abstract void refreshCacheItemsForAc(long acId);

	/**
	 * This function will refresh cache for a given Parliament Constituency on Demand
	 * @param pcId
	 */
	public abstract void refreshCacheItemsForPc(long pcId);
	
	/**
	 * This function will refresh cache for a given District on Demand
	 * @param districtId
	 */
	public abstract void refreshCacheItemsForDistrict(long districtId);
	
	/**
	 * This function will refresh cache for a given State on Demand
	 * @param stateId
	 */
	public abstract void refreshCacheItemsForState(long stateId);
	
	/**
	 * This function will refresh cache for all national Items on Demand
	 */
	public abstract void refreshCacheItemsForAcNational();
	
	/**
	 * This function will refresh cache for a given country on Demand
	 * @param countryId
	 */
	public abstract void refreshCacheItemsForCountry(long countryId);
	
	/**
	 * This function will refresh cache for a given Country Region on Demand
	 * @param countryRegionId
	 */
	public abstract void refreshCacheItemsForCountryRegion(long countryRegionId);
	
	/**
	 * This function will refresh cache for a given Country Region Area on Demand
	 * @param countryRegionAreaId
	 */
	public abstract void refreshCacheItemsForCountryRegionArea(long countryRegionAreaId);
	
	
	/**
	 * This function will refresh all cache and shud be called from postConstruct method of spring bean to intialize it
	 * @param countryRegionAreaId
	 */
	public abstract void refreshFullCache(long countryRegionAreaId);
	

}
