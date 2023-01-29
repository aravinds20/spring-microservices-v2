package com.aravind.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aravind.microservices.currencyconversionservice.bean.CurrencyConversion;
import com.aravind.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {

	private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getCurrencyConverted(@PathVariable("from") String from, 
			@PathVariable("to") String to, 
			@PathVariable("quantity") BigDecimal quantity) {
		
		logger.info("reached getCurrencyConverted with {}, {} and {}", from,to, quantity);
		Map<String, String> uri = new HashMap<>();
		uri.put("from", from);
		uri.put("to", to);
		
		ResponseEntity<CurrencyConversion> response =new RestTemplate().getForEntity
				("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uri);
		
		CurrencyConversion currencyConversion = response.getBody();
		
		return new CurrencyConversion(1000L, from, to , quantity, currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment());
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getCurrencyConvertedFeign(@PathVariable("from") String from, 
			@PathVariable("to") String to, 
			@PathVariable("quantity") BigDecimal quantity) {
		
		logger.info("reached getCurrencyConvertedFeign with {}, {} and {}", from,to, quantity);
		CurrencyConversion currencyConversion = proxy.getCurrencyConverted(from, to);
		
		return new CurrencyConversion(1000L, from, to , quantity, currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment());
	}
	
}
