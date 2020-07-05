package com.service;

import com.config.TheShopBackendConfig;
import com.domain.externalDto.EmailValidatorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MailValidatorApiService {

    @Autowired
    RestTemplate restTemplate;

    private static final String ENDPOINT = TheShopBackendConfig.getMailValidation();

    public EmailValidatorDto checkIfEmailValid(String mail) {
        String url = ENDPOINT + "/" + mail;
        return restTemplate.getForObject(url, EmailValidatorDto.class);
    }
}
