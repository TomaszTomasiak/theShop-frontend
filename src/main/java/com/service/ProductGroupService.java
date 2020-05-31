package com.service;

import com.client.ProductGroupClient;
import com.domain.ProductGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public ProductGroup getGroup(Long id) {
        return client.getGroup(id);
    }

    public void saveGroup(ProductGroup group) {
        client.createNewGroup(group);
    }

    public void updateGroup(ProductGroup group) {
        client.updateGroup(group.getId(), group);
    }

    public void deleteGroup(ProductGroup group) {
        client.deleteGroup(group.getId());
    }

    public Long groupId (ProductGroup group) {
        return group.getId();
    }
}
