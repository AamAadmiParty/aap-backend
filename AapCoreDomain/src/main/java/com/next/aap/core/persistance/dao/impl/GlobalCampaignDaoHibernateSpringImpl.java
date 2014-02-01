package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.GlobalCampaign;
import com.next.aap.core.persistance.dao.GlobalCampaignDao;

@Component
public class GlobalCampaignDaoHibernateSpringImpl extends BaseDaoHibernateSpring<GlobalCampaign> implements GlobalCampaignDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.GlobalCampaignDao#saveGlobalCampaign(com.next.aap.server.persistance.GlobalCampaign)
	 */
	@Override
	public GlobalCampaign saveGlobalCampaign(GlobalCampaign globalCampaign){
		checkIfStringMissing("CampaignId", globalCampaign.getCampaignId());
		globalCampaign.setCampaignIdUp(globalCampaign.getCampaignId().toUpperCase());
		globalCampaign = super.saveObject(globalCampaign);
		return globalCampaign;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.GlobalCampaignDao#getGlobalCampaignById(java.lang.Long)
	 */
	@Override
	public GlobalCampaign getGlobalCampaignById(Long id) {
		return super.getObjectById(GlobalCampaign.class, id);
	}
	@Override
	public GlobalCampaign getGlobalCampaignByGlobalCampaign(String globalCampaignId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("campaignIdUp", globalCampaignId.toUpperCase());
		GlobalCampaign globalCampaign = executeQueryGetObject("from GlobalCampaign where campaignIdUp = :campaignIdUp", params);
		return globalCampaign;
	}

	@Override
	public List<GlobalCampaign> getGlobalCampaigns() {
		String query = "from GlobalCampaign order by dateCreated desc";
		List<GlobalCampaign> list = executeQueryGetList(query, null);
		return list;
	}

}
