package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
import com.next.aap.core.util.MyaapInUtil;
import com.next.aap.web.cache.CandidateCacheImpl;
import com.next.aap.web.cache.LocationCacheDbImpl;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.CandidateDto;
import com.next.aap.web.dto.LocationCampaignDto;
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
	
	private String baseUrl = "http://my.aamaadmiparty.org";
	private String baseDonationUrl="https://donate.aamaadmiparty.org?utm_source=myaap&utm_medium=web&utm_term=donate-url&utm_content=donation&utm_campaign=candidate";
	//private String baseDonationUrl1="https://donate.aamaadmiparty.org/?State=UP&Loksabha=435";
	
	@ManagedProperty("#{candidateCacheImpl}")
	protected CandidateCacheImpl candidateCacheImpl;
	
	@ManagedProperty("#{locationCacheDbImpl}")
	private LocationCacheDbImpl locationCacheDbImpl;
	
	@ManagedProperty("#{myaapInUtil}")
	private MyaapInUtil myaapInUtil;
	
	List<StateDto> stateList;
	
	static Pattern myPattern = Pattern.compile("^[a-zA-Z0-9]+$");
	
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
				}else{
					StateDto selectedState = locationCacheDbImpl.getStateById(candidate.getStateId());
					if(selectedState != null){
						String selectedStateName = selectedState.getName();
						selectedStateName = selectedStateName.replaceAll(" " , "");
						selectedStateName = selectedStateName.toLowerCase();
						candidate.setUrlTextPart1(selectedStateName);	
					}
					
					ParliamentConstituencyDto selectedPc = locationCacheDbImpl.getParliamentConstituenciesById(candidate.getParliamentConstituencyId());
					if(selectedPc != null){
						String selectedPcName = selectedPc.getName();
						selectedPcName = selectedPcName.replaceAll(" " , "");
						selectedPcName = selectedPcName.toLowerCase();
						candidate.setUrlTextPart2(selectedPcName);
						candidate.setLandingPageUrlId(selectedPcName);
						candidate.setDonatePageUrlId("donate4"+selectedPcName);
					}
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
			}else{
				if(!myPattern.matcher(candidate.getDonatePageUrlId()).find()){
					sendErrorMessageToJsfScreen("Donation page URL Identifier/Name["+candidate.getDonatePageUrlId()+"] must contain only number and alphabets");
				}
				if(myaapInUtil.isUrlAlreadyExists(candidate.getDonatePageUrlId())){
					sendErrorMessageToJsfScreen("Donation page URL Identifier/Name["+candidate.getDonatePageUrlId()+"] already used, please try something else");
				}
			}
			if (StringUtil.isEmpty(candidate.getLandingPageUrlId())) {
				sendErrorMessageToJsfScreen("Please enter the Landing page id");
			}else{
				if(!myPattern.matcher(candidate.getLandingPageUrlId()).find()){
					sendErrorMessageToJsfScreen("Donation page URL Identifier/Name["+candidate.getLandingPageUrlId()+"] must contain only number and alphabets");
				}
				if(myaapInUtil.isUrlAlreadyExists(candidate.getLandingPageUrlId())){
					sendErrorMessageToJsfScreen("Donation page URL Identifier/Name["+candidate.getLandingPageUrlId()+"] already used, please try something else");
				}
			}
			if (StringUtil.isEmpty(candidate.getPcIdExt())) {
				sendErrorMessageToJsfScreen("Please enter the Parliament Constituency Number(provided by .net team)");
			}
			if (StringUtil.isEmpty(candidate.getStateIdExt())) {
				sendErrorMessageToJsfScreen("Please enter the State Identifier i.e HR,MH etc(provided by .net team)");
			}
			if (StringUtil.isEmpty(candidate.getUrlTextPart1())) {
				sendErrorMessageToJsfScreen("Please enter the targeturl part 1(Usually state name)");
			}
			if (StringUtil.isEmpty(candidate.getUrlTextPart2())) {
				sendErrorMessageToJsfScreen("Please enter the targeturl part 1(Usually loksabha name)");
			}
			
			if (candidate.getParliamentConstituencyId() == null) {
				sendErrorMessageToJsfScreen("Please select a parliament Constituency");
			}
			
			if (isValidInput()) {
				System.out.println("Valid Input, saving it now ");
				System.out.println("Getting Location Campaign For " + candidate.getParliamentConstituencyId());
				LocationCampaignDto locationCampaign = aapService.getDefaultPcLocationCampaign(candidate.getParliamentConstituencyId());
				System.out.println("locationCampaign : " + locationCampaign);
				String campaignId = "temp";
				if(locationCampaign != null){
					campaignId = locationCampaign.getCampaignId();
				}
				if(candidate.getId() == null || candidate.getId() <= 0){
					String donationPageFullUrl = baseDonationUrl +"&State=" +candidate.getStateIdExt()+"&Loksabha="+candidate.getPcIdExt() +"&cid=lcid="+campaignId;
					myaapInUtil.createShortUrl(donationPageFullUrl, candidate.getDonatePageUrlId());
					candidate.setDonationPageFullUrl(donationPageFullUrl);
					candidate.setLandingPageSmallUrl("http://myaap.in/"+candidate.getLandingPageUrlId());
					
					String landingPageFullUrl = baseUrl +"/candidate/"+candidate.getUrlTextPart1()+"/"+candidate.getUrlTextPart2();
					myaapInUtil.createShortUrl(landingPageFullUrl, candidate.getLandingPageUrlId());
					candidate.setLandingPageFullUrl(landingPageFullUrl);

				}

				candidate = aapService.saveCandidate(candidate);
				sendInfoMessageToJsfScreen("Candidate saved succesfully");
				showList = true;
				refreshCandidates();
				candidateCacheImpl.refreshCache();
			}else{
				System.out.println("Error foudn so can not save");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			sendErrorMessageToJsfScreen("Unable to save Post" + ex.getMessage());
		}

	}
	public void showCandidate(ActionEvent actionEvent){
		System.out.println("showCandidate="+actionEvent+", candidate="+candidate);
	}
	public void showCandidate(){
		System.out.println("candidate="+candidate);
	}

	private void refreshCandidates() {
		try {
			candidates = aapService.getCandidates(512, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onMarkerDrag(MarkerDragEvent markerDragEvent) {
		System.out.println("onMarkerDrag:"+markerDragEvent);
		marker = markerDragEvent.getMarker();
	}

	public void onStateChange(StateChangeEvent stateChangeEvent) {
		System.out.println("onStateChange:"+stateChangeEvent);
		this.candidate.setDepth(stateChangeEvent.getZoomLevel());
	}

	public CandidateDto getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateDto candidate) {
		this.candidate = candidate;
		System.out.println("Candidate = "+candidate);
		showList = false;
		formEditable = true;
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

	public MyaapInUtil getMyaapInUtil() {
		return myaapInUtil;
	}

	public void setMyaapInUtil(MyaapInUtil myaapInUtil) {
		this.myaapInUtil = myaapInUtil;
	}
	
}
