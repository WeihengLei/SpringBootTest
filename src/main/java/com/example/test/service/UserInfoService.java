package com.example.test.service;

import com.example.test.entity.shiro.UserInfo;
import org.springframework.stereotype.Repository;



@Repository
public interface UserInfoService {


    UserInfo findByUsername(String username);
}




