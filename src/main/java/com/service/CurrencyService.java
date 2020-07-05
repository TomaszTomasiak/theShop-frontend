package com.service;

import com.config.TheShopBackendConfig;
import org.springframework.web.client.RestTemplate;


public class CurrencyService {

    private final RestTemplate restTemplate= new RestTemplate() ;
    private final TheShopService theShopService = TheShopService.getInstance();

    private static final String ENDPOINT = TheShopBackendConfig.getCurrency();

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
        String url = ENDPOINT + "/eur";
        Double EUR = (Double) restTemplate.getForObject(url, Object.class);
        return EUR;
    }

    public Double getUSD() {
        String url = ENDPOINT + "/usd";
        Double USD = (Double) restTemplate.getForObject(url, Object.class);
        return USD;
    }

    public Double getGBP() {
        String url = ENDPOINT + "/gbp";
        Double GBP = (Double) restTemplate.getForObject(url, Object.class);

        return GBP;
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
