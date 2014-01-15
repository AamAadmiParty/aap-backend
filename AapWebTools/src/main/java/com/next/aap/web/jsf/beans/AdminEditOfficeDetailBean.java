package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.OfficeDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ManagedBean
@ViewScoped
@URLMapping(id = "adminEditOfficeDetailBean", beanName = "adminEditOfficeDetailBean", pattern = "/admin/office", viewId = "/WEB-INF/jsf/admin_office.xhtml")
@URLBeanName("adminEditOfficeDetailBean")
public class AdminEditOfficeDetailBean extends BaseMultiPermissionAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private OfficeDto selectedOffice;

	private boolean showList = true;
	
	private static double defaultLattitude = 23.2411685061471;
	private static double defaultLongitude = 77.4254605000001;
	
	private MapModel draggableMapModel;
	
	Marker marker;

	private List<OfficeDto> officeList;

	public AdminEditOfficeDetailBean() {
		super("/admin/office", AppPermission.EDIT_OFFICE_ADDRESS);
	}

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {
		if (!checkUserAccess()) {
			return;
		}
		refreshNewsList();
		draggableMapModel = new DefaultMapModel();
		
		LatLng coord1 = new LatLng(selectedOffice.getLattitude(), selectedOffice.getLongitude()); 
        //Draggable  
		marker = new Marker(coord1, "Office Location");
		marker.setDraggable(true);
		draggableMapModel.addOverlay(marker);  
	}
	
	public void onMarkerDrag(MarkerDragEvent event) {  
        marker = event.getMarker();  
    }  
	public void onStateChange(StateChangeEvent event) {
		selectedOffice.setDepth(event.getZoomLevel());
    }  

	private void refreshNewsList() {
		officeList = aapService.getLocationOffices(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
		if (officeList == null || officeList.size() <= 1) {
			if (officeList.size() == 1) {
				selectedOffice = officeList.get(0);
			} else {
				selectedOffice = new OfficeDto();
				selectedOffice.setLattitude(defaultLattitude);
				selectedOffice.setLongitude(defaultLongitude);
				selectedOffice.setDepth(10);
			}
			showList = false;
		}
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}

	public void saveOfficeDetail() {
		System.out.println(  "getLat = "+ marker.getLatlng().getLat());
		System.out.println("getLng = " + marker.getLatlng().getLng());
		System.out.println("getData = " + marker.getData());
		selectedOffice.setLattitude(marker.getLatlng().getLat());
		selectedOffice.setLongitude(marker.getLatlng().getLng());
		try {
			switch (menuBean.getLocationType()) {
			case Global:
				selectedOffice.setNational(true);
				break;
			case STATE:
				selectedOffice.setStateId(menuBean.getAdminSelectedLocationId());
				break;
			case DISTRICT:
				selectedOffice.setDistrictId(menuBean.getAdminSelectedLocationId());
				break;
			case AC:
				selectedOffice.setAssemblyConstituencyId(menuBean.getAdminSelectedLocationId());
				break;
			case PC:
				selectedOffice.setParliamentConstituencyId(menuBean.getAdminSelectedLocationId());
				break;
			case COUNTRY:
				selectedOffice.setCountryId(menuBean.getAdminSelectedLocationId());
				break;
			case REGION:
				selectedOffice.setCountryRegionId(menuBean.getAdminSelectedLocationId());
				break;
			case AREA:
				selectedOffice.setCountryRegionAreaId(menuBean.getAdminSelectedLocationId());
				break;
			default:
				sendErrorMessageToJsfScreen("No Location selected");
			}
			if (isValidInput()) {
				selectedOffice = aapService.saveOffice(selectedOffice);
				sendInfoMessageToJsfScreen("Office Details saved Succesfully");
				refreshNewsList();
			}
		} catch (Exception ex) {
			sendErrorMessageToJsfScreen("Unable to save Office Detail", ex);
		}
	}

	public boolean isShowList() {
		return showList;
	}

	public void setShowList(boolean showList) {
		this.showList = showList;
	}

	public OfficeDto getSelectedOffice() {
		return selectedOffice;
	}

	public void setSelectedoffice(OfficeDto selectedoffice) {
		this.selectedOffice = selectedoffice;
		showList = false;
	}

	public List<OfficeDto> getOfficeList() {
		return officeList;
	}

	public void setOfficeList(List<OfficeDto> officeList) {
		this.officeList = officeList;
	}

	public MapModel getDraggableMapModel() {
		return draggableMapModel;
	}

	public void setDraggableMapModel(MapModel draggableMapModel) {
		this.draggableMapModel = draggableMapModel;
	}

}
