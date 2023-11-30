package com.example.udemy.repositories;

import com.example.udemy.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
