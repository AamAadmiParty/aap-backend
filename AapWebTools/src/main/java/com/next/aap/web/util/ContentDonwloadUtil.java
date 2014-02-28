package com.next.aap.web.util;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.next.aap.core.util.AapBlogDownloader;
import com.next.aap.core.util.AapNewsDownloader;
import com.next.aap.core.util.VideoDownloader;
import com.next.aap.web.cache.AapDataCache;

@Component
public class ContentDonwloadUtil {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapNewsDownloader aapNewsDownloader;
	@Autowired
	private AapBlogDownloader aapBlogDownloader;
	@Autowired
	private VideoDownloader videoDownloader;
	@Autowired
	private AapDataCache aapDataCacheDbImpl;
	
	@PostConstruct
	public void init(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					//Curently it can not run on EC2 server as HtmlUnitDriver fails because of JS error on aap pages
					aapNewsDownloader.downloadData(59);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					//Curently it can not run on EC2 server as HtmlUnitDriver fails because of JS error on aap pages
					aapBlogDownloader.downloadAapBlogs();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					videoDownloader.refreshVideoList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		//new Thread(runnable).run();
		logger.info("Thread Started to download News");
		//aapService.saveAllCountries();
	}
	
	@Scheduled(cron = "01 01 * * * *")
	public void refreshVideoList() {
		try {
			//Curently it can not run on EC2 server as HtmlUnitDriver fails because of JS error on aap pages
			aapNewsDownloader.downloadData(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//Curently it can not run on EC2 server as HtmlUnitDriver fails because of JS error on aap pages
			aapBlogDownloader.downloadAapBlogs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			videoDownloader.refreshVideoList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		aapDataCacheDbImpl.refreshFullCache();
	}

}
