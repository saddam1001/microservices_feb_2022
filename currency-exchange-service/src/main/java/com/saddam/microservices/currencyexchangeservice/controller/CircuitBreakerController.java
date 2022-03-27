package com.saddam.microservices.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
//	@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
//	@CircuitBreaker(name = "sample-api", fallbackMethod = "hardcodedResponse")
//	@RateLimiter(name = "sample-api")
	// 10s -> 1000 calls to this api (only 1000 calls allowed to this api in 10 seconds)
//	@RateLimiter(name = "sample-api", fallbackMethod = "hardcodedResponse")
	@Bulkhead(name = "sample-api")
	public String sampleApi() {
		logger.info("Sample api call received");
//		ResponseEntity<String> forEntity = new RestTemplate()
//				.getForEntity("http://localhost:8080/some-dummy-url", String.class);
//		
//		return forEntity.getBody();
		
		return "Sample-Api";
	}
	
	public String hardcodedResponse(Exception ex) {
//		logger.info("param -> {} ", param1);
		return "hardcodedResponse";
	}
}
