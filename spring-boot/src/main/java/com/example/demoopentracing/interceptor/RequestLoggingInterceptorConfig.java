package com.example.demoopentracing.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class RequestLoggingInterceptorConfig extends WebMvcConfigurerAdapter {
   @Autowired
   RequestLoggingInterceptor requestLoggingInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(requestLoggingInterceptor);
   }
}
