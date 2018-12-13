package com.example.demoopentracing.controller;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;
import io.opentracing.Tracer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
/**
 * @author Pavol Loffay
 */
@RestController
public class HelloController {
    private static Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    private Random random = new Random();


    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path="/hello", name="hello", method= RequestMethod.GET)
    public String hello() {
        int millis = this.random.nextInt(1000);
      //  Tracer tracer = GlobalTracer.get();
      //  Span span = tracer.activeSpan().setOperationName("hello").setTag("tag", "tag1");
      //  tracer.activeSpan().setTag("random-sleep-millis", String.valueOf(millis));
      //  span.finish();
        return "Hello from Spring Boot!";
    }

    @RequestMapping(path="/chaining", name= "chaining", method = RequestMethod.GET)
    public String chaining() {
        int millis = this.random.nextInt(1000);
    //    Tracer tracer = GlobalTracer.get();
     //   Span span = tracer.activeSpan().setOperationName("chaining").setTag("tag", "chaining");
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/hello", String.class);
      //  tracer.activeSpan().setTag("random-sleep-millis", String.valueOf(millis));
       // span.finish();
        return "Chaining + " + response.getBody();
    }
}
