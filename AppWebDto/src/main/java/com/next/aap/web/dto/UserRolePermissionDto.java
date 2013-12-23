package com.next.aap.web.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class UserRolePermissionDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public UserRolePermissionDto(){
		allPermissions = new TreeSet<AppPermission>();
		statePermissions = new HashMap<Long, Set<AppPermission>>();
		districtPermissions = new HashMap<Long, Set<AppPermission>>();
		acPermissions = new HashMap<Long, Set<AppPermission>>();
		pcPermissions = new HashMap<Long, Set<AppPermission>>();
	}
	private boolean superUser;
	private Set<StateDto> adminStates;
	private Set<DistrictDto> adminDistricts;
	private Set<AssemblyConstituencyDto> adminAcs;
	private Set<ParliamentConstituencyDto> adminPcs;
	
	private Set<AppPermission> allPermissions;
	private Map<Long,Set<AppPermission>> statePermissions;
	private Map<Long,Set<AppPermission>> districtPermissions;
	private Map<Long,Set<AppPermission>> acPermissions;
	private Map<Long,Set<AppPermission>> pcPermissions;
	public boolean isSuperUser() {
		return superUser;
	}
	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}
	public Set<StateDto> getAdminStates() {
		return adminStates;
	}
	public void addAdminStates(StateDto adminState) {
		this.adminStates.add(adminState);
	}
	public Set<DistrictDto> getAdminDistricts() {
		return adminDistricts;
	}
	public void addAdminDistricts(DistrictDto adminDistrict) {
		this.adminDistricts.add(adminDistrict);
	}
	public Set<AssemblyConstituencyDto> getAdminAcs() {
		return adminAcs;
	}
	public void addAdminAcs(AssemblyConstituencyDto adminAc) {
		this.adminAcs.add(adminAc);
	}
	public Set<ParliamentConstituencyDto> getAdminPcs() {
		return adminPcs;
	}
	public void addAdminPcs(ParliamentConstituencyDto adminPc) {
		this.adminPcs.add(adminPc);
	}
	public Set<AppPermission> getAllPermissions() {
		return allPermissions;
	}
	public void addAllPermission(AppPermission allPermission) {
		this.allPermissions.add(allPermission);
	}
	public void addAllPermissions(Set<AppPermission> allPermissions) {
		this.allPermissions.addAll(allPermissions);
	}
	public Map<Long, Set<AppPermission>> getStatePermissions() {
		return statePermissions;
	}
	public void addStatePermissions(StateDto stateDto, Set<AppPermission> oneStatePermissions) {
		addLocationPermision(stateDto.getId(), oneStatePermissions, this.statePermissions);
		adminStates.add(stateDto);
	}
	public Map<Long, Set<AppPermission>> getDistrictPermissions() {
		return districtPermissions;
	}
	public void addDistrictPermissions(DistrictDto districtDto, Set<AppPermission> oneDistrictPermissions) {
		addLocationPermision(districtDto.getId(), oneDistrictPermissions, this.districtPermissions);
		adminDistricts.add(districtDto);
	}
	private void addLocationPermision(Long locationId, Set<AppPermission> oneLocationPermission, Map<Long,Set<AppPermission>> locationPermissions){
		Set<AppPermission> districtPermission = locationPermissions.get(locationId);
		if(districtPermission == null){
			districtPermission = new HashSet<AppPermission>();
			locationPermissions.put(locationId, districtPermission);
		}
		districtPermission.addAll(oneLocationPermission);

	}
	public Map<Long, Set<AppPermission>> getAcPermissions() {
		return acPermissions;
	}
	public void addAcPermissions(AssemblyConstituencyDto assemblyConstituencyDto, Set<AppPermission> oneAcPermissions) {
		addLocationPermision(assemblyConstituencyDto.getId(), oneAcPermissions, this.acPermissions);
		adminAcs.add(assemblyConstituencyDto);
	}
	public Map<Long, Set<AppPermission>> getPcPermissions() {
		return pcPermissions;
	}
	public void addPcPermissions(ParliamentConstituencyDto parliamentConstituencyDto, Set<AppPermission> onePcPermissions) {
		addLocationPermision(parliamentConstituencyDto.getId(), onePcPermissions, this.pcPermissions);
		adminPcs.add(parliamentConstituencyDto);
	}
	
	
}
