package com.exercise.fitnessapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="tbl_exercises")
@Data
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer wgerId;

    private String name;

    private Integer category;

    private String exercisePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="workout_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Workout workout;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "exercise")
    private List<Set> sets;

}
