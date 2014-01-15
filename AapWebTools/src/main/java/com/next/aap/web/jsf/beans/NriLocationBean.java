package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;

/*
 @Component
 //@Scope("session")
 @SessionScoped
 @URLMappings(mappings = { @URLMapping(id = "userProfileBean", pattern = "/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml") })
 @URLBeanName("userProfileBean")
 */
@Component
@Scope("prototype")
//@Scope("session")
@ViewScoped
//@URLMapping(id = "userProfileBean", beanName = "userProfileBean", pattern = "/profile", viewId = "/WEB-INF/jsf/userprofile.xhtml")
@URLBeanName("nriLocationBean")
public class NriLocationBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private List<CountryDto> nriCountries;
	private List<CountryRegionDto> nriCountryRegions;
	private List<CountryRegionAreaDto> nriCountryRegionAreas;
	private boolean disableNriCountryCombo = true;
	private boolean disableNriCountryRegionCombo = true;
	private boolean disableNriCountryRegionAreaCombo = true;

	private Long selectedNriCountryId;
	private Long selectedNriCountryRegionId;
	private Long selectedNriCountryRegionAreaId;
	
	private boolean nri;
	
	@Autowired
	protected AapService aapService;


	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	public void init(boolean nri, Long selectedNriCountryId, Long selectedNriCountryRegionId, Long selectedNriCountryRegionAreaId) throws Exception {
		this.nri = nri;
		this.selectedNriCountryId = selectedNriCountryId;
		this.selectedNriCountryRegionId = selectedNriCountryRegionId;
		this.selectedNriCountryRegionAreaId = selectedNriCountryRegionAreaId;
		if (nriCountries == null || nriCountries.isEmpty()) {
			nriCountries = aapService.getAllCountries();
			//Remove India from Nri Country List
			Iterator<CountryDto> iterator = nriCountries.iterator();
			while(iterator.hasNext()){
				if(iterator.next().getName().equalsIgnoreCase("India")){
					iterator.remove();
					break;
				}
			}
		}
		if(selectedNriCountryId != null){
			disableNriCountryRegionCombo = false;
			nriCountryRegions = aapService.getAllCountryRegionsOfCountry(selectedNriCountryId);
		}
		if(selectedNriCountryRegionId != null){
			disableNriCountryRegionAreaCombo = false;
			nriCountryRegionAreas = aapService.getAllCountryRegionAreasOfCountryRegion(selectedNriCountryRegionId);
			
		}
	}

	
	public void handleNriCountryChange(AjaxBehaviorEvent event) {
		selectedNriCountryRegionId = null;
		selectedNriCountryRegionAreaId = null;
		try {
			if (selectedNriCountryId == null || selectedNriCountryId <= 0) {
				disableNriCountryRegionCombo = true;
				disableNriCountryRegionAreaCombo = true;
				nriCountryRegions = new ArrayList<>();
				nriCountryRegionAreas = new ArrayList<>();
			} else {
				disableNriCountryRegionCombo = false;
				disableNriCountryRegionAreaCombo = true;
				nriCountryRegions = aapService.getAllCountryRegionsOfCountry(selectedNriCountryId);
				nriCountryRegionAreas = new ArrayList<>();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleNriCountryRegionChange(AjaxBehaviorEvent event) {
		selectedNriCountryRegionAreaId = null;
		try {
			if (selectedNriCountryRegionId == null || selectedNriCountryRegionId <= 0) {
				disableNriCountryRegionAreaCombo = true;
				nriCountryRegions = new ArrayList<>();
			} else {
				disableNriCountryRegionAreaCombo = false;
				nriCountryRegionAreas = aapService.getAllCountryRegionAreasOfCountryRegion(selectedNriCountryRegionId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onClickNri(){
		
	}

	public List<CountryDto> getNriCountries() {
		return nriCountries;
	}


	public void setNriCountries(List<CountryDto> nriCountries) {
		this.nriCountries = nriCountries;
	}


	public List<CountryRegionDto> getNriCountryRegions() {
		return nriCountryRegions;
	}


	public void setNriCountryRegions(List<CountryRegionDto> nriCountryRegions) {
		this.nriCountryRegions = nriCountryRegions;
	}


	public List<CountryRegionAreaDto> getNriCountryRegionAreas() {
		return nriCountryRegionAreas;
	}


	public void setNriCountryRegionAreas(List<CountryRegionAreaDto> nriCountryRegionAreas) {
		this.nriCountryRegionAreas = nriCountryRegionAreas;
	}


	public boolean isDisableNriCountryCombo() {
		return disableNriCountryCombo;
	}


	public void setDisableNriCountryCombo(boolean disableNriCountryCombo) {
		this.disableNriCountryCombo = disableNriCountryCombo;
	}


	public boolean isDisableNriCountryRegionCombo() {
		return disableNriCountryRegionCombo;
	}


	public void setDisableNriCountryRegionCombo(boolean disableNriCountryRegionCombo) {
		this.disableNriCountryRegionCombo = disableNriCountryRegionCombo;
	}


	public boolean isDisableNriCountryRegionAreaCombo() {
		return disableNriCountryRegionAreaCombo;
	}


	public void setDisableNriCountryRegionAreaCombo(boolean disableNriCountryRegionAreaCombo) {
		this.disableNriCountryRegionAreaCombo = disableNriCountryRegionAreaCombo;
	}


	public Long getSelectedNriCountryId() {
		return selectedNriCountryId;
	}


	public void setSelectedNriCountryId(Long selectedNriCountryId) {
		this.selectedNriCountryId = selectedNriCountryId;
	}


	public Long getSelectedNriCountryRegionId() {
		return selectedNriCountryRegionId;
	}


	public void setSelectedNriCountryRegionId(Long selectedNriCountryRegionId) {
		this.selectedNriCountryRegionId = selectedNriCountryRegionId;
	}


	public Long getSelectedNriCountryRegionAreaId() {
		return selectedNriCountryRegionAreaId;
	}


	public void setSelectedNriCountryRegionAreaId(Long selectedNriCountryRegionAreaId) {
		this.selectedNriCountryRegionAreaId = selectedNriCountryRegionAreaId;
	}


	public boolean isNri() {
		return nri;
	}


	public void setNri(boolean nri) {
		this.nri = nri;
	}

}
