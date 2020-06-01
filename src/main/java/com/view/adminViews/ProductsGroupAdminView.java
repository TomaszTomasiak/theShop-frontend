package com.view.adminViews;

import com.domain.ProductGroup;
import com.domain.User;
import com.form.ProductGroupForm;
import com.service.ProductGroupService;
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

@Route("admin_group")
public class ProductsGroupAdminView extends VerticalLayout {

    private Session session = Session.getInstance();
    private ProductGroupService productGroupService = ProductGroupService.getInstance();
    private TheShopService theShopService = TheShopService.getInstance();

    private List<ProductGroup> productGroupList = new ArrayList<>();
    private ProductGroupForm productGroupForm = new ProductGroupForm(this);
    private Grid<ProductGroup> grid = new Grid<>(ProductGroup.class);
    private TextField findByName = new TextField();
    private NumberField findById = new NumberField();
    private Button addNewGroup = new Button("Add new group");
    private Button orders = new Button("Orders page");
    private Button users = new Button("Users page");
    private Button products = new Button("Products page");
    private Button logout = new Button("Log out");
    private Text logged = new Text("");
    private Text info = new Text("");
    private String infoNumber = "";


    public ProductsGroupAdminView() {

        info.setText(infoNumber);
        logged.setText(session.nameOfLoggedUser());
        logout.addClickListener(event -> {
            session.cleanAll();
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        findByName.setPlaceholder("Filter by name");
        findByName.setClearButtonVisible(true);
        findByName.setValueChangeMode(ValueChangeMode.EAGER);
        findByName.addValueChangeListener(e -> updateName());
        findById.setPlaceholder("Find group by ID");
        findById.setClearButtonVisible(true);
        findById.setValueChangeMode(ValueChangeMode.EAGER);
        findById.addValueChangeListener(event -> findById());
        grid.setColumns("id", "name");
        addNewGroup.addClickListener(e -> {
            grid.asSingleSelect().clear();
            productGroupForm.setGroup(new ProductGroup());
        });
        users.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_users")));
        products.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_products")));
        orders.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_main")));
        HorizontalLayout header = new HorizontalLayout(findByName, findById, addNewGroup, info);
        HorizontalLayout directions = new HorizontalLayout(orders, users, products, logout, logged);
        HorizontalLayout mainContent = new HorizontalLayout(grid, productGroupForm);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, logout);
        header.setPadding(true);
        header.setSpacing(true);
        directions.setFlexGrow(1, logout);
        directions.setPadding(true);
        directions.setSpacing(true);
        mainContent.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> productGroupForm.setGroup(grid.asSingleSelect().getValue()));
        grid.setSizeFull();
        add(directions, header, mainContent);
        productGroupForm.setGroup(null);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        this.productGroupList = productGroupService.getAllGroups();
        grid.setItems(productGroupList);
        infoNumber = "Number of displayed groups: " + theShopService.numberOfGroups(productGroupList);
    }

    public void updateName() {
        this.productGroupList = theShopService.findGroupByName(findByName.getValue());
        grid.setItems(productGroupList);
        infoNumber = "Number of displayed groups: " + theShopService.numberOfGroups(productGroupList);
    }

    public void findById() {
        List<ProductGroup> tmpList = new ArrayList<>();
        double idDouble = findById.getValue();
        long id = (long) idDouble;
        ProductGroup searchedGroup = productGroupService.getGroup(id);
        tmpList.add(searchedGroup);
        this.productGroupList = tmpList;
        grid.setItems(productGroupList);
    }
}
