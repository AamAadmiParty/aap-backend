package com.next.aap.web.jsf.beans;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.PlannedEmailDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@Scope("session")
@URLMapping(id = "adminEmailBean", beanName = "adminEmailBean", pattern = "/admin/email", viewId = "/WEB-INF/jsf/admin_email.xhtml")
@URLBeanName("adminEmailBean")
public class AdminEmailBean extends BaseAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private PlannedEmailDto selectedEmail;

	@Autowired
	private MenuBean menuBean;

	private int pageNumber = 1;
	private int pageSize = 20;
	private boolean showList = true;

	private List<PlannedEmailDto> plannedEmails;

	public AdminEmailBean() {
		super(AppPermission.ADMIN_EMAIL, "/admin/email");
	}

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {
		if (!checkUserAccess()) {
			return;
		}
		refreshPosts();
		showList = true;

	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}

	public PlannedEmailDto getSelectedEmail() {
		return selectedEmail;
	}

	public void setSelectedEmail(PlannedEmailDto selectedEmail) {
		showList = false;
		this.selectedEmail = selectedEmail;
	}

	public void showPreview() {

	}

	public void savePost() {
		try {
			selectedEmail.setLocationType(menuBean.getLocationType());
			selectedEmail.setLocationId(menuBean.getAdminSelectedLocationId());

			if (StringUtil.isEmpty(selectedEmail.getMessage())) {
				sendErrorMessageToJsfScreen("Please enter Message");
			}
			if (StringUtil.isEmpty(selectedEmail.getSubject())) {
				sendErrorMessageToJsfScreen("Please enter Subject");
			}
			if (selectedEmail.getPostingTime() == null) {
				sendErrorMessageToJsfScreen("Please enter when you want to post it by choosing correct time in future");
			} else {
				Calendar today = Calendar.getInstance();
				if (today.getTime().after(selectedEmail.getPostingTime())) {
					sendErrorMessageToJsfScreen("Please enter a time in future(Now + 1 hour)");
				}
			}
			if (isValidInput()) {
				aapService.savePlannedEmail(selectedEmail);
				sendInfoMessageToJsfScreen("Email saved succesfully");
				refreshPosts();
				showList = true;
			}

		} catch (Exception ex) {
			sendErrorMessageToJsfScreen("Unable to save Post", ex);
		}

	}

	private void refreshPosts() {
		plannedEmails = aapService.getPlannedEmailsForLocation(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId(), pageNumber, pageSize);
	}

	public void newPost() {
		selectedEmail = new PlannedEmailDto();
		showList = false;
	}

	public void clear() {
		newPost();
	}

	public void cancel() {
		newPost();
		showList = true;
	}

	public List<PlannedEmailDto> getPlannedEmails() {
		return plannedEmails;
	}

	public void setPlannedEmails(List<PlannedEmailDto> plannedEmails) {
		this.plannedEmails = plannedEmails;
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

}
