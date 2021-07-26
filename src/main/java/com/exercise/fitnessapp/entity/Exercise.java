package com.exercise.fitnessapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="tbl_exercises")
@Data
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String bodyPart;
}
