package com.next.aap.web.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.TemplateDto;
import com.next.aap.web.dto.TemplateUrlDto;

@Component
public class TemplateCaheInMemoryImpl implements TemplateCache {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AapService aapService;

    private Map<Long, Map<String, TemplateUrlDto>> templateCache;

    @PostConstruct
    public void init(){
        logger.info("init");

        refreshCache();
    }

    private void addTemplateToCache(TemplateDto templateDto) {
        if (templateDto == null) {
            return;
        }
        Long stateId = 0L;
        if (templateDto.getStateDto() != null) {
            stateId = templateDto.getStateDto().getId();
        }
        Map<String, TemplateUrlDto> templates = new HashMap<String, TemplateUrlDto>();
        for (TemplateUrlDto oneTemplateUrlDto : templateDto.getTemplateUrlDtos()) {
            templates.put(oneTemplateUrlDto.getUrl(), oneTemplateUrlDto);
        }
        templateCache.put(stateId, templates);

    }
    @Override
    public TemplateUrlDto getStateTemplate(Long stateId, String url) {
        System.out.println("Getting Template for State : " + stateId);
        System.out.println("templateCache : " + templateCache);
        Map<String, TemplateUrlDto> templates = templateCache.get(stateId);
        if (templates == null) {
            templates = templateCache.get(0L);
        }
        TemplateUrlDto templateUrlDto = null;
        if (templates != null) {
            templateUrlDto = templates.get(url);
        }
        return templateUrlDto;
    }

    @Override
    public void refreshCache() {
        try {
            TemplateDto globalTemplate = aapService.getGlobalTemplate();
            List<TemplateDto> stateTemplates = aapService.getStateTemplates();

            templateCache = new HashMap<Long, Map<String, TemplateUrlDto>>();

            addTemplateToCache(globalTemplate);

            if (stateTemplates != null) {
                for (TemplateDto oneTemplateDto : stateTemplates) {
                    addTemplateToCache(oneTemplateDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
