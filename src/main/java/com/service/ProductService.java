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

import static java.util.Optional.ofNullable;

@Service
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

//    public List<Product> getAllProducts() {
//        return productClient.getAllProducts();
//    }

//    public Product getProduct(Long id) {
//        return productClient.getProduct(id);
//    }

//    public void saveProduct(Product product) {
//        productClient.createNewProduct(product);
//    }

//    public void updateProduct(Product product) {
//        productClient.updateProduct(product.getId(), product);
//    }

//    public void deleteProduct(Product product) {
//        productClient.deleteProduct(product.getId());
//    }

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

    public void updateProduct(Product product) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + productId)
                .build().encode().toUri();
        try {
            restTemplate.put(url, product);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
