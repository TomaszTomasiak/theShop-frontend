package com.form;

import com.domain.UserDto;
import com.service.UserService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.Binder;
import com.view.userViews.CreateUserView;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateUserForm extends FormLayout {

    @Autowired
    private UserService userService;

    @Autowired
    private Session session;

    private final IntegerField id = new IntegerField();
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField mailAdress = new EmailField("Mail adress");
    private final TextField phoneNumber = new TextField("Phone number");
    private final PasswordField password = new PasswordField("Password");
    private final Button save = new Button("Create account");
    private final Button cancel = new Button("Cancel");
    private final Binder<UserDto> binder = new Binder<>(UserDto.class);
    private final Text notFit = new Text("Password and repeated password are not the same");

    private final CreateUserView createUserView;

    public CreateUserForm(CreateUserView createUserView) {
        this.createUserView = createUserView;
        setSizeFull();
        firstName.setWidth("350px");
        lastName.setWidth("350px");
        mailAdress.setWidth("350px");
        phoneNumber.setWidth("350px");
        password.setWidth("350px");
        save.setWidth("170px");
        cancel.setWidth("170px");
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        VerticalLayout fields = new VerticalLayout(firstName, lastName, mailAdress, phoneNumber, password, buttons);
        add(fields);
        //id.setValue(1);
        //id.setVisible(false);
        //binder.forField(id).withConverter(Integer::longValue, Long::intValue).bind(UserDto::getId, UserDto::setId);
//        binder.bindInstanceFields(this);
        firstName.focus();
        save.addClickListener(event -> {
            if (saveUser()) {
                getUI().ifPresent(ui -> ui.navigate("user_view"));
            } else {
                createUserView.info(new Text("Ups. Something goes wrong. Try again later"));
            }
        });
        cancel.addClickListener(event -> cancel());
    }

    private boolean saveUser() {
        //UserDto newUserDto = binder.getBean();
        UserDto newUserDto =
                new UserDto(firstName.getValue(), lastName.getValue(), mailAdress.getValue(), phoneNumber.getValue(), password.getValue());

        userService.saveUser(newUserDto);
        return isUserCreated();
    }

    private boolean isUserCreated() {
        UserDto createdUserDto = userService.fetchUserByMail(mailAdress.getValue());
        if (createdUserDto.getId() != null) {
            session.setCurrentUserDto(createdUserDto);
            return true;
        } else {
            return false;
        }
    }

    private void cancel() {
        getUI().ifPresent(ui -> ui.navigate(""));
    }

    public CreateUserView getCreateUserView() {
        return createUserView;
    }
}
