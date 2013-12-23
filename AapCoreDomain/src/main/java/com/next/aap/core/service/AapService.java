package com.next.aap.core.service;

import java.util.List;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;

import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.VoiceOfAapData;

public interface AapService {

	UserDto saveFacebookUser(Long existingUserid, Connection<Facebook> connection, String facebookAppId);
	
	UserDto saveGoogleUser(Long existingUserid, Connection<Google> connection);
	
	UserDto saveTwitterUser(Long existingUserid, Connection<Twitter> connection);
	
	LoginAccountDto getUserLoginAccounts(Long userId);
	
	List<StateDto> getAllStates() ;
	
	StateDto getStateById(Long stateId) ;
	
	List<DistrictDto> getAllDistrictOfState(long stateId) ;
	
	List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfDistrict(long districtId) ;
	
	List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfState(long stateId) ;

	List<ParliamentConstituencyDto> getAllParliamentConstituenciesOfState(long stateId) ;
	
	UserDto saveUser(UserDto userDto);
	
	FacebookAppPermissionDto getFacebookPermission(long facebookAppId, long facebookAccountId);
	
	FacebookAppPermissionDto getVoiceOfAapFacebookPermission(long facebookAccountId);
	
	void saveVoiceOfAapSettings(Long facebookAccountId, boolean beVoiceOfAap,boolean postOnMyTimeLine,List<String> selectedGroups, List<String> selectedPages, boolean allowTweets);
	
	void saveFacebookAccountGroups(Long facebookAccountId, List<GroupMembership> userGroupMembership);
	
	VoiceOfAapData getVoiceOfAapSetting(Long facebookAcountId);
}
