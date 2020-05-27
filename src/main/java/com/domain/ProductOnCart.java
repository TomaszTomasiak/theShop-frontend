package com.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Data
@Component
public class ProductOnCart {

    private Product product;
    private double price;
    private int qty;
    private double value;

    public ProductOnCart(Product product, int qty) {
        this.product = product;
        this.price = product.getPrice();
        this.qty = qty;
        this.value = price * qty;
    }

    @Override
    public String toString() {
        return product +
                ", price: " + price +
                " PLN , ordered pieces: " + qty +
                ", value: " + value;
    }
}
