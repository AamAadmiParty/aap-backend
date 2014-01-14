package com.next.aap.web.jsf.beans;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.PlannedFacebookPostDto;
import com.next.aap.web.dto.PlannedSmsDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

//@Scope("session")
@ViewScoped
@ManagedBean
@URLMapping(id = "adminSmsBean", beanName = "adminSmsBean", pattern = "/admin/sms", viewId = "/WEB-INF/jsf/admin_sms.xhtml")
@URLBeanName("adminSmsBean")
public class AdminSmsBean extends BaseAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private PlannedSmsDto selectedPlannedSms;

	private int pageNumber = 1;
	private int pageSize = 20;
	private boolean showList = true;

	private List<PlannedSmsDto> plannedSmss;

	public AdminSmsBean() {
		super(AppPermission.ADMIN_SMS, "/admin/sms");
	}

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {
		if (!checkUserAccess()) {
			return;
		}
		refreshSmsList();
	}
	private void refreshSmsList(){
		plannedSmss = aapService.getPlannedSmssForLocation(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId(), pageNumber, pageSize);
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}

	public void setSelectedFacebookPost(PlannedFacebookPostDto selectedFacebookPost) {
	}

	public void savePost() {
		try {
			selectedPlannedSms.setLocationType(menuBean.getLocationType());
			selectedPlannedSms.setLocationId(menuBean.getAdminSelectedLocationId());

			if (StringUtil.isEmpty(selectedPlannedSms.getMessage())) {
				sendErrorMessageToJsfScreen("Please enter a Message");
			}
			if (selectedPlannedSms.getMessage().length() > 140) {
				sendErrorMessageToJsfScreen("Please enter a message equal or less then 140");
			}
			
			if (selectedPlannedSms.getPostingTime() == null) {
				sendErrorMessageToJsfScreen("Please enter when you want to sms it by choosing correct time in future");
			} else {
				Calendar today = Calendar.getInstance();
				if (today.getTime().after(selectedPlannedSms.getPostingTime())) {
					sendErrorMessageToJsfScreen("Please enter posting time in future");
				}
			}
			if (isValidInput()) {
				selectedPlannedSms = aapService.savePlannedSms(selectedPlannedSms);
				sendInfoMessageToJsfScreen("Sms saved succesfully");
				refreshSmsList();
				showList = true;
			}

		} catch (Exception ex) {
			sendErrorMessageToJsfScreen("Unable to save Post", ex);
		}

	}

	public void newPost() {
		selectedPlannedSms = new PlannedSmsDto();
		showList = false;
	}

	public void clear() {
		newPost();
	}

	public void cancel() {
		newPost();
		showList = true;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isShowList() {
		return showList;
	}

	public void setShowList(boolean showList) {
		this.showList = showList;
	}

	public PlannedSmsDto getSelectedPlannedSms() {
		return selectedPlannedSms;
	}

	public void setSelectedPlannedSms(PlannedSmsDto selectedPlannedSms) {
		this.selectedPlannedSms = selectedPlannedSms;
		showList = false;
	}

	public List<PlannedSmsDto> getPlannedSmss() {
		return plannedSmss;
	}

	public void setPlannedSmss(List<PlannedSmsDto> plannedSmss) {
		this.plannedSmss = plannedSmss;
	}

}
