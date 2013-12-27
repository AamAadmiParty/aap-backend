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
import com.next.aap.core.persistance.Country;
import com.next.aap.core.persistance.District;
import com.next.aap.core.persistance.DistrictRole;
import com.next.aap.core.persistance.Email;
import com.next.aap.core.persistance.Email.ConfirmationType;
import com.next.aap.core.persistance.FacebookAccount;
import com.next.aap.core.persistance.FacebookApp;
import com.next.aap.core.persistance.FacebookAppPermission;
import com.next.aap.core.persistance.FacebookGroup;
import com.next.aap.core.persistance.FacebookGroupMembership;
import com.next.aap.core.persistance.FacebookPage;
import com.next.aap.core.persistance.FacebookPost;
import com.next.aap.core.persistance.ParliamentConstituency;
import com.next.aap.core.persistance.PcRole;
import com.next.aap.core.persistance.Permission;
import com.next.aap.core.persistance.Phone;
import com.next.aap.core.persistance.Tweet;
import com.next.aap.core.persistance.Phone.PhoneType;
import com.next.aap.core.persistance.PlannedFacebookPost;
import com.next.aap.core.persistance.PlannedTweet;
import com.next.aap.core.persistance.Role;
import com.next.aap.core.persistance.State;
import com.next.aap.core.persistance.StateRole;
import com.next.aap.core.persistance.TwitterAccount;
import com.next.aap.core.persistance.User;
import com.next.aap.core.persistance.dao.AcRoleDao;
import com.next.aap.core.persistance.dao.AssemblyConstituencyDao;
import com.next.aap.core.persistance.dao.CountryDao;
import com.next.aap.core.persistance.dao.DistrictDao;
import com.next.aap.core.persistance.dao.DistrictRoleDao;
import com.next.aap.core.persistance.dao.EmailDao;
import com.next.aap.core.persistance.dao.FacebookAccountDao;
import com.next.aap.core.persistance.dao.FacebookAppDao;
import com.next.aap.core.persistance.dao.FacebookAppPermissionDao;
import com.next.aap.core.persistance.dao.FacebookGroupDao;
import com.next.aap.core.persistance.dao.FacebookGroupMembershipDao;
import com.next.aap.core.persistance.dao.FacebookPageDao;
import com.next.aap.core.persistance.dao.FacebookPostDao;
import com.next.aap.core.persistance.dao.ParliamentConstituencyDao;
import com.next.aap.core.persistance.dao.PcRoleDao;
import com.next.aap.core.persistance.dao.PermissionDao;
import com.next.aap.core.persistance.dao.PhoneDao;
import com.next.aap.core.persistance.dao.PlannedFacebookPostDao;
import com.next.aap.core.persistance.dao.PlannedTweetDao;
import com.next.aap.core.persistance.dao.RoleDao;
import com.next.aap.core.persistance.dao.StateDao;
import com.next.aap.core.persistance.dao.StateRoleDao;
import com.next.aap.core.persistance.dao.TweetDao;
import com.next.aap.core.persistance.dao.TwitterAccountDao;
import com.next.aap.core.persistance.dao.UserDao;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.FacebookPostDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PlannedFacebookPostDto;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PlannedTweetDto;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.StateDto;
import com.next.aap.web.dto.TweetDto;
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
	private FacebookPageDao facebookPageDao;
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
	@Autowired
	private CountryDao countryDao;
	@Autowired
	private FacebookPostDao facebookPostDao;
	@Autowired
	private PlannedTweetDao plannedTweetDao;
	@Autowired
	private TweetDao tweetDao;
	
	@Value("${voa_facebook_app_id}")
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

		dbFacebookAccount.setDateModified(new Date());
		dbFacebookAccount.setImageUrl(fbConnectionData.getImageUrl());
		dbFacebookAccount.setUser(user);
		dbFacebookAccount = facebookAccountDao.saveFacebookAccount(dbFacebookAccount);

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
		user.setNri(userDto.isNri());
		if(user.isNri()){
			if(userDto.getNriCountryId() != null && userDto.getNriCountryId() > 0){
				Country country = countryDao.getCountryById(userDto.getNriCountryId());
				user.setNriCountry(country);
			}
		}
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
			//user.setSuperAdmin(true);
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
	@Transactional
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
		plannedFacebookPost.setLocationType(plannedFacebookPostDto.getLocationType());
		plannedFacebookPost.setLocationId(plannedFacebookPostDto.getLocationId());
		
		plannedFacebookPost = plannedFacebookPostDao.savePlannedFacebookPost(plannedFacebookPost);
		
		return convertPlannedFacebookPost(plannedFacebookPost);
	}

	@Override
	@Transactional
	public List<PlannedFacebookPostDto> getPlannedFacebookPosts(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private PlannedFacebookPostDto convertPlannedFacebookPost(PlannedFacebookPost plannedFacebookPost){
		if(plannedFacebookPost == null){
			return null;
		}
		PlannedFacebookPostDto plannedFacebookPostDto = new PlannedFacebookPostDto();
		BeanUtils.copyProperties(plannedFacebookPost, plannedFacebookPostDto);
		return plannedFacebookPostDto;
	}
	private List<PlannedFacebookPostDto> convertPlannedFacebookPosts(List<PlannedFacebookPost> plannedFacebookPosts){
		List<PlannedFacebookPostDto> returnPlannedFacebookPostDtos = new ArrayList<>(plannedFacebookPosts.size());
		for(PlannedFacebookPost onePlannedFacebookPost:plannedFacebookPosts){
			returnPlannedFacebookPostDtos.add(convertPlannedFacebookPost(onePlannedFacebookPost));
		}
		return returnPlannedFacebookPostDtos;
	}
	private Set<AppPermission> convertPermissionToAppPermission(Set<Permission> permissions){
		Set<AppPermission> returnPermissions = new HashSet<>();
		if(permissions != null){
			for(Permission onePermission:permissions){
				returnPermissions.add(onePermission.getPermission());
			}
		}
		
		return returnPermissions;
	}

	@Override
	@Transactional
	public UserRolePermissionDto getUserRolePermissions(Long userId) {
		User user = userDao.getUserById(userId);
		UserRolePermissionDto userRolePermissionDto = new UserRolePermissionDto();
		userRolePermissionDto.setSuperUser(user.isSuperAdmin());
		
		Set<Role> allUserRolesAtWorldLevel = user.getAllRoles();
		logger.info("allUserRolesAtWorldLevel="+allUserRolesAtWorldLevel);
		if(allUserRolesAtWorldLevel != null && !allUserRolesAtWorldLevel.isEmpty()){
			for(Role oneRole:allUserRolesAtWorldLevel){
				if(!oneRole.getPermissions().isEmpty()){
					userRolePermissionDto.addAllPermissions(convertPermissionToAppPermission(oneRole.getPermissions()));	
				}
			}
		}
		
		Set<StateRole> stateRoles = user.getStateRoles();
		logger.info("stateRoles="+stateRoles);
		if(stateRoles != null && !stateRoles.isEmpty()){
			for(StateRole oneStateRole:stateRoles){
				System.out.println("oneStateRole="+oneStateRole);
				System.out.println("!oneStateRole.getRole().getRolePermissions().isEmpty()="+!oneStateRole.getRole().getPermissions().isEmpty());
				if(!oneStateRole.getRole().getPermissions().isEmpty()){
					userRolePermissionDto.addStatePermissions(convertState(oneStateRole.getState()), convertPermissionToAppPermission(oneStateRole.getRole().getPermissions()));
				}
				System.out.println("userRolePermissionDto.isStateAdmin()="+userRolePermissionDto.isStateAdmin());
			}
		}
		
		Set<DistrictRole> districtRoles = user.getDistrictRoles();
		logger.info("districtRoles="+districtRoles);
		if(districtRoles != null && !districtRoles.isEmpty()){
			for(DistrictRole oneDistrictRole:districtRoles){
				if(!oneDistrictRole.getRole().getPermissions().isEmpty()){
					userRolePermissionDto.addDistrictPermissions(convertDistrict(oneDistrictRole.getDistrict()), convertPermissionToAppPermission(oneDistrictRole.getRole().getPermissions()));
				}
			}
		}
		
		Set<AcRole> acRoles = user.getAcRoles();
		logger.info("acRoles="+acRoles);
		if(acRoles != null && !acRoles.isEmpty()){
			for(AcRole oneAcRole:acRoles){
				if(!oneAcRole.getRole().getPermissions().isEmpty()){
					userRolePermissionDto.addAcPermissions(convertAssemblyConstituency(oneAcRole.getAssemblyConstituency()), convertPermissionToAppPermission(oneAcRole.getRole().getPermissions()));
				}
			}
		}
		
		Set<PcRole> pcRoles = user.getPcRoles();
		logger.info("pcRoles="+pcRoles);
		if(pcRoles != null && !pcRoles.isEmpty()){
			for(PcRole onePcRole:pcRoles){
				if(!onePcRole.getRole().getPermissions().isEmpty()){
					userRolePermissionDto.addPcPermissions(convertParliamentConstituency(onePcRole.getParliamentConstituency()), convertPermissionToAppPermission(onePcRole.getRole().getPermissions()));
				}
			}
		}
		return userRolePermissionDto;
	}

	@Override
	@Transactional
	public void saveAllCountries() {
		saveCountry("Afghanistan","93");
		saveCountry("Albania","355");
		saveCountry("Algeria","213");
		saveCountry("American Samoa","1 684 ");
		saveCountry("Andorra","376");
		saveCountry("Angola","244");
		saveCountry("Anguilla","1 264 ");
		saveCountry("Antarctica","672");
		saveCountry("Antigua and Barbuda","1 268 ");
		saveCountry("Argentina","54");
		saveCountry("Armenia","374");
		saveCountry("Aruba","297");
		saveCountry("Australia","61");
		saveCountry("Austria","43");
		saveCountry("Azerbaijan","994");
		saveCountry("Bahamas","1 242 ");
		saveCountry("Bahrain","973");
		saveCountry("Bangladesh","880");
		saveCountry("Barbados","1 246 ");
		saveCountry("Belarus","375");
		saveCountry("Belgium","32");
		saveCountry("Belize","501");
		saveCountry("Benin","229");
		saveCountry("Bermuda","1 441 ");
		saveCountry("Bhutan","975");
		saveCountry("Bolivia","591");
		saveCountry("Bosnia and Herzegovina","387");
		saveCountry("Botswana","267");
		saveCountry("Brazil","55");
		saveCountry("British Indian Ocean Territory"," ");
		saveCountry("British Virgin Islands","1 284 ");
		saveCountry("Brunei","673");
		saveCountry("Bulgaria","359");
		saveCountry("Burkina Faso","226");
		saveCountry("Burma (Myanmar)","95");
		saveCountry("Burundi","257");
		saveCountry("Cambodia","855");
		saveCountry("Cameroon","237");
		saveCountry("Canada","1");
		saveCountry("Cape Verde","238");
		saveCountry("Cayman Islands","1 345 ");
		saveCountry("Central African Republic","236");
		saveCountry("Chad","235");
		saveCountry("Chile","56");
		saveCountry("China","86");
		saveCountry("Christmas Island","61");
		saveCountry("Cocos (Keeling) Islands","61");
		saveCountry("Colombia","57");
		saveCountry("Comoros","269");
		saveCountry("Cook Islands","682");
		saveCountry("Costa Rica","506");
		saveCountry("Croatia","385");
		saveCountry("Cuba","53");
		saveCountry("Cyprus","357");
		saveCountry("Czech Republic","420");
		saveCountry("Democratic Republic of the Congo","243");
		saveCountry("Denmark","45");
		saveCountry("Djibouti","253");
		saveCountry("Dominica","1 767 ");
		saveCountry("Dominican Republic","1 809 ");
		saveCountry("Ecuador","593");
		saveCountry("Egypt","20");
		saveCountry("El Salvador","503");
		saveCountry("Equatorial Guinea","240");
		saveCountry("Eritrea","291");
		saveCountry("Estonia","372");
		saveCountry("Ethiopia","251");
		saveCountry("Falkland Islands","500");
		saveCountry("Faroe Islands","298");
		saveCountry("Fiji","679");
		saveCountry("Finland","358");
		saveCountry("France","33");
		saveCountry("French Polynesia","689");
		saveCountry("Gabon","241");
		saveCountry("Gambia","220");
		saveCountry("Gaza Strip","970");
		saveCountry("Georgia","995");
		saveCountry("Germany","49");
		saveCountry("Ghana","233");
		saveCountry("Gibraltar","350");
		saveCountry("Greece","30");
		saveCountry("Greenland","299");
		saveCountry("Grenada","1 473 ");
		saveCountry("Guam","1 671 ");
		saveCountry("Guatemala","502");
		saveCountry("Guinea","224");
		saveCountry("Guinea-Bissau","245");
		saveCountry("Guyana","592");
		saveCountry("Haiti","509");
		saveCountry("Holy See (Vatican City)","39");
		saveCountry("Honduras","504");
		saveCountry("Hong Kong","852");
		saveCountry("Hungary","36");
		saveCountry("Iceland","354");
		saveCountry("India","91");
		saveCountry("Indonesia","62");
		saveCountry("Iran","98");
		saveCountry("Iraq","964");
		saveCountry("Ireland","353");
		saveCountry("Isle of Man","44");
		saveCountry("Israel","972");
		saveCountry("Italy","39");
		saveCountry("Ivory Coast","225");
		saveCountry("Jamaica","1 876 ");
		saveCountry("Japan","81");
		saveCountry("Jersey"," ");
		saveCountry("Jordan","962");
		saveCountry("Kazakhstan","7");
		saveCountry("Kenya","254");
		saveCountry("Kiribati","686");
		saveCountry("Kosovo","381");
		saveCountry("Kuwait","965");
		saveCountry("Kyrgyzstan","996");
		saveCountry("Laos","856");
		saveCountry("Latvia","371");
		saveCountry("Lebanon","961");
		saveCountry("Lesotho","266");
		saveCountry("Liberia","231");
		saveCountry("Libya","218");
		saveCountry("Liechtenstein","423");
		saveCountry("Lithuania","370");
		saveCountry("Luxembourg","352");
		saveCountry("Macau","853");
		saveCountry("Macedonia","389");
		saveCountry("Madagascar","261");
		saveCountry("Malawi","265");
		saveCountry("Malaysia","60");
		saveCountry("Maldives","960");
		saveCountry("Mali","223");
		saveCountry("Malta","356");
		saveCountry("Marshall Islands","692");
		saveCountry("Mauritania","222");
		saveCountry("Mauritius","230");
		saveCountry("Mayotte","262");
		saveCountry("Mexico","52");
		saveCountry("Micronesia","691");
		saveCountry("Moldova","373");
		saveCountry("Monaco","377");
		saveCountry("Mongolia","976");
		saveCountry("Montenegro","382");
		saveCountry("Montserrat","1 664 ");
		saveCountry("Morocco","212");
		saveCountry("Mozambique","258");
		saveCountry("Namibia","264");
		saveCountry("Nauru","674");
		saveCountry("Nepal","977");
		saveCountry("Netherlands","31");
		saveCountry("Netherlands Antilles","599");
		saveCountry("New Caledonia","687");
		saveCountry("New Zealand","64");
		saveCountry("Nicaragua","505");
		saveCountry("Niger","227");
		saveCountry("Nigeria","234");
		saveCountry("Niue","683");
		saveCountry("Norfolk Island","672");
		saveCountry("North Korea","850");
		saveCountry("Northern Mariana Islands","1 670 ");
		saveCountry("Norway","47");
		saveCountry("Oman","968");
		saveCountry("Pakistan","92");
		saveCountry("Palau","680");
		saveCountry("Panama","507");
		saveCountry("Papua New Guinea","675");
		saveCountry("Paraguay","595");
		saveCountry("Peru","51");
		saveCountry("Philippines","63");
		saveCountry("Pitcairn Islands","870");
		saveCountry("Poland","48");
		saveCountry("Portugal","351");
		saveCountry("Puerto Rico","1");
		saveCountry("Qatar","974");
		saveCountry("Republic of the Congo","242");
		saveCountry("Romania","40");
		saveCountry("Russia","7");
		saveCountry("Rwanda","250");
		saveCountry("Saint Barthelemy","590");
		saveCountry("Saint Helena","290");
		saveCountry("Saint Kitts and Nevis","1 869 ");
		saveCountry("Saint Lucia","1 758 ");
		saveCountry("Saint Martin","1 599 ");
		saveCountry("Saint Pierre and Miquelon","508");
		saveCountry("Saint Vincent and the Grenadines","1 784 ");
		saveCountry("Samoa","685");
		saveCountry("San Marino","378");
		saveCountry("Sao Tome and Principe","239");
		saveCountry("Saudi Arabia","966");
		saveCountry("Senegal","221");
		saveCountry("Serbia","381");
		saveCountry("Seychelles","248");
		saveCountry("Sierra Leone","232");
		saveCountry("Singapore","65");
		saveCountry("Slovakia","421");
		saveCountry("Slovenia","386");
		saveCountry("Solomon Islands","677");
		saveCountry("Somalia","252");
		saveCountry("South Africa","27");
		saveCountry("South Korea","82");
		saveCountry("Spain","34");
		saveCountry("Sri Lanka","94");
		saveCountry("Sudan","249");
		saveCountry("Suriname","597");
		saveCountry("Svalbard"," ");
		saveCountry("Swaziland","268");
		saveCountry("Sweden","46");
		saveCountry("Switzerland","41");
		saveCountry("Syria","963");
		saveCountry("Taiwan","886");
		saveCountry("Tajikistan","992");
		saveCountry("Tanzania","255");
		saveCountry("Thailand","66");
		saveCountry("Timor-Leste","670");
		saveCountry("Togo","228");
		saveCountry("Tokelau","690");
		saveCountry("Tonga","676");
		saveCountry("Trinidad and Tobago","1 868 ");
		saveCountry("Tunisia","216");
		saveCountry("Turkey","90");
		saveCountry("Turkmenistan","993");
		saveCountry("Turks and Caicos Islands","1 649 ");
		saveCountry("Tuvalu","688");
		saveCountry("Uganda","256");
		saveCountry("Ukraine","380");
		saveCountry("United Arab Emirates","971");
		saveCountry("United Kingdom","44");
		saveCountry("United States","1");
		saveCountry("Uruguay","598");
		saveCountry("US Virgin Islands","1 340 ");
		saveCountry("Uzbekistan","998");
		saveCountry("Vanuatu","678");
		saveCountry("Venezuela","58");
		saveCountry("Vietnam","84");
		saveCountry("Wallis and Futuna","681");
		saveCountry("West Bank","970");
		saveCountry("Western Sahara"," ");
		saveCountry("Yemen","967");
		saveCountry("Zambia","260");
		saveCountry("Zimbabwe","263");
		
	}
	private void saveCountry(String name, String isdCode){
		Country country = countryDao.getCountryByName(name);
		if(country == null){
			country = new Country();
			country.setName(name);
			country.setIsdCode(isdCode);
			country.setDateCreated(new Date());
			country.setDateModified(new Date());
			country = countryDao.saveCountry(country);
		}
		
	}

	@Override
	@Transactional
	public List<CountryDto> getAllCountries() {
		List<Country> countries = countryDao.getAllCountries();
		return convertCountries(countries);
	}
	private List<CountryDto> convertCountries(List<Country> countries){
		List<CountryDto> countryDtos = new ArrayList<>();
		for(Country oneCountry:countries){
			countryDtos.add(convertCountry(oneCountry));
		}
		return countryDtos;
	}
	private CountryDto convertCountry(Country country){
		if(country == null){
			return null;
		}
		CountryDto countryDto = new CountryDto();
		BeanUtils.copyProperties(country, countryDto);
		return countryDto;
	}

	@Override
	@Transactional
	public List<PlannedFacebookPostDto> getPlannedFacebookPostsForLocation(PostLocationType postLocationType, Long locationId, int pageNumber, int pageSize) {
		List<PlannedFacebookPost> plannedFacebookPosts = plannedFacebookPostDao.getPlannedFacebookPostByLocationTypeAndLocationId(postLocationType, locationId);
		return convertPlannedFacebookPosts(plannedFacebookPosts);
	}

	@Override
	@Transactional
	public PlannedFacebookPostDto getNextPlannedFacebookPostToPublish() {
		PlannedFacebookPost plannedFacebookPost = plannedFacebookPostDao.getNextPlannedFacebookPostToPublish();
		return convertPlannedFacebookPost(plannedFacebookPost);
	}

	@Override
	@Transactional
	public List<FacebookAccountDto> getAllFacebookAccountsForVoiceOfAap(PostLocationType locationType, Long locationId) {
		List<FacebookAccount> facebookAccounts = null;
		switch(locationType){
		case Global :
			facebookAccounts = facebookAccountDao.getAllFacebookAccountsForVoiceOfAapToPublishOnTimeLine();
			break;
		case STATE:
			facebookAccounts = facebookAccountDao.getStateFacebookAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		case DISTRICT:
			facebookAccounts = facebookAccountDao.getDistrictFacebookAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		case AC:
			facebookAccounts = facebookAccountDao.getAcFacebookAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		case PC:
			facebookAccounts = facebookAccountDao.getPcFacebookAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		}
		return convertFacebookAccounts(facebookAccounts);
	}

	@Override
	@Transactional
	public PlannedFacebookPostDto updatePlannedFacebookPostStatus(Long plannedFacebookPostId, PlannedPostStatus status, String errorMessage) {
		PlannedFacebookPost plannedFacebookPost = plannedFacebookPostDao.getPlannedFacebookPostById(plannedFacebookPostId);
		plannedFacebookPost.setStatus(status);
		plannedFacebookPost.setErrorMessage(errorMessage);
		plannedFacebookPost = plannedFacebookPostDao.savePlannedFacebookPost(plannedFacebookPost);
		return convertPlannedFacebookPost(plannedFacebookPost);
	}

	@Override
	@Transactional
	public FacebookPostDto getFacebookPostByPlannedPostIdAndFacebookAccountId(Long plannedFacebookPostId, Long facebookAccountId) {
		FacebookPost facebookPost = facebookPostDao.getFacebookPostByPlannedPostIdAndFacebookAccountId(plannedFacebookPostId, facebookAccountId);
		return convertFacebookPost(facebookPost);
	}
	
	private FacebookPostDto convertFacebookPost(FacebookPost facebookPost){
		if(facebookPost == null){
			return null;
		}
		FacebookPostDto facebookPostDto = new FacebookPostDto();
		BeanUtils.copyProperties(facebookPost, facebookPostDto);
		return facebookPostDto;
	}
	private List<FacebookPostDto> convertFacebookPosts(List<FacebookPost> facebookPosts){
		if(facebookPosts == null){
			return null;
		}
		List<FacebookPostDto> returnFacebookPosts = new ArrayList<>(facebookPosts.size());
		for(FacebookPost oneFacebookPost:facebookPosts){
			returnFacebookPosts.add(convertFacebookPost(oneFacebookPost));	
		}
		return returnFacebookPosts;
	}

	@Override
	@Transactional
	public FacebookPostDto saveFacebookPost(FacebookPostDto facebookPostDto) {
		FacebookPost facebookPost = new FacebookPost();
		FacebookAccount facebookAccount = facebookAccountDao.getFacebookAccountById(facebookPostDto.getFacebookAccountId());
		facebookPost.setFacebookAccount(facebookAccount);
		PlannedFacebookPost plannedFacebookPost = plannedFacebookPostDao.getPlannedFacebookPostById(facebookPostDto.getPlannedFacebookPostId());
		facebookPost.setPlannedFacebookPost(plannedFacebookPost);
		if(facebookPostDto.getFacebookGroupId() != null && facebookPostDto.getFacebookGroupId() > 0){
			FacebookGroup facebookGroup = facebookGroupDao.getFacebookGroupById(facebookPostDto.getFacebookGroupId());
			facebookPost.setFacebookGroup(facebookGroup);
		}
		if(facebookPostDto.getFacebookPageId() != null && facebookPostDto.getFacebookPageId() > 0){
			FacebookPage facebookPage = facebookPageDao.getFacebookPageById(facebookPostDto.getFacebookPageId());
			facebookPost.setFacebookPage(facebookPage);
		}
		facebookPost.setFacebookPostExternalId(facebookPostDto.getFacebookPostExternalId());
		facebookPost.setDateCreated(new Date());
		facebookPost.setDateModified(new Date());
		facebookPost = facebookPostDao.saveFacebookPost(facebookPost);
		return convertFacebookPost(facebookPost);
	}

	@Override
	@Transactional
	public List<FacebookPostDto> getUserFacebookPosts(Long facebookAccountId) {
		List<FacebookPost> facebookPosts = facebookPostDao.getFacebookPostByFacebookAccountId(facebookAccountId);
		return convertFacebookPosts(facebookPosts);
	}

	private PlannedTweetDto convertPlannedTweet(PlannedTweet plannedTweet){
		if(plannedTweet == null){
			return null;
		}
		PlannedTweetDto plannedTweetDto = new PlannedTweetDto();
		BeanUtils.copyProperties(plannedTweet, plannedTweetDto);
		return plannedTweetDto;
	}
	private List<PlannedTweetDto> convertPlannedTweets(List<PlannedTweet> plannedTweets){
		if(plannedTweets == null){
			return null;
		}
		List<PlannedTweetDto> plannedTweetDtos = new ArrayList<>(plannedTweets.size());
		for(PlannedTweet onePlannedTweet:plannedTweets){
			plannedTweetDtos.add(convertPlannedTweet(onePlannedTweet));
		}
		return plannedTweetDtos;
	}
	@Override
	@Transactional
	public PlannedTweetDto savePlannedTweet(PlannedTweetDto plannedTweetDto) {
		PlannedTweet plannedTweet = null;
		if(plannedTweetDto.getId() != null && plannedTweetDto.getId() > 0){
			plannedTweet = plannedTweetDao.getPlannedTweetById(plannedTweetDto.getId());
			if(plannedTweet == null){
				throw new RuntimeException("No such Tweet found[id="+plannedTweetDto.getId()+"]");
			}
		}else{
			plannedTweet = new PlannedTweet();
			plannedTweet.setDateCreated(new Date());
			plannedTweet.setStatus(PlannedPostStatus.PENDING);
		}
		plannedTweet.setMessage(plannedTweetDto.getMessage());
		plannedTweet.setPicture(plannedTweetDto.getPicture());
		plannedTweet.setPostingTime(plannedTweetDto.getPostingTime());
		plannedTweet.setLocationType(plannedTweetDto.getLocationType());
		plannedTweet.setLocationId(plannedTweetDto.getLocationId());
		plannedTweet.setTweetId(plannedTweetDto.getTweetId());
		plannedTweet.setTweetType(plannedTweetDto.getTweetType());
		
		plannedTweet = plannedTweetDao.savePlannedTweet(plannedTweet);
		
		return convertPlannedTweet(plannedTweet);
	}

	@Override
	@Transactional
	public PlannedTweetDto updatePlannedTweetStatus(Long plannedTweetId, PlannedPostStatus status, String errorMessage) {
		PlannedTweet plannedTweet = plannedTweetDao.getPlannedTweetById(plannedTweetId);
		plannedTweet.setStatus(status);
		plannedTweet.setErrorMessage(errorMessage);
		plannedTweet = plannedTweetDao.savePlannedTweet(plannedTweet);
		return convertPlannedTweet(plannedTweet);
	}

	@Override
	@Transactional
	public List<PlannedTweetDto> getPlannedTweets(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<PlannedTweetDto> getPlannedTweetsForLocation(PostLocationType postLocationType, Long locationId, int pageNumber, int pageSize) {
		List<PlannedTweet> plannedTweets = plannedTweetDao.getPlannedTweetByLocationTypeAndLocationId(postLocationType, locationId);
		return convertPlannedTweets(plannedTweets);
	}

	@Override
	@Transactional
	public PlannedTweetDto getNextPlannedTweetToPublish() {
		PlannedTweet plannedTweet = plannedTweetDao.getNextPlannedTweetToPublish();
		return convertPlannedTweet(plannedTweet);
	}

	@Override
	@Transactional
	public TweetDto getTweetByPlannedTweetIdAndTwitterAccountId(Long plannedTweetId, Long twitterAccountId) {
		Tweet tweet = tweetDao.getTweetByPlannedPostIdAndTwitterAccountId(plannedTweetId, twitterAccountId);
		return convertTweet(tweet);
	}

	@Override
	@Transactional
	public TweetDto saveTweetPost(TweetDto tweetDto) {
		Tweet tweet = new Tweet();
		TwitterAccount twitterAccount = twitterAccountDao.getTwitterAccountById(tweetDto.getTwitterAccountId());
		tweet.setTwitterAccount(twitterAccount);
		PlannedTweet plannedTweet = plannedTweetDao.getPlannedTweetById(tweetDto.getPlannedTweetId());
		tweet.setPlannedTweet(plannedTweet);
		tweet.setDateCreated(new Date());
		tweet.setDateModified(new Date());
		tweet.setTweetExternalId(tweetDto.getTweetExternalId());
		tweet = tweetDao.saveTweet(tweet);
		return convertTweet(tweet);
	}
	private TweetDto convertTweet(Tweet tweet){
		if(tweet == null){
			return null;
		}
		TweetDto tweetDto = new TweetDto();
		BeanUtils.copyProperties(tweet, tweetDto);
		return tweetDto;
	}
	private List<TweetDto> convertTweets(List<Tweet> tweets){
		if(tweets == null){
			return null;
		}
		List<TweetDto> returnTweets = new ArrayList<>(tweets.size());
		for(Tweet tweet:tweets){
			returnTweets.add(convertTweet(tweet));
		}
		return returnTweets;
	}

	@Override
	@Transactional
	public List<TweetDto> getUserTweets(Long userId) {
		List<Tweet> tweets = tweetDao.getTweetByUserId(userId);
		return convertTweets(tweets);
	}

	@Override
	@Transactional
	public List<TwitterAccountDto> getAllTwitterAccountsForVoiceOfAap(PostLocationType locationType, Long locationId) {
		List<TwitterAccount> twitterAccounts = null;
		switch(locationType){
		case Global :
			twitterAccounts = twitterAccountDao.getAllTwitterAccountsForVoiceOfAapToPublishOnTimeLine();
			break;
		case STATE:
			twitterAccounts = twitterAccountDao.getStateTwitterAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		case DISTRICT:
			twitterAccounts = twitterAccountDao.getDistrictTwitterAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		case AC:
			twitterAccounts = twitterAccountDao.getAcTwitterAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		case PC:
			twitterAccounts = twitterAccountDao.getPcTwitterAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		}
		return convertTwitterAccounts(twitterAccounts);
	}

}
