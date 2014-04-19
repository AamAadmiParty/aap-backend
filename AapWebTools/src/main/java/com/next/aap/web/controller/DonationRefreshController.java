package com.next.aap.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.service.HttpUtil;
import com.next.aap.web.dto.DonationDto;

@Controller
public class DonationRefreshController extends AppBaseController {

	@Autowired
	private HttpUtil httpUtil;

	private String ServiceUrl = "https://donate.aamaadmiparty.org/webservice/donationservice.asmx";

	String requestTemplate = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
			+ "<soap12:Body>" + "<DonorStatus xmlns=\"http://tempuri.org/\">" + "<DonorTransactionID>TXNID</DonorTransactionID>"
			+ "<DonorEmailId>DONOREMAILID</DonorEmailId>" + "</DonorStatus>" + "</soap12:Body>" + "</soap12:Envelope>";

	@RequestMapping(value = "/donationinfo.html", method = RequestMethod.GET)
	public ModelAndView getDonationInformation(ModelAndView mv, HttpServletRequest httpServletRequest) {
		addGenericValuesInModel(httpServletRequest, mv);
		mv.setViewName(design + "/donationinfo");
		return mv;
	}

	@RequestMapping(value = "/donationinfo.html", method = RequestMethod.POST)
	public ModelAndView refreshDonationFromDotNet(ModelAndView mv, HttpServletRequest httpServletRequest) {
		addGenericValuesInModel(httpServletRequest, mv);
		String buttonClicked = httpServletRequest.getParameter("buttonClicked");
		String txnId = httpServletRequest.getParameter("txnid");
		String emailId = httpServletRequest.getParameter("emailid");
		System.out.println("buttonClicked=" + buttonClicked);
		System.out.println("txnid=" + txnId);
		System.out.println("emailId=" + emailId);

		if ("View Donation Info".equals(buttonClicked)) {
			List<DonationDto> donations = new ArrayList<>();
			if (!StringUtil.isEmpty(txnId)) {
				DonationDto donationDto = aapService.getDonationByTransactionId(txnId);
				if (donationDto != null) {
					donations.add(donationDto);
				}
			} else if (!StringUtil.isEmpty(emailId)) {
				List<DonationDto> donationsByEmail = aapService.getDonationByEmailId(emailId);
				if (donationsByEmail != null) {
					donations.addAll(donationsByEmail);
				}
			} 
			if(donations.isEmpty()){
				mv.getModel().put("message", "No Donations found");
			}
			mv.getModel().put("donations", donations);
			mv.getModel().put("view", "donations");
		}
		if ("Refresh Donation Info From .net".equals(buttonClicked)) {
			String request = prepareRequest(txnId, emailId);
			System.out.println("request=" + request);
			try {
				String response = httpUtil.postSoapRequest(ServiceUrl, request);
				int startIndex = response.indexOf("<DonorStatusResult>") + "<DonorStatusResult>".length();
				int endIndex = response.indexOf("</DonorStatusResult>");
				String message = response.substring(startIndex, endIndex);
				mv.getModel().put("message", ".net server said : " + message);
			} catch (Exception ex) {
				logger.error("Exception while connecting to .net Server", ex);
			}
			mv.getModel().put("view", ".net");
		}

		mv.getModel().put("txnid", txnId);
		mv.getModel().put("emailid", emailId);
		mv.setViewName(design + "/donationinfo");
		return mv;
	}

	private String prepareRequest(String txnId, String emailId) {
		String request = requestTemplate.replaceAll("TXNID", txnId);
		request = request.replaceAll("DONOREMAILID", emailId);
		return request;
	}

}
