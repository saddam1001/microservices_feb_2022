package com.saddam.microservices.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.saddam.microservices.currencyexchangeservice.bean.CurrencyExchange;
import com.saddam.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {

	Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	CurrencyExchangeRepository repository;

	@Autowired
	private Environment env;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

		logger.info("call received with {} to {}", from, to);
		
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Unbale to find data fro " + from + " and" + to);
		}
//		CurrencyExchange currencyExchange = new CurrencyExchange(exchange.getId(), from, to, exchange.getConversionMultiple());
		String port = env.getProperty("local.server.port", "default");
		currencyExchange.setEnvironment(port);
		return currencyExchange;

	}
}
