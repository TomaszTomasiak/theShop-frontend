package com.view.adminViews;

import com.domain.User;
import com.form.UserForm;
import com.service.UserService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route("users_admin")
public class UsersAdminView extends VerticalLayout {

    @Autowired
    private UserService userService;

    @Autowired
    private Session session;

    private Button logout = new Button("Log out");
    private Text logged = new Text("");
    private Grid<User> grid = new Grid<>(User.class);
    private TextField lastNameFilter = new TextField();
    private TextField mailFilter = new TextField();
    private TextField phoneFilter = new TextField();
    private TextField firstNameFilter = new TextField();
    private UserForm form = new UserForm(this);
    private Button addNewUser = new Button("Add new user");
    private List<User> list;

    public UsersAdminView() {

        //logged.setText("Logged: " + session.getCurrentUser().getFirstName() + " " + session.getCurrentUser().getLastName());
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

        grid.setColumns("id", "firstName", "lastName", "mailAdress", "phoneNumber", "password");

        addNewUser.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setUser(new User());
        });

        HorizontalLayout toolbar = new HorizontalLayout(lastNameFilter, firstNameFilter, mailFilter, phoneFilter, addNewUser, logout, logged);

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);
        form.setUser(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setUser(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        list = new ArrayList<>(userService.getUsers());
        grid.setItems(list);
    }

    public void updateLastName() {
        grid.setItems(userService.findByLastName(lastNameFilter.getValue()));
    }

    public void updateFirstName() {
        grid.setItems(userService.findByFirstName(firstNameFilter.getValue()));
    }

    public void updateMail() {
        grid.setItems(userService.findByMail(mailFilter.getValue()));
    }

    public void updatePhone() {
        grid.setItems(userService.findByPhoneNumber(phoneFilter.getValue()));
    }
}