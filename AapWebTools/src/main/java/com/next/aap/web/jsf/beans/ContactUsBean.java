package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import com.next.aap.web.dto.OfficeDto;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "contactUsBean", beanName = "contactUsBean", pattern = "/contactus", viewId = "/WEB-INF/jsf/aapstyle/contactus.xhtml") })
@URLBeanName("contactUsBean")
public class ContactUsBean extends BaseUserJsfBean {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{locationBean}")
	private LocationBean locationBean;

	@ManagedProperty("#{nriLocationBean}")
	private NriLocationBean nriLocationBean;

	List<OfficeDto> nriOffices;
	List<OfficeDto> indianOffices;

	// @URLActions(actions = { @URLAction(mappingId = "contactUs") })
	@URLAction(onPostback = false)
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser(true, buildLoginUrl("/contactus"));
		if (loggedInUser == null) {
		} else {

		}
		refreshLocationData(loggedInUser);
		// Copy Logged In user to selectedUserForEditing

	}

	private void refreshLocationData(UserDto loggedInUser) throws Exception {
		if (loggedInUser == null) {
			locationBean.init(null, null, null, null);
			nriLocationBean.init(false, null, null, null);

		} else {
			locationBean.init(loggedInUser.getStateLivingId(), loggedInUser.getDistrictLivingId(), loggedInUser.getParliamentConstituencyLivingId(),
					loggedInUser.getAssemblyConstituencyLivingId());
			nriLocationBean.init(loggedInUser.isNri(), loggedInUser.getNriCountryId(), loggedInUser.getNriCountryRegionId(),
					loggedInUser.getNriCountryRegionAreaId());

		}
	}

	public void showNriContactInformation(ActionEvent event) {
		System.out.println("nriLocationBean.getSelectedNriCountryId()=" + nriLocationBean.getSelectedNriCountryId());
		if (nriLocationBean.getSelectedNriCountryId() == null || nriLocationBean.getSelectedNriCountryId() <= 0) {
			sendErrorMessageToJsfScreen("Please select your Country");
		}
		if (nriLocationBean.getSelectedNriCountryRegionId() == null || nriLocationBean.getSelectedNriCountryRegionId() <= 0) {
			sendErrorMessageToJsfScreen("Please select your Country Region");
		}
		if (isValidInput()) {
			nriOffices = new ArrayList<>();
			if(nriLocationBean.getSelectedNriCountryRegionAreaId() != null && nriLocationBean.getSelectedNriCountryRegionAreaId() > 0){
				List<OfficeDto> areaOffices = aapService.getLocationOffices(PostLocationType.AREA, nriLocationBean.getSelectedNriCountryRegionAreaId());
				if (areaOffices != null) {
					nriOffices.addAll(areaOffices);
				}
				
			}
			List<OfficeDto> regionOffices = aapService.getLocationOffices(PostLocationType.REGION, nriLocationBean.getSelectedNriCountryRegionId());
			if (regionOffices != null) {
				nriOffices.addAll(regionOffices);
			}
			List<OfficeDto> countryOffices = aapService.getLocationOffices(PostLocationType.COUNTRY, nriLocationBean.getSelectedNriCountryId());
			if (countryOffices != null) {
				nriOffices.addAll(countryOffices);
			}
			List<OfficeDto> globalOffices = aapService.getLocationOffices(PostLocationType.Global, 0L);
			if (globalOffices != null) {
				nriOffices.addAll(globalOffices);
			}
			System.out.println("nriOffices=" + nriOffices);
		}
	}

	public void showContactInformation(ActionEvent event) {
		System.out.println("locationBean.getSelectedAcId()=" + locationBean.getSelectedAcId());
		if (locationBean.getSelectedAcId() == null || locationBean.getSelectedAcId() <= 0) {
			sendErrorMessageToJsfScreen("Please select your Assembly Constituency");
		}
		indianOffices = new ArrayList<>();
		if (isValidInput()) {

			List<OfficeDto> acOffices = aapService.getLocationOffices(PostLocationType.AC, locationBean.getSelectedAcId());
			if (acOffices != null) {
				indianOffices.addAll(acOffices);
			}
			if (locationBean.getSelectedPcId() != null) {
				List<OfficeDto> pcOffices = aapService.getLocationOffices(PostLocationType.PC, locationBean.getSelectedPcId());
				if (pcOffices != null) {
					indianOffices.addAll(pcOffices);
				}
			}
			List<OfficeDto> districtOffices = aapService.getLocationOffices(PostLocationType.DISTRICT, locationBean.getSelectedDistrictId());
			if (districtOffices != null) {
				indianOffices.addAll(districtOffices);
			}
			List<OfficeDto> stateOffices = aapService.getLocationOffices(PostLocationType.STATE, locationBean.getSelectedStateId());
			if (stateOffices != null) {
				indianOffices.addAll(stateOffices);
			}
			List<OfficeDto> globalOffices = aapService.getLocationOffices(PostLocationType.Global, 0L);
			if (globalOffices != null) {
				indianOffices.addAll(globalOffices);
			}
			System.out.println("indianOffices=" + indianOffices);
		}
	}

	public void emptyCallToMakeItWork(AjaxBehaviorEvent event) {
		// This fucntion doesnt do anything but
	}

	public NriLocationBean getNriLocationBean() {
		return nriLocationBean;
	}

	public void setNriLocationBean(NriLocationBean nriLocationBean) {
		this.nriLocationBean = nriLocationBean;
	}

	public LocationBean getLocationBean() {
		return locationBean;
	}

	public void setLocationBean(LocationBean locationBean) {
		this.locationBean = locationBean;
	}

	public List<OfficeDto> getNriOffices() {
		return nriOffices;
	}

	public void setNriOffices(List<OfficeDto> nriOffices) {
		this.nriOffices = nriOffices;
	}

	public List<OfficeDto> getIndianOffices() {
		return indianOffices;
	}

	public void setIndianOffices(List<OfficeDto> indianOffices) {
		this.indianOffices = indianOffices;
	}

}
