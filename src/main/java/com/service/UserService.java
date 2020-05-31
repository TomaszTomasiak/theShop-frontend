package com.service;

import com.client.UserClient;
import com.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


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

    public void delete(Integer userId) {
        userClient.deleteUser(userId);
    }

    public User getUser(Long userId) {
        return userClient.getUser(userId);
    }

    public User fetchUserByMail(String mail) {
        List<User> usersWithIndicatedMail = users.stream()
                .filter(user -> user.getMailAdress().equals(mail.toLowerCase()))
                .collect(Collectors.toList());
        if (usersWithIndicatedMail.size() != 0) {
            return usersWithIndicatedMail.get(0);
        }
        return new User();
    }

    public Set<User> findByLastName(String lastName) {
        return users.stream().filter(user -> user.getLastName().contains(lastName)).collect(Collectors.toSet());
    }

    public Set<User> findByFirstName(String firstName) {
        return users.stream().filter(user -> user.getFirstName().contains(firstName)).collect(Collectors.toSet());
    }

    public Set<User> findByMail(String mail) {
        return users.stream().filter(user -> user.getMailAdress().contains(mail)).collect(Collectors.toSet());
    }

    public Set<User> findByPhoneNumber(String phone) {
        return users.stream().filter(user -> user.getPhoneNumber().contains(phone)).collect(Collectors.toSet());
    }

}

