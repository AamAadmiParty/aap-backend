package com.next.aap.web.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;

@Component
public class DonationImportUtil {

	@Autowired
	private AapService aapService;
	@PostConstruct
	public void init(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				int overAllProcessed = 0;
				int totalProcessed = 0;
				Long startime;
				Long endTime;
				Long globalStartime = System.currentTimeMillis();
				int recordsPerRound = 500;
				do{
					startime = System.currentTimeMillis();
					totalProcessed = aapService.importDonationRecords(recordsPerRound);
					overAllProcessed = overAllProcessed +totalProcessed;
					endTime = System.currentTimeMillis();
					System.out.println("Total Done="+overAllProcessed+", Speed = "+ ((recordsPerRound * 1000)/(endTime - startime)) +", overallSpeed="+ ((overAllProcessed * 1000)/(endTime - globalStartime)));
				}while(totalProcessed > 0);
				System.out.println("overAllProcessed="+overAllProcessed);
			}
		};
		new Thread(runnable).run();
	}
}
