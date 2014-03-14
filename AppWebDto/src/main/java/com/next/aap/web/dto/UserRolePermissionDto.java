package com.next.aap.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
		
		adminStates = new ArrayList<StateDto>();
		adminDistricts = new ArrayList<DistrictDto>();
		adminAcs = new ArrayList<AssemblyConstituencyDto>();
		adminPcs = new ArrayList<ParliamentConstituencyDto>();
		adminCountries = new ArrayList<CountryDto>();
		adminCountryRegions = new ArrayList<CountryRegionDto>();
	}
	private boolean superUser;
	private List<StateDto> adminStates;
	private List<DistrictDto> adminDistricts;
	private List<AssemblyConstituencyDto> adminAcs;
	private List<ParliamentConstituencyDto> adminPcs;
	private List<CountryDto> adminCountries;
	private List<CountryRegionDto> adminCountryRegions;
	
	private Set<AppPermission> allPermissions;
	private Map<Long,Set<AppPermission>> statePermissions;
	private Map<Long,Set<AppPermission>> districtPermissions;
	private Map<Long,Set<AppPermission>> acPermissions;
	private Map<Long,Set<AppPermission>> pcPermissions;
	private Map<Long,Set<AppPermission>> countryPermissions;
	private Map<Long,Set<AppPermission>> countryRegionPermissions;
	public boolean isSuperUser() {
		return superUser;
	}
	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}
	public List<StateDto> getAdminStates() {
		return adminStates;
	}
	public void addAdminStates(StateDto adminState) {
		addAdminLocation(adminState, adminStates);
	}
	public List<DistrictDto> getAdminDistricts() {
		return adminDistricts;
	}
	public void addAdminDistricts(DistrictDto adminDistrict) {
		addAdminLocation(adminDistrict, adminDistricts);
	}
	/**
	 * This function will make sure we don't add duplicate entries
	 * @param adminLocation
	 * @param adminLocations
	 */
	private <T> void addAdminLocation(T adminLocation, List<T> adminLocations){
		Set<T> locations = new HashSet<T>(adminLocations);
		locations.add(adminLocation);
		adminLocations.clear();
		adminLocations.addAll(locations);
	}
	public List<AssemblyConstituencyDto> getAdminAcs() {
		return adminAcs;
	}
	public void addAdminAcs(AssemblyConstituencyDto adminAc) {
		addAdminLocation(adminAc, adminAcs);
	}
	public List<ParliamentConstituencyDto> getAdminPcs() {
		return adminPcs;
	}
	public void addAdminPcs(ParliamentConstituencyDto adminPc) {
		addAdminLocation(adminPc, adminPcs);
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
		if(!adminStates.contains(stateDto)){
			adminStates.add(stateDto);	
		}
	}
	public Map<Long, Set<AppPermission>> getDistrictPermissions() {
		return districtPermissions;
	}
	public void addDistrictPermissions(DistrictDto districtDto, Set<AppPermission> oneDistrictPermissions) {
		addLocationPermision(districtDto.getId(), oneDistrictPermissions, this.districtPermissions);
		if(!adminDistricts.contains(districtDto)){
			adminDistricts.add(districtDto);	
		}
		
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
		if(!adminAcs.contains(assemblyConstituencyDto)){
			adminAcs.add(assemblyConstituencyDto);	
		}
		
	}
	public Map<Long, Set<AppPermission>> getPcPermissions() {
		return pcPermissions;
	}
	public void addPcPermissions(ParliamentConstituencyDto parliamentConstituencyDto, Set<AppPermission> onePcPermissions) {
		addLocationPermision(parliamentConstituencyDto.getId(), onePcPermissions, this.pcPermissions);
		if(!adminPcs.contains(parliamentConstituencyDto)){
			adminPcs.add(parliamentConstituencyDto);	
		}
	}
	
	public boolean isStateAdmin(){
		return !statePermissions.isEmpty(); 
	}
	public boolean isDistrictAdmin(){
		return !districtPermissions.isEmpty(); 
	}
	public boolean isAcAdmin(){
		return !acPermissions.isEmpty(); 
	}
	public boolean isPcAdmin(){
		return !acPermissions.isEmpty(); 
	}
	public boolean isAllAdmin(){
		return !allPermissions.isEmpty(); 
	}
	public boolean isAdmin(){
		return isSuperUser() || isStateAdmin() || isDistrictAdmin() || isAcAdmin() || isPcAdmin() || isAllAdmin() || isCountryAdmin() || isCountryRegionAdmin();
	}
	public List<CountryDto> getAdminCountries() {
		return adminCountries;
	}
	public List<CountryRegionDto> getAdminCountryRegions() {
		return adminCountryRegions;
	}
	public Map<Long, Set<AppPermission>> getCountryPermissions() {
		return countryPermissions;
	}
	public Map<Long, Set<AppPermission>> getCountryRegionPermissions() {
		return countryRegionPermissions;
	}
	
	public void addAdminCountry(CountryDto countryDto) {
		addAdminLocation(countryDto, adminCountries);
	}
	public void addAdminCountry(CountryRegionDto countryRegionDto) {
		addAdminLocation(countryRegionDto, adminCountryRegions);
	}
	public boolean isCountryAdmin(){
		return !countryPermissions.isEmpty(); 
	}
	public boolean isCountryRegionAdmin(){
		return !countryRegionPermissions.isEmpty(); 
	}
	
	public void addCountryPermissions(CountryDto countryDto, Set<AppPermission> countryPermissions) {
		addLocationPermision(countryDto.getId(), countryPermissions, this.countryPermissions);
		if(!adminCountries.contains(countryDto)){
			adminCountries.add(countryDto);	
		}
	}
	
	public void addCountryRegionPermissions(CountryRegionDto countryRegionDto, Set<AppPermission> countryPermissions) {
		addLocationPermision(countryRegionDto.getId(), countryPermissions, this.countryRegionPermissions);
		if(!adminCountryRegions.contains(countryRegionDto)){
			adminCountryRegions.add(countryRegionDto);	
		}
	}

}
