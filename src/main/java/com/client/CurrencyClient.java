package com.client;

import com.config.AppConfig;
import org.springframework.stereotype.Component;

@Component
public class CurrencyClient {

    private String endpoint = AppConfig.getCurrency();
}
