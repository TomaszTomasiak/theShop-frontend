package com.view.userViews;

import com.domain.Product;
import com.domain.ProductGroup;
import com.service.ProductGroupService;
import com.service.ProductService;
import com.service.TheShopService;
import com.session.Session;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "user_view")
public class UserView extends VerticalLayout {

    private final ProductGroupService productGroupService = ProductGroupService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final TheShopService theShopService = TheShopService.getInstance();
    private final Session session = Session.getInstance();

    private TextField filter = new TextField();
    private Button logout = new Button("Log out");
    private H4 logged = new H4("");
    private Button showProducts = new Button("Show all products");
    private Grid<ProductGroup> gridGroup = new Grid<>(ProductGroup.class);
    private Grid<Product> gridProduct = new Grid<>(Product.class);

    private List<Product> products;
    private List<ProductGroup> groups;

    public UserView() {
        add(logged);
        logged.setText(session.nameOfLoggedUser());
        filter.setWidth("320px");
        filter.setPlaceholder("Filter by product name");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        logout.setWidth("250px");
        gridGroup.setColumns("name");
        gridProduct.setColumns("name");
        HorizontalLayout header = new HorizontalLayout(filter, showProducts, logout);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        //header.setFlexGrow(1, filter);
        header.setPadding(true);
        header.setSpacing(true);
        header.setWidthFull();
        filter.addValueChangeListener(e -> filterByName());
        showProducts.setWidth("250px");
        showProducts.addClickListener(event -> refresh());
        HorizontalLayout mainContent = new HorizontalLayout(gridGroup, gridProduct);
        mainContent.setWidth("1500px");
        gridGroup.asSingleSelect().addValueChangeListener(event -> {
            gridGroup.asSingleSelect().clear();
            session.setProductGroup(gridGroup.asSingleSelect().getValue());
            productBelongToGroup();
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

    public void filterByName() {
        products = productService.findProductByName(filter.getValue());
        gridProduct.setItems(products);
    }

    public void refresh() {
        groups = productGroupService.getAllGroups();
        gridGroup.setItems(groups);
        products = productService.getAllProducts();
        gridProduct.setItems(products);
    }

    public void productBelongToGroup() {
        products = productService.getAllProducts().stream()
                .filter(product -> product.getGroupId().equals(session.getProductGroup().getId()))
                .collect(Collectors.toList());
        gridProduct.setItems(products);
    }
}
