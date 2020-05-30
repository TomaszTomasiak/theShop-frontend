package com.view.userViews;

import com.domain.ProductOnCart;
import com.service.CurrencyService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Currency;

@Route("cart_view")
public class CartView extends VerticalLayout {

    @Autowired
    private CurrencyService currencyService;

    private Session session = Session.getInstance();

    private Button back = new Button("Return to shopping");
    private Button logout = new Button("Log out");
    private Text logged = new Text("Logged: " + session.getCurrentUser().getFirstName() + " " + session.getCurrentUser().getLastName());
    private Grid<ProductOnCart> grid = new Grid<>(ProductOnCart.class);
    private Text totalValue = new Text("");

    public CartView() {
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
        totalValue.setText(value());

        add(totalValue);

        refresh();
    }

    public void refresh() {
        grid.setItems(session.getListOfProductsOnCart());
    }


    private double totalValue () {
        return session.getListOfProductsOnCart().stream()
                .mapToDouble(ProductOnCart::getValue)
                .sum();
    }

    private double roundToDecimal(double num, int dec) {
        int multi = (int) Math.pow(10, dec);
        int temp = (int) Math.round(num * multi);
        return (double) temp / multi;
    }

    private String value() {
        return "Total value of products on list is: " + roundToDecimal(totalValue(), 2) + " PLN\n" + "\n" +
                "Value in EUR: " + currencyService.valueEUR(totalValue()) +"\n" +
                "Value in GBP: " + currencyService.valueGBP(totalValue()) +"\n" +
                "Value in USD: " + currencyService.valueUSD(totalValue()) +"\n";
    }

}
