package com.service;

import com.client.ProductClient;
import com.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductClient productClient;

    private static ProductService productService;
    public List<Product> products;

    public ProductService() {
        this.products = new ArrayList<>(productClient.getAllProducts());
    }

    public static ProductService getInstance() {
        if (productService == null) {
            productService = new ProductService();
        }
        return productService;
    }

    public List<Product> getAllProducts() {
        return productClient.getAllProducts();
    }

    public Product getProduct(Long id) {
        return productClient.getProduct(id);
    }

    public void saveProduct(Product product) {
        productClient.createNewProduct(product);
    }

    public void updateProduct(Product product) {
        productClient.updateProduct(product.getId(), product);
    }

    public void deleteProduct(Product product) {
        productClient.deleteProduct(product.getId());
    }

    public List<Product> findByProductName(String name) {
        return products.stream()
                .filter(product -> product.getName().contains(name))
                .collect(Collectors.toList());
    }
}
