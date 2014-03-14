package com.next.aap.web.task;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.next.aap.web.controller.DonationTemplateEnum;
import com.next.aap.web.dto.DonationDto;

@Component
public class TaskManager {

	@Autowired
	private FutureCacheManager futureCacheManager;
	
	@Autowired
	@Qualifier(value="donationCertificateExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Value("${dc_base_directory}")
	private String donationCertifcateBaseDirectory;


	public void startDonationCertificateTask(String id, DonationDto donationDto, DonationTemplateEnum donationTemplateEnum){
		String filePath = getFilePath(id);
		DonationBadgeImageTask donationBadgeImageTask = new DonationBadgeImageTask(donationDto,filePath, donationTemplateEnum);
		Future<Boolean> future = threadPoolTaskExecutor.submit(donationBadgeImageTask);
		futureCacheManager.setImageKeyFuture(id, future);

	}
	public String getFilePath(String id){
		return donationCertifcateBaseDirectory+"/"+id+".png";
	}
}