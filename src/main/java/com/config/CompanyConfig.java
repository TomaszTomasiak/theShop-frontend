package com.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CompanyConfig {

    @Value("${company.name}")
    private String companyName;

    @Value("${company.website}")
    private String companyWebsite;

    @Value("${company.mail}")
    private String companyMail;
}
