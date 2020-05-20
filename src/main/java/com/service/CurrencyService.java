package com.service;

import com.config.AppConfig;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor
@Service
public class CurrencyService {

   @Autowired
   RestTemplate restTemplate;

    public String getEUR() {
        String urlEur = AppConfig.backendEndpoint + "/currency/eur";
        String EUR = (String) restTemplate.getForObject(urlEur, Object.class);
        return EUR;
    }

    public Double getUSD() {
        String urlUsd = AppConfig.backendEndpoint + "/currency/usd";
        Double USD = (double) restTemplate.getForObject(urlUsd, Object.class);
        return USD;
    }

    public Double getGBP() {
        String urlGbp = AppConfig.backendEndpoint + "/currency/gbp";
        Double GBP = (double) restTemplate.getForObject(urlGbp, Object.class);
        return GBP;
    }
}
