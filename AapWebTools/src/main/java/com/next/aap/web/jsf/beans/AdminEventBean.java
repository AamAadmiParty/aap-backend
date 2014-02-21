package com.next.aap.web.jsf.beans;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.EventDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.jsf.beans.model.AapScheduleEvent;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ManagedBean
@ViewScoped
@URLMapping(id = "adminEventBean", beanName = "adminEventBean", pattern = "/admin/event", viewId = "/WEB-INF/jsf/admin_event.xhtml")
@URLBeanName("adminEventBean")
public class AdminEventBean extends BaseAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private ScheduleModel eventModel;

	private AapScheduleEvent event = new AapScheduleEvent();
	
	private MapModel draggableMapModel;
	
	Marker marker;

	private static double defaultLattitude = 24.3771214;
	private static double defaultLongitude = 81.4086922;

	public AdminEventBean() {
		super(AppPermission.ADMIN_EVENT, "/admin/event");
        eventModel = new DefaultScheduleModel();  

	}

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {
		/*
		 * if (!checkUserAccess()) { return; }
		 */
		draggableMapModel = new DefaultMapModel();
		
		LatLng coord1 = new LatLng(defaultLattitude, defaultLongitude); 
        //Draggable  
		marker = new Marker(coord1, "Event Location");
		marker.setDraggable(true);
		draggableMapModel.addOverlay(marker);  
		
		event.setLattitude(defaultLattitude);
		event.setLongitude(defaultLongitude);
		event.setDepth(10);	
		refreshEvents();
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}

	public void saveEvent(ActionEvent actionEvent) {
		try {
			if (StringUtil.isEmpty(event.getTitle())) {
				sendErrorMessageToJsfScreen("Please enter Title");
			}
			if (StringUtil.isEmpty(event.getDescription())) {
				sendErrorMessageToJsfScreen("Please enter Event Description");
			}
			if (event.getStartDate() == null) {
				sendErrorMessageToJsfScreen("Please enter the start date of Event");
			}
			if (event.getEndDate() == null) {
				sendErrorMessageToJsfScreen("Please enter the end date of Event");
			}
			if (isValidInput()) {
				// aapService.savePlannedEmail(selectedEmail);
				sendInfoMessageToJsfScreen("Event saved succesfully");
				refreshEvents();
				//RequestContext.getCurrentInstance().execute("eventDialog.hide()");
				EventDto eventDto = new EventDto();
				if(!StringUtil.isEmpty(event.getId())){
					eventDto.setId(Long.parseLong(event.getId()));
				}
				eventDto.setAddress(event.getAddress());
				eventDto.setContactNumber1(event.getContactNumber1());
				eventDto.setContactNumber2(event.getContactNumber2());
				eventDto.setContactNumber3(event.getContactNumber3());
				eventDto.setContactNumber4(event.getContactNumber4());
				eventDto.setDepth(event.getDepth());
				eventDto.setDescription(event.getDescription());
				eventDto.setEndDate(event.getEndDate());
				eventDto.setFbEventId(event.getFbEventId());
				eventDto.setLattitude(event.getLattitude());
				eventDto.setLongitude(event.getLongitude());
				eventDto.setNational(event.isNational());
				eventDto.setStartDate(event.getStartDate());
				eventDto.setTitle(event.getTitle());
				aapService.saveEvent(eventDto);
				eventModel.addEvent(event);
			}

		} catch (Exception ex) {
			sendErrorMessageToJsfScreen("Unable to save Post", ex);
		}

	}
	public void onEventSelect(SelectEvent selectEvent) {  
        event = (AapScheduleEvent) selectEvent.getObject();  
        marker.setLatlng(new LatLng(((AapScheduleEvent)selectEvent.getObject()).getLattitude(), ((AapScheduleEvent)selectEvent.getObject()).getLongitude()));
    }  
      
    public void onDateSelect(SelectEvent selectEvent) {  
        event = new AapScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        event.setLattitude(defaultLattitude);
        event.setLattitude(defaultLongitude);
        event.setDepth(10);
        marker.setLatlng(new LatLng(defaultLattitude, defaultLongitude));
    }  
      
    public void onEventMove(ScheduleEntryMoveEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
          
        addMessage(message);  
    }  
      
    public void onEventResize(ScheduleEntryResizeEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
          
        addMessage(message);  
    }  
    private void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }
	private void refreshEvents() {
	}
	
	public void onMarkerDrag(MarkerDragEvent event) {  
        marker = event.getMarker();  
    }  
	public void onStateChange(StateChangeEvent stateChangeEvent) {
		this.event.setDepth(stateChangeEvent.getZoomLevel());
    }  

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public AapScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(AapScheduleEvent event) {
		this.event = event;
	}

	public MapModel getDraggableMapModel() {
		return draggableMapModel;
	}

	public void setDraggableMapModel(MapModel draggableMapModel) {
		this.draggableMapModel = draggableMapModel;
	}

}
