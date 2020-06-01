package com.service;

import com.client.UserClient;
import com.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserClient userClient;

    private static UserService userService;
    public List<User> users;

    private UserService() {
        this.users = new ArrayList<>(getUsers());
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public List<User> getUsers() {
        return userClient.getAllUsers();
    }

    public void createNewUser(User user) {
        userClient.saveUser(user);
    }

    public void delete(Long userId) {
        userClient.deleteUser(userId);
    }

    public User getUser(Long userId) {
        return userClient.getUser(userId);
    }

    public void updateUser(Long userId, User user) {
        userClient.updateUser(userId, user);
    }

}
