package com.example.udemy.mapper;

import com.example.udemy.dto.user.UserLoginRequestDTO;
import com.example.udemy.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserLoginRequestDTO> {
}
