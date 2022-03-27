package com.saddam.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.saddam.microservices.currencyconversionservice.bean.CurrencyConversion;
import com.saddam.microservices.currencyconversionservice.feign.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		String url = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(url,
				CurrencyConversion.class, uriVariables);
		CurrencyConversion response = responseEntity.getBody();

		return new CurrencyConversion(response.getId(), from, to, quantity, response.getConversionMultiple(),
				quantity.multiply(response.getConversionMultiple()), response.getEnvironment() + " rest template");
	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversion response = proxy.retrieveExchangeValue(from, to);

		return new CurrencyConversion(response.getId(), from, to, quantity, response.getConversionMultiple(),
				quantity.multiply(response.getConversionMultiple()), response.getEnvironment() + " feign");
	}
}
