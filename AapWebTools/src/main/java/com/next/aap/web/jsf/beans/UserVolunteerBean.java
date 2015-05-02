package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.exception.AppException;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.VolunteerDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;


@ManagedBean
@ViewScoped
//@URLMapping(id = "userVolunteerBean", beanName = "userVolunteerBean", pattern = "/volunteer", viewId = "/WEB-INF/jsf/aapstyle/uservolunteerprofile.xhtml")
/*
@URLMappings(mappings={
		@URLMapping(id = "userVolunteerBean1", beanName = "userVolunteerBean", pattern = "/aapstyle/volunteer", viewId = "/WEB-INF/jsf/aapstyle/uservolunteerprofile.xhtml"),
		@URLMapping(id = "userVolunteerBean2", beanName = "userVolunteerBean", pattern = "/volunteer", viewId = "/WEB-INF/jsf/aapnewstyle/uservolunteerprofile.xhtml")
		})
		*/
@URLBeanName("userVolunteerBean")
public class UserVolunteerBean extends BaseUserJsfBean {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{volunteerBean}")
	private VolunteerBean volunteerBean;

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true, buildLoginUrl("/volunteer"));
		if (loggedInUser == null) {
			return;
		}

		volunteerBean.init(getLoggedInUser());
		
	}

	public void saveProfile(ActionEvent event) {
		List<Long> selectedInterests = volunteerBean.getSelectedInterestIds();
		if (isValidInput()) {
			try {
				VolunteerDto selectedVolunteer = volunteerBean.getSelectedVolunteer();
				selectedVolunteer.setInfoRecordedAt("Self Service Portal");
				selectedVolunteer.setInfoRecordedBy("Self");
				selectedVolunteer.setUserId(getLoggedInUser().getId());
				selectedVolunteer = aapService.saveVolunteerDetails(selectedVolunteer, selectedInterests);
				sendInfoMessageToJsfScreen("Volunteer Profile saved succesfully.");
			} catch (AppException e) {
				sendErrorMessageToJsfScreen(e.getMessage(), e);
			} catch (Exception e) {
				sendErrorMessageToJsfScreen("Unable to save ", e);
			}
			
		}
	}

	
	public VolunteerBean getVolunteerBean() {
		return volunteerBean;
	}

	public void setVolunteerBean(VolunteerBean volunteerBean) {
		this.volunteerBean = volunteerBean;
	}
	
}
