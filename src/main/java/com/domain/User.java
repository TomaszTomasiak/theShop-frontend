package com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String mailAdress;
    @JsonProperty
    private String phoneNumber;
    @JsonProperty
    private String password;

    public User(String firstName, String lastName, String mailAdress, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAdress = mailAdress;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
