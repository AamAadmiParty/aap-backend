package com.next.aap.core.persistance.dao;

import java.util.List;
import java.util.Map;

import com.next.aap.core.persistance.Video;

public interface VideoDao {

	public abstract Video saveVideo(Video video);
	
	public abstract void deleteVideo(Video video);
	
	public abstract Video getVideoById(Long id);
	
	public abstract List<Video> getAllVideos(int totalItems, int pageNumber);
	
	public abstract List<Video> getAllVideos();
	
	public abstract List<Video> getAllPublishedVideos();
	
	public abstract Video getVideoByWebUrl(String webUrl);
	
	public abstract Video getVideoByVideoId(String videoId);
	
	public abstract long getLastVideoId();
	
	public abstract List<Video> getVideoItemsAfterId(long videoId);

	public abstract List<Long> getVideoByLocation(long acId, long districtId, long stateId);
	
	public abstract List<Long> getVideoByLocation(long pcId, long stateId);

	
	public abstract List<Long> getAllVideoByAc(long acId);
	
	public abstract List<Long> getAllVideoByPc(long pcId);
	
	public abstract List<Long> getAllVideoByDistrict(long districtId);
	
	public abstract List<Long> getAllVideoByState(long stateId);
	
	public abstract List<Long> getAllVideoByCountry(long countryId);
	
	public abstract List<Long> getAllVideoByCountryRegion(long countryRegionId);
	
	
	public abstract Map<Long, List<Long>> getVideoItemsOfAllAc();
	
	public abstract Map<Long, List<Long>> getVideoItemsOfAllPc();
	
	public abstract Map<Long, List<Long>> getVideoItemsOfAllDistrict();
	
	public abstract Map<Long, List<Long>> getVideoItemsOfAllState();
	
	public abstract Map<Long, List<Long>> getVideoItemsOfAllCountry();
	
	public abstract Map<Long, List<Long>> getVideoItemsOfAllCountryRegion();
}
