package com.service;

import com.config.TheShopBackendConfig;
import com.domain.Mail;
import com.domain.Order;
import com.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class OrderService {
//usunąć order confirmation
    private final EmailService emailService = new EmailService();
    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final String ENDPOINT = TheShopBackendConfig.getOrders();
    private final Session session = Session.getInstance();
    private static OrderService orderService;

    private OrderService() {
    }

    public static OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        return orderService;
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .build().encode().toUri();
        return url;
    }

    public List<Order> getOrders() {
        try {
            Order[] usersResponse = restTemplate.getForObject(getUrl(), Order[].class);
            return Arrays.asList(ofNullable(usersResponse).orElse(new Order[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public Order getOrder(Long orderId) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + orderId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, Order.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Order();
        }
    }

    public void deleteOrder(Long orderId) {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT + "/" + orderId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Order saveOrder(Order order) {
        Order newOrder = restTemplate.postForObject(getUrl(), order, Order.class);
        session.setOrder(newOrder);
        if (findOrderByUserAndCart()) {
            emailService.send(new Mail(
                    session.getCurrentUser().getMailAdress(),
                    "Order confirmation",
                    "You have just placed the order number: " + session.getOrder().getId()
            ));
        }
        return newOrder;
    }

    public void updateOrder(Long orderId, Order order) {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT + "/" + orderId)
                .build().encode().toUri();
        try {
            restTemplate.put(url, order);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    public boolean findOrderByUserAndCart() {
        List<Order> list = getOrders().stream()
                .filter(o -> o.getUserId().equals(session.getCurrentUser().getId()) && o.getCartId().equals(session.getCart().getId()))
                .collect(Collectors.toList());
        if (list.size() != 0) {
            session.setOrder(list.get(0));
            return true;
        }
        return false;
    }

    public List<Order> findOrdersByDateOfOrdered(LocalDate from, LocalDate to) {
        if (from == null) {
            from = LocalDate.now().minusYears(Integer.MAX_VALUE);
        } else if (to == null) {
            to = LocalDate.now();
        }
        LocalDate finalFrom = from;
        LocalDate finalTo = to;
        return getOrders().stream()
                .filter(order -> order.getOrdered().isAfter(finalFrom) && order.getOrdered().isBefore(finalTo))
                .collect(Collectors.toList());
    }

    public List<Order> findOrdersWithTotalValueBeetween(Double from, Double to) {
        if (from == null) {
            from = 0.0;
        } else if (to == null) {
            to = Double.POSITIVE_INFINITY;
        }
        Double finalFrom = from;
        Double finalTo = to;
        return getOrders().stream()
                .filter(order -> order.getTotalValue() >= finalFrom && order.getTotalValue() <= finalTo)
                .collect(Collectors.toList());
    }
}
