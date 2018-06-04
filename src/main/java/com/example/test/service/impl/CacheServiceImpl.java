package com.example.test.service.impl;

import com.example.test.config.Config;
import com.example.test.exceptions.BusinessException;
import com.example.test.model.TestMessage;
import com.example.test.service.CacheService;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Autowired
    private Config config;

    @Autowired
    private StringEncryptor stringEncryptor;

    @Override
    @Cacheable(value = "testMessage")
    public TestMessage testCache()throws BusinessException, UnsupportedEncodingException {

        double startTime = System.currentTimeMillis();
        LOGGER.info("@@@@@ Cache testMessage start: ### USE TIME [ "+startTime+" ] ms");

        TestMessage testMessage = new TestMessage();
        System.out.println("Cache testMessage");
        LOGGER.info("@@@@@ Cache testMessage end: ### USE TIME [ "+(System.currentTimeMillis()-startTime)/1000+" ] s");

        return testMessage;
    }


}