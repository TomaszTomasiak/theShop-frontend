package com.service;

import com.client.CartClient;
import com.config.TheShopBackendConfig;
import com.domain.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartClient.class);
    private static final String ENDPOINT = TheShopBackendConfig.getCarts();
    private final RestTemplate restTemplate = new RestTemplate();
    private static CartService cartService;

    private CartService() {
    }

    public static CartService getInstance() {
        if (cartService == null) {
            cartService = new CartService();
        }
        return cartService;
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .build().encode().toUri();
        return url;
    }
    public List<Cart> getCarts() {
        URI url = getUrl();
        try {
            Cart[] usersResponse = restTemplate.getForObject(url, Cart[].class);
            return Arrays.asList(ofNullable(usersResponse).orElse(new Cart[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public Cart getCart(Long cartId) {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT + "/" + cartId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, Cart.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Cart();
        }
    }

    public void deleteCart(Long cartId) {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT + "/" + cartId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void saveCart(Cart cart) {
        restTemplate.postForObject(getUrl(), cart, Cart.class);
    }

    public void updateCart(Long cartId, Cart cart) {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT + "/" + cartId)
                .build().encode().toUri();
        try {
            restTemplate.put(url, cart);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
