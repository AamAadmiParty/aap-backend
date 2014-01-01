package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;

@Component
@Scope("session")
@URLBeanName("homeBean")
public class MenuBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private Long adminSelectedLocationId;
	private PostLocationType locationType;
	private StateDto selectedAdminState;
	private DistrictDto selectedAdminDistrict;
	private AssemblyConstituencyDto selectedAdminAc;
	private ParliamentConstituencyDto selectedAdminPc;

	@Autowired
	protected AapService aapService;

	public boolean isAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if(userRolePermissionDto == null){
			return false;
		}
		/*
		System.out.println("isAcAdmin()="+userRolePermissionDto.isAcAdmin());
		System.out.println("isPcAdmin()="+userRolePermissionDto.isPcAdmin());
		System.out.println("isDistrictAdmin()="+userRolePermissionDto.isDistrictAdmin());
		System.out.println("isStateAdmin()="+userRolePermissionDto.isStateAdmin());
		System.out.println("isAllAdmin()="+userRolePermissionDto.isAllAdmin());
		System.out.println("isSuperUser()="+userRolePermissionDto.isSuperUser());
		System.out.println("isAdmin()="+userRolePermissionDto.isAdmin());
		*/
		return userRolePermissionDto.isAdmin();
		
	}
	
	public boolean isSuperUser() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if(userRolePermissionDto == null){
			return false;
		}
		return userRolePermissionDto.isSuperUser();
	}
	public boolean isStateAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if(userRolePermissionDto == null){
			return false;
		}
		return userRolePermissionDto.isStateAdmin();
	}
	public boolean isDistrictAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if(userRolePermissionDto == null){
			return false;
		}
		return userRolePermissionDto.isDistrictAdmin();
	}
	public boolean isAcAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if(userRolePermissionDto == null){
			return false;
		}
		return userRolePermissionDto.isAcAdmin();
	}
	public boolean isPcAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if(userRolePermissionDto == null){
			return false;
		}
		return userRolePermissionDto.isPcAdmin();
	}
	public boolean isGlobalAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if(userRolePermissionDto == null){
			return false;
		}
		return userRolePermissionDto.isAllAdmin();
	}
	public String getContext(){
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		return httpServletRequest.getContextPath();
	}
	public boolean isLoggedIn(){
		UserDto user = getLoggedInUser();
		if(user == null){
			return false;
		}
		return true;
	}
	

	public boolean isAllAdmin() {
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		if(userRolePermissionDto == null){
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

	public void selectGlobal(ActionEvent event){
		locationType = PostLocationType.Global;
		adminSelectedLocationId = -1L;
		buildAndRedirect("/locationadmin");
	}
	public void selectState(ActionEvent event){
		locationType = PostLocationType.STATE;
		adminSelectedLocationId = (Long)event.getComponent().getAttributes().get("stateId");
		selectedAdminState = aapService.getStateById(adminSelectedLocationId);
		buildAndRedirect("/locationadmin");
	}
	public void selectDistrict(ActionEvent event){
		locationType = PostLocationType.DISTRICT;
		adminSelectedLocationId = (Long)event.getComponent().getAttributes().get("districtId");
		selectedAdminDistrict = aapService.getDistrictById(adminSelectedLocationId);
		selectedAdminState = aapService.getStateById(selectedAdminDistrict.getStateId());
		buildAndRedirect("/locationadmin");
	}
	
	public void selectAc(ActionEvent event){
		locationType = PostLocationType.AC;
		adminSelectedLocationId = (Long)event.getComponent().getAttributes().get("acId");
		selectedAdminAc = aapService.getAssemblyConstituencyById(adminSelectedLocationId);
		selectedAdminDistrict = aapService.getDistrictById(selectedAdminAc.getDistrictId());
		selectedAdminState = aapService.getStateById(selectedAdminDistrict.getStateId());
		buildAndRedirect("/locationadmin");
	}
	public void selectPc(ActionEvent event){
		locationType = PostLocationType.PC;
		adminSelectedLocationId = (Long)event.getComponent().getAttributes().get("pcId");
		selectedAdminPc = aapService.getParliamentConstituencyById(adminSelectedLocationId);
		selectedAdminState = aapService.getStateById(selectedAdminPc.getStateId());
		buildAndRedirect("/locationadmin");
	}
	

	public PostLocationType getLocationType() {
		return locationType;
	}
	public String getCurrentLocationName() {
		switch(locationType){
		case Global :
			return "Global";
		case STATE :
			return selectedAdminState.getName();
		case DISTRICT :
			return selectedAdminDistrict.getName();
		case AC :
			return selectedAdminAc.getName();
		case PC :
			return selectedAdminPc.getName();
		}
		return "";
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
	
}
