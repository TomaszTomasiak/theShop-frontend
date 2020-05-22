package com.service;

import com.client.OrderClient;
import com.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderClient orderClient;

    private Set<Order> orders;

    public Set<Order> getOrders() {
        orders = orderClient.getAllOrders();
        return orders;
    }

    public Order getOrder(Long orderId) {
        return orderClient.getOrder(orderId);
    }

    public void saveOrder(Order order) {
        orderClient.createNewOrder(order);
    }

    public void updateOrder(Order order) {
        orderClient.updateOrder(order.getId(), order);
    }

    public void deleteOrder(Order order) {
        orderClient.deleteOrder(order.getId());
    }
}
