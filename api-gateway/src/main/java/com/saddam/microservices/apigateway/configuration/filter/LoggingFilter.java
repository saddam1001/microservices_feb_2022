package com.saddam.microservices.apigateway.configuration.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {
	
	Logger log = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// TODO Auto-generated method stub
		log.info("path of request received -> {}", exchange.getRequest().getPath());
		return chain.filter(exchange);
	}

}
