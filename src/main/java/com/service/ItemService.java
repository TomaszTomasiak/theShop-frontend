package com.service;

import com.client.ItemClient;
import com.domain.Item;
import com.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemClient itemClient;

    private static ItemService itemService;

    public static ItemService getInstance() {
        if (itemService == null) {
            itemService = new ItemService();
        }
        return itemService;
    }

    private List<Item> items;

    public ItemService() {
        this.items = new ArrayList<>(itemClient.getAllItems());
    }

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

    public Item findItemWithProductIdAndQty(Product product, Integer qty) {
        List<Item> theItems = itemClient.getAllItems().stream()
                .filter(item -> item.getProductId().equals(product))
                .filter(item -> item.getQuantity() == qty)
                .collect(Collectors.toList());
        if (theItems.size() != 0) {
            return theItems.get(0);
        }
        return new Item();
    }

}
