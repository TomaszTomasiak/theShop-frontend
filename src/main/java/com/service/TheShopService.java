package com.service;

import com.domain.Order;
import com.domain.Product;
import com.domain.ProductGroup;
import com.domain.User;
import com.session.Session;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TheShopService {
    private static TheShopService theShopService;
    private UserService userService = UserService.getInstance();
    private ProductService productService = ProductService.getInstance();
    private ProductGroupService productGroupService = ProductGroupService.getInstance();
    private ItemService itemService = ItemService.getInstance();
    private CurrencyService currencyService = CurrencyService.getInstance();
    private CartService cartService = CartService.getInstance();
    private OrderService orderService = OrderService.getInstance();
    private Session session = Session.getInstance();

    private TheShopService() {
    }

    public static TheShopService getInstance() {
        if (theShopService == null) {
            theShopService = new TheShopService();
        }
        return theShopService;
    }

    public double roundToDecimal(double num, int dec) {
        int multi = (int) Math.pow(10, dec);
        int temp = (int) Math.round(num * multi);
        return (double) temp / multi;
    }


    public double totalValue(List<Order> orders) {
        double sum =  orders.stream()
                .mapToDouble(Order::getTotalValue)
                .sum();
        return roundToDecimal(sum, 2);
    }

    public long numberOfOrders(List<Order> orders){
        return orders.stream()
                .count();
    }

    public long numberOfUsers(List<User> users){
        return users.stream()
                .count();
    }

    public long numberOfProducts(List<Product> products){
        return products.stream()
                .count();
    }

    public long numberOfGroups(List<ProductGroup> productGroups){
        return productGroups.stream()
                .count();
    }

    public boolean findOrderByUserAndCart() {
        Iterator<Order> orderIterator = orderService.getOrders().stream()
                .filter(o -> o.getUserId().equals(session.getCurrentUser().getId()) && o.getCartId().equals(session.getCart().getId()))
                .iterator();
        while (orderIterator.hasNext()) {
            session.setOrder(orderIterator.next());
            return true;
        }
        return false;
    }

    public List<Order> findOrdersByDateOfOrdered (LocalDate from, LocalDate to) {
        if (from == null) {
            from = LocalDate.now().minusYears(Integer.MAX_VALUE);
        } else if (to == null) {
            to = LocalDate.now();
        }
        LocalDate finalFrom = from;
        LocalDate finalTo = to;
        return orderService.getOrders().stream()
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
        return orderService.getOrders().stream()
                .filter(order -> order.getTotalValue() >= finalFrom && order.getTotalValue() <= finalTo)
                .collect(Collectors.toList());
    }

    public User fetchUserByMail(String mail) {
        List<User> usersWithIndicatedMail = userService.getUsers().stream()
                .filter(user -> user.getMailAdress().equals(mail.toLowerCase()))
                .collect(Collectors.toList());
        if (usersWithIndicatedMail.size() != 0) {
            return usersWithIndicatedMail.get(0);
        }
        return new User();
    }

    public List<User> findByLastName(String lastName) {
        return userService.getUsers().stream().filter(user -> user.getLastName().contains(lastName)).collect(Collectors.toList());
    }

    public List<User> findByFirstName(String firstName) {
        return userService.getUsers().stream().filter(user -> user.getFirstName().contains(firstName)).collect(Collectors.toList());
    }

    public List<User> findByMail(String mail) {
        return userService.getUsers().stream().filter(user -> user.getMailAdress().contains(mail)).collect(Collectors.toList());
    }

    public List<User> findByPhoneNumber(String phone) {
        return userService.getUsers().stream().filter(user -> user.getPhoneNumber().contains(phone)).collect(Collectors.toList());
    }

    public List<ProductGroup> findGroupByName(String name) {
        return productGroupService.getAllGroups().stream()
                .filter(product -> product.getName().contains(name))
                .collect(Collectors.toList());
    }

    public List<Product> findProductByName(String name) {
        return productService.getAllProducts().stream()
                .filter(product -> product.getName().contains(name))
                .collect(Collectors.toList());
    }

    public List<Product> findProductsWithPriceBeetween(Double from, Double to) {
        if (from == null) {
            from = 0.0;
        } else if (to == null) {
            to = Double.POSITIVE_INFINITY;
        }
        Double finalFrom = from;
        Double finalTo = to;
        return productService.getAllProducts().stream()
                .filter(product -> product.getPrice() >= finalFrom && product.getPrice() <= finalTo)
                .collect(Collectors.toList());
    }


}
