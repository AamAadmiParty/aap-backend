package com.next.aap.task;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.next.aap.core.service.AapService;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("Creating Context");
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("aap-task.xml");
		AapService aapService = applicationContext.getBean(AapService.class);
		System.out.println("aap Service " + aapService);
	}
}
