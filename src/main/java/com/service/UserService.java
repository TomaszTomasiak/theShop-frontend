package com.service;

import com.client.UserClient;
import com.domain.User;
import com.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private Session session;

    @Autowired
    private UserClient userClient;

    //private JsonBuilder<User> jsonBuilder = new JsonBuilder<>();
    //private List<User> users;
    public boolean isUserLogged = false;
    private Set<User> users;


    public Set<User> getUsers() {
        users = userClient.getAllUsers();
        return users;
    }
//    public List<User> fetchAll() {
//        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/users")
//                .encode()
//                .build()
//                .toUri();
//        Optional<User> users = Optional.ofNullable(restTemplate.getForObject(url, User.class));
//
//        return this.users = new ArrayList<>(users
//                .map(Arrays::asList)
//                .orElse(new ArrayList<>()));
//    }

    public User fetchUserByMail(String mail) {
        List<User> usersWithIndicatedMail = userClient.getAllUsers().stream()
                .filter(user -> user.getMailAdress().equals(mail.toLowerCase()))
                .collect(Collectors.toList());
        return usersWithIndicatedMail.get(0);
    }

    public Set<User> findByMailOrFirstnameOrLastName(String string) {
        return users.stream()
                .filter(user -> user.getLastName().contains(string.toLowerCase()) ||
                        user.getFirstName().contains(string.toLowerCase()) ||
                        user.getMailAdress().contains(string.toLowerCase()))
                .collect(Collectors.toSet());
    }

    public void save(User user) {
        if (session.getCurrentUser().getMailAdress()==null) {
            userClient.createNewUser(user);
        } else {
            userClient.updateUser(user.getId(), user);
        }
    }

//    public void update(User user) {
//        String url = AppConfig.backendEndpoint + "user";
//        restTemplate.put(url, jsonBuilder.prepareJson(user));
//    }

    public void delete(User user) {
        userClient.deleteUser(user.getId());
    }

//    public long count() {
//        String url = AppConfig.backendEndpoint + "user/count";
//        return restTemplate.getForObject(url, Long.class);
//    }

    public String userLogged(User user) {
        if (isUserLogged) {
            return "Logged: " + session.getCurrentUser().getFirstName() + " " + session.getCurrentUser().getLastName();
        }
        return "You must log in to start shopping";
    }

    public User getUser(Long userId) {
        return userClient.getUser(userId);
    }

}
