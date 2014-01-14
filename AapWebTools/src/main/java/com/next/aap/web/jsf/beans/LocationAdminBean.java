package com.next.aap.web.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

//@Scope("session")
@ViewScoped
@ManagedBean
@URLMapping(id = "locationAdminBean", beanName="locationAdminBean", pattern = "/admin/location", viewId = "/WEB-INF/jsf/location_admin.xhtml")
@URLBeanName("locationAdminBean")
public class LocationAdminBean extends BaseUserJsfBean {

	private static final long serialVersionUID = 1L;
	
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true,buildLoginUrl("/admin/location"));
		if(loggedInUser == null){
			return;
		}
		if(isLocationNotSelected(menuBean)){
			buildAndRedirect("/admin/home");
		}
	}
}
