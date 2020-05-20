package com.validator;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    @Autowired
    UserService userService;

//    public boolean validateUser (User user) {
//        user = userService.userByMail(user.getMailAdress());
//        if (user != null) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public void validateUser(Long userId) throws UserException, NullArgumentException {
//        if (userId == null) {
//            throw new NullArgumentException(NullArgumentException.ERR_ARGUMENTS_NULL);
//        }
//        if (!userService.getUser(userId).isPresent()) {
//            throw new UserException(UserException.ERR_USER_NOT_FOUND);
//        }
//    }
}
