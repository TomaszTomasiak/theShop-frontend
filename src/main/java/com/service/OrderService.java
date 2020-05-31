package com.service;

import com.client.OrderClient;
import com.domain.Mail;
import com.domain.Order;
import com.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private EmailService emailService;

    private final Session session = Session.getInstance();
    private static OrderService orderService;

    public List<Order> orders;

    public static OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        return orderService;
    }

    public OrderService() {
        this.orders = new ArrayList<>(orderClient.getAllOrders());
    }

    public List<Order> getOrders() {
        return orderClient.getAllOrders();
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

//    public void findOrdersWithTotalValueBeetween(Double from, Double to) {
//        orderClient.getAllOrders().stream()
//                .filter(order -> {
//                    if(from != null ){
//
//                    }
//                    order.getTotalValue() >
//                } )
//    }

}
