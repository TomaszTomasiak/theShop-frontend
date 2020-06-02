package com.service;

import com.client.EmailValidatorApiClient;
import com.domain.externalDto.EmailValidatorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailValidatorApiService {

    @Autowired
    private EmailValidatorApiClient client;

    public EmailValidatorDto checkIfEmailValid(String mail) {
        return client.validateEmail(mail);
    }
}
