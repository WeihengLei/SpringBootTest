package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ComponentScan("com.example.test")
//@ImportResource("classpath:/integration.xml")
@EnableCaching
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}