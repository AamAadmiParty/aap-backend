package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.DonationCampaignDto;
import com.next.aap.web.dto.DonationDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean
@ViewScoped
//@URLMapping(id = "myRippleDonationBean", beanName="myRippleDonationBean", pattern = "/ripple", viewId = "/WEB-INF/jsf/aapstyle/myrippledonation.xhtml")
@URLMappings(mappings={
		@URLMapping(id = "myRippleDonationBean1", beanName="myRippleDonationBean", pattern = "/aapstyle/ripple", viewId = "/WEB-INF/jsf/aapstyle/myrippledonation.xhtml"),
		@URLMapping(id = "myRippleDonationBean2", beanName="myRippleDonationBean", pattern = "/ripple", viewId = "/WEB-INF/jsf/aapnewstyle/myrippledonation.xhtml")
		})

@URLBeanName("myRippleDonationBean")
public class MyRippleDonationBean extends BaseUserJsfBean {

	private static final long serialVersionUID = 1L;
	
	private double total = 0.0;

	private List<DonationDto> successDonations;
	private List<DonationDto> failedDonations;
	private DonationCampaignDto rippleCampaign;
	private boolean rippleCampaignExists;
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true, buildLoginUrl("/ripple"));
		if (loggedInUser == null) {
			return;
		}
		rippleCampaign = aapService.getRippleDonationCamapign(loggedInUser.getId());
		if(rippleCampaign == null){
			rippleCampaignExists = false;
			rippleCampaign = new DonationCampaignDto();
		}else{
			rippleCampaignExists = true;
			successDonations = new ArrayList<>();
			failedDonations = new ArrayList<>();
			List<DonationDto> donations = aapService.getUserRippleDonations(loggedInUser.getId());
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
	}
	static Pattern myPattern = Pattern.compile("^[a-zA-Z0-9]+$");
	public void saveRippleCampaign(){
		try{
			if(StringUtil.isEmpty(rippleCampaign.getCampaignId())){
				sendErrorMessageToJsfScreen("Please enter a campaign Identifier");
			}else{
				String campaignId = rippleCampaign.getCampaignId();
				if(!myPattern.matcher(campaignId).find()){
					sendErrorMessageToJsfScreen("campaign Identifier must contain only number and alphabets");
				}
			}
			if(isValidInput()){
				rippleCampaign = aapService.saveRippleDonationCamapign(rippleCampaign.getCampaignId().toLowerCase(), rippleCampaign.getDescription(), getLoggedInUser().getId());	
			}
			
			
		}catch(Exception ex){
			sendErrorMessageToJsfScreen(ex);
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

	public DonationCampaignDto getRippleCampaign() {
		return rippleCampaign;
	}

	public void setRippleCampaign(DonationCampaignDto rippleCampaign) {
		this.rippleCampaign = rippleCampaign;
	}

	public boolean isRippleCampaignExists() {
		return rippleCampaignExists;
	}

	public void setRippleCampaignExists(boolean rippleCampaignExists) {
		this.rippleCampaignExists = rippleCampaignExists;
	}

	

}
