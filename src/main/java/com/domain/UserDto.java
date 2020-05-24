package com.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String mailAdress;
    private String phoneNumber;
    private String password;

    public UserDto(String firstName, String lastName, String mailAdress, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAdress = mailAdress;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
