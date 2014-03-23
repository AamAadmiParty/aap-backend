package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.LocationCampaign;
import com.next.aap.core.persistance.dao.LocationCampaignDao;

@Component
public class LocationCampaignDaoHibernateSpringImpl extends BaseDaoHibernateSpring<LocationCampaign> implements LocationCampaignDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.LocationCampaignDao#saveLocationCampaign(com.next.aap.server.persistance.LocationCampaign)
	 */
	@Override
	public LocationCampaign saveLocationCampaign(LocationCampaign locationCampaign){
		checkIfStringMissing("CampaignId", locationCampaign.getCampaignId());
		locationCampaign.setCampaignIdUp(locationCampaign.getCampaignId().toUpperCase());
		locationCampaign = super.saveObject(locationCampaign);
		return locationCampaign;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.LocationCampaignDao#getLocationCampaignById(java.lang.Long)
	 */
	@Override
	public LocationCampaign getLocationCampaignById(Long id) {
		return super.getObjectById(LocationCampaign.class, id);
	}
	@Override
	public LocationCampaign getLocationCampaignByLocationCampaign(String locationCampaignId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("campaignIdUp", locationCampaignId.toUpperCase());
		LocationCampaign locationCampaign = executeQueryGetObject("from LocationCampaign where campaignIdUp = :campaignIdUp", params);
		return locationCampaign;
	}

	@Override
	public LocationCampaign getDefaultLocationCampaignByStateId(Long stateId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("stateId", stateId);
		Long campaignId = executeSqlQueryGetLong("select sc.location_campaign_id from state_campaigns sc, location_campaign lc where state_id=:stateId and sc.location_campaign_id = lc.id and lc.campaign_type = 'DEFAULT'", params);
		if(campaignId == null){
			return null;
		}
		return getLocationCampaignById(campaignId);
	}

	@Override
	public LocationCampaign getDefaultLocationCampaignByDistrictId(Long districtId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("districtId", districtId);
		Long campaignId = executeSqlQueryGetLong("select dc.location_campaign_id from district_campaigns dc, location_campaign lc where district_id=:districtId and dc.location_campaign_id = lc.id and lc.campaign_type = 'DEFAULT'", params);
		if(campaignId == null){
			return null;
		}
		return getLocationCampaignById(campaignId);
	}

	@Override
	public LocationCampaign getDefaultLocationCampaignByAcId(Long acId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("acId", acId);
		Long campaignId = executeSqlQueryGetLong("select ac.location_campaign_id from ac_campaigns ac, location_campaign lc where ac_id=:acId and ac.location_campaign_id = lc.id and lc.campaign_type = 'DEFAULT'", params);
		if(campaignId == null){
			return null;
		}
		return getLocationCampaignById(campaignId);
	}

	@Override
	public LocationCampaign getDefaultLocationCampaignByPcId(Long pcId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("pcId", pcId);
		Long campaignId = executeSqlQueryGetLong("select pc.location_campaign_id from pc_campaigns pc, location_campaign lc where pc_id=:pcId and pc.location_campaign_id = lc.id and lc.campaign_type = 'DEFAULT'", params);
		if(campaignId == null){
			return null;
		}
		return getLocationCampaignById(campaignId);
	}

	@Override
	public List<LocationCampaign> getAllLocationCampaigns() {
		return executeQueryGetList("from LocationCampaign");
	}

}
