package com.next.aap.web.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.util.AapBlogDownloader;
import com.next.aap.core.util.AapNewsDownloader;

@Component
public class ContentDonwloadUtil {

	@Autowired
	private AapNewsDownloader aapNewsDownloader;
	@Autowired
	private AapBlogDownloader aapBlogDownloader;
	
	@PostConstruct
	public void init(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					//Curently it can not run on EC2 server as HtmlUnitDriver fails because of JS error on aap pages
					//aapNewsDownloader.downloadData();
					//aapBlogDownloader.downloadAapBlogs();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).run();
		System.out.println("Thread Started to download News");
		//aapService.saveAllCountries();
	}

}
