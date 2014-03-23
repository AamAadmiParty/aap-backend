package com.next.aap.core.persistance.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	public Donation getDonationByTransactionId(String transactionId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("transactionId", transactionId);
		Donation donation = executeQueryGetObject("from Donation where transactionId = :transactionId", params);
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
		if(queryResult != null && queryResult.size() > 0){
			for(Object[] oneRow:queryResult){
				returnResult.add(convertDonationDump(oneRow));
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
	private DonationDump convertDonationDump(Object[] oneRow){
		int index = 0;
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
		
		return donationDump;
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
		params.put("donorId", donorId);
		executeSqlQueryUpdate("update donation_dump set status=:status, status_message=:status_message where Donor_Id = :donorId", params);
	}

	@Override
	public Donation getDonationByDonorId(String donorId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("donorId", donorId);
		return executeQueryGetObject("from Donation where donorId = :donorId", params);
	}

	@Override
	public DonationDump getDonationDumpByDonorId(String donorId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("donorId", donorId);
		String selectParams = "Donor_Id, Merchant_Reference_No, Transaction_Id, Name, Gender, Age, Mobile,Email, Country_Id, State_Id, " +
				"District_Id, Address, Payment_Gateway_Used, Payment_Gateway, Donation_Date, Donor_IP, Amount, Utm_Source, Utm_Medium, Utm_Term" +
				", Utm_Content, Utm_Campaign, PGErrorMsg, cid, PGErrorDetail, Remarks, status, status_message";
		List<Object[]> queryResult = executeSqlQueryGetObjectList("select "+selectParams+" from donation_dump where donor_id=:donorId", params);
		if(queryResult == null || queryResult.isEmpty()){
			return null;
		}
		return convertDonationDump(queryResult.get(0));
	}

	@Override
	public DonationDump saveDonationDump(DonationDump donationDump) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("Donor_Id", donationDump.getDonorId());
		params.put("Merchant_Reference_No", donationDump.getMerchantReferenceNumber());
		params.put("Transaction_Id", donationDump.getTransactionId());
		params.put("Name", donationDump.getDonorName());
		params.put("Gender", donationDump.getDonorGender());
		params.put("Age", donationDump.getDonorAge());
		params.put("Mobile", donationDump.getDonorMobile());
		params.put("Email", donationDump.getDonorEmail());
		params.put("Country_Id", donationDump.getDonorCountryId());
		params.put("State_Id", donationDump.getDonorStateId());
		params.put("District_Id", donationDump.getDonorDistrictId());
		params.put("Address", donationDump.getDonorAddress());
		params.put("Payment_Gateway_Used", donationDump.getPaymentGateway());
		params.put("Payment_Gateway", donationDump.getTransactionType());
		params.put("Donation_Date", donationDump.getDonationDate());
		params.put("Donor_IP", donationDump.getDonorIp());
		params.put("Amount", donationDump.getAmount());
		params.put("Utm_Source", donationDump.getUtmSource());
		params.put("Utm_Medium", donationDump.getUtmMedium());
		params.put("Utm_Term", donationDump.getUtmTerm());
		params.put("Utm_Content", donationDump.getUtmContent());
		params.put("Utm_Campaign", donationDump.getUtmCampaign());
		params.put("PGErrorMsg", donationDump.getPgErrorMessage());
		params.put("cid", donationDump.getCid());
		params.put("PGErrorDetail", donationDump.getPgErrorDetail());
		params.put("Remarks", donationDump.getRemark());
		params.put("status", donationDump.getStatus());
		params.put("status_message", donationDump.getStatusMessage());
		executeSqlQueryUpdate("INSERT INTO donation_dump" +
				"(Donor_Id,Merchant_Reference_No,Transaction_Id,Name,Gender,Age,Mobile,Email,Country_Id,State_Id,District_Id,Address," +
				"Payment_Gateway_Used,Payment_Gateway,Donation_Date,Donor_IP,Amount,Utm_Source,Utm_Medium,Utm_Term,Utm_Content," +
				"Utm_Campaign,PGErrorMsg,cid,PGErrorDetail,Remarks,status,status_message)" +
				"VALUES" +
				"(:Donor_Id,:Merchant_Reference_No,:Transaction_Id,:Name,:Gender,:Age,:Mobile,:Email,:Country_Id,:State_Id,:District_Id,:Address," +
				":Payment_Gateway_Used,:Payment_Gateway,:Donation_Date,:Donor_IP,:Amount,:Utm_Source,:Utm_Medium,:Utm_Term,:Utm_Content," +
				":Utm_Campaign,:PGErrorMsg,:cid,:PGErrorDetail,:Remarks,:status,:status_message)", params);
		
		return donationDump;
	}

	@Override
	public void updateDonationPgStatus(String donorId, String PGErrorMsg, String PGErrorDetail) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("PGErrorMsg", PGErrorMsg);
		params.put("PGErrorDetail", PGErrorDetail);
		params.put("donorId", donorId);
		executeSqlQueryUpdate("update donation_dump set PGErrorMsg=:PGErrorMsg, PGErrorDetail=:PGErrorDetail where Donor_Id = :donorId", params);

	}

	@Override
	public List<Donation> getDonationsByCampaignId(String cid, int pageSize) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("cid", cid);
		return executeQueryGetList("from Donation where cid = :cid order by donationDate desc", params, pageSize);
	}

	@Override
	public List<Donation> getDonationsByCampaignId(String cid) {
		return getDonationsByCampaignId(cid, 0);//0 page size means no limit
	}

	@Override
	public List<Donation> getDonationsByLocationCampaignId(String lcid, int pageSize) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lcid", lcid);
		return executeQueryGetList("from Donation where lcid = :lcid order by donationDate desc", params, pageSize);
	}
	@Override
	public List<Donation> getDonationsByLocationCampaignId(String lcid) {
		return getDonationsByLocationCampaignId(lcid, 0);//0 page size means no limit
	}

	@Override
	public Double getTotalDonationOnDay(Date date) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(date);
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 1);
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(date);
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.MILLISECOND, 999);
		endDate.add(Calendar.SECOND, 1);

		return getTotalDonationBetweenTimes(startDate.getTime(), endDate.getTime());
	}

	@Override
	public Double getTotalDonationInMonth(Date date) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(date);
		startDate.set(Calendar.DATE, 1);
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 1);
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(date);
		endDate.set(Calendar.DAY_OF_MONTH,endDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.MILLISECOND, 999);

		return getTotalDonationBetweenTimes(startDate.getTime(), endDate.getTime());
	}
	
	private Double getTotalDonationBetweenTimes(Date startDate, Date endDate){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("status1", "'SUCCESS'");
		params.put("status2", "'Success'");
		DateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		params.put("startDate", "'"+ simpleDateFormat.format(startDate)+"'");
		params.put("endDate", "'"+simpleDateFormat.format(endDate)+"'");
		String startDateStr = "'"+ simpleDateFormat.format(startDate)+"'";
		String endDateStr = "'"+ simpleDateFormat.format(endDate)+"'";

		String sql = "select sum(amount) from donations where donation_date BETWEEN "+startDateStr+" AND "+endDateStr+" and (pg_error_msg='SUCCESS' or pg_error_msg='Success')";
		//String sql = "select sum(amount) from donations where donation_date BETWEEN :startDate AND :endDate and (pg_error_msg=:status1 or pg_error_msg=:status2)";
		return executeSqlQueryGetDouble(sql);
	}

	@Override
	public Double getTotalDonationAmountByLcid(String lcid) {

		String sql = "select sum(amount) from donations where lcid = '"+lcid+"' and (pg_error_msg='SUCCESS' or pg_error_msg='Success')";
		return executeSqlQueryGetDouble(sql);
	}

	@Override
	public Integer getTotalDonationCountByLcid(String lcid) {
		String sql = "select count(0) from donations where lcid = '"+lcid+"' and (pg_error_msg='SUCCESS' or pg_error_msg='Success')";
		return executeSqlQueryGetInt(sql);
	}
}
