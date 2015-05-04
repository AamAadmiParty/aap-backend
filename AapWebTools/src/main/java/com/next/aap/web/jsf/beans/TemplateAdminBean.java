package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.util.StringUtils;

import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.TemplateDto;
import com.next.aap.web.dto.TemplateUrlDto;
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
    private List<TemplateUrlDto> templateUrls;
    private TemplateUrlDto selectedTemplateUrl;
	
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
    }

    public boolean isShowTemplateList() {
        return showTemplateList;
    }

    public void setShowTemplateList(boolean showTemplateList) {
        this.showTemplateList = showTemplateList;
    }

    public List<TemplateUrlDto> getTemplateUrls() {
        return templateUrls;
    }

    public void setTemplateUrls(List<TemplateUrlDto> templateUrls) {
        this.templateUrls = templateUrls;
    }

    public TemplateUrlDto getSelectedTemplateUrl() {
        return selectedTemplateUrl;
    }

    public void setSelectedTemplateUrl(TemplateUrlDto selectedTemplateUrl) {
        this.selectedTemplateUrl = selectedTemplateUrl;
    }

}
