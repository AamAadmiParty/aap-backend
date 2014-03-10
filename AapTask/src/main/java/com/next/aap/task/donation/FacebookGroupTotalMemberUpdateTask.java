package com.next.aap.task.donation;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.FacebookGroupDto;

@Component
public class FacebookGroupTotalMemberUpdateTask implements Runnable{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private AapService aapService;

	
	//http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger
	//http://freshgroundjava.blogspot.in/2012/07/spring-scheduled-tasks-cron-expression.html
	//ss mm hh dd
	@Scheduled(cron="0 45 04 * * *")
	public void downlaodFacebookDataForUsers(){
		logger.info("Starting FacebookGroupUpdateTask");
		int pageSize = 50;
		try {
			long lastGroupId = 0L;
			logger.info("Calling aapService.getFacebookGroups "+lastGroupId +" , "+ pageSize);
			List<FacebookGroupDto> facebookGroups = aapService.getFacebookGroups(lastGroupId,pageSize);
			
			while(facebookGroups != null && !facebookGroups.isEmpty()){
				
				for(FacebookGroupDto oneFacebookGroup:facebookGroups){
					updateFacebookGroupTotalNumbers(oneFacebookGroup);
				}
				
				lastGroupId = lastGroupId + pageSize;
				logger.info("Calling aapService.getFacebookGroups "+lastGroupId +" , "+ pageSize);
				facebookGroups = aapService.getFacebookGroups(lastGroupId,pageSize);;
			}
		} catch (Exception e) {
			logger.error("Unable to update facebook group Data", e);
		}
		
	}
	
	private void updateFacebookGroupTotalNumbers(FacebookGroupDto oneFacebookGroup){
		try{
			logger.info("Working on "+oneFacebookGroup.getGroupName() +" , "+ oneFacebookGroup.getId());
			FacebookAppPermissionDto facebookAppPermissionDto = aapService.getFacebookPermissionForAGroup(oneFacebookGroup.getId());
			Facebook facebook;
			if(facebookAppPermissionDto == null){
				facebook = new FacebookTemplate();	
			}else{
				facebook = new FacebookTemplate(facebookAppPermissionDto.getToken());
			}
			List<FacebookProfile> facebookProfiles = facebook.groupOperations().getMemberProfiles(oneFacebookGroup.getFacebookGroupExternalId());
			logger.info("  Total members "+facebookProfiles.size() );
			aapService.updateFacebookGroupOverallTotalMembes(oneFacebookGroup.getId(), facebookProfiles.size());
		}catch(Exception ex){
			logger.error("Unable to update total members of group "+oneFacebookGroup.getId()+" , "+oneFacebookGroup.getGroupName());
		}
		

	}
	@Override
	public void run() {
		downlaodFacebookDataForUsers();
	}
	
}
