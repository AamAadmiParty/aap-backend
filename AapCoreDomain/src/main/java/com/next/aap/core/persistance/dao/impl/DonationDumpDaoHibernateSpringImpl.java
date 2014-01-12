package com.next.aap.core.persistance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.DonationDump;
import com.next.aap.core.persistance.dao.DonationDumpDao;

@Component
public class DonationDumpDaoHibernateSpringImpl extends BaseDaoHibernateSpring<DonationDump> implements DonationDumpDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DonationDumpDao#saveDonationDump(com.next.aap.server.persistance.DonationDump)
	 */
	@Override
	public DonationDump saveDonationDump(DonationDump donationDump){
		donationDump = super.saveObject(donationDump);
		return donationDump;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DonationDumpDao#getDonationDumpById(java.lang.Long)
	 */
	@Override
	public DonationDump getDonationDumpById(Long id) {
		return super.getObjectById(DonationDump.class, id);
	}

	@Override
	public List<DonationDump> getDonationDumpsToImport(int totalRecords) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("status", "Pending");
		List<DonationDump> queryResult = executeQueryGetList("from DonationDump where status=:status and donorEmail is not null and donorEmail !='' and donorMobile is not null and donorMobile != ''", params, totalRecords);
		if(queryResult == null){
			queryResult = executeQueryGetList("from DonationDump where status=:status and donorEmail is not null and donorEmail !=''", params, totalRecords);
		}
		if(queryResult == null){
			queryResult = executeQueryGetList("from DonationDump where status=:status and donorMobile is not null and donorMobile != ''", params, totalRecords);
		}
		return queryResult; 
	}
}
