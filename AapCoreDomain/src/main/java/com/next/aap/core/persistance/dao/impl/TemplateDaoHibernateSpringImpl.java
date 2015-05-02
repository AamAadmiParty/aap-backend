package com.next.aap.core.persistance.dao.impl;

import java.util.List;

import com.next.aap.core.persistance.Template;
import com.next.aap.core.persistance.dao.TemplateDao;

public class TemplateDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Template> implements TemplateDao {

    private static final long serialVersionUID = 1L;

    @Override
    public Template getGlobalTemplate() {
        String query = "from Template where status='published' and global=true";
        Template template = executeQueryGetObject(query);
        return template;
    }

    @Override
    public List<Template> getAllStateTemplates() {
        String query = "from Template where status='published' and global=false";
        return executeQueryGetList(query);
    }

}
