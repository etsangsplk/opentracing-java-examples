package com.example.demoopentracing;

import com.example.demoopentracing.interceptor.RequestSpanDecorator;
import io.opentracing.contrib.spring.web.interceptor.HandlerInterceptorSpanDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.splunk.observation.tracing.LightstepTracer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoOpentracingApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(LightstepTracer.class);

	private final static String serviceName = "DemoOpentracingApplication";

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
	    RestTemplate restTemplate = restTemplateBuilder.build();
	    return restTemplate;
	}


	@Bean
	public List<HandlerInterceptorSpanDecorator> spanDecorators() {
		LOGGER.info("This may be the place to register our own span decorators to fix the span");
		// We may not need Any of those tracing interceptors.
		// * https://github.com/opentracing-contrib/java-spring-web/blob/0933caece7de11ae9f9d411113a5473d35288cf8/opentracing-spring-web/src/main/java/io/opentracing/contrib/spring/web/interceptor/TracingHandlerInterceptor.java#L41
		//
		// *  https://github.com/opentracing-contrib/java-spring-web/tree/0933caece7de11ae9f9d411113a5473d35288cf8/opentracing-spring-web/src/main/java/io/opentracing/contrib/spring/web/interceptor
		//
		return new ArrayList<>();
	}

	@Bean
	public io.opentracing.Tracer lightstepTracer() {
        LOGGER.info("Initializing Tracer bean.");
		io.opentracing.Tracer tracer = LightstepTracer.getTracer(serviceName);
        LOGGER.info("Initialized Tracer bean.");
       return tracer;
	}


	public static void main(String[] args) {
		SpringApplication.run(DemoOpentracingApplication.class, args);
	}
}
