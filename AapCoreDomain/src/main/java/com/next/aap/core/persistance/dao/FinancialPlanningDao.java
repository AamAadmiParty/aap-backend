package com.next.aap.core.persistance.dao;

import com.next.aap.core.persistance.FinancialPlanning;

public interface FinancialPlanningDao {

	public abstract FinancialPlanning saveFinancialPlanning(FinancialPlanning financialPlanning);

	public abstract FinancialPlanning getFinancialPlanningById(Long id);

}