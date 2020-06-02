package com.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EmailValidatorApiConfig {

    @Value("${email.validator.api.endpoint}")
    private String emailValidatorApiEndpoint;

    @Value("${email.validator.api.host}")
    private String emailValidatorApiHost;

    @Value("${email.validator.api.key}")
    private String emailValidatorApiKey;
}
