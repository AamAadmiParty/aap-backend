package com.next.aap.web.cache;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TemplateCaheInMemoryImpl implements TemplateCache {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init(){
        logger.info("init");

    }
    @Override
    public String getStateTemplate(Long stateId, String url) {
        // TODO Auto-generated method stub
        return null;
    }

}
