package com.service;

import com.client.UserClient;
import com.domain.User;
import com.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private Session session;

    @Autowired
    private UserClient userClient;

    public boolean isUserLogged = false;

    public List<User> users;

    public List<User> getUsers() {
        users = userClient.getAllUsers();
        return users;
    }

    public User fetchUserByMail(String mail) {
        List<User> usersWithIndicatedMail = userClient.getAllUsers().stream()
                .filter(user -> user.getMailAdress().equals(mail.toLowerCase()))
                .collect(Collectors.toList());
        return usersWithIndicatedMail.get(0);
    }

    public void createNewUser(User user) {
          userClient.saveUser(user);
    }

    public void delete(User user) {
        userClient.deleteUser(user.getId());
    }

    public long count() {
        return users.size();
    }

    public String userLogged(User user) {
        if (isUserLogged) {
            return "Logged: " + session.getCurrentUser().getFirstName() + " " + session.getCurrentUser().getLastName();
        }
        return "You must log in to start shopping";
    }

    public User getUser(Long userId) {
        return userClient.getUser(userId);
    }

    public Set<User> findByLastName(String lastName) {
        return getUsers().stream().filter(user -> user.getLastName().contains(lastName)).collect(Collectors.toSet());
    }

    public Set<User> findByFirstName(String firstName) {
        return getUsers().stream().filter(user -> user.getFirstName().contains(firstName)).collect(Collectors.toSet());
    }

    public Set<User> findByMail(String mail) {
        return getUsers().stream().filter(user -> user.getMailAdress().contains(mail)).collect(Collectors.toSet());
    }

    public Set<User> findByPhoneNumber(String phone) {
        return getUsers().stream().filter(user -> user.getPhoneNumber().contains(phone)).collect(Collectors.toSet());
    }
}
