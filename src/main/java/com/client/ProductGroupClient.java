package com.client;

import com.config.TheShopBackendConfig;
import com.domain.ProductGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

import static java.util.Optional.ofNullable;

@Component
public class ProductGroupClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductGroupClient.class);
    private static final String endpoint = TheShopBackendConfig.getGroups();

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(endpoint)
                .build().encode().toUri();
        return url;
    }

    public List<ProductGroup> getAllGroups() {
        try {
            ProductGroup[] usersResponse = restTemplate.getForObject(getUrl(), ProductGroup[].class);
            return Arrays.asList(ofNullable(usersResponse).orElse(new ProductGroup[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public ProductGroup getGroup(Long groupId) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + groupId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, ProductGroup.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ProductGroup();
        }
    }

//    public void deleteGroup(Long groupId) {
//        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + groupId)
//                .build().encode().toUri();
//        try {
//            restTemplate.delete(url);
//        } catch (RestClientException e) {
//            LOGGER.error(e.getMessage(), e);
//        }
//    }

    public ProductGroup createNewGroup(ProductGroup productGroup) {
        return restTemplate.postForObject(getUrl(), productGroup, ProductGroup.class);
    }

    public void updateGroup(Long groupId, ProductGroup productGroup) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + groupId)
                .build().encode().toUri();
        try {
        restTemplate.put(url, productGroup);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

