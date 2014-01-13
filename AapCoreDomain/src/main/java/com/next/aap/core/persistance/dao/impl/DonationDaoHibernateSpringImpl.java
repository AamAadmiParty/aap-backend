package com.next.aap.core.persistance.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.Donation;
import com.next.aap.core.persistance.DonationDump;
import com.next.aap.core.persistance.dao.DonationDao;

@Component
public class DonationDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Donation> implements DonationDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DonationDao#saveDonation(com.next.aap.server.persistance.Donation)
	 */
	@Override
	public Donation saveDonation(Donation donation){
		donation = super.saveObject(donation);
		return donation;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DonationDao#getDonationById(java.lang.Long)
	 */
	@Override
	public Donation getDonationById(Long id) {
		return super.getObjectById(Donation.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.DonationDao#getDonationsAfterId(java.lang.Long, int)
	 */
	@Override
	public List<Donation> getDonationsAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		params.put("pageSize", pageSize);
		List<Donation> list = executeQueryGetList("from Donation where id > :lastId order by id asc limit :pageSize");
		return list;
	}

	@Override
	public Donation getDonationByDonation(String donationId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("donation", donationId.toUpperCase());
		Donation donation = executeQueryGetObject("from Donation where donationUp = :donation", params);
		return donation;
	}

	@Override
	public List<Donation> getDonationsByUserId(Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		return executeQueryGetList("from Donation where userId = :userId", params);
	}

	
	@Override
	public List<Donation> getStateDonations(Long stateId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("stateId", stateId);
		return executeQueryGetList("from Donation where user.stateVotingId = :stateId or user.stateLivingId = :stateId", params);
	}

	@Override
	public List<Donation> getDistrictDonations(Long districtId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("districtId", districtId);
		return executeQueryGetList("from Donation where user.districtVotingId = :districtId or user.districtLivingId = :districtId", params);
	}

	@Override
	public List<Donation> getAcDonations(Long acId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("acId", acId);
		return executeQueryGetList("from Donation where user.assemblyConstituencyVotingId = :acId or user.assemblyConstituencyLivingId = :acId", params);
	}

	@Override
	public List<Donation> getPcDonations(Long pcId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("pcId", pcId);
		return executeQueryGetList("from Donation where user.parliamentConstituencyVotingId = :pcId or user.parliamentConstituencyLivingId = :pcId", params);
	}

	@Override
	public List<Donation> getCountryDonations(Long nriCountryId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("nriCountryId", nriCountryId);
		return executeQueryGetList("from Donation where user.nriCountryId = :nriCountryId", params);
	}

	@Override
	public List<Donation> getCountryRegionDonations(Long nriCountryRegionId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("nriCountryRegionId", nriCountryRegionId);
		return executeQueryGetList("from Donation where user.nriCountryRegionId = :nriCountryRegionId", params);
	}

	@Override
	public List<Donation> getCountryRegionAreaDonations(Long nriCountryRegionAreaId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("nriCountryRegionAreaId", nriCountryRegionAreaId);
		return executeQueryGetList("from Donation where user.nriCountryRegionAreaId = :nriCountryRegionAreaId", params);
	}
	
	@Override
	public List<DonationDump> getDonationsToImport(int totalRecords) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("status", "Pending");
		String selectParams = "Donor_Id, Merchant_Reference_No, Transaction_Id, Name, Gender, Age, Mobile,Email, Country_Id, State_Id, " +
				"District_Id, Address, Payment_Gateway_Used, Payment_Gateway, Donation_Date, Donor_IP, Amount, Utm_Source, Utm_Medium, Utm_Term" +
				", Utm_Content, Utm_Campaign, PGErrorMsg, cid, PGErrorDetail, Remarks, status, status_message";
		List<Object[]> queryResult = executeSqlQueryGetObjectList("select "+selectParams+" from donation_dump where status=:status and Email is not null and Email !='' and Mobile is not null and Mobile != ''", params, totalRecords);
		if(queryResult == null){
			queryResult = executeSqlQueryGetObjectList("select "+selectParams+" from donation_dump where status=:status and Email is not null and Email !=''", params, totalRecords);
		}
		if(queryResult == null){
			queryResult = executeSqlQueryGetObjectList("select "+selectParams+" from donation_dump where status=:status and Mobile is not null and Mobile !=''", params, totalRecords);
		}
		
		List<DonationDump> returnResult = new ArrayList<>();
		int index;
		if(queryResult != null && queryResult.size() > 0){
			for(Object[] oneRow:queryResult){
				index = 0;
				
				DonationDump donationDump = new DonationDump();
				donationDump.setDonorId(getStringValue(oneRow[index++]));
				donationDump.setMerchantReferenceNumber(getStringValue(oneRow[index++]));
				donationDump.setTransactionId(getStringValue(oneRow[index++]));
				donationDump.setDonorName(getStringValue(oneRow[index++]));
				donationDump.setDonorGender(getStringValue(oneRow[index++]));
				donationDump.setDonorAge(getIntegerValue(oneRow[index++]));
				donationDump.setDonorMobile(getStringValue(oneRow[index++]));
				donationDump.setDonorEmail(getStringValue(oneRow[index++]));
				donationDump.setDonorCountryId(getStringValue(oneRow[index++]));
				donationDump.setDonorStateId(getStringValue(oneRow[index++]));
				donationDump.setDonorDistrictId(getStringValue(oneRow[index++]));
				donationDump.setDonorAddress(getStringValue(oneRow[index++]));
				donationDump.setPaymentGateway(getStringValue(oneRow[index++]));
				donationDump.setTransactionType(getStringValue(oneRow[index++]));
				donationDump.setDonationDate(getStringValue(oneRow[index++]));
				donationDump.setDonorIp(getStringValue(oneRow[index++]));
				donationDump.setAmount(getDoubleValue(oneRow[index++]));
				donationDump.setUtmSource(getStringValue(oneRow[index++]));
				donationDump.setUtmMedium(getStringValue(oneRow[index++]));
				donationDump.setUtmTerm(getStringValue(oneRow[index++]));
				donationDump.setUtmContent(getStringValue(oneRow[index++]));
				donationDump.setUtmCampaign(getStringValue(oneRow[index++]));
				donationDump.setPgErrorMessage(getStringValue(oneRow[index++]));
				donationDump.setCid(getStringValue(oneRow[index++]));
				donationDump.setPgErrorDetail(getStringValue(oneRow[index++]));
				donationDump.setRemark(getStringValue(oneRow[index++]));
				donationDump.setStatus(getStringValue(oneRow[index++]));
				donationDump.setStatusMessage(getStringValue(oneRow[index++]));
				returnResult.add(donationDump);
			}
		}
		return returnResult; 
	}
	private String getStringValue(Integer object){
		if(object == null){
			return null;
		}
		return object.toString();
	}
	private String getStringValue(Object object){
		String returnValue = null;
		if(object == null){
			return null;
		}
		try{
			returnValue = object.toString();
		}catch(Exception ex){
			
		}
		return returnValue;
	}
	private Integer getIntegerValue(Object obj){
		if(obj == null){
			return null;
		}
		try{
			if(obj instanceof Integer){
				return (Integer)obj;
			}else{
				Integer.parseInt(obj.toString());
			}
		}catch(Exception ex){
			
		}
		return null;
	}
	private Double getDoubleValue(Object data){
		if(data == null){
			return null;
		}
		try{
			if(data instanceof Double){
				return (Double)data;
			}
			return Double.parseDouble(data.toString());
		}catch(Exception ex){
			
		}
		return null;
	}

	@Override
	public void updateDonationStatus(String donorId, String Status, String statusMessage) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("status", Status);
		params.put("status_message", statusMessage);
		params.put("donorId", Long.parseLong(donorId));
		executeSqlQueryUpdate("update donation_dump set status=:status, status_message=:status_message where Donor_Id = :donorId", params);
	}

	@Override
	public Donation getDonationByDonorId(String donorId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("donorId", donorId);
		return executeQueryGetObject("from Donation where donorId = :donorId", params);
	}
}
