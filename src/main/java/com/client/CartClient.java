package com.client;

import com.config.AppConfig;
import com.domain.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

import static java.util.Optional.ofNullable;

@Component
public class CartClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartClient.class);
    private static final String ENDPOINT = AppConfig.getCarts();
    private final RestTemplate restTemplate = new RestTemplate();

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .build().encode().toUri();
        return url;
    }
    public List<Cart> getAllCarts() {
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

    public void createNewCart(Cart cart) {
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

//    public void addUpdateRemoveItemFromCart(Cart cart, Long itemId) {
//        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "/carts/" + cart.getId() + "&" + itemId)
//                .build().encode().toUri();
//        restTemplate.put(url, cart);
//    }

}

