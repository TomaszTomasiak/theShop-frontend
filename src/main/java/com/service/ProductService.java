package com.service;

import com.config.TheShopBackendConfig;
import com.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;


public class ProductService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final String ENDPOINT = TheShopBackendConfig.getProducts();
    private static ProductService productService;

    private ProductService() {
    }

    public static ProductService getInstance() {
        if (productService == null) {
            productService = new ProductService();
        }
        return productService;
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .build().encode().toUri();
        return url;
    }

    public List<Product> getAllProducts() {
        try {
            Product[] usersResponse = restTemplate.getForObject(getUrl(), Product[].class);
            return Arrays.asList(ofNullable(usersResponse).orElse(new Product[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    public Product getProduct(Long productId) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + productId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, Product.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Product();
        }
    }

    public void deleteProduct(Long productId) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + productId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Product saveProduct (Product product) {
        URI url = getUrl();
        return restTemplate.postForObject(url, product, Product.class);
    }

    public void updateProduct(Long productId, Product product) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + productId)
                .build().encode().toUri();
        try {
            restTemplate.put(url, product);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public List<Product> findProductByName(String name) {
        return getAllProducts().stream()
                .filter(product -> product.getName().contains(name))
                .collect(Collectors.toList());
    }

    public List<Product> findProductsWithPriceBeetween(Double from, Double to) {
        if (from == null) {
            from = 0.0;
        } else if (to == null) {
            to = Double.POSITIVE_INFINITY;
        }
        Double finalFrom = from;
        Double finalTo = to;
        return getAllProducts().stream()
                .filter(product -> product.getPrice() >= finalFrom && product.getPrice() <= finalTo)
                .collect(Collectors.toList());
    }
}
