package com.next.aap.core.persistance.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.Video;
import com.next.aap.core.persistance.dao.VideoDao;
import com.next.aap.web.dto.ContentStatus;

@Repository
public class VideoDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Video> implements VideoDao{


	@Override
	public Video saveVideo(Video video) {
		checkIfStringMissing("YoutubeVideoId", video.getYoutubeVideoId());
		saveObject(video);
		return video;
	}

	@Override
	public void deleteVideo(Video video) {
		deleteObject(video);
	}

	@Override
	public Video getVideoById(Long id) {
		return (Video)getObjectById(Video.class, id);
	}

	@Override
	public List<Video> getAllVideos(int totalItems, int pageNumber) {
		String query = "from Video order by dateCreated desc";
		List<Video> list = executeQueryGetList(query, null, totalItems, pageNumber);
		return list;
	}

	@Override
	public Video getVideoByWebUrl(String webUrl) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("webUrl", webUrl);
		String query = "from Video where webUrl=:webUrl";
		Video dbVideo = executeQueryGetObject(query, params);
		return dbVideo;
	}

	@Override
	public Video getVideoByVideoId(String youtubeVideoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("youtubeVideoId", youtubeVideoId);
		String query = "from Video where youtubeVideoId=:youtubeVideoId";
		Video dbVideo = executeQueryGetObject(query, params);
		return dbVideo;
	}

	@Override
	public List<Video> getAllVideos() {
		String query = "from Video order by publishDate desc";
		List<Video> list = executeQueryGetList(query);
		return list;
	}

	@Override
	public long getLastVideoId() {
		String query = "select max(id) from Video";
		Long maxId = executeQueryGetLong(query);
		if(maxId == null){
			return 0;
		}
		return maxId;
	}

	@Override
	public List<Video> getVideoItemsAfterId(long videoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lastId", videoId);
		String query = "from Video where id > :lastId order by id asc";
		List<Video> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public List<Long> getVideoByLocation(long acId, long districtId,
			long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acId", acId);
		params.put("districtId", districtId);
		params.put("stateId", stateId);
		
		String query = "select videolist.videoId from ((select video_id as videoId from video_ac where ac_id = :acId) " +
				"union (select video_id as videoId from video_district where district_id= :districtId) " +
				"union (select video_id as videoId from video_state where state_id= :stateId) " +
				"union (select id as videoId from videos where global_allowed= true)) videolist order by videolist.videoId desc";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}

	@Override
	public List<Long> getVideoByLocation(long pcId, long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pcId", pcId);
		params.put("stateId", stateId);
		
		String query = "select videolist.videoId from ((select video_id as videoId from video_pc where pc_id = :pcId) " +
				"union (select video_id as videoId from video_state where state_id= :stateId) " +
				"union (select id as videoId from videos where global_allowed= true)) videolist order by videolist.videoId desc";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;
	}
	@Override
	public List<Video> getAllPublishedVideos() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contentStatus", ContentStatus.Published);
		
		String query = "from Video where contentStatus = :contentStatus order by publishDate desc";
		List<Video> list = executeQueryGetList(query, params);
		return list;
	}

	@Override
	public List<Long> getAllVideoByAc(long acId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acId", acId);
		String query = "select video_id from video_ac where ac_id = :acId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllVideoByPc(long pcId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pcId", pcId);
		String query = "select video_id from video_pc where pc_id = :pcId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllVideoByDistrict(long districtId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("districtId", districtId);
		String query = "select video_id from video_district where district_id = :districtId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllVideoByState(long stateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stateId", stateId);
		String query = "select video_id from video_state where state_id = :stateId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllVideoByCountry(long countryId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countryId", countryId);
		String query = "select video_id from video_country where country_id = :countryId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	@Override
	public List<Long> getAllVideoByCountryRegion(long countryRegionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countryRegionId", countryRegionId);
		String query = "select video_id from video_country_region where country_region_id = :countryRegionId";
		List<Long> list = executeSqlQueryGetListOfLong(query, params);
		return list;	
	}

	private Map<Long, List<Long>> getVideoByLocationMapFromQuery(String query){
		List results = executeSqlQueryGetResultList(query);
		Map<Long, List<Long>> returnMap = new HashMap<>();
		Long acId;
		Long videoId;
		List<Long> videoIdList;
        for(ListIterator iter = results.listIterator(); iter.hasNext(); ) {
        	Object[] row = (Object[])iter.next();
        	if(row[0] instanceof BigInteger){
        		acId = ((BigInteger)row[0]).longValue();
            	videoId = ((BigInteger)row[1]).longValue();
        	}else{
        		acId = (Long)row[0];
            	videoId = (Long)row[1];
        	}
        	

        	videoIdList = returnMap.get(acId);
        	if(videoIdList == null){
        		videoIdList = new ArrayList<>();
        		returnMap.put(acId, videoIdList);
        	}
        	videoIdList.add(videoId);
        }
        return returnMap;
	}
	@Override
	public Map<Long, List<Long>> getVideoItemsOfAllAc() {
		String query = "select ac_id, video_id from video_ac";
		return getVideoByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getVideoItemsOfAllPc() {
		String query = "select pc_id, video_id from video_pc";
		return getVideoByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getVideoItemsOfAllDistrict() {
		String query = "select district_id, video_id from video_district";
		return getVideoByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getVideoItemsOfAllState() {
		String query = "select state_id, video_id from video_state";
		return getVideoByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getVideoItemsOfAllCountry() {
		String query = "select country_id, video_id from video_country";
		return getVideoByLocationMapFromQuery(query);	
	}

	@Override
	public Map<Long, List<Long>> getVideoItemsOfAllCountryRegion() {
		String query = "select country_region_id, video_id from video_country_region";
		return getVideoByLocationMapFromQuery(query);	
	}

}
