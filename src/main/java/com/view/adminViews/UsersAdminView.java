package com.view.adminViews;

import com.domain.Order;
import com.domain.User;
import com.form.UserForm;
import com.service.TheShopService;
import com.service.UserService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("admin_users")
public class UsersAdminView extends VerticalLayout {

    private UserService userService = UserService.getInstance();
    private Session session = Session.getInstance();
    private TheShopService theShopService = TheShopService.getInstance();
    private Button logout = new Button("Log out");
    private Text logged = new Text("");
    private Grid<User> grid = new Grid<>(User.class);
    private TextField lastNameFilter = new TextField();
    private TextField mailFilter = new TextField();
    private TextField phoneFilter = new TextField();
    private TextField firstNameFilter = new TextField();
    private NumberField findUserById = new NumberField();
    private UserForm form = new UserForm(this);
    private Button addNewUser = new Button("Add new user");
    private Button orders = new Button("Orders page");
    private Button groups = new Button("ProductGroups page");
    private Button products = new Button("Products page");
    private Text infoNumber = new Text("");
    private List<User> list = new ArrayList<>();
    private String infoNumberOfUsers = "";

    public UsersAdminView() {

        infoNumber.setText(infoNumberOfUsers);
        logged.setText(session.nameOfLoggedUser());
        logout.addClickListener(event -> {
            session.cleanAll();
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        lastNameFilter.setPlaceholder("Filter by last name");
        lastNameFilter.setClearButtonVisible(true);
        lastNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        lastNameFilter.addValueChangeListener(e -> updateLastName());
        mailFilter.setPlaceholder("Filter by e-mail");
        mailFilter.setClearButtonVisible(true);
        mailFilter.setValueChangeMode(ValueChangeMode.EAGER);
        mailFilter.addValueChangeListener(e -> updateMail());
        firstNameFilter.setPlaceholder("Filter by first name");
        firstNameFilter.setClearButtonVisible(true);
        firstNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        firstNameFilter.addValueChangeListener(e -> updateFirstName());
        phoneFilter.setPlaceholder("Filter by phone number");
        phoneFilter.setClearButtonVisible(true);
        phoneFilter.setValueChangeMode(ValueChangeMode.EAGER);
        phoneFilter.addValueChangeListener(e -> updatePhone());
        findUserById.setPlaceholder("Find user by ID");
        findUserById.setClearButtonVisible(true);
        findUserById.setValueChangeMode(ValueChangeMode.LAZY);
        findUserById.addValueChangeListener(e -> findById());

        grid.setColumns("id", "firstName", "lastName", "mailAdress", "phoneNumber", "password");

        addNewUser.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setUser(new User());
        });

        groups.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_groups")));
        products.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_products")));
        orders.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin_main")));

        HorizontalLayout header = new HorizontalLayout(findUserById, lastNameFilter, firstNameFilter, mailFilter, phoneFilter, addNewUser, infoNumber);
        HorizontalLayout directions = new HorizontalLayout(orders, groups, products, logout, logged);

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(directions, header, mainContent);
        form.setUser(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setUser(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        this.list = userService.getUsers();
        grid.setItems(list);
        infoNumberOfUsers = "Number of displayed users is: " + theShopService.numberOfUsers(list);

    }

    public void updateLastName() {
        this.list = theShopService.findByLastName(lastNameFilter.getValue());
        grid.setItems(list);
        infoNumberOfUsers = "Number of displayed users is: " + theShopService.numberOfUsers(list);
    }

    public void updateFirstName() {
        this.list = theShopService.findByFirstName(firstNameFilter.getValue());
        grid.setItems(list);
        infoNumberOfUsers = "Number of displayed users is: " + theShopService.numberOfUsers(list);
    }

    public void updateMail() {
        this.list = theShopService.findByMail(mailFilter.getValue());
        grid.setItems(list);
        infoNumberOfUsers = "Number of displayed users is: " + theShopService.numberOfUsers(list);
    }

    public void updatePhone() {
        this.list = theShopService.findByPhoneNumber(phoneFilter.getValue());
        grid.setItems(list);
        infoNumberOfUsers = "Number of displayed users is: " + theShopService.numberOfUsers(list);
    }

    public void findById() {
        List<User> tmpList = new ArrayList<>();
        double idDouble = findUserById.getValue();
        long id = (long) idDouble;

        User searchedOrder = userService.getUser(id);
        tmpList.add(searchedOrder);
        this.list = tmpList;
        grid.setItems(list);
    }
}