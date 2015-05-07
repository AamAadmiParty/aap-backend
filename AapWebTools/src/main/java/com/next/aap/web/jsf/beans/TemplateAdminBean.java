package com.next.aap.web.jsf.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.util.StringUtils;

import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.TemplateDto;
import com.next.aap.web.dto.TemplateUrlDto;
import com.next.aap.web.dto.TemplateUrlTypeDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ManagedBean
//@Scope("session")
@ViewScoped
@URLMapping(id = "templateAdminBean", beanName = "templateAdminBean", pattern = "/admin/templates", viewId = "/WEB-INF/jsf/admin_template.xhtml")
@URLBeanName("templateAdminBean")
public class TemplateAdminBean extends BaseMultiPermissionAdminJsfBean {

	private static final long serialVersionUID = 1L;

    private List<TemplateDto> templates;
    private TemplateDto selectedTemplate;
	
    private boolean showTemplateList = true;
    private TemplateUrlDto selectedTemplateUrl;
    private String selectedUrl;
    private Map<String, String> urls;
	
	public TemplateAdminBean(){
        super("/admin/templates", AppPermission.WEB_ADMIN, AppPermission.WEB_ADMIN_DRAFT);
	}
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		if(!checkUserAccess()){
			return;
		}
		refreshTemplateList();
        urls = new HashMap<String, String>();
        List<TemplateUrlTypeDto> templateUrlTypeDtos = aapService.getAllTemplateUrlTypes();
        for (TemplateUrlTypeDto oneTemplateUrlTypeDto : templateUrlTypeDtos) {
            urls.put(oneTemplateUrlTypeDto.getUrl(), oneTemplateUrlTypeDto.getUrl());
        }
	}
	private void refreshTemplateList(){
        try {
            templates = aapService.getAllTemplates(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}

    public void handleUrlSelection() {

        selectedTemplateUrl = null;
        for (TemplateUrlDto oneTemplateUrlDto : selectedTemplate.getTemplateUrlDtos()) {
            logger.info("oneTemplateUrlDto.getUrl()={}, selectedUrl={}", oneTemplateUrlDto.getUrl(), selectedUrl);
            if (oneTemplateUrlDto.getUrl().equals(selectedUrl)) {
                selectedTemplateUrl = oneTemplateUrlDto;
                break;
            }
        }
        if (selectedTemplateUrl == null) {
            selectedTemplateUrl = new TemplateUrlDto();
            selectedTemplateUrl.setUrl(selectedUrl);
        }
    }

    public void createTemplate() {
        selectedTemplate = new TemplateDto();
        showTemplateList = false;
    }

    public void cancel() {
        showTemplateList = true;
    }

    public void saveTemplate() {
        if (StringUtils.isEmpty(selectedTemplate.getName())) {
            sendErrorMessageToJsfScreen("Please enter Name for this template");
        }
        if (isValidInput()) {
            try {
                selectedTemplate = aapService.saveTemplate(selectedTemplate);
                if (selectedTemplateUrl != null) {
                    selectedTemplateUrl.setTemplateId(selectedTemplate.getId());
                    aapService.saveTemplateUrl(selectedTemplateUrl);
                }
                refreshTemplateList();
                cancel();
            } catch (Exception e) {
                sendErrorMessageToJsfScreen(e);
            }
        }
    }

    public List<TemplateDto> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplateDto> templates) {
        this.templates = templates;
    }

    public TemplateDto getSelectedTemplate() {
        return selectedTemplate;
    }

    public void setSelectedTemplate(TemplateDto selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
        showTemplateList = false;
    }

    public boolean isShowTemplateList() {
        return showTemplateList;
    }

    public void setShowTemplateList(boolean showTemplateList) {
        this.showTemplateList = showTemplateList;
    }

    public TemplateUrlDto getSelectedTemplateUrl() {
        return selectedTemplateUrl;
    }

    public void setSelectedTemplateUrl(TemplateUrlDto selectedTemplateUrl) {
        this.selectedTemplateUrl = selectedTemplateUrl;
    }

    public String getSelectedUrl() {
        return selectedUrl;
    }

    public void setSelectedUrl(String selectedUrl) {
        this.selectedUrl = selectedUrl;
    }

    public Map<String, String> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, String> urls) {
        this.urls = urls;
    }

}
