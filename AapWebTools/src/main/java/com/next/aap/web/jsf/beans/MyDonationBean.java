package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.next.aap.web.dto.DonationDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean
@ViewScoped
//@URLMapping(id = "myDonationBean", beanName="myDonationBean", pattern = "/mydonations", viewId = "/WEB-INF/jsf/aapstyle/mydonation.xhtml")
@URLMappings(mappings={
		@URLMapping(id = "myDonationBean1", beanName="myDonationBean", pattern = "/aapstyle/mydonations", viewId = "/WEB-INF/jsf/aapstyle/mydonation.xhtml"),
		@URLMapping(id = "myDonationBean2", beanName="myDonationBean", pattern = "/mydonations", viewId = "/WEB-INF/jsf/aapnewstyle/mydonation.xhtml")
		})
@URLBeanName("myDonationBean")
public class MyDonationBean extends BaseUserJsfBean {

	private static final long serialVersionUID = 1L;
	
	private double total = 0.0;

	private List<DonationDto> successDonations;
	private List<DonationDto> failedDonations;
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true, buildLoginUrl("/mydonations"));
		if (loggedInUser == null) {
			return;
		}
		successDonations = new ArrayList<>();
		failedDonations = new ArrayList<>();
		List<DonationDto> donations = aapService.getUserDonations(loggedInUser.getId());
		if(donations != null && donations.size() > 0){
			for(DonationDto oneDonationDto:donations){
				if("Success".equalsIgnoreCase(oneDonationDto.getPgErrorMessage())){
					total = total + oneDonationDto.getAmount();
					successDonations.add(oneDonationDto);
				}else{
					failedDonations.add(oneDonationDto);
				}
			}
		}
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<DonationDto> getSuccessDonations() {
		return successDonations;
	}

	public void setSuccessDonations(List<DonationDto> successDonations) {
		this.successDonations = successDonations;
	}

	public List<DonationDto> getFailedDonations() {
		return failedDonations;
	}

	public void setFailedDonations(List<DonationDto> failedDonations) {
		this.failedDonations = failedDonations;
	}

	

}
