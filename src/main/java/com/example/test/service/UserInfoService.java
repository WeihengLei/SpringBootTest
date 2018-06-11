package com.example.test.service;

import com.example.test.entity.TestUser;
import com.example.test.entity.shiro.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserInfoService {


    UserInfo findByUsername(String username);

//    TestUser
    List<TestUser> getUserList();

    TestUser findUserById(long id);

    void save(TestUser user);

    void edit(TestUser user);

    void delete(long id);
}




