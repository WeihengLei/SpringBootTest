package com.example.test.entity;

import lombok.Data;

import javax.persistence.*;



@Entity
@Data
public  class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "role",nullable = false)
    private String role;


}
