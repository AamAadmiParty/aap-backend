package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.next.aap.web.dto.DonationDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Component
@ViewScoped
@URLMapping(id = "myDonationBean", beanName="myDonationBean", pattern = "/mydonations", viewId = "/WEB-INF/jsf/aapstyle/mydonation.xhtml")
@URLBeanName("myDonationBean")
public class MyDonationBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private List<DonationDto> donations;
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true, buildLoginUrl("/mydonations"));
		if (loggedInUser == null) {
			return;
		}

		donations = aapService.getUserDonations(loggedInUser.getId());
		//donations = aapService.getUserDonations(24299L);
		
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public List<DonationDto> getDonations() {
		return donations;
	}

	public void setDonations(List<DonationDto> donations) {
		this.donations = donations;
	}

	

}
