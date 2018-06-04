package com.example.test.service;

import com.example.test.model.TestResponse;
import org.springframework.stereotype.Repository;


@Repository
public interface TestService {

    TestResponse testCall(String test)  throws Exception;

}