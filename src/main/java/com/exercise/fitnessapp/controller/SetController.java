package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Set;
import com.exercise.fitnessapp.repository.SetRepository;
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
public class SetController {

    @Autowired
    SetRepository setRepository;

    @GetMapping("/active_exercises")
    public ResponseEntity<List<Set>> readSets() {
        return new ResponseEntity<List<Set>>(setRepository.findAll(), HttpStatus.OK);
    }
}
