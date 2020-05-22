package com.client;


import com.config.AppConfig;
import com.domain.Product;
import com.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Optional.ofNullable;


@Component
public class ProductClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductClient.class);

    @Autowired
    private RestTemplate restTemplate;

    public Set<Product> getAllProducts() {

        URI url = getUrl();
        try {
            Product[] usersResponse = restTemplate.getForObject(url, Product[].class);
            return new HashSet<Product>(Arrays.asList(ofNullable(usersResponse).orElse(new Product[0])));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new HashSet<>();
        }
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/products")
                .build().encode().toUri();
        return url;
    }

    public Product getProduct(Long productId) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/products/" + productId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, Product.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Product();
        }
    }

    public void deleteProduct(Long productId) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/products/" + productId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Product createNewProduct(Product product) {
        URI url = getUrl();
        return restTemplate.postForObject(url, product, Product.class);
    }

    public void updateProduct(Long productId, Product product) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/products/" + productId)
                .build().encode().toUri();
        try {
        restTemplate.put(url, product);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

