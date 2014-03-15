package com.next.aap.web.task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.primefaces.component.chart.bubble.BubbleChart;
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

	@Value("${aws_access_key}") 
	private String accessKey;
	
	@Value("${aws_access_secret}") 
	private String accessSecret;

	@Value("${s3_bucket_name_for_dc}") 
	private String s3BucketnameForDonationCertificates;

	@Value("${s3_base_dir_for_dc}") 
	private String s3BaseDirectoryForDonationCertificates;

	@Value("${s3_base_http_path_dc}") 
	private String s3BaseHttpForDonation;

	public void startDonationCertificateTask(String id, DonationDto donationDto, DonationTemplateEnum donationTemplateEnum){
		String filePath = getFilePath(id);
		String remoteS3Path = getRemoteFilePath(id);
		DonationBadgeImageTask donationBadgeImageTask = new DonationBadgeImageTask(donationDto,filePath, donationTemplateEnum, accessKey,
				accessSecret, s3BucketnameForDonationCertificates, remoteS3Path);
		Future<Boolean> future = threadPoolTaskExecutor.submit(donationBadgeImageTask);
		futureCacheManager.setImageKeyFuture(id, future);
	}
	public String getFilePath(String id){
		return donationCertifcateBaseDirectory+"/"+id+".png";
	}
	public String getRemoteFilePath(String id){
		return s3BaseDirectoryForDonationCertificates+"/"+id+".png";
	}
	public String getRemoteHttpFilePath(String id){
		return s3BaseHttpForDonation+"/"+ s3BucketnameForDonationCertificates+"/" +s3BaseDirectoryForDonationCertificates+"/"+id+".png";
	}
}