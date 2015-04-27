package com.next.aap.core.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.extensions.Rating;
import com.google.gdata.data.geo.impl.GeoRssWhere;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaPlayer;
import com.google.gdata.data.media.mediarss.MediaThumbnail;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaContent;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeMediaRating;
import com.google.gdata.data.youtube.YtPublicationState;
import com.google.gdata.data.youtube.YtStatistics;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.VideoDto;

@Component
public class VideoDownloader{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private YouTubeService service;
	
	@Autowired
	private AapService aapService;
	
	
	@PostConstruct
	public void init(){
		service = new YouTubeService("aap",
				"AI39si6Rn3DP4YqgemsYsYjHYclvu64fznEc99gY0i8nksLdZ8GrXQl8rCSu4YxPY3UGeockCBcCM1QV0sfZGRGCtadY4IefpQ");
	}
	// http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger
	// http://freshgroundjava.blogspot.in/2012/07/spring-scheduled-tasks-cron-expression.html
	// ss mm hh dd
	public boolean refreshVideoList() {
        logger.info("Downloading Swaraj Samwad Videos");
        String channelIds[] = { "UCYm-AJyEXXAWOrw_qp0XQDw" };
		boolean newVideos = false;
		try{
			
			for(String oneChannel:channelIds){
				logger.info("oneChannel = "+oneChannel);
				newVideos = newVideos || downloadVideosOfChannel(oneChannel);
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return newVideos;
	}
	private boolean downloadVideosOfChannel(String channelId) throws Exception{
		List<VideoEntry> allVideos = new ArrayList<VideoEntry>();
		int maxResult = 25;
		int startIndex = 1;
		while(true){
			logger.info("StartIndex = "+startIndex+", MaxResults="+maxResult);
			String feedUrl = "http://gdata.youtube.com/feeds/api/users/"+channelId+"/uploads?start-index="+startIndex+"&max-results="+maxResult;
			logger.info("feedUrl = "+feedUrl);
			VideoFeed videoFeed = service.getFeed(new URL(feedUrl), VideoFeed.class);
			if(videoFeed.getEntries() == null || videoFeed.getEntries().size() == 0){
				break;
			}
			allVideos.addAll(videoFeed.getEntries());
			startIndex = startIndex + videoFeed.getEntries().size();
			
		}
		return downloadAndSaveVideo(allVideos, channelId);
		
	}

	public boolean downloadAndSaveVideo(List<VideoEntry> allVideos, String channelId) {
		boolean newVideoAvailable = false;
		VideoDto videoItem;
		VideoDto existingVideoDto;
		for (int i = allVideos.size() - 1; i >= 0; i--) {
			VideoEntry videoEntry = allVideos.get(i);
			YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();
			// printVideoEntry(videoEntry, detailed, count);
			existingVideoDto = aapService.getVideoByVideoId(mediaGroup.getVideoId());
			videoItem = new VideoDto();
			if(existingVideoDto != null){
				videoItem.setId(existingVideoDto.getId());
				continue;
			}else{
				newVideoAvailable = true;
			}
			Date date = new Date(videoEntry.getPublished().getValue());
			videoItem.setPublishDate(date);
			videoItem.setDescription(mediaGroup.getDescription()
					.getPlainTextContent());
			videoItem.setGlobal(true);
			videoItem.setImageUrl("http://i1.ytimg.com/vi/"
					+ mediaGroup.getVideoId() + "/mqdefault.jpg");
			videoItem.setYoutubeVideoId(mediaGroup.getVideoId());
			videoItem.setTitle(videoEntry.getTitle().getPlainText());
			videoItem.setChannelId(channelId);
			videoItem.setWebUrl("http://www.youtube.com/watch?v="
					+ mediaGroup.getVideoId());
			logger.info("Saving Youtube Video : "+ videoItem.getTitle()+", Video Id = "+videoItem.getYoutubeVideoId());
			videoItem = aapService.saveVideo(videoItem);
			aapService.publishVideo(videoItem.getId());
		}
		return newVideoAvailable;
	}

	public void printVideoEntry(VideoEntry videoEntry, boolean detailed,
			int count) {
		Calendar calendar = new GregorianCalendar(
				TimeZone.getTimeZone("Asia/Kolkata"));
		Date date = new Date(videoEntry.getPublished().getValue());
		calendar.setTimeInMillis(videoEntry.getPublished().getValue());
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		System.out.println(TimeZone.getTimeZone("Asia/Kolkata"));
		System.out.println("Title: " + count + " , "
				+ videoEntry.getTitle().getPlainText() + ","
				+ videoEntry.getPublished() + "," + date + ","
				+ calendar.getTime());

		if (videoEntry.isDraft()) {
			System.out.println("Video is not live");
			YtPublicationState pubState = videoEntry.getPublicationState();
			if (pubState.getState() == YtPublicationState.State.PROCESSING) {
				System.out.println("Video is still being processed.");
			} else if (pubState.getState() == YtPublicationState.State.REJECTED) {
				System.out.print("Video has been rejected because: ");
				System.out.println(pubState.getDescription());
				System.out.print("For help visit: ");
				System.out.println(pubState.getHelpUrl());
			} else if (pubState.getState() == YtPublicationState.State.FAILED) {
				System.out.print("Video failed uploading because: ");
				System.out.println(pubState.getDescription());
				System.out.print("For help visit: ");
				System.out.println(pubState.getHelpUrl());
			}
		}

		if (videoEntry.getEditLink() != null) {
			System.out.println("Video is editable by current user.");
		}

		if (detailed) {

			YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

			System.out.println("Uploaded by: " + mediaGroup.getUploader());

			System.out.println("Video ID: " + mediaGroup.getVideoId());
			System.out.println("Description: "
					+ mediaGroup.getDescription().getPlainTextContent());

			MediaPlayer mediaPlayer = mediaGroup.getPlayer();
			System.out.println("Web Player URL: " + mediaPlayer.getUrl());
			MediaKeywords keywords = mediaGroup.getKeywords();
			System.out.print("Keywords: ");
			for (String keyword : keywords.getKeywords()) {
				System.out.print(keyword + ",");
			}

			GeoRssWhere location = videoEntry.getGeoCoordinates();
			if (location != null) {
				System.out.println("Latitude: " + location.getLatitude());
				System.out.println("Longitude: " + location.getLongitude());
			}

			Rating rating = videoEntry.getRating();
			if (rating != null) {
				System.out.println("Average rating: " + rating.getAverage());
			}

			YtStatistics stats = videoEntry.getStatistics();
			if (stats != null) {
				System.out.println("View count: " + stats.getViewCount());
			}
			System.out.println();

			System.out.println("\tThumbnails:");
			for (MediaThumbnail mediaThumbnail : mediaGroup.getThumbnails()) {
				System.out.println("\t\tThumbnail URL: "
						+ mediaThumbnail.getUrl());
				System.out.println("\t\tThumbnail Time Index: "
						+ mediaThumbnail.getTime());
				System.out.println();
			}

			System.out.println("\tMedia:");
			for (YouTubeMediaContent mediaContent : mediaGroup
					.getYouTubeContents()) {
				System.out.println("\t\tMedia Location: "
						+ mediaContent.getUrl());
				System.out.println("\t\tMedia Type: " + mediaContent.getType());
				System.out.println("\t\tDuration: "
						+ mediaContent.getDuration());
				System.out.println();
			}

			for (YouTubeMediaRating mediaRating : mediaGroup
					.getYouTubeRatings()) {
				System.out
						.println("Video restricted in the following countries: "
								+ mediaRating.getCountries().toString());
			}
		}
	}
}
