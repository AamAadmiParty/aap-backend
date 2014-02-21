package com.next.aap.web.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ManagedBean
//@Scope("session")
@ViewScoped
@URLMapping(id = "adminPanelBean", beanName="adminPanelBean", pattern = "/admin/home", viewId = "/WEB-INF/jsf/admin_panel.xhtml")
@URLBeanName("adminPanelBean")
public class AdminPanelBean extends BaseUserJsfBean {

	private static final long serialVersionUID = 1L;
	
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true,buildLoginUrl("/admin/home"));
		if(loggedInUser == null){
			return;
		}
		int totalLocation = 0;
		if(menuBean.isGlobalAdmin()){
			totalLocation++;
		}
		if(menuBean.getAdminStates() != null && menuBean.getAdminStates().size() > 0){
			totalLocation = totalLocation + menuBean.getAdminStates().size();
		}
		if(menuBean.getAdminDistricts() != null && menuBean.getAdminDistricts().size() > 0){
			totalLocation = totalLocation + menuBean.getAdminDistricts().size();
		}
		if(menuBean.getAdminAcs() != null && menuBean.getAdminAcs().size() > 0){
			totalLocation = totalLocation + menuBean.getAdminAcs().size();
		}
		if(menuBean.getAdminPcs() != null && menuBean.getAdminPcs().size() > 0){
			totalLocation = totalLocation + menuBean.getAdminPcs().size();
		}
		if(menuBean.getAdminCountries() != null && menuBean.getAdminCountries().size() > 0){
			totalLocation = totalLocation + menuBean.getAdminCountries().size();
		}
		if(menuBean.getAdminCountryRegions() != null && menuBean.getAdminCountryRegions().size() > 0){
			totalLocation = totalLocation + menuBean.getAdminCountryRegions().size();
		}
		
		if(totalLocation == 1){
			if(menuBean.isGlobalAdmin()){
				menuBean.selectGlobal(null);
			}
			if(menuBean.getAdminStates() != null && menuBean.getAdminStates().size() > 0){
				menuBean.setSelectedAdminState(menuBean.getAdminStates().get(0));
			}
			if(menuBean.getAdminDistricts() != null && menuBean.getAdminDistricts().size() > 0){
				menuBean.setSelectedAdminDistrict(menuBean.getAdminDistricts().get(0));
			}
			if(menuBean.getAdminAcs() != null && menuBean.getAdminAcs().size() > 0){
				menuBean.setSelectedAdminAc(menuBean.getAdminAcs().get(0));
			}
			if(menuBean.getAdminPcs() != null && menuBean.getAdminPcs().size() > 0){
				menuBean.setSelectedAdminPc(menuBean.getAdminPcs().get(0));
			}
			if(menuBean.getAdminCountries() != null && menuBean.getAdminCountries().size() > 0){
				menuBean.setSelectedAdminCountry(menuBean.getAdminCountries().get(0));
			}
			if(menuBean.getAdminCountryRegions() != null && menuBean.getAdminCountryRegions().size() > 0){
				menuBean.setSelectedAdminCountryRegion(menuBean.getAdminCountryRegions().get(0));
			}
			return;
		}
		if(menuBean.getLocationType() == null || menuBean.getLocationType() == PostLocationType.NA){
			RequestContext.getCurrentInstance().execute("chooseLocation.show()");
			
		}
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}


}
