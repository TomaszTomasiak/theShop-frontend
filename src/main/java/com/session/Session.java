package com.session;

import com.domain.*;
import com.domain.ProductOnCart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class Session implements Serializable {

    private static Session session;

    private User currentUser;
    private Order order;
    private Product product;
    private Item item;
    private Cart cart;
    private ProductGroup productGroup;
    private List<ProductOnCart> listOfProductsOnCart;

    private Session() {
        currentUser = new User();
        order = new Order();
        product = new Product();
        item = new Item();
        cart = new Cart();
        productGroup = new ProductGroup();
        listOfProductsOnCart = new ArrayList<>();
    }

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public void cleanAll() {
        currentUser = new User();
        cart = new Cart();
        order = new Order();
        product = new Product();
        item = new Item();
        productGroup = new ProductGroup();

        for (ProductOnCart tmp : listOfProductsOnCart) {
            listOfProductsOnCart.remove(tmp);
        }
    }
}
