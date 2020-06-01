package com.service;

import com.client.CartClient;
import com.domain.Cart;
import com.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartClient cartClient;

    private static CartService cartService;

    public static CartService getInstance() {
        if (cartService == null) {
            cartService = new CartService();
        }
        return cartService;
    }

    public List<Cart> getCarts() {
        return cartClient.getAllCarts();
    }

    public Cart getCart(Long cartId) {
        return cartClient.getCart(cartId);
    }

    public void saveCart(Cart cart) {
        cartClient.createNewCart(cart);
    }

    public void updateCart(Cart cart) {
        cartClient.updateCart(cart.getId(), cart);
    }

    public void deleteCart(Cart cart) {
        cartClient.deleteCart(cart.getId());
    }

    public Cart findCartIdByUserAndListOfItems(Cart cart) {
        if(cart.getOrderId() == null) {
            List<Cart> cartList = getCarts().stream()
                    .filter(c -> c.getUserId().equals(Session.getInstance().getCurrentUser().getId()) && c.getItems().equals(Session.getInstance().getCart().getItems()))
                    .collect(Collectors.toList());
            return cartList.get(0);
        }
        return new Cart();
    }

}
