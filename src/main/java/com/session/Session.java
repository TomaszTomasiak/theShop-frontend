package com.session;

import com.domain.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Getter
@Setter
public class Session implements Serializable {
    private User currentUser;
    private Cart cart;
    private Order order;
    private Product product;
    private Item item;
    private ProductGroup productGroup;

    public void cleanAll() {
        currentUser = null;
        cart = null;
        order = null;
        product = null;
        item = null;
        productGroup = null;
    }
}
