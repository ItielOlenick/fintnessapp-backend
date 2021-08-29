package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Set;
import com.exercise.fitnessapp.entity.User;
import com.exercise.fitnessapp.entity.WorkoutLog;
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

    @GetMapping("/logs")
    public ResponseEntity<List<WorkoutLog>> readWorkoutLog(@RequestParam("user") String user) {
        return new ResponseEntity<List<WorkoutLog>>(workoutLogRepository.findByUser_Id(user), HttpStatus.OK);
    }

    @PostMapping("/logs")
    public ResponseEntity<WorkoutLog> creatWorkoutLog(@RequestBody WorkoutLog workout) {
        workoutLogRepository.save(workout);
        checkPr(workout, workout.getUser().getId());

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
        List<Set> userPrs = userRepository.findById(userId).get().getPrs();
        for (Set set : log.getSets()
        ) {
            System.out.println("are we even here?");
            if (userPrs.stream().noneMatch(o -> o.getName().equals(set.getName()))) {
                userPrs.add(set);
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
                    userPrs.add(set);
                    System.out.println("set to break pr:" + set);
                    System.out.println("updated pr, user's pr state: " + userPrs);

                    brokenPrs.add(new String[] {existingPr.getName(),set.getName()});
                    prBroken = true;
                }

            }

        }
//        if (prBroken || firstTime) {
//            User user = userRepository.findById(userId).get();
//            user.setPrs(userPrs);
//            System.out.println("updated user" + user);
//            userRepository.save(user);
//        }
        return brokenPrs;
    }
}
