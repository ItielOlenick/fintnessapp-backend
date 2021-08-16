//package com.exercise.fitnessapp.entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "tbl_users")
//@Data
//public class User {
//
//    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    private String name;
//
//    private String email;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Workout> workouts;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Exercise> exercises;
//}