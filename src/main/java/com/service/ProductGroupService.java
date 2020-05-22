package com.service;


import com.client.ProductGroupClient;
import com.domain.ProductGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductGroupService {

    @Autowired
    private ProductGroupClient client;

    private Set<ProductGroup> groups;

    public Set<ProductGroup> getGroups() {
        groups = client.getAllGroups();
        return groups;
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
