package com.next.aap.core.persistance.dao.impl;

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

}
