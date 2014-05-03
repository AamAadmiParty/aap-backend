package com.next.aap.web.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.aws.AwsQueueManager;
import com.next.aap.web.cache.PollItemCacheImpl;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.util.CookieUtil;

@Controller
public class PollController extends AppBaseController {

	@Autowired
	private PollItemCacheImpl pollItemCacheImpl;
	
	@Autowired
	private AwsQueueManager awsQueueManager;
	
	@RequestMapping(value = "/poll/{pollId}", method = RequestMethod.GET)
	public ModelAndView showPollPage(ModelAndView mv, HttpServletRequest httpServletRequest, @PathVariable Long pollId) {
		addGenericValuesInModel(httpServletRequest, mv);
		mv.setViewName(design+"/pollpage");
		
		addPollIntoModel(pollId, mv);
		
		boolean userAlreadyPolled = false;
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		if(loggedInUser != null){
			//Check if this user has already polled
			
			String cookieValue = CookieUtil.getUserPollCookie(httpServletRequest, loggedInUser.getId(), pollId);
			System.out.println("cookieValue = "+ cookieValue);
			if(cookieValue == null){
				String answerFromNoSql = getUserPollQuestion(loggedInUser.getId(), pollId);
				if(answerFromNoSql != null){
					System.out.println("answerFromNoSql = "+ answerFromNoSql);
					userAlreadyPolled = true;
				}
			}else{
				userAlreadyPolled = true;
			}
			
		}
		mv.getModel().put("userAlreadyPolled", userAlreadyPolled);
		if(userAlreadyPolled){
			// then get result and add google chart url
			addPollChartToModel(loggedInUser.getId(), pollId, mv);
		}
		return mv;
	}


	@RequestMapping(value = "/poll/{pollId}", method = RequestMethod.POST)
	public ModelAndView saveUserProfile(ModelAndView mv, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable Long pollId) {
		addGenericValuesInModel(httpServletRequest, mv);
		mv.setViewName(design+"/pollpage");
		addPollIntoModel(pollId, mv);
		
		UserDto loggedInUser = getLoggedInUserFromSesion(httpServletRequest);
		
		Long questionId = getLongPramater(httpServletRequest, "question", 0);
		Long answerId = getLongPramater(httpServletRequest, "answer", 0);
		
		if(loggedInUser == null){
			addErrorInModel(mv, "Please login to vote");
		}
		if(questionId <= 0){
			addErrorInModel(mv, "Invalid Poll");
		}
		if(answerId <= 0){
			addErrorInModel(mv, "Please select an answer");
		}
		if(!isValidInput(mv)){
			
			return mv;
		}
		//Set answer cookie
		CookieUtil.setUserPollCookie(httpServletResponse, loggedInUser.getId(), pollId, answerId);
		
		awsQueueManager.sendPollVoteMessage(loggedInUser.getId(), questionId, answerId);
		mv.getModel().put("userAlreadyPolled", true);
		addPollChartToModel(loggedInUser.getId(), questionId, mv);
		
		return mv;
	}

}
