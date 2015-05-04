package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.Template;
import com.next.aap.core.persistance.dao.TemplateDao;

@Repository
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

    @Override
    public Template getTemplateById(Long id) {
        return super.getObjectById(Template.class, id);
    }

    @Override
    public Template saveTemplate(Template template) {
        return super.saveObject(template);
    }

    @Override
    public List<Template> getAllGlobalTemplates() {
        String query = "from Template where global=true";
        return executeQueryGetList(query);
    }

    @Override
    public List<Template> getAllTemplatesOfState(Long stateId) {
        Map<String, Object> params = new TreeMap<String, Object>();
        params.put("stateId", stateId);
        String query = "from Template where stateId=:stateId";
        return executeQueryGetList(query, params);
    }

}
