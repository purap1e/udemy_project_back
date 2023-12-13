package com.example.udemy;

import com.example.udemy.entities.Role;
import com.example.udemy.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UdemyApplication {

    public static void main(String[] args) {
        SpringApplication.run(UdemyApplication.class, args);
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean(RoleRepository roleRepository) {
        return (args) -> {
            Role role = new Role();
            role.setName("USER");
            Role role1 = new Role();
            role1.setName("ADMIN");
            roleRepository.save(role1);
            roleRepository.save(role);
        };
    }
}
