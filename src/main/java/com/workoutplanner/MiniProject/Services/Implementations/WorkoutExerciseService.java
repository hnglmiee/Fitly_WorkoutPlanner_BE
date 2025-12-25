package com.workoutplanner.MiniProject.Services.Implementations;

import com.workoutplanner.MiniProject.Models.WorkoutExercise;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutExerciseResponse;
import com.workoutplanner.MiniProject.Repositories.WorkoutExerciseRepository;
import com.workoutplanner.MiniProject.Services.Interfaces.IWorkoutExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutExerciseService implements IWorkoutExerciseService {
    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

    @Override
    public List<WorkoutExerciseResponse> getAllWorkoutExercises() {
        List<WorkoutExercise> workoutExercises = workoutExerciseRepository.findAll();
        try {
            return workoutExercises.stream().map(workoutExercise -> {
                WorkoutExerciseResponse workoutExerciseResponse = new WorkoutExerciseResponse();
                workoutExerciseResponse.setPlanId(workoutExercise.getId());
                workoutExerciseResponse.setPlanTitle(workoutExercise.getPlan().getTitle());
                workoutExerciseResponse.setExerciseId(workoutExercise.getId());
                workoutExerciseResponse.setExerciseName(workoutExercise.getExercise().getName());
                workoutExerciseResponse.setSets(workoutExercise.getSets());
                workoutExerciseResponse.setReps(workoutExercise.getReps());
                workoutExerciseResponse.setWeight(workoutExercise.getWeight());
                workoutExerciseResponse.setComments(workoutExercise.getComments());
                return workoutExerciseResponse;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
