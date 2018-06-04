package com.example.test.model;


import java.io.Serializable;

public class TestMessage implements Serializable{


    private String username;
    private String password;

    public TestMessage() {
    }

    public TestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
