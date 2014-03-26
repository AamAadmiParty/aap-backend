package com.next.aap.task.voiceofaap;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseSocialTask {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	Random random = new Random(System.currentTimeMillis());
	protected void sleep(){
		try{
			int waitTime = random.nextInt(5) + 5;
			logger.info("waiting for " + waitTime +" seconds");
			Thread.sleep(waitTime * 1000);			
		}catch(Exception ex){
			
		}
	}

}
