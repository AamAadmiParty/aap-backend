package com.next.aap.web.jsf.beans;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.ImageIO;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.exception.AppException;
import com.next.aap.core.util.MyaapInUtil;
import com.next.aap.web.cache.CandidateCacheImpl;
import com.next.aap.web.cache.LocationCacheDbImpl;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.CandidateDto;
import com.next.aap.web.dto.ElectionDto;
import com.next.aap.web.dto.LocationCampaignDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.util.AwsImageUploadUtil;
import com.next.aap.web.util.AwsImageUtil;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ManagedBean
@SessionScoped
@URLMapping(id = "adminCandidateBean", beanName = "adminCandidateBean", pattern = "/admin/candidate", viewId = "/WEB-INF/jsf/admin_candidate.xhtml")
@URLBeanName("adminCandidateBean")
public class AdminCandidateBean extends BaseAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private CandidateDto candidate = new CandidateDto();
	
	List<CandidateDto> candidates;

    List<ElectionDto> elections;

	private boolean showList = true;
    private boolean showPc = false;
    private boolean showAc = false;
	private boolean enablePcBox = false;
    private boolean enableAcBox = false;
	private boolean formEditable = false;
    private String urlMidPart = "";
    private Long selectedElectionId;
	
	private String baseUrl = "http://my.aamaadmiparty.org";
	private String baseDonationUrl="https://donate.aamaadmiparty.org?utm_source=myaap&utm_medium=web&utm_term=donate-url&utm_content=donation&utm_campaign=candidate";
	//private String baseDonationUrl1="https://donate.aamaadmiparty.org/?State=UP&Loksabha=435";
	
	@ManagedProperty("#{candidateCacheImpl}")
	protected CandidateCacheImpl candidateCacheImpl;
	
	@ManagedProperty("#{locationCacheDbImpl}")
	private LocationCacheDbImpl locationCacheDbImpl;
	
	@ManagedProperty("#{awsImageUtil}")
	private AwsImageUtil awsImageUtil;
	
	@ManagedProperty("#{awsImageUploadUtil}")
	private AwsImageUploadUtil awsImageUploadUtil;
	

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
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = dimg = new BufferedImage(newW, newH, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
    }  
	public void handleFileUpload(FileUploadEvent event) {  
		//.
		
		System.out.println("event.getFileName()" +event.getFile().getFileName());
		System.out.println("event.getContentType()" +event.getFile().getContentType());
		System.out.println("event.getSize()" +event.getFile().getSize());
		logger.info("event.getFileName()" +event.getFile().getFileName());
		logger.info("event.getContentType()" +event.getFile().getContentType());
		logger.info("event.getSize()" +event.getFile().getSize());
		logger.info("awsImageUtil=" +awsImageUtil+",myaapInUtil="+myaapInUtil);
		logger.info("awsImageUploadUtil=" +awsImageUploadUtil+",myaapInUtil="+myaapInUtil);
		
		String imageType = ".jpg";
		if("image/png".equals(event.getFile().getContentType())){
			imageType = ".png";
		}
		if("image/jpeg".equals(event.getFile().getContentType())){
			imageType = ".jpg";
		}
		String remoteFileName = candidate.getId() + imageType;
			try {
				String httpFilePath = awsImageUtil.uploadCandidateProfileImage(remoteFileName, event.getFile().getInputstream());
				candidate.setImageUrl(httpFilePath);
				
				candidate.setImageUrl64(createAndUploadIcons(event.getFile().getInputstream(), imageType, 64));
				candidate.setImageUrl32(createAndUploadIcons(event.getFile().getInputstream(), imageType, 32));
				System.out.println("handleFileUpload:candidateDto.getImageUrl64()="+candidate.getImageUrl64());
				System.out.println("handleFileUpload:candidateDto.getImageUrl32()="+candidate.getImageUrl32());

				candidate = aapService.saveCandidate(candidate);
				System.out.println("httpFilePath = " +httpFilePath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (AppException e) {
				e.printStackTrace();
			}
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
	private String createAndUploadIcons(InputStream is,String imageType, int size) throws IOException, AppException{
		BufferedImage bufferedImage = ImageIO.read(is);
		BufferedImage iconImage = resize(bufferedImage, size, size);
		String remoteIconFileName = candidate.getId()+"_"+size+imageType;
		String localIconFileNameAndPath = "/tmp/"+remoteIconFileName;
		ImageIO.write(iconImage, "png", new FileOutputStream(localIconFileNameAndPath));
		String httpFilePath = awsImageUtil.uploadCandidateProfileImage(remoteIconFileName, new FileInputStream(localIconFileNameAndPath));
		System.out.println("createAndUploadIcons : httpFilePath = " +httpFilePath);
		return httpFilePath;
	}
	public boolean isFileUploadDisabled(){
		if(candidate.getId() == null || candidate.getId() <= 0){
			return true;
		}
		return false;
	}

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback = false)
	public void init() throws Exception {

		if (!checkUserAccess()) {
			return;
		}

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
			
            elections = aapService.getAllElections();
			stateList = locationCacheDbImpl.getAllStates();
			
            // refreshCandidates();
		} catch (Exception ex) {
			sendErrorMessageToJsfScreen(ex);
		}

	}

    public void handleElectionChange(AjaxBehaviorEvent event) {
        candidate.setElectionId(selectedElectionId);
        refreshCandidates();
    }

    public void handleCandidateTypeChange(AjaxBehaviorEvent event) {
        showPc = false;
        showAc = false;

        urlMidPart = "/";
        if ("MLA".equalsIgnoreCase(candidate.getCandidateType())) {
            showAc = true;
            formEditable = true;
            urlMidPart = "/ac/";
        } else if ("MP".equalsIgnoreCase(candidate.getCandidateType())) {
            showPc = true;
            formEditable = true;
        } else {
            formEditable = false;
        }
    }
	public void handleStateChange(AjaxBehaviorEvent event) {
		try {
			if (candidate.getStateId() == null || candidate.getStateId() <= 0) {
				enablePcBox = false;
                enableAcBox = false;
			} else {
				enablePcBox = true;
                enableAcBox = true;
			}
			formEditable = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public static void main(String[] args){
		String selectedStateName = "Ambaba (sc)";
		selectedStateName = selectedStateName.replaceAll("\\(sc\\)" , "");
		System.out.println(selectedStateName);
	}
	public void handlePcChange(AjaxBehaviorEvent event) {
		try {
			if (candidate.getParliamentConstituencyId() == null || candidate.getParliamentConstituencyId() <= 0) {
				formEditable = false;
			} else {
				formEditable = true;
                CandidateDto existingCandidate = aapService.getCandidateByPcIdAndElectionId(candidate.getParliamentConstituencyId(), candidate.getElectionId());
				if(existingCandidate != null){
					candidate = existingCandidate;
				}else{
					CandidateDto selectedCandidate = candidate;
					candidate = new CandidateDto();
					candidate.setStateId(selectedCandidate.getStateId());
                    candidate.setCandidateType(selectedCandidate.getCandidateType());
                    candidate.setElectionId(selectedElectionId);
					candidate.setParliamentConstituencyId(selectedCandidate.getParliamentConstituencyId());
					candidate.setDepth(defaultDepth);
                    candidate.setVoteUrl(selectedCandidate.getVoteUrl());
                    candidate.setTwitterId(selectedCandidate.getTwitterId());
					StateDto selectedState = locationCacheDbImpl.getStateById(candidate.getStateId());
					if(selectedState != null){
						String selectedStateName = selectedState.getName();
						selectedStateName = selectedStateName.toLowerCase();
						selectedStateName = selectedStateName.replaceAll(" " , "");
						selectedStateName = selectedStateName.replaceAll("&" , "");
						selectedStateName = selectedStateName.replaceAll("\\(sc\\)" , "");
						selectedStateName = selectedStateName.replaceAll("\\(st\\)" , "");
						candidate.setUrlTextPart1(selectedStateName);	
					}
					
					ParliamentConstituencyDto selectedPc = locationCacheDbImpl.getParliamentConstituenciesById(candidate.getParliamentConstituencyId());
					if(selectedPc != null){
						String selectedPcName = selectedPc.getName();
						selectedPcName = selectedPcName.replaceAll(" " , "");
						selectedPcName = selectedPcName.toLowerCase();
						selectedPcName = selectedPcName.replaceAll("&" , "");
						selectedPcName = selectedPcName.replaceAll("\\(sc\\)" , "");
						selectedPcName = selectedPcName.replaceAll("\\(st\\)" , "");
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

    public void handleAcChange(AjaxBehaviorEvent event) {
        try {
            if (candidate.getAssemblyConstituencyId() == null || candidate.getAssemblyConstituencyId() <= 0) {
                formEditable = false;
            } else {
                formEditable = true;
                CandidateDto existingCandidate = aapService.getCandidateByAcIdAndElectionId(candidate.getAssemblyConstituencyId(), candidate.getElectionId());
                if (existingCandidate != null) {
                    candidate = existingCandidate;
                } else {
                    CandidateDto selectedCandidate = candidate;
                    candidate = new CandidateDto();
                    candidate.setStateId(selectedCandidate.getStateId());
                    candidate.setAssemblyConstituencyId(selectedCandidate.getAssemblyConstituencyId());
                    candidate.setDepth(defaultDepth);
                    candidate.setElectionId(selectedElectionId);
                    candidate.setCandidateType(selectedCandidate.getCandidateType());
                    candidate.setVoteUrl(selectedCandidate.getVoteUrl());
                    candidate.setTwitterId(selectedCandidate.getTwitterId());
                    StateDto selectedState = locationCacheDbImpl.getStateById(candidate.getStateId());
                    if (selectedState != null) {
                        String selectedStateName = selectedState.getName();
                        selectedStateName = selectedStateName.toLowerCase();
                        selectedStateName = selectedStateName.replaceAll(" ", "");
                        selectedStateName = selectedStateName.replaceAll("&", "");
                        selectedStateName = selectedStateName.replaceAll("\\(sc\\)", "");
                        selectedStateName = selectedStateName.replaceAll("\\(st\\)", "");
                        candidate.setUrlTextPart1(selectedStateName);
                    }

                    AssemblyConstituencyDto selectedAc = locationCacheDbImpl.getAssemblyConstituencyById(candidate.getAssemblyConstituencyId());
                    if (selectedAc != null) {
                        String selectedAcName = selectedAc.getName();
                        selectedAcName = selectedAcName.replaceAll(" ", "");
                        selectedAcName = selectedAcName.toLowerCase();
                        selectedAcName = selectedAcName.replaceAll("&", "");
                        selectedAcName = selectedAcName.replaceAll("\\(sc\\)", "");
                        selectedAcName = selectedAcName.replaceAll("\\(st\\)", "");
                        candidate.setUrlTextPart2(selectedAcName);
                        candidate.setLandingPageUrlId(selectedAcName);
                        candidate.setDonatePageUrlId("donate4" + selectedAcName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public List<StateDto> getStateList(){
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

    public List<AssemblyConstituencyDto> getAcList() {
        if (candidate == null || candidate.getStateId() == null || candidate.getStateId() <= 0) {
            return new ArrayList<>();
        }
        return locationCacheDbImpl.getAllAssemblyConstituenciesOfState(candidate.getStateId());
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
        candidate.setElectionId(selectedElectionId);
        // Hard code logic for now
        updateUi();
    }

    private void updateUi() {
        if (selectedElectionId == 1) {
            candidate.setCandidateType("MP");
        }
        if (selectedElectionId == 2) {
            candidate.setCandidateType("MLA");
        }
        showList = false;
        handleCandidateTypeChange(null);
	}
	public void cancel(ActionEvent actionEvent) {
		showList = true;
	}
	public void saveCandidate(ActionEvent actionEvent) {
		try {
			logger.info("awsImageUtil=" +awsImageUtil+",myaapInUtil="+myaapInUtil);
			/*
			if (StringUtil.isEmpty(candidate.getName())) {
				sendErrorMessageToJsfScreen("Please enter Name");
			}
			
			if (StringUtil.isEmpty(candidate.getContent())) {
				sendErrorMessageToJsfScreen("Please enter Candidate Description");
			}
			*/
			if(candidate.getId() == null || candidate.getId() <= 0){
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
			}
			
            if (StringUtil.isEmpty(candidate.getCandidateType())) {
                sendErrorMessageToJsfScreen("Please select candidate Type");
            }
            if ("MP".equalsIgnoreCase(candidate.getCandidateType())) {
                if (StringUtil.isEmpty(candidate.getPcIdExt())) {
                    sendErrorMessageToJsfScreen("Please enter the Parliament Constituency Number(provided by .net team)");
                }
                if (candidate.getParliamentConstituencyId() == null) {
                    sendErrorMessageToJsfScreen("Please select a parliament Constituency");
                }
            }
            if ("MLA".equalsIgnoreCase(candidate.getCandidateType())) {
                if (StringUtil.isEmpty(candidate.getAcIdExt())) {
                    sendErrorMessageToJsfScreen("Please enter the Assembly Constituency Number(provided by .net team)");
                }
                if (candidate.getAssemblyConstituencyId() == null) {
                    sendErrorMessageToJsfScreen("Please select a Assembly Constituency");
                }
            }
			if (StringUtil.isEmpty(candidate.getStateIdExt())) {
				sendErrorMessageToJsfScreen("Please enter the State Identifier i.e HR,MH etc(provided by .net team)");
			}
			if (StringUtil.isEmpty(candidate.getUrlTextPart1())) {
				sendErrorMessageToJsfScreen("Please enter the targeturl part 1(Usually state name)");
			}
			if (StringUtil.isEmpty(candidate.getUrlTextPart2())) {
                sendErrorMessageToJsfScreen("Please enter the targeturl part 1(Usually loksabha or vishansabha name)");
			}
			
			if (isValidInput()) {
				LocationCampaignDto locationCampaign = null;
				if ("MP".equalsIgnoreCase(candidate.getCandidateType())) {
				    locationCampaign = aapService.getDefaultPcLocationCampaign(candidate.getParliamentConstituencyId());
                } else {
                    locationCampaign = aapService.getDefaultAcLocationCampaign(candidate.getAssemblyConstituencyId());
	            }
				String campaignId = "temp";
				if(locationCampaign != null){
					campaignId = locationCampaign.getCampaignId();
					candidate.setLocationCampaignId(campaignId);
				}
				if(candidate.getId() == null || candidate.getId() <= 0){
                    String donationPageFullUrl;
                    if ("MP".equalsIgnoreCase(candidate.getCandidateType())) {
                        donationPageFullUrl = baseDonationUrl + "&State=" + candidate.getStateIdExt() + "&Loksabha=" + candidate.getPcIdExt() + "&cid=lcid=" + campaignId;
                    } else {
                        donationPageFullUrl = baseDonationUrl + "&State=" + candidate.getStateIdExt() + "&Loksabha=" + candidate.getPcIdExt() + "&Vidhan=" + candidate.getAcIdExt() + "&cid=lcid="
                                + campaignId;
                    }

					myaapInUtil.createShortUrl(donationPageFullUrl, candidate.getDonatePageUrlId());
					candidate.setDonationPageFullUrl(donationPageFullUrl);
					candidate.setLandingPageSmallUrl("http://myaap.in/"+candidate.getLandingPageUrlId());
                    String landingPageFullUrl;
                    if ("MP".equalsIgnoreCase(candidate.getCandidateType())) {
                        landingPageFullUrl = baseUrl +"/candidate/"+candidate.getUrlTextPart1()+"/"+candidate.getUrlTextPart2()+".html";
                    } else {
                        landingPageFullUrl = baseUrl + "/candidate/" + candidate.getUrlTextPart1() + "/ac/" + candidate.getUrlTextPart2() + ".html";
                    }
					myaapInUtil.createShortUrl(landingPageFullUrl, candidate.getLandingPageUrlId());
					candidate.setLandingPageFullUrl(landingPageFullUrl);

				}
				candidate.setLongitude(marker.getLatlng().getLng());
				candidate.setLattitude(marker.getLatlng().getLat());

				candidate = aapService.saveCandidate(candidate);
				sendInfoMessageToJsfScreen("Candidate saved succesfully");
				showList = true;
				refreshCandidates();
				candidateCacheImpl.refreshCache();
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
        if (candidate.getElectionId() == null || candidate.getElectionId() <= 0) {
            candidates = new ArrayList<>();
        } else {
            try {
                candidates = aapService.getAllCandidatesOfElection(candidate.getElectionId());
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
    }

	public void onMarkerDrag(MarkerDragEvent markerDragEvent) {
		marker = markerDragEvent.getMarker();
	}

	public void onStateChange(StateChangeEvent stateChangeEvent) {
		this.candidate.setDepth(stateChangeEvent.getZoomLevel());
	}

	public CandidateDto getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateDto candidate) {
		this.candidate = candidate;
		LatLng coord1 = new LatLng(candidate.getLattitude(), candidate.getLongitude());
		// Draggable
		marker.setLatlng(coord1);
		showList = false;
		formEditable = true;
        selectedElectionId = candidate.getElectionId();
        updateUi();
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
	public AwsImageUtil getAwsImageUtil() {
		return awsImageUtil;
	}
	public void setAwsImageUtil(AwsImageUtil awsImageUtil) {
		this.awsImageUtil = awsImageUtil;
	}
	public AwsImageUploadUtil getAwsImageUploadUtil() {
		return awsImageUploadUtil;
	}
	public void setAwsImageUploadUtil(AwsImageUploadUtil awsImageUploadUtil) {
		this.awsImageUploadUtil = awsImageUploadUtil;
	}

    public List<ElectionDto> getElections() {
        return elections;
    }

    public void setElections(List<ElectionDto> elections) {
        this.elections = elections;
    }

    public boolean isShowPc() {
        return showPc;
    }

    public void setShowPc(boolean showPc) {
        this.showPc = showPc;
    }

    public boolean isShowAc() {
        return showAc;
    }

    public void setShowAc(boolean showAc) {
        this.showAc = showAc;
    }

    public boolean isEnableAcBox() {
        return enableAcBox;
    }

    public void setEnableAcBox(boolean enableAcBox) {
        this.enableAcBox = enableAcBox;
    }

    public String getUrlMidPart() {
        return urlMidPart;
    }

    public void setUrlMidPart(String urlMidPart) {
        this.urlMidPart = urlMidPart;
    }

    public Long getSelectedElectionId() {
        return selectedElectionId;
    }

    public void setSelectedElectionId(Long selectedElectionId) {
        this.selectedElectionId = selectedElectionId;
    }

}
