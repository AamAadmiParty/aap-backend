package com.next.aap.task.donation;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import com.next.aap.core.exception.AppException;
import com.next.aap.core.service.AapService;
import com.next.aap.core.service.HttpUtil;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;

@Component
public class FacebookGroupUpdateTask implements Runnable{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private AapService aapService;

	
	//http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger
	//http://freshgroundjava.blogspot.in/2012/07/spring-scheduled-tasks-cron-expression.html
	//ss mm hh dd
	@Scheduled(cron="0 45 03 * * *")
	public void downlaodFacebookDataForUsers(){
		logger.info("Starting FacebookGroupUpdateTask");
		int pageSize = 50;
		int totalGroupsUpdated;
		try {
			long lastGroupId = 0L;
			while(true){
				logger.info("Calling aapService.getFacebookAccounts "+lastGroupId +" , "+ pageSize);
				totalGroupsUpdated = aapService.updateFacebookGroupTotalMemberWithUs(lastGroupId,pageSize);
				if(totalGroupsUpdated <= 0){
					break;
				}
				lastGroupId = lastGroupId + pageSize;
			}
		} catch (Exception e) {
			logger.error("Unable to update facebook group Data", e);
		}
		
	}
	@Override
	public void run() {
		downlaodFacebookDataForUsers();
	}
	
}
