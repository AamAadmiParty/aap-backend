package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.social.google.api.plus.Person.UrlType;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.AapService;
import com.next.aap.web.controller.BaseController;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.ocpsoft.pretty.faces.annotation.URLAction.PhaseId;

/*
@Component
//@Scope("session")
@SessionScoped
@URLMappings(mappings = { @URLMapping(id = "userProfileBean", pattern = "/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml") })
@URLBeanName("userProfileBean")
*/
@Component
@Scope("session")
@URLMapping(id = "userProfileBean", beanName="userProfileBean", pattern = "/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml")
@URLBeanName("userProfileBean")
public class UserProfileBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private UserDto loggedInUser;
	private List<StateDto> stateList;
	private Long selectedStateId;
	private List<AssemblyConstituencyDto> assemblyConstituencyList;
	private List<DistrictDto> districtList;
	private Long selectedAssemblyConstituencyId;
	private Long selectedDistrictId;
	private boolean enableAssemblyConstituencyCombo = false;
	private boolean enableDistrictCombo = false;
	private String dateOfBirth;
	private String name;
	private String countryCode;
	private String mobileNumber;
	private String gender;

	@Autowired
	private AapService aapService;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction
	public void init() throws Exception {
		System.out.println("init " + aapService);
		loggedInUser = getLoggedInUser(true,buildLoginUrl("/profile"));
		if(loggedInUser == null){
			return;
		}
		selectedStateId = 10L;
		if(stateList == null || stateList.isEmpty()){
			stateList = aapService.getAllStates();
		}
		System.out.println("stateList="+stateList);
		if(loggedInUser != null){
			selectedStateId = loggedInUser.getStateLivingId();
			if(selectedStateId != null){
				districtList = aapService.getAllDistrictOfState(selectedStateId);
			}
			//assemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfState(selectedStateId);
			//selectedAssemblyConstituencyId = loggedInUser.getAssemblyConstituencyVotingId();
			if(loggedInUser.getDateOfBirth() != null){
				dateOfBirth = "";
				if(loggedInUser.getDateOfBirth().getDate() < 10){
					dateOfBirth = dateOfBirth+"0";
				}
				dateOfBirth = dateOfBirth + loggedInUser.getDateOfBirth().getDate() +"/";
				if(loggedInUser.getDateOfBirth().getMonth() < 10){
					dateOfBirth = dateOfBirth+"0";
				}
				dateOfBirth = dateOfBirth + loggedInUser.getDateOfBirth().getMonth() +"/";
				
				dateOfBirth = dateOfBirth + (loggedInUser.getDateOfBirth().getYear() + 1900);
				
			}
		}else{
			enableAssemblyConstituencyCombo = false;
		}
	}

	public void saveProfile() {
		/*
		if(selectedStateId == null || selectedStateId ==0 || selectedStateId == 36){
			sendErrorMessageToJsfScreen("Please select State where you registered as Voter");
		}
		*/
		if(selectedAssemblyConstituencyId == null || selectedAssemblyConstituencyId ==0 ){
			sendErrorMessageToJsfScreen("Please select Assembly Constituency where you registered as Voter");
		}
		Calendar dobCalendar = getDateOfBirthAsDate();
		if(dobCalendar == null){
			sendErrorMessageToJsfScreen("Please enter your date of birth to be eligible to vote");
		}else{
			Calendar todayCalendar = Calendar.getInstance();
			todayCalendar.add(Calendar.YEAR, -18);
			if(dobCalendar.after(todayCalendar)){
				sendErrorMessageToJsfScreen("You must be 18 to be eligible for vote");
			}
		}
		if(StringUtil.isEmptyOrWhitespace(loggedInUser.getName())){
			sendErrorMessageToJsfScreen("Please enter your full name");
		}
		if(isValidInput()){
			loggedInUser.setAssemblyConstituencyVotingId(selectedAssemblyConstituencyId);
			//loggedInUser.setStateVotingId(selectedStateId);
			loggedInUser.setDateOfBirth(dobCalendar.getTime());
			
			//loggedInUser = aapService.saveUser(loggedInUser);
			ssaveLoggedInUserInSession(loggedInUser);
			sendInfoMessageToJsfScreen("Profile updated succesfully. If you have already voted and you have updated your location, then you may want to re vote.");
		}
		String url = BaseController.getFinalRedirectUrlFromSesion(getHttpServletRequest());
		if(!StringUtil.isEmpty(url)){
			BaseController.clearFinalRedirectUrlInSesion(getHttpServletRequest());
			redirect(buildUrl(url));
		}
	}

	public void handleStateChange(AjaxBehaviorEvent event) {
		System.out.println("selected State Id = "+selectedStateId);
		try {
			if(selectedStateId == 0 || selectedStateId == 36){
				enableDistrictCombo = false;
				districtList = new ArrayList<>();
			}else{
				enableDistrictCombo = true;
				districtList = aapService.getAllDistrictOfState(selectedStateId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleDistrictChange(AjaxBehaviorEvent event) {
		System.out.println("selected District Id = "+selectedDistrictId);
		try {
			if(selectedDistrictId == 0){
				enableAssemblyConstituencyCombo = false;
				assemblyConstituencyList = new ArrayList<>();
			}else{
				enableAssemblyConstituencyCombo = true;
				assemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(selectedDistrictId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UserDto getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(UserDto loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public List<StateDto> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateDto> stateList) {
		this.stateList = stateList;
	}

	public Long getSelectedStateId() {
		return selectedStateId;
	}

	public void setSelectedStateId(Long selectedStateId) {
		this.selectedStateId = selectedStateId;
	}

	public List<AssemblyConstituencyDto> getAssemblyConstituencyList() {
		return assemblyConstituencyList;
	}

	public void setAssemblyConstituencyList(
			List<AssemblyConstituencyDto> assemblyConstituencyList) {
		this.assemblyConstituencyList = assemblyConstituencyList;
	}

	public Long getSelectedAssemblyConstituencyId() {
		return selectedAssemblyConstituencyId;
	}

	public void setSelectedAssemblyConstituencyId(
			Long selectedAssemblyConstituencyId) {
		this.selectedAssemblyConstituencyId = selectedAssemblyConstituencyId;
	}

	public boolean isEnableAssemblyConstituencyCombo() {
		System.out.println("enableAssemblyConstituencyCombo="+enableAssemblyConstituencyCombo);
		return enableAssemblyConstituencyCombo;
	}

	public void setEnableAssemblyConstituencyCombo(
			boolean enableAssemblyConstituencyCombo) {
		this.enableAssemblyConstituencyCombo = enableAssemblyConstituencyCombo;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public Calendar getDateOfBirthAsDate() {
		if(StringUtil.isEmptyOrWhitespace(dateOfBirth)){
			return null;
		}
		String[] ddmmyyyy = dateOfBirth.split("/");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, Integer.parseInt(ddmmyyyy[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(ddmmyyyy[1]) - 1);
		cal.set(Calendar.YEAR, Integer.parseInt(ddmmyyyy[2]));
		return cal;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<DistrictDto> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictDto> districtList) {
		this.districtList = districtList;
	}

	public Long getSelectedDistrictId() {
		return selectedDistrictId;
	}

	public void setSelectedDistrictId(Long selectedDistrictId) {
		this.selectedDistrictId = selectedDistrictId;
	}

	public boolean isEnableDistrictCombo() {
		System.out.println("enableDistrictCombo="+enableDistrictCombo);
		return enableDistrictCombo;
	}

	public void setEnableDistrictCombo(boolean enableDistrictCombo) {
		this.enableDistrictCombo = enableDistrictCombo;
	}

}
