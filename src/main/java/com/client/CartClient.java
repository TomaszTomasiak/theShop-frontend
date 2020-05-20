package com.client;


import com.config.AppConfig;
import com.domain.Cart;
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
public class CartClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartClient.class);
        private RestTemplate restTemplate = new RestTemplate();

    public Set<Cart> getAllCarts() {

        URI url = getUrl();
        try {
            Cart[] usersResponse = restTemplate.getForObject(url, Cart[].class);
            return new HashSet<>(Arrays.asList(ofNullable(usersResponse).orElse(new Cart[0])));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new HashSet<>();
        }
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/carts")
                .build().encode().toUri();
        return url;
    }

    public Cart getCart(Long cartId) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/carts/" + cartId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, Cart.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Cart();
        }
    }

    public void deleteCart(Long cartId) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/carts/" + cartId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Cart createNewCart(Cart cart) {
        URI url = getUrl();
        return restTemplate.postForObject(url, cart, Cart.class);
    }

    public void updateCart(Cart cart) {
        URI url = getUrl();
        restTemplate.put(url, cart);
    }
}

