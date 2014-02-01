package com.next.aap.web.jsf.beans;

import java.util.List;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.GlobalCampaignDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean
@ViewScoped
@URLMappings(mappings={
		@URLMapping(id = "adminGlobalDonationBean2", beanName="adminGlobalDonationBean", pattern = "/admin/globalcampaign", viewId = "/WEB-INF/jsf/admin_globalcampaign.xhtml")
		})

@URLBeanName("adminGlobalDonationBean")
public class AdminGlobalDonationBean extends BaseAdminJsfBean {

	private static final long serialVersionUID = 1L;
	
	private double total = 0.0;

	private List<GlobalCampaignDto> globalCmpaigns;
	private GlobalCampaignDto selectedGlobalCampaign;
	private boolean showList;
	private boolean editAllowed;

	public AdminGlobalDonationBean(){
		super(AppPermission.ADMIN_GLOBAL_CAMPAIGN, "/admin/globalcampaign");
		showList = true;
	}
	@URLAction(onPostback=false)
	public void init() throws Exception {
		if (!checkUserAccess()) {
			return;
		}
		globalCmpaigns = aapService.getGlobalCampaigns();
	}
	static Pattern myPattern = Pattern.compile("^[a-zA-Z0-9]+$");
	public void saveGlobalCampaign(){
		try{
			System.out.println("Checking if we can save campaign");
			if(isValidInput()){
				System.out.println("Saving Global Campaign");
				selectedGlobalCampaign = aapService.saveGlobalCampaign(selectedGlobalCampaign);
				globalCmpaigns = aapService.getGlobalCampaigns();
				showList = true;
				System.out.println("Campaign Saved");
			}
		}catch(Exception ex){
			System.out.println("Got exception when Saving Global Campaign");
			sendErrorMessageToJsfScreen(ex);
		}
	}
	public void createGlobalCampaign(){
		selectedGlobalCampaign = new GlobalCampaignDto();
		editAllowed = true;
		showList = false;
	}
	public void cancel(){
		selectedGlobalCampaign = new GlobalCampaignDto();
		showList = true;
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	public List<GlobalCampaignDto> getGlobalCmpaigns() {
		return globalCmpaigns;
	}
	public void setGlobalCmpaigns(List<GlobalCampaignDto> globalCmpaigns) {
		this.globalCmpaigns = globalCmpaigns;
	}
	public GlobalCampaignDto getSelectedGlobalCampaign() {
		return selectedGlobalCampaign;
	}
	public void setSelectedGlobalCampaign(GlobalCampaignDto selectedGlobalCampaign) {
		System.out.println("selectedGlobalCampaign="+selectedGlobalCampaign);
		this.selectedGlobalCampaign = selectedGlobalCampaign;
		editAllowed = false;
		showList = false;
	}
	public boolean isShowList() {
		return showList;
	}
	public void setShowList(boolean showList) {
		this.showList = showList;
	}
	public boolean isEditAllowed() {
		return editAllowed;
	}
	public void setEditAllowed(boolean editAllowed) {
		this.editAllowed = editAllowed;
	}
}
