package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.next.aap.core.service.AapService;
import com.next.aap.web.controller.BaseController;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;

@ManagedBean
@SessionScoped
@URLBeanName("menuBean")
public class MenuBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private Long adminSelectedLocationId = -1L;
	private PostLocationType locationType = PostLocationType.NA;
	private StateDto selectedAdminState;
	private DistrictDto selectedAdminDistrict;
	private AssemblyConstituencyDto selectedAdminAc;
	private ParliamentConstituencyDto selectedAdminPc;
	private CountryDto selectedAdminCountry;
	private CountryRegionDto selectedAdminCountryRegion;

	private Long selectedStateId;
	private Long selectedDistrictId;
	private Long selectedAcId;
	private Long selectedPcId;
	private Long selectedCountryId;
	private Long selectedCountryRegiontId;

	@ManagedProperty("#{aapService}")
	protected AapService aapService;


	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public UserRolePermissionDto getUserRolePermissionInSesion(){
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		return getUserRolePermissionInSesion(httpServletRequest);
	}
	protected UserDto getLoggedInUser() {
		return getLoggedInUser(false, "");
	}

	protected UserDto getLoggedInUser(boolean redirect, String url) {
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		UserDto user = getLoggedInUserFromSesion(httpServletRequest);
		if (user == null) {
			if (redirect) {
				redirect(url);
			}
		}
		return user;
	}

	
	public boolean isAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if (userRolePermissionDto == null) {
			return false;
		}
		return userRolePermissionDto.isAdmin();

	}

	public boolean isSuperUser() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if (userRolePermissionDto == null) {
			return false;
		}
		return userRolePermissionDto.isSuperUser();
	}

	public boolean isStateAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if (userRolePermissionDto == null) {
			return false;
		}
		return userRolePermissionDto.isStateAdmin();
	}

	public boolean isDistrictAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if (userRolePermissionDto == null) {
			return false;
		}
		return userRolePermissionDto.isDistrictAdmin();
	}

	public boolean isAcAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if (userRolePermissionDto == null) {
			return false;
		}
		return userRolePermissionDto.isAcAdmin();
	}

	public boolean isPcAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if (userRolePermissionDto == null) {
			return false;
		}
		return userRolePermissionDto.isPcAdmin();
	}

	public boolean isGlobalAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if (userRolePermissionDto == null) {
			return false;
		}
		return userRolePermissionDto.isAllAdmin();
	}

	public String getContext() {
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		return httpServletRequest.getContextPath();
	}

	public boolean isLoggedIn() {
		UserDto user = getLoggedInUser();
		if (user == null) {
			return false;
		}
		return true;
	}

	public boolean isAllAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if (userRolePermissionDto == null) {
			return false;
		}
		return userRolePermissionDto.isAllAdmin();
	}

	public List<StateDto> getAdminStates() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return userRolePermissionDto.getAdminStates();
	}

	public List<DistrictDto> getAdminDistricts() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return userRolePermissionDto.getAdminDistricts();
	}

	public List<AssemblyConstituencyDto> getAdminAcs() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return userRolePermissionDto.getAdminAcs();
	}

	public List<ParliamentConstituencyDto> getAdminPcs() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return userRolePermissionDto.getAdminPcs();
	}

	public List<CountryDto> getAdminCountries() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return userRolePermissionDto.getAdminCountries();
	}

	public List<CountryRegionDto> getAdminCountryRegions() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return userRolePermissionDto.getAdminCountryRegions();
	}

	public void selectGlobal(ActionEvent event) {
		locationType = PostLocationType.Global;
		adminSelectedLocationId = -1L;
		buildAndRedirect("/admin/location");
	}

	public void selectCurrent(ActionEvent event) {
		if (locationType == null || locationType == PostLocationType.Global || adminSelectedLocationId <= 0) {
			sendErrorMessageToJsfScreen("Please select a Location");
		} else {
			switch (locationType) {
			case STATE:
				selectState(adminSelectedLocationId);
				break;
			case DISTRICT:
				selectDistrict(adminSelectedLocationId);
				break;
			case AC:
				selectAc(adminSelectedLocationId);
				break;
			case PC:
				selectPc(adminSelectedLocationId);
				break;
			}
			buildAndRedirect("/admin/location");
		}

	}

	public void selectState(ActionEvent event) {
		selectState((Long) event.getComponent().getAttributes().get("stateId"));
		buildAndRedirect("/admin/location");
	}

	private void selectState(Long stateId) {
		locationType = PostLocationType.STATE;
		adminSelectedLocationId = stateId;
		selectedAdminState = aapService.getStateById(adminSelectedLocationId);
	}

	public void selectDistrict(ActionEvent event) {
		locationType = PostLocationType.DISTRICT;
		selectDistrict((Long) event.getComponent().getAttributes().get("districtId"));
		buildAndRedirect("/admin/location");
	}

	public void selectDistrict(Long districtId) {
		locationType = PostLocationType.DISTRICT;
		adminSelectedLocationId = districtId;
		selectedAdminDistrict = aapService.getDistrictById(adminSelectedLocationId);
		selectedAdminState = aapService.getStateById(selectedAdminDistrict.getStateId());
	}

	public void selectAc(ActionEvent event) {
		locationType = PostLocationType.AC;
		selectAc((Long) event.getComponent().getAttributes().get("acId"));
		buildAndRedirect("/admin/location");
	}
	public void selectAc(Long acId) {
		locationType = PostLocationType.AC;
		adminSelectedLocationId = acId;
		selectedAdminAc = aapService.getAssemblyConstituencyById(adminSelectedLocationId);
		selectedAdminDistrict = aapService.getDistrictById(selectedAdminAc.getDistrictId());
		selectedAdminState = aapService.getStateById(selectedAdminDistrict.getStateId());
	}

	public void selectPc(ActionEvent event) {
		locationType = PostLocationType.PC;
		selectPc((Long) event.getComponent().getAttributes().get("pcId"));
		buildAndRedirect("/admin/location");
	}
	public void selectPc(Long pcId) {
		locationType = PostLocationType.PC;
		adminSelectedLocationId = pcId;
		selectedAdminPc = aapService.getParliamentConstituencyById(adminSelectedLocationId);
		selectedAdminState = aapService.getStateById(selectedAdminPc.getStateId());
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		UserDto user = getLoggedInUser();
		if(user == null){
			user = new UserDto();
			user.setProfilePic("https://cdn2.iconfinder.com/data/icons/ios-7-icons/50/user_male2-256.png");
			user.setName("Guest");
		}
		return user;
	}

	public void goToVoiceOfAapAdminPageFb() {
		if (isVoiceOfAapFbAllowed()) {
			buildAndRedirect("/admin/voiceofaapfb");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToVoiceOfAapAdminPageTwitter() {
		if (isVoiceOfAapTwitterAllowed()) {
			buildAndRedirect("/admin/voiceofaaptwitter");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToEmailAdminPageTwitter() {
		if (isEmailAllowed()) {
			buildAndRedirect("/admin/email");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToSmsAdminPageTwitter() {
		if (isSmsAllowed()) {
			buildAndRedirect("/admin/sms");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToGlobalDonationcampaignAdminPage() {
		if (isGlobalDonationCampaignAllowed()) {
			buildAndRedirect("/admin/globalcampaign");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToManageNewsPage() {
		if (isManageNewsAllowed()) {
			buildAndRedirect("/admin/news");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToManageBlogPage() {
		if (isManageBlogAllowed()) {
			buildAndRedirect("/admin/blog");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToManagePollPage() {
		if (isManagePollAllowed()) {
			buildAndRedirect("/admin/poll");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}
	public void goToManageEventPage() {
		if (isManageEventAllowed()) {
			buildAndRedirect("/admin/event");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToEditOfficeDetailPage() {
		if (isEditOfficeDetailAllowed()) {
			buildAndRedirect("/admin/office");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToManageMemberPage() {
		if (isManageMemberAllowed()) {
			buildAndRedirect("/admin/register");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToTreasuryPage() {
		if (isTreasuryAllowed()) {
			buildAndRedirect("/admin/treasury");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToTreasuryAccountDetailPage() {
		if (isTreasuryAllowed()) {
			buildAndRedirect("/admin/treasurydetail");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public void goToManageUserRolePage() {
		if (isManageUserRoleAllowed()) {
			buildAndRedirect("/admin/roles");
		} else {
			buildAndRedirect("/admin/notallowed");
		}
	}

	public boolean isCampaignAllowed() {
		return isVoiceOfAapFbAllowed() || isVoiceOfAapTwitterAllowed() || isEmailAllowed() || isSmsAllowed() || isGlobalDonationCampaignAllowed();
	}

	public boolean isVoiceOfAapFbAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isVoiceOfAapFbAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}

	public boolean isVoiceOfAapTwitterAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isVoiceOfAapTwitterAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}

	public boolean isContentAllowed() {
		return isManageNewsAllowed() || isManageBlogAllowed() || isManagePollAllowed() || isManageEventAllowed();
	}

	public boolean isManageNewsAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManageNewsAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}
	
	public boolean isSmsAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isSmsAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}
	public boolean isGlobalDonationCampaignAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isGlobalDonationCampaignAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}
	
	public boolean isEmailAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isEmailAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}

	public boolean isManageBlogAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManageBlogAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}

	public boolean isManagePollAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManagePollAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}
	public boolean isManageEventAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManageEventAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}
	
	public boolean isEditOfficeDetailAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isEditOfficeDetailAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}

	public boolean isMemberAllowed() {
		return isManageMemberAllowed();
	}

	public boolean isManageMemberAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManageMemberAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}

	public boolean isTreasuryAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isTreasuryAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}

	public boolean isAdminAllowed() {
		return isManageUserRoleAllowed() || isEditOfficeDetailAllowed();
	}

	public boolean isManageUserRoleAllowed() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isManageUserRoleAllowed(userRolePermissionDto, adminSelectedLocationId, locationType);
	}

	public PostLocationType getLocationType() {
		return locationType;
	}

	public String getCurrentLocationName() {
		if (locationType == null) {
			return "None";
		}
		switch (locationType) {
		case Global:
			return "Global";
		case STATE:
			return selectedAdminState.getName();
		case DISTRICT:
			return selectedAdminDistrict.getName();
		case AC:
			return selectedAdminAc.getName();
		case PC:
			return selectedAdminPc.getName();
		}
		return "No Location Selected";
	}

	public Long getAdminSelectedLocationId() {
		return adminSelectedLocationId;
	}

	public void setAdminSelectedLocationId(Long adminSelectedLocationId) {
		this.adminSelectedLocationId = adminSelectedLocationId;
	}

	public StateDto getSelectedAdminState() {
		return selectedAdminState;
	}

	public void setSelectedAdminState(StateDto selectedAdminState) {
		this.selectedAdminState = selectedAdminState;
	}

	public DistrictDto getSelectedAdminDistrict() {
		return selectedAdminDistrict;
	}

	public void setSelectedAdminDistrict(DistrictDto selectedAdminDistrict) {
		this.selectedAdminDistrict = selectedAdminDistrict;
	}

	public AssemblyConstituencyDto getSelectedAdminAc() {
		return selectedAdminAc;
	}

	public void setSelectedAdminAc(AssemblyConstituencyDto selectedAdminAc) {
		this.selectedAdminAc = selectedAdminAc;
	}

	public ParliamentConstituencyDto getSelectedAdminPc() {
		return selectedAdminPc;
	}

	public void setSelectedAdminPc(ParliamentConstituencyDto selectedAdminPc) {
		this.selectedAdminPc = selectedAdminPc;
	}

	public void setLocationType(PostLocationType locationType) {
		this.locationType = locationType;
	}

	public CountryDto getSelectedAdminCountry() {
		return selectedAdminCountry;
	}

	public void setSelectedAdminCountry(CountryDto selectedAdminCountry) {
		this.selectedAdminCountry = selectedAdminCountry;
	}

	public CountryRegionDto getSelectedAdminCountryRegion() {
		return selectedAdminCountryRegion;
	}

	public void setSelectedAdminCountryRegion(CountryRegionDto selectedAdminCountryRegion) {
		this.selectedAdminCountryRegion = selectedAdminCountryRegion;
	}

	public List<CountryDto> getNriCountries() {
		return aapService.getNriCountries();
	}

	public List<StateDto> getStates() {
		return aapService.getAllStates();
	}

	public List<DistrictDto> getDistrictOfState() {
		if (selectedStateId == null) {
			return new ArrayList<>();
		}
		return aapService.getAllDistrictOfState(selectedStateId);
	}

	public List<AssemblyConstituencyDto> getAssemblyConstituencyOfDistrict() {
		if (selectedDistrictId == null) {
			return new ArrayList<>();
		}
		return aapService.getAllAssemblyConstituenciesOfDistrict(selectedDistrictId);
	}

	public List<ParliamentConstituencyDto> getParliamentConstituencyOfState() {
		if (selectedStateId == null) {
			return new ArrayList<>();
		}
		return aapService.getAllParliamentConstituenciesOfState(selectedStateId);
	}

	public List<CountryRegionDto> getCountryRegionOfCountry() {
		if (selectedCountryId == null) {
			return new ArrayList<>();
		}
		return aapService.getAllCountryRegionsOfCountry(selectedCountryId);
	}

	public List<CountryRegionAreaDto> getCountryRegionAreaOfCountryRegion() {
		if (selectedCountryRegiontId == null) {
			return new ArrayList<>();
		}
		return aapService.getAllCountryRegionAreasOfCountryRegion(selectedCountryRegiontId);
	}

	public void handleStateChange(AjaxBehaviorEvent event) {
		if (selectedStateId == 0 || selectedStateId == null) {
			locationType = PostLocationType.NA;
			adminSelectedLocationId = 0L;
		} else {
			locationType = PostLocationType.STATE;
			adminSelectedLocationId = selectedStateId;
		}
	}

	public void handleDistrictChange(AjaxBehaviorEvent event) {
		if (selectedDistrictId == 0 || selectedDistrictId == null) {
			locationType = PostLocationType.NA;
			adminSelectedLocationId = 0L;
		} else {
			locationType = PostLocationType.DISTRICT;
			adminSelectedLocationId = selectedDistrictId;
		}
	}

	public void handleAcChange(AjaxBehaviorEvent event) {
		if (selectedAcId == 0 || selectedAcId == null) {
			locationType = PostLocationType.NA;
			adminSelectedLocationId = 0L;
		} else {
			locationType = PostLocationType.AC;
			adminSelectedLocationId = selectedAcId;
		}
	}

	public void handlePcChange(AjaxBehaviorEvent event) {
		if (selectedPcId == 0 || selectedPcId == null) {
			locationType = PostLocationType.NA;
			adminSelectedLocationId = 0L;
		} else {
			locationType = PostLocationType.PC;
			adminSelectedLocationId = selectedPcId;
		}
	}

	public void handleCountryChange(AjaxBehaviorEvent event) {
		if (selectedCountryId == 0 || selectedCountryId == null) {
			locationType = PostLocationType.NA;
			adminSelectedLocationId = 0L;
		} else {
			locationType = PostLocationType.COUNTRY;
			adminSelectedLocationId = selectedCountryId;
		}
	}

	public void handleCountryRegionChange(AjaxBehaviorEvent event) {
		if (selectedCountryRegiontId == 0 || selectedCountryRegiontId == null) {
			locationType = PostLocationType.NA;
			adminSelectedLocationId = 0L;
		} else {
			locationType = PostLocationType.REGION;
			adminSelectedLocationId = selectedCountryRegiontId;
		}
	}

	public Long getSelectedDistrictId() {
		return selectedDistrictId;
	}

	public void setSelectedDistrictId(Long selectedDistrictId) {
		this.selectedDistrictId = selectedDistrictId;
	}

	public Long getSelectedAcId() {
		return selectedAcId;
	}

	public void setSelectedAcId(Long selectedAcId) {
		this.selectedAcId = selectedAcId;
	}

	public Long getSelectedPcId() {
		return selectedPcId;
	}

	public void setSelectedPcId(Long selectedPcId) {
		this.selectedPcId = selectedPcId;
	}

	public Long getSelectedCountrytId() {
		return selectedCountryId;
	}

	public void setSelectedCountrytId(Long selectedCountrytId) {
		this.selectedCountryId = selectedCountrytId;
	}

	public Long getSelectedCountryRegiontId() {
		return selectedCountryRegiontId;
	}

	public void setSelectedCountryRegiontId(Long selectedCountryRegiontId) {
		this.selectedCountryRegiontId = selectedCountryRegiontId;
	}

	public Long getSelectedStateId() {
		return selectedStateId;
	}

	public void setSelectedStateId(Long selectedStateId) {
		this.selectedStateId = selectedStateId;
	}

	public Long getSelectedCountryId() {
		return selectedCountryId;
	}

	public void setSelectedCountryId(Long selectedCountryId) {
		this.selectedCountryId = selectedCountryId;
	}
	public AapService getAapService() {
		return aapService;
	}
	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}
}
