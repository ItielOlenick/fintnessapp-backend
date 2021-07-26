package com.exercise.fitnessapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private String password;
}
