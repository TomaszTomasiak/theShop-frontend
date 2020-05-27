package com.service;

import com.client.CartClient;
import com.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartClient cartClient;

    private List<Cart> carts;

    public List<Cart> getCarts() {
        carts = cartClient.getAllCarts();
        return carts;
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

//    public void addItemToCart(Cart cart) {
//        cartClient.addUpdateRemoveItemFromCart(cart, cart.getId());
//    }
}
