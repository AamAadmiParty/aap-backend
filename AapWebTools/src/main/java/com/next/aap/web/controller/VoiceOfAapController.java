package com.next.aap.web.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
		if (loginAccounts == null || loginAccounts.getFacebookAccounts() == null || loginAccounts.getFacebookAccounts().isEmpty()) {
			// buildAndRedirect to facebook Login Account
			logger.info("User is not logegd In to facebook, So redirecting to facebook login");
			return redirect(mv, "/login/voa/facebook?group=timeline&" + REDIRECT_URL_PARAM_ID + "=voa.html");
		}
		FacebookAccountDto oneFacebookAccountDto = loginAccounts.getFacebookAccounts().get(0);

		FacebookAppPermissionDto facebookAppPermission = aapService.getVoiceOfAapFacebookPermission(oneFacebookAccountDto.getId());
		if (facebookAppPermission == null) {
			// buildAndRedirect to facebook Login Account
			logger.info("User is logegd In to facebook, but have not given permission to voice of aap");
			return redirect(mv, "/login/voa/facebook?group=timeline&" + REDIRECT_URL_PARAM_ID + "=voa.html");
		}

		Calendar currectTime = Calendar.getInstance();
		currectTime.add(Calendar.DATE, 10);
		if (currectTime.after(facebookAppPermission.getExpireTime())) {
			// buildAndRedirect to facebook Login Account
			logger.info("User is logegd In to facebook,and given permission to voice of aap but permission about to expire");
			return redirect(mv, "/login/voa/facebook?group=timeline&" + REDIRECT_URL_PARAM_ID + "=voa.html");
		}

		Facebook facebook = new FacebookTemplate(facebookAppPermission.getToken());
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
				postOnGroup = false;
			} else {
				postOnGroup = true;
				// get all facebook groups of user
				loadUserGroups(facebook, oneFacebookAccountDto);

			}
		} catch (RevokedAuthorizationException ex) {
			logger.info("User has revoked permission");
			beVoiceOfAap = false;
		}
		mv.setViewName(design + "/voa");
		return mv;
	}
	private List<GroupMembership> loadUserGroups(Facebook facebook, FacebookAccountDto oneFacebookAccountDto){
		
		List<GroupMembership> userGroupMembership = facebook.groupOperations().getMemberships();
		aapService.saveFacebookAccountGroups(oneFacebookAccountDto.getId(), userGroupMembership);
		VoiceOfAapData voiceOfAapData = aapService.getVoiceOfAapSetting(oneFacebookAccountDto.getId());
		return userGroupMembership;
	}

}
