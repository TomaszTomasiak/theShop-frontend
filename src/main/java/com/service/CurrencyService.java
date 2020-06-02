package com.service;

import com.client.NbpApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    private NbpApiClient client;

    @Autowired
    private TheShopService theShopService;

    private static CurrencyService currencyService;

    private CurrencyService() {
    }

    public static CurrencyService getInstance() {
        if (currencyService == null) {
            currencyService = new CurrencyService();
        }
        return currencyService;
    }

    public Double getEUR() {
        return client.getCurrencyFactor("eur");
    }

    public Double getUSD() {
        return client.getCurrencyFactor("usd");
    }

    public Double getGBP() {
        return client.getCurrencyFactor("gbp");
    }

    public Double valueEUR(Double valuePln) {
        double result = valuePln / getEUR();
        return theShopService.roundToDecimal(result, 2);
    }

    public Double valueGBP(Double valuePln) {
        double result = valuePln / getGBP();
        return theShopService.roundToDecimal(result, 2);
    }

    public Double valueUSD(Double valuePln) {
        double result = valuePln / getUSD();
        return theShopService.roundToDecimal(result, 2);
    }
}
