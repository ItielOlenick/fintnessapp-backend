package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Workout;
import com.exercise.fitnessapp.repository.WorkoutRepository;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/workouts")
    public ResponseEntity<Workout> creatWorkout(@RequestBody Workout workout){
        return new ResponseEntity<Workout>(workoutRepository.save(workout), HttpStatus.CREATED);
    }

    @GetMapping("/workouts/{id}")
    public ResponseEntity<Workout> readWorkout (@PathVariable Integer id) {
        return new ResponseEntity<Workout>(workoutRepository.findById(id).get(), HttpStatus.OK);
    }
}
