package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.DonationDump;

public interface DonationDumpDao {

	public abstract DonationDump saveDonationDump(DonationDump donationDump);

	public abstract DonationDump getDonationDumpById(Long id);

	public abstract List<DonationDump> getDonationDumpsToImport(int pageSize);

}