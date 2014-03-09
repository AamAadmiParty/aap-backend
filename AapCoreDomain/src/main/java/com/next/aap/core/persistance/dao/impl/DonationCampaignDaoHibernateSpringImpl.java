package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.DonationCampaign;
import com.next.aap.core.persistance.dao.DonationCampaignDao;
import com.next.aap.web.dto.DonationCampaignDto.CampaignType;

@Component
public class DonationCampaignDaoHibernateSpringImpl extends BaseDaoHibernateSpring<DonationCampaign> implements DonationCampaignDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DonationCampaignDao#saveDonationCampaign(com.next.aap.server.persistance.DonationCampaign)
	 */
	@Override
	public DonationCampaign saveDonationCampaign(DonationCampaign donationCampaign){
		checkIfStringMissing("CampaignId", donationCampaign.getCampaignId());
		donationCampaign.setCampaignIdUp(donationCampaign.getCampaignId().toUpperCase());
		donationCampaign = super.saveObject(donationCampaign);
		return donationCampaign;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DonationCampaignDao#getDonationCampaignById(java.lang.Long)
	 */
	@Override
	public DonationCampaign getDonationCampaignById(Long id) {
		return super.getObjectById(DonationCampaign.class, id);
	}
	@Override
	public DonationCampaign getDonationCampaignByDonationCampaign(String donationCampaignId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("donationCampaign", donationCampaignId.toUpperCase());
		DonationCampaign donationCampaign = executeQueryGetObject("from DonationCampaign where campaignIdUp = :donationCampaign", params);
		return donationCampaign;
	}

	@Override
	public List<DonationCampaign> getDonationCampaignsByUserId(Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		return executeQueryGetList("from DonationCampaign where userId = :userId", params);
	}

	@Override
	public DonationCampaign getDonationCampaignByTypeAndUserId(CampaignType campaignType, Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		params.put("campaignType", campaignType);
		return executeQueryGetObject("from DonationCampaign where userId = :userId and campaignType = :campaignType", params);
	}

	@Override
	public Object[] getOldDonationCampaignInfo(String facebookUserId) {
		String sqlQuery = "SELECT fb_user_id, email, cid, long_url, myaap_short_url, description from ripple_dump where fb_user_id = :facebookUserId";
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("facebookUserId", facebookUserId);
		List list = executeSqlQueryGetObjectList(sqlQuery, params);
		if(list == null || list.isEmpty()){
			return null;
		}
		return (Object[])list.get(0);
	}

	
}
