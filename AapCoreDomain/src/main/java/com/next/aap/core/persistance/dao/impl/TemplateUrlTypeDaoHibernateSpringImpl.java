package com.next.aap.core.persistance.dao.impl;

import java.util.List;

import com.next.aap.core.persistance.TemplateUrlType;
import com.next.aap.core.persistance.dao.TemplateUrlTypeDao;

public class TemplateUrlTypeDaoHibernateSpringImpl extends BaseDaoHibernateSpring<TemplateUrlType> implements TemplateUrlTypeDao {

    private static final long serialVersionUID = 1L;

    @Override
    public List<TemplateUrlType> getAllTemplateUrlTypes() {
        String query = "from TemplateUrlType";
        return executeQueryGetList(query);
    }

}
