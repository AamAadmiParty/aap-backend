package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.InterestDto;
import com.next.aap.web.dto.InterestGroupDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.jsf.beans.model.InterestGroupDtoModel;
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
@Component
//@Scope("session")
@ViewScoped
//@URLMapping(id = "userProfileBean", beanName = "userProfileBean", pattern = "/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml")
@URLMappings(mappings={
		@URLMapping(id = "userProfileBean1", beanName="userProfileBean", pattern = "/orig/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml"),
		@URLMapping(id = "userProfileBean2", beanName="userProfileBean", pattern = "/profile", viewId = "/WEB-INF/jsf/aapstyle/userprofile.xhtml")
		})
@URLBeanName("userProfileBean")
public class UserProfileBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private UserDto selectedUserForEditing;

	private List<StateDto> stateList;
	private List<DistrictDto> districtList;
	private List<AssemblyConstituencyDto> assemblyConstituencyList;
	private List<ParliamentConstituencyDto> parliamentConstituencyList;
	private boolean enableDistrictCombo = false;
	private boolean enableAssemblyConstituencyCombo = false;
	private boolean enableParliamentConstituencyCombo = false;

	private List<StateDto> livingStateList;
	private List<DistrictDto> livingDistrictList;
	private List<AssemblyConstituencyDto> livingAssemblyConstituencyList;
	private List<ParliamentConstituencyDto> livingParliamentConstituencyList;
	private boolean enableLivingDistrictCombo = false;
	private boolean enableLivingAssemblyConstituencyCombo = false;
	private boolean enableLivingParliamentConstituencyCombo = false;

	private boolean sameAsLiving;

	private List<CountryDto> countries;
	private List<CountryDto> nriCountries;
	private List<CountryRegionDto> nriCountryRegions;
	private List<CountryRegionAreaDto> nriCountryRegionAreas;
	private boolean disableNriCountryRegionCombo = true;
	private boolean disableNriCountryRegionAreaCombo = true;
	private List<InterestGroupDtoModel> interestGroupDtoModels;
	
	private Map<Long, Boolean> selectedInterestMap = new HashMap<Long, Boolean>();

	@Autowired
	private AapService aapService;

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {
		Long startTime = System.currentTimeMillis();
		UserDto loggedInUser = getLoggedInUser(true, buildLoginUrl("/profile"));
		if (loggedInUser == null) {
			return;
		}
		List<InterestGroupDto> interestGroups = aapService.getAllVolunterInterests();
		selectedInterestMap.clear();
		interestGroupDtoModels = new ArrayList<>();
		if(interestGroups != null && !interestGroups.isEmpty()){
			for(InterestGroupDto oneInterestGroupDto:interestGroups){
				interestGroupDtoModels.add(new InterestGroupDtoModel(oneInterestGroupDto));
			}
		}
		
		if (stateList == null || stateList.isEmpty()) {
			livingStateList = stateList = aapService.getAllStates();
		}
		if (countries == null || countries.isEmpty()) {
			countries = aapService.getAllCountries();
			nriCountries = new ArrayList<>(countries);
			//Remove India from Nri Country List
			Iterator<CountryDto> iterator = nriCountries.iterator();
			while(iterator.hasNext()){
				if(iterator.next().getName().equalsIgnoreCase("India")){
					iterator.remove();
					break;
				}
			}
		}
		disableNriCountryRegionCombo = true;
		disableNriCountryRegionAreaCombo = true;
		//Copy Logged In user to selectedUserForEditing
		selectedUserForEditing = new UserDto();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1981);
		selectedUserForEditing.setDateOfBirth(cal.getTime());

		BeanUtils.copyProperties(loggedInUser, selectedUserForEditing);
		
		if(selectedUserForEditing.getNriCountryId() != null){
			disableNriCountryRegionCombo = false;
			nriCountryRegions = aapService.getAllCountryRegionsOfCountry(selectedUserForEditing.getNriCountryId());
		}
		if(selectedUserForEditing.getNriCountryRegionId() != null){
			disableNriCountryRegionAreaCombo = false;
			nriCountryRegionAreas = aapService.getAllCountryRegionAreasOfCountryRegion(selectedUserForEditing.getNriCountryRegionId());
		}

		if (selectedUserForEditing.getStateVotingId() != null) {
			enableDistrictCombo = true;
			enableParliamentConstituencyCombo = true;
			parliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(selectedUserForEditing.getStateVotingId());
			districtList = aapService.getAllDistrictOfState(selectedUserForEditing.getStateVotingId());
			if (selectedUserForEditing.getDistrictVotingId() != null) {
				enableAssemblyConstituencyCombo = true;
				assemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(selectedUserForEditing.getDistrictVotingId());
			}
		}
		if (selectedUserForEditing.getStateLivingId() != null) {
			enableLivingDistrictCombo = true;
			enableLivingParliamentConstituencyCombo = true;
			livingParliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(selectedUserForEditing.getStateLivingId());
			livingDistrictList = aapService.getAllDistrictOfState(selectedUserForEditing.getStateLivingId());
			if (selectedUserForEditing.getDistrictLivingId() != null) {
				enableLivingAssemblyConstituencyCombo = true;
				livingAssemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(selectedUserForEditing.getDistrictLivingId());
			}
		}
		Long endTime = System.currentTimeMillis();
		System.out.println("Total Init Time = " +(endTime - startTime)+" ms");
		
	}

	public void saveProfile(ActionEvent event) {
		System.out.println("Total Selected : " + selectedInterestMap.size());
		for(Entry<Long, Boolean> oneInterest:selectedInterestMap.entrySet()){
			System.out.println("OneInterest : " + oneInterest.getKey() +" , "+oneInterest.getValue());
		}
		if (sameAsLiving) {
			selectedUserForEditing.setStateVotingId(selectedUserForEditing.getStateLivingId());
			selectedUserForEditing.setDistrictVotingId(selectedUserForEditing.getDistrictLivingId());
			selectedUserForEditing.setAssemblyConstituencyVotingId(selectedUserForEditing.getAssemblyConstituencyLivingId());
			selectedUserForEditing.setParliamentConstituencyVotingId(selectedUserForEditing.getParliamentConstituencyLivingId());
		}

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
		/*
		 * else{ Calendar todayCalendar = Calendar.getInstance();
		 * todayCalendar.add(Calendar.YEAR, -18);
		 * if(dobCalendar.after(todayCalendar)){
		 * sendErrorMessageToJsfScreen("You must be 18 to be eligible for vote"
		 * ); } }
		 */
		if (StringUtil.isEmptyOrWhitespace(selectedUserForEditing.getName())) {
			sendErrorMessageToJsfScreen("Please enter your full name");
		}
		if (isValidInput()) {
			selectedUserForEditing = aapService.saveUser(selectedUserForEditing);
			ssaveLoggedInUserInSession(selectedUserForEditing);
			sendInfoMessageToJsfScreen("Profile saved succesfully.");
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

	public void handleStateChange(AjaxBehaviorEvent event) {
		try {
			if (selectedUserForEditing.getStateVotingId() == 0 || selectedUserForEditing.getStateVotingId() == null) {
				enableDistrictCombo = false;
				enableParliamentConstituencyCombo = false;
				districtList = new ArrayList<>();
				parliamentConstituencyList = new ArrayList<>();
			} else {
				districtList = aapService.getAllDistrictOfState(selectedUserForEditing.getStateVotingId());
				parliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(selectedUserForEditing.getStateVotingId());
				enableDistrictCombo = true;
				enableParliamentConstituencyCombo = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleLivingStateChange(AjaxBehaviorEvent event) {
		try {
			if (selectedUserForEditing.getStateLivingId() == 0 || selectedUserForEditing.getStateLivingId() == null) {
				enableLivingDistrictCombo = false;
				enableLivingParliamentConstituencyCombo = false;
				livingDistrictList = new ArrayList<>();
			} else {
				livingParliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(selectedUserForEditing.getStateLivingId());
				livingDistrictList = aapService.getAllDistrictOfState(selectedUserForEditing.getStateLivingId());
				enableLivingParliamentConstituencyCombo = true;
				enableLivingDistrictCombo = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleDistrictChange(AjaxBehaviorEvent event) {
		try {
			if (selectedUserForEditing.getDistrictVotingId() == 0 || selectedUserForEditing.getDistrictVotingId() == null) {
				enableAssemblyConstituencyCombo = false;
				assemblyConstituencyList = new ArrayList<>();
			} else {
				enableAssemblyConstituencyCombo = true;
				assemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(selectedUserForEditing.getDistrictVotingId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleNriCountryChange(AjaxBehaviorEvent event) {
		try {
			if (selectedUserForEditing.getNriCountryId() == 0 || selectedUserForEditing.getNriCountryId() == null) {
				disableNriCountryRegionCombo = true;
				nriCountryRegions = new ArrayList<>();
			} else {
				disableNriCountryRegionCombo = false;
				nriCountryRegions = aapService.getAllCountryRegionsOfCountry(selectedUserForEditing.getNriCountryId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleNriCountryRegionChange(AjaxBehaviorEvent event) {
		try {
			if (selectedUserForEditing.getNriCountryRegionId() == 0 || selectedUserForEditing.getNriCountryRegionId() == null) {
				disableNriCountryRegionAreaCombo = true;
				nriCountryRegions = new ArrayList<>();
			} else {
				disableNriCountryRegionAreaCombo = false;
				nriCountryRegionAreas = aapService.getAllCountryRegionAreasOfCountryRegion(selectedUserForEditing.getNriCountryRegionId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleLivingDistrictChange(AjaxBehaviorEvent event) {
		try {
			if (selectedUserForEditing.getDistrictLivingId() == 0 || selectedUserForEditing.getDistrictLivingId() == null) {
				enableLivingAssemblyConstituencyCombo = false;
				livingAssemblyConstituencyList = new ArrayList<>();
			} else {
				enableLivingAssemblyConstituencyCombo = true;
				livingAssemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(selectedUserForEditing.getDistrictLivingId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isShowMemberPanel(){
		return StringUtil.isEmpty(selectedUserForEditing.getMembershipNumber());
	}

	public void onClickNri() {
	}
	public void onClickMember() {
	}

	public void onClickSameAsLiving() {
	}

	public List<StateDto> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateDto> stateList) {
		this.stateList = stateList;
	}

	public List<AssemblyConstituencyDto> getAssemblyConstituencyList() {
		return assemblyConstituencyList;
	}

	public void setAssemblyConstituencyList(List<AssemblyConstituencyDto> assemblyConstituencyList) {
		this.assemblyConstituencyList = assemblyConstituencyList;
	}

	public boolean isEnableAssemblyConstituencyCombo() {
		return enableAssemblyConstituencyCombo;
	}

	public void setEnableAssemblyConstituencyCombo(boolean enableAssemblyConstituencyCombo) {
		this.enableAssemblyConstituencyCombo = enableAssemblyConstituencyCombo;
	}

	public List<DistrictDto> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictDto> districtList) {
		this.districtList = districtList;
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

	public List<ParliamentConstituencyDto> getLivingParliamentConstituencyList() {
		return livingParliamentConstituencyList;
	}

	public void setLivingParliamentConstituencyList(List<ParliamentConstituencyDto> livingParliamentConstituencyList) {
		this.livingParliamentConstituencyList = livingParliamentConstituencyList;
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

	public List<CountryDto> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryDto> countries) {
		this.countries = countries;
	}

	public UserDto getSelectedUserForEditing() {
		return selectedUserForEditing;
	}

	public void setSelectedUserForEditing(UserDto selectedUserForEditing) {
		this.selectedUserForEditing = selectedUserForEditing;
	}

	public List<CountryDto> getNriCountries() {
		return nriCountries;
	}

	public void setNriCountries(List<CountryDto> nriCountries) {
		this.nriCountries = nriCountries;
	}

	public List<CountryRegionDto> getNriCountryRegions() {
		return nriCountryRegions;
	}

	public void setNriCountryRegions(List<CountryRegionDto> nriCountryRegions) {
		this.nriCountryRegions = nriCountryRegions;
	}

	public List<CountryRegionAreaDto> getNriCountryRegionAreas() {
		return nriCountryRegionAreas;
	}

	public void setNriCountryRegionAreas(List<CountryRegionAreaDto> nriCountryRegionAreas) {
		this.nriCountryRegionAreas = nriCountryRegionAreas;
	}

	public boolean isDisableNriCountryRegionCombo() {
		return disableNriCountryRegionCombo;
	}

	public void setDisableNriCountryRegionCombo(boolean disableNriCountryRegionCombo) {
		this.disableNriCountryRegionCombo = disableNriCountryRegionCombo;
	}

	public boolean isDisableNriCountryRegionAreaCombo() {
		return disableNriCountryRegionAreaCombo;
	}

	public void setDisableNriCountryRegionAreaCombo(boolean disableNriCountryRegionAreaCombo) {
		this.disableNriCountryRegionAreaCombo = disableNriCountryRegionAreaCombo;
	}

	public List<InterestGroupDtoModel> getInterestGroupDtoModels() {
		return interestGroupDtoModels;
	}

	public void setInterestGroupDtoModels(List<InterestGroupDtoModel> interestGroupDtoModels) {
		this.interestGroupDtoModels = interestGroupDtoModels;
	}

	public Map<Long, Boolean> getSelectedInterestMap() {
		return selectedInterestMap;
	}

	public void setSelectedInterestMap(Map<Long, Boolean> selectedInterestMap) {
		this.selectedInterestMap = selectedInterestMap;
	}
	
}
