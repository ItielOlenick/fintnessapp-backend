package com.exercise.fitnessapp.repository;

import com.exercise.fitnessapp.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetRepository extends JpaRepository<Set, Integer>{
}


