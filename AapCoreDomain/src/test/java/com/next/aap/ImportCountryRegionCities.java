package com.next.aap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.next.aap.core.service.AapService;

public class ImportCountryRegionCities {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("aap-core.xml");
		AapService aapService = applicationContext.getBean(AapService.class);
		aapService.importAllCountriesData();
		System.out.println("aap Service " + aapService);
		//39441
	}

}
