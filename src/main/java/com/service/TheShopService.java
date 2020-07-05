package com.service;

import com.domain.*;
import com.domain.externalDto.EmailValidatorDto;
import com.session.Session;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TheShopService {


    private final ItemService itemService = ItemService.getInstance();
    private final CartService cartService = CartService.getInstance();
    private final MailValidatorApiService mailValidatorApiService = new MailValidatorApiService();

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
