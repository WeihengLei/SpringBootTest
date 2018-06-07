package com.example.test.entity.security;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public  class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_id",nullable = false)
    private Integer userId;
    @Column(name = "role_id",nullable = false)
    private Integer roleId;


}
