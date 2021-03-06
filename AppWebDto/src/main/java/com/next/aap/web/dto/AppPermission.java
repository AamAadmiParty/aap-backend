package com.next.aap.web.dto;

public enum AppPermission {

	ADMIN_VOICE_OF_AAP_FB,
	ADMIN_VOICE_OF_AAP_TWITTER,
	CREATE_NEWS,
	UPDATE_NEWS,
	DELETE_NEWS,
	APPROVE_NEWS,
	ADD_MEMBER,
	UPDATE_MEMBER,
	UPDATE_GLOBAL_MEMBER,
	VIEW_MEMBER,
	EDIT_USER_ROLES,
	CREATE_BLOG,
	UPDATE_BLOG,
	DELETE_BLOG,
	APPROVE_BLOG,
	CREATE_POLL,
	UPDATE_POLL,
	DELETE_POLL,
	APPROVE_POLL,
	TREASURY,
	EDIT_OFFICE_ADDRESS,
	ADMIN_SMS,
	ADMIN_EMAIL,
	ADMIN_GLOBAL_CAMPAIGN,
	ADMIN_EVENT,
	ADMIN_CANDIDATE_PC,
	WEB_ADMIN_DRAFT,
	WEB_ADMIN
	;
	
	private AppPermission() {
        // TODO Auto-generated constructor stub
    }
	private AppPermission(String name, String description, boolean addStateRoles, boolean addDistrictRoles, boolean addAcRoles, boolean addPcRoles, boolean addCountryRole,
            boolean addCountryRegionRole, boolean addCuntryRegionAreaRole){
	    
	}
	
}
