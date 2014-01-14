package com.next.aap.web.jsf.beans;

import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Component;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
//@Scope("session")
@ViewScoped
@URLMapping(id = "adminNotAllowedBean", beanName="adminNotAllowedBean", pattern = "/admin/notallowed", viewId = "/WEB-INF/jsf/admin_not_allowed.xhtml")
@URLBeanName("adminNotAllowedBean")
public class AdminNotAllowedBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
	}

}
