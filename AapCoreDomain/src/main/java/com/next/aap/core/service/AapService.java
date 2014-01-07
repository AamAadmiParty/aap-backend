package com.next.aap.core.service;

import java.util.List;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;

import com.next.aap.web.dto.AccountTransactionDto;
import com.next.aap.web.dto.AdminAccountDto;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.ContentTweetDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.FacebookPostDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.NewsDto;
import com.next.aap.web.dto.OfficeDto;
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

public interface AapService {

	UserDto saveFacebookUser(Long existingUserid, Connection<Facebook> connection, String facebookAppId);
	
	UserDto saveGoogleUser(Long existingUserid, Connection<Google> connection);
	
	UserDto saveTwitterUser(Long existingUserid, Connection<Twitter> connection);
	
	LoginAccountDto getUserLoginAccounts(Long userId);
	
	List<StateDto> getAllStates() ;
	
	List<CountryDto> getAllCountries() ;
	
	StateDto getStateById(Long stateId) ;
	
	List<DistrictDto> getAllDistrictOfState(long stateId) ;
	
	DistrictDto getDistrictById(Long districtId) ;
	
	List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfDistrict(long districtId) ;
	
	List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfState(long stateId) ;
	
	AssemblyConstituencyDto getAssemblyConstituencyById(long acId) ;

	List<ParliamentConstituencyDto> getAllParliamentConstituenciesOfState(long stateId) ;
	
	ParliamentConstituencyDto getParliamentConstituencyById(long pcId) ;
	
	UserDto saveUser(UserDto userDto);
	
	FacebookAppPermissionDto getFacebookPermission(long facebookAppId, long facebookAccountId);
	
	FacebookAppPermissionDto getVoiceOfAapFacebookPermission(long facebookAccountId);
	
	void saveVoiceOfAapSettings(Long facebookAccountId, boolean beVoiceOfAap,boolean postOnMyTimeLine,List<String> selectedGroups, List<String> selectedPages, boolean allowTweets);
	
	void saveFacebookAccountGroups(Long facebookAccountId, List<GroupMembership> userGroupMembership);
	
	VoiceOfAapData getVoiceOfAapSetting(Long facebookAcountId);
	
	void updateAllPermissionsAndRole();
	
	void saveAllCountries();
	
	PlannedFacebookPostDto savePlannedFacebookPost(PlannedFacebookPostDto plannedFacebookPostDto);
	
	PlannedFacebookPostDto updatePlannedFacebookPostStatus(Long plannedFacebookPostId, PlannedPostStatus status, String message);
	
	List<PlannedFacebookPostDto> getPlannedFacebookPosts(int pageNumber, int pageSize);
	
	List<PlannedFacebookPostDto> getPlannedFacebookPostsForLocation(PostLocationType locationType, Long locationId, int pageNumber, int pageSize);
	
	UserRolePermissionDto getUserRolePermissions(Long userId);
	
	PlannedFacebookPostDto getNextPlannedFacebookPostToPublish();
	
	List<FacebookAccountDto> getAllFacebookAccountsForVoiceOfAap(PostLocationType locationType, Long locationId);
	
	FacebookPostDto getFacebookPostByPlannedPostIdAndFacebookAccountId(Long plannedFacebookPostId, Long facebookAccountId);
	
	FacebookPostDto saveFacebookPost(FacebookPostDto facebookPostDto);
	
	List<FacebookPostDto> getUserFacebookPosts(Long facebookAccountId);

	
	PlannedTweetDto savePlannedTweet(PlannedTweetDto plannedTweetDto);
	
	PlannedTweetDto updatePlannedTweetStatus(Long plannedTweetId, PlannedPostStatus status, String message);
	
	List<PlannedTweetDto> getPlannedTweets(int pageNumber, int pageSize);
	
	List<PlannedTweetDto> getPlannedTweetsForLocation(PostLocationType locationType, Long locationId, int pageNumber, int pageSize);
	
	PlannedTweetDto getNextPlannedTweetToPublish();

	TweetDto getTweetByPlannedTweetIdAndTwitterAccountId(Long plannedTweetId, Long twitterAccountId);
	
	TweetDto saveTweetPost(TweetDto tweetDto);
	
	List<TweetDto> getUserTweets(Long userId);
	
	List<TwitterAccountDto> getAllTwitterAccountsForVoiceOfAap(PostLocationType locationType, Long locationId);
	
	NewsDto saveNews(NewsDto newsDto, List<ContentTweetDto> contentTweetDtos, PostLocationType locationType, Long locationId);
	
	NewsDto publishNews(Long newsId);
	
	List<NewsDto> getNews(PostLocationType locationType, Long locationId);
	
	BlogDto saveBlog(BlogDto blogDto, List<ContentTweetDto> contentTweetDtos, PostLocationType locationType, Long locationId);
	
	BlogDto publishBlog(Long newsId);
	
	List<BlogDto> getBlog(PostLocationType locationType, Long locationId);
	
	List<ContentTweetDto> getNewsContentTweets(Long newsId);
	
	List<ContentTweetDto> getBlogContentTweets(Long blogId);
	
	UserDto registerMember(UserDto userDto);
	
	SearchMemberResultDto searchMembers(UserDto searchUserDto);
	
	DistrictDto saveDistrict(DistrictDto districtWeb) ;
	
	DistrictDto getDistrictByNameAndStateId(String name, Long stateId) ;
	
	AssemblyConstituencyDto saveAssemblyConstituency(AssemblyConstituencyDto assemblyConstituencyWeb) ;
	
	List<RoleDto> getUserRoles(Long userId, PostLocationType postLocationType, Long locationId);
	
	List<RoleDto> getLocationRoles(PostLocationType postLocationType, Long locationId);
	
	void removeAllUserRolesAtLocation(Long userId, PostLocationType postLocationType, Long locationId);
	
	void saveUserRolesAtLocation(Long userId, PostLocationType postLocationType, Long locationId, List<RoleDto> userRoleDtos);
	
	PollQuestionDto savePollQuestion(PollQuestionDto blogDto, List<PollAnswerDto> pollAnswers, PostLocationType locationType, Long locationId);
	
	List<PollQuestionDto> getPollQuestion(PostLocationType locationType, Long locationId);
	
	List<PollAnswerDto> getPollAnswers(Long pollQuestionId);
	
	PollQuestionDto publishPollQuestion(Long pollQuestionId);
	
	UserDto receiveMembershipFee(Long userId, double amount, Long adminUserId);
	
	List<AdminAccountDto> getAdminAccountDetails(PostLocationType locationType, Long locationId);
	
	List<OfficeDto> getLocationOffices(PostLocationType locationType, Long locationId);
	
	OfficeDto saveOffice(OfficeDto officeDto);
	
	void receiveMoneyIntoTreasuryAccount(PostLocationType locationType, Long locationId, Long treasuryUserId, double amount, Long adminUserId);
	
	List<AccountTransactionDto> getAccountTransactions(long accountId);
	
	
	List<AccountTransactionDto> getTreasuryCashAccountTransactions(PostLocationType locationType, Long locationId);
	
	List<AccountTransactionDto> getTreasuryBankAccountTransactions(PostLocationType locationType, Long locationId);
	
	void importAllCountriesData();
}
