package com.example.test.service.impl;


import com.example.test.config.Config;
import com.example.test.model.TestRequest;
import com.example.test.model.TestResponse;
import com.example.test.service.TestService;
//import com.example.test.util.RestTemplateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service("testService")
public class TestServiceImpl implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private Config config;

    //@Autowired
    //private RestTemplateTool restTemplateTool;

    //@Autowired
    //private TestDB testDB;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public TestResponse testCall(String test)  throws Exception{
        TestResponse testResponse= new TestResponse();
        testResponse.setRespCode("200");
        TestRequest testRequest = new TestRequest();
        testRequest.setUser(test);
        testResponse.setResponseBody(testRequest);

        holderIncThread(test);
        return testResponse;
    }


    private String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    public void holderIncThread(String message) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Runnable syncRunnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("test cache:"+message);
            }
        };
        executorService.execute(syncRunnable);
    }


}