package com.workoutplanner.MiniProject.Controllers;

import com.workoutplanner.MiniProject.Constants.ApiPaths;
import com.workoutplanner.MiniProject.Payload.Request.UserGoalRequest;
import com.workoutplanner.MiniProject.Payload.Request.WorkoutLogRequest;
import com.workoutplanner.MiniProject.Payload.Response.ApiResponse;
import com.workoutplanner.MiniProject.Payload.Response.UserGoalCreateResponse;
import com.workoutplanner.MiniProject.Payload.Response.UserGoalResponse;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutSessionChartResponse;
import com.workoutplanner.MiniProject.Services.Implementations.UserGoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.UserGoal.ROOT)
public class UserGoalController {
    @Autowired
    private UserGoalService userGoalService;

    @GetMapping("/progress")
    public ApiResponse<UserGoalResponse> checkGoalProgress() {
        ApiResponse<UserGoalResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userGoalService.checkGoalProgress());
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }

    @GetMapping()
    public ApiResponse<List<UserGoalResponse>> getAllUserGoals() {
        ApiResponse<List<UserGoalResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userGoalService.getAllUserGoal());
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }

    @PostMapping()
    public ApiResponse<UserGoalCreateResponse> createUserGoal(@RequestBody @Valid UserGoalRequest request) {
        ApiResponse<UserGoalCreateResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userGoalService.createUserGoal(request));
        apiResponse.setMessage("Create data successfully!");
        return apiResponse;
    }

    @PutMapping("/{id}")
    public ApiResponse<UserGoalCreateResponse> updateUserGoal(@PathVariable Integer id, @RequestBody @Valid UserGoalRequest request) {
        ApiResponse<UserGoalCreateResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userGoalService.updateUserGoal(id, request));
        apiResponse.setMessage("Update data successfully!");
        return apiResponse;
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteUserGoal(@PathVariable Integer id) {
        userGoalService.deleteUserGoal(id);
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(Boolean.TRUE);
        apiResponse.setMessage("Delete data successfully!");
        return apiResponse;
    }
    @GetMapping("/workouts/chart/weekly")
    public ApiResponse<List<WorkoutSessionChartResponse>> getWeeklyWorkoutChart() {
        ApiResponse<List<WorkoutSessionChartResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userGoalService.getWeeklyWorkoutChart());
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }
}
