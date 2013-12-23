package com.next.aap.core.util;

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
		aapService.updateAllPermissions();
	}
}
