package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

@Controller
public class HomeNewController extends AppBaseJsonController {
	
    @RequestMapping(value = "/indexnew.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
			HttpServletRequest httpServletRequest) {
		try{
            JsonObject contextJsonObject = new JsonObject();
            addGenericValuesInModel(httpServletRequest, contextJsonObject);
            System.out.println("Server=[" + httpServletRequest.getServerName() + "]");
            if (httpServletRequest.getServerName().equals("www.swarajabhiyan.org")) {
                mv.setViewName("handlebar/index");
            } else {
                mv.setViewName("handlebar/index2");
            }

            addNewsInModel(httpServletRequest, contextJsonObject);
            addUserAcCandidateInModel(httpServletRequest, contextJsonObject);
			
		}catch(Exception ex){
			logger.error("Unable to Generate Main Page",ex);
		}
		return mv;
	}
	
}
