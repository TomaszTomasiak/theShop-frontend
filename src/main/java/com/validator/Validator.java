package com.validator;

import com.domain.User;
import com.service.UserService;
import com.vaadin.flow.component.textfield.PasswordField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Autowired
    UserService userService;

    public boolean validateUser(User user) {

        if(userService.fetchUserByMail(user.getMailAdress()).equals(user.getMailAdress())) {
            return true;
        }
        return false;
    }

    public boolean validatePassword(PasswordField password, PasswordField repeated) {

        if (password.equals(repeated)){
            return true;
        } else {
            return false;
        }
    }

    public void validatePayment() {

    }
}

