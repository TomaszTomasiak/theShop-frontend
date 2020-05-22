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
   private RestTemplate restTemplate;

    public Double getEUR() {
        String urlEur = AppConfig.backendEndpoint + "/currency/eur";
        Double EUR = (Double) restTemplate.getForObject(urlEur, Object.class);
        return EUR;
    }

    public Double getUSD() {
        String urlUsd = AppConfig.backendEndpoint + "/currency/usd";
        Double USD = (Double) restTemplate.getForObject(urlUsd, Object.class);
        return USD;
    }

    public Double getGBP() {
        String urlGbp = AppConfig.backendEndpoint + "/currency/gbp";
        Double GBP = (Double) restTemplate.getForObject(urlGbp, Object.class);
        return GBP;
    }

    public Double priceEUR (Double pricePln) {
        Double result = pricePln / getEUR();
        return roundToDecimal(result, 2);
    }

    public Double priceGBP (Double pricePln) {
        Double result = pricePln / getGBP();
        return roundToDecimal(result, 2);
    }

    public Double priceUSD (Double pricePln) {
        Double result = pricePln / getUSD();
        return roundToDecimal(result, 2);
    }

    private static double roundToDecimal(double num, int dec) {
        int multi = (int) Math.pow(10, dec);
        int temp = (int) Math.round(num * multi);
        return (double) temp / multi;
    }
}
