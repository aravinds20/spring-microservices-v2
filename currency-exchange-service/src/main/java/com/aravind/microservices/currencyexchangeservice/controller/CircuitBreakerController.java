package com.aravind.microservices.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
//@Retry(name="sample-api", fallbackMethod="hardcodedResponse")
//@CircuitBreaker(name="sample-api", fallbackMethod="hardcodedResponse")
@RateLimiter(name="default")
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	public String sampleApi() {
		logger.info("calling sample api");
//		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:8080/sample", null);
//		return response.getBody();
		return "sample-api";
	}
	
	public String hardcodedResponse(Exception ex) {
		return "fallback-response";
	}

}
