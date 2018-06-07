package com.example.test.dao;

import com.example.test.entity.security.User;
import com.example.test.entity.shiro.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    UserInfo findUserByUsername(String username);
}