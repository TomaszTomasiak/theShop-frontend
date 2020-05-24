package com.client;

import com.config.AppConfig;
import com.domain.UserDto;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppConfig appConfig;

    public List<UserDto> getAllUsers() {
        try {
            UserDto[] usersResponse = restTemplate.getForObject(getUrl(), UserDto[].class);
            return Arrays.asList(ofNullable(usersResponse).orElse(new UserDto[0]));
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

    public UserDto getUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "/users/" + userId)
                .build().encode().toUri();
        try {
            return restTemplate.getForObject(url, UserDto.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new UserDto();
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

    public void saveUser(UserDto userDto) {
        restTemplate.postForObject(getUrl(), userDto, UserDto.class);
    }

    public void updateUser(Long userId, UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "/users/" + userId)
                .build().encode().toUri();
        try {
        restTemplate.put(url, userDto);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

