package com.next.aap.web.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.DonationDto;
import com.next.aap.web.task.FutureCacheManager;
import com.next.aap.web.task.TaskManager;

@Controller
public class DonationCertificateController extends AppBaseController {

	@Autowired
	private TaskManager taskManager;
	@Autowired
	private FutureCacheManager futureCacheManager;
	
	long cacheAge = 2592000;
	long expiry = new Date().getTime() + cacheAge*1000;

	@RequestMapping(value = "/donationcertifcates.html", method = RequestMethod.GET)
	public ModelAndView viewDonationCertificatePage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv)
			throws IOException {
		addGenericValuesInModel(httpServletRequest, mv);
		String txnId = httpServletRequest.getParameter("txnid");
		if (!StringUtil.isEmptyOrWhitespace(txnId)) {
			try {
				DonationDto donationDto = aapService.getUserDonationByTxnid(txnId);
				if (donationDto == null) {
					mv.getModel().put("Error",
							"No donation found with txn id " + txnId + ", there can be delay between actuall donation and donation visible in this portal");
				}
				mv.getModel().put("donation", donationDto);
			} catch (Exception ex) {
				ex.printStackTrace();
				mv.getModel().put("Error", "We are unable to find this transaction id in system. " + txnId);
			}
		}
		mv.setViewName(design + "/donationcertificates");
		return mv;
	}

	@RequestMapping(value = "/dc/{template}.html", method = RequestMethod.GET)
	public ModelAndView viewTemplatePage(HttpServletRequest httpServletRequest, HttpServletResponse response, ModelAndView modelAndView,
			@PathVariable String template) throws IOException {
		String txnId = httpServletRequest.getParameter("txnid");
		if(StringUtil.isEmpty(txnId)){
			RedirectView rv = new RedirectView(httpServletRequest.getContextPath() + "/donationcertifcates.html");
			rv.setExposeModelAttributes(false);
			modelAndView.setView(rv);
			return modelAndView;
		}
		DonationDto donation = aapService.getUserDonationByTxnid(txnId);
		if(donation == null){
			RedirectView rv = new RedirectView(httpServletRequest.getContextPath() + "/donationcertifcates.html");
			rv.setExposeModelAttributes(false);
			modelAndView.setView(rv);
			return modelAndView;
		}
		DonationTemplateEnum donationTemplateEnum = DonationTemplateEnum.parse(template);
		
		String imageKey = UUID.randomUUID().toString();
		taskManager.startDonationCertificateTask(imageKey, donation, donationTemplateEnum);
		modelAndView.getModel().put("ImageSource", imageKey + ".jpg");
		modelAndView.getModel().put("ImageKey", imageKey);
		modelAndView.getModel().put("template", template);
		
		StringBuilder sb = new StringBuilder("http://");
		URL url = new URL(httpServletRequest.getRequestURL().toString());
		sb.append(url.getHost());
		if (url.getPort() != 80 && url.getPort() > 0) {
			sb.append(":");
			sb.append(url.getPort());
		}
		sb.append(httpServletRequest.getContextPath());
		sb.append("/dc/images/" + imageKey + ".jpg");
		modelAndView.getModel().put("imageurl", sb.toString());
		addGenericValuesInModel(httpServletRequest, modelAndView);
		modelAndView.setViewName(design+"/donationcertificateshare");
		return modelAndView;
	}
	@RequestMapping(value = "/dc/images/{imageKey}.jpg", method = RequestMethod.GET)
	public void showImage(HttpServletRequest httpServletRequest, HttpServletResponse response, ModelAndView modelAndView,
			@PathVariable String imageKey) throws IOException {
		logger.info("Generating Image for {}",imageKey);
		Future<Boolean> future = futureCacheManager.getImageKeyFuture(imageKey);
		if(future != null){
			if(!future.isDone()){
				logger.info("Will wait for task to finish {}",imageKey);
			}else{
				logger.info("Will NOT wait for task to finish {}",imageKey);
			}
			try {
				future.get();
				logger.info("Wait finished {}",imageKey);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		}
		response.setContentType("image/jpeg");
		response.setDateHeader("Expires", expiry);
		response.setHeader("Cache-Control", "max-age="+ cacheAge);
		String fileName = taskManager.getFilePath(imageKey);
		IOUtils.copyLarge(new FileInputStream(fileName), response.getOutputStream());
	}

}
