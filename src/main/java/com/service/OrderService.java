package com.service;

import com.client.OrderClient;
import com.domain.Mail;
import com.domain.Order;
import com.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Session session;

    private List<Order> orders;

    public List<Order> getOrders() {
        orders = orderClient.getAllOrders();
        return orders;
    }

    public Order getOrder(Long orderId) {
        return orderClient.getOrder(orderId);
    }

    public void saveOrder(Order order) {
        Order newOrder = orderClient.createNewOrder(order);
        session.setOrder(newOrder);
        emailService.send(new Mail(
                session.getCurrentUser().getMailAdress(),
                "Order confirmation",
                "You just placed the order number: " + session.getOrder().getId()
        ));
    }

    public void updateOrder(Order order) {
        orderClient.updateOrder(order.getId(), order);
    }

    public void deleteOrder(Order order) {
        orderClient.deleteOrder(order.getId());
    }

}
