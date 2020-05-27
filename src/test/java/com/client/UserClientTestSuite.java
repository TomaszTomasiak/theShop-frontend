package com.client;

import com.config.AppConfig;
import com.domain.User;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URISyntaxException;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserClientTestSuite {

    private String endpoint = "http://test.com";
    private String test = "test";
    private String firstName = "TestFirstName";
    private String lastName = "TestLastName";
    private String mail = "abc@test.com";
    private String phoneNumber = "123456789";
    private String password = "passwordTest";
    private Integer id = 12;

    @InjectMocks
    private UserClient userClient;

    @Mock
    private AppConfig appConfig;

    @Mock
    RestTemplate restTemplate;

    @Before
    public void init() {
        when(appConfig.getBackendEndpoint()).thenReturn(endpoint);
    }


    @Test
    public void shouldFetchAllUsers() throws URISyntaxException {
        //Given
        User[] users = new User[1];
        users[0] = new User(id, firstName, lastName, mail, phoneNumber, password);

        when(restTemplate.getForObject(url(), User[].class)).thenReturn(users);

        //When
        List<User> fetchedUsers = userClient.getAllUsers();

        //Then
        assertEquals(1, fetchedUsers.size());
        assertEquals(12, fetchedUsers.get(0).getId());
        assertEquals("TestFirstName", fetchedUsers.get(0).getFirstName());
    }

    @Test
    public void shouldCreateUser() {
        //Given
        User user = new User(1, firstName, lastName, mail, phoneNumber, password);
        User createdUser = new User(15, firstName, lastName, mail, phoneNumber, password);

        when(restTemplate.postForObject(url(), user, User.class)).thenReturn(createdUser);

        //When
        User newUser = userClient.saveUser(user);

        //Then
        assertEquals(15, newUser.getId());
        assertEquals("TestFirstName", newUser.getFirstName());

    }

    @Test
    public void shouldReturnEmptyList() {
        //Given

        when(restTemplate.getForObject(url(), User[].class)).thenReturn(null);

        //When
        List<User> fetchedUsers = userClient.getAllUsers();

        //Then
        assertEquals(0, fetchedUsers.size());

    }

    private URI url() {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/users").build().encode().toUri();
    }

    private URI urlWithParam() {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/users/" + id).build().encode().toUri();
    }
}
