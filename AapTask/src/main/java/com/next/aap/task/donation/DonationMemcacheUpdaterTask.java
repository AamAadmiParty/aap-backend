package com.next.aap.task.donation;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;

@Component
public class DonationMemcacheUpdaterTask {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AapService aapService;

	
	// http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger
	// http://freshgroundjava.blogspot.in/2012/07/spring-scheduled-tasks-cron-expression.html
	// ss mm hh dd
	//@PostConstruct
	public void startDonationMemcacheUpdaterTask() {
		logger.info(new Date().toString());
		logger.info("Starting DonationMemcacheUpdaterTask task");
		try {
			aapService.updateAllLocationCampaignInCache();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
