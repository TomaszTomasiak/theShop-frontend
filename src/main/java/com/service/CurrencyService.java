package com.service;

import com.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

   @Autowired
   private RestTemplate restTemplate;

   @Autowired
   private AppConfig appConfig;

    public Double getEUR() {
        String urlEur = appConfig.getBackendEndpoint() + "/currency/eur";
        Double EUR = (Double) restTemplate.getForObject(urlEur, Object.class);
        return EUR;
    }

    public Double getUSD() {
        String urlUsd = appConfig.getBackendEndpoint() + "/currency/usd";
        Double USD = (Double) restTemplate.getForObject(urlUsd, Object.class);
        return USD;
    }

    public Double getGBP() {
        String urlGbp = appConfig.getBackendEndpoint() + "/currency/gbp";
        Double GBP = (Double) restTemplate.getForObject(urlGbp, Object.class);
        return GBP;
    }

    public Double valueEUR(Double pricePln) {
        Double result = pricePln / getEUR();
        return roundToDecimal(result, 2);
    }

    public Double valueGBP(Double pricePln) {
        Double result = pricePln / getGBP();
        return roundToDecimal(result, 2);
    }

    public Double valueUSD(Double pricePln) {
        Double result = pricePln / getUSD();
        return roundToDecimal(result, 2);
    }

    private static double roundToDecimal(double num, int dec) {
        int multi = (int) Math.pow(10, dec);
        int temp = (int) Math.round(num * multi);
        return (double) temp / multi;
    }
}
