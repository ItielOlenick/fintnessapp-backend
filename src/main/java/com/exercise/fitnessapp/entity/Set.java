package com.exercise.fitnessapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="tbl_set")
@Data
public class Set{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int  reps;

    private int weight;

    private boolean isPr = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exercise_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Exercise exercise;
}
