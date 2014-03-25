package com.next.aap.task.voiceofaap;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.task.util.AwsEmailSender;
import com.next.aap.web.dto.EmailUserDto;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.PlannedEmailDto;
import com.next.aap.web.dto.PlannedFacebookPostDto;
import com.next.aap.web.dto.PlannedPostStatus;

@Component
public class SendEmailTask {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapService aapService;
	
	@Autowired
	@Qualifier(value="emailThreadPool")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	//@Scheduled(cron="0 45 00 * * *")
	@Scheduled(fixedDelay=300000)
	public void runTask(){
		PlannedEmailDto plannedEmail = aapService.getNextPlannedEmailToSend();
		if(plannedEmail == null){
			//No post to publish
			logger.info("No Plnaned post found");
			return;
		}
		try{
			logger.info("plannedEmail="+plannedEmail);
			List<EmailUserDto> allEmailUsers = aapService.getEmailsOfLocation(plannedEmail.getLocationType(), plannedEmail.getLocationId());
			logger.info("allEmailUsers="+allEmailUsers);
			if(allEmailUsers != null && !allEmailUsers.isEmpty()){
				for(EmailUserDto oneEmailUser:allEmailUsers){
					if(oneEmailUser.getEmail().equals("rakesh.pratap@gmail.com") || oneEmailUser.getEmail().equals("ping2ravi@gmail.com")){
						LinkedList<String> list = new LinkedList<>();
						list.add(oneEmailUser.getEmail());
						AwsEmailSender.sendMail("info@aapuk.org", list, plannedEmail.getSubject(), plannedEmail.getMessage());
					}
				}
				//wait for all task to finish before proceeding
			}
			aapService.updatePlannedEmailStatus(plannedEmail.getId(), PlannedPostStatus.DONE, null);
		}catch(Exception ex){
			aapService.updatePlannedEmailStatus(plannedEmail.getId(), PlannedPostStatus.DONE_WITH_ERROR, null);
		}
		
		
		
	}

}
