package com.example.test.service.impl;

import com.example.test.dao.TestUserRepository;
import com.example.test.dao.UserRepository;
import com.example.test.entity.TestUser;
import com.example.test.entity.security.User;
import com.example.test.entity.shiro.UserInfo;
import com.example.test.service.UserInfoService;
import com.example.test.util.HttpUriUtil;
import com.example.test.util.RestTemplateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@Service
public class UserServiceImpl implements UserInfoService {

    @Resource
    private UserRepository userRepository;

    private RestTemplateTool restTemplateTool;

    @Resource
    private TestUserRepository testUserRepository;

    @Override
    public  UserInfo findByUsername(String username) {
       // HttpUriUtil.test(null,null,restTemplateTool,"http://oa.gz.gtomato.com.cn:80/GTInterview/appAPI/checkIn/hrToDolist");


        return userRepository.findUserByUsername(username);
    }

    /*@Override
    public  void test() {
        HttpUriUtil.test(null,null,restTemplateTool,"http://oa.gz.gtomato.com.cn:80/GTInterview/appAPI/checkIn/hrToDolist");

    }*/


    //    TestUser
    @Override
    public List<TestUser> getUserList() {
        return testUserRepository.findAll();
    }

    @Override
    public TestUser findUserById(long id) {
        return testUserRepository.findById(id);
    }

    @Override
    public void save(TestUser user) {
        testUserRepository.save(user);
    }

    @Override
    public void edit(TestUser user) {
        testUserRepository.save(user);
    }

    @Override
    public void delete(long id) {
        testUserRepository.delete(id);
    }

}