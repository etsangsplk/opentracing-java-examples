package com.example.demoopentracing.interceptor;


import io.opentracing.Span;
import io.opentracing.contrib.spring.web.interceptor.HandlerInterceptorSpanDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestSpanDecorator implements HandlerInterceptorSpanDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestSpanDecorator.class);

    @Override
    public void onPreHandle(HttpServletRequest httpServletRequest, Object handler, Span span) {
        LOGGER.info("Request Span decorator onPrehandle");

    }

    @Override
    public void onAfterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex, Span span) {
        LOGGER.info("Request Span decorator onAfterCompletion");

    }
}
