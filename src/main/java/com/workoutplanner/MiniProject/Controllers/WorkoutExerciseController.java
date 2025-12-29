package com.workoutplanner.MiniProject.Controllers;

import com.workoutplanner.MiniProject.Constants.ApiPaths;
import com.workoutplanner.MiniProject.Models.WorkoutExercise;
import com.workoutplanner.MiniProject.Payload.Request.WorkoutExerciseRequest;
import com.workoutplanner.MiniProject.Payload.Response.ApiResponse;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutExerciseResponse;
import com.workoutplanner.MiniProject.Services.Interfaces.IWorkoutExerciseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.WorkoutExercise.ROOT)
public class WorkoutExerciseController {
    private final IWorkoutExerciseService workoutExerciseService;

    public WorkoutExerciseController(IWorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @GetMapping("/admin")
    public ApiResponse<List<WorkoutExerciseResponse>> getAllWorkoutExercises() {
        ApiResponse<List<WorkoutExerciseResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutExerciseService.getAllWorkoutExercises());
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }

    @GetMapping("/my-workout-exercise")
    public ApiResponse<List<WorkoutExerciseResponse>> getMyWorkoutExercises() {
        ApiResponse<List<WorkoutExerciseResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutExerciseService.getMyWorkoutExercises());
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }

    @PostMapping("/{id}")
    public ApiResponse<WorkoutExerciseResponse> createWorkoutExercises(@RequestBody @Valid WorkoutExerciseRequest request) {
        ApiResponse<WorkoutExerciseResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutExerciseService.createWorkoutExercise(request));
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }
}
