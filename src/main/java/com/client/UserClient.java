package com.client;


import com.config.AppConfig;
import com.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Optional.ofNullable;


@Component
public class UserClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserClient.class);
        private RestTemplate restTemplate = new RestTemplate();

    public Set<User> getUsers() {

        URI url = getUrl();
        try {
            User[] usersResponse = restTemplate.getForObject(url, User[].class);
            return new HashSet<>(Arrays.asList(ofNullable(usersResponse).orElse(new User[0])));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new HashSet<>();
        }
    }

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/users")
                .build().encode().toUri();
        return url;
    }

    public User getUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/users" + userId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, User.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new User();
        }
    }

    public void deleteUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "/users" + userId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public User createNewUser(User user) {
        URI url = getUrl();
        return restTemplate.postForObject(url, user, User.class);
    }

    public void updateUser(User user) {
        URI url = getUrl();
        restTemplate.put(url, user);
    }
}

