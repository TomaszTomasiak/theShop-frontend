package com.client;

import com.config.TheShopBackendConfig;
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
    private static final String ENDPOINT = TheShopBackendConfig.getUsers();


    private RestTemplate restTemplate = new RestTemplate();

    private URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .build().encode().toUri();
        return url;
    }

    public List<User> getAllUsers() {
        try {
            User[] usersResponse = restTemplate.getForObject(getUrl(), User[].class);
            return Arrays.asList(ofNullable(usersResponse).orElse(new User[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
        /*
        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url(), TrelloBoardDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
         */
    }

    public User getUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + userId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, User.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new User();
        }
    }

    public void deleteUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + userId)
                .build().encode().toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void saveUser(User user) {
        try {
            restTemplate.postForObject(getUrl(), user, User.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }


//        TheClientHTTP client = new TheClientHTTP(appConfig.getBackendEndpoint() + "users");
//
//        try {
//            //client.sendObject(user);
//            restTemplate.postForObject(getUrl(), user, User.class);
//        } catch (RestClientException e) {
//            LOGGER.error(e.getMessage(), e);
//        }
    }

    public void updateUser(Long userId, User user) {
        URI url = UriComponentsBuilder.fromHttpUrl(getUrl() + "/" + userId)
                .build().encode().toUri();
        try {
        restTemplate.put(url, user);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

