package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.FacebookAccount;
import com.next.aap.core.persistance.dao.FacebookAccountDao;

@Component
public class FacebookAccountDaoHibernateSpringImpl extends BaseDaoHibernateSpring<FacebookAccount> implements FacebookAccountDao{


	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FacebookAccountDao#saveFacebookAccount(com.next.aap.server.persistance.FacebookAccount)
	 */
	@Override
	public FacebookAccount saveFacebookAccount(FacebookAccount facebookAccount){
		facebookAccount = super.saveObject(facebookAccount);
		return facebookAccount;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FacebookAccountDao#getFacebookAccountById(java.lang.Long)
	 */
	@Override
	public FacebookAccount getFacebookAccountById(Long id) {
		return super.getObjectById(FacebookAccount.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FacebookAccountDao#getFacebookAccountByUserId(java.lang.Long)
	 */
	@Override
	public FacebookAccount getFacebookAccountByUserId(Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		FacebookAccount facebookAccount = executeQueryGetObject("from FacebookAccount where userId = :userId", params);
		return facebookAccount;
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FacebookAccountDao#getFacebookAccountByUserName(java.lang.String)
	 */
	@Override
	public FacebookAccount getFacebookAccountByUserName(String userName){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userName", userName);
		FacebookAccount facebookAccount = executeQueryGetObject("from FacebookAccount where userName = :userName", params);
		return facebookAccount;
	}
	
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FacebookAccountDao#getFacebookAccountsAfterId(java.lang.Long, int)
	 */
	@Override
	public List<FacebookAccount> getFacebookAccountsAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		List<FacebookAccount> list = executeQueryGetList("from FacebookAccount where id > :lastId order by id asc", params, pageSize);
		return list;
	}

	@Override
	public FacebookAccount getFacebookAccountByFacebookUserId(String facebookUserId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("facebookUserId", facebookUserId);
		FacebookAccount facebookAccount = executeQueryGetObject("from FacebookAccount where facebookUserId = :facebookUserId", params);
		return facebookAccount;
	}

	@Override
	public List<FacebookAccount> getAllFacebookAccountsForVoiceOfAapToPublishOnTimeLine() {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("voiceOfAap", true);
		params.put("allowTimeLine", true);
		List<FacebookAccount> list = executeQueryGetList("from FacebookAccount where voiceOfAap = :voiceOfAap and allowTimeLine = :allowTimeLine", params);
		return list;
	}

	@Override
	public List<FacebookAccount> getStateFacebookAccountsForVoiceOfAapToPublishOnTimeLine(Long stateId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("voiceOfAap", true);
		params.put("allowTimeLine", true);
		params.put("stateId", stateId);
		List<FacebookAccount> list = executeQueryGetList("from FacebookAccount where voiceOfAap = :voiceOfAap and allowTimeLine = :allowTimeLine and (user.stateLivingId = :stateId or user.stateVotingId = :stateId)", params);
		return list;
	}

	@Override
	public List<FacebookAccount> getDistrictFacebookAccountsForVoiceOfAapToPublishOnTimeLine(Long districtId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("voiceOfAap", true);
		params.put("allowTimeLine", true);
		params.put("districtId", districtId);
		List<FacebookAccount> list = executeQueryGetList("from FacebookAccount where voiceOfAap = :voiceOfAap and allowTimeLine = :allowTimeLine and (user.districtLivingId = :districtId or user.districtVotingId = :districtId)", params);
		return list;
	}

	@Override
	public List<FacebookAccount> getAcFacebookAccountsForVoiceOfAapToPublishOnTimeLine(Long acId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("voiceOfAap", true);
		params.put("allowTimeLine", true);
		params.put("acId", acId);
		List<FacebookAccount> list = executeQueryGetList("from FacebookAccount where voiceOfAap = :voiceOfAap and allowTimeLine = :allowTimeLine and (user.assemblyConstituencyLivingId = :acId or user.assemblyConstituencyVotingId = :acId)");
		return list;
	}

	@Override
	public List<FacebookAccount> getPcFacebookAccountsForVoiceOfAapToPublishOnTimeLine(Long pcId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("voiceOfAap", true);
		params.put("allowTimeLine", true);
		params.put("pcId", pcId);
		List<FacebookAccount> list = executeQueryGetList("from FacebookAccount where voiceOfAap = :voiceOfAap and allowTimeLine = :allowTimeLine and (user.parliamentConstituencyLivingId = :pcId or user.parliamentConstituencyVotingId = :pcId)", params);
		return list;
	}

	@Override
	public List<FacebookAccount> getCountryFacebookAccountsForVoiceOfAapToPublishOnTimeLine(Long countryId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("voiceOfAap", true);
		params.put("allowTimeLine", true);
		params.put("nriCountryId", countryId);
		List<FacebookAccount> list = executeQueryGetList("from FacebookAccount where nriCountryId = :nriCountryId and voiceOfAap = :voiceOfAap and allowTimeLine = :allowTimeLine", params);
		return list;
	}

	@Override
	public List<FacebookAccount> getCountryRegionFacebookAccountsForVoiceOfAapToPublishOnTimeLine(Long countryRegionId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("voiceOfAap", true);
		params.put("allowTimeLine", true);
		params.put("nriCountryRegionId", countryRegionId);
		List<FacebookAccount> list = executeQueryGetList("from FacebookAccount where nriCountryRegionId = :nriCountryRegionId and voiceOfAap = :voiceOfAap and allowTimeLine = :allowTimeLine", params);
		return list;
	}

	@Override
	public List<FacebookAccount> getCountryRegionAreaFacebookAccountsForVoiceOfAapToPublishOnTimeLine(Long countryRegionAreaId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("voiceOfAap", true);
		params.put("allowTimeLine", true);
		params.put("nriCountryRegionAreaId", countryRegionAreaId);
		List<FacebookAccount> list = executeQueryGetList("from FacebookAccount where nriCountryRegionAreaId = :nriCountryRegionAreaId and voiceOfAap = :voiceOfAap and allowTimeLine = :allowTimeLine", params);
		return list;
	}
}
