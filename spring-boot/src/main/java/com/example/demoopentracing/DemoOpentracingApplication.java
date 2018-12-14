package com.example.demoopentracing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.splunk.observation.tracing.LightstepTracer;

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
