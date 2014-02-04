package com.next.aap.web.jsf.beans;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.BeanUtils;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.exception.AppException;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

/*
 @Component
 //@Scope("session")
 @SessionScoped
 @URLMappings(mappings = { @URLMapping(id = "userProfileBean", pattern = "/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml") })
 @URLBeanName("userProfileBean")
 */
@ManagedBean
//@Scope("session")
@ViewScoped
//@URLMapping(id = "userProfileBean", beanName = "userProfileBean", pattern = "/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml")
@URLMappings(mappings={
		@URLMapping(id = "userProfileBean1", beanName="userProfileBean", pattern = "/orig/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml"),
		@URLMapping(id = "userProfileBean2", beanName="userProfileBean", pattern = "/aapstyle/profile", viewId = "/WEB-INF/jsf/aapstyle/userprofile.xhtml"),
		@URLMapping(id = "userProfileBean3", beanName="userProfileBean", pattern = "/profile", viewId = "/WEB-INF/jsf/aapnewstyle/userprofile.xhtml")
		})
@URLBeanName("userProfileBean")
public class UserProfileBean extends BaseUserJsfBean {

	private static final long serialVersionUID = 1L;

	private UserDto selectedUserForEditing;

	@ManagedProperty("#{locationBean}")
	private LocationBean votingLocation;
	
	@ManagedProperty("#{locationBean}")
	private LocationBean livingLocation;
	
	@ManagedProperty("#{nriLocationBean}")
	private NriLocationBean nriLocationBean;

	private boolean sameAsLiving;

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true, buildLoginUrl("/profile"));
		if (loggedInUser == null) {
			return;
		}
		
		//Copy Logged In user to selectedUserForEditing
		selectedUserForEditing = new UserDto();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1981);
		selectedUserForEditing.setDateOfBirth(cal.getTime());

		BeanUtils.copyProperties(loggedInUser, selectedUserForEditing);
		
		refreshLocationData();
	}
	public String getVotingLocationMessage(){
		if(selectedUserForEditing == null || !selectedUserForEditing.isNri()){
			return "Select the place where you registered as voter";
		}else{
			return "Select the place where you registered as voter in india or your address of india";
		}
	}
	private void refreshLocationData() throws Exception{
		System.out.println("livingLocation="+livingLocation);
		System.out.println("votingLocation="+votingLocation);
		System.out.println("nriLocationBean="+nriLocationBean);
		livingLocation.init(selectedUserForEditing.getStateLivingId(), selectedUserForEditing.getDistrictLivingId(), selectedUserForEditing.getParliamentConstituencyLivingId(), selectedUserForEditing.getAssemblyConstituencyLivingId());
		votingLocation.init(selectedUserForEditing.getStateVotingId(), selectedUserForEditing.getDistrictVotingId(), selectedUserForEditing.getParliamentConstituencyVotingId(), selectedUserForEditing.getAssemblyConstituencyVotingId());
		nriLocationBean.init(selectedUserForEditing.isNri(), selectedUserForEditing.getNriCountryId(), selectedUserForEditing.getNriCountryRegionId(), selectedUserForEditing.getNriCountryRegionAreaId());
	}

	public void saveProfile(ActionEvent event) {
		//Copy location first
		selectedUserForEditing.setStateLivingId(livingLocation.getSelectedStateId());
		selectedUserForEditing.setDistrictLivingId(livingLocation.getSelectedDistrictId());
		selectedUserForEditing.setAssemblyConstituencyLivingId(livingLocation.getSelectedAcId());
		selectedUserForEditing.setParliamentConstituencyLivingId(livingLocation.getSelectedPcId());
		if(selectedUserForEditing.isNri()){
			selectedUserForEditing.setStateLivingId(selectedUserForEditing.getStateVotingId());
			selectedUserForEditing.setDistrictLivingId(selectedUserForEditing.getDistrictVotingId());
			selectedUserForEditing.setAssemblyConstituencyLivingId(selectedUserForEditing.getAssemblyConstituencyVotingId());
			selectedUserForEditing.setParliamentConstituencyLivingId(selectedUserForEditing.getParliamentConstituencyVotingId());
			
		}else{
			if (sameAsLiving) {
				selectedUserForEditing.setStateVotingId(selectedUserForEditing.getStateLivingId());
				selectedUserForEditing.setDistrictVotingId(selectedUserForEditing.getDistrictLivingId());
				selectedUserForEditing.setAssemblyConstituencyVotingId(selectedUserForEditing.getAssemblyConstituencyLivingId());
				selectedUserForEditing.setParliamentConstituencyVotingId(selectedUserForEditing.getParliamentConstituencyLivingId());
			}else{
				selectedUserForEditing.setStateVotingId(votingLocation.getSelectedStateId());
				selectedUserForEditing.setDistrictVotingId(votingLocation.getSelectedDistrictId());
				selectedUserForEditing.setAssemblyConstituencyVotingId(votingLocation.getSelectedAcId());
				selectedUserForEditing.setParliamentConstituencyVotingId(votingLocation.getSelectedPcId());
			}
		}
		
		selectedUserForEditing.setNri(nriLocationBean.isNri());
		selectedUserForEditing.setNriCountryId(nriLocationBean.getSelectedNriCountryId());
		selectedUserForEditing.setNriCountryRegionId(nriLocationBean.getSelectedNriCountryRegionId());
		selectedUserForEditing.setNriCountryRegionAreaId(nriLocationBean.getSelectedNriCountryRegionAreaId());
		selectedUserForEditing.setNriMobileNumber(nriLocationBean.getMobile());

		if(!selectedUserForEditing.isNri()){
			if (selectedUserForEditing.getStateLivingId() == null || selectedUserForEditing.getStateLivingId() == 0) {
				sendErrorMessageToJsfScreen("Please select State where you are living currently");
			}
			if (selectedUserForEditing.getDistrictLivingId() == null || selectedUserForEditing.getDistrictLivingId() == 0) {
				sendErrorMessageToJsfScreen("Please select District where you are living currently");
			}
			if (selectedUserForEditing.getAssemblyConstituencyLivingId() == null || selectedUserForEditing.getAssemblyConstituencyLivingId() == 0) {
				sendErrorMessageToJsfScreen("Please select Assembly Constituency where you are living currently");
			}
			if (selectedUserForEditing.getParliamentConstituencyLivingId() == null || selectedUserForEditing.getParliamentConstituencyLivingId() == 0) {
				sendErrorMessageToJsfScreen("Please select Parliament Constituency where you are living currently");
			}
		}
		
		if (selectedUserForEditing.getStateVotingId() == null || selectedUserForEditing.getStateVotingId() == 0) {
			sendErrorMessageToJsfScreen("Please select State where you are registered as Voter");
		}
		if (selectedUserForEditing.getDistrictVotingId() == null || selectedUserForEditing.getDistrictVotingId() == 0) {
			sendErrorMessageToJsfScreen("Please select District where you are registered as Voter");
		}
		if (selectedUserForEditing.getAssemblyConstituencyVotingId() == null || selectedUserForEditing.getAssemblyConstituencyVotingId() == 0) {
			sendErrorMessageToJsfScreen("Please select Assembly Constituency where you registered as Voter");
		}
		if (selectedUserForEditing.getParliamentConstituencyVotingId() == null || selectedUserForEditing.getParliamentConstituencyVotingId() == 0) {
			sendErrorMessageToJsfScreen("Please select Parliament Constituency where you registered as Voter");
		}
		if (selectedUserForEditing.isNri() ){
			if((selectedUserForEditing.getNriCountryId() == null || selectedUserForEditing.getNriCountryId() == 0)) {
				sendErrorMessageToJsfScreen("Please select Country where you Live");
			}
			if(selectedUserForEditing.isMember() && StringUtil.isEmpty(selectedUserForEditing.getPassportNumber())){
				sendErrorMessageToJsfScreen("Please enter passport number. Its Required for NRIs to become member.");
			}
		}
		if (selectedUserForEditing.getDateOfBirth() == null) {
			sendErrorMessageToJsfScreen("Please enter your Date of Birth");
		}
		if (StringUtil.isEmptyOrWhitespace(selectedUserForEditing.getName())) {
			sendErrorMessageToJsfScreen("Please enter your full name");
		}
		if (isValidInput()) {
			try {
				selectedUserForEditing = aapService.saveUser(selectedUserForEditing);
				ssaveLoggedInUserInSession(selectedUserForEditing);
				sendInfoMessageToJsfScreen("Profile saved succesfully.");
				refreshLocationData();
			} catch (AppException e) {
				sendErrorMessageToJsfScreen(e.getMessage(), e);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*
		 * String url =
		 * BaseController.getFinalRedirectUrlFromSesion(getHttpServletRequest
		 * ()); if(!StringUtil.isEmpty(url)){
		 * BaseController.clearFinalRedirectUrlInSesion
		 * (getHttpServletRequest()); redirect(buildUrl(url)); }
		 */
	}

	public void emptyCallToMakeItWork(AjaxBehaviorEvent event) {
		// This fucntion doesnt do anything but
	}
	
	public List<CountryDto> getCountries(){
		return aapService.getAllCountries();
	}
	

	public boolean isShowMemberPanel(){
		return StringUtil.isEmpty(selectedUserForEditing.getMembershipNumber());
	}

	public void onClickNri() {
	}
	public void onClickMember() {
	}

	public void onClickSameAsLiving() {
		if(sameAsLiving){
			votingLocation.setEnableAssemblyConstituencyCombo(false);
			votingLocation.setEnableDistrictCombo(false);
			votingLocation.setEnableParliamentConstituencyCombo(false);
			votingLocation.setEnableStateCombo(false);
		}else{
			votingLocation.setEnableAssemblyConstituencyCombo(false);
			votingLocation.setEnableDistrictCombo(false);
			votingLocation.setEnableParliamentConstituencyCombo(false);
			votingLocation.setEnableStateCombo(true);
		}
		
	}

	public boolean isSameAsLiving() {
		return sameAsLiving;
	}

	public void setSameAsLiving(boolean sameAsLiving) {
		this.sameAsLiving = sameAsLiving;
	}

	public AapService getAapService() {
		return aapService;
	}

	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}

	public UserDto getSelectedUserForEditing() {
		return selectedUserForEditing;
	}

	public void setSelectedUserForEditing(UserDto selectedUserForEditing) {
		this.selectedUserForEditing = selectedUserForEditing;
	}

	public LocationBean getVotingLocation() {
		return votingLocation;
	}

	public void setVotingLocation(LocationBean votingLocation) {
		this.votingLocation = votingLocation;
	}

	public LocationBean getLivingLocation() {
		return livingLocation;
	}

	public void setLivingLocation(LocationBean livingLocation) {
		this.livingLocation = livingLocation;
	}
	public NriLocationBean getNriLocationBean() {
		return nriLocationBean;
	}
	public void setNriLocationBean(NriLocationBean nriLocationBean) {
		this.nriLocationBean = nriLocationBean;
	}

}
