package com.next.aap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.cache.LocationCacheDbImpl;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.util.ContentDonwloadUtil;

@Controller
public class LocationController extends AppBaseController {
	
	@Autowired
	private ContentDonwloadUtil contentDonwloadUtil;
	@Autowired
	private LocationCacheDbImpl locationCacheDbImpl;
	
	@RequestMapping(value = "/countries", method = RequestMethod.GET)
	public ModelAndView getAllCountries(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		addNewsInModel(httpServletRequest, mv);
		mv.setViewName(design+"/index");
		return mv;
	}
	@RequestMapping(value = "/region/{countryId}", method = RequestMethod.GET)
	@ResponseBody
	public List<CountryRegionDto> getAllRegionsOfCountry(ModelAndView mv,
			HttpServletRequest httpServletRequest, @PathVariable Long countryId) {
		List<CountryRegionDto> countryRegions = locationCacheDbImpl.getCountryRegionsOfCountry(countryId);
		return countryRegions;
	}
	
	@RequestMapping(value = "/regionarea/{countryRegionId}", method = RequestMethod.GET)
	@ResponseBody
	public List<CountryRegionAreaDto> getAllRegionsAreasOfCountryRegion(ModelAndView mv,
			HttpServletRequest httpServletRequest, @PathVariable Long countryRegionId) {
		List<CountryRegionAreaDto> countryRegionAreas = locationCacheDbImpl.getCountryRegionAreasOfCountryRegion(countryRegionId);
		return countryRegionAreas;
	}
	
	@RequestMapping(value = "/district/{stateId}", method = RequestMethod.GET)
	@ResponseBody
	public List<DistrictDto> getDistrictOfStates(ModelAndView mv,
			HttpServletRequest httpServletRequest, @PathVariable Long stateId) {
		List<DistrictDto> districts = locationCacheDbImpl.getAllDistrictOfState(stateId);
		return districts;
	}
	
	@RequestMapping(value = "/pc/{stateId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ParliamentConstituencyDto> getPcOfStates(ModelAndView mv,
			HttpServletRequest httpServletRequest, @PathVariable Long stateId) {
		List<ParliamentConstituencyDto> pcs = locationCacheDbImpl.getAllParliamentConstituenciesOfState(stateId);
		return pcs;
	}
	
	@RequestMapping(value = "/ac/{districtId}", method = RequestMethod.GET)
	@ResponseBody
	public List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfDistrict(ModelAndView mv,
			HttpServletRequest httpServletRequest, @PathVariable Long districtId) {
		List<AssemblyConstituencyDto> acs = locationCacheDbImpl.getAllAssemblyConstituenciesOfDistrict(districtId);
		return acs;
	}
	
}
