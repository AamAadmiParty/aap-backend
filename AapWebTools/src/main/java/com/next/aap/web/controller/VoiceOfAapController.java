package com.next.aap.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.RevokedAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.GroupMembership;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.next.aap.web.dto.FacebookAccountDto;
import com.next.aap.web.dto.FacebookAppPermissionDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.VoiceOfAapData;
import com.next.aap.web.util.ContentDonwloadUtil;
import com.next.aap.web.util.CookieUtil;

@Controller
public class VoiceOfAapController extends AppBaseController {

	@Autowired
	private ContentDonwloadUtil contentDonwloadUtil;

	protected ModelAndView redirect(ModelAndView mv, String url){
		RedirectView rv = new RedirectView(url);
        rv.setExposeModelAttributes(false);
        mv.setView(rv);
        return mv;
	}
	@RequestMapping(value = "/voa.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv, HttpServletRequest httpServletRequest) {

		addGenericValuesInModel(httpServletRequest, mv);

		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		LoginAccountDto loginAccounts = getLoggedInAccountsFromSesion(httpServletRequest);
		String contextPath = httpServletRequest.getContextPath();
		if (loginAccounts == null || loginAccounts.getFacebookAccounts() == null || loginAccounts.getFacebookAccounts().isEmpty()) {
			// buildAndRedirect to facebook Login Account
			logger.info("User is not logegd In to facebook, So redirecting to facebook login");
			return redirect(mv, contextPath+"/login/voa/facebook?group=timeline&" + REDIRECT_URL_PARAM_ID + "=voa.html");
		}
		FacebookAccountDto oneFacebookAccountDto = loginAccounts.getFacebookAccounts().get(0);

		FacebookAppPermissionDto facebookAppPermission = aapService.getVoiceOfAapFacebookPermission(oneFacebookAccountDto.getId());
		if (facebookAppPermission == null) {
			// buildAndRedirect to facebook Login Account
			logger.info("User is logegd In to facebook, but have not given permission to voice of aap");
			return redirect(mv, contextPath+"/login/voa/facebook?group=timeline&" + REDIRECT_URL_PARAM_ID + "=voa.html");
		}

		Calendar currectTime = Calendar.getInstance();
		currectTime.add(Calendar.DATE, 10);
		if (currectTime.after(facebookAppPermission.getExpireTime())) {
			// buildAndRedirect to facebook Login Account
			logger.info("User is logegd In to facebook,and given permission to voice of aap but permission about to expire");
			return redirect(mv, contextPath+"/login/voa/facebook?group=timeline&" + REDIRECT_URL_PARAM_ID + "=voa.html");
		}
		logger.info("facebook User Token " + facebookAppPermission.getToken());
		Facebook facebook = new FacebookTemplate(facebookAppPermission.getToken());
		logger.info("isAuthorized = " + facebook.isAuthorized());
		boolean beVoiceOfAap;
		try {
			if (!facebook.userOperations().getUserPermissions().contains("publish_actions")
					|| !facebook.userOperations().getUserPermissions().contains("publish_stream")) {
				logger.info("User has not given publish permission");
				beVoiceOfAap = false;
			} else {
				beVoiceOfAap = true;
			}
			boolean postOnGroup;
			if (!facebook.userOperations().getUserPermissions().contains("publish_actions")
					|| !facebook.userOperations().getUserPermissions().contains("publish_stream")
					|| !facebook.userOperations().getUserPermissions().contains("user_groups")) {
				logger.info("User has not given publish permission");
				mv.getModel().put("postOnGroup", true);
				mv.getModel().put("postOnTimeLine", true);
				mv.getModel().put("postOnTwitter", false);
			} else {
				// get all facebook groups of user
				//loadUserGroups(facebook, oneFacebookAccountDto);
				VoiceOfAapData voiceOfAapData = aapService.getVoiceOfAapSetting(oneFacebookAccountDto.getId());
				mv.getModel().put("voiceOfAapData", voiceOfAapData);
				mv.getModel().put("postOnGroup", true);
				mv.getModel().put("postOnTimeLine", voiceOfAapData.isPostOnTimeLine());
				mv.getModel().put("postOnTwitter", voiceOfAapData.isTweetFromMyAccount());
				beVoiceOfAap = voiceOfAapData.isBeVoiceOfAap();
			}
		} catch (RevokedAuthorizationException ex) {
			logger.info("User has revoked permission");
			beVoiceOfAap = false;
		} catch (InvalidAuthorizationException ex) {
			logger.info("User is logegd In to facebook,and given permission to voice of aap but permission about to expire");
			beVoiceOfAap = false;
			return redirect(mv, contextPath+"/login/voa/facebook?group=timeline&" + REDIRECT_URL_PARAM_ID + "=voa.html");
			
		}
		mv.setViewName(design + "/voa");
		return mv;
	}
	@RequestMapping(value = "/voa.html", method = RequestMethod.POST)
	public ModelAndView saveVoiceOFAAp(ModelAndView mv, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		System.out.println("saveVoiceOFAAp");
		try{
			LoginAccountDto loginAccounts = getLoggedInAccountsFromSesion(httpServletRequest);
			System.out.println("loginAccounts="+loginAccounts);
			FacebookAccountDto oneFacebookAccountDto = loginAccounts.getFacebookAccounts().get(0);
			System.out.println("oneFacebookAccountDto="+oneFacebookAccountDto);
			System.out.println("postOnTimeLine param="+httpServletRequest.getParameter("postOnTimeLine"));
			System.out.println("postOnGroup param="+httpServletRequest.getParameter("postOnGroup"));
			System.out.println("postOnTwitter param="+httpServletRequest.getParameter("postOnTwitter"));
			
			boolean postOnTimeLine = Boolean.parseBoolean(httpServletRequest.getParameter("postOnTimeLine"));
			boolean postOnGroups = Boolean.parseBoolean(httpServletRequest.getParameter("postOnGroup"));
			boolean postOnTwitter = Boolean.parseBoolean(httpServletRequest.getParameter("postOnTwitter"));
			System.out.println("postOnTimeLine="+postOnTimeLine);
			System.out.println("postOnGroups="+postOnGroups);
			System.out.println("postOnTwitter="+postOnTwitter);
			mv.getModel().put("postOnTimeLine", postOnTimeLine);
			mv.getModel().put("postOnGroup", postOnGroups);
			mv.getModel().put("postOnTwitter", postOnTwitter);
			List<String> selectedGroups = new ArrayList<>();
			if(postOnGroups || postOnTimeLine){
				FacebookAppPermissionDto facebookAppPermission = aapService.getVoiceOfAapFacebookPermission(oneFacebookAccountDto.getId());
				Facebook facebook = new FacebookTemplate(facebookAppPermission.getToken());
				List<GroupMembership> groups = loadUserGroups(facebook, oneFacebookAccountDto);
				for(GroupMembership oneGroupMembership:groups){
					selectedGroups.add(oneGroupMembership.getId());
				}
			}
			aapService.saveVoiceOfAapSettings(oneFacebookAccountDto.getId(), true, postOnTimeLine, selectedGroups, null, postOnTwitter);
			mv.getModel().put("successmessage", "Your Voice of AAP settings have been saved. Thanks for your contribution");
			CookieUtil.setVoiceOfAapPageCookie(httpServletResponse);
		}catch(Exception ex){
			ex.printStackTrace();
			addErrorInModel(mv, "unable to save setting");
		}
		addGenericValuesInModel(httpServletRequest, mv);
		mv.setViewName(design + "/voa");
		return mv;
	}
	private List<GroupMembership> loadUserGroups(Facebook facebook, FacebookAccountDto oneFacebookAccountDto){
		
		List<GroupMembership> userGroupMembership = facebook.groupOperations().getMemberships();
		aapService.saveFacebookAccountGroups(oneFacebookAccountDto.getId(), userGroupMembership);
		
		return userGroupMembership;
	}

}
