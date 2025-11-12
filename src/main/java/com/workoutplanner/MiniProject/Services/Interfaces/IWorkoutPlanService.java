package com.workoutplanner.MiniProject.Services.Interfaces;

import com.workoutplanner.MiniProject.Models.WorkoutPlan;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutPlanResponse;

import java.util.List;

public interface IWorkoutPlanService {
    List<WorkoutPlanResponse> getAllWorkoutPlan();
}
