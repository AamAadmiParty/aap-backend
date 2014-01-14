package com.next.aap.web.jsf.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import com.next.aap.core.service.AapService;

public class BaseUserJsfBean extends BaseJsfBean implements Serializable {

	@ManagedProperty("#{aapService}")
	protected AapService aapService;
	
	@ManagedProperty("#{menuBean}")
	protected MenuBean menuBean;

	public AapService getAapService() {
		return aapService;
	}

	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}

	public MenuBean getMenuBean() {
		return menuBean;
	}

	public void setMenuBean(MenuBean menuBean) {
		this.menuBean = menuBean;
	}

}
