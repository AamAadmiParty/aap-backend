package com.next.aap.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.persistance.AcRole;
import com.next.aap.core.persistance.AssemblyConstituency;
import com.next.aap.core.persistance.District;
import com.next.aap.core.persistance.DistrictRole;
import com.next.aap.core.persistance.Email;
import com.next.aap.core.persistance.Email.ConfirmationType;
import com.next.aap.core.persistance.FacebookAccount;
import com.next.aap.core.persistance.FacebookApp;
import com.next.aap.core.persistance.FacebookAppPermission;
import com.next.aap.core.persistance.FacebookGroup;
import com.next.aap.core.persistance.FacebookGroupMembership;
import com.next.aap.core.persistance.ParliamentConstituency;
import com.next.aap.core.persistance.PcRole;
import com.next.aap.core.persistance.Permission;
import com.next.aap.core.persistance.Phone;
import com.next.aap.core.persistance.Phone.PhoneType;
import com.next.aap.core.persistance.PlannedFacebookPost;
import com.next.aap.core.persistance.Role;
import com.next.aap.core.persistance.State;
import com.next.aap.core.persistance.StateRole;
import com.next.aap.core.persistance.TwitterAccount;
import com.next.aap.core.persistance.User;
import com.next.aap.core.persistance.dao.AcRoleDao;
import com.next.aap.core.persistance.dao.AssemblyConstituencyDao;
import com.next.aap.core.persistance.dao.DistrictDao;
import com.next.aap.core.persistance.dao.DistrictRoleDao;
import com.next.aap.core.persistance.dao.EmailDao;
import com.next.aap.core.persistance.dao.FacebookAccountDao;
import com.next.aap.core.persistance.dao.FacebookAppDao;
import com.next.aap.core.persistance.dao.FacebookAppPermissionDao;
import com.next.aap.core.persistance.dao.FacebookGroupDao;
import com.next.aap.core.persistance.dao.FacebookGroupMembershipDao;
import com.next.aap.core.persistance.dao.ParliamentConstituencyDao;
import com.next.aap.core.persistance.dao.PcRoleDao;
import com.next.aap.core.persistance.dao.PermissionDao;
import com.next.aap.core.persistance.dao.PhoneDao;
import com.next.aap.core.persistance.dao.PlannedFacebookPostDao;
import com.next.aap.core.persistance.dao.RoleDao;
import com.next.aap.core.persistance.dao.StateDao;
import com.next.aap.core.persistance.dao.StateRoleDao;
import com.next.aap.core.persistance.dao.TwitterAccountDao;
import com.next.aap.core.persistance.dao.UserDao;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PlannedFacebookPostDto;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.TwitterAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.dto.VoiceOfAapData;

@Service("aapService")
public class AapServiceImpl implements AapService, Serializable {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDao userDao;
	@Autowired
	private FacebookAccountDao facebookAccountDao;
	@Autowired
	private TwitterAccountDao twitterAccountDao;
	@Autowired
	private EmailDao emailDao;
	@Autowired
	private StateDao stateDao;
	@Autowired
	private DistrictDao districtDao;
	@Autowired
	private AssemblyConstituencyDao assemblyConstituencyDao;
	@Autowired
	private ParliamentConstituencyDao parliamentConstituencyDao;
	@Autowired
	private FacebookAppDao facebookAppDao;
	@Autowired
	private FacebookAppPermissionDao facebookAppPermissionDao;
	@Autowired
	private FacebookGroupMembershipDao facebookGroupMembershipDao;
	@Autowired
	private FacebookGroupDao facebookGroupDao;
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PhoneDao phoneDao;
	@Autowired
	private PlannedFacebookPostDao plannedFacebookPostDao;
	@Autowired
	private StateRoleDao stateRoleDao;
	@Autowired
	private DistrictRoleDao districtRoleDao;
	@Autowired
	private AcRoleDao acRoleDao;
	@Autowired
	private PcRoleDao pcRoleDao;
	
	@Value("${voa.facebook.app.id}")
	private String voiceOfAapAppId;


	@Override
	@Transactional
	public UserDto saveFacebookUser(Long existingUserId, Connection<Facebook> connection, String facebookAppId) {
		User user = null;
		// See if user exists
		if (existingUserId != null && existingUserId > 0) {
			System.out.println("existingUserId=" + existingUserId);
			user = userDao.getUserById(existingUserId);
		}

		FacebookAccount dbFacebookAccount = null;
		ConnectionData fbConnectionData = connection.createData();
		dbFacebookAccount = facebookAccountDao.getFacebookAccountByFacebookUserId(fbConnectionData.getProviderUserId());
		System.out.println("dbFacebookAccount=" + dbFacebookAccount);
		String facebookAccountEmail = connection.getApi().userOperations().getUserProfile().getEmail();

		if (dbFacebookAccount == null) {
			System.out.println("creating new dbFacebookAccount=" + dbFacebookAccount);
			// Account do not exists so create one now
			dbFacebookAccount = new FacebookAccount();
			dbFacebookAccount.setDateCreated(new Date());
			dbFacebookAccount.setFacebookUserId(fbConnectionData.getProviderUserId());
			if (fbConnectionData.getDisplayName() == null) {
				dbFacebookAccount.setUserName(facebookAccountEmail);
			} else {
				dbFacebookAccount.setUserName(connection.getDisplayName());
			}

		} else {
			user = dbFacebookAccount.getUser();
		}

		FacebookApp facebookApp = facebookAppDao.getFacebookAppByAppId(facebookAppId);
		if (facebookApp == null) {
			logger.error("Facebook Application " + facebookAppId + " is not saved in facebook_app table");
		} else {
			FacebookAppPermission facebookAppPermission = facebookAppPermissionDao.getFacebookAppPermissionByAppIdAndFacebookAccountId(facebookApp.getId(),
					dbFacebookAccount.getId());
			if (facebookAppPermission == null) {
				facebookAppPermission = new FacebookAppPermission();
			}
			facebookAppPermission.setFacebookAccount(dbFacebookAccount);
			facebookAppPermission.setFacebookApp(facebookApp);
			facebookAppPermission.setToken(fbConnectionData.getAccessToken());
			facebookAppPermission.setExpireTime(new Date(fbConnectionData.getExpireTime()));
			facebookAppPermission = facebookAppPermissionDao.saveFacebookAppPermission(facebookAppPermission);
		}
		System.out.println("user=" + user);
		// First create/update user
		if (user == null) {
			System.out.println("creating new user=" + user);
			user = new User();
			if (fbConnectionData.getDisplayName() == null) {
				user.setName(facebookAccountEmail);
			} else {
				user.setName(fbConnectionData.getDisplayName());
			}

			user.setDateCreated(new Date());
			user.setExternalId(UUID.randomUUID().toString());
		}
		//always use facebook Image Url
		user.setProfilePic(fbConnectionData.getImageUrl());
		System.out.println("user=" + user);
		user = userDao.saveUser(user);

		// check if this email already exists in our system
		if (!StringUtil.isEmpty(facebookAccountEmail)) {
			Email email = emailDao.getEmailByEmail(facebookAccountEmail);
			if (email == null) {
				// create a new Email in the system
				email = new Email();
				email.setEmail(facebookAccountEmail);
				email.setConfirmationType(ConfirmationType.CONFIRMED_FACEBOOK_ACCOUNT);
				email.setConfirmed(true);
				email.setUser(user);
				email.setDateCreated(new Date());
			} else {
				email.setDateModified(new Date());
				if (email.isConfirmed()) {
					// if existing email was confirmed
					// check if User are same
					if (email.getUser().equals(user)) {
						// if same no need to merge ot detach user
					} else {
						// merge two users data
						mergeUser(user, email.getUser());
						email.setUser(user);
					}
				} else {
					if (email.getUser() != null) {
						// detach this email from this user.. Need to write more
						// code here to
						// make sure we delete the detached user if that user do
						// not have anything like email or mobile number etc
						email.setUser(user);
					}
				}

			}
			email = emailDao.saveEmail(email);
		}

		dbFacebookAccount.setDateModified(new Date());
		dbFacebookAccount.setImageUrl(fbConnectionData.getImageUrl());
		dbFacebookAccount.setUser(user);
		dbFacebookAccount = facebookAccountDao.saveFacebookAccount(dbFacebookAccount);

		return connvertUser(user);
	}

	private UserDto connvertUser(User user) {
		UserDto returnUser = new UserDto();
		BeanUtils.copyProperties(user, returnUser);
		List<Phone> userPhones = phoneDao.getPhonesOfUser(user.getId());
		Phone onePhone = null;
		if(userPhones != null && !userPhones.isEmpty()){
			for(Phone phone:userPhones){
				if(phone.getPhoneType().equals(PhoneType.MOBILE)){
					onePhone = phone;
					break;
				}
			}
			if(onePhone == null){
				onePhone = userPhones.get(0);
			}
			returnUser.setCountryCode(onePhone.getCountryCode());
			returnUser.setMobileNumber(onePhone.getPhoneNumber());
		}
		return returnUser;
	}

	private void mergeUser(User targetUser, User sourceUser) {

	}

	@Override
	@Transactional
	public UserDto saveTwitterUser(Long existingUserId, Connection<Twitter> connection) {
		User user = null;
		// See if user exists
		if (existingUserId != null && existingUserId > 0) {
			System.out.println("existingUserId=" + existingUserId);
			user = userDao.getUserById(existingUserId);
		}

		TwitterAccount dbTwitterAccount = null;
		ConnectionData twitterConnectionData = connection.createData();
		dbTwitterAccount = twitterAccountDao.getTwitterAccountByTwitterUserId(twitterConnectionData.getProviderUserId());
		System.out.println("dbTwitterAccount=" + dbTwitterAccount);

		if (dbTwitterAccount == null) {
			System.out.println("creating new dbTwitterAccount=" + dbTwitterAccount);
			// Account do not exists so create one now
			dbTwitterAccount = new TwitterAccount();
			dbTwitterAccount.setDateCreated(new Date());
			dbTwitterAccount.setTwitterId(twitterConnectionData.getProviderUserId());
			dbTwitterAccount.setScreenName(twitterConnectionData.getDisplayName());

		} else {
			user = dbTwitterAccount.getUser();
		}
		System.out.println("user=" + user);
		// First create/update user
		if (user == null) {
			System.out.println("creating new user=" + user);
			user = new User();
			user.setName(twitterConnectionData.getDisplayName());
			user.setDateCreated(new Date());
			user.setExternalId(UUID.randomUUID().toString());
			user.setProfilePic(twitterConnectionData.getImageUrl());
		}
		user = userDao.saveUser(user);

		dbTwitterAccount.setDateModified(new Date());
		dbTwitterAccount.setToken(twitterConnectionData.getAccessToken());
		dbTwitterAccount.setTokenSecret(twitterConnectionData.getSecret());
		dbTwitterAccount.setImageUrl(twitterConnectionData.getImageUrl());
		dbTwitterAccount.setUser(user);
		dbTwitterAccount = twitterAccountDao.saveTwitterAccount(dbTwitterAccount);

		return connvertUser(user);
	}

	@Override
	public UserDto saveGoogleUser(Long existingUserid, Connection<Google> connection) {
		return null;
	}

	@Override
	@Transactional
	public LoginAccountDto getUserLoginAccounts(Long userId) {
		User user = userDao.getUserById(userId);
		LoginAccountDto loginAccountDto = new LoginAccountDto();
		loginAccountDto.setTwitterAccounts(convertTwitterAccounts(user.getTwitterAccounts()));
		loginAccountDto.setFacebookAccounts(convertFacebookAccounts(user.getFacebookAccounts()));
		return loginAccountDto;
	}

	private List<TwitterAccountDto> convertTwitterAccounts(Collection<TwitterAccount> twitterAccounts) {
		List<TwitterAccountDto> returnTwitterAccounts = new ArrayList<>();
		if (twitterAccounts != null) {
			for (TwitterAccount oneTwitterAccount : twitterAccounts) {
				returnTwitterAccounts.add(convertTwitterAccount(oneTwitterAccount));
			}
		}
		return returnTwitterAccounts;
	}

	private TwitterAccountDto convertTwitterAccount(TwitterAccount twitterAccount) {
		if (twitterAccount == null) {
			return null;
		}
		TwitterAccountDto returnTwitterAccountDto = new TwitterAccountDto();
		BeanUtils.copyProperties(twitterAccount, returnTwitterAccountDto);
		return returnTwitterAccountDto;
	}

	private List<FacebookAccountDto> convertFacebookAccounts(Collection<FacebookAccount> facebookAccounts) {
		List<FacebookAccountDto> returnTwitterAccounts = new ArrayList<>();
		if (facebookAccounts != null) {
			for (FacebookAccount oneFacebookAccount : facebookAccounts) {
				returnTwitterAccounts.add(convertFacebookAccount(oneFacebookAccount));
			}
		}
		return returnTwitterAccounts;
	}

	private FacebookAccountDto convertFacebookAccount(FacebookAccount facebookAccount) {
		if (facebookAccount == null) {
			return null;
		}
		FacebookAccountDto returnFacebookAccountDto = new FacebookAccountDto();
		BeanUtils.copyProperties(facebookAccount, returnFacebookAccountDto);
		return returnFacebookAccountDto;
	}

	@Override
	@Transactional
	public List<StateDto> getAllStates() {
		List<State> allStates = stateDao.getAllStates();
		List<StateDto> returnList = new ArrayList<StateDto>();
		for (State oneState : allStates) {
			returnList.add(convertState(oneState));
		}
		return returnList;
	}

	private StateDto convertState(State oneState) {
		if (oneState == null) {
			return null;
		}
		StateDto oneStateDto = new StateDto();
		oneStateDto.setId(oneState.getId());
		oneStateDto.setName(oneState.getName());
		oneStateDto.setDistrictDataAvailable(oneState.getDistrictDataAvailable());
		return oneStateDto;
	}

	@Override
	@Transactional
	public List<DistrictDto> getAllDistrictOfState(long stateId) {
		List<District> allDistricts = districtDao.getDistrictOfState(stateId);
		List<DistrictDto> returnList = new ArrayList<DistrictDto>();
		for (District oneDistrict : allDistricts) {
			returnList.add(convertDistrict(oneDistrict));
		}
		return returnList;
	}

	private DistrictDto convertDistrict(District oneDistrict) {
		DistrictDto oneDistrictDto = new DistrictDto();
		oneDistrictDto.setId(oneDistrict.getId());
		oneDistrictDto.setName(oneDistrict.getName());
		oneDistrictDto.setAcDataAvailable(oneDistrict.getAcDataAvailable());
		return oneDistrictDto;
	}

	@Override
	@Transactional
	public List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfDistrict(long districtId) {
		List<AssemblyConstituency> allAssemblyConstituencies = assemblyConstituencyDao.getAssemblyConstituencyOfDistrict(districtId);
		return convertAssemblyConstituencies(allAssemblyConstituencies);
	}

	private AssemblyConstituencyDto convertAssemblyConstituency(AssemblyConstituency oneAssemblyConstituency) {
		if (oneAssemblyConstituency == null) {
			return null;
		}
		AssemblyConstituencyDto oneAssemblyConstituencyDto = new AssemblyConstituencyDto();
		BeanUtils.copyProperties(oneAssemblyConstituency, oneAssemblyConstituencyDto);
		return oneAssemblyConstituencyDto;
	}

	private List<AssemblyConstituencyDto> convertAssemblyConstituencies(List<AssemblyConstituency> allAssemblyConstituencies) {
		List<AssemblyConstituencyDto> returnList = new ArrayList<AssemblyConstituencyDto>(allAssemblyConstituencies.size());
		for (AssemblyConstituency oneAssemblyConstituency : allAssemblyConstituencies) {
			returnList.add(convertAssemblyConstituency(oneAssemblyConstituency));
		}
		return returnList;
	}

	@Override
	@Transactional
	public StateDto getStateById(Long stateId) {
		State state = stateDao.getStateById(stateId);
		return convertState(state);
	}

	@Override
	@Transactional
	public List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfState(long stateId) {
		List<AssemblyConstituency> allAssemblyConstituencies = assemblyConstituencyDao.getAssemblyConstituencyOfState(stateId);
		List<AssemblyConstituencyDto> returnList = convertAssemblyConstituencies(allAssemblyConstituencies);
		return returnList;
	}

	private List<ParliamentConstituencyDto> convertParliamentConstituencyList(List<ParliamentConstituency> parliamentConstituencies) {
		List<ParliamentConstituencyDto> returnList = new ArrayList<ParliamentConstituencyDto>();
		if (parliamentConstituencies == null) {
			return returnList;
		}
		for (ParliamentConstituency oneAssemblyConstituency : parliamentConstituencies) {
			returnList.add(convertParliamentConstituency(oneAssemblyConstituency));
		}
		return returnList;
	}

	private ParliamentConstituencyDto convertParliamentConstituency(ParliamentConstituency oneParliamentConstituency) {
		ParliamentConstituencyDto oneParliamentConstituencyDto = new ParliamentConstituencyDto();
		oneParliamentConstituencyDto.setId(oneParliamentConstituency.getId());
		oneParliamentConstituencyDto.setName(oneParliamentConstituency.getName());
		return oneParliamentConstituencyDto;

	}

	@Override
	@Transactional
	public List<ParliamentConstituencyDto> getAllParliamentConstituenciesOfState(long stateId) {
		List<ParliamentConstituency> parliamentConstituencies = parliamentConstituencyDao.getParliamentConstituencyOfState(stateId);
		return convertParliamentConstituencyList(parliamentConstituencies);
	}

	@Override
	@Transactional
	public UserDto saveUser(UserDto userDto) {
		User dbUser = userDao.getUserById(userDto.getId());
		if (dbUser == null) {
			logger.error("User DO NOT Exists [id=" + userDto.getId() + "]");
			return null;
		}
		// find out if we have facebook account with given user name
		User user = userDao.getUserById(userDto.getId());
		user.setDateModified(new Date());
		// user.setEmail(userDto.getEmail());
		// user.setMobile(userDto.getMobile());
		user.setName(userDto.getName());
		user.setDateOfBirth(userDto.getDateOfBirth());
		if (userDto.getStateLivingId() != null && userDto.getStateLivingId() > 0) {
			State stateLiving = stateDao.getStateById(userDto.getStateLivingId());
			user.setStateLiving(stateLiving);
		}
		if (userDto.getDistrictLivingId() != null && userDto.getDistrictLivingId() > 0) {
			District districtLiving = districtDao.getDistrictById(userDto.getDistrictLivingId());
			user.setDistrictLiving(districtLiving);
		}
		if (userDto.getAssemblyConstituencyLivingId() != null && userDto.getAssemblyConstituencyLivingId() > 0) {
			AssemblyConstituency assemblyConstituencyLiving = assemblyConstituencyDao.getAssemblyConstituencyById(userDto.getAssemblyConstituencyLivingId());
			user.setAssemblyConstituencyLiving(assemblyConstituencyLiving);
		}

		if (userDto.getParliamentConstituencyLivingId() != null && userDto.getParliamentConstituencyLivingId() > 0) {
			ParliamentConstituency parliamentConstituencyLiving = parliamentConstituencyDao.getParliamentConstituencyById(userDto
					.getParliamentConstituencyLivingId());
			user.setParliamentConstituencyLiving(parliamentConstituencyLiving);
		}

		if (userDto.getStateVotingId() != null && userDto.getStateVotingId() > 0) {
			State stateVoting = stateDao.getStateById(userDto.getStateVotingId());
			user.setStateVoting(stateVoting);
		}
		if (userDto.getDistrictVotingId() != null && userDto.getDistrictVotingId() > 0) {
			District districtVoting = districtDao.getDistrictById(userDto.getDistrictVotingId());
			user.setDistrictVoting(districtVoting);
		}
		if (userDto.getAssemblyConstituencyVotingId() != null && userDto.getAssemblyConstituencyVotingId() > 0) {
			AssemblyConstituency assemblyConstituencyVoting = assemblyConstituencyDao.getAssemblyConstituencyById(userDto.getAssemblyConstituencyVotingId());
			user.setAssemblyConstituencyVoting(assemblyConstituencyVoting);
		}
		if (userDto.getParliamentConstituencyVotingId() != null && userDto.getParliamentConstituencyVotingId() > 0) {
			ParliamentConstituency parliamentConstituencyVoting = parliamentConstituencyDao.getParliamentConstituencyById(userDto
					.getParliamentConstituencyVotingId());
			user.setParliamentConstituencyVoting(parliamentConstituencyVoting);
		}
		user.setGender(userDto.getGender());
		user = userDao.saveUser(user);
		
		if(!StringUtil.isEmpty(userDto.getMobileNumber())){
			//save Mobile number
			List<Phone> userPhones = phoneDao.getPhonesOfUser(user.getId());
			Phone onePhone = null;
			if(userPhones == null || userPhones.isEmpty()){
				onePhone = new Phone();
				onePhone.setCountryCode(userDto.getCountryCode());
				onePhone.setDateCreated(new Date());
				onePhone.setPhoneNumber(userDto.getMobileNumber());
				onePhone.setPhoneType(PhoneType.MOBILE);
				onePhone.setUser(user);
				onePhone.setDateModified(new Date());
				onePhone = phoneDao.savePhone(onePhone);
			}else{
				for(Phone phone:userPhones){
					if(phone.getPhoneType().equals(PhoneType.MOBILE)){
						onePhone = phone;
						break;
					}
				}
				if(onePhone == null){
					onePhone = userPhones.get(0);
				}
				onePhone.setCountryCode(userDto.getCountryCode());
				onePhone.setPhoneNumber(userDto.getMobileNumber());
				onePhone.setPhoneType(PhoneType.MOBILE);
				onePhone.setUser(user);
				onePhone.setDateModified(new Date());
				onePhone = phoneDao.savePhone(onePhone);
			}
		}
		user = userDao.getUserById(user.getId());
		return connvertUser(user);
	}

	@Override
	@Transactional
	public FacebookAppPermissionDto getFacebookPermission(long facebookAppId, long facebookAccountId) {
		FacebookAppPermission facebookAppPermission = facebookAppPermissionDao.getFacebookAppPermissionByAppIdAndFacebookAccountId(facebookAppId,facebookAccountId);
		return convertFacebookAppPermission(facebookAppPermission);
	}
	private FacebookAppPermissionDto convertFacebookAppPermission(FacebookAppPermission facebookAppPermission){
		if(facebookAppPermission == null){
			return null;
		}
		FacebookAppPermissionDto facebookAppPermissionDto = new FacebookAppPermissionDto();
		BeanUtils.copyProperties(facebookAppPermission, facebookAppPermissionDto);
		return facebookAppPermissionDto;
	}

	@Override
	@Transactional
	public FacebookAppPermissionDto getVoiceOfAapFacebookPermission(long facebookAccountId) {
		FacebookAppPermission facebookAppPermission = facebookAppPermissionDao.getFacebookAppPermissionByFacebookAppIdAndFacebookAccountId(voiceOfAapAppId,facebookAccountId);
		return convertFacebookAppPermission(facebookAppPermission);
	}

	@Override
	@Transactional
	public void saveVoiceOfAapSettings(Long facebookAccountId, boolean beVoiceOfAap, boolean postOnMyTimeLine, List<String> selectedGroups, List<String> selectedPages, boolean allowTweets) {
		FacebookAccount dbFacebookAccount = facebookAccountDao.getFacebookAccountById(facebookAccountId);
		if(dbFacebookAccount == null){
			throw new RuntimeException("No such Facebook account found [id="+facebookAccountId+"]");
		}
		dbFacebookAccount.setAllowDdu(beVoiceOfAap);
		dbFacebookAccount.setVoiceOfAap(beVoiceOfAap);
		dbFacebookAccount.setAllowTimeLine(postOnMyTimeLine);
		
		dbFacebookAccount = facebookAccountDao.saveFacebookAccount(dbFacebookAccount);
		
		User user = dbFacebookAccount.getUser(); 
		user.setAllowTweets(allowTweets);
		
		List<FacebookGroupMembership> facebookGroupMemberships = facebookGroupMembershipDao.getFacebookGroupMembershipByFacebookAccountId(facebookAccountId);
		if(facebookGroupMemberships == null){
			return;
		}
		Set<String> selectedGroupIds = new HashSet<>(selectedGroups);
		
		for(FacebookGroupMembership oneFacebookGroupMembership:facebookGroupMemberships){
			if(selectedGroupIds.contains(oneFacebookGroupMembership.getFacebookGroup().getFacebookGroupExternalId())){
				oneFacebookGroupMembership.setAllowDduPost(beVoiceOfAap);
				oneFacebookGroupMembership.setAllowVoiceOfAapPost(beVoiceOfAap);
			}else{
				oneFacebookGroupMembership.setAllowDduPost(false);
				oneFacebookGroupMembership.setAllowVoiceOfAapPost(false);
			}
		}
	}

	@Override
	@Transactional
	public void saveFacebookAccountGroups(Long facebookAccountId, List<GroupMembership> userGroupMembership) {
		if(userGroupMembership == null || userGroupMembership.isEmpty()){
			return;
		}
		FacebookAccount dbFacebookAccount = facebookAccountDao.getFacebookAccountById(facebookAccountId);
		if(dbFacebookAccount == null){
			throw new RuntimeException("No such Facebook account found [id="+facebookAccountId+"]");
		}
		FacebookGroupMembership oneFacebookGroupMembership;
		FacebookGroup oneFacebookGroup;
		for(GroupMembership oneGroupMembership:userGroupMembership){
			oneFacebookGroup = facebookGroupDao.getFacebookGroupByFacebookGroupExternalId(oneGroupMembership.getId());
			if(oneFacebookGroup == null){
				oneFacebookGroup = new FacebookGroup();
				oneFacebookGroup.setFacebookGroupExternalId(oneGroupMembership.getId());
			}
			oneFacebookGroup.setGroupName(oneGroupMembership.getName());
			oneFacebookGroup = facebookGroupDao.saveFacebookGroup(oneFacebookGroup);
			
			oneFacebookGroupMembership = facebookGroupMembershipDao.getFacebookGroupMembershipByFacebookUserIdAndGroupId(facebookAccountId, oneFacebookGroup.getId());
			if(oneFacebookGroupMembership == null){
				oneFacebookGroupMembership = new FacebookGroupMembership();
				oneFacebookGroupMembership.setFacebookAccount(dbFacebookAccount);
				oneFacebookGroupMembership.setFacebookGroup(oneFacebookGroup);
				oneFacebookGroupMembership = facebookGroupMembershipDao.saveFacebookGroupMembership(oneFacebookGroupMembership);
			}
		}
		
	}

	@Override
	@Transactional
	public VoiceOfAapData getVoiceOfAapSetting(Long facebookAcountId) {
		VoiceOfAapData voiceOfAapData = new VoiceOfAapData();
		FacebookAccount facebookAccount = facebookAccountDao.getFacebookAccountById(facebookAcountId);
		voiceOfAapData.setBeVoiceOfAap(facebookAccount.isVoiceOfAap());
		voiceOfAapData.setPostOnTimeLine(facebookAccount.isAllowTimeLine());
		voiceOfAapData.setTweetFromMyAccount(facebookAccount.getUser().isAllowTweets());
		List<String> groupIdsList = new ArrayList<>();
		
		List<FacebookGroupMembership> groupMemberships = facebookGroupMembershipDao.getFacebookGroupMembershipByFacebookAccountId(facebookAcountId);
		if(groupMemberships != null){
			for(FacebookGroupMembership oneFacebookGroupMembership:groupMemberships){
				if(oneFacebookGroupMembership.isAllowVoiceOfAapPost()){
					groupIdsList.add(oneFacebookGroupMembership.getFacebookGroup().getFacebookGroupExternalId());
				}
			}
		}
		voiceOfAapData.setSelectedGroups(groupIdsList);

		return voiceOfAapData;
	}

	@Override
	@Transactional
	public void updateAllPermissionsAndRole() {
		//Make sure all permission exists in Database
		Permission onePermission;
		for(AppPermission oneAppPermission:AppPermission.values()){
			onePermission = permissionDao.getPermissionByName(oneAppPermission);
			if(onePermission == null){
				onePermission = new Permission();
				onePermission.setPermission(oneAppPermission);
				onePermission = permissionDao.savePermission(onePermission);
			}
		}
		
		//assign super admin role to Ravi's account
		
		Email email = emailDao.getEmailByEmail("ping2ravi@gmail.com");
		User user = null;
		if(email == null){
			FacebookAccount facebookAccount = facebookAccountDao.getFacebookAccountByFacebookUserId("691358626");
			if(facebookAccount == null){
				TwitterAccount twitterAccount = twitterAccountDao.getTwitterAccountByTwitterUserId("287659262");
				if(twitterAccount != null){
					user = twitterAccount.getUser();
				}
			}else{
				user = facebookAccount.getUser();
			}
		}else{
			user = email.getUser();
		}
		if(user == null){
			logger.error("No predefined user found to make a user Super Admin");			
		}else{
			user.setSuperAdmin(true);
		}
		
		
		//Now create all custom Roles
		createRoleWithPermissions("VoiceOfAapFacebookAdminRole"," User of this role will be able to make Facebook post using voice of AAP Application", true, true,false,false, AppPermission.ADMIN_VOICE_OF_AAP_FB);
		createRoleWithPermissions("VoiceOfAapTwitterAdminRole", "User of this role will be able to make Twitter post using voice of AAP Application", true, true,false, false, AppPermission.ADMIN_VOICE_OF_AAP_TWITTER);

	}
	private void createRoleWithPermissions(String name,String description,boolean addStateRoles, boolean addDistrictRoles,boolean addAcRoles,boolean addPcRoles,AppPermission...appPermissions){
		Role role = roleDao.getRoleByName(name);
		if(role == null){
			role = new Role();
			role.setName(name);
			role.setDescription(description);
			role = roleDao.saveRole(role);
		}
		if(role.getPermissions() == null){
			role.setPermissions(new HashSet<Permission>());
		}
		Permission onePermission;
		for(AppPermission oneAppPermission:appPermissions){
			onePermission = permissionDao.getPermissionByName(oneAppPermission);
			role.getPermissions().add(onePermission);
		}
		if(addStateRoles){
			List<State> allStates  = stateDao.getAllStates();
			StateRole oneStateRole;
			for(State oneSate:allStates){
				oneStateRole = stateRoleDao.getStateRoleByStateIdAndRoleId(oneSate.getId(), role.getId());
				if(oneStateRole == null){
					oneStateRole = new StateRole();
					oneStateRole.setState(oneSate);
					oneStateRole.setRole(role);
					oneStateRole = stateRoleDao.saveStateRole(oneStateRole);
				}
			}
		}
		
		if(addDistrictRoles){
			List<District> allDistricts  = districtDao.getAllDistricts();
			DistrictRole oneDistrictRole;
			for(District oneDistrict:allDistricts){
				oneDistrictRole = districtRoleDao.getDistrictRoleByDistrictIdAndRoleId(oneDistrict.getId(), role.getId());
				if(oneDistrictRole == null){
					oneDistrictRole = new DistrictRole();
					oneDistrictRole.setDistrict(oneDistrict);
					oneDistrictRole.setRole(role);
					oneDistrictRole = districtRoleDao.saveDistrictRole(oneDistrictRole);
				}
			}
		}
		
		if(addAcRoles){
			List<AssemblyConstituency> allAcs  = assemblyConstituencyDao.getAllAssemblyConstituencys();
			AcRole oneAcRole;
			for(AssemblyConstituency oneAssemblyConstituency:allAcs){
				oneAcRole = acRoleDao.getAcRoleByAcIdAndRoleId(oneAssemblyConstituency.getId(), role.getId());
				if(oneAcRole == null){
					oneAcRole = new AcRole();
					oneAcRole.setAssemblyConstituency(oneAssemblyConstituency);
					oneAcRole.setRole(role);
					oneAcRole = acRoleDao.saveAcRole(oneAcRole);
				}
			}
		}
		
		if(addPcRoles){
			List<ParliamentConstituency> allPcs  = parliamentConstituencyDao.getAllParliamentConstituencys();
			PcRole onePcRole;
			for(ParliamentConstituency oneParliamentConstituency:allPcs){
				onePcRole = pcRoleDao.getPcRoleByPcIdAndRoleId(oneParliamentConstituency.getId(), role.getId());
				if(onePcRole == null){
					onePcRole = new PcRole();
					onePcRole.setParliamentConstituency(oneParliamentConstituency);
					onePcRole.setRole(role);
					onePcRole = pcRoleDao.savePcRole(onePcRole);
				}
			}
		}
	}

	@Override
	public PlannedFacebookPostDto savePlannedFacebookPost(PlannedFacebookPostDto plannedFacebookPostDto) {
		PlannedFacebookPost plannedFacebookPost = null;
		if(plannedFacebookPostDto.getId() != null && plannedFacebookPostDto.getId() > 0){
			plannedFacebookPost = plannedFacebookPostDao.getPlannedFacebookPostById(plannedFacebookPostDto.getId());
			if(plannedFacebookPost == null){
				throw new RuntimeException("No such Post found[id="+plannedFacebookPostDto.getId()+"]");
			}
		}else{
			plannedFacebookPost = new PlannedFacebookPost();
			plannedFacebookPost.setDateCreated(new Date());
			plannedFacebookPost.setStatus(PlannedPostStatus.PENDING);
		}
		plannedFacebookPost.setCaption(plannedFacebookPostDto.getCaption());
		plannedFacebookPost.setDescription(plannedFacebookPostDto.getDescription());
		plannedFacebookPost.setLink(plannedFacebookPostDto.getLink());
		plannedFacebookPost.setMessage(plannedFacebookPostDto.getMessage());
		plannedFacebookPost.setName(plannedFacebookPostDto.getName());
		plannedFacebookPost.setPicture(plannedFacebookPostDto.getPicture());
		plannedFacebookPost.setPostingTime(plannedFacebookPostDto.getPostingTime());
		plannedFacebookPost.setPostType(plannedFacebookPostDto.getPostType());
		plannedFacebookPost.setSource(plannedFacebookPostDto.getSource());
		
		plannedFacebookPost = plannedFacebookPostDao.savePlannedFacebookPost(plannedFacebookPost);
		
		return convertPlannedFacebookPost(plannedFacebookPost);
	}

	@Override
	public List<PlannedFacebookPostDto> getPlannedFacebookPosts(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private PlannedFacebookPostDto convertPlannedFacebookPost(PlannedFacebookPost plannedFacebookPost){
		PlannedFacebookPostDto plannedFacebookPostDto = new PlannedFacebookPostDto();
		BeanUtils.copyProperties(plannedFacebookPost, plannedFacebookPostDto);
		return plannedFacebookPostDto;
	}

	@Override
	public UserRolePermissionDto getUserRolePermissions(Long userId) {
		User user = userDao.getUserById(userId);
		UserRolePermissionDto userRolePermissionDto = new UserRolePermissionDto();
		userRolePermissionDto.setSuperUser(user.isSuperAdmin());
		
		Set<Role> allUserRolesAtWorldLevel = user.getAllRoles();
		if(allUserRolesAtWorldLevel != null && !allUserRolesAtWorldLevel.isEmpty()){
			for(Role oneRole:allUserRolesAtWorldLevel){
				if(!oneRole.getRolePermissions().isEmpty()){
					userRolePermissionDto.addAllPermissions(oneRole.getRolePermissions());	
				}
			}
		}
		
		Set<StateRole> stateRoles = user.getStateRoles();
		if(stateRoles != null && !stateRoles.isEmpty()){
			for(StateRole oneStateRole:stateRoles){
				if(!oneStateRole.getRole().getRolePermissions().isEmpty()){
					userRolePermissionDto.addStatePermissions(convertState(oneStateRole.getState()), oneStateRole.getRole().getRolePermissions());
				}
			}
		}
		
		Set<DistrictRole> districtRoles = user.getDistrictRoles();
		if(districtRoles != null && !districtRoles.isEmpty()){
			for(DistrictRole oneDistrictRole:districtRoles){
				if(!oneDistrictRole.getRole().getRolePermissions().isEmpty()){
					userRolePermissionDto.addDistrictPermissions(convertDistrict(oneDistrictRole.getDistrict()), oneDistrictRole.getRole().getRolePermissions());
				}
			}
		}
		
		Set<AcRole> acRoles = user.getAcRoles();
		if(acRoles != null && !acRoles.isEmpty()){
			for(AcRole oneAcRole:acRoles){
				if(!oneAcRole.getRole().getRolePermissions().isEmpty()){
					userRolePermissionDto.addAcPermissions(convertAssemblyConstituency(oneAcRole.getAssemblyConstituency()), oneAcRole.getRole().getRolePermissions());
				}
			}
		}
		
		Set<PcRole> pcRoles = user.getPcRoles();
		if(pcRoles != null && !pcRoles.isEmpty()){
			for(PcRole onePcRole:pcRoles){
				if(!onePcRole.getRole().getRolePermissions().isEmpty()){
					userRolePermissionDto.addPcPermissions(convertParliamentConstituency(onePcRole.getParliamentConstituency()), onePcRole.getRole().getRolePermissions());
				}
			}
		}
		return userRolePermissionDto;
	}
}