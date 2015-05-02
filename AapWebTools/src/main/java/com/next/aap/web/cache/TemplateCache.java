package com.next.aap.web.cache;

import com.next.aap.web.dto.TemplateUrlDto;

public interface TemplateCache {

    TemplateUrlDto getStateTemplate(Long stateId, String url);
}
