package com.next.aap.web.analytics;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Set;

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

public class GAReader {
	private String gaServiceAccountEmailId;
	private String gaViewId;
	private File privateKeyFile;
	private Credential credential;
	private static final String APPLICATION_NAME = "AAP-Analytics/v1";
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	public GAReader() throws GeneralSecurityException, IOException {
		// initialize the transport
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();

	}

	public void init() throws GeneralSecurityException, IOException {
		Set<String> scopes = new HashSet<String>();
		scopes.add(AnalyticsScopes.ANALYTICS_READONLY);
		credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(gaServiceAccountEmailId)
				.setServiceAccountScopes(scopes)
				.setServiceAccountPrivateKeyFromP12File(privateKeyFile).build();
	}

	public void setGaServiceAccountEmailId(String gaServiceAccountEmailId) {
		this.gaServiceAccountEmailId = gaServiceAccountEmailId;
	}

	public void setGaViewId(String gaViewId) {
		if (!gaViewId.startsWith("ga")) {
			gaViewId = "ga:" + gaViewId;
		}
		this.gaViewId = gaViewId;
	}

	public void setPrivateKeyFile(File privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}

	public void getAnalytics() {
		Analytics analytics = new Analytics.Builder(httpTransport,
				JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
				.build();
		Get get;
		try {
			get = analytics
					.data()
					.ga()
					.get(gaViewId, "2006-01-01", "2100-12-31",
							"ga:totalEvents,ga:uniqueEvents");
			get.setDimensions("ga:eventCategory");
			// get.setMaxResults(3);
			// get.setFilters(filters)
			GaData gaData = get.execute();
			System.out.println(gaData);
			System.out.println(gaData.getRows());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			GAReader gaReader = new GAReader();
			gaReader.setGaServiceAccountEmailId("709673867506-v2tsss4lvihfbihbt4g89d97psfuoj2q@developer.gserviceaccount.com");
			gaReader.setGaViewId("81601907");
			gaReader.setPrivateKeyFile(new File(
					GAReader.class
							.getResource(
									"/ga/c8aa9a7f75b477b3822f8528ba95f3b3446f2410-privatekey.p12")
							.getFile()));
			gaReader.init();
			gaReader.getAnalytics();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
