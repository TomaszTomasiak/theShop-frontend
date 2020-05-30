package com.form;

import com.domain.User;
import com.service.UserService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.*;
import com.view.userViews.CreateUserView;

import java.io.IOException;

public class CreateUserForm extends FormLayout {

    private UserService userService = UserService.getInstance();
    private Session session = Session.getInstance();

    private IntegerField id = new IntegerField("User ID");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField mailAdress = new EmailField("Mail adress");
    private TextField phoneNumber = new TextField("Phone number");
    private Text userNotCreated = new Text("");
    private PasswordField password = new PasswordField("Password");
    private Button save = new Button("Create account");
    private Button cancel = new Button("Cancel");
    //private Binder<User> binder = new Binder<>(User.class);
    private Text notFit = new Text("Password and repeated password are not the same");

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
        VerticalLayout fields = new VerticalLayout(firstName, lastName, mailAdress, phoneNumber, password, buttons);
        add(fields);
        add(userNotCreated);
//        id.setValue(0);
//        id.setVisible(false);
        //binder.bindInstanceFields(this);
        firstName.focus();
        save.addClickListener(event -> {
            try {
                saveUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isUserCreated()) {
                getUI().ifPresent(ui -> ui.navigate("user_view"));
            } else {
                this.createUserView.add(new H1("Ups. Something goes wrong. Try again later"));
            }
        });
        cancel.addClickListener(event -> cancel());
    }

    private void saveUser() throws IOException {
//        User newUser = binder.getBean();
        User newUser =
                new User(firstName.getValue(), lastName.getValue(), mailAdress.getValue(), phoneNumber.getValue(), password.getValue());
        if (!isThereUserWithMail(newUser)) {
            userService.createNewUser(newUser);
            session.setCurrentUser(userService.fetchUserByMail(newUser.getMailAdress()));
        }
        createUserView.add(new Notification("Account with this email adress has already exist.\n"));
    }

    private boolean isUserCreated() {
        if (session.getCurrentUser().getId() != null) {
            return true;
        } else {
            return false;
        }
    }

    private void cancel() {
        getUI().ifPresent(ui -> ui.navigate(""));
    }

    public boolean isThereUserWithMail(User user) {

        if (userService.fetchUserByMail(user.getMailAdress()).getMailAdress() != null) {
            return true;
        }
        return false;
    }

}
