package com.client;

import com.config.AppConfig;
import com.domain.Item;
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
public class ItemClient {

    @Autowired
    RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemClient.class);

    public Set<Item> getAllItems() {

        URI url = getUrl();
        try {
            Item[] usersResponse = restTemplate.getForObject(url, Item[].class);
            return new HashSet<>(Arrays.asList(ofNullable(usersResponse).orElse(new Item[0])));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new HashSet<>();
        }
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/items")
                .build().encode().toUri();

        return url;
    }

    public Item getItem(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/items/" + id)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, Item.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Item();
        }
    }

    public void deleteItem(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/items/" + id)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Item createNewItem(Item item) {
        URI url = getUrl();
        return restTemplate.postForObject(url, item, Item.class);
    }

    public void updateItem(Long id, Item item) {
        URI url = getUrl();UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/items/" + id)
                .build().encode().toUri();
        try {
        restTemplate.put(url, item);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
