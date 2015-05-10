package com.next.aap.web.controller;

import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewsController extends AppBaseController {
	
	@RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,HttpServletRequest httpServletRequest, @PathVariable Long newsId) {
		
		addGenericValuesInModel(httpServletRequest, mv);
		addSingleNewsInModel(httpServletRequest, mv, newsId);
        //addUserAcCandidateInModel(httpServletRequest, mv);
        mv.setViewName(design + "/news");
        System.out.println("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables=" + mv.getModel().get("HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE"));
        for (Entry<String, Object> oneEntry : mv.getModel().entrySet()) {
            System.out.println(oneEntry.getKey() + " = " + oneEntry.getValue());
        }
		return mv;
		
	}
	
}
