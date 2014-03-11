package com.next.aap.web.cache;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.EventDto;

@Component
public class EventCacheImpl {

	@Autowired
	private AapService aapService;
	
	private List<EventDto> events;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostConstruct
	public void refreshCache(){
		try {
			events = aapService.getAllFutureEvents();
			for(EventDto oneEventDto:events){
				oneEventDto.setAddress(StringEscapeUtils.escapeEcmaScript(oneEventDto.getAddress().replaceAll("\n", "<br>")));
				
			}
			events = Collections.unmodifiableList(events);
		} catch (Exception e) {
			logger.error("unable to refresh Event Cache", e);
		}
	}
	
	
	public List<EventDto> getEvents(){
		return events;
	}

}
