package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Set;
import com.exercise.fitnessapp.entity.User;
import com.exercise.fitnessapp.entity.WorkoutLog;
import com.exercise.fitnessapp.repository.SetRepository;
import com.exercise.fitnessapp.repository.UserRepository;
import com.exercise.fitnessapp.repository.WorkoutLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public ResponseEntity<WorkoutLog> creatWorkoutLog(@RequestBody WorkoutLog workout) {
//        workoutLogRepository.save(workout);
        checkPr(workout, workout.getUser().getId());
        workoutLogRepository.save(workout);
        return new ResponseEntity<WorkoutLog>(HttpStatus.CREATED);
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
    public ResponseEntity<WorkoutLog> updateWorkout(@RequestBody WorkoutLog workout) {
        return new ResponseEntity<WorkoutLog>(workoutLogRepository.save(workout), HttpStatus.OK);
    }


    public List<String[]> checkPr(WorkoutLog log, String userId) {
        List<String[]> brokenPrs = new ArrayList<>();
        boolean prBroken = false;
        boolean firstTime = false;
//        List<Set> userPrs = userRepository.findById(userId).get().getPrs();
        List<Set> userPrs = setRepository.findByUser_IdAndPrIsTrue(userId);
        for (Set set : log.getSets()
        ) {
            System.out.println("are we even here?");
            if (userPrs.stream().noneMatch(o -> o.getName().equals(set.getName()))) {

                set.setPr(true);
                firstTime = true;
                System.out.println("First time using this exercise");
            } else {
                List<Set> findPrsByName = userPrs.stream().filter(o -> o.getName().equals(set.getName())).collect(Collectors.toList());
                Set existingPr = findPrsByName.get(findPrsByName.size() - 1);
                System.out.println("Existing pr for" + set.getName() + " is " + existingPr);
                System.out.println("Checking against " + set);
                if (existingPr.getWeight() < set.getWeight()) {
//                    uncomment if want to show only last pr as pr
//                    existingPr.setPr(false);
                    set.setPr(true);

                    System.out.println("set to break pr:" + set);
                    System.out.println("updated pr, user's pr state: " + userPrs);
                    brokenPrs.add(new String[] {existingPr.getName(),set.getName()});
                    prBroken = true;
                }
            }
        }
        return brokenPrs;
    }
}
