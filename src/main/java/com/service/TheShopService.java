package com.service;

import com.domain.*;
import com.domain.externalDto.EmailValidatorDto;
import com.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheShopService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductGroupService productGroupService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private MailValidatorApiService mailValidatorApiService;

    private final Session session = Session.getInstance();
    private static TheShopService theShopService;

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
        double sum = orders.stream()
                .mapToDouble(Order::getTotalValue)
                .sum();
        return roundToDecimal(sum, 2);
    }

    public long numberOfOrders(List<Order> orders) {
        return orders.size();
    }

    public long numberOfUsers(List<User> users) {
        return users.size();
    }

    public long numberOfProducts(List<Product> products) {
        return products.size();
    }

    public long numberOfGroups(List<ProductGroup> productGroups) {
        return productGroups.size();
    }

    public boolean findOrderByUserAndCart() {
        List<Order> list = orderService.getOrders().stream()
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

    public String isValid(User user) {
        EmailValidatorDto emailValidatorDto = mailValidatorApiService.checkIfEmailValid(user.getMailAdress());
        if (emailValidatorDto.isValid()) {
            return " is valid";
        } else {
            return " is not valid";
        }
    }

    public Item findItemWithProductIdAndQty(Product product, Integer qty) {
        List<Item> theItems = itemService.getItems().stream()
                .filter(item -> item.getProductId().equals(product.getId()))
                .filter(item -> item.getQuantity().equals(qty))
                .collect(Collectors.toList());
        if (theItems.size() != 0) {
            return theItems.get(0);
        }
        return new Item();
    }

    public Cart findCartIdByUserAndListOfItems(Cart cart) {
        if (cart.getOrderId() == null) {
            List<Cart> cartList = cartService.getCarts().stream()
                    .filter(c -> c.getUserId().equals(Session.getInstance().getCurrentUser().getId()) && c.getItems().equals(Session.getInstance().getCart().getItems()))
                    .collect(Collectors.toList());
            return cartList.get(0);
        }
        return new Cart();
    }
}
