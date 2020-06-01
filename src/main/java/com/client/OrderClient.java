package com.client;

import com.config.AppConfig;
import com.domain.Order;
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
public class OrderClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderClient.class);
    private static final String endpoint = AppConfig.getOrders();

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(endpoint)
                .build().encode().toUri();
        return url;
    }

    public List<Order> getAllOrders() {
        try {
            Order[] usersResponse = restTemplate.getForObject(getUrl(), Order[].class);
            return Arrays.asList(ofNullable(usersResponse).orElse(new Order[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public Order getOrder(Long orderId) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + orderId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, Order.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Order();
        }
    }

    public void deleteOrder(Long orderId) {
        URI url = UriComponentsBuilder.fromHttpUrl(endpoint + "/" + orderId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Order createNewOrder(Order order) {
        URI url = getUrl();
        return restTemplate.postForObject(url, order, Order.class);
    }

    public void updateOrder(Long orderId, Order order) {
        URI url = UriComponentsBuilder.fromHttpUrl(endpoint + "/" + orderId)
                .build().encode().toUri();
        try {
            restTemplate.put(url, order);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

