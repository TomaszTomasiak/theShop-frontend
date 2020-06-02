package com.service;

import com.client.ProductGroupClient;
import com.domain.ProductGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductGroupService {

    @Autowired
    private ProductGroupClient client;

    private static ProductGroupService productGroupService;

    private ProductGroupService() {
    }

    public static ProductGroupService getInstance() {
        if (productGroupService == null) {
            productGroupService = new ProductGroupService();
        }
        return productGroupService;
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
}
