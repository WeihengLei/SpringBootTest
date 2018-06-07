package com.example.test.dao;

import com.example.test.entity.security.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    UserRole findById(Integer id);
    Long deleteById(Integer id);

    List<UserRole> findAllByUserId(Integer id);
}