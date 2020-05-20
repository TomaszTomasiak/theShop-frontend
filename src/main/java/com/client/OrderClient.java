package com.client;

import com.config.AppConfig;
import com.domain.Order;
import com.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class OrderClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderClient.class);
        private RestTemplate restTemplate = new RestTemplate();

    public Set<Order> getAllOrders() {

        URI url = getUrl();
        try {
            Order[] usersResponse = restTemplate.getForObject(url, Order[].class);
            return new HashSet<>(Arrays.asList(ofNullable(usersResponse).orElse(new Order[0])));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new HashSet<>();
        }
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/orders")
                .build().encode().toUri();
        return url;
    }

    public Order getOrder(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/orders/" + userId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, Order.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Order();
        }
    }

    public void deleteOrder(Long orderId) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/orders/" + orderId)
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

    public void updateOrder(Order order) {
        URI url = getUrl();
        restTemplate.put(url, order);
    }
}

