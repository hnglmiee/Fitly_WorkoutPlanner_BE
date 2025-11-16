package com.workoutplanner.MiniProject.Controllers;

import com.workoutplanner.MiniProject.Constants.ApiPaths;
import com.workoutplanner.MiniProject.Payload.Response.ApiResponse;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutLogResponse;
import com.workoutplanner.MiniProject.Services.Interfaces.IWorkoutLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.WorkoutLog.ROOT)
public class WorkoutLogController {
    private final IWorkoutLogService workoutLogService;

    public WorkoutLogController(IWorkoutLogService workoutLogService) {
        this.workoutLogService = workoutLogService;
    }

    @GetMapping("/admin")
    public ApiResponse <List<WorkoutLogResponse>> getWorkoutLogs() {
        ApiResponse<List<WorkoutLogResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutLogService.getAllWorkoutLog());
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }

    @GetMapping("/my-workout-logs")
    public ApiResponse <List<WorkoutLogResponse>> getMyWorkoutLogs() {
        ApiResponse<List<WorkoutLogResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutLogService.getMyWorkoutLog());
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }
}
