package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.cache.CandidateCacheImpl;
import com.next.aap.web.cache.LocationCacheDbImpl;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.CandidateDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ManagedBean
@ViewScoped
@URLMapping(id = "adminCandidateBean", beanName = "adminCandidateBean", pattern = "/admin/candidate", viewId = "/WEB-INF/jsf/admin_candidate.xhtml")
@URLBeanName("adminCandidateBean")
public class AdminCandidateBean extends BaseAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private CandidateDto candidate = new CandidateDto();
	
	List<CandidateDto> candidates;

	private boolean showList = true;
	private boolean enablePcBox = false;
	private boolean formEditable = false;
	
	@ManagedProperty("#{candidateCacheImpl}")
	protected CandidateCacheImpl candidateCacheImpl;

	@ManagedProperty("#{locationCacheDbImpl}")
	private LocationCacheDbImpl locationCacheDbImpl;
	
	List<StateDto> stateList;
	
	
	private MapModel draggableMapModel;

	Marker marker;

	private static double defaultLattitude = 23.934102635197338;
	private static double defaultLongitude = 78.310546875;
	private static int defaultDepth = 8;

	public AdminCandidateBean() {
		super(AppPermission.ADMIN_CANDIDATE_PC, "/admin/candidate");
	}

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {

		if (!checkUserAccess()) {
			return;
		}

		System.out.println("Init");
		try {
			draggableMapModel = new DefaultMapModel();

			LatLng coord1 = new LatLng(defaultLattitude, defaultLongitude);
			// Draggable
			marker = new Marker(coord1, "Candidate Location");
			marker.setDraggable(true);
			draggableMapModel.addOverlay(marker);

			candidate.setLattitude(defaultLattitude);
			candidate.setLongitude(defaultLongitude);
			candidate.setDepth(defaultDepth);
			
			stateList = locationCacheDbImpl.getAllStates();
			System.out.println("stateList = "+ stateList);
			System.out.println("stateList.size = "+ stateList.size());
			
			refreshCandidates();
		} catch (Exception ex) {
			sendErrorMessageToJsfScreen(ex);
		}

	}
	public void handleStateChange(AjaxBehaviorEvent event) {
		System.out.println("handleStateChange() = "+ event);
		System.out.println("candidate.getStateId() = "+ candidate.getStateId());
		try {
			if (candidate.getStateId() == null || candidate.getStateId() <= 0) {
				enablePcBox = false;
			} else {
				enablePcBox = true;
			}
			formEditable = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public void handlePcChange(AjaxBehaviorEvent event) {
		try {
			if (candidate.getParliamentConstituencyId() == null || candidate.getParliamentConstituencyId() <= 0) {
				formEditable = false;
			} else {
				formEditable = true;
				CandidateDto existingCandidate = aapService.getCandidateByPcId(candidate.getParliamentConstituencyId());
				if(existingCandidate != null){
					candidate = existingCandidate;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public List<StateDto> getStateList(){
		System.out.println("getStateList() = "+ stateList);
		return stateList;
	}
	public void setStateList(List<StateDto> stateList) {
		this.stateList = stateList;
	}

	public List<ParliamentConstituencyDto> getPcList(){
		if(candidate == null || candidate.getStateId() == null || candidate.getStateId() <= 0){
			return new ArrayList<>();
		}
		return locationCacheDbImpl.getAllParliamentConstituenciesOfState(candidate.getStateId());
	}
	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}

	public void createCandidate(){
		candidate = new CandidateDto();
		candidate.setLattitude(defaultLattitude);
		candidate.setLongitude(defaultLongitude);
		candidate.setDepth(defaultDepth);
		showList = false;
	}
	public void cancel(ActionEvent actionEvent) {
		showList = true;
	}
	public void saveCandidate(ActionEvent actionEvent) {
		try {
			/*
			if (StringUtil.isEmpty(candidate.getName())) {
				sendErrorMessageToJsfScreen("Please enter Name");
			}
			
			if (StringUtil.isEmpty(candidate.getContent())) {
				sendErrorMessageToJsfScreen("Please enter Candidate Description");
			}
			*/
			if (StringUtil.isEmpty(candidate.getDonatePageUrlId())) {
				sendErrorMessageToJsfScreen("Please enter the donation page id");
			}
			if (StringUtil.isEmpty(candidate.getLandingPageUrlId())) {
				sendErrorMessageToJsfScreen("Please enter the Landing page id");
			}
			if (StringUtil.isEmpty(candidate.getPcIdExt())) {
				sendErrorMessageToJsfScreen("Please enter the Parliament Constituency Number(provided by .net team)");
			}
			if (StringUtil.isEmpty(candidate.getStateIdExt())) {
				sendErrorMessageToJsfScreen("Please enter the State Identifier i.e HR,MH etc(provided by .net team)");
			}
			if (candidate.getParliamentConstituencyId() == null) {
				sendErrorMessageToJsfScreen("Please select a parliament Constituency");
			}
			if (isValidInput()) {
				aapService.saveCandidate(candidate);
				sendInfoMessageToJsfScreen("Candidate saved succesfully");
				showList = true;
				//candidateCacheImpl.refreshCache();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			sendErrorMessageToJsfScreen("Unable to save Post", ex);
		}

	}

	private void refreshCandidates() {
		try {
			candidates = aapService.getCandidates(512, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onMarkerDrag(MarkerDragEvent candidate) {
		marker = candidate.getMarker();
	}

	public void onStateChange(StateChangeEvent stateChangeEvent) {
		this.candidate.setDepth(stateChangeEvent.getZoomLevel());
	}

	public CandidateDto getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateDto candidate) {
		this.candidate = candidate;
		showList = false;
	}

	public MapModel getDraggableMapModel() {
		return draggableMapModel;
	}

	public void setDraggableMapModel(MapModel draggableMapModel) {
		this.draggableMapModel = draggableMapModel;
	}

	public CandidateCacheImpl getCandidateCacheImpl() {
		return candidateCacheImpl;
	}

	public void setCandidateCacheImpl(CandidateCacheImpl candidateCacheImpl) {
		this.candidateCacheImpl = candidateCacheImpl;
	}

	public List<CandidateDto> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<CandidateDto> candidates) {
		this.candidates = candidates;
	}

	public boolean isShowList() {
		return showList;
	}

	public void setShowList(boolean showList) {
		this.showList = showList;
	}

	public LocationCacheDbImpl getLocationCacheDbImpl() {
		return locationCacheDbImpl;
	}

	public void setLocationCacheDbImpl(LocationCacheDbImpl locationCacheDbImpl) {
		this.locationCacheDbImpl = locationCacheDbImpl;
	}

	public boolean isEnablePcBox() {
		return enablePcBox;
	}

	public void setEnablePcBox(boolean enablePcBox) {
		this.enablePcBox = enablePcBox;
	}

	public boolean isFormEditable() {
		return formEditable;
	}

	public void setFormEditable(boolean formEditable) {
		this.formEditable = formEditable;
	}
	
}
