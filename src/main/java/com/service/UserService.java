package com.service;

import com.client.TheClientHTTP;
import com.config.AppConfig;
import com.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


import static java.util.Optional.ofNullable;

@Service
public class UserService {

    private final RestTemplate restTemplate = new RestTemplate();
    //private final JsonBuilder<User> jsonBuilder = new JsonBuilder<>();
    private final AppConfig appConfig = AppConfig.getInstance();

    private static UserService userService;
    //private final Session session = Session.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    public List<User> users;

    private UserService() {
        this.users = new ArrayList<>();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "users")
                .build().encode().toUri();
        return url;
    }

    public List<User> getUsers() {
        URI url = getUrl();
        try {
            User[] usersResponse = restTemplate.getForObject(url, User[].class);
            return Arrays.asList(ofNullable(usersResponse).orElse(new User[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public User fetchUserByMail(String mail) {
        List<User> usersWithIndicatedMail = getUsers().stream()
                .filter(user -> user.getMailAdress().equals(mail.toLowerCase()))
                .collect(Collectors.toList());
        if (usersWithIndicatedMail.size() != 0) {
            return usersWithIndicatedMail.get(0);
        }
        return new User();
    }


    public void createNewUser(User user) throws IOException {
        TheClientHTTP client = new TheClientHTTP(appConfig.getBackendEndpoint() + "users");

        try {
            //client.sendObject(user);
            restTemplate.postForObject(getUrl(), user, User.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    public void delete(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "users/" + userId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public User getUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "users/" + userId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, User.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new User();
        }
    }

//    public User getUserByMail(String mail) {
//        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "users/" + mail)
//                .build().encode().toUri();
//        try {
//            return restTemplate.getForObject(url, User.class);
//        } catch (RestClientException e) {
//            LOGGER.error(e.getMessage(), e);
//            return new User();
//        }
//    }

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

