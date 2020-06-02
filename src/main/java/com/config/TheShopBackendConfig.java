package com.config;

import org.springframework.stereotype.Component;

@Component
public class TheShopBackendConfig {

   private static TheShopBackendConfig theShopBackendConfig;

   private static final String BACKEND_ENDPOINT = "http://localhost:4600/api/v1/";
   private static final String CARTS = "carts";
   private static final String PRODUCTS = "products";
   private static final String ITEMS = "items";
   private static final String ORDERS = "orders";
   private static final String GROUPS = "groups";
   private static final String USERS = "users";

   private TheShopBackendConfig() {
   }

   public static TheShopBackendConfig getInstance() {
      if (theShopBackendConfig == null) {
         theShopBackendConfig = new TheShopBackendConfig();
      }
      return theShopBackendConfig;
   }

   public static String getBackendEndpoint() {
      return BACKEND_ENDPOINT;
   }

   public static String getCarts() {
      return BACKEND_ENDPOINT + CARTS;
   }

   public static String getProducts() {
      return BACKEND_ENDPOINT + PRODUCTS;
   }

   public static String getItems() {
      return BACKEND_ENDPOINT + ITEMS;
   }

   public static String getOrders() {
      return BACKEND_ENDPOINT + ORDERS;
   }

   public static String getGroups() {
      return BACKEND_ENDPOINT + GROUPS;
   }

   public static String getUsers() {
      return BACKEND_ENDPOINT + USERS;
   }

}
