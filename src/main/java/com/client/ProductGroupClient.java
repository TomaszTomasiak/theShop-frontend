package com.client;


import com.config.AppConfig;
import com.domain.ProductGroup;
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
public class ProductGroupClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductGroupClient.class);

    @Autowired
    private RestTemplate restTemplate;

    public Set<ProductGroup> getAllGroups() {

        URI url = getUrl();
        try {
            ProductGroup[] usersResponse = restTemplate.getForObject(url, ProductGroup[].class);
            return new HashSet<>(Arrays.asList(ofNullable(usersResponse).orElse(new ProductGroup[0])));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new HashSet<>();
        }
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/groups")
                .build().encode().toUri();

        return url;
    }

    public ProductGroup getGroup(Long groupId) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/groups/" + groupId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, ProductGroup.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ProductGroup();
        }
    }

    public void deleteGroup(Long groupId) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/groups/" + groupId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public ProductGroup createNewGroup(ProductGroup productGroup) {
        URI url = getUrl();
        return restTemplate.postForObject(url, productGroup, ProductGroup.class);
    }

    public void updateGroup(Long groupId, ProductGroup productGroup) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/groups/" + groupId)
                .build().encode().toUri();
        try {
        restTemplate.put(url, productGroup);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

