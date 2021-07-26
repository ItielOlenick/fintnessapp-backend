package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Workout;
import com.exercise.fitnessapp.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class WorkoutController {

    @Autowired
    WorkoutRepository workoutRepository;

    @GetMapping("/workouts")
    public ResponseEntity<List<Workout>> readWorkouts(){
        return new ResponseEntity<List<Workout>>(workoutRepository.findAll(), HttpStatus.OK);
    }
}
