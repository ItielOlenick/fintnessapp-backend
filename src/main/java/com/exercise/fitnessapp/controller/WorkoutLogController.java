package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Set;

import com.exercise.fitnessapp.entity.WorkoutLog;
import com.exercise.fitnessapp.repository.SetRepository;
import com.exercise.fitnessapp.repository.UserRepository;
import com.exercise.fitnessapp.repository.WorkoutLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class WorkoutLogController {

    @Autowired
    WorkoutLogRepository workoutLogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SetRepository setRepository;

    @GetMapping("/logs")
    public ResponseEntity<List<WorkoutLog>> readWorkoutLog(@RequestParam("user") String user) {
        return new ResponseEntity<List<WorkoutLog>>(workoutLogRepository.findByUser_IdOrderByCreatedAtDesc(user), HttpStatus.OK);
    }

    @PostMapping("/logs")
    public ResponseEntity<List<Set>> creatWorkoutLog(@RequestBody WorkoutLog workout) {
        List<Set> broken = checkPr(workout, workout.getUser().getId());
        workoutLogRepository.save(workout);
        return new ResponseEntity<List<Set>>(broken, HttpStatus.CREATED);
    }

    @GetMapping("/logs/{id}")
    public ResponseEntity<WorkoutLog> readWorkoutLog(@PathVariable Integer id) {
        return new ResponseEntity<WorkoutLog>(workoutLogRepository.findById(id).get(), HttpStatus.OK);
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<HttpStatus> deleteWorkout(@PathVariable Integer id) {
        workoutLogRepository.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/logs")
    public ResponseEntity<List<Set>> updateWorkout(@RequestBody WorkoutLog workout) {
        List<Set> broken = checkPr(workout, workout.getUser().getId());
        workoutLogRepository.save(workout);
        return new ResponseEntity<List<Set>>(broken, HttpStatus.CREATED);
    }


    public List<Set> checkPr(WorkoutLog log, String userId) {

        List<Set> brokenPrs = new ArrayList<>();
        List<Set> userPrs = setRepository.findByUser_Id(userId);

        java.util.Set<String> nameList = new HashSet<>();
        List<Set> distinctSets = log.getSets().stream().filter(s -> nameList.add(s.getName())).collect(Collectors.toList());


        for (Set set : distinctSets
        ) {
            System.out.println("******************set in check: "+set.getName());
            if (userPrs.stream().noneMatch(o -> o.getName().equals(set.getName()))) {
//                First time using this exercise
                Set maxSet = log.getSets().stream().filter(o -> o.getName().equals(set.getName())).max(Comparator.comparing(Set::getWeight)).orElseThrow(NoSuchElementException::new);
                maxSet.setPr(true);
            } else {

                Set existingPr = setRepository.findByUser_IdAndNameIsOrderByWeightDesc(userId, set.getName()).get(0);
                Set maxSet = log.getSets().stream().filter(o -> o.getName().equals(set.getName())).max(Comparator.comparing(Set::getWeight)).orElseThrow(NoSuchElementException::new);

                System.out.println("Existing pr for" + set.getName() + " is " + existingPr);
                System.out.println("Checking against " + maxSet);
                maxSet.setPr(true);
                if (existingPr.getWeight() < maxSet.getWeight()) {
//                    existingPr.setPr(false);


                    System.out.println("set to break pr:" + maxSet);
                    System.out.println("updated pr, user's pr state: " + userPrs);
                    Set copy = new Set();
                    copy.setWeight(existingPr.getWeight());
                    copy.setName(existingPr.getName());
                    brokenPrs.add(copy);
                    brokenPrs.add(maxSet);

                }
            }
        }
        return brokenPrs;
    }
}
