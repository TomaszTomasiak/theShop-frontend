package com.view.userViews;

import com.domain.ProductOnCart;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("order_view")
public class OrderView extends VerticalLayout {

    private Session session = Session.getInstance();

    private Button back = new Button("Return to shopping");
    private Button logout = new Button("Log out");
    private Button pay = new Button("Pay");
    private Text logged = new Text("Logged: " + session.getCurrentUser().getFirstName() + " " + session.getCurrentUser().getLastName());
    private Grid<ProductOnCart> grid = new Grid<>(ProductOnCart.class);


    public OrderView() {
        setAlignItems(Alignment.CENTER);
        HorizontalLayout header = new HorizontalLayout(back, logout, logged);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, back);
        header.setPadding(true);
        header.setSpacing(true);

        back.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("user_view"));
        });

        logout.addClickListener(event -> {
            session.cleanAll();
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        grid.setColumns("product", "price", "quantity", "value");
        add(grid);

        pay.setWidth("200");
        add(pay);
        refresh();
    }

    public void refresh() {
        grid.setItems(session.getListOfProductsOnCart());
    }
}
