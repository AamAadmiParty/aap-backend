package com.next.aap.web.util;

import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.UserRolePermissionDto;

public class ClientPermissionUtil {
	public static boolean isVoiceOfAapFbAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return isAllowed(AppPermission.ADMIN_VOICE_OF_AAP_FB, userRolePermissionDto, locationId, locationType);
	}
	public static boolean isVoiceOfAapTwitterAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return isAllowed(AppPermission.ADMIN_VOICE_OF_AAP_TWITTER, userRolePermissionDto, locationId, locationType);
	}
	public static boolean isManageNewsAllowed(UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		return 
				isAllowed(AppPermission.CREATE_NEWS, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.UPDATE_NEWS, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.DELETE_NEWS, userRolePermissionDto, locationId, locationType)
				|| isAllowed(AppPermission.APPROVE_NEWS, userRolePermissionDto, locationId, locationType);
	}

	public static boolean isAllowed(AppPermission appPermission, UserRolePermissionDto userRolePermissionDto, Long locationId, PostLocationType locationType){
		
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
		default :
			return false;
		}
	}
}
