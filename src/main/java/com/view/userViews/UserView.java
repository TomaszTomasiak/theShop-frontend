package com.view.userViews;

import com.domain.Product;
import com.domain.ProductGroup;
import com.service.ProductGroupService;
import com.service.ProductService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "user_view")
public class UserView extends VerticalLayout {

    @Autowired
    private ProductGroupService productGroupService;

    @Autowired
    private ProductService productService;

    private Session session = Session.getInstance();

    private TextField filter = new TextField();
    private Button logout = new Button("Log out");
    private Text logged = new Text("");
    private Button showProducts = new Button("Show all products");
    private Grid<ProductGroup> gridGroup = new Grid<>(ProductGroup.class);
    private Grid<Product> gridProduct = new Grid<>(Product.class);

    public UserView() {
        logged.setText(nameOfLoggedUser());
        filter.setPlaceholder("Filter by product name");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());
        gridGroup.setColumns("name");
        gridProduct.setColumns("name");

        HorizontalLayout header = new HorizontalLayout(filter, showProducts, logout, logged);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, filter);
        header.setPadding(true);
        header.setSpacing(true);

        HorizontalLayout mainContent = new HorizontalLayout(gridGroup, gridProduct);
        mainContent.setSizeFull();
        //gridGroup.setSizeFull();

        gridGroup.asSingleSelect().addValueChangeListener(event -> {
            gridGroup.asSingleSelect().clear();
            gridProduct.setItems(productService.getAllProducts().stream()
                    .filter(product -> product.getGroupId().equals(gridGroup.asSingleSelect().getValue().getId())));
        });

        gridProduct.asSingleSelect().addValueChangeListener(event -> {
            session.setProduct(gridProduct.asSingleSelect().getValue());
            getUI().ifPresent(ui -> ui.navigate("product_view"));
        });

        showProducts.addClickListener(event -> refresh());
        add(header, mainContent);

        logout.addClickListener(event -> {
            session.cleanAll();
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        setSizeFull();
        refresh();
    }

    public void update() {
        gridProduct.setItems(productService.findByProductName(filter.getValue()));
    }

    public void refresh() {
        gridGroup.setItems(productGroupService.getAllGroups());
        gridProduct.setItems(productService.getAllProducts());
    }

    public String nameOfLoggedUser () {
        return "Logged: " + session.getCurrentUser().getFirstName() + " " + session.getCurrentUser().getLastName();
    }
}
