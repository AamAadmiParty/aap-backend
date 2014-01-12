package com.next.aap.core.service;

import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.bytecode.opencsv.CSVReader;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.exception.AppException;
import com.next.aap.core.persistance.AcRole;
import com.next.aap.core.persistance.Account;
import com.next.aap.core.persistance.AccountTransaction;
import com.next.aap.core.persistance.AssemblyConstituency;
import com.next.aap.core.persistance.Blog;
import com.next.aap.core.persistance.ContentTweet;
import com.next.aap.core.persistance.Country;
import com.next.aap.core.persistance.CountryRegion;
import com.next.aap.core.persistance.CountryRegionArea;
import com.next.aap.core.persistance.CountryRegionAreaRole;
import com.next.aap.core.persistance.CountryRegionRole;
import com.next.aap.core.persistance.CountryRole;
import com.next.aap.core.persistance.District;
import com.next.aap.core.persistance.DistrictRole;
import com.next.aap.core.persistance.Donation;
import com.next.aap.core.persistance.DonationDump;
import com.next.aap.core.persistance.Email;
import com.next.aap.core.persistance.Email.ConfirmationType;
import com.next.aap.core.persistance.FacebookAccount;
import com.next.aap.core.persistance.FacebookApp;
import com.next.aap.core.persistance.FacebookAppPermission;
import com.next.aap.core.persistance.FacebookGroup;
import com.next.aap.core.persistance.FacebookGroupMembership;
import com.next.aap.core.persistance.FacebookPage;
import com.next.aap.core.persistance.FacebookPost;
import com.next.aap.core.persistance.Interest;
import com.next.aap.core.persistance.InterestGroup;
import com.next.aap.core.persistance.News;
import com.next.aap.core.persistance.Office;
import com.next.aap.core.persistance.ParliamentConstituency;
import com.next.aap.core.persistance.PcRole;
import com.next.aap.core.persistance.Permission;
import com.next.aap.core.persistance.Phone;
import com.next.aap.core.persistance.Phone.PhoneType;
import com.next.aap.core.persistance.PlannedEmail;
import com.next.aap.core.persistance.PlannedFacebookPost;
import com.next.aap.core.persistance.PlannedSms;
import com.next.aap.core.persistance.PlannedTweet;
import com.next.aap.core.persistance.PollAnswer;
import com.next.aap.core.persistance.PollQuestion;
import com.next.aap.core.persistance.Role;
import com.next.aap.core.persistance.State;
import com.next.aap.core.persistance.StateRole;
import com.next.aap.core.persistance.Tweet;
import com.next.aap.core.persistance.TwitterAccount;
import com.next.aap.core.persistance.User;
import com.next.aap.core.persistance.Volunteer;
import com.next.aap.core.persistance.dao.AcRoleDao;
import com.next.aap.core.persistance.dao.AccountDao;
import com.next.aap.core.persistance.dao.AccountTransactionDao;
import com.next.aap.core.persistance.dao.AssemblyConstituencyDao;
import com.next.aap.core.persistance.dao.BlogDao;
import com.next.aap.core.persistance.dao.ContentTweetDao;
import com.next.aap.core.persistance.dao.CountryDao;
import com.next.aap.core.persistance.dao.CountryRegionAreaDao;
import com.next.aap.core.persistance.dao.CountryRegionDao;
import com.next.aap.core.persistance.dao.CountryRegionRoleDao;
import com.next.aap.core.persistance.dao.CountryRoleDao;
import com.next.aap.core.persistance.dao.DistrictDao;
import com.next.aap.core.persistance.dao.DistrictRoleDao;
import com.next.aap.core.persistance.dao.DonationDao;
import com.next.aap.core.persistance.dao.DonationDumpDao;
import com.next.aap.core.persistance.dao.EmailDao;
import com.next.aap.core.persistance.dao.FacebookAccountDao;
import com.next.aap.core.persistance.dao.FacebookAppDao;
import com.next.aap.core.persistance.dao.FacebookAppPermissionDao;
import com.next.aap.core.persistance.dao.FacebookGroupDao;
import com.next.aap.core.persistance.dao.FacebookGroupMembershipDao;
import com.next.aap.core.persistance.dao.FacebookPageDao;
import com.next.aap.core.persistance.dao.FacebookPostDao;
import com.next.aap.core.persistance.dao.InterestDao;
import com.next.aap.core.persistance.dao.InterestGroupDao;
import com.next.aap.core.persistance.dao.NewsDao;
import com.next.aap.core.persistance.dao.OfficeDao;
import com.next.aap.core.persistance.dao.ParliamentConstituencyDao;
import com.next.aap.core.persistance.dao.PcRoleDao;
import com.next.aap.core.persistance.dao.PermissionDao;
import com.next.aap.core.persistance.dao.PhoneDao;
import com.next.aap.core.persistance.dao.PlannedEmailDao;
import com.next.aap.core.persistance.dao.PlannedFacebookPostDao;
import com.next.aap.core.persistance.dao.PlannedSmsDao;
import com.next.aap.core.persistance.dao.PlannedTweetDao;
import com.next.aap.core.persistance.dao.PollAnswerDao;
import com.next.aap.core.persistance.dao.PollQuestionDao;
import com.next.aap.core.persistance.dao.RoleDao;
import com.next.aap.core.persistance.dao.StateDao;
import com.next.aap.core.persistance.dao.StateRoleDao;
import com.next.aap.core.persistance.dao.TweetDao;
import com.next.aap.core.persistance.dao.TwitterAccountDao;
import com.next.aap.core.persistance.dao.UserDao;
import com.next.aap.core.persistance.dao.VolunteerDao;
import com.next.aap.core.util.DataUtil;
import com.next.aap.web.dto.AccountTransactionDto;
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
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
import com.next.aap.web.dto.CreationType;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.EmailUserDto;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.FacebookPostDto;
import com.next.aap.web.dto.InterestDto;
import com.next.aap.web.dto.InterestGroupDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.OfficeDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.PlannedEmailDto;
import com.next.aap.web.dto.PlannedFacebookPostDto;
import com.next.aap.web.dto.PlannedPostStatus;
import com.next.aap.web.dto.PlannedSmsDto;
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
import com.next.aap.web.dto.VolunteerDto;

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
	private CountryRegionDao countryRegionDao;
	@Autowired
	private CountryRegionAreaDao countryRegionAreaDao;
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
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private CountryRoleDao countryRoleDao;
	@Autowired
	private CountryRegionRoleDao countryRegionRoleDao;
	@Autowired
	private InterestDao interestDao;
	@Autowired
	private InterestGroupDao interestGroupDao;
	@Autowired
	private PlannedSmsDao plannedSmsDao;
	@Autowired
	private PlannedEmailDao plannedEmailDao;
	@Autowired
	private VolunteerDao volunteerDao;
	@Autowired
	private DonationDao donationDao;
	@Autowired
	private DonationDumpDao donationDumpDao;

	@Value("${voa_facebook_app_id}")
	private String voiceOfAapAppId;

	Map<String, String> countryCodeMap = new HashMap<>();
	
	@PostConstruct
	public void init(){
		countryCodeMap.put("AF", "Afghanistan");
		countryCodeMap.put("AL", "Albania");
		countryCodeMap.put("DZ", "Algeria");
		countryCodeMap.put("AS", "American Samoa");
		countryCodeMap.put("AD", "Andorra");
		countryCodeMap.put("AO", "Angola");
		countryCodeMap.put("AI", "Anguilla");
		countryCodeMap.put("AQ", "Antarctica");
		countryCodeMap.put("AG", "Antigua and Barbuda");
		countryCodeMap.put("AR", "Argentina");
		countryCodeMap.put("AM", "Armenia");
		countryCodeMap.put("AW", "Aruba");
		countryCodeMap.put("AU", "Australia");
		countryCodeMap.put("AT", "Austria");
		countryCodeMap.put("AZ", "Azerbaijan");
		countryCodeMap.put("BS", "Bahamas");
		countryCodeMap.put("BH", "Bahrain");
		countryCodeMap.put("BD", "Bangladesh");
		countryCodeMap.put("BB", "Barbados");
		countryCodeMap.put("BY", "Belarus");
		countryCodeMap.put("BE", "Belgium");
		countryCodeMap.put("BZ", "Belize");
		countryCodeMap.put("BJ", "Benin");
		countryCodeMap.put("BM", "Bermuda");
		countryCodeMap.put("BT", "Bhutan");
		countryCodeMap.put("BO", "Bolivia");
		countryCodeMap.put("BA", "Bosnia and Herzegovina");
		countryCodeMap.put("BW", "Botswana");
		countryCodeMap.put("BR", "Brazil");
		countryCodeMap.put("IO", "British Indian Ocean Territory");
		countryCodeMap.put("BN", "Brunei");
		countryCodeMap.put("BG", "Bulgaria");
		countryCodeMap.put("BF", "Burkina Faso");
		countryCodeMap.put("BI", "Burundi");
		countryCodeMap.put("KH", "Cambodia");
		countryCodeMap.put("CM", "Cameroon");
		countryCodeMap.put("CA", "Canada");
		countryCodeMap.put("CV", "Cape Verde");
		countryCodeMap.put("KY", "Cayman Islands");
		countryCodeMap.put("CF", "Central African Republic");
		countryCodeMap.put("TD", "Chad");
		countryCodeMap.put("CL", "Chile");
		countryCodeMap.put("CN", "China");
		countryCodeMap.put("CX", "Christmas Island");
		countryCodeMap.put("CC", "Cocos (Keeling) Islands");
		countryCodeMap.put("CO", "Colombia");
		countryCodeMap.put("KM", "Comoros");
		countryCodeMap.put("CG", "Comoros");
		countryCodeMap.put("CK", "Cook Islands");
		countryCodeMap.put("CR", "Costa Rica");
		countryCodeMap.put("HR", "Croatia");
		countryCodeMap.put("CU", "Cuba");
		countryCodeMap.put("CY", "Cyprus");
		countryCodeMap.put("CZ", "Czech Republic");
		countryCodeMap.put("DK", "Denmark");
		countryCodeMap.put("DJ", "Djibouti");
		countryCodeMap.put("DM", "Dominica");
		countryCodeMap.put("DO", "Dominican Republic");
		countryCodeMap.put("EC", "Ecuador");
		countryCodeMap.put("EG", "Egypt");
		countryCodeMap.put("SV", "El Salvador");
		countryCodeMap.put("GQ", "Equatorial Guinea");
		countryCodeMap.put("ER", "Eritrea");
		countryCodeMap.put("EE", "Estonia");
		countryCodeMap.put("ET", "Ethiopia");
		countryCodeMap.put("FK", "Falkland Islands");
		countryCodeMap.put("FO", "Faroe Islands");
		countryCodeMap.put("FJ", "Fiji");
		countryCodeMap.put("FI", "Finland");
		countryCodeMap.put("FR", "France");
		countryCodeMap.put("PF", "French Polynesia");
		countryCodeMap.put("GA", "Gabon");
		countryCodeMap.put("GM", "Gambia");
		countryCodeMap.put("GE", "Georgia");
		countryCodeMap.put("DE", "Germany");
		countryCodeMap.put("GH", "Ghana");
		countryCodeMap.put("GI", "Gibraltar");
		countryCodeMap.put("GR", "Greece");
		countryCodeMap.put("GL", "Greenland");
		countryCodeMap.put("GD", "Grenada");
		countryCodeMap.put("GU", "Guam");
		countryCodeMap.put("GT", "Guatemala");
		countryCodeMap.put("GN", "Guinea");
		countryCodeMap.put("GW", "Guinea-Bissau");
		countryCodeMap.put("GY", "Guyana");
		countryCodeMap.put("HT", "Haiti");
		countryCodeMap.put("VA", "Holy See (Vatican City)");
		countryCodeMap.put("HN", "Honduras");
		countryCodeMap.put("HK", "Hong Kong");
		countryCodeMap.put("HU", "Hungary");
		countryCodeMap.put("IS", "Iceland");
		countryCodeMap.put("IN", "India");
		countryCodeMap.put("ID", "Indonesia");
		countryCodeMap.put("IR", "Iran");
		countryCodeMap.put("IQ", "Iraq");
		countryCodeMap.put("IE", "Ireland");
		countryCodeMap.put("IL", "Israel");
		countryCodeMap.put("IT", "Italy");
		countryCodeMap.put("JM", "Jamaica");
		countryCodeMap.put("JP", "Japan");
		countryCodeMap.put("JO", "Jordan");
		countryCodeMap.put("KZ", "Kazakhstan");
		countryCodeMap.put("KE", "Kenya");
		countryCodeMap.put("KI", "Kiribati");
		countryCodeMap.put("KW", "Kuwait");
		countryCodeMap.put("KG", "Kyrgyzstan");
		countryCodeMap.put("LA", "Laos");
		countryCodeMap.put("LV", "Latvia");
		countryCodeMap.put("LB", "Lebanon");
		countryCodeMap.put("LS", "Lesotho");
		countryCodeMap.put("LR", "Liberia");
		countryCodeMap.put("LY", "Libya");
		countryCodeMap.put("LI", "Liechtenstein");
		countryCodeMap.put("LT", "Lithuania");
		countryCodeMap.put("LU", "Luxembourg");
		countryCodeMap.put("MO", "Macau");
		countryCodeMap.put("MK", "Macedonia");
		countryCodeMap.put("MG", "Madagascar");
		countryCodeMap.put("MW", "Malawi");
		countryCodeMap.put("MY", "Malaysia");
		countryCodeMap.put("MV", "Maldives");
		countryCodeMap.put("ML", "Mali");
		countryCodeMap.put("MT", "Malta");
		countryCodeMap.put("MH", "Marshall Islands");
		countryCodeMap.put("MR", "Mauritania");
		countryCodeMap.put("MU", "Mauritius");
		countryCodeMap.put("YT", "Mayotte");
		countryCodeMap.put("MX", "Mexico");
		countryCodeMap.put("FM", "Micronesia");
		countryCodeMap.put("MD", "Moldova");
		countryCodeMap.put("MC", "Monaco");
		countryCodeMap.put("MN", "Mongolia");
		countryCodeMap.put("MS", "Montserrat");
		countryCodeMap.put("MA", "Morocco");
		countryCodeMap.put("MZ", "Mozambique");
		countryCodeMap.put("MM", "Burma (Myanmar)");
		countryCodeMap.put("NA", "Namibia");
		countryCodeMap.put("NR", "Nauru");
		countryCodeMap.put("NP", "Nepal");
		countryCodeMap.put("NL", "Netherlands");
		countryCodeMap.put("AN", "Netherlands Antilles");
		countryCodeMap.put("NC", "New Caledonia");
		countryCodeMap.put("NZ", "New Zealand");
		countryCodeMap.put("NI", "Nicaragua");
		countryCodeMap.put("NE", "Niger");
		countryCodeMap.put("NG", "Nigeria");
		countryCodeMap.put("NU", "Niue");
		countryCodeMap.put("NF", "Norfolk Island");
		countryCodeMap.put("MP", "Northern Mariana Islands");
		countryCodeMap.put("NO", "Norway");
		countryCodeMap.put("OM", "Oman");
		countryCodeMap.put("PW", "Palau");
		countryCodeMap.put("PA", "Panama");
		countryCodeMap.put("PG", "Papua New Guinea");
		countryCodeMap.put("PY", "Paraguay");
		countryCodeMap.put("PE", "Peru");
		countryCodeMap.put("PH", "Philippines");
		countryCodeMap.put("PN", "Pitcairn Islands");
		countryCodeMap.put("PL", "Poland");
		countryCodeMap.put("PT", "Portugal");
		countryCodeMap.put("PR", "Puerto Rico");
		countryCodeMap.put("QA", "Qatar");
		countryCodeMap.put("RO", "Romania");
		countryCodeMap.put("RU", "Russia");
		countryCodeMap.put("RW", "Rwanda");
		countryCodeMap.put("KN", "Saint Kitts and Nevis");
		countryCodeMap.put("LC", "Saint Lucia");
		countryCodeMap.put("VC", "Saint Vincent and the Grenadines");
		countryCodeMap.put("WS", "Samoa");
		countryCodeMap.put("SM", "San Marino");
		countryCodeMap.put("ST", "Sao Tome and Principe");
		countryCodeMap.put("SA", "Saudi Arabia");
		countryCodeMap.put("SN", "Senegal");
		countryCodeMap.put("SC", "Seychelles");
		countryCodeMap.put("SL", "Sierra Leone");
		countryCodeMap.put("SG", "Singapore");
		countryCodeMap.put("SK", "Slovakia");
		countryCodeMap.put("SI", "Slovenia");
		countryCodeMap.put("SB", "Solomon Islands");
		countryCodeMap.put("SO", "Somalia");
		countryCodeMap.put("ZA", "South Africa");
		countryCodeMap.put("ES", "Spain");
		countryCodeMap.put("LK", "Sri Lanka");
		countryCodeMap.put("SH", "Saint Helena");
		countryCodeMap.put("PM", "Saint Pierre and Miquelon");
		countryCodeMap.put("SD", "Sudan");
		countryCodeMap.put("SR", "Suriname");
		countryCodeMap.put("SJ", "Svalbard");
		countryCodeMap.put("SZ", "Swaziland");
		countryCodeMap.put("SE", "Sweden");
		countryCodeMap.put("CH", "Switzerland");
		countryCodeMap.put("SY", "Syria");
		countryCodeMap.put("TW", "Taiwan");
		countryCodeMap.put("TJ", "Tajikistan");
		countryCodeMap.put("TZ", "Tanzania");
		countryCodeMap.put("TH", "Thailand");
		countryCodeMap.put("TG", "Togo");
		countryCodeMap.put("TK", "Tokelau");
		countryCodeMap.put("TO", "Tonga");
		countryCodeMap.put("TT", "Trinidad and Tobago");
		countryCodeMap.put("TN", "Tunisia");
		countryCodeMap.put("TR", "Turkey");
		countryCodeMap.put("TM", "Turkmenistan");
		countryCodeMap.put("TC", "Turks and Caicos Islands");
		countryCodeMap.put("TV", "Tuvalu");
		countryCodeMap.put("UG", "Uganda");
		countryCodeMap.put("UA", "Ukraine");
		countryCodeMap.put("AE", "United Arab Emirates");
		countryCodeMap.put("GB", "United Kingdom");
		countryCodeMap.put("US", "United States");
		countryCodeMap.put("UY", "Uruguay");
		countryCodeMap.put("UZ", "Uzbekistan");
		countryCodeMap.put("VU", "Vanuatu");
		countryCodeMap.put("VE", "Venezuela");
		countryCodeMap.put("VN", "Vietnam");
		countryCodeMap.put("VI", "US Virgin Islands");
		countryCodeMap.put("VG", "British Virgin Islands");
		countryCodeMap.put("WF", "Wallis and Futuna");
		countryCodeMap.put("EH", "Western Sahara");
		countryCodeMap.put("YE", "Yemen");
		countryCodeMap.put("YU", "Serbia");
		countryCodeMap.put("ZM", "Zambia");
		countryCodeMap.put("ZW", "Zimbabwe");
		countryCodeMap.put("CG", "Republic of the Congo");
		countryCodeMap.put("CD", "Democratic Republic of the Congo");
		countryCodeMap.put("KP", "North Korea");
		countryCodeMap.put("KR", "South Korea");
	}

	@Override
	@Transactional
	public UserDto saveFacebookUser(Long existingUserId, Connection<Facebook> connection, String facebookAppId) throws AppException{
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
				today.add(Calendar.YEAR, 10);
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

	private void mergeUser(User targetUser, User sourceUser) throws AppException {
		if(targetUser == null || sourceUser == null){
			//Nothing to merge
			return;
		}
		if(targetUser.equals(sourceUser)){
			//Nothing to merge, both are same user
			return;
		}
		if(targetUser.getMembershipNumber() != null && sourceUser.getMembershipNumber() != null){
			if(!targetUser.getMembershipNumber().equals(sourceUser.getMembershipNumber())){
				throw new AppException("User "+targetUser.getId() +" and User "+ sourceUser.getId()+" can not be merged as both have different membership number");
			}
		}
		if(targetUser.getCreationType() == CreationType.SelfServiceUser && sourceUser.getCreationType() == CreationType.SelfServiceUser){
			throw new AppException("User "+targetUser.getId() +" and User "+ sourceUser.getId()+" can not be merged as both created by self Service user");
		}
		//We will not copy any Admin related roles from source user to target user.
		//Only Donation, Facebook Accounts, twitter acounts etc will be merged
		if(sourceUser.getAssemblyConstituencyLiving() != null && targetUser.getAssemblyConstituencyLiving() == null){
			targetUser.setAssemblyConstituencyLiving(sourceUser.getAssemblyConstituencyLiving());
		}
		if(sourceUser.getAssemblyConstituencyVoting() != null && targetUser.getAssemblyConstituencyVoting() == null){
			targetUser.setAssemblyConstituencyVoting(sourceUser.getAssemblyConstituencyVoting());
		}
		if(sourceUser.getCreationType() == CreationType.SelfServiceUser || targetUser.getCreationType() == CreationType.SelfServiceUser){
			targetUser.setCreationType(CreationType.SelfServiceUser);
		}
		if(sourceUser.getDateOfBirth() != null && targetUser.getDateOfBirth() == null){
			targetUser.setDateOfBirth(sourceUser.getDateOfBirth());
		}
		if(!StringUtil.isEmpty(sourceUser.getAddress()) && StringUtil.isEmpty(targetUser.getAddress())){
			targetUser.setAddress(sourceUser.getAddress());
		}
		if(sourceUser.getDistrictLiving() != null && targetUser.getDistrictLiving() == null){
			targetUser.setDistrictLiving(sourceUser.getDistrictLiving());
		}
		if(sourceUser.getDistrictVoting() != null && targetUser.getDistrictVoting() == null){
			targetUser.setDistrictVoting(sourceUser.getDistrictVoting());
		}
		//Currently we support only one facebook account so no need to merge Facebook accounts
		//all facebook account user's creation type will be SelfService User 
		/*
		if(sourceUser.getFacebookAccounts() != null && !sourceUser.getFacebookAccounts().isEmpty()){
			targetUser.setDistrictVoting(sourceUser.getDistrictVoting());
		}
		*/
		if(!StringUtil.isEmpty(sourceUser.getFatherName()) && StringUtil.isEmpty(targetUser.getFatherName())){
			targetUser.setFatherName(sourceUser.getFatherName());
		}
		if(!StringUtil.isEmpty(sourceUser.getGender()) && (StringUtil.isEmpty(targetUser.getGender()) || targetUser.getGender().equals("NotSpecified"))){
			targetUser.setGender(sourceUser.getGender());
		}
		//Interests can be merged
		if(sourceUser.getInterests() != null && !sourceUser.getInterests().isEmpty()){
			if(targetUser.getInterests() == null){
				targetUser.setInterests(new HashSet<Interest>());
			}
			targetUser.getInterests().addAll(sourceUser.getInterests());
		}
		
		if(!StringUtil.isEmpty(sourceUser.getMembershipNumber()) && StringUtil.isEmpty(targetUser.getMembershipNumber())){
			targetUser.setMembershipNumber(sourceUser.getMembershipNumber());
			targetUser.setMembershipStatus(sourceUser.getMembershipStatus());
			targetUser.setMembershipConfirmedBy(sourceUser.getMembershipConfirmedBy());
		}
		if(!StringUtil.isEmpty(sourceUser.getMotherName()) && StringUtil.isEmpty(targetUser.getMotherName())){
			targetUser.setMotherName(sourceUser.getMotherName());
		}
		if(sourceUser.getNriCountry() != null && targetUser.getNriCountry() == null){
			targetUser.setNriCountry(sourceUser.getNriCountry());
		}
		if(sourceUser.getNriCountryRegion() != null && targetUser.getNriCountryRegion() == null){
			targetUser.setNriCountryRegion(sourceUser.getNriCountryRegion());
		}
		if(sourceUser.getNriCountryRegionArea() != null && targetUser.getNriCountryRegionArea() == null){
			targetUser.setNriCountryRegionArea(sourceUser.getNriCountryRegionArea());
		}
		if(sourceUser.getParliamentConstituencyLiving() != null && targetUser.getParliamentConstituencyLiving() == null){
			targetUser.setParliamentConstituencyLiving(sourceUser.getParliamentConstituencyLiving());
		}
		if(sourceUser.getParliamentConstituencyVoting() != null && targetUser.getParliamentConstituencyVoting() == null){
			targetUser.setParliamentConstituencyVoting(sourceUser.getParliamentConstituencyVoting());
		}
		if(sourceUser.getPassportNumber() != null && targetUser.getPassportNumber() == null){
			targetUser.setPassportNumber(sourceUser.getPassportNumber());
		}
		if(!StringUtil.isEmpty(sourceUser.getProfilePic()) && StringUtil.isEmpty(targetUser.getProfilePic())){
			targetUser.setProfilePic(sourceUser.getProfilePic());
		}
		if(sourceUser.getStateLiving() != null && targetUser.getStateLiving() == null){
			targetUser.setStateLiving(sourceUser.getStateLiving());
		}
		if(sourceUser.getStateVoting() != null && targetUser.getStateVoting() == null){
			targetUser.setStateVoting(sourceUser.getStateVoting());
		}
		if(!StringUtil.isEmpty(sourceUser.getVoterId()) && StringUtil.isEmpty(targetUser.getVoterId())){
			targetUser.setVoterId(sourceUser.getVoterId());
		}
		//Twiitter accounts can not be merged as they will belongs to different Self Service uSer, but in future we will
		//give option to user to merge users
		
		//Move donations from source user to target user
		if(sourceUser.getDonations() != null && !sourceUser.getDonations().isEmpty()){
			for(Donation oneDonation:sourceUser.getDonations()){
				oneDonation.setUser(targetUser);
			}
		}
		if(sourceUser.getEmails() != null && !sourceUser.getEmails().isEmpty()){
			for(Email oneEmail:sourceUser.getEmails()){
				oneEmail.setUser(targetUser);
			}
		}
		if(sourceUser.getPhones() != null && !sourceUser.getPhones().isEmpty()){
			for(Phone onePhone:sourceUser.getPhones()){
				onePhone.setUser(targetUser);
			}
		}
		
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
	@Cacheable("location")
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
	@Cacheable("location")
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
	@Cacheable("location")
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
	@Cacheable("location")
	public StateDto getStateById(Long stateId) {
		State state = stateDao.getStateById(stateId);
		return convertState(state);
	}

	@Override
	@Transactional
	@Cacheable("location")
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
	@Cacheable("location")
	public List<ParliamentConstituencyDto> getAllParliamentConstituenciesOfState(long stateId) {
		List<ParliamentConstituency> parliamentConstituencies = parliamentConstituencyDao.getParliamentConstituencyOfState(stateId);
		return convertParliamentConstituencyList(parliamentConstituencies);
	}

	@Override
	@Transactional
	public UserDto saveUser(UserDto userDto) throws AppException {
		User user;
		System.out.println("userDto.getId = " + userDto.getId());
		if (userDto.getId() == null || userDto.getId() <= 0) {
			user = new User();
		} else {
			user = userDao.getUserById(userDto.getId());
			if (user == null) {
				throw new AppException("User DO NOT Exists [id=" + userDto.getId() + "]");
			}
		}

		SearchMemberResultDto searchMemberResultDto = searchExistingUser(userDto);
		if (searchMemberResultDto != null && searchMemberResultDto.isUserAlreadyExists()) {
			if (userDto.getId() == null || userDto.getId() <= 0) {
				throw new RuntimeException(searchMemberResultDto.getUserAlreadyExistsMessage());
			} else {
				if (!searchMemberResultDto.getUsers().get(0).getId().equals(userDto.getId())) {
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
			user.setStateLivingId(stateLiving.getId());
		}
		if (userDto.getDistrictLivingId() != null && userDto.getDistrictLivingId() > 0) {
			District districtLiving = districtDao.getDistrictById(userDto.getDistrictLivingId());
			user.setDistrictLiving(districtLiving);
			user.setDistrictLivingId(userDto.getDistrictLivingId());
		}
		if (userDto.getAssemblyConstituencyLivingId() != null && userDto.getAssemblyConstituencyLivingId() > 0) {
			AssemblyConstituency assemblyConstituencyLiving = assemblyConstituencyDao.getAssemblyConstituencyById(userDto.getAssemblyConstituencyLivingId());
			user.setAssemblyConstituencyLiving(assemblyConstituencyLiving);
			user.setAssemblyConstituencyLivingId(userDto.getAssemblyConstituencyLivingId());
		}

		if (userDto.getParliamentConstituencyLivingId() != null && userDto.getParliamentConstituencyLivingId() > 0) {
			ParliamentConstituency parliamentConstituencyLiving = parliamentConstituencyDao.getParliamentConstituencyById(userDto
					.getParliamentConstituencyLivingId());
			user.setParliamentConstituencyLiving(parliamentConstituencyLiving);
			user.setParliamentConstituencyLivingId(userDto.getParliamentConstituencyLivingId());
		}

		if (userDto.getStateVotingId() != null && userDto.getStateVotingId() > 0) {
			State stateVoting = stateDao.getStateById(userDto.getStateVotingId());
			user.setStateVoting(stateVoting);
			user.setStateVotingId(userDto.getStateVotingId());
		}
		if (userDto.getDistrictVotingId() != null && userDto.getDistrictVotingId() > 0) {
			District districtVoting = districtDao.getDistrictById(userDto.getDistrictVotingId());
			user.setDistrictVoting(districtVoting);
			user.setDistrictVotingId(userDto.getDistrictVotingId());
		}
		if (userDto.getAssemblyConstituencyVotingId() != null && userDto.getAssemblyConstituencyVotingId() > 0) {
			AssemblyConstituency assemblyConstituencyVoting = assemblyConstituencyDao.getAssemblyConstituencyById(userDto.getAssemblyConstituencyVotingId());
			user.setAssemblyConstituencyVoting(assemblyConstituencyVoting);
			user.setAssemblyConstituencyVotingId(userDto.getAssemblyConstituencyVotingId());
		}
		if (userDto.getParliamentConstituencyVotingId() != null && userDto.getParliamentConstituencyVotingId() > 0) {
			ParliamentConstituency parliamentConstituencyVoting = parliamentConstituencyDao.getParliamentConstituencyById(userDto
					.getParliamentConstituencyVotingId());
			user.setParliamentConstituencyVoting(parliamentConstituencyVoting);
			user.setParliamentConstituencyVotingId(userDto.getParliamentConstituencyVotingId());
		}
		if (userDto.getNriCountryRegionId() != null && userDto.getNriCountryRegionId() > 0) {
			CountryRegion countryRegion = countryRegionDao.getCountryRegionById(userDto.getNriCountryRegionId());
			user.setNriCountryRegion(countryRegion);
			user.setNriCountryRegionId(userDto.getNriCountryRegionId());
		}
		if (userDto.getNriCountryRegionAreaId() != null && userDto.getNriCountryRegionAreaId() > 0) {
			CountryRegionArea countryRegionArea = countryRegionAreaDao.getCountryRegionAreaById(userDto.getNriCountryRegionAreaId());
			user.setNriCountryRegionArea(countryRegionArea);
			user.setNriCountryRegionAreaId(userDto.getNriCountryRegionAreaId());
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
				user.setNriCountryId(userDto.getNriCountryId());
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
		 * createRoleWithPermissions("VoiceOfAapFacebookAdminRole",
		 * " User of this role will be able to make Facebook post using voice of AAP Application"
		 * , true, true, false, false,true, true,false,
		 * AppPermission.ADMIN_VOICE_OF_AAP_FB);
		 * createRoleWithPermissions("VoiceOfAapTwitterAdminRole",
		 * "User of this role will be able to make Twitter post using voice of AAP Application"
		 * , true, true, false, false,true, true,false,
		 * AppPermission.ADMIN_VOICE_OF_AAP_TWITTER); // News Related Roles
		 * createRoleWithPermissions("NewsAdminRole",
		 * "User of this role will be able to create/update/Approve/delete news for a location"
		 * , true, true, true, true,true, true,false, AppPermission.CREATE_NEWS,
		 * AppPermission.UPDATE_NEWS, AppPermission.DELETE_NEWS,
		 * AppPermission.APPROVE_NEWS);
		 * 
		 * createRoleWithPermissions("NewsReporterRole",
		 * "User of this role will be able to create/update news for a location"
		 * , true, true, true, true, true, true,false,AppPermission.CREATE_NEWS,
		 * AppPermission.UPDATE_NEWS);
		 * 
		 * createRoleWithPermissions("NewsEditorRole",
		 * "User of this role will be able to create/update/approve and publish news for a location"
		 * , true, true, true, true, true, true,false,AppPermission.CREATE_NEWS,
		 * AppPermission.UPDATE_NEWS, AppPermission.APPROVE_NEWS);
		 * 
		 * createRoleWithPermissions("NewsApproverRole",
		 * "User of this role will be able to approve/publish existing news for a location"
		 * , true, true, true, true, true,
		 * true,false,AppPermission.APPROVE_NEWS);
		 * 
		 * createRoleWithPermissions("GlobalMemberAdminRole",
		 * "User of this role will be able to add new member at any location and will be able to update any member"
		 * , false, false, false, false, true,
		 * true,false,AppPermission.ADD_MEMBER,
		 * AppPermission.UPDATE_GLOBAL_MEMBER, AppPermission.VIEW_MEMBER);
		 * 
		 * createRoleWithPermissions("MemberAdminRole",
		 * "User of this role will be able to add new member at any location and will be able to update member at his location only"
		 * , true, true, true, true, true, true,false,AppPermission.ADD_MEMBER,
		 * AppPermission.UPDATE_MEMBER, AppPermission.VIEW_MEMBER);
		 * 
		 * createRoleWithPermissions("AdminEditUserRoles",
		 * "User of this role will be able to add or remove user roles on a location"
		 * , true, true, true, true, true,
		 * true,false,AppPermission.EDIT_USER_ROLES);
		 * 
		 * createRoleWithPermissions("BlogAdminRole",
		 * "User of this role will be able to create/update/Approve/delete blog for a location"
		 * , true, true, true, true, true, true,false,AppPermission.CREATE_BLOG,
		 * AppPermission.UPDATE_BLOG, AppPermission.DELETE_BLOG,
		 * AppPermission.APPROVE_BLOG);
		 * 
		 * createRoleWithPermissions("BlogReporterRole",
		 * "User of this role will be able to create/update blog for a location"
		 * , true, true, true, true, true, true,false,AppPermission.CREATE_BLOG,
		 * AppPermission.UPDATE_BLOG);
		 * 
		 * createRoleWithPermissions("BlogEditorRole",
		 * "User of this role will be able to create/update/approve and publish blog for a location"
		 * , true, true, true, true, true, true,false,AppPermission.CREATE_BLOG,
		 * AppPermission.UPDATE_BLOG, AppPermission.APPROVE_BLOG);
		 * 
		 * createRoleWithPermissions("BlogApproverRole",
		 * "User of this role will be able to approve/publish existing blog for a location"
		 * , true, true, true, true, true,
		 * true,false,AppPermission.APPROVE_BLOG);
		 * 
		 * //Poll createRoleWithPermissions("PollAdminRole",
		 * "User of this role will be able to create/update/Approve/delete poll for a location"
		 * , true, true, true, true, true, true,false,AppPermission.CREATE_POLL,
		 * AppPermission.UPDATE_POLL, AppPermission.DELETE_POLL,
		 * AppPermission.APPROVE_POLL);
		 * 
		 * createRoleWithPermissions("PollReporterRole",
		 * "User of this role will be able to create/update poll for a location"
		 * , true, true, true, true, true, true,false,AppPermission.CREATE_POLL,
		 * AppPermission.UPDATE_POLL);
		 * 
		 * createRoleWithPermissions("PollEditorRole",
		 * "User of this role will be able to create/update/approve and publish poll for a location"
		 * , true, true, true, true, true, true,false,AppPermission.CREATE_POLL,
		 * AppPermission.UPDATE_POLL, AppPermission.APPROVE_POLL);
		 * 
		 * createRoleWithPermissions("PollApproverRole",
		 * "User of this role will be able to approve/publish existing poll for a location"
		 * , true, true, true, true, true,
		 * true,false,AppPermission.APPROVE_POLL);
		 * 
		 * createRoleWithPermissions("Treasury",
		 * "User of this role will be able to do all Treasury operation of a location"
		 * , true, true, true, true, false, false,false,AppPermission.TREASURY);
		 * createRoleWithPermissions("OfficeAdmin",
		 * "User of this role will be able to do all Office related operation of a location, i.e. editing Office address,contact information etc"
		 * , true, true, true, true, true,
		 * true,false,AppPermission.EDIT_OFFICE_ADDRESS);
		 */
		createRoleWithPermissions("SmsSender", "User of this role will be able to send SMS to all people in his/her location", true, true, true, true, true,
				true, false, AppPermission.ADMIN_SMS);
		createRoleWithPermissions("EmailSender", "User of this role will be able to send EMAIL to all people in his/her location", true, true, true, true,
				true, true, false, AppPermission.ADMIN_EMAIL);

		logger.info("All Roles and permissions are created");
	}

	private void createRoleWithPermissions(String name, String description, boolean addStateRoles, boolean addDistrictRoles, boolean addAcRoles,
			boolean addPcRoles, boolean addCountryRole, boolean addCountryRegionRole, boolean addCuntryRegionAreaRole, AppPermission... appPermissions) {
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
		if (addCountryRole) {
			List<Country> countries = countryDao.getAllCountries();
			CountryRole oneCountryRole;
			for (Country oneCountry : countries) {
				if (oneCountry.getName().equals("India")) {
					continue;
				}
				oneCountryRole = countryRoleDao.getCountryRoleByCountryIdAndRoleId(oneCountry.getId(), role.getId());
				if (oneCountryRole == null) {
					oneCountryRole = new CountryRole();
					oneCountryRole.setCountry(oneCountry);
					oneCountryRole.setRole(role);
					oneCountryRole = countryRoleDao.saveCountryRole(oneCountryRole);
				}

			}
		}

		if (addCountryRegionRole) {
			List<CountryRegion> countryRegions = countryRegionDao.getAllCountryRegions();
			CountryRegionRole oneCountryRegionRole;
			for (CountryRegion oneCountryRegion : countryRegions) {
				oneCountryRegionRole = countryRegionRoleDao.getCountryRegionRoleByCountryRegionIdAndRoleId(oneCountryRegion.getId(), role.getId());
				if (oneCountryRegionRole == null) {
					oneCountryRegionRole = new CountryRegionRole();
					oneCountryRegionRole.setCountryRegion(oneCountryRegion);
					oneCountryRegionRole.setRole(role);
					oneCountryRegionRole = countryRegionRoleDao.saveCountryRegionRole(oneCountryRegionRole);
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

		Set<CountryRole> countryRoles = user.getCountryRoles();
		if (countryRoles != null && !countryRoles.isEmpty()) {
			for (CountryRole oneCountryRole : countryRoles) {
				if (!oneCountryRole.getRole().getPermissions().isEmpty()) {
					userRolePermissionDto.addCountryPermissions(convertCountry(oneCountryRole.getCountry()), convertPermissionToAppPermission(oneCountryRole
							.getRole().getPermissions()));
				}
			}
		}

		Set<CountryRegionRole> countryRegionRoles = user.getCountryRegionRoles();
		if (countryRoles != null && !countryRoles.isEmpty()) {
			for (CountryRegionRole oneCountryRegionRole : countryRegionRoles) {
				if (!oneCountryRegionRole.getRole().getPermissions().isEmpty()) {
					userRolePermissionDto.addCountryRegionPermissions(convertCountryRegion(oneCountryRegionRole.getCountryRegion()),
							convertPermissionToAppPermission(oneCountryRegionRole.getRole().getPermissions()));
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
	@Cacheable("location")
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
		case COUNTRY:
			facebookAccounts = facebookAccountDao.getCountryFacebookAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		case REGION:
			facebookAccounts = facebookAccountDao.getCountryRegionFacebookAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		case AREA:
			facebookAccounts = facebookAccountDao.getCountryRegionAreaFacebookAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
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
		case COUNTRY:
			twitterAccounts = twitterAccountDao.getCountryTwitterAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		case REGION:
			twitterAccounts = twitterAccountDao.getCountryRegionTwitterAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
			break;
		case AREA:
			twitterAccounts = twitterAccountDao.getCountryRegionAreaTwitterAccountsForVoiceOfAapToPublishOnTimeLine(locationId);
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
		case COUNTRY:
			if (news.getCountries() == null) {
				news.setCountries(new ArrayList<Country>());
			}
			Country country = countryDao.getCountryById(locationId);
			news.getCountries().add(country);
			break;
		case REGION:
			if (news.getCountryRegions() == null) {
				news.setCountryRegions(new ArrayList<CountryRegion>());
			}
			CountryRegion countryRegion = countryRegionDao.getCountryRegionById(locationId);
			news.getCountryRegions().add(countryRegion);
			break;
		case AREA:
			if (news.getCountryRegionsAreas() == null) {
				news.setCountryRegionsAreas(new ArrayList<CountryRegionArea>());
			}
			CountryRegionArea countryRegionArea = countryRegionAreaDao.getCountryRegionAreaById(locationId);
			news.getCountryRegionsAreas().add(countryRegionArea);
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
		case COUNTRY:
			news = newsDao.getCountryNews(locationId);
			break;
		case REGION:
			news = newsDao.getCountryRegionNews(locationId);
			break;
		case AREA:
			news = newsDao.getCountryRegionAreaNews(locationId);
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
		if (searchMemberResult == null) {
			searchMemberResult = new SearchMemberResultDto();
		} else {
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
	@Cacheable("location")
	public DistrictDto getDistrictByNameAndStateId(String name, Long stateId) {
		District district = districtDao.getDistrictByNameAndStateId(stateId, name);
		return convertDistrict(district);
	}

	@Override
	@Transactional
	@Cacheable("location")
	public DistrictDto getDistrictById(Long districtId) {
		District district = districtDao.getDistrictById(districtId);
		return convertDistrict(district);
	}

	@Override
	@Transactional
	@Cacheable("location")
	public AssemblyConstituencyDto getAssemblyConstituencyById(long stateId) {
		AssemblyConstituency assemblyConstituency = assemblyConstituencyDao.getAssemblyConstituencyById(stateId);
		return convertAssemblyConstituency(assemblyConstituency);
	}

	@Override
	@Transactional
	@Cacheable("location")
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
		case COUNTRY:
			roles = roleDao.getUserCountryRoles(userId, locationId);
			break;
		case REGION:
			roles = roleDao.getUserCountryRegionRoles(userId, locationId);
			break;
		case AREA:
			roles = roleDao.getUserCountryRegionAreaRoles(userId, locationId);
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
		case COUNTRY:
			roles = roleDao.getCountryRoles(locationId);
			break;
		case REGION:
			roles = roleDao.getCountryRegionRoles(locationId);
			break;
		case AREA:
			roles = roleDao.getCountryRegionAreaRoles(locationId);
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
		case COUNTRY: {
			Set<CountryRole> allCountryRoles = user.getCountryRoles();
			if (allCountryRoles != null && !allCountryRoles.isEmpty()) {
				Iterator<CountryRole> iterator = allCountryRoles.iterator();
				CountryRole oneCountryRole;
				while (iterator.hasNext()) {
					oneCountryRole = iterator.next();
					if (oneCountryRole.getCountryId().equals(locationId)) {
						iterator.remove();
					}
				}
			}
		}
			break;
		case REGION: {
			Set<CountryRegionRole> allCountryRegionRoles = user.getCountryRegionRoles();
			if (allCountryRegionRoles != null && !allCountryRegionRoles.isEmpty()) {
				Iterator<CountryRegionRole> iterator = allCountryRegionRoles.iterator();
				CountryRegionRole oneCountryRegionRole;
				while (iterator.hasNext()) {
					oneCountryRegionRole = iterator.next();
					if (oneCountryRegionRole.getCountryRegionId().equals(locationId)) {
						iterator.remove();
					}
				}
			}
		}
		case AREA: {
			Set<CountryRegionAreaRole> allCountryRegionRoles = user.getCountryRegionAreaRoles();
			if (allCountryRegionRoles != null && !allCountryRegionRoles.isEmpty()) {
				Iterator<CountryRegionAreaRole> iterator = allCountryRegionRoles.iterator();
				CountryRegionAreaRole oneCountryRegionAreaRole;
				while (iterator.hasNext()) {
					oneCountryRegionAreaRole = iterator.next();
					if (oneCountryRegionAreaRole.getCountryRegionAreaId().equals(locationId)) {
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
		case COUNTRY: {
			if (userRoleDtos != null && !userRoleDtos.isEmpty()) {
				Set<CountryRole> allNewCountryRoles = new HashSet<>();
				CountryRole oneCountryRole;
				for (RoleDto oneRoleDto : userRoleDtos) {
					oneCountryRole = countryRoleDao.getCountryRoleByCountryIdAndRoleId(locationId, oneRoleDto.getId());
					allNewCountryRoles.add(oneCountryRole);
				}
				user.getCountryRoles().addAll(allNewCountryRoles);
			}
		}
			break;
		case REGION: {
			if (userRoleDtos != null && !userRoleDtos.isEmpty()) {
				Set<CountryRegionRole> allNewCountryRoles = new HashSet<>();
				CountryRegionRole oneCountryRegionRole;
				for (RoleDto oneRoleDto : userRoleDtos) {
					oneCountryRegionRole = countryRegionRoleDao.getCountryRegionRoleByCountryRegionIdAndRoleId(locationId, oneRoleDto.getId());
					allNewCountryRoles.add(oneCountryRegionRole);
				}
				user.getCountryRegionRoles().addAll(allNewCountryRoles);
			}
		}
			break;
		case AREA:
			/*
			 * { if (userRoleDtos != null && !userRoleDtos.isEmpty()) {
			 * Set<CountryRegionAreaRole> allNewCountryRegionAreaRoles = new
			 * HashSet<>(); CountryRegionAreaRole oneCountryRegionAreaRole; for
			 * (RoleDto oneRoleDto : userRoleDtos) { oneCountryRegionAreaRole =
			 * countryRegionAreaRoleDao
			 * .getCountryRegionRoleByCountryRegionIdAndRoleId(locationId,
			 * oneRoleDto.getId());
			 * allNewCountryRegionAreaRoles.add(oneCountryRegionAreaRole); }
			 * user
			 * .getCountryRegionRoles().addAll(allNewCountryRegionAreaRoles); }
			 * } break;
			 */
			throw new RuntimeException("This has not been implemneted yet");
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
		case COUNTRY:
			if (blog.getCountries() == null) {
				blog.setCountries(new ArrayList<Country>());
			}
			Country country = countryDao.getCountryById(locationId);
			blog.getCountries().add(country);
			break;
		case REGION:
			if (blog.getCountryRegions() == null) {
				blog.setCountryRegions(new ArrayList<CountryRegion>());
			}
			CountryRegion countryRegion = countryRegionDao.getCountryRegionById(locationId);
			blog.getCountryRegions().add(countryRegion);
			break;
		case AREA:
			if (blog.getCountryRegionAreas() == null) {
				blog.setCountryRegionAreas(new ArrayList<CountryRegionArea>());
			}
			CountryRegionArea countryRegionArea = countryRegionAreaDao.getCountryRegionAreaById(locationId);
			blog.getCountryRegionAreas().add(countryRegionArea);
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
		case COUNTRY:
			blog = blogDao.getCountryBlog(locationId);
			break;
		case REGION:
			blog = blogDao.getCountryRegionBlog(locationId);
			break;
		case AREA:
			blog = blogDao.getCountryRegionAreaBlog(locationId);
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
		case COUNTRY:
			if (pollQuestion.getCountries() == null) {
				pollQuestion.setCountries(new ArrayList<Country>());
			}
			Country country = countryDao.getCountryById(locationId);
			pollQuestion.getCountries().add(country);
			break;
		case REGION:
			if (pollQuestion.getCountryRegions() == null) {
				pollQuestion.setCountryRegions(new ArrayList<CountryRegion>());
			}
			CountryRegion countryRegion = countryRegionDao.getCountryRegionById(locationId);
			pollQuestion.getCountryRegions().add(countryRegion);
			break;
		case AREA:
			if (pollQuestion.getCountryRegionAreas() == null) {
				pollQuestion.setCountryRegionAreas(new ArrayList<CountryRegionArea>());
			}
			CountryRegionArea countryRegionArea = countryRegionAreaDao.getCountryRegionAreaById(locationId);
			pollQuestion.getCountryRegionAreas().add(countryRegionArea);
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

	private PollQuestionDto convertPollQuestion(PollQuestion pollQuestion) {
		if (pollQuestion == null) {
			return null;
		}
		PollQuestionDto pollQuestionDto = new PollQuestionDto();
		BeanUtils.copyProperties(pollQuestion, pollQuestionDto);
		return pollQuestionDto;
	}

	private List<PollQuestionDto> convertPollQuestions(List<PollQuestion> pollQuestions) {
		List<PollQuestionDto> pollQuestionDtos = new ArrayList<>();
		if (pollQuestions == null) {
			return pollQuestionDtos;
		}
		for (PollQuestion onePollQuestion : pollQuestions) {
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
		case COUNTRY:
			pollQuestions = pollQuestionDao.getCountryPollQuestion(locationId);
			break;
		case REGION:
			pollQuestions = pollQuestionDao.getCountryRegionPollQuestion(locationId);
			break;
		case AREA:
			pollQuestions = pollQuestionDao.getCountryRegionAreaPollQuestion(locationId);
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

	private PollAnswerDto convertPollAnswer(PollAnswer pollAnswer) {
		if (pollAnswer == null) {
			return null;
		}
		PollAnswerDto pollAnswerDto = new PollAnswerDto();
		BeanUtils.copyProperties(pollAnswer, pollAnswerDto);
		return pollAnswerDto;
	}

	private List<PollAnswerDto> convertPollAnswers(Collection<PollAnswer> pollAnswers) {
		List<PollAnswerDto> pollAnswerDtos = new ArrayList<>();
		if (pollAnswers == null) {
			return pollAnswerDtos;
		}
		for (PollAnswer onePollAnswer : pollAnswers) {
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

	private Account getAdminAccount(User adminUser) {
		Account account = accountDao.getAdminAccountByUserId(adminUser.getId());
		if (account == null) {
			account = new Account();
			account.setAccountOwner(adminUser);
			account.setAccountType(AccountType.Admin);
			account.setBalance(0.0);
			account.setDescription("Admin account for " + adminUser.getMembershipNumber());
			account.setDateCreated(new Date());
			account.setDateModified(new Date());
			account.setGlobal(false);
			account = accountDao.saveAccount(account);
		}
		return account;
	}

	private Account getTreasuryCashAccount(PostLocationType locationType, Long locationId) {
		return getTreasuryAccount(locationType, locationId, AccountType.TreasuryCash);
	}

	private Account getTreasuryBankAccount(PostLocationType locationType, Long locationId) {
		return getTreasuryAccount(locationType, locationId, AccountType.TreasuryBank);
	}

	private Account getTreasuryAccount(PostLocationType locationType, Long locationId, AccountType accountType) {
		Account account = null;
		switch (locationType) {
		case Global:
			account = accountDao.getGlobalTreasuryAccount(accountType);
			break;
		case STATE:
			account = accountDao.getStateTreasuryAccount(locationId, accountType);
			break;
		case DISTRICT:
			account = accountDao.getDistrictTreasuryAccount(locationId, accountType);
			break;
		case AC:
			account = accountDao.getAcTreasuryAccount(locationId, accountType);
			break;
		case PC:
			account = accountDao.getPcTreasuryAccount(locationId, accountType);
			break;

		default:
			break;
		}
		if (account == null) {
			account = new Account();
			account.setAccountType(accountType);
			account.setBalance(0.0);
			if (locationType == PostLocationType.Global) {
				account.setDescription("National Treasury account");
				account.setGlobal(true);
			} else {
				account.setDescription("Treasury account for " + locationType + " locationId");
				account.setGlobal(false);
			}

			account.setDateCreated(new Date());
			account.setDateModified(new Date());

			account = accountDao.saveAccount(account);
		}
		return account;
	}

	private void addAccountTransaction(Account account, double amount, User adminUser, Date now, String description,
			AccountTransactionMode accountTransactionMode, AccountTransactionType accountTransactionType) {
		AccountTransaction accountTransaction = new AccountTransaction();
		accountTransaction.setAccount(account);
		accountTransaction.setAccountTransactionMode(accountTransactionMode);
		accountTransaction.setAccountTransactionType(accountTransactionType);
		accountTransaction.setAmount(amount);
		if (accountTransactionType == AccountTransactionType.Credit) {
			accountTransaction.setBalance(account.getBalance() + amount);
		} else {
			accountTransaction.setBalance(account.getBalance() - amount);
		}
		accountTransaction.setDateCreated(now);
		accountTransaction.setDateModified(now);
		accountTransaction.setCreatorId(adminUser.getId());
		accountTransaction.setModifierId(adminUser.getId());
		accountTransaction.setDescription(description);
		accountTransaction.setTransactionDate(now);
		accountTransaction = accountTransactionDao.saveAccountTransaction(accountTransaction);

		if (accountTransactionType == AccountTransactionType.Credit) {
			account.setBalance(account.getBalance() + amount);
		} else {
			account.setBalance(account.getBalance() - amount);
		}

	}

	@Override
	@Transactional
	public UserDto receiveMembershipFee(Long userId, double amount, Long adminUserId) {

		if (amount < DataUtil.MEMBERSHIP_FEE) {
			throw new RuntimeException("Amount must be more then " + DataUtil.MEMBERSHIP_FEE + " Rs");
		}
		User user = userDao.getUserById(userId);
		User adminUser = userDao.getUserById(adminUserId);

		// First COnfirm Membership
		user.setMembershipStatus("Confirmed");
		user.setMembershipConfirmedBy(adminUser);
		user = userDao.saveUser(user);

		// Now handle accounting
		Account adminAccount = getAdminAccount(adminUser);
		// create Membership transaction under this account
		Date now = new Date();

		addAccountTransaction(adminAccount, DataUtil.MEMBERSHIP_FEE, adminUser, now,
				"Membership fee for " + user.getMembershipNumber() + " , " + user.getName(), AccountTransactionMode.Cash, AccountTransactionType.Credit);

		if (amount > DataUtil.MEMBERSHIP_FEE) {
			double donationAmount = amount - DataUtil.MEMBERSHIP_FEE;
			// TODO create a donation entry, will be done later

			addAccountTransaction(adminAccount, donationAmount, adminUser, now, "Donation by " + user.getMembershipNumber() + " , " + user.getName(),
					AccountTransactionMode.Cash, AccountTransactionType.Credit);
		}

		// now update overall account balance
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
		if (admins != null) {
			accounts = accountDao.getAccountsByUserId(admins);
		}
		return convertAdminAccounts(accounts);
	}

	private AdminAccountDto convertAdminAccount(Account oneAccount) {
		if (oneAccount == null) {
			return null;
		}
		AdminAccountDto adminAccountDto = new AdminAccountDto();
		BeanUtils.copyProperties(oneAccount, adminAccountDto);
		adminAccountDto.setAccountOwnerDto(convertUser(oneAccount.getAccountOwner()));
		return adminAccountDto;
	}

	private List<AdminAccountDto> convertAdminAccounts(List<Account> accounts) {
		List<AdminAccountDto> adminAccountDtos = new ArrayList<>();
		if (accounts == null) {
			return null;
		}
		for (Account oneAccount : accounts) {
			adminAccountDtos.add(convertAdminAccount(oneAccount));
		}
		return adminAccountDtos;
	}

	@Override
	@Transactional
	public List<OfficeDto> getLocationOffices(PostLocationType locationType, Long locationId) {
		List<Office> offices = null;
		switch (locationType) {
		case Global:
			offices = officeDao.getNationalOffices();
			break;
		case STATE:
			offices = officeDao.getStateOffices(locationId);
			break;
		case DISTRICT:
			offices = officeDao.getDistrictOffices(locationId);
			break;
		case AC:
			offices = officeDao.getAcOffices(locationId);
			break;
		case PC:
			offices = officeDao.getPcOffices(locationId);
			break;
		case COUNTRY:
			offices = officeDao.getCountryOffices(locationId);
			break;
		case REGION:
			offices = officeDao.getCountryRegionOffices(locationId);
			break;
		case AREA:
			offices = officeDao.getCountryRegionAreaOffices(locationId);
			break;
		default:
		}
		return convertOffices(offices);
	}

	private OfficeDto convertOffice(Office office) {
		if (office == null) {
			return null;
		}
		OfficeDto officeDto = new OfficeDto();
		BeanUtils.copyProperties(office, officeDto);
		return officeDto;
	}

	private List<OfficeDto> convertOffices(Collection<Office> offices) {
		List<OfficeDto> returnList = new ArrayList<>();
		if (offices == null) {
			return returnList;
		}
		for (Office oneOffice : offices) {
			returnList.add(convertOffice(oneOffice));
		}
		return returnList;
	}

	@Override
	@Transactional
	public OfficeDto saveOffice(OfficeDto officeDto) {
		Office office = null;
		if (officeDto.getId() == null || officeDto.getId() <= 0) {
			office = new Office();
			office.setDateCreated(new Date());
		} else {
			office = officeDao.getOfficeById(officeDto.getId());
		}
		office.setAddress(officeDto.getAddress());
		office.setDateModified(new Date());
		office.setDepth(officeDto.getDepth());
		office.setFbGroupId(officeDto.getFbGroupId());
		office.setFbPageId(officeDto.getFbPageId());
		office.setLandlineNumber1(officeDto.getLandlineNumber1());
		office.setLandlineNumber2(officeDto.getLandlineNumber2());
		office.setLattitude(officeDto.getLattitude());
		office.setLongitude(officeDto.getLongitude());
		office.setMobileNumber1(officeDto.getMobileNumber1());
		office.setMobileNumber2(officeDto.getMobileNumber2());
		office.setNational(officeDto.isNational());
		office.setOtherInformation(officeDto.getOtherInformation());
		office.setTwitterHandle(officeDto.getTwitterHandle());

		if (officeDto.getAssemblyConstituencyId() != null && officeDto.getAssemblyConstituencyId() > 0) {
			AssemblyConstituency assemblyConstituency = assemblyConstituencyDao.getAssemblyConstituencyById(officeDto.getAssemblyConstituencyId());
			office.setAssemblyConstituency(assemblyConstituency);
			office.setAssemblyConstituencyId(officeDto.getAssemblyConstituencyId());
		}
		if (officeDto.getParliamentConstituencyId() != null && officeDto.getParliamentConstituencyId() > 0) {
			ParliamentConstituency parliamentConstituency = parliamentConstituencyDao.getParliamentConstituencyById(officeDto.getParliamentConstituencyId());
			office.setParliamentConstituency(parliamentConstituency);
			office.setParliamentConstituencyId(officeDto.getParliamentConstituencyId());
		}
		if (officeDto.getDistrictId() != null && officeDto.getDistrictId() > 0) {
			District district = districtDao.getDistrictById(officeDto.getDistrictId());
			office.setDistrict(district);
			office.setDistrictId(officeDto.getDistrictId());
		}
		if (officeDto.getStateId() != null && officeDto.getStateId() > 0) {
			State state = stateDao.getStateById(officeDto.getStateId());
			office.setState(state);
			office.setStateId(officeDto.getStateId());
		}
		if (officeDto.getCountryId() != null && officeDto.getCountryId() > 0) {
			Country country = countryDao.getCountryById(officeDto.getCountryId());
			office.setCountry(country);
			office.setCountryId(officeDto.getCountryId());
		}
		if (officeDto.getCountryRegionId() != null && officeDto.getCountryRegionId() > 0) {
			CountryRegion countryRegion = countryRegionDao.getCountryRegionById(officeDto.getCountryRegionId());
			office.setCountryRegion(countryRegion);
			office.setCountryRegionId(officeDto.getCountryRegionId());
		}
		if (officeDto.getCountryRegionAreaId() != null && officeDto.getCountryRegionAreaId() > 0) {
			CountryRegionArea countryRegionArea = countryRegionAreaDao.getCountryRegionAreaById(officeDto.getCountryRegionAreaId());
			office.setCountryRegionArea(countryRegionArea);
			office.setCountryRegionAreaId(officeDto.getCountryRegionAreaId());
		}

		office = officeDao.saveOffice(office);

		return convertOffice(office);
	}

	@Override
	@Transactional
	public void receiveMoneyIntoTreasuryAccount(PostLocationType locationType, Long locationId, Long treasuryUserId, double amount, Long adminUserId) {
		User adminUser = userDao.getUserById(adminUserId);
		User treasuryUser = userDao.getUserById(treasuryUserId);

		Account debitAdminAccount = getAdminAccount(adminUser);
		Account creditTreasuryAccount = getTreasuryCashAccount(locationType, locationId);

		Date now = new Date();

		addAccountTransaction(debitAdminAccount, amount, adminUser, now, "Money transfered to treasury account, receiver : " + treasuryUser.getName() + "["
				+ treasuryUser.getMembershipNumber() + "]", AccountTransactionMode.Cash, AccountTransactionType.Debit);

		addAccountTransaction(creditTreasuryAccount, amount, treasuryUser, now,
				"Money received from : " + adminUser.getName() + "[" + adminUser.getMembershipNumber() + "]", AccountTransactionMode.Cash,
				AccountTransactionType.Credit);
	}

	@Override
	@Transactional
	public List<AccountTransactionDto> getAccountTransactions(long accountId) {
		List<AccountTransaction> accountTransactions = accountTransactionDao.getAccountTransactionsByAccountId(accountId);
		return convertAccountTransactions(accountTransactions);
	}

	private AccountTransactionDto convertAccountTransaction(AccountTransaction accountTransaction) {
		if (accountTransaction == null) {
			return null;
		}
		AccountTransactionDto accountTransactionDto = new AccountTransactionDto();
		BeanUtils.copyProperties(accountTransaction, accountTransactionDto);
		return accountTransactionDto;
	}

	private List<AccountTransactionDto> convertAccountTransactions(List<AccountTransaction> accountTransactions) {
		List<AccountTransactionDto> accountTransactionDtos = new ArrayList<>();
		if (accountTransactions == null) {
			return accountTransactionDtos;
		}

		for (AccountTransaction oneAccountTransaction : accountTransactions) {
			accountTransactionDtos.add(convertAccountTransaction(oneAccountTransaction));
		}
		return accountTransactionDtos;
	}

	@Override
	@Transactional
	public List<AccountTransactionDto> getTreasuryCashAccountTransactions(PostLocationType locationType, Long locationId) {
		Account cashAccount = getTreasuryCashAccount(locationType, locationId);
		return getAccountTransactions(cashAccount.getId());
	}

	@Override
	@Transactional
	public List<AccountTransactionDto> getTreasuryBankAccountTransactions(PostLocationType locationType, Long locationId) {
		Account cashAccount = getTreasuryBankAccount(locationType, locationId);
		return getAccountTransactions(cashAccount.getId());
	}

	@Override
	@Transactional
	public void importAllCountriesData() {

		try {
			CSVReader csvReader = new CSVReader(new FileReader(new File("/Users/ravi/Downloads/forCSV.txt")));
			List<String[]> allPcs = csvReader.readAll();
			int totalCountries = 0;
			int totalCountriesRegion = 0;
			int totalCountriesRegionArea = 0;
			Set<String> missingStates = new TreeSet<String>();
			String countryName;
			String prevCountryName = "";
			Map<String, String> countryMap = new HashMap<String, String>();
			countryMap.put("The Bahamas", "Bahamas");
			countryMap.put("Brunei Darussalam", "Brunei");
			countryMap.put("Burma", "Burma (Myanmar)");
			countryMap.put("Congo, Democratic Republic of the", "Democratic Republic of the Congo");
			countryMap.put("Democratic Republic of the", "Democratic Republic of the Congo");
			countryMap.put("Republic of the", "Republic of the Congo");
			countryMap.put("Congo, Republic of the", "Republic of the Congo");
			countryMap.put("Cote d'Ivoire", "Ivory Coast");
			countryMap.put("French Guiana", "Guinea");
			countryMap.put("The Gambia", "Gambia");
			countryMap.put("Guadeloupe", "CONTINUE");
			countryMap.put("Guernsey", "CONTINUE");
			countryMap.put("Hong Kong (SAR)", "Hong Kong");
			countryMap.put("Korea, North", "North Korea");
			countryMap.put("Korea, South", "South Korea");
			countryMap.put("Macao", "Macau");
			countryMap.put("Macedonia, The Former Yugoslav Republic of", "Macedonia");
			countryMap.put("Man, Isle of", "Isle of Man");
			countryMap.put("Martinique", "CONTINUE");
			countryMap.put("Micronesia, Federated States of", "Micronesia");
			countryMap.put("RTunion", "CONTINUE");
			countryMap.put("Spo TomT", "CONTINUE");
			countryMap.put("Spo TomT and Prfncipe", "CONTINUE");
			countryMap.put("Virgin Islands", "US Virgin Islands");
			countryMap.put("Yugoslavia", "CONTINUE");
			countryMap.put("India", "CONTINUE");
			countryMap.put("Palestinian Territory, Occupied", "CONTINUE");

			String countryRegionName;
			String countryRegionAreaName;
			Country country = null;
			int counter = 0;
			for (String[] oneCountryRow : allPcs) {
				counter++;
				countryName = oneCountryRow[0].trim();
				countryRegionName = oneCountryRow[1].trim();
				countryRegionAreaName = oneCountryRow[2].trim();
				if (countryName.equals("") || countryRegionName.equals("") || countryRegionAreaName.equals("")) {
					continue;
				}

				if (countryMap.get(countryName) != null) {
					countryName = countryMap.get(countryName);
				}
				if ("CONTINUE".equals(countryName)) {
					// System.out.println("Country CONTINUE "+onePcRow[0]
					// +" NOT FOUND");
					continue;
				}

				if (!countryName.equals(prevCountryName)) {
					totalCountries++;
					country = countryDao.getCountryByName(countryName);
				}
				prevCountryName = countryName;
				if (country == null) {
					System.out.println("ERROR : Country " + countryName + " NOT FOUND");
				}

				CountryRegion countryRegion = countryRegionDao.getCountryRegionByNameAndCountryId(country.getId(), countryRegionName);
				if (countryRegion == null) {
					System.out.println("Creating CountryRegion = " + countryRegionName);
					countryRegion = new CountryRegion();
					countryRegion.setName(countryRegionName);
					countryRegion.setCountry(country);
					countryRegion = countryRegionDao.saveCountryRegion(countryRegion);
				}

				CountryRegionArea countryRegionArea = countryRegionAreaDao.getCountryRegionAreaByNameAndCountryRegionId(countryRegion.getId(),
						countryRegionAreaName);
				if (countryRegionArea == null) {
					System.out.println("Creating CountryRegionArea = " + countryRegionAreaName);
					countryRegionArea = new CountryRegionArea();
					countryRegionArea.setName(countryRegionAreaName);
					countryRegionArea.setCountryRegion(countryRegion);
					countryRegionArea = countryRegionAreaDao.saveCountryRegionArea(countryRegionArea);
				}
				System.out.println(counter + " : Total Countries = " + totalCountries + "," + countryName + "," + prevCountryName);
			}
			System.out.println("Total Rows = " + allPcs.size());
			System.out.println("Total Pc = " + totalCountries);
			System.out.println("Total Missing States = " + missingStates.size());
			for (String oneStateName : missingStates) {
				System.out.println("     " + oneStateName);
			}
			csvReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	@Transactional
	@Cacheable("location")
	public List<CountryRegionDto> getAllCountryRegionsOfCountry(Long countryId) {
		List<CountryRegion> countryRegions = countryRegionDao.getCountryRegionsByCountryId(countryId);
		return convertCountryRegions(countryRegions);
	}

	private CountryRegionDto convertCountryRegion(CountryRegion countryRegion) {
		if (countryRegion == null) {
			return null;
		}
		CountryRegionDto countryRegionDto = new CountryRegionDto();
		BeanUtils.copyProperties(countryRegion, countryRegionDto);
		return countryRegionDto;
	}

	private List<CountryRegionDto> convertCountryRegions(List<CountryRegion> countryRegions) {
		List<CountryRegionDto> returnCountryRegions = new ArrayList<>();
		if (countryRegions == null) {
			return returnCountryRegions;
		}
		for (CountryRegion oneCountryRegion : countryRegions) {
			returnCountryRegions.add(convertCountryRegion(oneCountryRegion));
		}
		return returnCountryRegions;
	}

	@Override
	@Transactional
	@Cacheable("location")
	public List<CountryRegionAreaDto> getAllCountryRegionAreasOfCountryRegion(Long countryRegionId) {
		List<CountryRegionArea> countryRegionAreas = countryRegionAreaDao.getCountryRegionAreasByCountryRegionId(countryRegionId);
		return convertCountryRegionAreas(countryRegionAreas);
	}

	private CountryRegionAreaDto convertCountryRegionArea(CountryRegionArea countryRegionArea) {
		if (countryRegionArea == null) {
			return null;
		}
		CountryRegionAreaDto countryRegionAreaDto = new CountryRegionAreaDto();
		BeanUtils.copyProperties(countryRegionArea, countryRegionAreaDto);
		return countryRegionAreaDto;
	}

	private List<CountryRegionAreaDto> convertCountryRegionAreas(List<CountryRegionArea> countryRegionAreas) {
		List<CountryRegionAreaDto> returnCountryRegionAreas = new ArrayList<>();
		if (countryRegionAreas == null) {
			return returnCountryRegionAreas;
		}
		for (CountryRegionArea oneCountryRegionArea : countryRegionAreas) {
			returnCountryRegionAreas.add(convertCountryRegionArea(oneCountryRegionArea));
		}
		return returnCountryRegionAreas;
	}

	@Override
	@Transactional
	public List<InterestGroupDto> getAllVolunterInterests() {
		List<InterestGroup> interestGroups = interestGroupDao.getAllInterestGroups();
		return convertInterestGroups(interestGroups);
	}

	private InterestDto convertInterest(Interest interest) {
		if (interest == null) {
			return null;
		}
		InterestDto interestDto = new InterestDto();
		BeanUtils.copyProperties(interest, interestDto);
		return interestDto;
	}

	private List<InterestDto> convertInterests(Collection<Interest> interests) {
		List<InterestDto> returnInterests = new ArrayList<>();
		if (interests == null) {
			return returnInterests;
		}
		for (Interest interest : interests) {
			returnInterests.add(convertInterest(interest));
		}
		return returnInterests;
	}

	private InterestGroupDto convertInterestGroup(InterestGroup interestGroup) {
		if (interestGroup == null) {
			return null;
		}
		InterestGroupDto interestGroupDto = new InterestGroupDto();
		BeanUtils.copyProperties(interestGroup, interestGroupDto);
		interestGroupDto.setInterestDtos(convertInterests(interestGroup.getInterests()));
		return interestGroupDto;
	}

	private List<InterestGroupDto> convertInterestGroups(List<InterestGroup> interestGroups) {
		List<InterestGroupDto> returnInterestGroups = new ArrayList<>();
		if (interestGroups == null) {
			return returnInterestGroups;
		}
		for (InterestGroup interestGroup : interestGroups) {
			returnInterestGroups.add(convertInterestGroup(interestGroup));
		}
		return returnInterestGroups;
	}

	private PlannedSmsDto convertPlannedSms(PlannedSms plannedSms) {
		if (plannedSms == null) {
			return null;
		}
		PlannedSmsDto plannedSmsDto = new PlannedSmsDto();
		BeanUtils.copyProperties(plannedSms, plannedSmsDto);
		return plannedSmsDto;
	}

	private List<PlannedSmsDto> convertPlannedSmss(List<PlannedSms> plannedSmss) {
		if (plannedSmss == null) {
			return null;
		}
		List<PlannedSmsDto> plannedSmsDtos = new ArrayList<>(plannedSmss.size());
		for (PlannedSms onePlannedSms : plannedSmss) {
			plannedSmsDtos.add(convertPlannedSms(onePlannedSms));
		}
		return plannedSmsDtos;
	}

	@Override
	@Transactional
	public PlannedSmsDto savePlannedSms(PlannedSmsDto plannedSmsDto) {
		PlannedSms plannedSms = null;
		if (plannedSmsDto.getId() != null && plannedSmsDto.getId() > 0) {
			plannedSms = plannedSmsDao.getPlannedSmsById(plannedSmsDto.getId());
			if (plannedSms == null) {
				throw new RuntimeException("No such Sms found[id=" + plannedSmsDto.getId() + "]");
			}
		} else {
			plannedSms = new PlannedSms();
			plannedSms.setDateCreated(new Date());
			plannedSms.setStatus(PlannedPostStatus.PENDING);
		}
		plannedSms.setMessage(plannedSmsDto.getMessage());
		plannedSms.setPostingTime(plannedSmsDto.getPostingTime());
		plannedSms.setLocationType(plannedSmsDto.getLocationType());
		plannedSms.setLocationId(plannedSmsDto.getLocationId());
		plannedSms = plannedSmsDao.savePlannedSms(plannedSms);

		return convertPlannedSms(plannedSms);
	}

	@Override
	@Transactional
	public PlannedSmsDto updatePlannedSmsStatus(Long plannedSmsId, PlannedPostStatus status, String errorMessage) {
		PlannedSms plannedSms = plannedSmsDao.getPlannedSmsById(plannedSmsId);
		plannedSms.setStatus(status);
		plannedSms.setErrorMessage(errorMessage);
		plannedSms = plannedSmsDao.savePlannedSms(plannedSms);
		return convertPlannedSms(plannedSms);
	}

	@Override
	@Transactional
	public List<PlannedSmsDto> getPlannedSmssForLocation(PostLocationType locationType, Long locationId, int pageNumber, int pageSize) {
		List<PlannedSms> plannedSmses = plannedSmsDao.getPlannedSmsByLocationTypeAndLocationId(locationType, locationId);
		return convertPlannedSmss(plannedSmses);
	}

	private PlannedEmailDto convertPlannedEmail(PlannedEmail plannedEmail) {
		if (plannedEmail == null) {
			return null;
		}
		PlannedEmailDto plannedEmailDto = new PlannedEmailDto();
		BeanUtils.copyProperties(plannedEmail, plannedEmailDto);
		return plannedEmailDto;
	}

	private List<PlannedEmailDto> convertPlannedEmails(List<PlannedEmail> plannedEmails) {
		if (plannedEmails == null) {
			return null;
		}
		List<PlannedEmailDto> plannedEmailDtos = new ArrayList<>(plannedEmails.size());
		for (PlannedEmail onePlannedEmail : plannedEmails) {
			plannedEmailDtos.add(convertPlannedEmail(onePlannedEmail));
		}
		return plannedEmailDtos;
	}

	@Override
	@Transactional
	public PlannedEmailDto savePlannedEmail(PlannedEmailDto plannedEmailDto) {
		PlannedEmail plannedEmail = null;
		if (plannedEmailDto.getId() != null && plannedEmailDto.getId() > 0) {
			plannedEmail = plannedEmailDao.getPlannedEmailById(plannedEmailDto.getId());
			if (plannedEmail == null) {
				throw new RuntimeException("No such Email found[id=" + plannedEmailDto.getId() + "]");
			}
		} else {
			plannedEmail = new PlannedEmail();
			plannedEmail.setDateCreated(new Date());
			plannedEmail.setStatus(PlannedPostStatus.PENDING);
		}
		plannedEmail.setMessage(plannedEmailDto.getMessage());
		plannedEmail.setSubject(plannedEmailDto.getSubject());
		plannedEmail.setPostingTime(plannedEmailDto.getPostingTime());
		plannedEmail.setLocationType(plannedEmailDto.getLocationType());
		plannedEmail.setLocationId(plannedEmailDto.getLocationId());
		plannedEmail = plannedEmailDao.savePlannedEmail(plannedEmail);

		return convertPlannedEmail(plannedEmail);
	}

	@Override
	@Transactional
	public PlannedEmailDto updatePlannedEmailStatus(Long plannedEmailId, PlannedPostStatus status, String message) {
		PlannedEmail plannedEmail = plannedEmailDao.getPlannedEmailById(plannedEmailId);
		plannedEmail.setStatus(status);
		plannedEmail.setErrorMessage(message);
		plannedEmail = plannedEmailDao.savePlannedEmail(plannedEmail);
		return convertPlannedEmail(plannedEmail);
	}

	@Override
	@Transactional
	public List<PlannedEmailDto> getPlannedEmailsForLocation(PostLocationType locationType, Long locationId, int pageNumber, int pageSize) {
		List<PlannedEmail> plannedEmails = plannedEmailDao.getPlannedEmailByLocationTypeAndLocationId(locationType, locationId);
		return convertPlannedEmails(plannedEmails);
	}

	@Override
	@Transactional
	public VolunteerDto saveVolunteerDetails(VolunteerDto volunteerDto, List<Long> selectedInterests) throws AppException {
		if (volunteerDto.getUserId() == null || volunteerDto.getUserId() <= 0) {
			throw new AppException("No User id found");
		}
		User user = userDao.getUserById(volunteerDto.getUserId());
		if (user == null) {
			throw new AppException("No User found");
		}

		Volunteer volunteer = volunteerDao.getVolunteersByUserId(user.getId());
		if (volunteer == null) {
			volunteer = new Volunteer();
			volunteer.setDateCreated(new Date());
		}
		volunteer.setDateModified(new Date());
		volunteer.setDomainExpertise(volunteerDto.getDomainExpertise());
		volunteer.setEducation(volunteerDto.getEducation());
		volunteer.setEmergencyContactName(volunteerDto.getEmergencyContactName());
		volunteer.setEmergencyContactNo(volunteerDto.getEmergencyContactNo());
		volunteer.setEmergencyContactRelation(volunteerDto.getEmergencyContactRelation());
		volunteer.setInfoRecordedAt(volunteerDto.getInfoRecordedAt());
		volunteer.setInfoRecordedBy(volunteerDto.getInfoRecordedBy());
		volunteer.setOffences(volunteerDto.getOffences());
		volunteer.setProfessionalBackground(volunteerDto.getProfessionalBackground());
		volunteer.setUser(user);
		volunteer.setUserId(user.getId());

		volunteer = volunteerDao.saveVolunteer(volunteer);

		user.setVolunteer(true);

		if (volunteer.getInterests() == null) {
			volunteer.setInterests(new HashSet<Interest>());
		} else {
			volunteer.getInterests().clear();
		}
		for (Long oneInterestId : selectedInterests) {
			Interest oneInterest = interestDao.getInterestById(oneInterestId);
			volunteer.getInterests().add(oneInterest);
		}

		return convertVolunteer(volunteer);
	}

	private VolunteerDto convertVolunteer(Volunteer volunteer) {
		if (volunteer == null) {
			return null;
		}
		VolunteerDto volunteerDto = new VolunteerDto();
		BeanUtils.copyProperties(volunteer, volunteerDto);
		return volunteerDto;
	}

	@Override
	@Transactional
	public VolunteerDto getVolunteerDataForUser(Long userId) throws AppException {
		Volunteer volunteer = volunteerDao.getVolunteersByUserId(userId);
		return convertVolunteer(volunteer);
	}

	@Override
	@Transactional
	public List<InterestDto> getuserInterests(Long userId) throws AppException {
		Volunteer volunteer = volunteerDao.getVolunteersByUserId(userId);
		if (volunteer == null) {
			return null;
		}
		return convertInterests(volunteer.getInterests());
	}

	@Override
	@Transactional
	public UserDto saveUserFromAdmiPanel(UserDto userDto, VolunteerDto volunteerDto, List<Long> interests) throws AppException {
		userDto = saveUser(userDto);
		volunteerDto.setUserId(userDto.getId());
		saveVolunteerDetails(volunteerDto, interests);
		return userDto;
	}

	@Override
	@Transactional
	public PlannedEmailDto getNextPlannedEmailToSend() {
		PlannedEmail plannedEmail = plannedEmailDao.getNextPlannedEmailToPublish();
		return convertPlannedEmail(plannedEmail);
	}

	@Override
	@Transactional
	public List<EmailUserDto> getEmailsOfLocation(PostLocationType locationType, Long locationId) throws AppException {
		List<Email> emails = null;
		switch (locationType) {
		case Global:
			// emails = emailDao.getStateEmails(locationId);
			break;
		case STATE:
			emails = emailDao.getStateEmails(locationId);
			break;
		case DISTRICT:
			emails = emailDao.getDistrictEmails(locationId);
			break;
		case AC:
			emails = emailDao.getAcEmails(locationId);
			break;
		case PC:
			emails = emailDao.getPcEmails(locationId);
			break;
		case COUNTRY:
			emails = emailDao.getCountryEmails(locationId);
			break;
		case REGION:
			emails = emailDao.getCountryRegionEmails(locationId);
			break;
		case AREA:
			emails = emailDao.getCountryRegionAreaEmails(locationId);
			break;
		default:
		}
		return convertEmailUsers(emails);
	}

	private EmailUserDto convertEmailUser(Email oneEmail) {
		if (oneEmail == null) {
			return null;
		}
		EmailUserDto emailUserDto = new EmailUserDto();
		BeanUtils.copyProperties(oneEmail, emailUserDto);
		return emailUserDto;
	}

	private List<EmailUserDto> convertEmailUsers(List<Email> emails) {
		List<EmailUserDto> emailUserDtos = new ArrayList<>();
		if (emails == null) {
			return emailUserDtos;
		}
		for (Email oneEmail : emails) {
			emailUserDtos.add(convertEmailUser(oneEmail));
		}
		return emailUserDtos;
	}

	// 07 Jan 2013 17:55:41:917
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:S");

	@Override
	@Transactional
	public int importDonationRecords(int totalRecords) {

		List<DonationDump> donations = donationDao.getDonationsToImport(totalRecords);

		String emailId = null;
		String donorId = null;

		Email email;
		Phone phone;
		
		int totalDonations = 0;
		for (DonationDump oneDonation : donations) {
			try {
				//System.out.println("Doantion Data " + oneDonation.getId()+"," + oneDonation.getDonorId()+" , "+oneDonation.getTransactionId() +" , "+oneDonation.getStatus());
				totalDonations++;
				User phoneUser = null;
				User emailUser = null;
				User mainUser = null;
				emailId = oneDonation.getDonorEmail();
				donorId = oneDonation.getDonorId();

				email = getDbEmail(emailId, oneDonation);
				
				if(email != null){
					emailUser = email.getUser();
				}
				phone = getDbPhone(oneDonation, emailUser);
				if(email == null && phone == null){
					donationDao.updateDonationStatus(oneDonation.getDonorId(), "DataError", "No Email or Phone Number found");
					continue;
				}
				
				//Both email and Phone number are provided
				if(email != null && phone != null){
					//Try to merge user or error out this Record
					emailUser = email.getUser();
					phoneUser = phone.getUser();
					if(emailUser.getId().equals(phoneUser.getId())){
						//nothing to merge they are same user
						mainUser = emailUser;
					}else{
						//Merge these users
						try{
							mergeUser(emailUser, phoneUser);
							mainUser = emailUser;
						}catch(AppException ex){
							donationDao.updateDonationStatus(oneDonation.getDonorId(), "DataError", ex.getMessage());
							continue;
						}catch(Exception ex){
							donationDao.updateDonationStatus(oneDonation.getDonorId(), "DataError", ex.getMessage());
							continue;
						}
					}
				}
				if(email != null && phone == null){
					mainUser = email.getUser();
				}
				if(email == null && phone != null){
					mainUser = phone.getUser();
				}
				if(mainUser == null){
					donationDao.updateDonationStatus(oneDonation.getDonorId(), "CodeError", "No Main User found");
				}
				System.out.println("oneDonation.getDonationDate()="+oneDonation.getDonationDate());
				Date donationDate = null;
				if(!StringUtil.isEmpty(oneDonation.getDonationDate())){
					donationDate = simpleDateFormat.parse(oneDonation.getDonationDate());	
				}
				
				System.out.println("donationDate="+donationDate);
				//Update User State
				updateUserLocation(mainUser, oneDonation.getDonorCountryId(), oneDonation.getDonorStateId());
				
				updateUserAddress(mainUser, oneDonation);
				
				updateUserName(mainUser, oneDonation);
				
				updateUserDob(mainUser, oneDonation, donationDate);
				
				
				
				
				Donation donation = donationDao.getDonationByDonorId(donorId.toString());
				if(donation == null){
					donation = new Donation();
					donation.setUser(mainUser);
					donation.setAmount(oneDonation.getAmount());
					donation.setCid(oneDonation.getCid());
					donation.setDonationDate(donationDate);
					donation.setDateCreated(donationDate);
					donation.setDateModified(donationDate);
					donation.setDonorId(donorId.toString());

					donation.setDonorIp(oneDonation.getDonorIp());
					donation.setMerchantReferenceNumber(oneDonation.getMerchantReferenceNumber());

					donation.setPaymentGateway(oneDonation.getPaymentGateway());
					donation.setPgErrorDetail(oneDonation.getPgErrorDetail());
					donation.setPgErrorMessage(oneDonation.getPgErrorMessage());
					donation.setRemark(oneDonation.getRemark());
					donation.setTransactionId(oneDonation.getTransactionId());
					donation.setTransactionType(oneDonation.getTransactionType());
					donation.setUtmCampaign(oneDonation.getUtmCampaign());
					donation.setUtmContent(oneDonation.getUtmContent());
					donation.setUtmMedium(oneDonation.getUtmMedium());
					donation.setUtmSource(oneDonation.getUtmSource());
					donation.setUtmTerm(oneDonation.getUtmTerm());

					donation = donationDao.saveDonation(donation);
				}
				//System.out.println(oneDonation[0] + ", " + oneDonation[1] + " , " + oneDonation[oneDonation.length - 2]);
				donationDao.updateDonationStatus(oneDonation.getDonorId(), "Imported", "");
			} catch (Exception ex) {
				ex.printStackTrace();
				donationDao.updateDonationStatus(oneDonation.getDonorId(), "DataError", ex.getMessage());
			}
		}
		return totalDonations;
	}
		
	private void updateUserName(User mainUser,DonationDump oneDonation){
		try{
			if(mainUser.getName().indexOf("@") >= 0){
				mainUser.setName(oneDonation.getDonorName());
			}
		}catch(Exception ex){
			//Ignore exception
		}
	}
	private void updateUserDob(User mainUser,DonationDump oneDonation, Date donationDate){
		try{
			System.out.println("mainUser.getDateOfBirth() == null ="+mainUser.getDateOfBirth() == null +", donationDate="+donationDate);
			if(mainUser.getDateOfBirth() == null && donationDate != null){
				//try to guess date of birth using Donation Record
				//atleast we will have year right
				if(oneDonation.getDonorAge() == null){
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.YEAR, 0 - oneDonation.getDonorAge());
					mainUser.setDateOfBirth(calendar.getTime());
				}
			}
		}catch(Exception ex){
			//Ignore exception
		}
	}
	private void updateUserAddress(User mainUser,DonationDump oneDonation){
		try{
			if(StringUtil.isEmpty(mainUser.getAddress())){
				mainUser.setAddress(oneDonation.getDonorAddress());
			}
		}catch(Exception ex){
			//Ignore exception
		}
	}
	private Email getDbEmail(String emailId, DonationDump oneDonation){
		if (StringUtil.isEmpty(emailId)) {
			return null;
		}

		Email email = emailDao.getEmailByEmail(emailId);
		User user;

		if (email == null) {
			// means create new User and email
			user = createUserFromDonationData(oneDonation);

			email = new Email();
			email.setEmail(emailId);
			email.setConfirmationType(ConfirmationType.DONOR_ENTERED);
			email.setConfirmed(true);
			email.setUser(user);
			email.setDateCreated(new Date());

			email = emailDao.saveEmail(email);
		} else {
			user = email.getUser();
		}
		return email;
	}
	private User createUserFromDonationData(DonationDump oneDonation){
		User user = new User();
		user.setAddress(oneDonation.getDonorAddress());
		if (!StringUtil.isEmpty(oneDonation.getDonorGender())) {
			if ("F".equals(oneDonation.getDonorGender())) {
				user.setGender("Female");
			}
			if ("M".equals(oneDonation.getDonorGender())) {
				user.setGender("Male");
			}
			if ("O".equals(oneDonation.getDonorGender())) {
				user.setGender("NotSpecified");
			}
			if (user.getGender() == null) {
				user.setGender("NotSpecified");
			}
		}
		user.setDonor(true);
		user.setName(oneDonation.getDonorName());
		user.setCreationType(CreationType.Imported);
		user.setDateCreated(new Date());
		user = userDao.saveUser(user);
		return user;
	}
	
	private Phone getDbPhone(DonationDump oneDonation, User user){
		String phoneNumber = oneDonation.getDonorMobile();
		if (StringUtil.isEmpty(phoneNumber)) {
			return null;
		}
		String countryName = countryCodeMap.get(oneDonation.getDonorCountryId());
		if(countryName == null){
			return null;
		}
		Country country = countryDao.getCountryByName(countryName);
		if(country == null){
			return null;
		}
		phoneNumber = removeCountryCode(phoneNumber, country.getIsdCode());
		Phone phone = phoneDao.getPhoneByPhone(phoneNumber, country.getIsdCode());

		if (phone == null) {
			// means create new User and email
			if(user == null){
				user = createUserFromDonationData(oneDonation);
			}
			
			phone = new Phone();
			phone.setCountryCode(country.getIsdCode());
			phone.setPhoneNumber(phoneNumber);
			phone.setPhoneType(PhoneType.MOBILE);
			phone.setUser(user);
			phone.setDateCreated(new Date());
			phone.setDateModified(new Date());

			phone = phoneDao.savePhone(phone);
		} 
		return phone;
	}
	private String removeCountryCode(String phoneNumber,String countryCode){
		phoneNumber = phoneNumber.replace("+", "");
		if(phoneNumber.indexOf(countryCode) == 0){
			phoneNumber = phoneNumber.substring(countryCode.length());
		}
		return phoneNumber;
	}
	
	private void updateUserLocation(User user, String countryId, String stateId){
		try{
			if(StringUtil.isEmpty(countryId)){
				return;
			}
			if("IN".equalsIgnoreCase(countryId)){
				//Update indian State
				if(StringUtil.isEmpty(stateId)){
					return;
				}
				State state = null;
				if("AN".equals(stateId)){
					state = stateDao.getStateByName("Andaman and Nicobar");
				}
				if("AP".equals(stateId)){
					state = stateDao.getStateByName("Andhra Pradesh");
				}
				if("AR".equals(stateId)){
					state = stateDao.getStateByName("Arunachal Pradesh");
				}
				if("AS".equals(stateId)){
					state = stateDao.getStateByName("Assam");
				}
				if("BR".equals(stateId)){
					state = stateDao.getStateByName("Bihar");
				}
				if("CN".equals(stateId)){
					state = stateDao.getStateByName("Chandigarh");
				}
				if("CG".equals(stateId)){
					state = stateDao.getStateByName("Chhattisgarh");
				}
				if("DN".equals(stateId)){
					state = stateDao.getStateByName("Dadra and Nagar Haveli(UT)");
				}
				if("DD".equals(stateId)){
					state = stateDao.getStateByName("Daman and Diu(UT)");
				}
				if("DL".equals(stateId)){
					state = stateDao.getStateByName("Delhi");
				}
				if("GO".equals(stateId)){
					state = stateDao.getStateByName("Goa");
				}
				if("GJ".equals(stateId)){
					state = stateDao.getStateByName("Gujarat");
				}
				if("HR".equals(stateId)){
					state = stateDao.getStateByName("Haryana");
				}
				if("HP".equals(stateId)){
					state = stateDao.getStateByName("Himachal Pradesh");
				}
				if("JK".equals(stateId)){
					state = stateDao.getStateByName("Jammu and Kashmir");
				}
				if("JH".equals(stateId)){
					state = stateDao.getStateByName("Jharkhand");
				}
				if("KA".equals(stateId)){
					state = stateDao.getStateByName("Karnataka");
				}
				if("KL".equals(stateId)){
					state = stateDao.getStateByName("Kerala");
				}
				if("LD".equals(stateId)){
					state = stateDao.getStateByName("Lakshadweep");
				}
				if("MP".equals(stateId)){
					state = stateDao.getStateByName("Madhya Pradesh");
				}
				if("MH".equals(stateId)){
					state = stateDao.getStateByName("Maharashtra");
				}
				if("MN".equals(stateId)){
					state = stateDao.getStateByName("Manipur");
				}
				if("ML".equals(stateId)){
					state = stateDao.getStateByName("Meghalaya");
				}
				if("MZ".equals(stateId)){
					state = stateDao.getStateByName("Mizoram");
				}
				if("NG".equals(stateId)){
					state = stateDao.getStateByName("Nagaland");
				}
				if("OR".equals(stateId)){
					state = stateDao.getStateByName("Odisha");
				}
				if("PY".equals(stateId)){
					state = stateDao.getStateByName("Puducherry");
				}
				if("PB".equals(stateId)){
					state = stateDao.getStateByName("Punjab");
				}
				if("RJ".equals(stateId)){
					state = stateDao.getStateByName("Rajasthan");
				}
				if("SK".equals(stateId)){
					state = stateDao.getStateByName("Sikkim");
				}
				if("TN".equals(stateId)){
					state = stateDao.getStateByName("Tamil Nadu");
				}
				if("TR".equals(stateId)){
					state = stateDao.getStateByName("Tripura");
				}
				if("UP".equals(stateId)){
					state = stateDao.getStateByName("Uttar Pradesh");
				}
				if("UA".equals(stateId)){
					state = stateDao.getStateByName("Uttarakhand");
				}
				if("WB".equals(stateId)){
					state = stateDao.getStateByName("West Bengal");
				}
				if(state != null){
					user.setStateLiving(state);
					user.setStateVoting(state);
				}
				
				
			}else{
				//Update NRI Location
				if(user.getNriCountry() == null){
					user.setNri(true);
					Country country = null;
					String countryName = countryCodeMap.get(countryId);
					if(countryName != null){
						country = countryDao.getCountryByName(countryName);
						user.setNriCountry(country);
					}
				}
				/*
				 * Not Found
				<option value="BV">BOUVET ISLAND</option>
				<option value="CI">COTE D&#39;IVOIRE</option>
				<option value="TP">EAST TIMOR</option>
				<option value="FX">FRANCE, METROPOLITAN</option>
				<option value="GF">FRENCH GUIANA</option>
				<option value="TF">FRENCH SOUTHERN TERRITORIES</option>
				Gaza Strip
				<option value="GP">GUADELOUPE</option>
				<option value="HM">HEARD AND MC DONALD ISLANDS</option>
				Ivory Coast
				<option value="MQ">MARTINIQUE</option>
				<option value="RE">REUNION</option>
				<option value="GS">SOUTH GEORGIA AND SOUTH S.S.</option>
				<option value="SS">SOUTH SUDAN</option>
				<option value="UM">U.S. MINOR ISLANDS</option>
				*/
			}
		}catch(Exception ex){
			//ignore exception
		}
		
	}

}
