package com.service;

import com.client.CartClient;
import com.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartService {

    @Autowired
    private CartClient cartClient;

    private Set<Cart> carts;

    public Set<Cart> getCarts() {
        carts = cartClient.getAllCarts();
        return carts;
    }

    public Cart getCart(Long cartId) {
        return cartClient.getCart(cartId);
    }

    public void save(Cart cart) {
        cartClient.createNewCart(cart);
    }

    public void update(Cart cart) {
        cartClient.updateCart(cart.getId(), cart);
    }

    public void delete(Cart cart) {
        cartClient.deleteCart(cart.getId());
    }

//    public void addItemToCart(Cart cart) {
//        cartClient.addUpdateRemoveItemFromCart(cart, cart.getId());
//    }
}
