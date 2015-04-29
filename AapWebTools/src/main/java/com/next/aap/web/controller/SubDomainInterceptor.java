package com.next.aap.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.next.aap.web.cache.LocationCacheDbImpl;
import com.next.aap.web.dto.StateDto;


public class SubDomainInterceptor implements HandlerInterceptor {

    @Autowired
    private LocationCacheDbImpl locationCache;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Domain name " + request.getServerName());
        StateDto state = locationCache.getStatesByDomain(request.getServerName().toLowerCase());
        System.out.println("state : " + state);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
