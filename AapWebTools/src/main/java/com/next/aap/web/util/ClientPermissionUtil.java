package com.next.aap.web.util;

import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.UserRolePermissionDto;

public class ClientPermissionUtil {
	private static boolean checkLocationType(PostLocationType locationType, Long locationId){
		if(locationType == null || locationType == PostLocationType.NA || (locationType != PostLocationType.Global && locationId <= 0)){
			return false;
		}
		return true;
	}
	public static boolean isVoiceOfAapFbAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) && 
				(userRolePermissionDto.isSuperUser() || isAllowed(AppPermission.ADMIN_VOICE_OF_AAP_FB, userRolePermissionDto, locationId, locationType));
	}
	public static boolean isVoiceOfAapTwitterAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || isAllowed(AppPermission.ADMIN_VOICE_OF_AAP_TWITTER, userRolePermissionDto, locationId, locationType));
	}
	public static boolean isManageNewsAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.CREATE_NEWS, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.UPDATE_NEWS, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.DELETE_NEWS, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.APPROVE_NEWS, userRolePermissionDto, locationId, locationType));
	}
	public static boolean isSmsAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.ADMIN_SMS, userRolePermissionDto, locationId, locationType));
	}
	public static boolean isGlobalDonationCampaignAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.ADMIN_GLOBAL_CAMPAIGN, userRolePermissionDto, locationId, locationType));
	}
	public static boolean isEmailAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.ADMIN_EMAIL, userRolePermissionDto, locationId, locationType));
	}
	public static boolean isManageBlogAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.CREATE_BLOG, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.UPDATE_BLOG, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.DELETE_BLOG, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.APPROVE_BLOG, userRolePermissionDto, locationId, locationType));
	}
	public static boolean isManagePollAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.CREATE_POLL, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.UPDATE_POLL, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.DELETE_POLL, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.APPROVE_POLL, userRolePermissionDto, locationId, locationType));
	}
	public static boolean isManageEventAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.ADMIN_EVENT, userRolePermissionDto, locationId, locationType));
	}
	public static boolean isEditOfficeDetailAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.EDIT_OFFICE_ADDRESS, userRolePermissionDto, locationId, locationType));
	}
	
	public static boolean isManageMemberAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.ADD_MEMBER, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.VIEW_MEMBER, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.UPDATE_GLOBAL_MEMBER, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.UPDATE_MEMBER, userRolePermissionDto, locationId, locationType));
	}
	public static boolean isTreasuryAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.TREASURY, userRolePermissionDto, locationId, locationType));
	}

	public static boolean isManageUserRoleAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return checkLocationType(locationType, locationId) &&
				(userRolePermissionDto.isSuperUser() || 
				isAllowed(AppPermission.EDIT_USER_ROLES, userRolePermissionDto, locationId, locationType));
	}

	public static boolean isAllowed(AppPermission appPermission, UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		if(userRolePermissionDto.isSuperUser()){
			return true;
		}
		switch(locationType){
		case Global:
			return userRolePermissionDto.getAllPermissions().contains(appPermission);
		case STATE:
			if(userRolePermissionDto.getStatePermissions().get(locationId) == null){
				return false;
			}
			return userRolePermissionDto.getStatePermissions().get(locationId).contains(appPermission);
		case DISTRICT:
			if(userRolePermissionDto.getDistrictPermissions().get(locationId) == null){
				return false;
			}
			return userRolePermissionDto.getDistrictPermissions().get(locationId).contains(appPermission);
		case AC:
			if(userRolePermissionDto.getAcPermissions().get(locationId) == null){
				return false;
			}
			return userRolePermissionDto.getAcPermissions().get(locationId).contains(appPermission);
		case PC:
			if(userRolePermissionDto.getPcPermissions().get(locationId) == null){
				return false;
			}
			return userRolePermissionDto.getPcPermissions().get(locationId).contains(appPermission);
		case COUNTRY:
			if(userRolePermissionDto.getCountryPermissions().get(locationId) == null){
				return false;
			}
			return userRolePermissionDto.getCountryPermissions().get(locationId).contains(appPermission);
		case REGION:
			if(userRolePermissionDto.getCountryRegionPermissions().get(locationId) == null){
				return false;
			}
			return userRolePermissionDto.getCountryRegionPermissions().get(locationId).contains(appPermission);
		default :
			return false;
		}
	}
}
