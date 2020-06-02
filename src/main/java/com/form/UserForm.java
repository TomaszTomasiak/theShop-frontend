package com.form;

import com.domain.User;
import com.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.view.adminViews.UsersAdminView;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class UserForm extends FormLayout {

    @Autowired
    private UserService userService;

    private NumberField id = new NumberField("User Id");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField mailAdress = new TextField("E-mail adress");
    private TextField phoneNumber = new TextField("Phone number");
    private TextField password = new TextField("Password");

    private Button save = new Button("Save new user");
    private Button delete = new Button("Delete");
    private Button update = new Button("Update");
    private Binder<User> binder = new Binder<>(User.class);

    private final UsersAdminView usersAdminView;

    public UserForm(UsersAdminView usersAdminView) {
        this.usersAdminView = usersAdminView;
        HorizontalLayout buttons = new HorizontalLayout(update, save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(firstName, lastName, mailAdress, phoneNumber, password, buttons);
        id.setVisible(false);
        binder.forField(id).withConverter(Double::longValue, Long::doubleValue).bind(User::getId, User::setId);
        binder.bindInstanceFields(this);
        save.addClickListener(event -> {
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getUI().ifPresent(ui -> ui.navigate("useer_view"));
        });
        delete.addClickListener(event -> delete());
        update.addClickListener(event -> update());

    }

    private void save() throws IOException {
        userService.createNewUser(createdUser());
        setUser(null);
        usersAdminView.refresh();
    }

    private void update() {
        User user = binder.getBean();
        setUser(null);
        usersAdminView.refresh();
    }

    private void delete() {
        Long id = createdUser().getId();
        userService.delete(id);
        setUser(null);
        usersAdminView.refresh();
    }

    public void setUser(User user) {
        binder.setBean(createdUser());

        if (user == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }

    private User createdUser() {
        User newUser = new User();
        newUser.setFirstName(firstName.getValue());
        newUser.setLastName(lastName.getValue());
        newUser.setMailAdress(mailAdress.getValue());
        newUser.setPassword(password.getValue());
        newUser.setPhoneNumber(phoneNumber.getValue());
        return newUser;
    }
}
