package com.service;

import com.client.ItemClient;
import com.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemClient itemClient;

    public List<Item> getItems() {
        return itemClient.getAllItems();
    }

    public Item getItem(Long id) {
        return itemClient.getItem(id);
    }

    public void saveItem(Item item) {
        itemClient.createNewItem(item);
    }

    public void updateItem(Item item) {
        itemClient.updateItem(item.getId(), item);
    }

    public void deleteItem(Item item) {
        itemClient.deleteItem(item.getId());
    }

}
