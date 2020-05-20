package com.validator;

import com.domain.User;
import com.domain.dto.UserDto;
import com.service.UserService;
import com.vaadin.flow.component.textfield.PasswordField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Autowired
    UserService userService;


    public void validatePayment() {

    }
}

