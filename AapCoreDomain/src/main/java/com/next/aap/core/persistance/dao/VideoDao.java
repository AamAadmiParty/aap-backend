package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Video;

public interface VideoDao {

	public abstract Video saveVideo(Video video);
	
	public abstract void deleteVideo(Video video);
	
	public abstract Video getVideoById(Long id);
	
	public abstract List<Video> getAllVideos(int totalItems, int pageNumber);
	
	public abstract List<Video> getAllVideos();
	
	public abstract Video getVideoByWebUrl(String webUrl);
	
	public abstract Video getVideoByVideoId(String videoId);
	
	public abstract long getLastVideoId();
	
	public abstract List<Video> getVideoItemsAfterId(long videoId);

	public abstract List<Long> getVideoByLocation(long acId, long districtId, long stateId);
	
	public abstract List<Long> getVideoByLocation(long pcId, long stateId);

}
