package com.form;

import com.domain.UserDto;
import com.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.view.adminViews.UsersAdminView;
import org.springframework.beans.factory.annotation.Autowired;

public class UserForm extends FormLayout {

    @Autowired
    UserService userService;

    private Text id = new Text("UserDto Id");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField mailAdress = new TextField("E-mail adress");
    private TextField phoneNumber = new TextField("Phone number");
    private TextField password = new TextField("Password");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<UserDto> binder = new Binder<>(UserDto.class);


    private UsersAdminView usersAdminView;

    public UserForm(UsersAdminView usersAdminView) {
        this.usersAdminView = usersAdminView;
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(id, firstName, lastName, mailAdress, phoneNumber, password, buttons);
        binder.bindInstanceFields(this);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
    }

    private void save() {
        UserDto userDto = binder.getBean();
        userService.saveUser(userDto);
        setUser(null);
        usersAdminView.refresh();
    }

    private void delete() {
        UserDto userDto = binder.getBean();

        //userService.delete(userDto);
        setUser(null);
        usersAdminView.refresh();

    }

    public void setUser(UserDto userDto) {
        binder.setBean(userDto);

        if (userDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }
}
