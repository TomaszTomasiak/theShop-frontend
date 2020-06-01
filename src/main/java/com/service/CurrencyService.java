package com.service;

import com.config.AppConfig;
import com.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

   @Autowired
   private RestTemplate restTemplate;

    private static CurrencyService currencyService;
    private TheShopService theShopService = TheShopService.getInstance();

    private CurrencyService() {
    }

    public static CurrencyService getInstance() {
        if (currencyService == null) {
            currencyService = new CurrencyService();
        }
        return currencyService;
    }

   private static final String url = AppConfig.getCurrency();

    public Double getEUR() {
        String urlEur = url + "/eur";
        Double EUR = (Double) restTemplate.getForObject(urlEur, Object.class);
        return EUR;
    }

    public Double getUSD() {
        String urlUsd = url + "/usd";
        Double USD = (Double) restTemplate.getForObject(urlUsd, Object.class);
        return USD;
    }

    public Double getGBP() {
        String urlGbp = url + "/gbp";
        Double GBP = (Double) restTemplate.getForObject(urlGbp, Object.class);
        return GBP;
    }

    public Double valueEUR(Double valuePln) {
        Double result = valuePln / getEUR();
        return theShopService.roundToDecimal(result, 2);
    }

    public Double valueGBP(Double valuePln) {
        Double result = valuePln / getGBP();
        return theShopService.roundToDecimal(result, 2);
    }

    public Double valueUSD(Double valuePln) {
        Double result = valuePln / getUSD();
        return theShopService.roundToDecimal(result, 2);
    }

}
