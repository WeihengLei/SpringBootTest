package com.example.test.util;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {

    public void holderIncThread(String authHeader, String appId) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        Runnable syncRunnable = () -> {
            //test
        };
        executorService.execute(syncRunnable);
    }
}