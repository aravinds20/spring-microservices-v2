package com.aravind.microservices.currencyconversionservice.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aravind.microservices.currencyconversionservice.bean.CurrencyConversion;

@FeignClient(name="currency-exchange", url="localhost:8000")
public interface CurrencyExchangeProxy {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion getCurrencyConverted(@PathVariable("from") String from, 
			@PathVariable("to") String to);
	
}
