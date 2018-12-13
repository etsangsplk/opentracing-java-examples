package com.example.demoopentracing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
        return "Hello from Spring Boot!";
    }

    @RequestMapping(path="/chaining", name= "chaining", method = RequestMethod.GET)
    public String chaining() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/hello", String.class);
        return "Chaining + " + response.getBody();
    }

    @RequestMapping(path="/{tenant}/operationA", name="operationA", method = RequestMethod.GET)
    public String operationA() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/hello", String.class);
        return "Chaining + " + response.getBody();
    }
}
