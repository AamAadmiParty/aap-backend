package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.cache.CandidateCacheImpl;
import com.next.aap.web.dto.CandidateDto;

@Controller
public class CandidateController extends AppBaseController {
	
	@Autowired
	private CandidateCacheImpl candidateCacheImpl;
	
	@RequestMapping(value = "/candidate/{urlPart1}/{urlPart2}.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,HttpServletRequest httpServletRequest,
			@PathVariable String urlPart1, @PathVariable String urlPart2) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		CandidateDto candidateDto = candidateCacheImpl.getCandidate(urlPart1, urlPart2);
		System.out.println("candidate = "+candidateDto);
		mv.getModel().put("candidate", candidateDto);
		mv.setViewName(design+"/candidate");
		return mv;
	}
	
}
