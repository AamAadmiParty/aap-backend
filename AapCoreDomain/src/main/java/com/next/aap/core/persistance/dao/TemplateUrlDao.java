package com.next.aap.core.persistance.dao;

import com.next.aap.core.persistance.TemplateUrl;

public interface TemplateUrlDao {

    public abstract TemplateUrl saveTemplateUrl(TemplateUrl templateUrl);

    public abstract TemplateUrl getTemplateUrlById(Long id);

}