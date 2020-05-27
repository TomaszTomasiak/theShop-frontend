package com.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String adminMail;

    @Value("${spring.mail.password}")
    private String adminMailPassword;
}
