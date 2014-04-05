package com.next.aap.task.voiceofaap;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseSocialTask {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	Random random = new Random(System.currentTimeMillis());
	protected void sleep(){
		sleep(5);
	}
	protected void sleep(int minimumTime){
		try{
			int waitTime = random.nextInt(3000) + minimumTime *1000;
			logger.info("waiting for " + waitTime +" milli seconds");
			Thread.sleep(waitTime);			
		}catch(Exception ex){
			
		}
	}

}
