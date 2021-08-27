package com.exercise.fitnessapp.repository;

import com.exercise.fitnessapp.entity.User;
import com.exercise.fitnessapp.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
//    List<Workout> findByUser_Id(String user);
    List<Workout> findByUser_IdAndType(String user, String type);
//    List<Workout> findByTypeAndUser_Id(String type, String user)

}

