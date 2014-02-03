package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.map.MapModel;
import org.springframework.beans.BeanUtils;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.RoleDto;
import com.next.aap.web.dto.SearchMemberResultDto;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.jsf.beans.model.RoleDtoModel;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

/*
 @Component
 //@Scope("session")
 @SessionScoped
 @URLMappings(mappings = { @URLMapping(id = "adminEditUserRoleBean", pattern = "/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml") })
 @URLBeanName("adminEditUserRoleBean")
 */
@ManagedBean
@ViewScoped
@URLMapping(id = "adminEditUserRoleBean", beanName = "adminEditUserRoleBean", pattern = "/admin/roles", viewId = "/WEB-INF/jsf/admin_edituserrole.xhtml")
@URLBeanName("adminEditUserRoleBean")
public class AdminEditUserRoleBean extends BaseMultiPermissionAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private UserDto selectedUserForEditing;
	private UserDto searchedUser;
	private SearchMemberResultDto searchMemberResult;

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

	private boolean showResult;
	private boolean showSearchPanel;
	private List<CountryDto> countries;
	private String location;

	private Long selectedStateIdForRoles;
	private Long selectedDistrictIdForRoles;
	private Long selectedAcIdForRoles;
	private Long selectedPcIdForRoles;
	private Long selectedCountryIdForRoles;
	private Long selectedCountryRegionIdForRoles;
	private Long selectedCountryRegionAreaIdForRoles;

	private List<DistrictDto> memberDistrictList;
	private List<AssemblyConstituencyDto> memberAssemblyConstituencyList;
	private List<ParliamentConstituencyDto> memberParliamentConstituencyList;
	private List<CountryDto> memberCountryList;
	private List<CountryRegionDto> memberCountryRegionList;
	private List<CountryRegionAreaDto> memberCountryRegionAreaList;

	private boolean showRolesPanel;
	private boolean disableSaveMemberRoleButton = true;

	private RoleDtoModel userLocationRoles;
	List<RoleDto> editingUserLocationRoles;

	PostLocationType selectedPostLocationType;
	Long selectedPostLocationId;

	public AdminEditUserRoleBean() {
		super("/admin/roles", AppPermission.EDIT_USER_ROLES);
	}

	@URLAction(onPostback = false)
	public void init() throws Exception {
		if (!checkUserAccess()) {
			return;
		}

		if (countries == null || countries.isEmpty()) {
			countries = aapService.getAllCountries();
		}

		searchMemberResult = new SearchMemberResultDto();
		showResult = false;
		showSearchPanel = true;

		UserDto loggedInAdminUser = getLoggedInUser(true, buildLoginUrl("/admin/roles"));
		if (stateList == null || stateList.isEmpty()) {
			livingStateList = stateList = aapService.getAllStates();
		}
		if (loggedInAdminUser == null) {
			return;
		}
		searchedUser = new UserDto();
		searchedUser.setStateLivingId(loggedInAdminUser.getStateLivingId());
		searchedUser.setDistrictLivingId(loggedInAdminUser.getDistrictLivingId());
		searchedUser.setAssemblyConstituencyLivingId(loggedInAdminUser.getAssemblyConstituencyLivingId());
		searchedUser.setParliamentConstituencyLivingId(loggedInAdminUser.getParliamentConstituencyLivingId());

		searchedUser.setStateVotingId(loggedInAdminUser.getStateVotingId());
		searchedUser.setDistrictVotingId(loggedInAdminUser.getDistrictVotingId());
		searchedUser.setAssemblyConstituencyVotingId(loggedInAdminUser.getAssemblyConstituencyVotingId());
		searchedUser.setParliamentConstituencyVotingId(loggedInAdminUser.getParliamentConstituencyVotingId());
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1981);
		searchedUser.setDateOfBirth(cal.getTime());

		// Copy Logged In user to selectedUserForEditing
		selectedUserForEditing = new UserDto();
		searchedUser.setDateOfBirth(cal.getTime());

		if (searchedUser.getStateVotingId() != null) {
			enableDistrictCombo = true;
			enableParliamentConstituencyCombo = true;
			parliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(searchedUser.getStateVotingId());
			districtList = aapService.getAllDistrictOfState(searchedUser.getStateVotingId());
			if (searchedUser.getDistrictVotingId() != null) {
				enableAssemblyConstituencyCombo = true;
				assemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(searchedUser.getDistrictVotingId());
			}
		}
		if (searchedUser.getStateLivingId() != null) {

			enableLivingDistrictCombo = true;
			enableLivingParliamentConstituencyCombo = true;
			livingParliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(searchedUser.getStateLivingId());
			livingDistrictList = aapService.getAllDistrictOfState(searchedUser.getStateLivingId());
			if (searchedUser.getDistrictLivingId() != null) {
				enableLivingAssemblyConstituencyCombo = true;
				livingAssemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(searchedUser.getDistrictLivingId());
			}
		}

		switch (menuBean.getLocationType()) {
		case Global:
			memberCountryList = aapService.getAllCountries();
			break;
		case STATE:
			memberDistrictList = aapService.getAllDistrictOfState(menuBean.getAdminSelectedLocationId());
			memberParliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(menuBean.getAdminSelectedLocationId());
			break;
		case DISTRICT:
			memberAssemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(menuBean.getAdminSelectedLocationId());
			break;
		case COUNTRY:
			memberCountryRegionList = aapService.getAllCountryRegionsOfCountry(menuBean.getAdminSelectedLocationId());
			break;
		case REGION:
			memberCountryRegionAreaList = aapService.getAllCountryRegionAreasOfCountryRegion(menuBean.getAdminSelectedLocationId());
			break;
		}
		// assemblyConstituencyList =
		// aapService.getAllAssemblyConstituenciesOfState(selectedStateId);
		// selectedAssemblyConstituencyId =
		// loggedInUser.getAssemblyConstituencyVotingId();

	}

	public void createNewMember() {
		showSearchPanel = false;
		selectedUserForEditing = new UserDto();
		BeanUtils.copyProperties(searchedUser, selectedUserForEditing);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1981);
		selectedUserForEditing.setDateOfBirth(cal.getTime());
		MapModel draggableModel;
	}

	public void cancelSaveMemberRole() {
		showSearchPanel = true;
		resetRolePanel();
	}

	public boolean isMemberUpdateAllowed(UserDto userDto) {
		UserDto loggedInUser = getLoggedInUser();
		return !loggedInUser.getId().equals(userDto.getId());
	}

	public void saveProfile(ActionEvent event) {
		if (selectedPostLocationType == null ||(selectedPostLocationType != PostLocationType.Global && (selectedPostLocationId == null || selectedPostLocationId <= 0))) {
			sendErrorMessageToJsfScreen("Please select a Location");
		}

		if (isValidInput()) {
			if (editingUserLocationRoles == null || editingUserLocationRoles.isEmpty()) {
				aapService.removeAllUserRolesAtLocation(selectedUserForEditing.getId(), selectedPostLocationType, selectedPostLocationId);
			} else {
				aapService.saveUserRolesAtLocation(selectedUserForEditing.getId(), selectedPostLocationType, selectedPostLocationId, editingUserLocationRoles);
			}
			showSearchPanel = true;

			resetRolePanel();
			sendInfoMessageToJsfScreen("Roles updated succesfully");
		}
	}
	
	public void resetRolePanel(){
		showRolesPanel = false;

		userLocationRoles = null;
		editingUserLocationRoles = null;

		selectedPostLocationType = null;
		selectedPostLocationId = null;
		selectedAcIdForRoles = null;
		selectedDistrictIdForRoles = null;
		selectedPcIdForRoles = null;
		selectedStateIdForRoles = null;
		location = null;
		disableSaveMemberRoleButton = true;
	}

	public void searchMember() {
		searchMemberResult = aapService.searchMembers(searchedUser);
		showResult = true;
	}

	public void handleStateChange(AjaxBehaviorEvent event) {
		try {
			if (searchedUser.getStateVotingId() == 0 || searchedUser.getStateVotingId() == null) {
				enableDistrictCombo = false;
				enableParliamentConstituencyCombo = false;
				districtList = new ArrayList<>();
				parliamentConstituencyList = new ArrayList<>();
			} else {
				districtList = aapService.getAllDistrictOfState(searchedUser.getStateVotingId());
				parliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(searchedUser.getStateVotingId());
				enableDistrictCombo = true;
				enableParliamentConstituencyCombo = true;
				enableAssemblyConstituencyCombo = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleUserVotingStateChange(AjaxBehaviorEvent event) {
		try {
			if (searchedUser.getStateVotingId() == 0 || searchedUser.getStateVotingId() == null) {
				enableDistrictCombo = false;
				enableParliamentConstituencyCombo = false;
				districtList = new ArrayList<>();
				parliamentConstituencyList = new ArrayList<>();
			} else {
				districtList = aapService.getAllDistrictOfState(searchedUser.getStateVotingId());
				parliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(searchedUser.getStateVotingId());
				enableDistrictCombo = true;
				enableParliamentConstituencyCombo = true;
				enableAssemblyConstituencyCombo = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleLocationClick() {
		if (location.indexOf("Global-") == 0) {
			// User choose his/her location
			UserDto loggedInUser = getLoggedInUser();
			loadLocationRoles(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId(), loggedInUser.isSuperAdmin());
		} else {
			selectedPostLocationType = null;
			selectedPostLocationId = null;
			showRolesPanel = false;
			selectedAcIdForRoles =  null;
			selectedDistrictIdForRoles = null;
			selectedPcIdForRoles = null;
			selectedStateIdForRoles = null;
			disableSaveMemberRoleButton = true;
			// loadLocationRoles(menuBean.getLocationType(), 0L, false);
		}
	}

	private void loadLocationRoles(PostLocationType selectedLocationType, Long selectedLocationId, boolean getAllRolesOfLocation) {

		selectedPostLocationType = selectedLocationType;
		selectedPostLocationId = selectedLocationId;
		if ((selectedPostLocationType != null && selectedPostLocationType != PostLocationType.Global)
				&& (selectedLocationId == null || selectedLocationId <= 0)) {
			showRolesPanel = false;
			disableSaveMemberRoleButton = true;
			return;
		}
		showRolesPanel = true;
		disableSaveMemberRoleButton = false;
		// get Roles of LoggedInUserId in this location
		UserDto loggedInuser = getLoggedInUser();
		if (getAllRolesOfLocation) {
			userLocationRoles = new RoleDtoModel(aapService.getLocationRoles(selectedLocationType, selectedLocationId));
		} else {
			userLocationRoles = new RoleDtoModel(aapService.getUserRoles(loggedInuser.getId(), selectedLocationType, selectedLocationId));
		}

		// get Roles of editing User
		editingUserLocationRoles = aapService.getUserRoles(selectedUserForEditing.getId(), selectedLocationType, selectedLocationId);
		// and merge them to present to screen

	}

	public void handleLivingStateChange(AjaxBehaviorEvent event) {
		try {
			if (searchedUser.getStateLivingId() == 0 || searchedUser.getStateLivingId() == null) {
				enableLivingDistrictCombo = false;
				enableLivingParliamentConstituencyCombo = false;
				livingDistrictList = new ArrayList<>();
			} else {
				livingParliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(searchedUser.getStateLivingId());
				livingDistrictList = aapService.getAllDistrictOfState(searchedUser.getStateLivingId());
				enableLivingParliamentConstituencyCombo = true;
				enableLivingDistrictCombo = true;
				enableLivingAssemblyConstituencyCombo = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleUserLivingStateChange(AjaxBehaviorEvent event) {
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
				enableLivingAssemblyConstituencyCombo = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleDistrictChange(AjaxBehaviorEvent event) {
		try {
			if (searchedUser.getDistrictVotingId() == 0 || searchedUser.getDistrictVotingId() == null) {
				enableAssemblyConstituencyCombo = false;
				assemblyConstituencyList = new ArrayList<>();
			} else {
				enableAssemblyConstituencyCombo = true;
				assemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(searchedUser.getDistrictVotingId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleLivingDistrictChange(AjaxBehaviorEvent event) {
		try {
			if (searchedUser.getDistrictLivingId() == 0 || searchedUser.getDistrictLivingId() == null) {
				enableLivingAssemblyConstituencyCombo = false;
				livingAssemblyConstituencyList = new ArrayList<>();
			} else {
				enableLivingAssemblyConstituencyCombo = true;
				livingAssemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(searchedUser.getDistrictLivingId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleUserVotingDistrictChange(AjaxBehaviorEvent event) {
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

	public void handleUserLivingDistrictChange(AjaxBehaviorEvent event) {
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

	public boolean isDisableStateComobForRoles() {
		boolean returnValue = !"State".equals(location);
		return returnValue;
	}

	public boolean isRenderStateComboForRoles() {
		boolean returnValue = (PostLocationType.Global == menuBean.getLocationType());
		return returnValue;
	}

	public boolean isDisableDistrictComobForRoles() {
		boolean returnValue = !"District".equals(location);
		return returnValue;
	}

	public boolean isRenderDistrictComboForRoles() {
		boolean returnValue = (PostLocationType.STATE == menuBean.getLocationType());
		return returnValue;
	}
	
	public boolean isDisableCountryComobForRoles() {
		boolean returnValue = !"Country".equals(location);
		return returnValue;
	}

	public boolean isRenderCountryComboForRoles() {
		boolean returnValue = (PostLocationType.Global == menuBean.getLocationType());
		return returnValue;
	}

	public boolean isDisableCountryRegionComobForRoles() {
		boolean returnValue = !"CountryRegion".equals(location);
		return returnValue;
	}

	public boolean isRenderCountryRegionComboForRoles() {
		boolean returnValue = (PostLocationType.COUNTRY == menuBean.getLocationType());
		return returnValue;
	}

	public boolean isDisableAcComobForRoles() {
		boolean returnValue = !"Ac".equals(location);
		return returnValue;
	}

	public boolean isRenderAcComboForRoles() {
		boolean returnValue = (PostLocationType.DISTRICT == menuBean.getLocationType());
		return returnValue;
	}

	public boolean isDisablePcComobForRoles() {
		boolean returnValue = !"Pc".equals(location);
		return returnValue;
	}

	public boolean isRenderPcComboForRoles() {
		boolean returnValue = (PostLocationType.STATE == menuBean.getLocationType());
		return returnValue;
	}

	public boolean isShowMemberPanel() {
		return StringUtil.isEmpty(selectedUserForEditing.getMembershipNumber());
	}

	public void handleRoleStateChange(AjaxBehaviorEvent event) {
		try {
			loadLocationRoles(PostLocationType.STATE, selectedStateIdForRoles, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void handleRoleCountryChange(AjaxBehaviorEvent event) {
		try {
			loadLocationRoles(PostLocationType.COUNTRY, selectedCountryIdForRoles, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void handleRoleCountryRegionChange(AjaxBehaviorEvent event) {
		try {
			loadLocationRoles(PostLocationType.REGION, selectedCountryRegionIdForRoles, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleRoleDistrictChange(AjaxBehaviorEvent event) {
		try {
			loadLocationRoles(PostLocationType.DISTRICT, selectedDistrictIdForRoles, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleRoleAcChange(AjaxBehaviorEvent event) {
		try {
			loadLocationRoles(PostLocationType.AC, selectedAcIdForRoles, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleRolePcChange(AjaxBehaviorEvent event) {
		try {
			loadLocationRoles(PostLocationType.PC, selectedPcIdForRoles, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public UserDto getSelectedUserForEditing() {
		return selectedUserForEditing;
	}

	public void setSelectedUserForEditing(UserDto selectedUserForEditing) {
		this.selectedUserForEditing = selectedUserForEditing;
		showSearchPanel = false;
	}

	public UserDto getSearchedUser() {
		return searchedUser;
	}

	public void setSearchedUser(UserDto searchedUser) {
		this.searchedUser = searchedUser;
	}

	public SearchMemberResultDto getSearchMemberResult() {
		return searchMemberResult;
	}

	public void setSearchMemberResult(SearchMemberResultDto searchMemberResult) {
		this.searchMemberResult = searchMemberResult;
	}

	public boolean isShowResult() {
		return showResult;
	}

	public void setShowResult(boolean showResult) {
		this.showResult = showResult;
	}

	public boolean isShowSearchPanel() {
		return showSearchPanel;
	}

	public void setShowSearchPanel(boolean showSearchPanel) {
		this.showSearchPanel = showSearchPanel;
	}

	public List<CountryDto> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryDto> countries) {
		this.countries = countries;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCurrentLocationType() {
		return menuBean.getLocationType().name();
	}

	public String getCurrentLocationName() {
		return menuBean.getCurrentLocationName();
	}

	public Long getSelectedStateIdForRoles() {
		return selectedStateIdForRoles;
	}

	public void setSelectedStateIdForRoles(Long selectedStateIdForRoles) {
		this.selectedStateIdForRoles = selectedStateIdForRoles;
	}

	public Long getSelectedDistrictIdForRoles() {
		return selectedDistrictIdForRoles;
	}

	public void setSelectedDistrictIdForRoles(Long selectedDistrictIdForRoles) {
		this.selectedDistrictIdForRoles = selectedDistrictIdForRoles;
	}

	public Long getSelectedAcIdForRoles() {
		return selectedAcIdForRoles;
	}

	public void setSelectedAcIdForRoles(Long selectedAcIdForRoles) {
		this.selectedAcIdForRoles = selectedAcIdForRoles;
	}

	public Long getSelectedPcIdForRoles() {
		return selectedPcIdForRoles;
	}

	public void setSelectedPcIdForRoles(Long selectedPcIdForRoles) {
		this.selectedPcIdForRoles = selectedPcIdForRoles;
	}

	public List<DistrictDto> getMemberDistrictList() {
		return memberDistrictList;
	}

	public void setMemberDistrictList(List<DistrictDto> memberDistrictList) {
		this.memberDistrictList = memberDistrictList;
	}

	public List<AssemblyConstituencyDto> getMemberAssemblyConstituencyList() {
		return memberAssemblyConstituencyList;
	}

	public void setMemberAssemblyConstituencyList(List<AssemblyConstituencyDto> memberAssemblyConstituencyList) {
		this.memberAssemblyConstituencyList = memberAssemblyConstituencyList;
	}

	public List<ParliamentConstituencyDto> getMemberParliamentConstituencyList() {
		return memberParliamentConstituencyList;
	}

	public void setMemberParliamentConstituencyList(List<ParliamentConstituencyDto> memberParliamentConstituencyList) {
		this.memberParliamentConstituencyList = memberParliamentConstituencyList;
	}

	public boolean isShowRolesPanel() {
		return showRolesPanel;
	}

	public void setShowRolesPanel(boolean showRolesPanel) {
		this.showRolesPanel = showRolesPanel;
	}

	public RoleDtoModel getUserLocationRoles() {
		return userLocationRoles;
	}

	public void setUserLocationRoles(RoleDtoModel userLocationRoles) {
		this.userLocationRoles = userLocationRoles;
	}

	public List<RoleDto> getEditingUserLocationRoles() {
		return editingUserLocationRoles;
	}

	public void setEditingUserLocationRoles(List<RoleDto> editingUserLocationRoles) {
		this.editingUserLocationRoles = editingUserLocationRoles;
	}

	public boolean isDisableSaveMemberRoleButton() {
		return disableSaveMemberRoleButton;
	}

	public void setDisableSaveMemberRoleButton(boolean disableSaveMemberRoleButton) {
		this.disableSaveMemberRoleButton = disableSaveMemberRoleButton;
	}

	public List<CountryRegionDto> getMemberCountryRegionList() {
		return memberCountryRegionList;
	}

	public void setMemberCountryRegionList(List<CountryRegionDto> memberCountryRegionList) {
		this.memberCountryRegionList = memberCountryRegionList;
	}

	public List<CountryRegionAreaDto> getMemberCountryRegionAreaList() {
		return memberCountryRegionAreaList;
	}

	public void setMemberCountryRegionAreaList(List<CountryRegionAreaDto> memberCountryRegionAreaList) {
		this.memberCountryRegionAreaList = memberCountryRegionAreaList;
	}

	public List<CountryDto> getMemberCountryList() {
		return memberCountryList;
	}

	public void setMemberCountryList(List<CountryDto> memberCountryList) {
		this.memberCountryList = memberCountryList;
	}

	public Long getSelectedCountryIdForRoles() {
		return selectedCountryIdForRoles;
	}

	public void setSelectedCountryIdForRoles(Long selectedCountryIdForRoles) {
		this.selectedCountryIdForRoles = selectedCountryIdForRoles;
	}

	public Long getSelectedCountryRegionIdForRoles() {
		return selectedCountryRegionIdForRoles;
	}

	public void setSelectedCountryRegionIdForRoles(Long selectedCountryRegionIdForRoles) {
		this.selectedCountryRegionIdForRoles = selectedCountryRegionIdForRoles;
	}

	public Long getSelectedCountryRegionAreaIdForRoles() {
		return selectedCountryRegionAreaIdForRoles;
	}

	public void setSelectedCountryRegionAreaIdForRoles(Long selectedCountryRegionAreaIdForRoles) {
		this.selectedCountryRegionAreaIdForRoles = selectedCountryRegionAreaIdForRoles;
	}

}
