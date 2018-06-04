package com.example.test.controller;

import com.example.test.model.BaseResponse;
import com.example.test.model.TestMessage;
import com.example.test.model.TestResponse;
import com.example.test.service.CacheService;
import com.example.test.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
//@RequestMapping("/api")
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @Autowired
    private CacheService cacheService;


    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasRole('USER')")
    @GetMapping("/test/{key}")//@Valid @RequestBody Message message,
    public BaseResponse test(@PathVariable("key") String key, HttpServletRequest request) throws Exception {
        final String method = "TestController.test";
        TestController.LOGGER.debug("{} enter", method);
        TestController.LOGGER.debug("{} request is {}", method, key);

        TestMessage testMessage=cacheService.testCache();

        TestResponse testResponse=testService.testCall(key);
        TestController.LOGGER.debug("{}, response is {}", method, testResponse.toString());
        TestController.LOGGER.debug("{} exit", method);

        return testResponse;
    }

    /*@GetMapping("/login")//@Valid @RequestBody Message message,
    public BaseResponse login( HttpServletRequest request) throws Exception {
        TestResponse testResponse=testService.testCall("login");
        return testResponse;
    }*/

    /*@RequestMapping("/")
    public String index() {
        return "home";
    }*/



}
