package com.example.udemy.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDTO {
    private String username;
    private String firstName;
    private String lastName;
}
