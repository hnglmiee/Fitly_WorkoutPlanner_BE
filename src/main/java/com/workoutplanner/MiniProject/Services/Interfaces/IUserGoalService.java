package com.workoutplanner.MiniProject.Services.Interfaces;

import com.workoutplanner.MiniProject.Payload.Request.UserGoalRequest;
import com.workoutplanner.MiniProject.Payload.Response.UserGoalCreateResponse;
import com.workoutplanner.MiniProject.Payload.Response.UserGoalResponse;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutPlanResponse;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutSessionChartResponse;

import java.util.List;

public interface IUserGoalService {
    UserGoalResponse checkGoalProgress();
    List<UserGoalResponse> getAllUserGoal();
    UserGoalCreateResponse createUserGoal(UserGoalRequest request);
    UserGoalCreateResponse updateUserGoal(Integer id, UserGoalRequest request);
    boolean deleteUserGoal(Integer id);
    List<WorkoutSessionChartResponse> getWeeklyWorkoutChart();
}
