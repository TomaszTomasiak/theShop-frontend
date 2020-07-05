package com.view.userViews;

import com.domain.Cart;
import com.domain.Order;
import com.domain.ProductOnCart;
import com.form.ProductsOnCartForm;
import com.service.CartService;
import com.service.CurrencyService;
import com.service.OrderService;
import com.service.TheShopService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.validator.Validator;

@Route("cart_view")
public class CartView extends VerticalLayout {

    CurrencyService currencyService = CurrencyService.getInstance();
    private OrderService orderService = OrderService.getInstance();
    private CartService cartService = CartService.getInstance();
    private Validator validator = Validator.getInstance();
    private TheShopService theShopService = TheShopService.getInstance();
    private Session session = Session.getInstance();
    private Button back = new Button("Return to shopping");
    private Button logout = new Button("Log out");
    private Button pay = new Button("Pay");
    private Text logged = new Text("Logged: " + session.getCurrentUser().getFirstName() + " " + session.getCurrentUser().getLastName());
    private Text totalValue = new Text("");
    private Button orderCreator = new Button("Create order");
    private ProductsOnCartForm form = new ProductsOnCartForm(this);
    private Grid<ProductOnCart> grid = new Grid<>(ProductOnCart.class);

    public CartView() {
        setAlignItems(Alignment.CENTER);
        HorizontalLayout header = new HorizontalLayout(back, logout, logged);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, back);
        header.setPadding(true);
        header.setSpacing(true);
        pay.setWidth("200");

        back.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("user_view"));
        });

        logout.addClickListener(event -> {
            session.cleanAll();
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        orderCreator.addClickListener(event -> {
            makeOrder();
        });

        pay.addClickListener(event -> {
            if (validator.validatePayment()) {
                add(new Notification("Your order is paid. We start preparing shipment for you."));
            }
            add(new Notification("We are waiting for payment"));
        });

        grid.setColumns("product", "price", "quantity", "value");
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> form.setProductOnCart(grid.asSingleSelect().getValue()));
        totalValue.setText(valueInfo());
        add(header, mainContent, totalValue);
        add(orderCreator);
        setSizeFull();
        form.setProductOnCart(null);
        refresh();
    }

    public void refresh() {
        grid.setItems(session.getListOfProductsOnCart());
    }

    private double totalValue() {
        return session.getListOfProductsOnCart().stream()
                .mapToDouble(ProductOnCart::getValue)
                .sum();
    }

    private String valueInfo() {
        return "Total value of products on list is: " + theShopService.roundToDecimal(totalValue(), 2) + " PLN\n" + "\n" +
                "Value in EUR: " + currencyService.valueEUR(totalValue()) + "\n" +
                "Value in GBP: " + currencyService.valueGBP(totalValue()) + "\n" +
                "Value in USD: " + currencyService.valueUSD(totalValue()) + "\n";
    }

    public void makeOrder() {
        Cart newCart = new Cart();
        newCart.setUserId(session.getCurrentUser().getId());
        newCart.setItems(session.getCart().getItems());
        cartService.saveCart(newCart);
        if (theShopService.findCartIdByUserAndListOfItems(newCart) != null) {
            session.setCart(theShopService.findCartIdByUserAndListOfItems(newCart));
        }
        Order newOrder = new Order();
        newOrder.setCartId(session.getCart().getId());
        newOrder.setUserId(session.getCurrentUser().getId());
        orderService.saveOrder(newOrder);

        add(pay);
    }

}

