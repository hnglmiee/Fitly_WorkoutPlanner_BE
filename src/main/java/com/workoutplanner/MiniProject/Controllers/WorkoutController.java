package com.workoutplanner.MiniProject.Controllers;

import com.workoutplanner.MiniProject.Constants.ApiPaths;
import com.workoutplanner.MiniProject.Payload.Response.ApiResponse;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutPlanResponse;
import com.workoutplanner.MiniProject.Services.Interfaces.IWorkoutPlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.WorkoutPlan.ROOT)
public class WorkoutController {
    private final IWorkoutPlanService workoutPlanService;

    public WorkoutController(IWorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @GetMapping
    public ApiResponse<List<WorkoutPlanResponse>> getAllWorkoutPlans() {
        ApiResponse<List<WorkoutPlanResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutPlanService.getAllWorkoutPlan());
        apiResponse.setMessage("Success");
        return apiResponse;
    }
}
