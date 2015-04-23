package com.next.aap.web.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;

@Component
public class AppPermisionUtil {

	@Autowired
	private AapService aapService;
	@PostConstruct
	public void init(){
		
        System.out.println("Creating All PErmissions and Roles");
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				aapService.updateAllPermissionsAndRole();
			}
		};
		new Thread(runnable).run();
		
		//aapService.saveAllCountries();
	}
}
