package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;

@Component
@Scope("session")
@URLBeanName("homeBean")
public class MenuBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private Long adminSelectedLocationId;
	private PostLocationType locationType;

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
		buildAndRedirect("/locationadmin");
	}
	public void selectDistrict(ActionEvent event){
		locationType = PostLocationType.DISTRICT;
		adminSelectedLocationId = (Long)event.getComponent().getAttributes().get("districtId");
		buildAndRedirect("/locationadmin");
	}
	
	public void selectAc(ActionEvent event){
		locationType = PostLocationType.AC;
		adminSelectedLocationId = (Long)event.getComponent().getAttributes().get("acId");
		buildAndRedirect("/locationadmin");
	}
	public void selectPc(ActionEvent event){
		locationType = PostLocationType.PC;
		adminSelectedLocationId = (Long)event.getComponent().getAttributes().get("pcId");
		buildAndRedirect("/locationadmin");
	}
	

	public PostLocationType getLocationType() {
		return locationType;
	}

	public Long getAdminSelectedLocationId() {
		return adminSelectedLocationId;
	}

	public void setAdminSelectedLocationId(Long adminSelectedLocationId) {
		this.adminSelectedLocationId = adminSelectedLocationId;
	}
	
}
