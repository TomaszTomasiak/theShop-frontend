package com.config;

import org.springframework.stereotype.Component;

@Component
public class AppConfig {

   private static AppConfig appConfig;

   private static final String backendEndpoint = "http://localhost:4600/api/v1/";

   private AppConfig() {

   }

   public static AppConfig getInstance() {
      if(appConfig == null) {
         appConfig = new AppConfig();
      }
      return appConfig;
   }

   public String getBackendEndpoint() {
      return backendEndpoint;
   }
}
