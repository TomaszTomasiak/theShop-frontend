package com.service;

import com.client.ProductGroupClient;
import com.domain.Product;
import com.domain.ProductGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductGroupService {

    @Autowired
    private ProductGroupClient client;

    private static ProductGroupService productGroupService;
    public List<ProductGroup> productGroupList;

    public ProductGroupService() {
        this.productGroupList = new ArrayList<>(client.getAllGroups());
    }

    public static ProductGroupService getInstance() {
        if (productGroupService == null) {
            productGroupService = new ProductGroupService();
        }
        return productGroupService;
    }

    public List<ProductGroup> getProductGroupList() {
        return client.getAllGroups();
    }

    public List<ProductGroup> getAllGroups() {
        return client.getAllGroups();
    }

    public ProductGroup getGroup(Long id) {
        return client.getGroup(id);
    }

    public void saveGroup(ProductGroup productGroup) {
        client.createNewGroup(productGroup);
    }

    public void updateGroup(ProductGroup productGroup) {
        client.updateGroup(productGroup.getId(), productGroup);
    }

    public List<ProductGroup> findByGroupByName(String name) {
        return productGroupList.stream()
                .filter(product -> product.getName().contains(name))
                .collect(Collectors.toList());
    }

}
