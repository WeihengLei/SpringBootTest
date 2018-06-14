/*
 * Copyright (c) 2017, HSBC. All rights reserved.
 */
package com.example.test.dao;



import com.example.test.entity.MessageResource;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 *
 * @author A-team WeiHeng
 * @date 2017-09-28
 */
public interface MessageResourceRepository extends JpaRepository<MessageResource, Integer> {

}
