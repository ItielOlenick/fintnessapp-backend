package com.exercise.fitnessapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tbl_user")
@Data
public class User {

    @Id
    private String id;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Workout> workouts;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Set> prs;
}
