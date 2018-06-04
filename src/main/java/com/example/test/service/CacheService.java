package com.example.test.service;

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


@Repository
public interface CacheService {

    Logger LOGGER = LoggerFactory.getLogger(CacheService.class);

    TestMessage testCache() throws BusinessException, UnsupportedEncodingException;

    @Component
    class TestCache implements ApplicationListener<ApplicationReadyEvent> {
        @Autowired
        private CacheService cacheService;
        @Override
        public void onApplicationEvent(final ApplicationReadyEvent event) {
            try {
                cacheService.testCache();
            } catch (BusinessException e) {
                LOGGER.debug(e.getMessage());
            } catch (UnsupportedEncodingException e) {
                LOGGER.debug(e.getMessage());
            }
        }

    }
}




