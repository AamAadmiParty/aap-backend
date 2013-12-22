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
import com.next.aap.core.persistance.dao.ParliamentConstituencyDao;
import com.next.aap.core.service.AapService;
import com.next.aap.web.controller.BaseController;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
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
	private List<DistrictDto> districtList;
	private List<AssemblyConstituencyDto> assemblyConstituencyList;
	private List<ParliamentConstituencyDto> parliamentConstituencyList;
	private Long selectedStateId;
	private Long selectedDistrictId;
	private Long selectedAssemblyConstituencyId;
	private Long selectedParliamentConstituencyId;
	private boolean enableDistrictCombo = false;
	private boolean enableAssemblyConstituencyCombo = false;
	private boolean enableParliamentConstituencyCombo = false;
					
	
	private List<StateDto> livingStateList;
	private List<DistrictDto> livingDistrictList;
	private List<AssemblyConstituencyDto> livingAssemblyConstituencyList;
	private List<ParliamentConstituencyDto> livingParliamentConstituencyList;
	private Long selectedLivingStateId;
	private Long selectedLivingDistrictId;
	private Long selectedLivingAssemblyConstituencyId;
	private Long selectedLivingParliamentConstituencyId;
	private boolean enableLivingDistrictCombo = false;
	private boolean enableLivingAssemblyConstituencyCombo = false;
	private boolean enableLivingParliamentConstituencyCombo = false;
	
	private boolean sameAsLiving;
	
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
		if(stateList == null || stateList.isEmpty()){
			livingStateList = stateList = aapService.getAllStates();
		}
		if(loggedInUser == null){
			return;
		}
		if(loggedInUser != null){
			selectedStateId = loggedInUser.getStateVotingId();
			selectedDistrictId = loggedInUser.getDistrictVotingId();
			selectedAssemblyConstituencyId = loggedInUser.getAssemblyConstituencyVotingId();
			selectedParliamentConstituencyId = loggedInUser.getParliamentConstituencyVotingId();
			if(selectedStateId != null){
				enableDistrictCombo = true;
				enableParliamentConstituencyCombo = true;
				parliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(selectedStateId);
				districtList = aapService.getAllDistrictOfState(selectedStateId);
				if(selectedDistrictId != null){
					enableAssemblyConstituencyCombo = true;
					assemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(selectedDistrictId);
				}
			}
			selectedLivingStateId = loggedInUser.getStateLivingId();
			selectedLivingDistrictId = loggedInUser.getDistrictLivingId();
			selectedLivingAssemblyConstituencyId = loggedInUser.getAssemblyConstituencyLivingId();
			selectedLivingParliamentConstituencyId = loggedInUser.getParliamentConstituencyLivingId();
			if(selectedLivingStateId != null){
				enableLivingDistrictCombo = true;
				enableLivingParliamentConstituencyCombo = true;
				livingParliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(selectedLivingStateId);
				livingDistrictList = aapService.getAllDistrictOfState(selectedLivingStateId);
				if(selectedLivingDistrictId != null){
					enableLivingAssemblyConstituencyCombo = true;
					livingAssemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(selectedLivingDistrictId);
				}
			}
			//assemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfState(selectedStateId);
			//selectedAssemblyConstituencyId = loggedInUser.getAssemblyConstituencyVotingId();
			System.out.println("loggedInUser.getDateOfBirth()="+loggedInUser.getDateOfBirth());
			if(loggedInUser.getDateOfBirth() != null){
				dateOfBirth = "";
				if(loggedInUser.getDateOfBirth().getDate() < 10){
					dateOfBirth = dateOfBirth+"0";
				}
				System.out.println("dateOfBirth="+dateOfBirth);
				dateOfBirth = dateOfBirth + loggedInUser.getDateOfBirth().getDate() +"/";
				if(loggedInUser.getDateOfBirth().getMonth() < 9){
					dateOfBirth = dateOfBirth+"0";
				}
				dateOfBirth = dateOfBirth + (loggedInUser.getDateOfBirth().getMonth() + 1) +"/";
				
				dateOfBirth = dateOfBirth + (loggedInUser.getDateOfBirth().getYear() + 1900);
				System.out.println("dateOfBirth="+dateOfBirth);
			}
			name = loggedInUser.getName();
			gender = loggedInUser.getGender();
		}else{
			enableAssemblyConstituencyCombo = false;
		}
	}

	public void saveProfile() {
		if(sameAsLiving){
			selectedStateId = selectedLivingStateId;
			selectedDistrictId = selectedLivingDistrictId;
			selectedAssemblyConstituencyId = selectedLivingAssemblyConstituencyId;
			selectedParliamentConstituencyId = selectedLivingParliamentConstituencyId;
		}
		System.out.println("selectedLivingStateId="+selectedLivingStateId);
		System.out.println("selectedLivingDistrictId="+selectedLivingDistrictId);
		System.out.println("selectedAssemblyConstituencyId="+selectedAssemblyConstituencyId);
		System.out.println("selectedLivingParliamentConstituencyId="+selectedLivingParliamentConstituencyId);
		System.out.println("selectedStateId="+selectedStateId);
		System.out.println("selectedDistrictId="+selectedDistrictId);
		System.out.println("selectedAssemblyConstituencyId="+selectedAssemblyConstituencyId);
		System.out.println("selectedParliamentConstituencyId="+selectedParliamentConstituencyId);
		
		if(selectedLivingStateId == null || selectedLivingStateId == 0 ){
			sendErrorMessageToJsfScreen("Please select State where you are living currently");
		}
		if(selectedLivingDistrictId == null || selectedLivingDistrictId ==0 ){
			sendErrorMessageToJsfScreen("Please select District where you are living currently");
		}
		if(selectedLivingAssemblyConstituencyId == null || selectedLivingAssemblyConstituencyId ==0 ){
			sendErrorMessageToJsfScreen("Please select Assembly Constituency where you are living currently");
		}
		if(selectedLivingParliamentConstituencyId == null || selectedLivingParliamentConstituencyId ==0 ){
			sendErrorMessageToJsfScreen("Please select Parliament Constituency where you are living currently");
		}
		if(selectedStateId == null || selectedStateId == 0){
			sendErrorMessageToJsfScreen("Please select State where you are registered as Voter");
		}
		if(selectedDistrictId == null || selectedDistrictId ==0 ){
			sendErrorMessageToJsfScreen("Please select District where you are registered as Voter");
		}
		if(selectedAssemblyConstituencyId == null || selectedAssemblyConstituencyId ==0 ){
			sendErrorMessageToJsfScreen("Please select Assembly Constituency where you registered as Voter");
		}
		if(selectedParliamentConstituencyId == null || selectedParliamentConstituencyId ==0 ){
			sendErrorMessageToJsfScreen("Please select Parliament Constituency where you registered as Voter");
		}
		Calendar dobCalendar = getDateOfBirthAsDate();
		if(dobCalendar == null){
			sendErrorMessageToJsfScreen("Please enter your Date of Birth");
		}
		/*
		else{
			Calendar todayCalendar = Calendar.getInstance();
			todayCalendar.add(Calendar.YEAR, -18);
			if(dobCalendar.after(todayCalendar)){
				sendErrorMessageToJsfScreen("You must be 18 to be eligible for vote");
			}
		}
		*/
		if(StringUtil.isEmptyOrWhitespace(loggedInUser.getName())){
			sendErrorMessageToJsfScreen("Please enter your full name");
		}
		if(isValidInput()){
			UserDto user = new UserDto();
			
			user.setAssemblyConstituencyLivingId(selectedLivingAssemblyConstituencyId);
			user.setAssemblyConstituencyVotingId(selectedAssemblyConstituencyId);
			
			user.setDistrictLivingId(selectedLivingDistrictId);
			user.setDistrictVotingId(selectedDistrictId);
			
			user.setParliamentConstituencyLivingId(selectedLivingParliamentConstituencyId);
			user.setParliamentConstituencyVotingId(selectedParliamentConstituencyId);
			
			user.setStateLivingId(selectedLivingStateId);
			user.setStateVotingId(selectedStateId);
			
			 
			user.setExternalId(loggedInUser.getExternalId());
			user.setId(loggedInUser.getId());
			
			user.setName(name);
			user.setGender(gender);
			//loggedInUser.setStateVotingId(selectedStateId);
			user.setDateOfBirth(dobCalendar.getTime());
			
			loggedInUser = aapService.saveUser(user);
			ssaveLoggedInUserInSession(loggedInUser);
			sendInfoMessageToJsfScreen("Profile saved succesfully.");
		}
		/*
		String url = BaseController.getFinalRedirectUrlFromSesion(getHttpServletRequest());
		if(!StringUtil.isEmpty(url)){
			BaseController.clearFinalRedirectUrlInSesion(getHttpServletRequest());
			redirect(buildUrl(url));
		}
		*/
	}

	public void handleStateChange(AjaxBehaviorEvent event) {
		System.out.println("selected State Id = "+selectedStateId);
		try {
			if(selectedStateId == 0 || selectedStateId == 36){
				enableDistrictCombo = false;
				enableParliamentConstituencyCombo = false;
				districtList = new ArrayList<>();
				parliamentConstituencyList = new ArrayList<>();
			}else{
				districtList = aapService.getAllDistrictOfState(selectedStateId);
				parliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(selectedStateId);
				enableDistrictCombo = true;
				enableParliamentConstituencyCombo = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleLivingStateChange(AjaxBehaviorEvent event) {
		System.out.println("selected State Id = "+selectedLivingStateId);
		try {
			if(selectedLivingStateId == 0 || selectedLivingStateId == 36){
				enableLivingDistrictCombo = false;
				enableLivingParliamentConstituencyCombo = false;
				livingDistrictList = new ArrayList<>();
			}else{
				livingParliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(selectedLivingStateId);
				livingDistrictList = aapService.getAllDistrictOfState(selectedLivingStateId);
				enableLivingParliamentConstituencyCombo = true;
				enableLivingDistrictCombo = true;
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
	public void handleLivingDistrictChange(AjaxBehaviorEvent event) {
		System.out.println("selected District Id = "+selectedLivingDistrictId);
		try {
			if(selectedLivingDistrictId == 0){
				enableLivingAssemblyConstituencyCombo = false;
				livingAssemblyConstituencyList = new ArrayList<>();
			}else{
				enableLivingAssemblyConstituencyCombo = true;
				livingAssemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(selectedLivingDistrictId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onClickSameAsLiving(){
		System.out.println("sameAsLiving"+sameAsLiving);
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
		return enableDistrictCombo;
	}

	public void setEnableDistrictCombo(boolean enableDistrictCombo) {
		this.enableDistrictCombo = enableDistrictCombo;
	}

	public List<StateDto> getLivingStateList() {
		return livingStateList;
	}

	public void setLivingStateList(List<StateDto> livingStateList) {
		this.livingStateList = livingStateList;
	}

	public List<DistrictDto> getLivingDistrictList() {
		return livingDistrictList;
	}

	public void setLivingDistrictList(List<DistrictDto> livingDistrictList) {
		this.livingDistrictList = livingDistrictList;
	}

	public List<AssemblyConstituencyDto> getLivingAssemblyConstituencyList() {
		return livingAssemblyConstituencyList;
	}

	public void setLivingAssemblyConstituencyList(List<AssemblyConstituencyDto> livingAssemblyConstituencyList) {
		this.livingAssemblyConstituencyList = livingAssemblyConstituencyList;
	}

	public Long getSelectedLivingStateId() {
		return selectedLivingStateId;
	}

	public void setSelectedLivingStateId(Long selectedLivingStateId) {
		this.selectedLivingStateId = selectedLivingStateId;
	}

	public Long getSelectedLivingDistrictId() {
		return selectedLivingDistrictId;
	}

	public void setSelectedLivingDistrictId(Long selectedLivingDistrictId) {
		this.selectedLivingDistrictId = selectedLivingDistrictId;
	}

	public Long getSelectedLivingAssemblyConstituencyId() {
		return selectedLivingAssemblyConstituencyId;
	}

	public void setSelectedLivingAssemblyConstituencyId(Long selectedLivingAssemblyConstituencyId) {
		this.selectedLivingAssemblyConstituencyId = selectedLivingAssemblyConstituencyId;
	}

	public boolean isEnableLivingDistrictCombo() {
		return enableLivingDistrictCombo;
	}

	public void setEnableLivingDistrictCombo(boolean enableLivingDistrictCombo) {
		this.enableLivingDistrictCombo = enableLivingDistrictCombo;
	}

	public boolean isEnableLivingAssemblyConstituencyCombo() {
		return enableLivingAssemblyConstituencyCombo;
	}

	public void setEnableLivingAssemblyConstituencyCombo(boolean enableLivingAssemblyConstituencyCombo) {
		this.enableLivingAssemblyConstituencyCombo = enableLivingAssemblyConstituencyCombo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isSameAsLiving() {
		return sameAsLiving;
	}

	public void setSameAsLiving(boolean sameAsLiving) {
		this.sameAsLiving = sameAsLiving;
	}

	public List<ParliamentConstituencyDto> getParliamentConstituencyList() {
		return parliamentConstituencyList;
	}

	public void setParliamentConstituencyList(List<ParliamentConstituencyDto> parliamentConstituencyList) {
		this.parliamentConstituencyList = parliamentConstituencyList;
	}

	public Long getSelectedParliamentConstituencyId() {
		return selectedParliamentConstituencyId;
	}

	public void setSelectedParliamentConstituencyId(Long selectedParliamentConstituencyId) {
		this.selectedParliamentConstituencyId = selectedParliamentConstituencyId;
	}

	public List<ParliamentConstituencyDto> getLivingParliamentConstituencyList() {
		return livingParliamentConstituencyList;
	}

	public void setLivingParliamentConstituencyList(List<ParliamentConstituencyDto> livingParliamentConstituencyList) {
		this.livingParliamentConstituencyList = livingParliamentConstituencyList;
	}

	public Long getSelectedLivingParliamentConstituencyId() {
		return selectedLivingParliamentConstituencyId;
	}

	public void setSelectedLivingParliamentConstituencyId(Long selectedLivingParliamentConstituencyId) {
		this.selectedLivingParliamentConstituencyId = selectedLivingParliamentConstituencyId;
	}

	public boolean isEnableLivingParliamentConstituencyCombo() {
		return enableLivingParliamentConstituencyCombo;
	}

	public void setEnableLivingParliamentConstituencyCombo(boolean enableLivingParliamentConstituencyCombo) {
		this.enableLivingParliamentConstituencyCombo = enableLivingParliamentConstituencyCombo;
	}

	public AapService getAapService() {
		return aapService;
	}

	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}

	public boolean isEnableParliamentConstituencyCombo() {
		return enableParliamentConstituencyCombo;
	}

	public void setEnableParliamentConstituencyCombo(boolean enableParliamentConstituencyCombo) {
		this.enableParliamentConstituencyCombo = enableParliamentConstituencyCombo;
	}

}
