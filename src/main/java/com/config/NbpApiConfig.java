package com.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class NbpApiConfig {

    @Value("${nbp.api.endpoint}")
    private String nbpApiEndpoint;
}
