package com.workoutplanner.MiniProject.Services.Interfaces;

import com.workoutplanner.MiniProject.Payload.Request.WorkoutScheduleRequest;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutScheduleResponseAdmin;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutScheduleResponseCustomer;

import java.util.List;

public interface IWorkoutScheduleService {
    List<WorkoutScheduleResponseAdmin> getAllWorkoutSchedule();
    List<WorkoutScheduleResponseCustomer> getMySchedule();
    WorkoutScheduleResponseAdmin getWorkoutScheduleById(Integer id);
    WorkoutScheduleResponseCustomer createWorkoutSchedule(WorkoutScheduleRequest request);
    WorkoutScheduleResponseCustomer updateWorkoutPlan(Integer id, WorkoutScheduleRequest request);
    boolean deleteWorkoutSchedule(Integer id);
    boolean deleteWorkoutScheduleByAdmin(Integer id);
}
