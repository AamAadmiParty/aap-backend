package com.next.aap.web.cache;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.Analytics.Data.Ga.Get;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.GaData;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.VideoDto;

@Component
public class PopularContentCacheImpl implements PopularContentCache {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapService aapService;
	private String gaServiceAccountEmailId;
	private String gaViewId;
	private File privateKeyFile;
	private String trackingId;
	private Credential credential;
	private Integer maxResults = 100;
	private String sortConfig = "-ga:uniqueEvents";
	private static final String APPLICATION_NAME = "AAP-Analytics/v1";
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private List<BlogDto> blogDtos = new ArrayList<BlogDto>();
	private List<NewsDto> newsDtos = new ArrayList<NewsDto>();
	private List<VideoDto> videoDtos = new ArrayList<VideoDto>();
	private Get gaReader;

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	@Value("${ga.serviceEmailId:709673867506-v2tsss4lvihfbihbt4g89d97psfuoj2q@developer.gserviceaccount.com}")
	public void setGaServiceAccountEmailId(String gaServiceAccountEmailId) {
		this.gaServiceAccountEmailId = gaServiceAccountEmailId;
	}

	@Value("${ga.viewId:ga:81671961}")
	public void setGaViewId(String gaViewId) {
		if (!gaViewId.startsWith("ga")) {
			gaViewId = "ga:" + gaViewId;
		}
		this.gaViewId = gaViewId;
	}

	@Value("${ga.privateKeyFileName:c8aa9a7f75b477b3822f8528ba95f3b3446f2410-privatekey.p12}")
	public void setPrivateKeyFile(File privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}

	@Value("${ga.trackingId:UA-47668243-1}")
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	// Connect to Google Analytics and get analytics every hour
	@Scheduled(cron = "01 01 * * * *")
	public void refreshPopularContent() {
		Assert.notNull(gaReader);
		int gaIndex = 0; // This should correspond to the location of the eventLabel dimension in the dimensions query
		// Get Video Links
		gaReader.setFilters("ga:eventCategory==Videos");
		try {
			GaData gaData = gaReader.execute();
			if (gaData.getRows().size() > 0) {
				List dtos = new ArrayList();
				for (List<String> result : gaData.getRows()) {
					dtos.add(aapService.getVideoByVideoId(result.get(gaIndex)));
				}
				if (dtos.size() > 0) {
					synchronized (videoDtos) {
						videoDtos = dtos;
					}
				}
			}
		} catch (IOException e) {
			logger.error("Error while retrieving the Google Analytics data", e);
		}
		// Get Blog Links
		gaReader.setFilters("ga:eventCategory==Blogs");
		try {
			GaData gaData = gaReader.execute();
			if (gaData.getRows().size() > 0) {
				List dtos = new ArrayList();
				for (List<String> result : gaData.getRows()) {
					dtos.add(aapService.getBlogById(Long.valueOf(result.get(gaIndex))));
				}
				if (dtos.size() > 0) {
					synchronized (blogDtos) {
						blogDtos = dtos;
					}
				}
			}
		} catch (NumberFormatException e) {
			logger.error("Google eventLabel should be a number", e);
		} catch (IOException e) {
			logger.error("Error while retrieving the Google Analytics data", e);
		}
		// Get Blog Links
		gaReader.setFilters("ga:eventCategory==News");
		try {
			GaData gaData = gaReader.execute();
			if (gaData.getRows().size() > 0) {
				List dtos = new ArrayList();
				for (List<String> result : gaData.getRows()) {
					dtos.add(aapService.getNewsById(Long.valueOf(result.get(gaIndex))));
				}
				if (dtos.size() > 0) {
					synchronized (newsDtos) {
						newsDtos = dtos;
					}
				}
			}
		} catch (NumberFormatException e) {
			logger.error("Google eventLabel should be a number", e);
		} catch (IOException e) {
			logger.error("Error while retrieving the Google Analytics data", e);
		}
	}

	@Override
	public List<BlogDto> getPopularBlogs() {
		return blogDtos;
	}

	@Override
	public List<NewsDto> getPopularNews() {
		return newsDtos;
	}

	@Override
	public List<VideoDto> getPopularVideos() {
		return videoDtos;
	}

	@PostConstruct
	public void init() {
		// initialize the transport
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			Set<String> scopes = new HashSet<String>();
			scopes.add(AnalyticsScopes.ANALYTICS_READONLY);
			credential = new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(JSON_FACTORY)
					.setServiceAccountId(gaServiceAccountEmailId).setServiceAccountScopes(scopes)
					.setServiceAccountPrivateKeyFromP12File(privateKeyFile).build();
			Analytics analytics = new Analytics.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
					APPLICATION_NAME).build();
			gaReader = analytics.data().ga().get(gaViewId, "2006-01-01", "2100-12-31", "ga:uniqueEvents");
			gaReader.setDimensions("ga:eventCategory,ga:eventLabel");
			gaReader.setSort(sortConfig);
			gaReader.setMaxResults(maxResults);
		} catch (GeneralSecurityException | IOException e) {
			logger.error("Error while initializing the GA bean", e);
			throw new IllegalStateException("Error while initializing the GA bean", e);
		}
		refreshPopularContent();
	}
}
