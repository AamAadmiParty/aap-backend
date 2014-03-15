package com.next.aap.task.donation;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.next.aap.core.service.AapService;
import com.next.aap.task.util.DduUtil;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.FacebookGroupDto;
import com.next.aap.web.dto.FacebookPostDto;

@Component
public class DailyDonationImageTask implements Runnable {

	private final String baseUrl = "http://remote.aamaadmiparty.org:8080/swaraj/rest/stats/";
	private final String newBaseUrl = "http://remote.aamaadmiparty.org:8080/swaraj/rest/stats/";

	private final String dayDonationUrl = baseUrl + "date";
	private final String monthDonationUrl = newBaseUrl + "month";

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AapService aapService;

	public static void main(String[] args) throws Exception {
		/*
		 * DonationImageTask task = new DonationImageTask(); task.httpUtil = new
		 * HttpUtil(); //task.printData(); Calendar cal =
		 * Calendar.getInstance(TimeZone.getTimeZone("IST"));
		 * 
		 * FacebookClient facebookClient = new DefaultFacebookClient(
		 * "CAACEdEose0cBALqlo8hw1Thkbg1vyyAkdsIcXQp9QErbnXRZBncv1Sj6dbZCmbEZBNDtFbFwXM1aDpXMRV9vDWGSpZC09Wqh1m4z75mDaLEvZAZAZB8yiU2tmdZCsTAPh8U7kpKuRhEDaumLZAJuncjVydK6J9ZBXZAeUsvagRGjdDmns1ozV6tR5KJZBdSGy7JReeQZD"
		 * ); FacebookType publishMessageResponse =
		 * facebookClient.publish("167171270096883/feed", FacebookType.class,
		 * Parameter.with("message",
		 * "2nd Test Message from Ravi, dont panic my friends"));
		 * System.out.println(publishMessageResponse.getId());
		 * System.out.println(publishMessageResponse.getType());
		 * System.out.println(publishMessageResponse.getMetadata());
		 */
		Calendar today = Calendar.getInstance();
		System.out.println(today.get(Calendar.DATE));
		System.out.println(today.get(Calendar.MONTH));
		if (today.get(Calendar.DATE) == 9 && today.get(Calendar.MONTH) == 7) {
			System.out.println("Yes");
		}
		System.out.println(today.getActualMaximum(Calendar.DAY_OF_MONTH));
		Double donationForMonth = 123456745.45;
		String monthDonation = String.format("%9.1f" , donationForMonth);
		System.out.println(monthDonation);
		System.out.println(donationForMonth.toString());
	}

	// http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger
	// http://freshgroundjava.blogspot.in/2012/07/spring-scheduled-tasks-cron-expression.html
	// ss mm hh dd
	@Scheduled(cron = "0 45 00 * * *")
	public void downloadImageAndPostToFacebook() {
		logger.info(new Date().toString());
		logger.info("Starting Scheduled task");
		try {
			Calendar yesterday = Calendar.getInstance();
			yesterday.add(Calendar.DAY_OF_MONTH, -1);
			String yesterdayMonth = yesterday.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
			int yesterdayYear = yesterday.get(Calendar.YEAR);
			int yesterdayDay = yesterday.get(Calendar.DATE);
			String fullDateString = yesterdayDay + "-" + yesterdayMonth + "-" + yesterdayYear;
			String fullDateStringDDMMMYYYY = yesterdayDay + "-" + yesterday.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH) + "-"
					+ yesterdayYear;
			logger.info("Genrating data for {}", fullDateString);
			Double donationForYesterday = aapService.getDayDonation(yesterday.getTime());
			if (donationForYesterday == null) {
				donationForYesterday = 10.0;
			}
			String dayDonation = donationForYesterday.toString();

			Double donationForMonth = aapService.getMonthDonation(yesterday.getTime());
			if (donationForMonth == null) {
				donationForMonth = 10.0;
			}
			String monthDonation = String.format("%9.1f" , donationForMonth);
			if (donationForMonth <= 1.0 || donationForYesterday <= 1.0) {
				logger.info("Unable to get donation data from DB so not sharing the photo");
				return;
			}
			String filePath = "/usr/local/ddu/" + fullDateStringDDMMMYYYY + ".png";
			FileOutputStream fileOutputStream;

			try {
				fileOutputStream = new FileOutputStream(filePath);
				DduUtil.createTemplate01DDU(fileOutputStream, fullDateStringDDMMMYYYY, dayDonation, monthDonation, "100000000");

				fileOutputStream.close();
				logger.info("File created");
				Resource resource = new FileSystemResource(filePath);
				// Share of facebook page
				// publishOnAapPage(filePath);

				// Now share image on the facebook groups
				long startId = 0l;
				int pageSize = 100;
				List<FacebookGroupDto> allFacebookGroups = aapService.getAllFacebookGroupsWhereWeCanPost(startId, pageSize);
				logger.info("Total Facebook Groups Where i can post = " + allFacebookGroups.size());
				while (allFacebookGroups != null && !allFacebookGroups.isEmpty()) 
				{
					for (FacebookGroupDto oneFacebookGroupDto : allFacebookGroups) {
						postOnGroup(oneFacebookGroupDto, resource);
						startId = oneFacebookGroupDto.getId();
					}
					allFacebookGroups = aapService.getAllFacebookGroupsWhereWeCanPost(startId, pageSize);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void postOnGroup(FacebookGroupDto oneFacebookGroupDto, Resource resource) {
		FacebookAccountDto facebookAccountForGroupPosting = null;
		Set<String> alreadyTriedUsers = new HashSet<String>();
		alreadyTriedUsers.clear();
		while (true) {
			try {
				facebookAccountForGroupPosting = aapService.getFacebookAccountToPostOnGroup(oneFacebookGroupDto.getId());
				if (facebookAccountForGroupPosting == null) {
					break;
				}
				if (alreadyTriedUsers.contains(facebookAccountForGroupPosting.getFacebookUserId())) {
					break;
				}
				FacebookAppPermissionDto facebookAppPermissionDto = aapService.getVoiceOfAapFacebookPermission(facebookAccountForGroupPosting.getId());
				if (facebookAppPermissionDto == null) {
					logger.warn("Facebook Account[" + facebookAccountForGroupPosting.getId() + "] have not given pemrission to voice of aap");
					alreadyTriedUsers.add(facebookAccountForGroupPosting.getFacebookUserId());
					continue;
				}

				logger.info("Publishing photo on group " + oneFacebookGroupDto.getGroupName() + " from user " + facebookAccountForGroupPosting.getUserName());

				Facebook facebook = new FacebookTemplate(facebookAppPermissionDto.getToken());
				MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
				parts.set("source", resource);
				String facebookPostExternalId = facebook.publish(oneFacebookGroupDto.getFacebookGroupExternalId(), "photos", parts);

				// Parameter.with("message", message));
				logger.info("Published photo ID: " + facebookPostExternalId + " on group " + oneFacebookGroupDto.getGroupName() + " from user "
						+ facebookAccountForGroupPosting.getUserName());
				updateFacebookPost(facebookPostExternalId, "Success", "", oneFacebookGroupDto, facebookAccountForGroupPosting);
				Thread.sleep(15000);
				break;
			} catch (Exception ex) {
				alreadyTriedUsers.add(facebookAccountForGroupPosting.getFacebookUserId());
				updateFacebookPost("", "Failed", ex.getMessage(), oneFacebookGroupDto, facebookAccountForGroupPosting);
				logger.error("Unable to post", ex);
				break;
			}
		}
	}
	private void updateFacebookPost(String facebookPostExternalId, String status, String errorMessage,
			FacebookGroupDto facebookGroupDto, FacebookAccountDto facebookAccountDto){
		FacebookPostDto facebookPostDto = new FacebookPostDto();
		facebookPostDto.setFacebookAccountId(facebookAccountDto.getId());
		facebookPostDto.setFacebookPostExternalId(facebookPostExternalId);
		facebookPostDto.setFacebookGroupId(facebookGroupDto.getId());
		facebookPostDto.setPostStatus(status);
		facebookPostDto.setErrorMessage(errorMessage);
		facebookPostDto = aapService.saveFacebookPost(facebookPostDto);
	}

	/*
	 * public void publishOnAapPage() throws FileNotFoundException{ Calendar cal
	 * = Calendar.getInstance(); int yesterdayYear = cal.get(Calendar.YEAR); int
	 * yesterdayDay = cal.get(Calendar.DATE); String fullDateStringDDMMMYYYY =
	 * yesterdayDay +"-"+cal.getDisplayName(Calendar.MONTH, Calendar.SHORT ,
	 * Locale.ENGLISH)+"-"+yesterdayYear; String filePath =
	 * fullDateStringDDMMMYYYY+".png"; publishOnAapPage(filePath); } private
	 * void publishOnAapPage(String filePath) throws FileNotFoundException{ try{
	 * String token =
	 * "CAAHXYzhpziIBAODfXxgrWEDE8pKxBKDY5D4mAlJe8TN0u4orxKHHKNLw7WV5oxHeTgDsaNPtRwumkHyvfhUHzdZBDuJsw0JhCSh2gXVMZBA1m26omZBEnbppsF58USF7JUyGOuxoTr8frEwL4NlCH2xjtZBG9SeDK7zTDnBXLq1IzZCWZCszbS"
	 * ; FacebookClient facebookClient = new DefaultFacebookClient(token);
	 * FacebookType publishPhotoResponse = facebookClient.publish("me/photos",
	 * FacebookType.class,BinaryAttachment.with("ddu.png", new
	 * FileInputStream(filePath))); }catch(Exception ex){
	 * logger.error("Unable to post on AAP Page", ex); }
	 * //Parameter.with("message", message)); }
	 */

	@Override
	public void run() {
		downloadImageAndPostToFacebook();
	}

}
