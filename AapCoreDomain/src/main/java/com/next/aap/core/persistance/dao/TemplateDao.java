package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Template;

public interface TemplateDao {

    public abstract Template getTemplateById(Long id);

    public abstract Template saveTemplate(Template template);

    public abstract Template getGlobalTemplate();

    public abstract List<Template> getAllStateTemplates();

    public abstract List<Template> getAllGlobalTemplates();

    public abstract List<Template> getAllTemplatesOfState(Long stateId);

}