package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.StateDto;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;

@Component
@ApplicationScoped
@URLBeanName("appDataBean")
public class AppDataBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private List<CountryDto> countries;
	private List<CountryDto> nriCountries;
	private List<StateDto> states;
	private Map<Long,List<DistrictDto>> districtMaps;
	private Map<Long,List<AssemblyConstituencyDto>> acMaps;
	private Map<Long,List<ParliamentConstituencyDto>> pcMaps;
	private Map<Long,List<CountryRegionDto>> countryRegionMaps;
	private Map<Long,List<CountryRegionAreaDto>> countryRegionAreaMaps;
	@Autowired
	protected AapService aapService;

	public void init(){
		districtMaps = new HashMap<Long, List<DistrictDto>>();
		acMaps = new HashMap<>();
		pcMaps = new HashMap<>();
		countryRegionMaps = new HashMap<>();
		countryRegionAreaMaps = new HashMap<>();
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
		states = aapService.getAllStates();
	}
	
	public List<CountryDto> getCountries(){
		return countries;
	}
	public List<CountryDto> getNriCountries(){
		return nriCountries;
	}
	public List<StateDto> getStates(){
		return states;
	}
	public List<DistrictDto> getDistrictOfState(Long stateId){
		List<DistrictDto> districtList = districtMaps.get(stateId);
		if(districtList == null){
			districtList = aapService.getAllDistrictOfState(stateId);
			districtMaps.put(stateId, districtList);
		}
		return districtList;
	}
	
	public List<AssemblyConstituencyDto> getAssemblyConstituencyOfDistrict(Long districtId){
		List<AssemblyConstituencyDto> acList = acMaps.get(districtId);
		if(acList == null){
			acList = aapService.getAllAssemblyConstituenciesOfDistrict(districtId);
			acMaps.put(districtId, acList);
		}
		return acList;
	}
	public List<ParliamentConstituencyDto> getParliamentConstituencyOfState(Long stateId){
		List<ParliamentConstituencyDto> pcList = pcMaps.get(stateId);
		if(pcList == null){
			pcList = aapService.getAllParliamentConstituenciesOfState(stateId);
			pcMaps.put(stateId, pcList);
		}
		return pcList;
	}
	
	public List<CountryRegionDto> getCountryRegionOfCountry(Long countryId){
		List<CountryRegionDto> countryRegionList = countryRegionMaps.get(countryId);
		if(countryRegionList == null){
			countryRegionList = aapService.getAllCountryRegionsOfCountry(countryId);
			countryRegionMaps.put(countryId, countryRegionList);
		}
		return countryRegionList;
	}
	
	public List<CountryRegionAreaDto> getCountryRegionAreaOfCountryRegion(Long countryRegionId){
		List<CountryRegionAreaDto> countryRegionList = countryRegionAreaMaps.get(countryRegionId);
		if(countryRegionList == null){
			countryRegionList = aapService.getAllCountryRegionAreasOfCountryRegion(countryRegionId);
			countryRegionAreaMaps.put(countryRegionId, countryRegionList);
		}
		return countryRegionList;
	}
	
}
