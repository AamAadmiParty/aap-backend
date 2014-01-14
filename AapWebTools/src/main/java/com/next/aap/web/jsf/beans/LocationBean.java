package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.StateDto;
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
@URLBeanName("locationBean")
public class LocationBean extends BaseUserJsfBean {

	private static final long serialVersionUID = 1L;

	private List<StateDto> stateList;
	private List<DistrictDto> districtList;
	private List<AssemblyConstituencyDto> assemblyConstituencyList;
	private List<ParliamentConstituencyDto> parliamentConstituencyList;
	private boolean enableStateCombo = true;
	private boolean enableDistrictCombo = false;
	private boolean enableAssemblyConstituencyCombo = false;
	private boolean enableParliamentConstituencyCombo = false;
	
	private Long selectedStateId;
	private Long selectedDistrictId;
	private Long selectedAcId;
	private Long selectedPcId;
	
	@Autowired
	protected AapService aapService;

	
	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	public void init(Long stateId, Long districtId, Long pcId, Long acId) throws Exception {
		selectedStateId = stateId;
		selectedDistrictId = districtId;
		selectedAcId = acId;
		selectedPcId = pcId;
		if (stateList == null || stateList.isEmpty()) {
			stateList = aapService.getAllStates();
		}
		if (stateId != null) {
			enableDistrictCombo = true;
			enableParliamentConstituencyCombo = true;
			parliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(stateId);
			districtList = aapService.getAllDistrictOfState(stateId);
			if (districtId != null) {
				enableAssemblyConstituencyCombo = true;
				assemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(districtId);
			}
		}
	}

	
	public void handleStateChange(AjaxBehaviorEvent event) {
		System.out.println("handleStateChange "+ selectedStateId);
		try {
			if ( selectedStateId == null || selectedStateId <= 0) {
				enableDistrictCombo = false;
				enableParliamentConstituencyCombo = false;
				districtList = new ArrayList<>();
				parliamentConstituencyList = new ArrayList<>();
			} else {
				districtList = aapService.getAllDistrictOfState(selectedStateId);
				parliamentConstituencyList = aapService.getAllParliamentConstituenciesOfState(selectedStateId);
				enableDistrictCombo = true;
				enableParliamentConstituencyCombo = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleDistrictChange(AjaxBehaviorEvent event) {
		
		try {
			if (selectedDistrictId == null || selectedDistrictId <= 0) {
				enableAssemblyConstituencyCombo = false;
				assemblyConstituencyList = new ArrayList<>();
			} else {
				enableAssemblyConstituencyCombo = true;
				assemblyConstituencyList = aapService.getAllAssemblyConstituenciesOfDistrict(selectedDistrictId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public List<ParliamentConstituencyDto> getParliamentConstituencyList() {
		return parliamentConstituencyList;
	}

	public void setParliamentConstituencyList(List<ParliamentConstituencyDto> parliamentConstituencyList) {
		this.parliamentConstituencyList = parliamentConstituencyList;
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


	public Long getSelectedStateId() {
		return selectedStateId;
	}


	public void setSelectedStateId(Long selectedStateId) {
		this.selectedStateId = selectedStateId;
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


	public boolean isEnableStateCombo() {
		return enableStateCombo;
	}


	public void setEnableStateCombo(boolean enableStateCombo) {
		this.enableStateCombo = enableStateCombo;
	}

}
