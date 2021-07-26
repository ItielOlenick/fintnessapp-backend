package com.exercise.fitnessapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_workouts")
@Data
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int exercises;
}
