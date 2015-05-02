package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Template;

public interface TemplateDao {

    public abstract Template getGlobalTemplate();

    public abstract List<Template> getAllStateTemplates();

}