package com.example.test.dao;

import com.example.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(Integer id);
    Long deleteById(Integer id);

    User findUserByUsername(String username);
}