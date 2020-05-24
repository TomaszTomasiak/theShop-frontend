package com.service;

import com.client.UserClient;
import com.domain.UserDto;
import com.session.Session;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
@NoArgsConstructor
public class UserService {

    @Autowired
    private Session session;

    @Autowired
    private UserClient userClient;

    public boolean isUserLogged = false;
//    private List<UserDto> userDtos;


    public List<UserDto> getUserDtos() {
//        userDtos = userClient.getAllUsers();
        return userClient.getAllUsers();
    }

    public UserDto fetchUserByMail(String mail) {
        List<UserDto> usersWithIndicatedMail = userClient.getAllUsers().stream()
                .filter(user -> user.getMailAdress().equals(mail.toLowerCase()))
                .collect(Collectors.toList());
        return usersWithIndicatedMail.get(0);
    }

    public void saveUser(UserDto userDto) {
            userClient.saveUser(userDto);
    }


    public void delete(UserDto userDto) {
        userClient.deleteUser(userDto.getId());
    }

    public long count() {
        return getUserDtos().size();
    }

    public String userLogged(UserDto userDto) {
        if (isUserLogged) {
            return "Logged: " + session.getCurrentUserDto().getFirstName() + " " + session.getCurrentUserDto().getLastName();
        }
        return "You must log in to start shopping";
    }

    public UserDto getUser(Long userId) {
        return userClient.getUser(userId);
    }

    public Set<UserDto> findByLastName(String lastName) {
        return getUserDtos().stream().filter(user -> user.getLastName().contains(lastName)).collect(Collectors.toSet());
    }

    public Set<UserDto> findByFirstName(String firstName) {
        return getUserDtos().stream().filter(user -> user.getFirstName().contains(firstName)).collect(Collectors.toSet());
    }

    public Set<UserDto> findByMail(String mail) {
        return getUserDtos().stream().filter(user -> user.getMailAdress().contains(mail)).collect(Collectors.toSet());
    }

    public Set<UserDto> findByPhoneNumber(String phone) {
        return getUserDtos().stream().filter(user -> user.getPhoneNumber().contains(phone)).collect(Collectors.toSet());
    }
}
