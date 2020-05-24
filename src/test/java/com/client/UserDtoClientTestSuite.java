package com.client;

import com.config.AppConfig;
import com.domain.UserDto;


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
public class UserDtoClientTestSuite {

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
        UserDto[] userDtos = new UserDto[1];
        userDtos[0] = new UserDto(id, firstName, lastName, mail, phoneNumber, password);

        when(restTemplate.getForObject(url(), UserDto[].class)).thenReturn(userDtos);

        //When
        List<UserDto> fetchedUserDtos = userClient.getAllUsers();

        //Then
        assertEquals(1, fetchedUserDtos.size());
        assertEquals(12, fetchedUserDtos.get(0).getId());
        assertEquals("TestFirstName", fetchedUserDtos.get(0).getFirstName());
    }

    @Test
    public void shouldCreateUser() {
        //Given
        UserDto userDto = new UserDto(1, firstName, lastName, mail, phoneNumber, password);
        UserDto createdUserDto = new UserDto(15, firstName, lastName, mail, phoneNumber, password);

        when(restTemplate.postForObject(url(), userDto, UserDto.class)).thenReturn(createdUserDto);

        //When
        UserDto newUserDto = userClient.saveUser(userDto);

        //Then
        assertEquals(15, newUserDto.getId());
        assertEquals("TestFirstName", newUserDto.getFirstName());

    }

    @Test
    public void shouldReturnEmptyList() {
        //Given

        when(restTemplate.getForObject(url(), UserDto[].class)).thenReturn(null);

        //When
        List<UserDto> fetchedUserDtos = userClient.getAllUsers();

        //Then
        assertEquals(0, fetchedUserDtos.size());

    }

    private URI url() {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/users").build().encode().toUri();
    }

    private URI urlWithParam() {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/users/" + id).build().encode().toUri();
    }
}
