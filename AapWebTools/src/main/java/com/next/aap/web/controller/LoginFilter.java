package com.next.aap.web.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.next.aap.web.dto.UserDto;

public class LoginFilter implements Filter{
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        
		UserDto user = BaseController.getLoggedInUserFromSesion(request);
		if(user == null){
            String redirectUrl = request.getServletPath();
            request.getRequestDispatcher("/login?" + BaseController.REDIRECT_URL_PARAM_ID + "=" + redirectUrl).forward(request, res);
	        return;
		}
        chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
