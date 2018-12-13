package com.example.demoopentracing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.splunk.observation.tracing.ConcreteTracer;

import com.uber.jaeger.Configuration;
import com.uber.jaeger.samplers.ProbabilisticSampler;

// import brave.Tracing;
// import brave.opentracing.BraveTracer;s
// import brave.sampler.Sampler;
// import zipkin.Span;
// import zipkin.reporter.AsyncReporter;
// import zipkin.reporter.Encoding;
// import zipkin.reporter.okhttp3.OkHttpSender;

@SpringBootApplication
public class DemoOpentracingApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConcreteTracer.class);

	private final static String serviceName = "DemoOpentracingApplication";

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

//	@Bean
//	public io.opentracing.Tracer jaegerTracer() {
//		return new Configuration("spring-boot", new Configuration.SamplerConfiguration(ProbabilisticSampler.TYPE, 1),
//				new Configuration.ReporterConfiguration())
//				.getTracer();
//	}

	@Bean
	public io.opentracing.Tracer lightstepTracer() {
        LOGGER.info("Initializing Tracer bean.");
		io.opentracing.Tracer tracer = ConcreteTracer.getTracer(serviceName);
        LOGGER.info("Initialized Tracer bean.");
       return tracer;
	}


	// @Bean
	// public io.opentracing.Tracer zipkinTracer() {
	// 	OkHttpSender okHttpSender = OkHttpSender.builder()
	// 			.encoding(Encoding.JSON)
	// 			.endpoint("http://localhost:9411/api/v1/spans")
	// 			.build();
	// 	AsyncReporter<Span> reporter = AsyncReporter.builder(okHttpSender).build();
	// 	Tracing braveTracer = Tracing.newBuilder()
	// 			.localServiceName("spring-boot")
	// 			.reporter(reporter)
	// 			.traceId128Bit(true)
	// 			.sampler(Sampler.ALWAYS_SAMPLE)
	// 			.build();
	// 	return BraveTracer.create(braveTracer);
	// }

	public static void main(String[] args) {
		SpringApplication.run(DemoOpentracingApplication.class, args);
	}
}
