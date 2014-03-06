package com.next.aap.task.donation;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.persistance.DonationDump;
import com.next.aap.core.service.AapService;
import com.next.aap.task.util.AwsQueueListener;

@Component
public class ReceiveDonationListener extends AwsQueueListener{

	@Autowired
	public ReceiveDonationListener(@Value("${donation_queue_name}") String queueName,@Value("${aws_access_key}") String accessKey
			, @Value("${aws_access_secret}") String accessSecret) {
		super(queueName, accessKey, accessSecret);
	}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapService aapService;
	@Override
	public void onQueueMessage(String jsonMessage) {
		try{
			logger.info("onQueueMessage "+ jsonMessage);
			JSONObject jsonObject = new JSONObject(jsonMessage);
			String data = jsonObject.getString("Message");
			JSONObject donationJsonObject = new JSONObject(data);
			DonationDump donationDump = new DonationDump();
			//29 Jan 2014 04:32:26:290
			
			donationDump.setAmount(donationJsonObject.getDouble("Amount"));
			String cid = donationJsonObject.getString("cid");
			logger.info("scid "+ cid);
			if(!StringUtil.isEmpty(cid)){
				if(cid.contains("=")){
					String[] ids = cid.split(";");
					for(String oneToken:ids){
						String[] values = oneToken.split("=");
						if(values[0].equalsIgnoreCase("cid")){
							donationDump.setCid(values[1]);
						}
						if(values[0].equalsIgnoreCase("lcid")){
							donationDump.setLid(values[1]);
						}
					}
				}else{
					donationDump.setCid(cid);
				}
					
			}
			
			donationDump.setDateCreated(new Date());
			donationDump.setDateModified(new Date());
			donationDump.setDonationDate(donationJsonObject.getString("DonationDate"));
			donationDump.setDonorAddress(donationJsonObject.getString("Address"));
			donationDump.setDonorAge(donationJsonObject.getInt("Age"));
			donationDump.setDonorCountryId(donationJsonObject.getString("Country_id"));
			donationDump.setDonorDistrictId(donationJsonObject.getString("District_id"));
			donationDump.setDonorEmail(donationJsonObject.getString("Email"));
			donationDump.setDonorGender(donationJsonObject.getString("Gender"));
			donationDump.setDonorId(donationJsonObject.getString("DonorID"));
			//donationDump.setDonorIp(donationJsonObject.getString("DonorID"));
			donationDump.setDonorMobile(donationJsonObject.getString("Mobile"));
			donationDump.setDonorName(donationJsonObject.getString("Name"));
			donationDump.setDonorStateId(donationJsonObject.getString("State_id"));
			donationDump.setMerchantReferenceNumber(donationJsonObject.getString("MerchantReferenceNo"));
			donationDump.setPaymentGateway(donationJsonObject.getString("PaymentGatewayUsed"));
			//donationDump.setPgErrorDetail(donationJsonObject.getString("PaymentGateway"));
			donationDump.setPgErrorMessage(donationJsonObject.getString("PGErrorMsg"));
			donationDump.setRemark(donationJsonObject.getString("Remark"));
			donationDump.setStatus("Pending");
			donationDump.setStatusMessage("");
			donationDump.setTransactionId(donationJsonObject.getString("TransactionId"));
			donationDump.setTransactionType(donationJsonObject.getString("PaymentGateway"));
			donationDump.setUtmCampaign(donationJsonObject.getString("utm_campaign"));
			donationDump.setUtmContent(donationJsonObject.getString("utm_content"));
			donationDump.setUtmMedium(donationJsonObject.getString("utm_medium"));
			donationDump.setUtmSource(donationJsonObject.getString("utm_source"));
			donationDump.setUtmTerm(donationJsonObject.getString("utm_term"));
			
			logger.info("Saving Donation Dump : "+donationDump);
			aapService.saveDonationDump(donationDump);
			
		}catch(Exception ex){
			logger.error("Unable to import Donation"+ jsonMessage,ex);
		}
		
		
		
	}
	

}
