package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Exercise;
import com.exercise.fitnessapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ExerciseController {

    @Autowired
    ExerciseRepository exerciseRepository;

    @GetMapping("/exercises")
    public ResponseEntity<List<Exercise>> readExercises(){
        return new ResponseEntity<List<Exercise>>(exerciseRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/exercises")
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise){
        return new ResponseEntity<Exercise>(exerciseRepository.save(exercise), HttpStatus.CREATED);
    }

    @DeleteMapping("/exercises/{id}")
    public ResponseEntity<HttpStatus> deleteExercise (@PathVariable Integer id){
        exerciseRepository.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
