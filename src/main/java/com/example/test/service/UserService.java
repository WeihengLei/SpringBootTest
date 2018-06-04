package com.example.test.service;

import com.example.test.entity.User;
import com.example.test.exceptions.BusinessException;
import com.example.test.model.TestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.util.List;


@Repository
public interface UserService {

    public List<User> getUserList();

    public User findUserById(Integer id);

    public void save(User user);

    public void edit(User user);

    public void delete(Integer id);
}




