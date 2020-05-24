package com.service;

import com.client.ProductClient;
import com.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductClient productClient;

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

    public Set<Product> findByProductName(String name) {
        return getAllProducts().stream()
                .filter(product -> product.getName().contains(name))
                .collect(Collectors.toSet());
    }
}
