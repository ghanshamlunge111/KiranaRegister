package com.kiranaregister.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kiranaregister.entity.ExchangeRatesResponse;

@Service
public class CurrencyConversionService {
    private static final String API_BASE_URL = "https://api.fxratesapi.com/latest";

    private final RestTemplate restTemplate;

    @Autowired
    public CurrencyConversionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExchangeRatesResponse fetchLatestExchangeRates() {
     
    	String apiUrl = API_BASE_URL;
        ExchangeRatesResponse response = restTemplate.getForObject(apiUrl, ExchangeRatesResponse.class);

        return response;
    }
}
