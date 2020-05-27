package com.form;

import com.config.JsonBuilder;
import com.domain.User;
import com.service.UserService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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

    private IntegerField id = new IntegerField("User ID");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField mailAdress = new EmailField("Mail adress");
    private TextField phoneNumber = new TextField("Phone number");
    private Text userNotCreated  = new Text("");
    private PasswordField password = new PasswordField("Password");
    private Button save = new Button("Create account");
    private Button cancel = new Button("Cancel");
    private Binder<User> binder = new Binder<>(User.class);
    private Text notFit = new Text("Password and repeated password are not the same");
    private User createdUser;

    private CreateUserView createUserView;

    public CreateUserForm(CreateUserView createUserView) {
        this.createUserView = createUserView;
        setSizeFull();
        this.createUserView.setAlignItems(FlexComponent.Alignment.CENTER);
        firstName.setWidth("350px");
        lastName.setWidth("350px");
        mailAdress.setWidth("350px");
        phoneNumber.setWidth("350px");
        password.setWidth("350px");
        save.setWidth("170px");
        cancel.setWidth("170px");
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        VerticalLayout fields = new VerticalLayout(id, firstName, lastName, mailAdress, phoneNumber, password, buttons);
        add(fields);
        add(userNotCreated);
        id.setValue(1);
        id.setVisible(false);
        //binder.forField(id).withConverter(Integer::longValue, Long::intValue).bind(User::getId, User::setId);
        binder.bindInstanceFields(this);
        firstName.focus();
        save.addClickListener(event -> {
            saveUser();
            if (isUserCreated()) {
                getUI().ifPresent(ui -> ui.navigate("user_view"));
            } else {
                this.createUserView.add(new Text("Ups. Something goes wrong. Try again later"));
            }
        });
        cancel.addClickListener(event -> cancel());
    }

    private void saveUser() {
        User newUser = binder.getBean();
//        User newUser =
//                new User(firstName.getValue(), lastName.getValue(), mailAdress.getValue(), phoneNumber.getValue(), password.getValue());

        userService.createNewUser(newUser);

    }

    private boolean isUserCreated() {
        createdUser = userService.fetchUserByMail(mailAdress.getValue());
        if (createdUser.getId() != null) {
            session.setCurrentUser(createdUser);
            return true;
        } else {
            return false;
        }
    }
    private void cancel() {
        getUI().ifPresent(ui -> ui.navigate(""));
    }

}
