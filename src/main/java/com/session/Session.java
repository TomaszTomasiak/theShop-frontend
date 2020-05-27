package com.session;

import com.domain.*;
import com.domain.ProductOnCart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class Session implements Serializable {

    private User currentUser = new User();
    private Order order = new Order();
    private Product product = new Product();
    private Item item = new Item();
    private Cart cart = new Cart();
    private ProductGroup productGroup = new ProductGroup();
    private List<ProductOnCart> listOfProductsOnCart = new ArrayList<>();

    public void cleanAll() {
        currentUser = new User();
        cart = new Cart();
        order = new Order();
        product = new Product();
        item = new Item();
        productGroup = new ProductGroup();

        for (ProductOnCart tmp:listOfProductsOnCart) {
            listOfProductsOnCart.remove(tmp);
        }
    }

    private BigDecimal orderTotalValue() {
        return BigDecimal.valueOf(getListOfProductsOnCart().stream()
                .mapToDouble(i -> i.getValue())
                .sum()).setScale(2);
    }
}
