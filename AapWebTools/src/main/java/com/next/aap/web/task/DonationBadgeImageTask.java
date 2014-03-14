package com.next.aap.web.task;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.next.aap.web.controller.DonationTemplateEnum;
import com.next.aap.web.dto.DonationDto;
import com.next.aap.web.util.BadgeImageGenerator;

@Component
public class DonationBadgeImageTask implements Callable<Boolean>{

	private static Logger logger = LoggerFactory.getLogger(DonationBadgeImageTask.class);
	private DonationDto donationDto;
	private String filePath;
	private String id;
	private DonationTemplateEnum donationTemplateEnum;
	
	public DonationBadgeImageTask() {
	}

	public DonationBadgeImageTask(DonationDto donationDto,String filePath, DonationTemplateEnum donationTemplateEnum) {
		this.donationDto = donationDto;
		this.filePath = filePath;
		this.donationTemplateEnum = donationTemplateEnum;
	}

	public static void main(String[] args) throws Exception{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		System.out.println(simpleDateFormat.format(Calendar.getInstance().getTime()));
	}
	@Override
	public Boolean call() throws Exception {
		logger.info("DonationSummaryTask:Generating Image for "+id);
		if(donationDto == null){
			return false;
		}
		FileOutputStream fileOutputStream;
		try {
			
			fileOutputStream = new FileOutputStream(filePath);
			logger.info("filePath {}",filePath);
			String amount = String.format("%8.1f" , donationDto.getAmount()) ;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			String donationDate = simpleDateFormat.format(donationDto.getDonationDate());
			switch(donationTemplateEnum){
				case template01:
					BadgeImageGenerator.createImageTemplate01(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
					break;
				case template02:
					BadgeImageGenerator.createImageTemplate02(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
					break;
				case template03:
					BadgeImageGenerator.createImageTemplate03(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
					break;
				case template04:
					BadgeImageGenerator.createImageTemplate04(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
					break;
				case template05:
					BadgeImageGenerator.createImageTemplate05(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
					break;
				case template06:
					BadgeImageGenerator.createImageTemplate06(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
					break;
				case template07:
					BadgeImageGenerator.createImageTemplate07(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
					break;
				default :
					BadgeImageGenerator.createImageTemplate03(fileOutputStream, amount, donationDto.getDonorName(), donationDate, donationDto.getTransactionId());
					break;
			}
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
