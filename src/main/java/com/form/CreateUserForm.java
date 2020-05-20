package com.form;

import com.domain.User;
import com.domain.dto.UserDto;
import com.service.UserService;
import com.session.Session;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.validator.Validator;
import com.view.userViews.CreateUserView;
import org.springframework.beans.factory.annotation.Autowired;


public class CreateUserForm extends FormLayout {

    @Autowired
    UserService userService;

    @Autowired
    Validator validator;

    @Autowired
    Session session;

    private NumberField id = new NumberField();
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField mailAdress = new EmailField("Mail adress");
    private TextField phoneNumber = new TextField("Phone number");
    private PasswordField password = new PasswordField("Password");
    //private PasswordField repeatPassword = new PasswordField("Repeat password");
    private Button save = new Button("Create account");
    private Button cancel = new Button("Cancel");
    private Binder<User> binder = new Binder<>(User.class);
    private Text notFit = new Text("Password and repeated password are not the same");
    private User user;

    private CreateUserView createUserView;

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
        //repeatPassword.setWidth("350px");
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout layout = new VerticalLayout(id, firstName, lastName, mailAdress, phoneNumber, password, buttons);
        add(layout);
        id.setVisible(false);
        id.setValue(null);
        binder.forField(id).withConverter(Double::longValue, Long::doubleValue).bind(User::getId, User::setId);
        binder.bindInstanceFields(this);


//        add(formLayout);
        firstName.focus();
        save.addClickListener(event -> save());
        cancel.addClickListener(event -> cancel());
    }

    private void save() {
        User user = binder.getBean();
        userService.save(user);
        //Long userId = session.getCurrentUser().getId();
        if (validateUser(user)) {
           if( validatePassword(user)) {
               session.setCurrentUser(user);
               userService.isUserLogged = true;
               getUI().ifPresent(ui -> ui.navigate("user_view"));
           }

        }
    }

    private void cancel() {
        //setNewUserFormVisible(null);
        getUI().ifPresent(ui -> ui.navigate(""));
    }

//    public void setNewUserFormVisible(User user) {
//        binder.setBean(user);
//        if (user == null) {
//            setVisible(false);
//        } else {
//            setVisible(true);
//            firstName.focus();
//        }
//    }

    public boolean validateUser(User user) {

        String mail = mailAdress.getValue();

        if(!userService.fetchUserByMail(user.getMailAdress()).equals(mail)) {
            return false;
        }
        return true;
    }

    public boolean validatePassword(User user) {

        String pass = password.getValue();

        if ((userService.fetchUserByMail(user.getMailAdress())).equals(pass)){
            return true;
        } else {
            return false;
        }
    }

}
