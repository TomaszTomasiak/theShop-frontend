package com.client;

import com.config.TheShopBackendConfig;
import com.domain.User;


import com.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
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
    private Long id = 12L;

    @InjectMocks
    private UserService userService;

    @Mock
    private TheShopBackendConfig theShopBackendConfig;

    @Mock
    RestTemplate restTemplate;

    @Before
    public void init() {
        when(theShopBackendConfig.getBackendEndpoint()).thenReturn(endpoint);
    }


    @Test
    public void shouldFetchAllUsers() throws URISyntaxException {
        //Given
        User[] users = new User[1];
        users[0] = new User(id, firstName, lastName, mail, phoneNumber, password);

        when(restTemplate.getForObject(url(), User[].class)).thenReturn(users);

        //When
        List<User> fetchedUsers = userService.getUsers();

        //Then
        assertEquals(1, fetchedUsers.size());
        assertEquals(12, fetchedUsers.get(0).getId());
        assertEquals("TestFirstName", fetchedUsers.get(0).getFirstName());
    }

    @Test
    public void shouldCreateUser() throws IOException {
        //Given
        User user = new User(1L, firstName, lastName, mail, phoneNumber, password);
        User createdUser = new User(15L, firstName, lastName, mail, phoneNumber, password);

        when(restTemplate.postForObject(url(), user, User.class)).thenReturn(createdUser);

        //When
        userService.createNewUser(user);
        User newUser = userService.fetchUserByMail(createdUser.getMailAdress());

        //Then
        assertEquals(15, newUser.getId());
        assertEquals("TestFirstName", newUser.getFirstName());

    }

    @Test
    public void shouldReturnEmptyList() {
        //Given

        when(restTemplate.getForObject(url(), User[].class)).thenReturn(null);

        //When
        List<User> fetchedUsers = userService.getUsers();

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
