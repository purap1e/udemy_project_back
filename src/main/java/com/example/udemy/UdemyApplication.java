package com.example.udemy;

import com.example.udemy.entities.User;
import com.example.udemy.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class UdemyApplication {

    public static void main(String[] args) {
        SpringApplication.run(UdemyApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
//          userService.saveUser(new User(null,"daniyar","purap1e","danik6785",new ArrayList<>()));
        };
    }
}
