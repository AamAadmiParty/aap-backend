package com.next.aap.core.persistance.dao.impl;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.FinancialPlanning;
import com.next.aap.core.persistance.dao.FinancialPlanningDao;

@Component
public class FinancialPlanningDaoHibernateSpringImpl extends BaseDaoHibernateSpring<FinancialPlanning> implements FinancialPlanningDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FinancialPlanningDao#saveFinancialPlanning(com.next.aap.server.persistance.FinancialPlanning)
	 */
	@Override
	public FinancialPlanning saveFinancialPlanning(FinancialPlanning financialPlanning){
		checkIfObjectMissing("End Date", financialPlanning.getEndDate());
		checkIfObjectMissing("Title", financialPlanning.getTitle());
		financialPlanning = super.saveObject(financialPlanning);
		return financialPlanning;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.FinancialPlanningDao#getFinancialPlanningById(java.lang.Long)
	 */
	@Override
	public FinancialPlanning getFinancialPlanningById(Long id) {
		return super.getObjectById(FinancialPlanning.class, id);
	}

}
