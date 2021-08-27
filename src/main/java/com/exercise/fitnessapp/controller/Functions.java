package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Set;
import com.exercise.fitnessapp.entity.WorkoutLog;
import com.exercise.fitnessapp.repository.UserRepository;
import com.exercise.fitnessapp.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class Functions {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

/*
TODO
    1. get pr's as list of exercises.
    2. for each set in the new workout if there is an exercise with the same name in the pr's list
    3.        if the weight is higher in the new exercise then update the weight
    4.            notice the user that a new pr is set (if there are a few this will be a list)
    5.        else add this exercise to the pr list
    */
    public void checkPr(WorkoutLog log, String userId) {
        List<Set> userPrs = userRepository.findById(userId).get().getPrs();
        for (Set set : log.getSets()
        ) {
            //check for entries to add

            Set existingPr = userPrs.stream().filter(o -> o.getName().equals(set.getName())).findFirst().get();
            if (existingPr.getWeight() < set.getWeight()){
                existingPr.setWeight(set.getWeight());
            }

            if (!userPrs.stream().anyMatch(o-> o.getName().equals(set.getName()))){
                userPrs.add(set);
            }
        }

        userRepository.findById(userId).get().setPrs(userPrs);
    }
}
