package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.CountryDto;
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
@Component
@Scope("session")
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

	private List<DistrictDto> memberDistrictList;
	private List<AssemblyConstituencyDto> memberAssemblyConstituencyList;
	private List<ParliamentConstituencyDto> memberParliamentConstituencyList;

	private boolean showRolesPanel;

	private RoleDtoModel userLocationRoles;
	List<RoleDto> editingUserLocationRoles;

	PostLocationType selectedPostLocationType;
	Long selectedPostLocationId;

	@Autowired
	private AapService aapService;
	@Autowired
	private MenuBean menuBean;

	public AdminEditUserRoleBean() {
		super("/admin/roles", AppPermission.EDIT_USER_ROLES);
	}

	@URLAction(onPostback = false)
	public void init() throws Exception {
		System.out.println("init " + aapService);
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

		// Copy Logged In user to selectedUserForEditing
		selectedUserForEditing = new UserDto();
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
			break;
		case STATE:
			memberDistrictList = aapService.getAllDistrictOfState(menuBean.getAdminSelectedLocationId());
			memberParliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(menuBean.getAdminSelectedLocationId());
		case DISTRICT:
			memberAssemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(menuBean.getAdminSelectedLocationId());

		}
		// assemblyConstituencyList =
		// aapService.getAllAssemblyConstituenciesOfState(selectedStateId);
		// selectedAssemblyConstituencyId =
		// loggedInUser.getAssemblyConstituencyVotingId();
		// System.out.println("loggedInUser.getDateOfBirth()="+loggedInUser.getDateOfBirth());

	}

	public void createNewMember() {
		showSearchPanel = false;
		selectedUserForEditing = new UserDto();
		BeanUtils.copyProperties(searchedUser, selectedUserForEditing);
	}

	public void cancelSaveMember() {
		showSearchPanel = true;
	}

	public boolean isMemberUpdateAllowed(UserDto userDto) {
		UserDto loggedInUser = getLoggedInUser();
		return !loggedInUser.getId().equals(userDto.getId());
	}

	public void saveProfile(ActionEvent event) {
		System.out.println("Saving User Roles");

		if (selectedPostLocationType == null || selectedPostLocationId == null || selectedPostLocationId <= 0) {
			sendErrorMessageToJsfScreen("Please select a Location");
		}

		if (isValidInput()) {
			if (editingUserLocationRoles == null || editingUserLocationRoles.isEmpty()) {
				System.out.println("Removing all Roles at this location");
				aapService.removeAllUserRolesAtLocation(selectedUserForEditing.getId(), selectedPostLocationType, selectedPostLocationId);
			} else {
				System.out.println("Adding Roles at this location");
				aapService.saveUserRolesAtLocation(selectedUserForEditing.getId(), selectedPostLocationType, selectedPostLocationId, editingUserLocationRoles);
			}
			showSearchPanel = true;

			showRolesPanel = false;

			userLocationRoles = null;
			editingUserLocationRoles = null;
			;

			selectedPostLocationType = null;
			selectedPostLocationId = null;
			selectedAcIdForRoles = null;
			selectedDistrictIdForRoles = null;
			selectedPcIdForRoles = null;
			selectedStateIdForRoles = null;
			location = null;
			sendInfoMessageToJsfScreen("Roles updated succesfully");
		} else {
			System.out.println("Not a valid Input");
		}
	}

	public void searchMember() {
		System.out.println("Search Member " + searchedUser.getAssemblyConstituencyLivingId() + " , " + searchedUser.getAssemblyConstituencyVotingId());
		searchMemberResult = aapService.searchMembers(searchedUser);
		showResult = true;
	}

	public void handleStateChange(AjaxBehaviorEvent event) {
		System.out.println("selected State Id = " + searchedUser.getStateVotingId());
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
		System.out.println("selected State Id = " + searchedUser.getStateVotingId());
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
		System.out.println("handleGlobalLocationClick " + location);
		if (location.indexOf("Global-") == 0) {
			// User choose his/her location
			loadLocationRoles(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId(), false);
		} else {
			selectedPostLocationType = null;
			selectedPostLocationId = null;
			showRolesPanel = false;
			selectedAcIdForRoles =  null;
			selectedDistrictIdForRoles = null;
			selectedPcIdForRoles = null;
			selectedStateIdForRoles = null;
			// loadLocationRoles(menuBean.getLocationType(), 0L, false);
		}
	}

	private void loadLocationRoles(PostLocationType selectedLocationType, Long selectedLocationId, boolean getAllRolesOfLocation) {

		selectedPostLocationType = selectedLocationType;
		selectedPostLocationId = selectedLocationId;
		if ((selectedPostLocationType != null && selectedPostLocationType != PostLocationType.Global)
				&& (selectedLocationId == null || selectedLocationId <= 0)) {
			showRolesPanel = false;
			return;
		}
		showRolesPanel = true;
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
		System.out.println("selected State Id = " + searchedUser.getStateLivingId());
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
		System.out.println("selected State Id = " + selectedUserForEditing.getStateLivingId());
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
		System.out.println("selected District Id = " + searchedUser.getDistrictVotingId());
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
		System.out.println("selected District Id = " + searchedUser.getDistrictLivingId());
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
		System.out.println("selected District Id = " + selectedUserForEditing.getDistrictVotingId());
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
		System.out.println("selected District Id = " + selectedUserForEditing.getDistrictLivingId());
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
		System.out.println("isRenderStateComobForRoles=" + returnValue);
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
		System.out.println("selectedStateIdForRoles = " + selectedStateIdForRoles);
		try {
			loadLocationRoles(PostLocationType.STATE, selectedStateIdForRoles, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleRoleDistrictChange(AjaxBehaviorEvent event) {
		System.out.println("selectedDistrictIdForRoles = " + selectedDistrictIdForRoles);
		try {
			loadLocationRoles(PostLocationType.DISTRICT, selectedDistrictIdForRoles, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleRoleAcChange(AjaxBehaviorEvent event) {
		System.out.println("selectedAcIdForRoles = " + selectedAcIdForRoles);
		try {
			loadLocationRoles(PostLocationType.AC, selectedAcIdForRoles, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleRolePcChange(AjaxBehaviorEvent event) {
		System.out.println("selectedPcIdForRoles = " + selectedPcIdForRoles);
		try {
			loadLocationRoles(PostLocationType.PC, selectedPcIdForRoles, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onClickNri() {
		System.out.println("nri" + selectedUserForEditing.isNri());
	}

	public void onClickMember() {
		System.out.println("member" + selectedUserForEditing.isMember());
	}

	public void onClickSameAsLiving() {
		System.out.println("sameAsLiving" + sameAsLiving);
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

}
