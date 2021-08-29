package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Category;
import com.exercise.fitnessapp.entity.Exercise;
import com.exercise.fitnessapp.entity.wger.WgerDatabase;
import com.exercise.fitnessapp.entity.wger.Result;
import com.exercise.fitnessapp.repository.CategoryRepository;
import com.exercise.fitnessapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableScheduling
public class WgerController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

//    @Scheduled(fixedRate = 604800000)
    @PutMapping
    public void updateWgerDatabase() {
        List<Exercise> exercises = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        WgerDatabase exerciseDatabase =
                webClientBuilder.build()
                        .get()
                        .uri("https://wger.de/api/v2/exercise/?language=2&limit=1000")
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(WgerDatabase.class).block();
        assert exerciseDatabase != null;
        for (Result result : exerciseDatabase.getResults()) {
            Exercise exercise = new Exercise();

            exercise.setWgerId(result.getId());
            exercise.setOwner("wger");
            exercise.setName(result.getName());
            exercise.setCategory(result.getCategory());
            exercise.setExercisePath("[\"wgerExercises\","+result.getCategory()+",\""+result.getName()+"\"]");
            exercises.add(exercise);
        }

        WgerDatabase categoryDatabase =
                webClientBuilder.build()
                        .get()
                        .uri("https://wger.de/api/v2/exercisecategory/")
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(WgerDatabase.class).block();
        assert categoryDatabase != null;
        for (Result result : categoryDatabase.getResults()) {
            Category category = new Category();
            category.setId(result.getId());
            category.setName(result.getName());
            categories.add(category);
        }
        exerciseRepository.saveAll(exercises);
        categoryRepository.saveAll(categories);


    }
}

