package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
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

}
