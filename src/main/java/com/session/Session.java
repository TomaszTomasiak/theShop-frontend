package com.session;

import com.domain.*;
import com.form.ProductsList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class Session implements Serializable{

    private UserDto currentUserDto; // = new UserDto(1L, "Adam", "Sandler", "abc@abc.com", "123456789", "password");
    private Order order;
    private Product product;
    private Item item;
    private Cart cart;
    private ProductGroup productGroup;
    private List<ProductsList> listOfProductsOnCart;

    private Session() {
        this.currentUserDto = new UserDto();
        this.order = new Order();
        this.product = new Product();
        this.item = new Item();
        this.cart = new Cart();
        this.productGroup = new ProductGroup();
        this.listOfProductsOnCart = new ArrayList<>();
    }

    public void cleanAll() {
        currentUserDto = null;
        cart = null;
        order = null;
        product = null;
        item = null;
        productGroup = null;

        for (ProductsList tmp:listOfProductsOnCart) {
            listOfProductsOnCart.remove(tmp);
        }
    }
}
