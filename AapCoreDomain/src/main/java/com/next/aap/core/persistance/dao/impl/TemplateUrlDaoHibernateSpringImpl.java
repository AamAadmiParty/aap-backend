package com.next.aap.core.persistance.dao.impl;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.TemplateUrl;
import com.next.aap.core.persistance.dao.TemplateUrlDao;

@Repository
public class TemplateUrlDaoHibernateSpringImpl extends BaseDaoHibernateSpring<TemplateUrl> implements TemplateUrlDao {

    private static final long serialVersionUID = 1L;

    @Override
    public TemplateUrl saveTemplateUrl(TemplateUrl templateUrl) {
        return super.saveObject(templateUrl);
    }

    @Override
    public TemplateUrl getTemplateUrlById(Long id) {
        return super.getObjectById(TemplateUrl.class, id);
    }

}
