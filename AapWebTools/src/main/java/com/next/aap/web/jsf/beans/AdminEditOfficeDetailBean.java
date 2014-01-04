package com.next.aap.web.jsf.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.OfficeDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "adminEditOfficeDetailBean", beanName = "adminEditOfficeDetailBean", pattern = "/admin/office", viewId = "/WEB-INF/jsf/admin_office.xhtml")
@URLBeanName("adminEditOfficeDetailBean")
public class AdminEditOfficeDetailBean extends BaseMultiPermissionAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private OfficeDto selectedOffice;

	@Autowired
	private MenuBean menuBean;

	private boolean showList = true;

	private List<OfficeDto> officeList;

	public AdminEditOfficeDetailBean() {
		super("/admin/office", AppPermission.EDIT_OFFICE_ADDRESS);
	}

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {
		if (!checkUserAccess()) {
			return;
		}
		refreshNewsList();
	}

	private void refreshNewsList() {
		officeList = aapService.getLocationOffices(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
		if (officeList == null || officeList.size() <= 1) {
			if (officeList.size() == 1) {
				selectedOffice = officeList.get(0);
			} else {
				selectedOffice = new OfficeDto();
			}
			showList = false;
		}
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}

	public void saveOfficeDetail() {
		try {
			switch (menuBean.getLocationType()) {
			case Global:
				selectedOffice.setNational(true);
				break;
			case STATE:
				selectedOffice.setStateId(menuBean.getAdminSelectedLocationId());
				break;
			case DISTRICT:
				selectedOffice.setDistrictId(menuBean.getAdminSelectedLocationId());
				break;
			case AC:
				selectedOffice.setAssemblyConstituencyId(menuBean.getAdminSelectedLocationId());
				break;
			case PC:
				selectedOffice.setParliamentConstituencyId(menuBean.getAdminSelectedLocationId());
				break;
			default:
				sendErrorMessageToJsfScreen("No Location selected");
			}
			if (isValidInput()) {
				selectedOffice = aapService.saveOffice(selectedOffice);
				sendInfoMessageToJsfScreen("Office Details saved Succesfully");
				refreshNewsList();
			}
		} catch (Exception ex) {
			sendErrorMessageToJsfScreen("Unable to save Office Detail", ex);
		}
	}

	public boolean isShowList() {
		return showList;
	}

	public void setShowList(boolean showList) {
		this.showList = showList;
	}

	public OfficeDto getSelectedOffice() {
		return selectedOffice;
	}

	public void setSelectedoffice(OfficeDto selectedoffice) {
		this.selectedOffice = selectedoffice;
		showList = false;
	}

	public List<OfficeDto> getOfficeList() {
		return officeList;
	}

	public void setOfficeList(List<OfficeDto> officeList) {
		this.officeList = officeList;
	}

}
