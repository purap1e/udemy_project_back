package com.example.udemy.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
