package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;
import org.springframework.beans.BeanUtils;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.AapService;
import com.next.aap.core.util.DataUtil;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.SearchMemberResultDto;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ManagedBean
@ViewScoped
@URLMapping(id = "adminSearchMemberBean", beanName = "adminSearchMemberBean", pattern = "/admin/search", viewId = "/WEB-INF/jsf/admin_searchmember.xhtml")
@URLBeanName("adminSearchMemberBean")
public class AdminSearchMemberBean extends BaseMultiPermissionAdminJsfBean {
	
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
	private boolean showVolunteerPanel = false;
    private boolean showSearchVolunteerPanel = false;
	
	private boolean disableMemberCheckForSelectedUserEditing = false;
	private boolean disableVolunteerCheckForSelectedUserEditing = false;

	private List<CountryDto> countries;
	
	private boolean showResult;
	private boolean showSearchPanel;
	
	private double fee = DataUtil.MEMBERSHIP_FEE;
	
	@ManagedProperty("#{volunteerBean}")
	private VolunteerBean volunteerBean;
	
    @ManagedProperty("#{volunteerBean}")
    private VolunteerBean searchVolunteerBean;
	
	public AdminSearchMemberBean(){
        super("/admin/search", AppPermission.ADD_MEMBER, AppPermission.VIEW_MEMBER, AppPermission.UPDATE_GLOBAL_MEMBER, AppPermission.UPDATE_MEMBER);
	}

	@URLAction(onPostback = false)
	public void init() throws Exception {
		if(!checkUserAccess()){
			return;
		}
		volunteerBean.init(null);
        searchVolunteerBean.init(null);
		searchMemberResult = new SearchMemberResultDto();
		showResult = false;
		showSearchPanel = true;
		
		UserDto loggedInAdminUser = getLoggedInUser(true, buildLoginUrl("/admin/register"));
		if (stateList == null || stateList.isEmpty()) {
			livingStateList = stateList = aapService.getAllStates();
		}
		if (countries == null || countries.isEmpty()) {
			countries = aapService.getAllCountries();
		}
		if (loggedInAdminUser == null) {
			return;
		}
		searchedUser = new UserDto();
		/*
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
		*/

		//Copy Logged In user to selectedUserForEditing
		selectedUserForEditing = new UserDto();
        // selectedUserForEditing.setDateOfBirth(cal.getTime());
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
	}

	public void createNewMember(){
		showSearchPanel = false;
		selectedUserForEditing = new UserDto();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1981);
		selectedUserForEditing.setDateOfBirth(cal.getTime());
		BeanUtils.copyProperties(searchedUser, selectedUserForEditing);
		disableVolunteerCheckForSelectedUserEditing = false;
		disableMemberCheckForSelectedUserEditing = false;
		try {
			volunteerBean.init(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onClickVolunteer(){
		showVolunteerPanel = selectedUserForEditing.isVolunteer();
	}

    public void onClickSearchVolunteer() {
        showSearchVolunteerPanel = searchedUser.isVolunteer();
    }
	
	public void cancelSaveMember(){
		showSearchPanel = true;
	}
	public boolean isMemberUpdateAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isAllowed(AppPermission.UPDATE_GLOBAL_MEMBER, userRolePermissionDto, null, PostLocationType.Global)
				||ClientPermissionUtil.isAllowed(AppPermission.UPDATE_MEMBER, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isMemberUpdateAllowed(UserDto userDto){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isAllowed(AppPermission.UPDATE_GLOBAL_MEMBER, userRolePermissionDto, null, PostLocationType.Global)
				||ClientPermissionUtil.isAllowed(AppPermission.UPDATE_MEMBER, userRolePermissionDto, userDto.getAssemblyConstituencyLivingId(), PostLocationType.AC)
				||ClientPermissionUtil.isAllowed(AppPermission.UPDATE_MEMBER, userRolePermissionDto, userDto.getParliamentConstituencyLivingId(), PostLocationType.PC)
				||ClientPermissionUtil.isAllowed(AppPermission.UPDATE_MEMBER, userRolePermissionDto, userDto.getDistrictLivingId(), PostLocationType.DISTRICT)
				||ClientPermissionUtil.isAllowed(AppPermission.UPDATE_MEMBER, userRolePermissionDto, userDto.getStateLivingId(), PostLocationType.STATE)
				||ClientPermissionUtil.isAllowed(AppPermission.UPDATE_MEMBER, userRolePermissionDto, userDto.getAssemblyConstituencyVotingId(), PostLocationType.AC)
				||ClientPermissionUtil.isAllowed(AppPermission.UPDATE_MEMBER, userRolePermissionDto, userDto.getParliamentConstituencyVotingId(), PostLocationType.PC)
				||ClientPermissionUtil.isAllowed(AppPermission.UPDATE_MEMBER, userRolePermissionDto, userDto.getDistrictVotingId(), PostLocationType.DISTRICT)
				||ClientPermissionUtil.isAllowed(AppPermission.UPDATE_MEMBER, userRolePermissionDto, userDto.getStateVotingId(), PostLocationType.STATE);
	}
	public void saveProfile(ActionEvent event) {
		if (sameAsLiving) {
			selectedUserForEditing.setStateVotingId(selectedUserForEditing.getStateLivingId());
			selectedUserForEditing.setDistrictVotingId(selectedUserForEditing.getDistrictLivingId());
			selectedUserForEditing.setAssemblyConstituencyVotingId(selectedUserForEditing.getAssemblyConstituencyLivingId());
			selectedUserForEditing.setParliamentConstituencyVotingId(selectedUserForEditing.getParliamentConstituencyLivingId());
		}

		if (selectedUserForEditing.getStateLivingId() == null || selectedUserForEditing.getStateLivingId() == 0) {
			sendErrorMessageToJsfScreen("Please select State where new Member is living currently");
		}
		if (selectedUserForEditing.getDistrictLivingId() == null || selectedUserForEditing.getDistrictLivingId() == 0) {
			sendErrorMessageToJsfScreen("Please select District where new Member is living currently");
		}
		if (selectedUserForEditing.getAssemblyConstituencyLivingId() == null || selectedUserForEditing.getAssemblyConstituencyLivingId() == 0) {
			sendErrorMessageToJsfScreen("Please select Assembly Constituency where new Member is living currently");
		}
		if (selectedUserForEditing.getParliamentConstituencyLivingId() == null || selectedUserForEditing.getParliamentConstituencyLivingId() == 0) {
			sendErrorMessageToJsfScreen("Please select Parliament Constituency where new Member is living currently");
		}
		if (selectedUserForEditing.getStateVotingId() == null || selectedUserForEditing.getStateVotingId() == 0) {
			sendErrorMessageToJsfScreen("Please select State where new Member is registered as Voter");
		}
		if (selectedUserForEditing.getDistrictVotingId() == null || selectedUserForEditing.getDistrictVotingId() == 0) {
			sendErrorMessageToJsfScreen("Please select District where new Member is registered as Voter");
		}
		if (selectedUserForEditing.getAssemblyConstituencyVotingId() == null || selectedUserForEditing.getAssemblyConstituencyVotingId() == 0) {
			sendErrorMessageToJsfScreen("Please select Assembly Constituency where new Member is registered as Voter");
		}
		if (selectedUserForEditing.getParliamentConstituencyVotingId() == null || selectedUserForEditing.getParliamentConstituencyVotingId() == 0) {
			sendErrorMessageToJsfScreen("Please select Parliament Constituency where new Member is registered as Voter");
		}
		/*
		if (selectedUserForEditing.isNri() ){
			if((selectedUserForEditing.getNriCountryId() == null || selectedUserForEditing.getNriCountryId() == 0)) {
				sendErrorMessageToJsfScreen("Please select Country where you Live");
			}
			if(selectedUserForEditing.isMember() && StringUtil.isEmpty(selectedUserForEditing.getPassportNumber())){
				sendErrorMessageToJsfScreen("Please enter passport number. Its Required for NRIs to become member.");
			}
		}
		*/
		if (selectedUserForEditing.getDateOfBirth() == null) {
			sendErrorMessageToJsfScreen("Please enter Date of Birth of Member");
		}
		
		if(StringUtil.isEmpty(selectedUserForEditing.getEmail()) && StringUtil.isEmpty(selectedUserForEditing.getMobileNumber())){
			sendErrorMessageToJsfScreen("Please enter either Email or Mobile or Both");
		}
		/*
		 * else{ Calendar todayCalendar = Calendar.getInstance();
		 * todayCalendar.add(Calendar.YEAR, -18);
		 * if(dobCalendar.after(todayCalendar)){
		 * sendErrorMessageToJsfScreen("You must be 18 to be eligible for vote"
		 * ); } }
		 */
		if (StringUtil.isEmptyOrWhitespace(selectedUserForEditing.getName())) {
			sendErrorMessageToJsfScreen("Please enter Member full name");
		}
		if(!selectedUserForEditing.isMember() && !selectedUserForEditing.isVolunteer()){
			sendErrorMessageToJsfScreen("Please select if you are registering user as Member or Volunteer or both");
		}
		if (isValidInput()) {
			try{
				boolean isNew = (selectedUserForEditing.getId() == null || selectedUserForEditing.getId() <= 0);
				selectedUserForEditing = aapService.saveUserFromAdmiPanel(selectedUserForEditing, volunteerBean.getSelectedVolunteer(), volunteerBean.getSelectedInterestIds());
				ssaveLoggedInUserInSession(selectedUserForEditing);
				sendInfoMessageToJsfScreen("Profile saved succesfully.");
				if(isNew){
					searchMemberResult.getUsers().add(0, selectedUserForEditing);
				}else{
					searchMemberResult = aapService.searchMembers(searchedUser);
				}
				
				if(selectedUserForEditing.getMembershipStatus().equals("Payment Await")){
					//Show the Payment Dialoge
				    RequestContext.getCurrentInstance().execute("paymentDialog.show()");
				}
				showSearchPanel = true;
				disableVolunteerCheckForSelectedUserEditing = selectedUserForEditing.isVolunteer();
				disableMemberCheckForSelectedUserEditing = selectedUserForEditing.isMember();

			}catch(Exception ex){
				sendErrorMessageToJsfScreen(ex);
			}
		}
	}

	public void searchMember(){
        logger.info("Search Member Clicked");
        searchedUser.setVolunteerDto(volunteerBean.getSelectedVolunteer());
        searchMemberResult = aapService.searchMemberVolunteers(searchedUser, volunteerBean.getSelectedInterestIds());
		showResult = true;
	}
	
	public void receiveMembershipPayment(){
		if(fee < DataUtil.MEMBERSHIP_FEE){
			sendErrorMessageToJsfScreen("Payment must be more then or equal to "+DataUtil.MEMBERSHIP_FEE+" Rs");
		}
		if(isValidInput()){
			try{
				UserDto loggedInAdmin = getLoggedInUser();
				selectedUserForEditing = aapService.receiveMembershipFee(selectedUserForEditing.getId(), fee, loggedInAdmin.getId());
				if(fee > DataUtil.MEMBERSHIP_FEE){
					sendInfoMessageToJsfScreen("Membership is confirmed and donation of Rs " + (fee - DataUtil.MEMBERSHIP_FEE)+" has been received");	
				}else{
					sendInfoMessageToJsfScreen("Membership is confirmed");
				}
				searchMemberResult = aapService.searchMembers(searchedUser);
				RequestContext.getCurrentInstance().execute("paymentDialog.hide()");
				fee = DataUtil.MEMBERSHIP_FEE;
			}catch(Exception ex){
				ex.printStackTrace();
				sendErrorMessageToJsfScreen(ex.getMessage(), ex);
			}
		}
	}
	public boolean isDisablePaymentReceiveButton(){
		if(selectedUserForEditing.getMembershipStatus() == null){
			return true;
		}
		return !selectedUserForEditing.getMembershipStatus().equals("Payment Await");
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
                enableAssemblyConstituencyCombo = false;
				districtList = new ArrayList<>();
				parliamentConstituencyList = new ArrayList<>();
                searchedUser.setAssemblyConstituencyVotingId(0L);
                searchedUser.setDistrictVotingId(0L);
			} else {
				districtList = aapService.getAllDistrictOfState(searchedUser.getStateVotingId());
				parliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(searchedUser.getStateVotingId());
                searchedUser.setAssemblyConstituencyVotingId(0L);
                searchedUser.setDistrictVotingId(0L);
				enableDistrictCombo = true;
				enableParliamentConstituencyCombo = true;
				enableAssemblyConstituencyCombo = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleLivingStateChange(AjaxBehaviorEvent event) {
		try {
			if (searchedUser.getStateLivingId() == 0 || searchedUser.getStateLivingId() == null) {
				enableLivingDistrictCombo = false;
				enableLivingParliamentConstituencyCombo = false;
                enableLivingAssemblyConstituencyCombo = false;
				livingDistrictList = new ArrayList<>();
                searchedUser.setAssemblyConstituencyLivingId(0L);
                searchedUser.setDistrictLivingId(0L);
			} else {
				livingParliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(searchedUser.getStateLivingId());
				livingDistrictList = aapService.getAllDistrictOfState(searchedUser.getStateLivingId());
                searchedUser.setAssemblyConstituencyLivingId(0L);
                searchedUser.setDistrictLivingId(0L);
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

	@Override
    public AapService getAapService() {
		return aapService;
	}

	@Override
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
		disableVolunteerCheckForSelectedUserEditing = selectedUserForEditing.isVolunteer();
		disableMemberCheckForSelectedUserEditing = selectedUserForEditing.isMember();
		showVolunteerPanel = selectedUserForEditing.isVolunteer();

		try {
			volunteerBean.init(selectedUserForEditing);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public VolunteerBean getVolunteerBean() {
		return volunteerBean;
	}

	public void setVolunteerBean(VolunteerBean volunteerBean) {
		this.volunteerBean = volunteerBean;
	}

	public boolean isShowVolunteerPanel() {
		return showVolunteerPanel;
	}

	public void setShowVolunteerPanel(boolean showVolunteerPanel) {
		this.showVolunteerPanel = showVolunteerPanel;
	}

	public boolean isDisableMemberCheckForSelectedUserEditing() {
		return disableMemberCheckForSelectedUserEditing;
	}

	public void setDisableMemberCheckForSelectedUserEditing(boolean disableMemberCheckForSelectedUserEditing) {
		this.disableMemberCheckForSelectedUserEditing = disableMemberCheckForSelectedUserEditing;
	}

	public boolean isDisableVolunteerCheckForSelectedUserEditing() {
		return disableVolunteerCheckForSelectedUserEditing;
	}

	public void setDisableVolunteerCheckForSelectedUserEditing(boolean disableVolunteerCheckForSelectedUserEditing) {
		this.disableVolunteerCheckForSelectedUserEditing = disableVolunteerCheckForSelectedUserEditing;
	}

    public VolunteerBean getSearchVolunteerBean() {
        return searchVolunteerBean;
    }

    public void setSearchVolunteerBean(VolunteerBean searchVolunteerBean) {
        this.searchVolunteerBean = searchVolunteerBean;
    }

    public boolean isShowSearchVolunteerPanel() {
        return showSearchVolunteerPanel;
    }

    public void setShowSearchVolunteerPanel(boolean showSearchVolunteerPanel) {
        this.showSearchVolunteerPanel = showSearchVolunteerPanel;
    }

}
