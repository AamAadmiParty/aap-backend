package com.next.aap.web.jsf.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@Component
@Scope("session")
//@URLMapping(id = "homeBean", beanName="homeBean", pattern = "/home", viewId = "/WEB-INF/jsf/home.xhtml")
@URLMappings(mappings={
		@URLMapping(id = "homeBean1", beanName="homeBean", pattern = "/orig/home", viewId = "/WEB-INF/jsf/home.xhtml"),
		@URLMapping(id = "homeBean2", beanName="homeBean", pattern = "/design1/home", viewId = "/WEB-INF/jsf/design1/home.xhtml"),
		@URLMapping(id = "homeBean3", beanName="homeBean", pattern = "/home", viewId = "/WEB-INF/jsf/aapstyle/home.xhtml")
		})
@URLBeanName("homeBean")
public class HomeBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true,buildLoginUrl("/home"));
		if(loggedInUser == null){
			return;
		}
	}

}
