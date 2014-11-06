package com.next.aap.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;

import com.next.aap.core.exception.AppException;
import com.next.aap.core.persistance.DonationDump;
import com.next.aap.web.dto.AccountTransactionDto;
import com.next.aap.web.dto.AdminAccountDto;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.CandidateDto;
import com.next.aap.web.dto.ContentTweetDto;
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.DonationCampaignDto;
import com.next.aap.web.dto.DonationDto;
import com.next.aap.web.dto.ElectionDto;
import com.next.aap.web.dto.EmailUserDto;
import com.next.aap.web.dto.EventDto;
import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.FacebookGroupDto;
import com.next.aap.web.dto.FacebookPostDto;
import com.next.aap.web.dto.FinancialPlanningDto;
import com.next.aap.web.dto.GlobalCampaignDto;
import com.next.aap.web.dto.InterestDto;
import com.next.aap.web.dto.InterestGroupDto;
import com.next.aap.web.dto.LocationCampaignDto;
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
import com.next.aap.web.dto.VideoDto;
import com.next.aap.web.dto.VoiceOfAapData;
import com.next.aap.web.dto.VolunteerDto;

public interface AapService {

	UserDto saveFacebookUser(Long existingUserid, Connection<Facebook> connection, String facebookAppId) throws AppException;
	
	UserDto saveGoogleUser(Long existingUserid, Connection<Google> connection);
	
	UserDto saveTwitterUser(Long existingUserid, Connection<Twitter> connection);
	
	LoginAccountDto getUserLoginAccounts(Long userId);
	
	List<StateDto> getAllStates() ;
	
	List<CountryDto> getAllCountries() ;
	
	List<CountryDto> getNriCountries() ;
	
	List<CountryRegionDto> getAllCountryRegionsOfCountry(Long countryId);
	
	List<CountryRegionDto> getAllCountryRegions();
	
	List<CountryRegionAreaDto> getAllCountryRegionAreasOfCountryRegion(Long countryRegionId);
	
	List<CountryRegionAreaDto> getAllCountryRegionAreas();
	
	StateDto getStateById(Long stateId) ;
	
	List<DistrictDto> getAllDistrictOfState(long stateId) ;
	
	List<DistrictDto> getAllDistricts();
	
	DistrictDto getDistrictById(Long districtId) ;
	
	List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfDistrict(long districtId) ;
	
	List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfState(long stateId) ;
	
	AssemblyConstituencyDto getAssemblyConstituencyById(long acId) ;
	
	List<AssemblyConstituencyDto> getAllAssemblyConstituencies() ;
	
	List<ParliamentConstituencyDto> getAllParliamentConstituencies() ;
	

	List<ParliamentConstituencyDto> getAllParliamentConstituenciesOfState(long stateId) ;
	
	ParliamentConstituencyDto getParliamentConstituencyById(long pcId) ;
	
	UserDto saveUser(UserDto userDto) throws AppException ;
	
	UserDto saveUserFromAdmiPanel(UserDto userDto, VolunteerDto volunteerDto, List<Long> interests) throws AppException ;
	
	FacebookAppPermissionDto getFacebookPermission(long facebookAppId, long facebookAccountId);
	
	FacebookAppPermissionDto getVoiceOfAapFacebookPermission(long facebookAccountId);
	
	void saveVoiceOfAapSettings(Long facebookAccountId, boolean beVoiceOfAap,boolean postOnMyTimeLine,List<String> selectedGroups, List<String> selectedPages, boolean allowTweets);
	
	void saveFacebookAccountGroups(Long facebookAccountId, List<GroupMembership> userGroupMembership);
	
	VoiceOfAapData getVoiceOfAapSetting(Long facebookAcountId);
	
	void updateAllPermissionsAndRole();
	
	void saveAllCountries();
	
	PlannedFacebookPostDto savePlannedFacebookPost(PlannedFacebookPostDto plannedFacebookPostDto);
	
	PlannedFacebookPostDto updatePlannedFacebookPostStatus(Long plannedFacebookPostId, PlannedPostStatus status, String message, int totalSuccessTimeLines,
			int totalSuccessTimeLineFriends, int totalFailedTimeLines, int totalFailedTimeLineFriends);
	
	List<PlannedFacebookPostDto> getPlannedFacebookPosts(int pageNumber, int pageSize);
	
	List<PlannedFacebookPostDto> getPlannedFacebookPostsForLocation(PostLocationType locationType, Long locationId, int pageNumber, int pageSize);
	
	List<PlannedFacebookPostDto> getExecutedFacebookPostsForLocation(PostLocationType locationType, Long locationId, int pageNumber, int pageSize);
	
	UserRolePermissionDto getUserRolePermissions(Long userId);
	
	PlannedFacebookPostDto getNextPlannedFacebookPostToPublish();
	
	List<FacebookAccountDto> getAllFacebookAccountsForVoiceOfAap(PostLocationType locationType, Long locationId);
	
	FacebookPostDto getFacebookPostByPlannedPostIdAndFacebookAccountId(Long plannedFacebookPostId, Long facebookAccountId);
	
	FacebookPostDto saveFacebookPost(FacebookPostDto facebookPostDto);
	
	List<FacebookPostDto> getUserFacebookPosts(Long facebookAccountId);

	
	PlannedTweetDto savePlannedTweet(PlannedTweetDto plannedTweetDto);
	
	PlannedTweetDto updatePlannedTweetStatus(Long plannedTweetId, PlannedPostStatus status, String message, int totalSuccessTweet,
			int totalFailedTweet);
	
	List<PlannedTweetDto> getPlannedTweets(int pageNumber, int pageSize);
	
	List<PlannedTweetDto> getPlannedTweetsForLocation(PostLocationType locationType, Long locationId, int pageNumber, int pageSize);
	
	List<PlannedTweetDto> getExecutedTweetsForLocation(PostLocationType locationType, Long locationId, int pageNumber, int pageSize);
	
	PlannedTweetDto getNextPlannedTweetToPublish();

	TweetDto getTweetByPlannedTweetIdAndTwitterAccountId(Long plannedTweetId, Long twitterAccountId);
	
	TweetDto saveTweetPost(TweetDto tweetDto);
	
	List<TweetDto> getUserTweets(Long userId);
	
	List<TwitterAccountDto> getAllTwitterAccountsForVoiceOfAap(PostLocationType locationType, Long locationId);
	
	NewsDto saveNews(NewsDto newsDto, List<ContentTweetDto> contentTweetDtos, PostLocationType locationType, Long locationId);
	
	NewsDto publishNews(Long newsId);
	
	NewsDto rejectNews(Long newsId, String reason);
	
	NewsDto getNewsByOriginalUrl(String originalUrl);
	
	NewsDto getNewsById(Long newsId);
	
	List<NewsDto> getNews(PostLocationType locationType, Long locationId);
	
	List<NewsDto> getAllPublishedNews();
	
	BlogDto saveBlog(BlogDto blogDto, List<ContentTweetDto> contentTweetDtos, PostLocationType locationType, Long locationId);
	
	BlogDto publishBlog(Long newsId);
	
	BlogDto rejectBlog(Long newsId, String reason);
	
	BlogDto getBlogByOriginalUrl(String originalUrl);
	
	BlogDto getBlogById(Long blogId);
	
	List<BlogDto> getBlog(PostLocationType locationType, Long locationId);
	
	List<BlogDto> getAllPublishedBlogs();
	
	List<VideoDto> getAllPublishedVideos();
	
	List<PollQuestionDto> getAllPublishedPolls();
	
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
	
	PollQuestionDto rejectPollQuestion(Long pollQuestionId, String reason);
	
	PollQuestionDto getPollQuestionById(Long pollQuestionId);
	
	UserDto receiveMembershipFee(Long userId, double amount, Long adminUserId);
	
	List<AdminAccountDto> getAdminAccountDetails(PostLocationType locationType, Long locationId);
	
	List<OfficeDto> getLocationOffices(PostLocationType locationType, Long locationId);
	
	OfficeDto saveOffice(OfficeDto officeDto);
	
	void receiveMoneyIntoTreasuryAccount(PostLocationType locationType, Long locationId, Long treasuryUserId, double amount, Long adminUserId);
	
	List<AccountTransactionDto> getAccountTransactions(long accountId);
	
	
	List<AccountTransactionDto> getTreasuryCashAccountTransactions(PostLocationType locationType, Long locationId);
	
	List<AccountTransactionDto> getTreasuryBankAccountTransactions(PostLocationType locationType, Long locationId);
	
	void importAllCountriesData();
	
	List<InterestGroupDto> getAllVolunterInterests();
	
	PlannedSmsDto savePlannedSms(PlannedSmsDto plannedSmsDto);
	
	PlannedSmsDto updatePlannedSmsStatus(Long plannedSmsId, PlannedPostStatus status, String message);
	
	List<PlannedSmsDto> getPlannedSmssForLocation(PostLocationType locationType, Long locationId, int pageNumber, int pageSize);
	
	PlannedEmailDto savePlannedEmail(PlannedEmailDto plannedEmailDto);
	
	PlannedEmailDto updatePlannedEmailStatus(Long plannedEmailId, PlannedPostStatus status, String message);
	
	PlannedEmailDto getNextPlannedEmailToSend();
	
	List<PlannedEmailDto> getPlannedEmailsForLocation(PostLocationType locationType, Long locationId, int pageNumber, int pageSize);

	VolunteerDto saveVolunteerDetails(VolunteerDto volunteerDto, List<Long> selectedInterests) throws AppException;
	
	VolunteerDto getVolunteerDataForUser(Long userId) throws AppException;
	
	List<InterestDto> getuserInterests(Long userId) throws AppException;
	
	List<EmailUserDto> getEmailsOfLocation(PostLocationType locationType, Long locationId) throws AppException;
	
	int importDonationRecords(int totalRecords);
	
	List<DonationDto> getUserDonations(Long userId);
	
	List<DonationDto> getUserRippleDonations(Long userId);
	
	List<DonationDto> getUserFacebookDonations(Long userId);
	
	DonationDto getUserDonationByTxnid(String txnId);
	
	List<DonationCampaignDto> getUserCampaigns(Long userId);
	
	DonationCampaignDto getRippleDonationCamapign(Long userId);
	
	DonationCampaignDto getRippleDonationCamapignByCid(String cid);
	
	DonationCampaignDto saveRippleDonationCamapign(String campaignId, String description, Long userId) throws AppException ;
	
	List<Long> getAllNewsItemsOfAc(long acId);
	
	List<Long> getAllBlogItemsOfAc(long acId);
	
	List<Long> getAllVideoItemsOfAc(long acId);
	
	List<Long> getAllPollItemsOfAc(long acId);
	
	List<Long> getAllNewsItemsOfParliamentConstituency(long pcId);
	
	List<Long> getBlogItemsOfParliamentConstituency(long pcId);
	
	List<Long> getVideoItemsOfParliamentConstituency(long pcId);
	
	List<Long> getPollItemsOfParliamentConstituency(long acId);
	
	void saveDonationDump(DonationDump donationDump);

	VideoDto saveVideo(VideoDto videoItem);
	
	VideoDto getVideoByVideoId(String videoId);

	VideoDto publishVideo(Long videoId);
	
	VideoDto getVideoById(Long videoId);
	
	String savePollVote(Long userId, Long questionId, Long answerId, boolean async);
	
	void updatePollVoteAnswerTotalCount(Long answerId, Long existingAnswerId);
	
	LocationCampaignDto saveStateLocationCampaign(LocationCampaignDto locationCampaignDto, long stateId) throws AppException;
	
	LocationCampaignDto saveDistrictLocationCampaign(LocationCampaignDto locationCampaignDto, long districtId) throws AppException;
	
	LocationCampaignDto saveAcLocationCampaign(LocationCampaignDto locationCampaignDto, long acId) throws AppException;
	
	LocationCampaignDto savePcLocationCampaign(LocationCampaignDto locationCampaignDto, long pcId) throws AppException;
	
	LocationCampaignDto getDefaultStateLocationCampaign(long stateId) throws AppException;
	
	LocationCampaignDto getDefaultDistrictLocationCampaign(long districtId) throws AppException;
	
	LocationCampaignDto getDefaultAcLocationCampaign(long acId) throws AppException;
	
	LocationCampaignDto getDefaultPcLocationCampaign(long pcId) throws AppException;

	GlobalCampaignDto saveGlobalCampaign(GlobalCampaignDto globalCampaignDto) throws AppException;
	
	GlobalCampaignDto getGlobalCampaignByCid(String cid) throws AppException;
	
	List<GlobalCampaignDto> getGlobalCampaigns() throws AppException;

	List<DonationDto> getDonationsByCampaignId(String campaignId);
	
	DonationDto getDonationByTransactionId(String txnId);
	
	List<DonationDto> getDonationByEmailId(String txnId);
	
	EventDto saveEvent(EventDto eventDto,PostLocationType locationType, Long locationId) throws AppException;
	
	List<EventDto> getEventsOfLocation(PostLocationType locationType, Long locationId) throws AppException;
	
	List<EventDto> getFutureEventsOfLocation(PostLocationType locationType, Long locationId) throws AppException;
	
	List<EventDto> getAllFutureEvents() throws AppException;
	
	FinancialPlanningDto saveFinancialPlanning(FinancialPlanningDto financialPlanningDto) throws AppException;
	
	List<Long> getNewsItemsOfAc(long acId);
	
	List<Long> getNewsItemsOfPc(long pcId);
	
	List<Long> getNewsItemsOfDistrict(long districtId);
	
	List<Long> getNewsItemsOfState(long stateId);
	
	List<Long> getNewsItemsOfCountry(long countryId);
	
	List<Long> getNewsItemsOfCountryRegion(long countryRegionId);
	
	Map<Long, List<Long>> getNewsItemsOfAllAc();
	
	Map<Long, List<Long>> getNewsItemsOfAllPc();
	
	Map<Long, List<Long>> getNewsItemsOfAllDistrict();
	
	Map<Long, List<Long>> getNewsItemsOfAllState();
	
	Map<Long, List<Long>> getNewsItemsOfAllCountry();
	
	Map<Long, List<Long>> getNewsItemsOfAllCountryRegion();
	
	
	List<Long> getBlogItemsOfAc(long acId);
	
	List<Long> getBlogItemsOfPc(long pcId);
	
	List<Long> getBlogItemsOfDistrict(long districtId);
	
	List<Long> getBlogItemsOfState(long stateId);
	
	List<Long> getBlogItemsOfCountry(long countryId);
	
	List<Long> getBlogItemsOfCountryRegion(long countryRegionId);
	
	Map<Long, List<Long>> getBlogItemsOfAllAc();
	
	Map<Long, List<Long>> getBlogItemsOfAllPc();
	
	Map<Long, List<Long>> getBlogItemsOfAllDistrict();
	
	Map<Long, List<Long>> getBlogItemsOfAllState();
	
	Map<Long, List<Long>> getBlogItemsOfAllCountry();
	
	Map<Long, List<Long>> getBlogItemsOfAllCountryRegion();
	
	
	List<Long> getVideoItemsOfAc(long acId);
	
	List<Long> getVideoItemsOfPc(long pcId);
	
	List<Long> getVideoItemsOfDistrict(long districtId);
	
	List<Long> getVideoItemsOfState(long stateId);
	
	List<Long> getVideoItemsOfCountry(long countryId);
	
	List<Long> getVideoItemsOfCountryRegion(long countryRegionId);
	
	Map<Long, List<Long>> getVideoItemsOfAllAc();
	
	Map<Long, List<Long>> getVideoItemsOfAllPc();
	
	Map<Long, List<Long>> getVideoItemsOfAllDistrict();
	
	Map<Long, List<Long>> getVideoItemsOfAllState();
	
	Map<Long, List<Long>> getVideoItemsOfAllCountry();
	
	Map<Long, List<Long>> getVideoItemsOfAllCountryRegion();
	
	
	List<Long> getPollQuestionItemsOfAc(long acId);
	
	List<Long> getPollQuestionItemsOfPc(long pcId);
	
	List<Long> getPollQuestionItemsOfDistrict(long districtId);
	
	List<Long> getPollQuestionItemsOfState(long stateId);
	
	List<Long> getPollQuestionItemsOfCountry(long countryId);
	
	List<Long> getPollQuestionItemsOfCountryRegion(long countryRegionId);
	
	Map<Long, List<Long>> getPollQuestionItemsOfAllAc();
	
	Map<Long, List<Long>> getPollQuestionItemsOfAllPc();
	
	Map<Long, List<Long>> getPollQuestionItemsOfAllDistrict();
	
	Map<Long, List<Long>> getPollQuestionItemsOfAllState();
	
	Map<Long, List<Long>> getPollQuestionItemsOfAllCountry();
	
	Map<Long, List<Long>> getPollQuestionItemsOfAllCountryRegion();
	
	List<FacebookAccountDto> getFacebookAccounts(Long startId, int pageSize) throws AppException;
	
	List<FacebookAppPermissionDto> getAllFacebookAppPermissions(Long facebookAccountId) throws AppException;
	
	List<FacebookAppPermissionDto> getAllFacebookAppPermissions(Long startId, int pageSize) throws AppException;
	
	void updateFacebookAppPermissionExpiryTime(Long appPermissionId, Date expiryTime) throws AppException;
	
	void saveFacebookUserFriends(Long facebookAccountId, List<FacebookProfile> facebookProfiles, int totalFriends) throws AppException;
	
	Double getDayDonation(Date date) throws AppException;
	
	Double getMonthDonation(Date date) throws AppException;
	
	List<FacebookGroupDto> getAllFacebookGroupsWhereWeCanPost(Long lastGroupId, int pageSize) throws AppException;
	
	FacebookAccountDto getFacebookAccountToPostOnGroup(Long facebookGroupId) throws AppException;
	
	int updateFacebookGroupTotalMemberWithUs(Long lastGroupId, int pageSize);
	
	List<FacebookGroupDto> getFacebookGroups(Long lastGroupId, int pageSize);
	
	FacebookAppPermissionDto getFacebookPermissionForAGroup(long facebookGroupId);
	
	int updateFacebookGroupOverallTotalMembes(Long facebookGroupId, int totalMembers);
	
	CandidateDto saveCandidate(CandidateDto candidateDto) throws AppException;
	
	List<CandidateDto> getCandidates(int pageSize, int pageNumber) throws AppException;
	
    List<CandidateDto> getAllCandidatesOfElection(Long electionId) throws AppException;

    CandidateDto getCandidateByPcIdAndElectionId(Long pcId, Long electionId) throws AppException;

    CandidateDto getCandidateByAcIdAndElectionId(Long acId, Long electionId) throws AppException;
	
	void updateAllLocationCampaignInCache() throws AppException;
	
	UserDto getUserByid(Long userId) throws AppException;
	
	void updateLocationCampaignDetailInMemcache(String oneLocationCampaignId);

    ElectionDto saveElection(ElectionDto electionDto) throws AppException;

    List<ElectionDto> getAllElections() throws AppException;
}
