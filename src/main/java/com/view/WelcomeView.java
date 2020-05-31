package com.view;

import com.config.AdminConfig;
import com.domain.User;
import com.service.UserService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route("")
public class WelcomeView extends VerticalLayout {

    private H1 welcome = new H1("Welcome to theSHOP");
    private H3 space = new H3();
    private Text instruction = new Text("Please log in to your account or create new account");
    private EmailField mail = new EmailField();
    private PasswordField password = new PasswordField();
    private Button loginButton = new Button("Log in to account");
    private Button createNewAccount = new Button("Create new account");

    @Autowired
    private UserService userService;

    @Autowired
    private AdminConfig adminConfig;

    private Session session = Session.getInstance();

    public WelcomeView() {

        setAlignItems(Alignment.CENTER);
        add(space);
        add(welcome);
        add(instruction);

        mail.setPlaceholder("E-mail adress");
        mail.setWidth("300px");
        password.setPlaceholder("Password");
        password.setWidth("300px");
        loginButton.setWidth("200px");
        createNewAccount.setWidth("200px");

        createNewAccount.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("new_user"));
        });

        loginButton.addClickListener(event -> {
            if (isAdmin()) {
                getUI().ifPresent(ui -> ui.navigate("admin_main"));
            }
            if (validateUser()) {
                getUI().ifPresent(ui -> ui.navigate("user_view"));
            }
            add(new Notification("Email adress or password is incorrect"));
        });

        add(mail, password, loginButton, createNewAccount);
    }

    public boolean validateUser() {
        User user = userService.fetchUserByMail(mail.getValue());
        if (user != null && (password.getValue()).equals(user.getPassword())) {
            session.setCurrentUser(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean isAdmin() {
        if (mail.getValue().equals(adminConfig.getAdminMail())) {
            User admin = new User();
            admin.setFirstName("Admin");
            session.setCurrentUser(admin);
            return true;
        }
        return false;
    }

}
