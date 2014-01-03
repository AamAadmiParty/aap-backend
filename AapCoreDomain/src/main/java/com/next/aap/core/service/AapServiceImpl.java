package com.next.aap.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.next.aap.core.persistance.AccountTransaction;
import com.next.aap.core.persistance.AssemblyConstituency;
import com.next.aap.core.persistance.Blog;
import com.next.aap.core.persistance.ContentTweet;
import com.next.aap.core.persistance.Country;
import com.next.aap.core.persistance.District;
import com.next.aap.core.persistance.DistrictRole;
import com.next.aap.core.persistance.Email;
import com.next.aap.core.persistance.Email.ConfirmationType;
import com.next.aap.core.persistance.Account;
import com.next.aap.core.persistance.FacebookAccount;
import com.next.aap.core.persistance.FacebookApp;
import com.next.aap.core.persistance.FacebookAppPermission;
import com.next.aap.core.persistance.FacebookGroup;
import com.next.aap.core.persistance.FacebookGroupMembership;
import com.next.aap.core.persistance.FacebookPage;
import com.next.aap.core.persistance.FacebookPost;
import com.next.aap.core.persistance.News;
import com.next.aap.core.persistance.ParliamentConstituency;
import com.next.aap.core.persistance.PcRole;
import com.next.aap.core.persistance.Permission;
import com.next.aap.core.persistance.Phone;
import com.next.aap.core.persistance.Phone.PhoneType;
import com.next.aap.core.persistance.PlannedFacebookPost;
import com.next.aap.core.persistance.PlannedTweet;
import com.next.aap.core.persistance.PollAnswer;
import com.next.aap.core.persistance.PollQuestion;
import com.next.aap.core.persistance.Role;
import com.next.aap.core.persistance.State;
import com.next.aap.core.persistance.StateRole;
import com.next.aap.core.persistance.Tweet;
import com.next.aap.core.persistance.TwitterAccount;
import com.next.aap.core.persistance.User;
import com.next.aap.core.persistance.dao.AcRoleDao;
import com.next.aap.core.persistance.dao.AccountDao;
import com.next.aap.core.persistance.dao.AccountTransactionDao;
import com.next.aap.core.persistance.dao.AssemblyConstituencyDao;
import com.next.aap.core.persistance.dao.BlogDao;
import com.next.aap.core.persistance.dao.ContentTweetDao;
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
import com.next.aap.core.persistance.dao.NewsDao;
import com.next.aap.core.persistance.dao.ParliamentConstituencyDao;
import com.next.aap.core.persistance.dao.PcRoleDao;
import com.next.aap.core.persistance.dao.PermissionDao;
import com.next.aap.core.persistance.dao.PhoneDao;
import com.next.aap.core.persistance.dao.PlannedFacebookPostDao;
import com.next.aap.core.persistance.dao.PlannedTweetDao;
import com.next.aap.core.persistance.dao.PollAnswerDao;
import com.next.aap.core.persistance.dao.PollQuestionDao;
import com.next.aap.core.persistance.dao.RoleDao;
import com.next.aap.core.persistance.dao.StateDao;
import com.next.aap.core.persistance.dao.StateRoleDao;
import com.next.aap.core.persistance.dao.TweetDao;
import com.next.aap.core.persistance.dao.TwitterAccountDao;
import com.next.aap.core.persistance.dao.UserDao;
import com.next.aap.core.util.DataUtil;
import com.next.aap.web.dto.AccountTransactionMode;
import com.next.aap.web.dto.AccountTransactionType;
import com.next.aap.web.dto.AccountType;
import com.next.aap.web.dto.AdminAccountDto;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.ContentStatus;
import com.next.aap.web.dto.ContentTweetDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.FacebookPostDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PlannedFacebookPostDto;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PlannedTweetDto;
import com.next.aap.web.dto.PollAnswerDto;
import com.next.aap.web.dto.PollQuestionDto;
import com.next.aap.web.dto.PostLocationType;
import com.next.aap.web.dto.RoleDto;
import com.next.aap.web.dto.SearchMemberResultDto;
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
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private ContentTweetDao contentTweetDao;
	@Autowired
	private BlogDao blogDao;
	@Autowired
	private PollQuestionDao pollQuestionDao;
	@Autowired
	private PollAnswerDao pollAnswerDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountTransactionDao accountTransactionDao;
	

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
			if (fbConnectionData.getExpireTime() == null) {
				Calendar today = Calendar.getInstance();
				today.add(Calendar.HOUR, 2);
				facebookAppPermission.setExpireTime(today.getTime());
			} else {
				facebookAppPermission.setExpireTime(new Date(fbConnectionData.getExpireTime()));
			}

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
		}
		// always use facebook Image Url
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

		return convertUser(user);
	}

	private UserDto convertUser(User user) {
		UserDto returnUser = new UserDto();
		BeanUtils.copyProperties(user, returnUser);
		List<Phone> userPhones = phoneDao.getPhonesOfUser(user.getId());
		Phone onePhone = null;
		if (userPhones != null && !userPhones.isEmpty()) {
			for (Phone phone : userPhones) {
				if (phone.getPhoneType().equals(PhoneType.MOBILE)) {
					onePhone = phone;
					break;
				}
			}
			if (onePhone == null) {
				onePhone = userPhones.get(0);
			}
			returnUser.setCountryCode(onePhone.getCountryCode());
			returnUser.setMobileNumber(onePhone.getPhoneNumber());
		}

		List<Email> emails = emailDao.getEmailsByUserId(user.getId());
		if (emails != null && !emails.isEmpty()) {
			returnUser.setEmail(emails.get(0).getEmail());
		}
		return returnUser;
	}

	private List<UserDto> convertUsers(List<User> users) {
		List<UserDto> returnUsers = new ArrayList<>();
		if (users == null) {
			return returnUsers;
		}
		for (User oneUser : users) {
			returnUsers.add(convertUser(oneUser));
		}
		return returnUsers;
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
			user.setProfilePic(twitterConnectionData.getImageUrl());
		}
		user = userDao.saveUser(user);

		dbTwitterAccount.setDateModified(new Date());
		dbTwitterAccount.setToken(twitterConnectionData.getAccessToken());
		dbTwitterAccount.setTokenSecret(twitterConnectionData.getSecret());
		dbTwitterAccount.setImageUrl(twitterConnectionData.getImageUrl());
		dbTwitterAccount.setUser(user);
		dbTwitterAccount = twitterAccountDao.saveTwitterAccount(dbTwitterAccount);

		return convertUser(user);
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
		BeanUtils.copyProperties(oneDistrict, oneDistrictDto);
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
		User user;
		System.out.println("userDto.getId = "+userDto.getId());
		if (userDto.getId() == null || userDto.getId() <= 0) {
			user = new User();
		} else {
			user = userDao.getUserById(userDto.getId());
			if (user == null) {
				logger.error("User DO NOT Exists [id=" + userDto.getId() + "]");
				return null;
			}
		}

		SearchMemberResultDto searchMemberResultDto = searchExistingUser(userDto);
		if(searchMemberResultDto != null && searchMemberResultDto.isUserAlreadyExists()){
			if(userDto.getId() == null || userDto.getId() <= 0){
				throw new RuntimeException(searchMemberResultDto.getUserAlreadyExistsMessage());
			}else{
				if(!searchMemberResultDto.getUsers().get(0).getId().equals(userDto.getId())){
					throw new RuntimeException(searchMemberResultDto.getUserAlreadyExistsMessage());
				}
			}
		}
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
		user.setMember(userDto.isMember());
		user.setFatherName(userDto.getFatherName());
		user.setMotherName(userDto.getMotherName());
		user.setAddress(userDto.getAddress());
		user.setVoterId(userDto.getVoterId());
		user.setPassportNumber(userDto.getPassportNumber());
		if (user.isNri()) {
			if (userDto.getNriCountryId() != null && userDto.getNriCountryId() > 0) {
				Country country = countryDao.getCountryById(userDto.getNriCountryId());
				user.setNriCountry(country);
			}
		}
		user = userDao.saveUser(user);

		if (!StringUtil.isEmpty(userDto.getMobileNumber())) {
			// save Mobile number
			List<Phone> userPhones = phoneDao.getPhonesOfUser(user.getId());
			Phone onePhone = null;
			if (userPhones == null || userPhones.isEmpty()) {
				onePhone = new Phone();
				onePhone.setCountryCode(userDto.getCountryCode());
				onePhone.setDateCreated(new Date());
				onePhone.setPhoneNumber(userDto.getMobileNumber());
				onePhone.setPhoneType(PhoneType.MOBILE);
				onePhone.setUser(user);
				onePhone.setDateModified(new Date());
				onePhone = phoneDao.savePhone(onePhone);
			} else {
				for (Phone phone : userPhones) {
					if (phone.getPhoneType().equals(PhoneType.MOBILE)) {
						onePhone = phone;
						break;
					}
				}
				if (onePhone == null) {
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

		if (!StringUtil.isEmpty(userDto.getEmail())) {
			// save Mobile number
			List<Email> userEmails = emailDao.getEmailsByUserId(user.getId());
			Email oneEmail = null;
			if (userEmails == null || userEmails.isEmpty()) {
				oneEmail = new Email();
				oneEmail.setDateCreated(new Date());
				oneEmail.setEmail(userDto.getEmail());
				oneEmail.setConfirmationType(ConfirmationType.ADMIN_ENTERED);
				oneEmail.setConfirmed(false);
				oneEmail.setUser(user);
				oneEmail.setDateModified(new Date());
				oneEmail = emailDao.saveEmail(oneEmail);
			} else {
				oneEmail = null;
				for (Email email : userEmails) {
					oneEmail = email;
					if (email.getConfirmationType().equals(ConfirmationType.ADMIN_ENTERED)) {
						oneEmail = email;
						break;
					}
				}
				if (oneEmail == null) {
					oneEmail = new Email();
					oneEmail.setDateCreated(new Date());
					oneEmail.setConfirmationType(ConfirmationType.ADMIN_ENTERED);
					oneEmail.setConfirmed(false);
					oneEmail.setUser(user);
					oneEmail.setDateModified(new Date());
				}
				oneEmail.setEmail(userDto.getEmail());
				oneEmail.setUser(user);
				oneEmail.setDateModified(new Date());
				oneEmail = emailDao.saveEmail(oneEmail);
			}
		}
		user = userDao.getUserById(user.getId());
		return convertUser(user);
	}

	@Override
	@Transactional
	public FacebookAppPermissionDto getFacebookPermission(long facebookAppId, long facebookAccountId) {
		FacebookAppPermission facebookAppPermission = facebookAppPermissionDao.getFacebookAppPermissionByAppIdAndFacebookAccountId(facebookAppId,
				facebookAccountId);
		return convertFacebookAppPermission(facebookAppPermission);
	}

	private FacebookAppPermissionDto convertFacebookAppPermission(FacebookAppPermission facebookAppPermission) {
		if (facebookAppPermission == null) {
			return null;
		}
		FacebookAppPermissionDto facebookAppPermissionDto = new FacebookAppPermissionDto();
		BeanUtils.copyProperties(facebookAppPermission, facebookAppPermissionDto);
		return facebookAppPermissionDto;
	}

	@Override
	@Transactional
	public FacebookAppPermissionDto getVoiceOfAapFacebookPermission(long facebookAccountId) {
		FacebookAppPermission facebookAppPermission = facebookAppPermissionDao.getFacebookAppPermissionByFacebookAppIdAndFacebookAccountId(voiceOfAapAppId,
				facebookAccountId);
		return convertFacebookAppPermission(facebookAppPermission);
	}

	@Override
	@Transactional
	public void saveVoiceOfAapSettings(Long facebookAccountId, boolean beVoiceOfAap, boolean postOnMyTimeLine, List<String> selectedGroups,
			List<String> selectedPages, boolean allowTweets) {
		FacebookAccount dbFacebookAccount = facebookAccountDao.getFacebookAccountById(facebookAccountId);
		if (dbFacebookAccount == null) {
			throw new RuntimeException("No such Facebook account found [id=" + facebookAccountId + "]");
		}
		dbFacebookAccount.setAllowDdu(beVoiceOfAap);
		dbFacebookAccount.setVoiceOfAap(beVoiceOfAap);
		dbFacebookAccount.setAllowTimeLine(postOnMyTimeLine);

		dbFacebookAccount = facebookAccountDao.saveFacebookAccount(dbFacebookAccount);

		User user = dbFacebookAccount.getUser();
		user.setAllowTweets(allowTweets);

		List<FacebookGroupMembership> facebookGroupMemberships = facebookGroupMembershipDao.getFacebookGroupMembershipByFacebookAccountId(facebookAccountId);
		if (facebookGroupMemberships == null) {
			return;
		}
		Set<String> selectedGroupIds = new HashSet<>(selectedGroups);

		for (FacebookGroupMembership oneFacebookGroupMembership : facebookGroupMemberships) {
			if (selectedGroupIds.contains(oneFacebookGroupMembership.getFacebookGroup().getFacebookGroupExternalId())) {
				oneFacebookGroupMembership.setAllowDduPost(beVoiceOfAap);
				oneFacebookGroupMembership.setAllowVoiceOfAapPost(beVoiceOfAap);
			} else {
				oneFacebookGroupMembership.setAllowDduPost(false);
				oneFacebookGroupMembership.setAllowVoiceOfAapPost(false);
			}
		}
	}

	@Override
	@Transactional
	public void saveFacebookAccountGroups(Long facebookAccountId, List<GroupMembership> userGroupMembership) {
		if (userGroupMembership == null || userGroupMembership.isEmpty()) {
			return;
		}
		FacebookAccount dbFacebookAccount = facebookAccountDao.getFacebookAccountById(facebookAccountId);
		if (dbFacebookAccount == null) {
			throw new RuntimeException("No such Facebook account found [id=" + facebookAccountId + "]");
		}
		FacebookGroupMembership oneFacebookGroupMembership;
		FacebookGroup oneFacebookGroup;
		for (GroupMembership oneGroupMembership : userGroupMembership) {
			oneFacebookGroup = facebookGroupDao.getFacebookGroupByFacebookGroupExternalId(oneGroupMembership.getId());
			if (oneFacebookGroup == null) {
				oneFacebookGroup = new FacebookGroup();
				oneFacebookGroup.setFacebookGroupExternalId(oneGroupMembership.getId());
			}
			oneFacebookGroup.setGroupName(oneGroupMembership.getName());
			oneFacebookGroup = facebookGroupDao.saveFacebookGroup(oneFacebookGroup);

			oneFacebookGroupMembership = facebookGroupMembershipDao.getFacebookGroupMembershipByFacebookUserIdAndGroupId(facebookAccountId,
					oneFacebookGroup.getId());
			if (oneFacebookGroupMembership == null) {
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
		if (groupMemberships != null) {
			for (FacebookGroupMembership oneFacebookGroupMembership : groupMemberships) {
				if (oneFacebookGroupMembership.isAllowVoiceOfAapPost()) {
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
		// Make sure all permission exists in Database
		Permission onePermission;
		for (AppPermission oneAppPermission : AppPermission.values()) {
			onePermission = permissionDao.getPermissionByName(oneAppPermission);
			if (onePermission == null) {
				onePermission = new Permission();
				onePermission.setPermission(oneAppPermission);
				onePermission = permissionDao.savePermission(onePermission);
			}
		}

		// assign super admin role to Ravi's account

		Email email = emailDao.getEmailByEmail("ping2ravi@gmail.com");
		User user = null;
		if (email == null) {
			FacebookAccount facebookAccount = facebookAccountDao.getFacebookAccountByFacebookUserId("691358626");
			if (facebookAccount == null) {
				TwitterAccount twitterAccount = twitterAccountDao.getTwitterAccountByTwitterUserId("287659262");
				if (twitterAccount != null) {
					user = twitterAccount.getUser();
				}
			} else {
				user = facebookAccount.getUser();
			}
		} else {
			user = email.getUser();
		}
		if (user == null) {
			logger.error("No predefined user found to make a user Super Admin");
		} else {
			// user.setSuperAdmin(true);
		}

		// Now create all custom Roles
/*
		createRoleWithPermissions("VoiceOfAapFacebookAdminRole", " User of this role will be able to make Facebook post using voice of AAP Application", true,
				true, false, false, AppPermission.ADMIN_VOICE_OF_AAP_FB);
		createRoleWithPermissions("VoiceOfAapTwitterAdminRole", "User of this role will be able to make Twitter post using voice of AAP Application", true,
				true, false, false, AppPermission.ADMIN_VOICE_OF_AAP_TWITTER);
		// News Related Roles
		createRoleWithPermissions("NewsAdminRole", "User of this role will be able to create/update/Approve/delete news for a location", true, true, true,
				true, AppPermission.CREATE_NEWS, AppPermission.UPDATE_NEWS, AppPermission.DELETE_NEWS, AppPermission.APPROVE_NEWS);

		createRoleWithPermissions("NewsReporterRole", "User of this role will be able to create/update news for a location", true, true, true, true,
				AppPermission.CREATE_NEWS, AppPermission.UPDATE_NEWS);

		createRoleWithPermissions("NewsEditorRole", "User of this role will be able to create/update/approve and publish news for a location", true, true,
				true, true, AppPermission.CREATE_NEWS, AppPermission.UPDATE_NEWS, AppPermission.APPROVE_NEWS);

		createRoleWithPermissions("NewsApproverRole", "User of this role will be able to approve/publish existing news for a location", true, true, true, true,
				AppPermission.APPROVE_NEWS);

		createRoleWithPermissions("GlobalMemberAdminRole",
				"User of this role will be able to add new member at any location and will be able to update any member", false, false, false, false,
				AppPermission.ADD_MEMBER, AppPermission.UPDATE_GLOBAL_MEMBER, AppPermission.VIEW_MEMBER);

		createRoleWithPermissions("MemberAdminRole",
				"User of this role will be able to add new member at any location and will be able to update member at his location only", true, true, true,
				true, AppPermission.ADD_MEMBER, AppPermission.UPDATE_MEMBER, AppPermission.VIEW_MEMBER);

		createRoleWithPermissions("AdminEditUserRoles", "User of this role will be able to add or remove user roles on a location", true, true, true, true,
				AppPermission.EDIT_USER_ROLES);

		createRoleWithPermissions("BlogAdminRole", "User of this role will be able to create/update/Approve/delete blog for a location", true, true, true,
				true, AppPermission.CREATE_BLOG, AppPermission.UPDATE_BLOG, AppPermission.DELETE_BLOG, AppPermission.APPROVE_BLOG);

		createRoleWithPermissions("BlogReporterRole", "User of this role will be able to create/update blog for a location", true, true, true, true,
				AppPermission.CREATE_BLOG, AppPermission.UPDATE_BLOG);

		createRoleWithPermissions("BlogEditorRole", "User of this role will be able to create/update/approve and publish blog for a location", true, true,
				true, true, AppPermission.CREATE_BLOG, AppPermission.UPDATE_BLOG, AppPermission.APPROVE_BLOG);

		createRoleWithPermissions("BlogApproverRole", "User of this role will be able to approve/publish existing blog for a location", true, true, true, true,
				AppPermission.APPROVE_BLOG);

		//Poll
		createRoleWithPermissions("PollAdminRole", "User of this role will be able to create/update/Approve/delete poll for a location", true, true, true,
				true, AppPermission.CREATE_POLL, AppPermission.UPDATE_POLL, AppPermission.DELETE_POLL, AppPermission.APPROVE_POLL);

		createRoleWithPermissions("PollReporterRole", "User of this role will be able to create/update poll for a location", true, true, true, true,
				AppPermission.CREATE_POLL, AppPermission.UPDATE_POLL);

		createRoleWithPermissions("PollEditorRole", "User of this role will be able to create/update/approve and publish poll for a location", true, true,
				true, true, AppPermission.CREATE_POLL, AppPermission.UPDATE_POLL, AppPermission.APPROVE_POLL);

		createRoleWithPermissions("PollApproverRole", "User of this role will be able to approve/publish existing poll for a location", true, true, true, true,
				AppPermission.APPROVE_POLL);
*/
		
		createRoleWithPermissions("Treasury", "User of this role will be able to do all Treasury operation of a location", true, true, true, true,
				AppPermission.TREASURY);
		
		

		logger.info("All Roles and permissions are created");
	}

	private void createRoleWithPermissions(String name, String description, boolean addStateRoles, boolean addDistrictRoles, boolean addAcRoles,
			boolean addPcRoles, AppPermission... appPermissions) {
		logger.info("Creating Role " + name);

		Role role = roleDao.getRoleByName(name);
		if (role == null) {
			role = new Role();
			role.setName(name);
			role.setDescription(description);
			role = roleDao.saveRole(role);
		}
		if (role.getPermissions() == null) {
			role.setPermissions(new HashSet<Permission>());
		}
		Permission onePermission;
		for (AppPermission oneAppPermission : appPermissions) {
			onePermission = permissionDao.getPermissionByName(oneAppPermission);
			role.getPermissions().add(onePermission);
		}
		if (addStateRoles) {
			List<State> allStates = stateDao.getAllStates();
			StateRole oneStateRole;
			for (State oneSate : allStates) {
				oneStateRole = stateRoleDao.getStateRoleByStateIdAndRoleId(oneSate.getId(), role.getId());
				if (oneStateRole == null) {
					oneStateRole = new StateRole();
					oneStateRole.setState(oneSate);
					oneStateRole.setRole(role);
					oneStateRole = stateRoleDao.saveStateRole(oneStateRole);
				}
			}
		}

		if (addDistrictRoles) {
			List<District> allDistricts = districtDao.getAllDistricts();
			DistrictRole oneDistrictRole;
			for (District oneDistrict : allDistricts) {
				oneDistrictRole = districtRoleDao.getDistrictRoleByDistrictIdAndRoleId(oneDistrict.getId(), role.getId());
				if (oneDistrictRole == null) {
					oneDistrictRole = new DistrictRole();
					oneDistrictRole.setDistrict(oneDistrict);
					oneDistrictRole.setRole(role);
					oneDistrictRole = districtRoleDao.saveDistrictRole(oneDistrictRole);
				}
			}
		}

		if (addAcRoles) {
			List<AssemblyConstituency> allAcs = assemblyConstituencyDao.getAllAssemblyConstituencys();
			AcRole oneAcRole;
			for (AssemblyConstituency oneAssemblyConstituency : allAcs) {
				oneAcRole = acRoleDao.getAcRoleByAcIdAndRoleId(oneAssemblyConstituency.getId(), role.getId());
				if (oneAcRole == null) {
					oneAcRole = new AcRole();
					oneAcRole.setAssemblyConstituency(oneAssemblyConstituency);
					oneAcRole.setRole(role);
					oneAcRole = acRoleDao.saveAcRole(oneAcRole);
				}
			}
		}

		if (addPcRoles) {
			List<ParliamentConstituency> allPcs = parliamentConstituencyDao.getAllParliamentConstituencys();
			PcRole onePcRole;
			for (ParliamentConstituency oneParliamentConstituency : allPcs) {
				onePcRole = pcRoleDao.getPcRoleByPcIdAndRoleId(oneParliamentConstituency.getId(), role.getId());
				if (onePcRole == null) {
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
		if (plannedFacebookPostDto.getId() != null && plannedFacebookPostDto.getId() > 0) {
			plannedFacebookPost = plannedFacebookPostDao.getPlannedFacebookPostById(plannedFacebookPostDto.getId());
			if (plannedFacebookPost == null) {
				throw new RuntimeException("No such Post found[id=" + plannedFacebookPostDto.getId() + "]");
			}
		} else {
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

	private PlannedFacebookPostDto convertPlannedFacebookPost(PlannedFacebookPost plannedFacebookPost) {
		if (plannedFacebookPost == null) {
			return null;
		}
		PlannedFacebookPostDto plannedFacebookPostDto = new PlannedFacebookPostDto();
		BeanUtils.copyProperties(plannedFacebookPost, plannedFacebookPostDto);
		return plannedFacebookPostDto;
	}

	private List<PlannedFacebookPostDto> convertPlannedFacebookPosts(List<PlannedFacebookPost> plannedFacebookPosts) {
		List<PlannedFacebookPostDto> returnPlannedFacebookPostDtos = new ArrayList<>(plannedFacebookPosts.size());
		for (PlannedFacebookPost onePlannedFacebookPost : plannedFacebookPosts) {
			returnPlannedFacebookPostDtos.add(convertPlannedFacebookPost(onePlannedFacebookPost));
		}
		return returnPlannedFacebookPostDtos;
	}

	private Set<AppPermission> convertPermissionToAppPermission(Set<Permission> permissions) {
		Set<AppPermission> returnPermissions = new HashSet<>();
		if (permissions != null) {
			for (Permission onePermission : permissions) {
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
		logger.info("allUserRolesAtWorldLevel=" + allUserRolesAtWorldLevel);
		if (allUserRolesAtWorldLevel != null && !allUserRolesAtWorldLevel.isEmpty()) {
			for (Role oneRole : allUserRolesAtWorldLevel) {
				if (!oneRole.getPermissions().isEmpty()) {
					userRolePermissionDto.addAllPermissions(convertPermissionToAppPermission(oneRole.getPermissions()));
				}
			}
		}

		Set<StateRole> stateRoles = user.getStateRoles();
		logger.info("stateRoles=" + stateRoles);
		if (stateRoles != null && !stateRoles.isEmpty()) {
			for (StateRole oneStateRole : stateRoles) {
				System.out.println("oneStateRole=" + oneStateRole);
				System.out.println("!oneStateRole.getRole().getRolePermissions().isEmpty()=" + !oneStateRole.getRole().getPermissions().isEmpty());
				if (!oneStateRole.getRole().getPermissions().isEmpty()) {
					userRolePermissionDto.addStatePermissions(convertState(oneStateRole.getState()), convertPermissionToAppPermission(oneStateRole.getRole()
							.getPermissions()));
				}
				System.out.println("userRolePermissionDto.isStateAdmin()=" + userRolePermissionDto.isStateAdmin());
			}
		}

		Set<DistrictRole> districtRoles = user.getDistrictRoles();
		logger.info("districtRoles=" + districtRoles);
		if (districtRoles != null && !districtRoles.isEmpty()) {
			for (DistrictRole oneDistrictRole : districtRoles) {
				if (!oneDistrictRole.getRole().getPermissions().isEmpty()) {
					userRolePermissionDto.addDistrictPermissions(convertDistrict(oneDistrictRole.getDistrict()),
							convertPermissionToAppPermission(oneDistrictRole.getRole().getPermissions()));
				}
			}
		}

		Set<AcRole> acRoles = user.getAcRoles();
		logger.info("acRoles=" + acRoles);
		if (acRoles != null && !acRoles.isEmpty()) {
			for (AcRole oneAcRole : acRoles) {
				if (!oneAcRole.getRole().getPermissions().isEmpty()) {
					userRolePermissionDto.addAcPermissions(convertAssemblyConstituency(oneAcRole.getAssemblyConstituency()),
							convertPermissionToAppPermission(oneAcRole.getRole().getPermissions()));
				}
			}
		}

		Set<PcRole> pcRoles = user.getPcRoles();
		logger.info("pcRoles=" + pcRoles);
		if (pcRoles != null && !pcRoles.isEmpty()) {
			for (PcRole onePcRole : pcRoles) {
				if (!onePcRole.getRole().getPermissions().isEmpty()) {
					userRolePermissionDto.addPcPermissions(convertParliamentConstituency(onePcRole.getParliamentConstituency()),
							convertPermissionToAppPermission(onePcRole.getRole().getPermissions()));
				}
			}
		}
		return userRolePermissionDto;
	}

	@Override
	@Transactional
	public void saveAllCountries() {
		saveCountry("Afghanistan", "93");
		saveCountry("Albania", "355");
		saveCountry("Algeria", "213");
		saveCountry("American Samoa", "1 684 ");
		saveCountry("Andorra", "376");
		saveCountry("Angola", "244");
		saveCountry("Anguilla", "1 264 ");
		saveCountry("Antarctica", "672");
		saveCountry("Antigua and Barbuda", "1 268 ");
		saveCountry("Argentina", "54");
		saveCountry("Armenia", "374");
		saveCountry("Aruba", "297");
		saveCountry("Australia", "61");
		saveCountry("Austria", "43");
		saveCountry("Azerbaijan", "994");
		saveCountry("Bahamas", "1 242 ");
		saveCountry("Bahrain", "973");
		saveCountry("Bangladesh", "880");
		saveCountry("Barbados", "1 246 ");
		saveCountry("Belarus", "375");
		saveCountry("Belgium", "32");
		saveCountry("Belize", "501");
		saveCountry("Benin", "229");
		saveCountry("Bermuda", "1 441 ");
		saveCountry("Bhutan", "975");
		saveCountry("Bolivia", "591");
		saveCountry("Bosnia and Herzegovina", "387");
		saveCountry("Botswana", "267");
		saveCountry("Brazil", "55");
		saveCountry("British Indian Ocean Territory", " ");
		saveCountry("British Virgin Islands", "1 284 ");
		saveCountry("Brunei", "673");
		saveCountry("Bulgaria", "359");
		saveCountry("Burkina Faso", "226");
		saveCountry("Burma (Myanmar)", "95");
		saveCountry("Burundi", "257");
		saveCountry("Cambodia", "855");
		saveCountry("Cameroon", "237");
		saveCountry("Canada", "1");
		saveCountry("Cape Verde", "238");
		saveCountry("Cayman Islands", "1 345 ");
		saveCountry("Central African Republic", "236");
		saveCountry("Chad", "235");
		saveCountry("Chile", "56");
		saveCountry("China", "86");
		saveCountry("Christmas Island", "61");
		saveCountry("Cocos (Keeling) Islands", "61");
		saveCountry("Colombia", "57");
		saveCountry("Comoros", "269");
		saveCountry("Cook Islands", "682");
		saveCountry("Costa Rica", "506");
		saveCountry("Croatia", "385");
		saveCountry("Cuba", "53");
		saveCountry("Cyprus", "357");
		saveCountry("Czech Republic", "420");
		saveCountry("Democratic Republic of the Congo", "243");
		saveCountry("Denmark", "45");
		saveCountry("Djibouti", "253");
		saveCountry("Dominica", "1 767 ");
		saveCountry("Dominican Republic", "1 809 ");
		saveCountry("Ecuador", "593");
		saveCountry("Egypt", "20");
		saveCountry("El Salvador", "503");
		saveCountry("Equatorial Guinea", "240");
		saveCountry("Eritrea", "291");
		saveCountry("Estonia", "372");
		saveCountry("Ethiopia", "251");
		saveCountry("Falkland Islands", "500");
		saveCountry("Faroe Islands", "298");
		saveCountry("Fiji", "679");
		saveCountry("Finland", "358");
		saveCountry("France", "33");
		saveCountry("French Polynesia", "689");
		saveCountry("Gabon", "241");
		saveCountry("Gambia", "220");
		saveCountry("Gaza Strip", "970");
		saveCountry("Georgia", "995");
		saveCountry("Germany", "49");
		saveCountry("Ghana", "233");
		saveCountry("Gibraltar", "350");
		saveCountry("Greece", "30");
		saveCountry("Greenland", "299");
		saveCountry("Grenada", "1 473 ");
		saveCountry("Guam", "1 671 ");
		saveCountry("Guatemala", "502");
		saveCountry("Guinea", "224");
		saveCountry("Guinea-Bissau", "245");
		saveCountry("Guyana", "592");
		saveCountry("Haiti", "509");
		saveCountry("Holy See (Vatican City)", "39");
		saveCountry("Honduras", "504");
		saveCountry("Hong Kong", "852");
		saveCountry("Hungary", "36");
		saveCountry("Iceland", "354");
		saveCountry("India", "91");
		saveCountry("Indonesia", "62");
		saveCountry("Iran", "98");
		saveCountry("Iraq", "964");
		saveCountry("Ireland", "353");
		saveCountry("Isle of Man", "44");
		saveCountry("Israel", "972");
		saveCountry("Italy", "39");
		saveCountry("Ivory Coast", "225");
		saveCountry("Jamaica", "1 876 ");
		saveCountry("Japan", "81");
		saveCountry("Jersey", " ");
		saveCountry("Jordan", "962");
		saveCountry("Kazakhstan", "7");
		saveCountry("Kenya", "254");
		saveCountry("Kiribati", "686");
		saveCountry("Kosovo", "381");
		saveCountry("Kuwait", "965");
		saveCountry("Kyrgyzstan", "996");
		saveCountry("Laos", "856");
		saveCountry("Latvia", "371");
		saveCountry("Lebanon", "961");
		saveCountry("Lesotho", "266");
		saveCountry("Liberia", "231");
		saveCountry("Libya", "218");
		saveCountry("Liechtenstein", "423");
		saveCountry("Lithuania", "370");
		saveCountry("Luxembourg", "352");
		saveCountry("Macau", "853");
		saveCountry("Macedonia", "389");
		saveCountry("Madagascar", "261");
		saveCountry("Malawi", "265");
		saveCountry("Malaysia", "60");
		saveCountry("Maldives", "960");
		saveCountry("Mali", "223");
		saveCountry("Malta", "356");
		saveCountry("Marshall Islands", "692");
		saveCountry("Mauritania", "222");
		saveCountry("Mauritius", "230");
		saveCountry("Mayotte", "262");
		saveCountry("Mexico", "52");
		saveCountry("Micronesia", "691");
		saveCountry("Moldova", "373");
		saveCountry("Monaco", "377");
		saveCountry("Mongolia", "976");
		saveCountry("Montenegro", "382");
		saveCountry("Montserrat", "1 664 ");
		saveCountry("Morocco", "212");
		saveCountry("Mozambique", "258");
		saveCountry("Namibia", "264");
		saveCountry("Nauru", "674");
		saveCountry("Nepal", "977");
		saveCountry("Netherlands", "31");
		saveCountry("Netherlands Antilles", "599");
		saveCountry("New Caledonia", "687");
		saveCountry("New Zealand", "64");
		saveCountry("Nicaragua", "505");
		saveCountry("Niger", "227");
		saveCountry("Nigeria", "234");
		saveCountry("Niue", "683");
		saveCountry("Norfolk Island", "672");
		saveCountry("North Korea", "850");
		saveCountry("Northern Mariana Islands", "1 670 ");
		saveCountry("Norway", "47");
		saveCountry("Oman", "968");
		saveCountry("Pakistan", "92");
		saveCountry("Palau", "680");
		saveCountry("Panama", "507");
		saveCountry("Papua New Guinea", "675");
		saveCountry("Paraguay", "595");
		saveCountry("Peru", "51");
		saveCountry("Philippines", "63");
		saveCountry("Pitcairn Islands", "870");
		saveCountry("Poland", "48");
		saveCountry("Portugal", "351");
		saveCountry("Puerto Rico", "1");
		saveCountry("Qatar", "974");
		saveCountry("Republic of the Congo", "242");
		saveCountry("Romania", "40");
		saveCountry("Russia", "7");
		saveCountry("Rwanda", "250");
		saveCountry("Saint Barthelemy", "590");
		saveCountry("Saint Helena", "290");
		saveCountry("Saint Kitts and Nevis", "1 869 ");
		saveCountry("Saint Lucia", "1 758 ");
		saveCountry("Saint Martin", "1 599 ");
		saveCountry("Saint Pierre and Miquelon", "508");
		saveCountry("Saint Vincent and the Grenadines", "1 784 ");
		saveCountry("Samoa", "685");
		saveCountry("San Marino", "378");
		saveCountry("Sao Tome and Principe", "239");
		saveCountry("Saudi Arabia", "966");
		saveCountry("Senegal", "221");
		saveCountry("Serbia", "381");
		saveCountry("Seychelles", "248");
		saveCountry("Sierra Leone", "232");
		saveCountry("Singapore", "65");
		saveCountry("Slovakia", "421");
		saveCountry("Slovenia", "386");
		saveCountry("Solomon Islands", "677");
		saveCountry("Somalia", "252");
		saveCountry("South Africa", "27");
		saveCountry("South Korea", "82");
		saveCountry("Spain", "34");
		saveCountry("Sri Lanka", "94");
		saveCountry("Sudan", "249");
		saveCountry("Suriname", "597");
		saveCountry("Svalbard", " ");
		saveCountry("Swaziland", "268");
		saveCountry("Sweden", "46");
		saveCountry("Switzerland", "41");
		saveCountry("Syria", "963");
		saveCountry("Taiwan", "886");
		saveCountry("Tajikistan", "992");
		saveCountry("Tanzania", "255");
		saveCountry("Thailand", "66");
		saveCountry("Timor-Leste", "670");
		saveCountry("Togo", "228");
		saveCountry("Tokelau", "690");
		saveCountry("Tonga", "676");
		saveCountry("Trinidad and Tobago", "1 868 ");
		saveCountry("Tunisia", "216");
		saveCountry("Turkey", "90");
		saveCountry("Turkmenistan", "993");
		saveCountry("Turks and Caicos Islands", "1 649 ");
		saveCountry("Tuvalu", "688");
		saveCountry("Uganda", "256");
		saveCountry("Ukraine", "380");
		saveCountry("United Arab Emirates", "971");
		saveCountry("United Kingdom", "44");
		saveCountry("United States", "1");
		saveCountry("Uruguay", "598");
		saveCountry("US Virgin Islands", "1 340 ");
		saveCountry("Uzbekistan", "998");
		saveCountry("Vanuatu", "678");
		saveCountry("Venezuela", "58");
		saveCountry("Vietnam", "84");
		saveCountry("Wallis and Futuna", "681");
		saveCountry("West Bank", "970");
		saveCountry("Western Sahara", " ");
		saveCountry("Yemen", "967");
		saveCountry("Zambia", "260");
		saveCountry("Zimbabwe", "263");

	}

	private void saveCountry(String name, String isdCode) {
		Country country = countryDao.getCountryByName(name);
		if (country == null) {
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

	private List<CountryDto> convertCountries(List<Country> countries) {
		List<CountryDto> countryDtos = new ArrayList<>();
		for (Country oneCountry : countries) {
			countryDtos.add(convertCountry(oneCountry));
		}
		return countryDtos;
	}

	private CountryDto convertCountry(Country country) {
		if (country == null) {
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
		switch (locationType) {
		case Global:
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

	private FacebookPostDto convertFacebookPost(FacebookPost facebookPost) {
		if (facebookPost == null) {
			return null;
		}
		FacebookPostDto facebookPostDto = new FacebookPostDto();
		BeanUtils.copyProperties(facebookPost, facebookPostDto);
		return facebookPostDto;
	}

	private List<FacebookPostDto> convertFacebookPosts(List<FacebookPost> facebookPosts) {
		if (facebookPosts == null) {
			return null;
		}
		List<FacebookPostDto> returnFacebookPosts = new ArrayList<>(facebookPosts.size());
		for (FacebookPost oneFacebookPost : facebookPosts) {
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
		if (facebookPostDto.getFacebookGroupId() != null && facebookPostDto.getFacebookGroupId() > 0) {
			FacebookGroup facebookGroup = facebookGroupDao.getFacebookGroupById(facebookPostDto.getFacebookGroupId());
			facebookPost.setFacebookGroup(facebookGroup);
		}
		if (facebookPostDto.getFacebookPageId() != null && facebookPostDto.getFacebookPageId() > 0) {
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

	private PlannedTweetDto convertPlannedTweet(PlannedTweet plannedTweet) {
		if (plannedTweet == null) {
			return null;
		}
		PlannedTweetDto plannedTweetDto = new PlannedTweetDto();
		BeanUtils.copyProperties(plannedTweet, plannedTweetDto);
		return plannedTweetDto;
	}

	private List<PlannedTweetDto> convertPlannedTweets(List<PlannedTweet> plannedTweets) {
		if (plannedTweets == null) {
			return null;
		}
		List<PlannedTweetDto> plannedTweetDtos = new ArrayList<>(plannedTweets.size());
		for (PlannedTweet onePlannedTweet : plannedTweets) {
			plannedTweetDtos.add(convertPlannedTweet(onePlannedTweet));
		}
		return plannedTweetDtos;
	}

	@Override
	@Transactional
	public PlannedTweetDto savePlannedTweet(PlannedTweetDto plannedTweetDto) {
		PlannedTweet plannedTweet = null;
		if (plannedTweetDto.getId() != null && plannedTweetDto.getId() > 0) {
			plannedTweet = plannedTweetDao.getPlannedTweetById(plannedTweetDto.getId());
			if (plannedTweet == null) {
				throw new RuntimeException("No such Tweet found[id=" + plannedTweetDto.getId() + "]");
			}
		} else {
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

	private TweetDto convertTweet(Tweet tweet) {
		if (tweet == null) {
			return null;
		}
		TweetDto tweetDto = new TweetDto();
		BeanUtils.copyProperties(tweet, tweetDto);
		return tweetDto;
	}

	private List<TweetDto> convertTweets(List<Tweet> tweets) {
		if (tweets == null) {
			return null;
		}
		List<TweetDto> returnTweets = new ArrayList<>(tweets.size());
		for (Tweet tweet : tweets) {
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
		switch (locationType) {
		case Global:
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

	@Override
	@Transactional
	public NewsDto saveNews(NewsDto newsDto, List<ContentTweetDto> contentTweetDtos, PostLocationType locationType, Long locationId) {
		News news = null;
		if (newsDto.getId() != null && newsDto.getId() > 0) {
			news = newsDao.getNewsById(newsDto.getId());
		}
		if (news == null) {
			news = new News();
			news.setDateCreated(new Date());
			news.setContentStatus(ContentStatus.Pending);
		}
		news.setAuthor(newsDto.getAuthor());
		news.setContent(newsDto.getContent());
		news.setDateModified(new Date());
		news.setImageUrl(newsDto.getImageUrl());
		news.setSource(newsDto.getSource());
		news.setTitle(newsDto.getTitle());

		switch (locationType) {
		case Global:
			news.setGlobal(true);
			break;
		case STATE:
			if (news.getStates() == null) {
				news.setStates(new ArrayList<State>());
			}
			State state = stateDao.getStateById(locationId);
			news.getStates().add(state);
			break;
		case DISTRICT:
			if (news.getDistricts() == null) {
				news.setDistricts(new ArrayList<District>());
			}
			District district = districtDao.getDistrictById(locationId);
			news.getDistricts().add(district);
			break;
		case AC:
			if (news.getAssemblyConstituencies() == null) {
				news.setAssemblyConstituencies(new ArrayList<AssemblyConstituency>());
			}
			AssemblyConstituency assemblyConstituency = assemblyConstituencyDao.getAssemblyConstituencyById(locationId);
			news.getAssemblyConstituencies().add(assemblyConstituency);
			break;
		case PC:
			if (news.getParliamentConstituencies() == null) {
				news.setParliamentConstituencies(new ArrayList<ParliamentConstituency>());
			}
			ParliamentConstituency parliamentConstituency = parliamentConstituencyDao.getParliamentConstituencyById(locationId);
			news.getParliamentConstituencies().add(parliamentConstituency);
			break;
		}

		news = newsDao.saveNews(news);

		// add all tweets
		if (contentTweetDtos != null && contentTweetDtos.size() > 0) {
			if (news.getTweets() == null) {
				news.setTweets(new ArrayList<ContentTweet>());
			}
			ContentTweet oneContentTweet;
			for (ContentTweetDto oneContentTweetDto : contentTweetDtos) {
				if (oneContentTweetDto.getId() == null || oneContentTweetDto.getId() <= 0) {
					oneContentTweet = new ContentTweet();
				} else {
					oneContentTweet = contentTweetDao.getContentTweetById(oneContentTweetDto.getId());
				}
				oneContentTweet.setImageUrl(oneContentTweetDto.getImageUrl());
				oneContentTweet.setTweetContent(oneContentTweetDto.getTweetContent());

				oneContentTweet = contentTweetDao.saveContentTweet(oneContentTweet);
				news.getTweets().add(oneContentTweet);
			}
		}

		return convertNews(news);
	}

	private NewsDto convertNews(News news) {
		if (news == null) {
			return null;
		}
		NewsDto newsDto = new NewsDto();
		BeanUtils.copyProperties(news, newsDto);
		return newsDto;
	}

	private List<NewsDto> convertNews(List<News> news) {
		if (news == null) {
			return null;
		}
		List<NewsDto> returnNews = new ArrayList<>(news.size());
		for (News oneNews : news) {
			returnNews.add(convertNews(oneNews));
		}
		return returnNews;
	}

	@Override
	@Transactional
	public List<NewsDto> getNews(PostLocationType locationType, Long locationId) {
		List<News> news = null;
		switch (locationType) {
		case Global:
			news = newsDao.getGlobalNews();
			break;
		case STATE:
			news = newsDao.getStateNews(locationId);
			break;
		case DISTRICT:
			news = newsDao.getDistrictNews(locationId);
			break;
		case AC:
			news = newsDao.getAcNews(locationId);
			break;
		case PC:
			news = newsDao.getPcNews(locationId);
			break;
		}
		return convertNews(news);
	}

	@Override
	@Transactional
	public NewsDto publishNews(Long newsId) {
		News news = newsDao.getNewsById(newsId);
		news.setContentStatus(ContentStatus.Published);
		news = newsDao.saveNews(news);
		return convertNews(news);
	}

	private ContentTweetDto convertContentTweet(ContentTweet contentTweet) {
		if (contentTweet == null) {
			return null;
		}
		ContentTweetDto contentTweetDto = new ContentTweetDto();
		BeanUtils.copyProperties(contentTweet, contentTweetDto);
		return contentTweetDto;
	}

	private List<ContentTweetDto> convertContentTweets(Collection<ContentTweet> contentTweets) {
		if (contentTweets == null) {
			return null;
		}
		List<ContentTweetDto> contentTweetDtos = new ArrayList<>(contentTweets.size());
		for (ContentTweet oneContentTweet : contentTweets) {
			contentTweetDtos.add(convertContentTweet(oneContentTweet));
		}
		return contentTweetDtos;
	}

	@Override
	@Transactional
	public List<ContentTweetDto> getNewsContentTweets(Long newsId) {
		News news = newsDao.getNewsById(newsId);
		return convertContentTweets(news.getTweets());
	}
	
	@Override
	@Transactional
	public List<ContentTweetDto> getBlogContentTweets(Long newsId) {
		Blog blog = blogDao.getBlogById(newsId);
		return convertContentTweets(blog.getTweets());
	}

	@Override
	@Transactional
	public UserDto registerMember(UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

	private SearchMemberResultDto searchExistingUser(UserDto searchUserDto) {
		SearchMemberResultDto searchMemberResult = new SearchMemberResultDto();
		if (!StringUtil.isEmpty(searchUserDto.getMembershipNumber())) {
			User user = userDao.getUserByMembershipNumber(searchUserDto.getMembershipNumber());
			if (user != null) {
				searchMemberResult.getUsers().add(convertUser(user));
				searchMemberResult.setUserAlreadyExists(true);
				searchMemberResult.setUserAlreadyExistsMessage("Member with membership number " + searchUserDto.getMembershipNumber() + " already exists");
				return searchMemberResult;
			}
		}
		if (!StringUtil.isEmpty(searchUserDto.getEmail())) {
			Email email = emailDao.getEmailByEmail(searchUserDto.getEmail());
			if (email != null) {
				User user = email.getUser();
				if (user != null) {
					searchMemberResult.getUsers().add(convertUser(user));
					searchMemberResult.setUserAlreadyExists(true);
					searchMemberResult.setUserAlreadyExistsMessage("Member with email " + searchUserDto.getEmail() + " already exists");
					return searchMemberResult;
				}
			}
		}
		if (!StringUtil.isEmpty(searchUserDto.getMobileNumber())) {
			Phone phone = phoneDao.getPhoneByPhone(searchUserDto.getMobileNumber(), searchUserDto.getCountryCode());
			if (phone != null) {
				User user = phone.getUser();
				if (user != null) {
					searchMemberResult.getUsers().add(convertUser(user));
					searchMemberResult.setUserAlreadyExists(true);
					searchMemberResult.setUserAlreadyExistsMessage("Member with phone " + searchUserDto.getMobileNumber() + " already exists");
					return searchMemberResult;
				}
			}
		}

		if (!StringUtil.isEmpty(searchUserDto.getPassportNumber())) {
			User user = userDao.getUserByPassportNumber(searchUserDto.getPassportNumber());
			if (user != null) {
				searchMemberResult.getUsers().add(convertUser(user));
				searchMemberResult.setUserAlreadyExists(true);
				searchMemberResult.setUserAlreadyExistsMessage("Member with passport number " + searchUserDto.getPassportNumber() + " already exists");
				return searchMemberResult;
			}
		}
		return null;
	}
	@Override
	@Transactional
	public SearchMemberResultDto searchMembers(UserDto searchUserDto) {
		SearchMemberResultDto searchMemberResult = searchExistingUser(searchUserDto);
		if(searchMemberResult == null){
			searchMemberResult = new SearchMemberResultDto();
		}else{
			return searchMemberResult;
		}

		if (!StringUtil.isEmpty(searchUserDto.getName())) {
			List<User> users = userDao.searchUserOfAssemblyConstituency(searchUserDto.getName(), searchUserDto.getAssemblyConstituencyLivingId(),
					searchUserDto.getAssemblyConstituencyVotingId());
			if (users != null) {
				searchMemberResult.getUsers().addAll(convertUsers(users));
				searchMemberResult.setUserAlreadyExists(false);
				searchMemberResult.setUserAlreadyExistsMessage("Similar member found inthis area, check manually if member already exists");
				return searchMemberResult;
			}
		}
		searchMemberResult.setUserAlreadyExists(false);
		searchMemberResult.setUserAlreadyExistsMessage("No Member found");
		return searchMemberResult;
	}

	@Override
	@Transactional
	public AssemblyConstituencyDto saveAssemblyConstituency(AssemblyConstituencyDto assemblyConstituencyDto) {
		AssemblyConstituency dbAssemblyConstituency;
		if (assemblyConstituencyDto.getId() == null || assemblyConstituencyDto.getId() <= 0) {
			dbAssemblyConstituency = assemblyConstituencyDao.getAssemblyConstituencyNameAndDistrictId(assemblyConstituencyDto.getDistrictId(),
					assemblyConstituencyDto.getName());
			if (dbAssemblyConstituency == null) {
				dbAssemblyConstituency = new AssemblyConstituency();
				dbAssemblyConstituency.setDateCreated(new Date());
			}
		} else {
			dbAssemblyConstituency = assemblyConstituencyDao.getAssemblyConstituencyById(assemblyConstituencyDto.getId());
			if (dbAssemblyConstituency == null) {
				throw new RuntimeException("No such Assembly Constituency exist[id=" + assemblyConstituencyDto.getId() + "]");
			}
		}
		dbAssemblyConstituency.setDateModified(new Date());
		dbAssemblyConstituency.setName(assemblyConstituencyDto.getName());
		District district = districtDao.getDistrictById(assemblyConstituencyDto.getDistrictId());
		district.setAcDataAvailable(true);
		dbAssemblyConstituency.setDistrict(district);

		dbAssemblyConstituency = assemblyConstituencyDao.saveAssemblyConstituency(dbAssemblyConstituency);

		assemblyConstituencyDto.setId(dbAssemblyConstituency.getId());
		return assemblyConstituencyDto;
	}

	@Override
	@Transactional
	public DistrictDto saveDistrict(DistrictDto districtWeb) {
		District dbDistrict;
		if (districtWeb.getId() == null || districtWeb.getId() <= 0) {
			dbDistrict = districtDao.getDistrictByNameAndStateId(districtWeb.getStateId(), districtWeb.getName());
			if (dbDistrict == null) {
				dbDistrict = new District();
				dbDistrict.setDateCreated(new Date());
			}
		} else {
			dbDistrict = districtDao.getDistrictById(districtWeb.getId());
			if (dbDistrict == null) {
				throw new RuntimeException("No such District exist[id=" + districtWeb.getId() + "]");
			}
		}

		if (dbDistrict.getDateCreated() == null) {
			dbDistrict.setDateCreated(new Date());
		}
		dbDistrict.setName(districtWeb.getName());
		dbDistrict.setDateModified(new Date());

		State state = stateDao.getStateById(districtWeb.getStateId());
		dbDistrict.setState(state);

		dbDistrict = districtDao.saveDistrict(dbDistrict);

		districtWeb.setId(dbDistrict.getId());
		return districtWeb;
	}

	@Override
	@Transactional
	public DistrictDto getDistrictByNameAndStateId(String name, Long stateId) {
		District district = districtDao.getDistrictByNameAndStateId(stateId, name);
		return convertDistrict(district);
	}

	@Override
	@Transactional
	public DistrictDto getDistrictById(Long districtId) {
		District district = districtDao.getDistrictById(districtId);
		return convertDistrict(district);
	}

	@Override
	@Transactional
	public AssemblyConstituencyDto getAssemblyConstituencyById(long stateId) {
		AssemblyConstituency assemblyConstituency = assemblyConstituencyDao.getAssemblyConstituencyById(stateId);
		return convertAssemblyConstituency(assemblyConstituency);
	}

	@Override
	@Transactional
	public ParliamentConstituencyDto getParliamentConstituencyById(long pcId) {
		ParliamentConstituency parliamentConstituency = parliamentConstituencyDao.getParliamentConstituencyById(pcId);
		return convertParliamentConstituency(parliamentConstituency);
	}

	@Override
	@Transactional
	public List<RoleDto> getUserRoles(Long userId, PostLocationType postLocationType, Long locationId) {
		List<Role> roles = null;
		switch (postLocationType) {
		case Global:
			roles = roleDao.getUserGlobalRoles(userId);
			break;
		case STATE:
			roles = roleDao.getUserStateRoles(userId, locationId);
			break;
		case DISTRICT:
			roles = roleDao.getUserDistrictRoles(userId, locationId);
			break;
		case AC:
			roles = roleDao.getUserAcRoles(userId, locationId);
			break;
		case PC:
			roles = roleDao.getUserPcRoles(userId, locationId);
			break;
		}
		return convertRoles(roles);
	}

	private RoleDto convertRole(Role role) {
		if (role == null) {
			return null;
		}
		RoleDto roleDto = new RoleDto();
		BeanUtils.copyProperties(role, roleDto);
		return roleDto;
	}

	private List<RoleDto> convertRoles(List<Role> roles) {
		List<RoleDto> returnRoles = new ArrayList<>();
		if (roles == null) {
			return returnRoles;
		}
		for (Role role : roles) {
			returnRoles.add(convertRole(role));
		}
		return returnRoles;
	}

	@Override
	@Transactional
	public List<RoleDto> getLocationRoles(PostLocationType postLocationType, Long locationId) {
		List<Role> roles = null;
		switch (postLocationType) {
		case Global:
			roles = roleDao.getGlobalRoles();
			break;
		case STATE:
			roles = roleDao.getStateRoles(locationId);
			break;
		case DISTRICT:
			roles = roleDao.getDistrictRoles(locationId);
			break;
		case AC:
			roles = roleDao.getAcRoles(locationId);
			break;
		case PC:
			roles = roleDao.getPcRoles(locationId);
			break;
		}
		return convertRoles(roles);
	}

	@Override
	@Transactional
	public void removeAllUserRolesAtLocation(Long userId, PostLocationType postLocationType, Long locationId) {
		User user = userDao.getUserById(userId);
		switch (postLocationType) {
		case Global:
			user.getAllRoles().clear();
			break;
		case STATE: {
			Set<StateRole> allStateRoles = user.getStateRoles();
			if (allStateRoles != null && !allStateRoles.isEmpty()) {
				Iterator<StateRole> iterator = allStateRoles.iterator();
				StateRole oneStateRole;
				while (iterator.hasNext()) {
					oneStateRole = iterator.next();
					if (oneStateRole.getStateId().equals(locationId)) {
						iterator.remove();
					}
				}
			}
		}
			break;
		case DISTRICT: {
			Set<DistrictRole> allDistrictRoles = user.getDistrictRoles();
			if (allDistrictRoles != null && !allDistrictRoles.isEmpty()) {
				Iterator<DistrictRole> iterator = allDistrictRoles.iterator();
				DistrictRole oneDistrictRole;
				while (iterator.hasNext()) {
					oneDistrictRole = iterator.next();
					if (oneDistrictRole.getDistrictId().equals(locationId)) {
						iterator.remove();
					}
				}
			}
		}
			break;
		case AC: {
			Set<AcRole> allAcRoles = user.getAcRoles();
			if (allAcRoles != null && !allAcRoles.isEmpty()) {
				Iterator<AcRole> iterator = allAcRoles.iterator();
				AcRole oneAcRole;
				while (iterator.hasNext()) {
					oneAcRole = iterator.next();
					if (oneAcRole.getAssemblyConstituencyId().equals(locationId)) {
						iterator.remove();
					}
				}
			}
		}
			break;
		case PC: {
			Set<PcRole> allPcRoles = user.getPcRoles();
			if (allPcRoles != null && !allPcRoles.isEmpty()) {
				Iterator<PcRole> iterator = allPcRoles.iterator();
				PcRole onePcRole;
				while (iterator.hasNext()) {
					onePcRole = iterator.next();
					if (onePcRole.getParliamentConstituencyId().equals(locationId)) {
						iterator.remove();
					}
				}
			}
		}
			break;
		}
	}

	@Override
	@Transactional
	public void saveUserRolesAtLocation(Long userId, PostLocationType postLocationType, Long locationId, List<RoleDto> userRoleDtos) {
		User user = userDao.getUserById(userId);
		// First remove all roles at given location
		removeAllUserRolesAtLocation(userId, postLocationType, locationId);
		System.out.println("postLocationType=" + postLocationType);
		System.out.println("locationId=" + locationId);
		switch (postLocationType) {
		case Global:
			// and then add all new role
			if (userRoleDtos != null && !userRoleDtos.isEmpty()) {
				Set<Role> allNewRoles = new HashSet<>();
				Role oneRole;
				for (RoleDto oneRoleDto : userRoleDtos) {
					oneRole = roleDao.getRoleById(oneRoleDto.getId());
					allNewRoles.add(oneRole);
				}
				user.getAllRoles().addAll(allNewRoles);
			}
			break;
		case STATE: {
			if (userRoleDtos != null && !userRoleDtos.isEmpty()) {
				Set<StateRole> allNewStateRoles = new HashSet<>();
				StateRole oneStateRole;
				for (RoleDto oneRoleDto : userRoleDtos) {
					oneStateRole = stateRoleDao.getStateRoleByStateIdAndRoleId(locationId, oneRoleDto.getId());
					allNewStateRoles.add(oneStateRole);
				}
				user.getStateRoles().addAll(allNewStateRoles);
			}
		}
			break;
		case DISTRICT: {
			if (userRoleDtos != null && !userRoleDtos.isEmpty()) {
				Set<DistrictRole> allNewDistrictRoles = new HashSet<>();
				DistrictRole oneDistrictRole;
				for (RoleDto oneRoleDto : userRoleDtos) {
					oneDistrictRole = districtRoleDao.getDistrictRoleByDistrictIdAndRoleId(locationId, oneRoleDto.getId());
					allNewDistrictRoles.add(oneDistrictRole);
				}
				user.getDistrictRoles().addAll(allNewDistrictRoles);
			}
		}
			break;
		case AC: {
			System.out.println("Adding AC Roles");
			if (userRoleDtos != null && !userRoleDtos.isEmpty()) {
				Set<AcRole> allNewAcRoles = new HashSet<>();
				AcRole oneAcRole;
				for (RoleDto oneRoleDto : userRoleDtos) {
					System.out.println("oneRoleDto " + oneRoleDto.getId());
					oneAcRole = acRoleDao.getAcRoleByAcIdAndRoleId(locationId, oneRoleDto.getId());
					System.out.println("oneAcRole " + oneAcRole.getId());
					allNewAcRoles.add(oneAcRole);
				}
				user.getAcRoles().addAll(allNewAcRoles);
			}
		}
			break;
		case PC: {
			if (userRoleDtos != null && !userRoleDtos.isEmpty()) {
				Set<PcRole> allNewPcRoles = new HashSet<>();
				PcRole onePcRole;
				for (RoleDto oneRoleDto : userRoleDtos) {
					onePcRole = pcRoleDao.getPcRoleByPcIdAndRoleId(locationId, oneRoleDto.getId());
					allNewPcRoles.add(onePcRole);
				}
				user.getPcRoles().addAll(allNewPcRoles);
			}
		}
			break;
		}
		user = userDao.saveUser(user);
	}

	@Override
	@Transactional
	public BlogDto saveBlog(BlogDto blogDto, List<ContentTweetDto> contentTweetDtos, PostLocationType locationType, Long locationId) {
		Blog blog = null;
		if (blogDto.getId() != null && blogDto.getId() > 0) {
			blog = blogDao.getBlogById(blogDto.getId());
		}
		if (blog == null) {
			blog = new Blog();
			blog.setDateCreated(new Date());
			blog.setContentStatus(ContentStatus.Pending);
		}
		blog.setAuthor(blogDto.getAuthor());
		blog.setContent(blogDto.getContent());
		blog.setDateModified(new Date());
		blog.setImageUrl(blogDto.getImageUrl());
		blog.setSource(blogDto.getSource());
		blog.setTitle(blogDto.getTitle());

		switch (locationType) {
		case Global:
			blog.setGlobal(true);
			break;
		case STATE:
			if (blog.getStates() == null) {
				blog.setStates(new ArrayList<State>());
			}
			State state = stateDao.getStateById(locationId);
			blog.getStates().add(state);
			break;
		case DISTRICT:
			if (blog.getDistricts() == null) {
				blog.setDistricts(new ArrayList<District>());
			}
			District district = districtDao.getDistrictById(locationId);
			blog.getDistricts().add(district);
			break;
		case AC:
			if (blog.getAssemblyConstituencies() == null) {
				blog.setAssemblyConstituencies(new ArrayList<AssemblyConstituency>());
			}
			AssemblyConstituency assemblyConstituency = assemblyConstituencyDao.getAssemblyConstituencyById(locationId);
			blog.getAssemblyConstituencies().add(assemblyConstituency);
			break;
		case PC:
			if (blog.getParliamentConstituencies() == null) {
				blog.setParliamentConstituencies(new ArrayList<ParliamentConstituency>());
			}
			ParliamentConstituency parliamentConstituency = parliamentConstituencyDao.getParliamentConstituencyById(locationId);
			blog.getParliamentConstituencies().add(parliamentConstituency);
			break;
		}

		blog = blogDao.saveBlog(blog);

		// add all tweets
		if (contentTweetDtos != null && contentTweetDtos.size() > 0) {
			if (blog.getTweets() == null) {
				blog.setTweets(new ArrayList<ContentTweet>());
			}
			ContentTweet oneContentTweet;
			for (ContentTweetDto oneContentTweetDto : contentTweetDtos) {
				if (oneContentTweetDto.getId() == null || oneContentTweetDto.getId() <= 0) {
					oneContentTweet = new ContentTweet();
				} else {
					oneContentTweet = contentTweetDao.getContentTweetById(oneContentTweetDto.getId());
				}
				oneContentTweet.setImageUrl(oneContentTweetDto.getImageUrl());
				oneContentTweet.setTweetContent(oneContentTweetDto.getTweetContent());

				oneContentTweet = contentTweetDao.saveContentTweet(oneContentTweet);
				blog.getTweets().add(oneContentTweet);
			}
		}

		return convertBlog(blog);
	}

	private BlogDto convertBlog(Blog blog) {
		if (blog == null) {
			return null;
		}
		BlogDto blogDto = new BlogDto();
		BeanUtils.copyProperties(blog, blogDto);
		return blogDto;
	}

	private List<BlogDto> convertBlog(List<Blog> blog) {
		if (blog == null) {
			return null;
		}
		List<BlogDto> returnBlog = new ArrayList<>(blog.size());
		for (Blog oneBlog : blog) {
			returnBlog.add(convertBlog(oneBlog));
		}
		return returnBlog;
	}

	@Override
	@Transactional
	public List<BlogDto> getBlog(PostLocationType locationType, Long locationId) {
		List<Blog> blog = null;
		switch (locationType) {
		case Global:
			blog = blogDao.getGlobalBlog();
			break;
		case STATE:
			blog = blogDao.getStateBlog(locationId);
			break;
		case DISTRICT:
			blog = blogDao.getDistrictBlog(locationId);
			break;
		case AC:
			blog = blogDao.getAcBlog(locationId);
			break;
		case PC:
			blog = blogDao.getPcBlog(locationId);
			break;
		}
		return convertBlog(blog);
	}

	@Override
	@Transactional
	public BlogDto publishBlog(Long blogId) {
		Blog blog = blogDao.getBlogById(blogId);
		blog.setContentStatus(ContentStatus.Published);
		blog = blogDao.saveBlog(blog);
		return convertBlog(blog);
	}

	@Override
	@Transactional
	public PollQuestionDto savePollQuestion(PollQuestionDto pollQuestionDto, List<PollAnswerDto> pollAnswers, PostLocationType locationType, Long locationId) {
		PollQuestion pollQuestion = null;
		if (pollQuestionDto.getId() != null && pollQuestionDto.getId() > 0) {
			pollQuestion = pollQuestionDao.getPollQuestionById(pollQuestionDto.getId());
		}
		if (pollQuestion == null) {
			pollQuestion = new PollQuestion();
			pollQuestion.setDateCreated(new Date());
			pollQuestion.setContentStatus(ContentStatus.Pending);
		}
		pollQuestion.setContent(pollQuestionDto.getContent());
		pollQuestion.setDateModified(new Date());
		pollQuestion.setImageUrl(pollQuestionDto.getImageUrl());

		switch (locationType) {
		case Global:
			pollQuestion.setGlobal(true);
			break;
		case STATE:
			if (pollQuestion.getStates() == null) {
				pollQuestion.setStates(new ArrayList<State>());
			}
			State state = stateDao.getStateById(locationId);
			pollQuestion.getStates().add(state);
			break;
		case DISTRICT:
			if (pollQuestion.getDistricts() == null) {
				pollQuestion.setDistricts(new ArrayList<District>());
			}
			District district = districtDao.getDistrictById(locationId);
			pollQuestion.getDistricts().add(district);
			break;
		case AC:
			if (pollQuestion.getAssemblyConstituencies() == null) {
				pollQuestion.setAssemblyConstituencies(new ArrayList<AssemblyConstituency>());
			}
			AssemblyConstituency assemblyConstituency = assemblyConstituencyDao.getAssemblyConstituencyById(locationId);
			pollQuestion.getAssemblyConstituencies().add(assemblyConstituency);
			break;
		case PC:
			if (pollQuestion.getParliamentConstituencies() == null) {
				pollQuestion.setParliamentConstituencies(new ArrayList<ParliamentConstituency>());
			}
			ParliamentConstituency parliamentConstituency = parliamentConstituencyDao.getParliamentConstituencyById(locationId);
			pollQuestion.getParliamentConstituencies().add(parliamentConstituency);
			break;
		}

		pollQuestion = pollQuestionDao.savePollQuestion(pollQuestion);

		// add all tweets
		if (pollAnswers != null && pollAnswers.size() > 0) {
			if (pollQuestion.getPollAnswers() == null) {
				pollQuestion.setPollAnswers(new HashSet<PollAnswer>());
			}
			PollAnswer onePollAnswer;
			for (PollAnswerDto onePollAnswerDto : pollAnswers) {
				if (onePollAnswerDto.getId() == null || onePollAnswerDto.getId() <= 0) {
					onePollAnswer = new PollAnswer();
				} else {
					onePollAnswer = pollAnswerDao.getPollAnswerById(onePollAnswerDto.getId());
				}
				onePollAnswer.setContent(onePollAnswerDto.getContent());
				onePollAnswer.setPollQuestion(pollQuestion);
				onePollAnswer = pollAnswerDao.savePollAnswer(onePollAnswer);
				pollQuestion.getPollAnswers().add(onePollAnswer);
			}
		}
		return convertPollQuestion(pollQuestion);
	}
	private PollQuestionDto convertPollQuestion(PollQuestion pollQuestion){
		if(pollQuestion == null){
			return null;
		}
		PollQuestionDto pollQuestionDto = new PollQuestionDto();
		BeanUtils.copyProperties(pollQuestion, pollQuestionDto);
		return pollQuestionDto;
	}
	
	private List<PollQuestionDto> convertPollQuestions(List<PollQuestion> pollQuestions){
		List<PollQuestionDto> pollQuestionDtos = new ArrayList<>();
		if(pollQuestions == null){
			return pollQuestionDtos;
		}
		for(PollQuestion onePollQuestion:pollQuestions){
			pollQuestionDtos.add(convertPollQuestion(onePollQuestion));
		}
		return pollQuestionDtos;
	}

	@Override
	@Transactional
	public List<PollQuestionDto> getPollQuestion(PostLocationType locationType, Long locationId) {
		List<PollQuestion> pollQuestions = null;
		switch (locationType) {
		case Global:
			pollQuestions = pollQuestionDao.getGlobalPollQuestion();
			break;
		case STATE:
			pollQuestions = pollQuestionDao.getStatePollQuestion(locationId);
			break;
		case DISTRICT:
			pollQuestions = pollQuestionDao.getDistrictPollQuestion(locationId);
			break;
		case AC:
			pollQuestions = pollQuestionDao.getAcPollQuestion(locationId);
			break;
		case PC:
			pollQuestions = pollQuestionDao.getPcPollQuestion(locationId);
			break;
		}
		return convertPollQuestions(pollQuestions);
	}

	@Override
	@Transactional
	public List<PollAnswerDto> getPollAnswers(Long pollQuestionId) {
		PollQuestion pollQuestion = pollQuestionDao.getPollQuestionById(pollQuestionId);
		return convertPollAnswers(pollQuestion.getPollAnswers());
	}
	
	private PollAnswerDto convertPollAnswer(PollAnswer pollAnswer){
		if(pollAnswer == null){
			return null;
		}
		PollAnswerDto pollAnswerDto = new PollAnswerDto();
		BeanUtils.copyProperties(pollAnswer, pollAnswerDto);
		return pollAnswerDto;
	}
	
	private List<PollAnswerDto> convertPollAnswers(Collection<PollAnswer> pollAnswers){
		List<PollAnswerDto> pollAnswerDtos = new ArrayList<>();
		if(pollAnswers == null){
			return pollAnswerDtos;
		}
		for(PollAnswer onePollAnswer:pollAnswers){
			pollAnswerDtos.add(convertPollAnswer(onePollAnswer));
		}
		return pollAnswerDtos;
	}

	@Override
	@Transactional
	public PollQuestionDto publishPollQuestion(Long pollQuestionId) {
		PollQuestion pollQuestion = pollQuestionDao.getPollQuestionById(pollQuestionId);
		pollQuestion.setContentStatus(ContentStatus.Published);
		pollQuestion = pollQuestionDao.savePollQuestion(pollQuestion);
		return convertPollQuestion(pollQuestion);
	}

	private Account getAdminAccount(User adminUser){
		Account account = accountDao.getAdminAccountByUserId(adminUser.getId());
		if(account == null){
			account = new Account();
			account.setAccountOwner(adminUser);
			account.setAccountType(AccountType.Admin);
			account.setBalance(0.0);
			account.setDescription("Admin account for " +adminUser.getMembershipNumber());
			account.setDateCreated(new Date());
			account.setDateModified(new Date());
			account.setGlobal(false);
			account = accountDao.saveAccount(account);
		}
		return account;
	}
	private void addAccountTransaction(Account account, double amount, User adminUser, Date now, String description){
		AccountTransaction accountTransaction = new AccountTransaction();
		accountTransaction.setAccount(account);
		accountTransaction.setAccountTransactionMode(AccountTransactionMode.Cash);
		accountTransaction.setAccountTransactionType(AccountTransactionType.Credit);
		accountTransaction.setAmount(amount);
		accountTransaction.setBalance(account.getBalance() + amount);
		accountTransaction.setDateCreated(now);
		accountTransaction.setDateModified(now);
		accountTransaction.setCreatorId(adminUser.getId());
		accountTransaction.setModifierId(adminUser.getId());
		accountTransaction.setDescription(description);
		accountTransaction.setTransactionDate(now);
		accountTransaction = accountTransactionDao.saveAccountTransaction(accountTransaction);
		
		account.setBalance(account.getBalance() + amount);
	}
	
	@Override
	@Transactional
	public UserDto receiveMembershipFee(Long userId, double amount, Long adminUserId) {

		if(amount < DataUtil.MEMBERSHIP_FEE){
			throw new RuntimeException("Amount must be more then " + DataUtil.MEMBERSHIP_FEE+ " Rs");
		}
		User user = userDao.getUserById(userId);
		User adminUser = userDao.getUserById(adminUserId);
		
		//First COnfirm Membership
		user.setMembershipStatus("Confirmed");
		user.setMembershipConfirmedBy(adminUser);
		user = userDao.saveUser(user);
		
		//Now handle accounting
		Account adminAccount = getAdminAccount(adminUser);
		//create Membership transaction under this account
		Date now = new Date();
		
		addAccountTransaction(adminAccount, DataUtil.MEMBERSHIP_FEE, adminUser, now, "Membership fee for "+user.getMembershipNumber());
		
		
		if(amount > DataUtil.MEMBERSHIP_FEE){
			double donationAmount = amount - DataUtil.MEMBERSHIP_FEE;
			//TODO create a donation entry, will be done later
			
			addAccountTransaction(adminAccount, donationAmount, adminUser, now, "Donation by "+user.getMembershipNumber());
		}
		
		//now update overall account balance
		adminAccount.setBalance(adminAccount.getBalance() + amount);
		adminAccount = accountDao.saveAccount(adminAccount);
		
		return convertUser(user);
	}

	@Override
	@Transactional
	public List<AdminAccountDto> getAdminAccountDetails(PostLocationType locationType, Long locationId) {
		List<Account> accounts = null;
		List<Long> admins = null;
		switch (locationType) {
		case Global:
			admins = userDao.getAllAdminUserForGlobalTreasur();
			break;
		case STATE:
			admins = userDao.getAllAdminUserForStateTreasure(locationId);
			break;
		case DISTRICT:
			admins = userDao.getAllAdminUserForDistrictTreasure(locationId);
			break;
		case AC:
			admins = userDao.getAllAdminUserForAcTreasure(locationId);
			break;
		case PC:
			admins = userDao.getAllAdminUserForPcTreasure(locationId);
			break;
		default:
		}
		System.out.println("*** "+ locationType +" ,admins= "+ admins);
		if(admins != null){
			accounts = accountDao.getAccountsByUserId(admins);
		}
		System.out.println("*** "+ locationType +"  ,accounts= "+ accounts);
		return convertAdminAccounts(accounts);
	}
	
	private AdminAccountDto convertAdminAccount(Account oneAccount){
		if(oneAccount == null){
			return null;
		}
		AdminAccountDto adminAccountDto = new AdminAccountDto();
		BeanUtils.copyProperties(oneAccount, adminAccountDto);
		adminAccountDto.setAccountOwnerDto(convertUser(oneAccount.getAccountOwner()));
		return adminAccountDto;
	}
	private List<AdminAccountDto> convertAdminAccounts(List<Account> accounts){
		List<AdminAccountDto> adminAccountDtos = new ArrayList<>();
		if(accounts == null){
			return null;
		}
		for(Account oneAccount : accounts){
			adminAccountDtos.add(convertAdminAccount(oneAccount));
		}
		return adminAccountDtos;
	}
	
	

}
