package com.example.demoopentracing.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
   private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

   @Override
   public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) throws Exception {
      LOGGER.info("Prehandle is called inside {}.", this.getClass().getName());
      return true;
   }
   @Override
   public void postHandle(HttpServletRequest request,
                          HttpServletResponse response,
                          Object handler,
                          ModelAndView modelAndView) throws Exception {

      LOGGER.info("PostHandle is called inside {}.", this.getClass().getName());
      System.out.println("Post Handle method is YYY Calling");
   }
   @Override
   public void afterCompletion (HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception exception) throws Exception {
      LOGGER.info("afterCompletion is called inside {}.", this.getClass().getName());
      System.out.println("Request and Response is YYY completed");
   }
}