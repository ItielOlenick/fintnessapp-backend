package com.exercise.fitnessapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="tbl_set")
@Data
public class Set{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String bodyPart;

    private int reps;
}