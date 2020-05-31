package com.form;

import com.domain.User;
import com.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.view.adminViews.UsersAdminView;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class UserForm extends FormLayout {

    @Autowired
    private UserService userService;

    private IntegerField id = new IntegerField("User Id");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField mailAdress = new TextField("E-mail adress");
    private TextField phoneNumber = new TextField("Phone number");
    private TextField password = new TextField("Password");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<User> binder = new Binder<>(User.class);


    private final UsersAdminView usersAdminView;

    public UserForm(UsersAdminView usersAdminView) {
        this.usersAdminView = usersAdminView;
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(id, firstName, lastName, mailAdress, phoneNumber, password, buttons);
        binder.bindInstanceFields(this);
        save.addClickListener(event -> {
            try {
                save();
                getUI().ifPresent(ui -> ui.navigate("useer_view"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delete.addClickListener(event -> delete());
    }

    private void save() throws IOException {
        User user = binder.getBean();
        userService.createNewUser(user);
        setUser(null);
        usersAdminView.refresh();
    }

    private void delete() {
        User user = binder.getBean();
        Long id = user.getId();
        userService.delete(id);
        setUser(null);
        usersAdminView.refresh();
    }

    public void setUser(User user) {
        binder.setBean(user);

        if (user == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }
}
