package com.view.adminViews;

import com.domain.Product;
import com.domain.ProductGroup;
import com.form.ProductForm;
import com.service.ProductService;
import com.service.TheShopService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("admin_product")
public class ProductsAdminView extends VerticalLayout {
    private Session session = Session.getInstance();
    private ProductService productService = ProductService.getInstance();
    private TheShopService theShopService = TheShopService.getInstance();

    private List<Product> list;
    private ProductForm productForm = new ProductForm(this);
    private Grid<Product> grid = new Grid<>(Product.class);
    private TextField findByName = new TextField();
    private NumberField findById = new NumberField();
    private NumberField priceFrom = new NumberField("Price from");
    private NumberField priceTo = new NumberField("Price from");
    private Button addNewProduct = new Button("Add new product");
    private Button orders = new Button("Orders page");
    private Button users = new Button("Users page");
    private Button groups = new Button("Groups page");
    private Button logout = new Button("Log out");
    private Text logged = new Text("");
    private Text info = new Text("");
    private String infoNumber = "";

    public ProductsAdminView() {
        setAlignItems(Alignment.CENTER);
        info.setText(infoNumber);
        logged.setText(session.nameOfLoggedUser());
        logout.addClickListener(event -> {
            session.cleanAll();
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        findByName.setPlaceholder("Filter by name");
        findByName.setClearButtonVisible(true);
        findByName.setValueChangeMode(ValueChangeMode.EAGER);
        findByName.addValueChangeListener(e -> updateByName());
        findById.setPlaceholder("Find product by ID");
        findById.setClearButtonVisible(true);
        findById.setValueChangeMode(ValueChangeMode.EAGER);
        findById.addValueChangeListener(event -> findById());

        priceFrom.setPlaceholder("Filter by price higher than");
        priceFrom.setClearButtonVisible(true);
        priceFrom.setValueChangeMode(ValueChangeMode.LAZY);
        priceFrom.addValueChangeListener(e -> updateByPrice());
        priceTo.setPlaceholder("Filter by value lower than");
        priceTo.setClearButtonVisible(true);
        priceTo.setValueChangeMode(ValueChangeMode.LAZY);
        priceTo.addValueChangeListener(e -> updateByPrice());
        grid.setColumns("id", "name", "price", "description", "groupId", "available");
        addNewProduct.addClickListener(e -> {
            grid.asSingleSelect().clear();
            productForm.setProduct(new Product());
        });
        users.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_users")));
        groups.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_groups")));
        orders.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_main")));
        HorizontalLayout header = new HorizontalLayout(findByName, findById, priceFrom, priceTo, addNewProduct, info);
        HorizontalLayout directions = new HorizontalLayout(orders, users, groups, logout, logged);
        HorizontalLayout mainContent = new HorizontalLayout(grid, productForm);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, logout);
        header.setPadding(true);
        header.setSpacing(true);
        directions.setFlexGrow(1, logout);
        directions.setPadding(true);
        directions.setSpacing(true);
        mainContent.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> productForm.setProduct(grid.asSingleSelect().getValue()));
        grid.setSizeFull();
        add(directions, header, mainContent);
        productForm.setProduct(null);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        this.list = productService.getAllProducts();
        grid.setItems(list);
        infoNumber = "Number of displayed products: " + theShopService.numberOfProducts(list);
    }

    public void updateByName() {
        this.list = productService.findProductByName(findByName.getValue());
        grid.setItems(list);
        infoNumber = "Number of displayed products: " + theShopService.numberOfProducts(list);
    }

    public void findById() {
        List<Product> tmpList = new ArrayList<>();
        double idDouble = findById.getValue();
        long id = (long) idDouble;
        Product searchedProduct = productService.getProduct(id);
        tmpList.add(searchedProduct);
        this.list = tmpList;
        grid.setItems(list);
    }

    public void updateByPrice() {
        this.list = productService.findProductsWithPriceBeetween(priceFrom.getValue(), priceTo.getValue());
        grid.setItems(list);
        infoNumber = "Number of displayed products: " + theShopService.numberOfProducts(list);
    }
}
