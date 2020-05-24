package com.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfig {
   @Value("${theShop.api.endpoint.prod}")
   private String backendEndpoint;
}
