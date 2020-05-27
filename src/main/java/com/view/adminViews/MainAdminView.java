package com.view.adminViews;

import com.service.CartService;
import com.service.CurrencyService;
import com.service.ItemService;
import com.service.OrderService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("ALL")
@Route("admin_main")
public class MainAdminView extends VerticalLayout {

    @Autowired
    private Session session;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;


    //private Button back = new Button("Back to products site");
    private Button logout = new Button("Log out");
    private Text logged = new Text("Logged: " + session.getCurrentUser().getFirstName() + " " + session.getCurrentUser().getLastName());

    public MainAdminView() {
        setAlignItems(Alignment.CENTER);
        HorizontalLayout header = new HorizontalLayout(logout, logged);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, logout);
        header.setPadding(true);
        header.setSpacing(true);



    }
}
