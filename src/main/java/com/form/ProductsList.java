package com.form;

import com.domain.Product;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class ProductsList {

    private Product product;
    private double price;
    private int qty;
    private double value;

    public ProductsList(Product product, double price, int qty, double value) {
        this.product = product;
        this.price = price;
        this.qty = qty;
        this.value = price * qty;
    }
}
