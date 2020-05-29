package com.client;

import com.config.AppConfig;
import com.config.JsonBuilder;
import com.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;


import static java.util.Optional.ofNullable;

@Component
public class UserClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserClient.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private AppConfig appConfig;

    public List<User> getAllUsers() {
        try {
            User[] usersResponse = restTemplate.getForObject(getUrl(), User[].class);
            return Arrays.asList(ofNullable(usersResponse).orElse(new User[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "/users")
                .build().encode().toUri();
        return url;
    }

    public User getUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "/users/" + userId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, User.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new User();
        }
    }

    public void deleteUser(Integer userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "/users/" + userId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public User saveUser(User user) {
        try {
            User created = restTemplate.postForObject(getUrl(), user, User.class);
           return created;
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public void updateUser(Long userId, User user) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "/users/" + userId)
                .build().encode().toUri();
        try {
        restTemplate.put(url, user);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

