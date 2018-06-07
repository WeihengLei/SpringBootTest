package com.example.test.dao;

import com.example.test.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findById(Integer id);
    Long deleteById(Integer id);
}