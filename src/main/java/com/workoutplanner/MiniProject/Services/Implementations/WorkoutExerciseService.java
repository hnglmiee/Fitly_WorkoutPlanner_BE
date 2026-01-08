package com.workoutplanner.MiniProject.Services.Implementations;

import com.workoutplanner.MiniProject.Exception.AppException;
import com.workoutplanner.MiniProject.Exception.ErrorCode;
import com.workoutplanner.MiniProject.Models.Exercise;
import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Models.WorkoutExercise;
import com.workoutplanner.MiniProject.Models.WorkoutPlan;
import com.workoutplanner.MiniProject.Payload.Request.WorkoutExerciseRequest;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutExerciseResponse;
import com.workoutplanner.MiniProject.Repositories.ExerciseRepository;
import com.workoutplanner.MiniProject.Repositories.UserRepository;
import com.workoutplanner.MiniProject.Repositories.WorkoutExerciseRepository;
import com.workoutplanner.MiniProject.Repositories.WorkoutPlanRepository;
import com.workoutplanner.MiniProject.Services.Interfaces.IWorkoutExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutExerciseService implements IWorkoutExerciseService {
    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public List<WorkoutExerciseResponse> getAllWorkoutExercises() {
        List<WorkoutExercise> workoutExercises = workoutExerciseRepository.findAll();
        try {
            return workoutExercises.stream().map(workoutExercise -> {
                WorkoutExerciseResponse workoutExerciseResponse = new WorkoutExerciseResponse();
                workoutExerciseResponse.setWorkoutExerciseId(workoutExercise.getId());
                workoutExerciseResponse.setPlanId(workoutExercise.getPlan().getId());
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

    @Override
    public List<WorkoutExerciseResponse> getMyWorkoutExercises() {
        User user = getCurrentUser();
        List<WorkoutExercise> workoutExercises = workoutExerciseRepository.findByPlanUser(user);

        return workoutExercises.stream().map(exercise -> {
            WorkoutExerciseResponse workoutExerciseResponse = new WorkoutExerciseResponse();
            workoutExerciseResponse.setWorkoutExerciseId(exercise.getId());
            workoutExerciseResponse.setPlanId(exercise.getPlan().getId());
            workoutExerciseResponse.setPlanTitle(exercise.getPlan().getTitle());
            workoutExerciseResponse.setExerciseId(exercise.getExercise().getId());
            workoutExerciseResponse.setExerciseName(exercise.getExercise().getName());
            workoutExerciseResponse.setSets(exercise.getSets());
            workoutExerciseResponse.setReps(exercise.getReps());
            workoutExerciseResponse.setWeight(exercise.getWeight());
            workoutExerciseResponse.setComments(exercise.getComments());
            return workoutExerciseResponse;
        }).toList();
    }

    @Override
    public WorkoutExerciseResponse createWorkoutExercise(WorkoutExerciseRequest request) {
        User user = getCurrentUser();
        // Tìm WorkoutPlan theo planId và kiểm tra quyền sở hữu
        WorkoutPlan plan = workoutPlanRepository.findById(request.getPlanId()).orElseThrow(() -> new AppException(ErrorCode.WORKOUT_PLAN_NOT_EXISTED));

        // Kiểm tra plan có thuộc về user hiện tại không
        if(!plan.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.WORKOUT_PLAN_NOT_EXISTED);
        }

        // Tìm Exercise theo exerciseId
        Exercise exercise = exerciseRepository.findById(request.getExerciseId()).orElseThrow(() -> new AppException(ErrorCode.EXERCISE_NOT_EXISTED));

        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setPlan(plan);
        workoutExercise.setExercise(exercise);
        workoutExercise.setSets(request.getSets());
        workoutExercise.setReps(request.getReps());
        workoutExercise.setWeight(request.getWeight());
        workoutExercise.setComments(request.getComments());

        WorkoutExercise savedExercise = workoutExerciseRepository.save(workoutExercise);

        WorkoutExerciseResponse workoutExerciseResponse = new WorkoutExerciseResponse();
        workoutExerciseResponse.setWorkoutExerciseId(savedExercise.getId());
        workoutExerciseResponse.setPlanId(savedExercise.getPlan().getId());
        workoutExerciseResponse.setPlanTitle(savedExercise.getPlan().getTitle());
        workoutExerciseResponse.setExerciseId(savedExercise.getId());
        workoutExerciseResponse.setExerciseName(savedExercise.getExercise().getName());
        workoutExerciseResponse.setSets(savedExercise.getSets());
        workoutExerciseResponse.setReps(savedExercise.getReps());
        workoutExerciseResponse.setWeight(savedExercise.getWeight());
        workoutExerciseResponse.setComments(savedExercise.getComments());
        return workoutExerciseResponse;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}
