package com.view.userViews;

import com.domain.Item;
import com.service.CartService;
import com.service.CurrencyService;
import com.service.ItemService;
import com.service.OrderService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("cart_view")
public class CartView extends VerticalLayout {

    @Autowired
    private Session session;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ItemService itemService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    private Button back = new Button("Return to shopping");
    private Button logout = new Button("Log out");
    private Text logged = new Text("Logged: " + session.getCurrentUserDto().getFirstName() + " " + session.getCurrentUserDto().getLastName());
    private Grid<Item> listOfProductsOnCarts = new Grid<>(Item.class);

    public CartView() {
        setAlignItems(Alignment.CENTER);
        HorizontalLayout header = new HorizontalLayout(back, logout, logged);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, back);
        header.setPadding(true);
        header.setSpacing(true);

        back.addClickListener(event -> {
            //session.setProduct(null);
            getUI().ifPresent(ui -> ui.navigate("user_view"));
        });

        logout.addClickListener(event -> {
            session.cleanAll();
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        listOfProductsOnCarts.setColumns("product", "price", "quantity", "value");
    }
}
